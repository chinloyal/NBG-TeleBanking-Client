package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.abstracts.ArtificialIntelligence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import communication.Request;
import communication.Response;
import connection.Client;
import enums.CardType;
import enums.TransactionType;
import models.ChartVals;
import models.Transaction;
import models.User;

public class TransactionController extends ArtificialIntelligence {

	private Client client;
	private User loggedInUser;

	private Logger logger = LogManager.getLogger(TransactionController.class);

	public TransactionController() {
		this(new Client());
	}

	public TransactionController(Client client) {
		this.client = client;
		mute = true;

		loggedInUser = AuthController.getLoggedInUser();
		
	}

	public void processAction(String response, String request) {

		new Thread(() -> {
			if (response.length() > 35 && response.substring(0, 36).equals("OK, I need a few seconds to transfer")) {
				handleTransfer(response);
			} else if (response.equals("Give me a few seconds to check your balance.")) {
				handleInquiry(response);
			} else if (response.equals("Excellent, just give me a moment to process your deposit.")) {

				handleDeposit(response, request);
			} else if (response.length() > 14 && response.substring(0, 15).equals("Sure, I'll find")) {
				handleBillPayment(response);
			}

		}).start();
	}

	private void handleTransfer(String response) {
		String regex = "(OK, I need a few seconds to transfer)(.*)(to)(.*)";
		Pattern pat = Pattern.compile(regex);

		Matcher mat = pat.matcher(response);

		if (mat.find()) {
			String amountString = 0 + mat.group(2);
			double amount = 0;

			amountString = amountString.replaceAll("[^0-9\\.]", "");

			try {
				amount = Double.parseDouble(amountString);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Invalid amount.");
				return;
			}
			String email = mat.group(4).trim();
			email = convertVoiceToEmail(email);
			String emailRegex = "^([a-z0-9]+[\\-\\.]?[\\w]+){1,255}@([a-z0-9]+[\\-]{0,4}[a-z0-9]+){1,255}([\\.][a-z]{2,13}){1,2}$";
			Pattern emailPat = Pattern.compile(emailRegex);
			Matcher emailMat = emailPat.matcher(email);

			if (emailMat.find()) {
				if (email.equals(loggedInUser.getEmail())) {
					JOptionPane.showMessageDialog(null,
							"It's illogical to send money to your self, because then you would have the same balance.",
							"There's an issue", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (!confirmTransfer(amount, email))
					return;

				Transaction transaction = new Transaction();

				transaction.setSender(loggedInUser.getId());
				transaction.setReceiver(email);
				transaction.setAmount(amount);
				transaction.setCardType(CardType.CREDIT);
				transaction.setDescription(
						loggedInUser.getFirstName() + " sent $" + amount + " to user with email: " + email);
				transaction.setTransactionType(TransactionType.TRANSFER);

				try {
					Request clientRequest = new Request("transaction_transfer", transaction);
					client.connect();
					client.send(clientRequest);

					Response clientResponse = (Response) client.readResponse();

					if (clientResponse.isSuccess()) {
						String message = "You sent $" + amount + " user with email: " + email + " successfully";
						JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);

						try {
							speak(message);
						} catch (InterruptedException | ExecutionException e) {
							logger.error("The assistant was unable to produce voice output.");
						}
					} else {
						JOptionPane.showMessageDialog(null, clientResponse.getMessage(), "There was an issue",
								JOptionPane.ERROR_MESSAGE);

						try {
							speak(clientResponse.getMessage());
						} catch (InterruptedException | ExecutionException e) {
							logger.error("The assistant was unable to produce voice output.");
						}
					}

					client.send(new Request("EXIT"));
					client.closeConnection();
				} catch (IOException | ClassNotFoundException e) {
					logger.error("Unable to send or receive transaction transfer request/response.");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Invalid email address '" + email + "'");
				return;
			}
		}
	}

	private void handleInquiry(String response) {
		double balance = getBalance();
		String message = "Your balance is $" + balance;
		JOptionPane.showMessageDialog(null, message);
		try {
			speak(message);
		} catch (InterruptedException | ExecutionException e) {
			logger.error("The assistant was unable to produce voice output.");
		}
	}

	private void handleDeposit(String response, String request) {
		String amountString = request.replaceAll("[^0-9\\.]", "");
		double amount = 0;

		try {
			amount = Double.parseDouble(amountString);
		} catch (NumberFormatException e) {
			try {
				String amtString = JOptionPane.showInputDialog(null,
						"You either specified or did not specify a valid amount", "Invalid amount",
						JOptionPane.WARNING_MESSAGE);
				amount = Double.parseDouble(amtString);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "You either specified or did not specify a valid amount",
						"Invalid amount", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		Transaction transaction = new Transaction();
		transaction.setSender(loggedInUser.getId());
		transaction.setTransactionType(TransactionType.DEPOSIT);
		transaction.setCardType(CardType.DEBIT);
		transaction.setAmount(amount);
		transaction.setDescription("You deposited $" + amount + " to your account.");

		try {
			client.connect();
			client.send(new Request("transaction_deposit", transaction));

			Response clientResponse = (Response) client.readResponse();

			if (clientResponse.isSuccess()) {
				String message = "You just deposited $" + amount + " to your account.";
				JOptionPane.showMessageDialog(null, message);

				try {
					speak(message);
				} catch (InterruptedException | ExecutionException e) {
					logger.error("The assistant was unable to produce voice output.");
				}
			} else {
				JOptionPane.showMessageDialog(null, clientResponse.getMessage());

				try {
					speak(clientResponse.getMessage());
				} catch (InterruptedException | ExecutionException e) {
					logger.error("The assistant was unable to produce voice output.");
				}
			}

			client.send(new Request("EXIT"));
			client.closeConnection();
		} catch (IOException | ClassNotFoundException e) {
			logger.error("Unable to send or receive transaction deposit request/response.");
		}
	}

	private void handleBillPayment(String response) {
		String regex = "(Sure, I'll find )(.*)( and pay them )(.*)";
		Pattern pat = Pattern.compile(regex);

		Matcher mat = pat.matcher(response);

		if (mat.find()) {
			String provider = mat.group(2).trim();
			String amountString = mat.group(4);
			double amount = 0;

			amountString = amountString.replaceAll("[^0-9\\.]", "");

			try {
				amount = Double.parseDouble(amountString);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Invalid amount.");
				return;
			}

			if (!confirmBillPayment(amount, provider))
				return;

			Transaction transaction = new Transaction();
			transaction.setSender(loggedInUser.getId());
			transaction.setReceiver(provider);
			transaction.setAmount(amount);
			transaction.setCardType(CardType.CREDIT);
			transaction.setTransactionType(TransactionType.BILL);
			transaction.setDescription("You payed a bill of $" + amount + " to " + provider);

			try {
				client.connect();
				client.send(new Request("transaction_bill", transaction));

				Response clientResponse = (Response) client.readResponse();

				if (clientResponse.isSuccess()) {
					String message = "You payed a bill of $" + amount + " to " + provider + " successfully.";
					JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);

					try {
						speak(message);
					} catch (InterruptedException | ExecutionException e) {
						logger.error("The assistant was unable to produce voice output.");
					}
				} else {
					JOptionPane.showMessageDialog(null, clientResponse.getMessage(), "There was an issue",
							JOptionPane.ERROR_MESSAGE);

					try {
						speak(clientResponse.getMessage());
					} catch (InterruptedException | ExecutionException e) {
						logger.error("The assistant was unable to produce voice output.");
					}
				}
			} catch (IOException | ClassNotFoundException e) {
				logger.error("Unable to send or receive bill payment request/response.");
			}
		}
	}

	public static double getBalance() {
		try {
			Client client = new Client();
			client.connect();
			client.send(new Request("transaction_inquiry"));

			Response clientResponse = (Response) client.readResponse();

			if (clientResponse.isSuccess()) {
				return (double) clientResponse.getData();

			} else {
				JOptionPane.showMessageDialog(null, clientResponse.getMessage());
			}
		} catch (IOException | ClassNotFoundException e) {
			LogManager.getLogger().error("Unable to send or receive transaction inquiry request/response.");
		}

		return 0;
	}

	public List<ChartVals> managerChartValues()
	{
		try {
			client.connect();
			client.send(new Request("values_for_chart"));

			Response response = (Response)client.readResponse();

			client.send(new Request("EXIT"));


			return (List<ChartVals>) response.getData();

		}catch(IOException e)
		{
			logger.error("Unable to send request for chart values ", e.getMessage());
		}catch(ClassCastException | ClassNotFoundException e)
		{
			logger.error("Unable to read response");
		}
		return null;

	}

	private boolean confirmTransfer(double amount, String email) {
		return JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null,
				"Are you sure you want to send $" + amount + " to " + email + "?", "Confirm your transaction",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean confirmBillPayment(double amount, String provider) {
		return JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null,
				"Are you sure you want to pay $" + amount + " to " + provider + "?", "Confirm payment",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public List<Transaction> getTransactions(User customer) {
		List<Transaction> cusTransactions = new ArrayList<Transaction>();

		try {
			// GETTING TRANSACTIONS FOR THIS SPECIFIC CUSTOMER
			client.connect();
			client.send(new Request("get_transactions_for_customer", customer));
			Response response = client.readResponse();
			cusTransactions = (List<Transaction>) response.getData();

		} catch (IOException | ClassNotFoundException | ClassCastException e) {
			logger.error("Unable to read or receive transactions for customer.");
		}
		return cusTransactions;
	}

	public List<Transaction> filter(String start, String end, int lowerVal, int higherVal, User customer) {
		List<Transaction> filTrans = new ArrayList<Transaction>();

		try {
			List<Transaction> temp = new ArrayList<Transaction>();
			temp = this.getTransactions(customer);

			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(start);
			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(end);

			for (Transaction t : temp) {
				if (((t.getDate().after(date1) && t.getDate().before(date2))
						|| (t.getDate().equals(date1) || t.getDate().equals(date2)))
						&& (t.getAmount() >= lowerVal && t.getAmount() <= higherVal)) {
					filTrans.add(t);
				}
			}

			if (filTrans.isEmpty()) {

				if (temp.isEmpty()) {
					JOptionPane.showMessageDialog(null, "No Transactions Found. :(");
				} else {
					JOptionPane.showMessageDialog(null, "No Transactions Match The Search Criteria. :("
							+ "\nPlease Try Again With Different Dates and/or Values.\n\nNow Filtering Most Recent Transactions...");
					filTrans = temp;
				}
			}

			return filTrans;
		} catch (ClassCastException | ParseException e) {
			logger.error("Unable to retrieve customer's transactions.");
		}
		return filTrans;
	}

	public boolean storeMessage(String message, String email, String queryType) {
		boolean success = false;

		try {

			List<String> cusMessage = new ArrayList<String>();
			cusMessage.add(email);
			cusMessage.add(message);
			cusMessage.add(queryType);

			client.connect();
			client.send(new Request("store_message", cusMessage));
			Response response = client.readResponse();

			success = response.isSuccess();

			client.send(new Request("EXIT"));
			return success;
		} catch (IOException | ClassNotFoundException | ClassCastException e) {
			logger.error("Unable to send message to customer representative.");
		}

		return success;
	}
	
	public String convertVoiceToEmail(String input) {
		//Regex here
		String regex = "(.*)at(.*)\\.(.*)";
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(input);
		
		//email
		StringBuilder email = new StringBuilder("");
		
		if(mat.find()) {
			//append username of email
			email.append(mat.group(1).toLowerCase().trim().replaceAll("[\\s]", ""));
			
			//append @ sign
			email.append('@');
			
			//append domain
			email.append(mat.group(2).toLowerCase().trim().replaceAll("[\\s]", ""));
			
			//append extension
			email.append('.' + mat.group(3).toLowerCase().trim().replaceAll("[\\s]", ""));
			
			return email.toString();
			
		}else {
			return input;
		}
	}
	
	public void setMute(boolean val) {
		mute = val;
	}
	
	public boolean getMute() {
		return mute;
	}

}
