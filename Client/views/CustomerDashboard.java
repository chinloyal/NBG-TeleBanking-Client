package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import ch.qos.logback.classic.Logger;
import controllers.AuthController;
import controllers.CustomerController;
import controllers.TransactionController;
import models.Transaction;
import models.User;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.SwingConstants;
import java.awt.Font;

public class CustomerDashboard extends JFrame implements ActionListener {
	private static User customer = AuthController.getLoggedInUser();
	private JLabel cusPhoto;
	private JComboBox<String> transactionBox;
	private JButton btnGo;
	private JTextArea transactionList;
	private JComboBox<String> startMonth;
	private JComboBox<String> startDay;
	private JComboBox<String> startYear;
	private JComboBox<String> endMonth;
	private JComboBox<String> endDay;
	private JComboBox<String> endYear;
	private JComboBox<String> lowestVal;
	private JComboBox<String> highestVal;
	private JButton btnFilter;
	private JButton btnClrSearch;
	
	private JTable tableTransactions;
	
	JPanel tablePanel = new JPanel(new BorderLayout());
	private JPanel originalTablePanel = new JPanel();
	JPanel filteredPanel = new JPanel();

	// Table Heading (Column Names)
	String[] columns = { "ID", "Type", "Amount", "Description", "Debit/Credit", "Date" };

	public CustomerDashboard(User customer) {
		this.customer = customer;
		initView();
		configureListeners();
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Cannot set UI");
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				CustomerDashboard frame = new CustomerDashboard(customer);
				frame.setVisible(true);
			}
		});
	}

	public void initView() {
		setTitle("NBG TeleBanking - Customer Dashboard");
		setSize(1200, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		getContentPane().setLayout(new GridLayout(0, 2));

		// Top Half of Dashboard
		JPanel heading = new JPanel();
		heading.setLayout(new BorderLayout(0, 0));

		JLabel welcomelbl = new JLabel("Welcome to your Dashboard!");
		welcomelbl.setFont(new Font("SansSerif", Font.BOLD, 15));
		welcomelbl.setHorizontalAlignment(SwingConstants.CENTER);
		welcomelbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		heading.add(welcomelbl, BorderLayout.NORTH);

		System.out.println(customer.getPhoto().getName());

		cusPhoto = new JLabel(new ImageIcon(
				new ImageIcon(CustomerDashboard.class.getResource("/storage/uploads/" + customer.getPhoto().getName()))
						.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
		cusPhoto.setBorder(UIManager.getBorder("TitledBorder.border"));
		cusPhoto.setIconTextGap(0);
		cusPhoto.setVerticalAlignment(SwingConstants.TOP);

		heading.add(cusPhoto, BorderLayout.CENTER);

		JPanel cusInfo = new JPanel(new GridLayout(4, 1));
		cusInfo.setPreferredSize(new Dimension(230, 0));
		cusInfo.setBorder(UIManager.getBorder("TitledBorder.border"));

		heading.add(cusInfo, BorderLayout.EAST);
		String fullName = (customer.getFirstName() + " " + customer.getLastName()).toUpperCase();
		JLabel label = new JLabel("\tName: " + fullName);
		label.setPreferredSize(new Dimension(200, 16));
		label.setSize(new Dimension(20, 0));
		cusInfo.add(label);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel label_1 = new JLabel("\tAccount #: CU2018-" + customer.getId());
		cusInfo.add(label_1);

		JPanel cusAmt = new JPanel(new FlowLayout());
		cusAmt.add(new JLabel("<html>Your Current Account Balance is: <span style=\"color: green;\">$"
				+ TransactionController.getBalance() + "</span></html>"));
		heading.add(cusAmt, BorderLayout.SOUTH);
		
		getContentPane().add(heading);
		
		JPanel activity = new JPanel(new GridLayout(3, 1));

		// ----- Transactions - Make New Transaction, OR View List of Transactions
		JPanel transactions = new JPanel(new BorderLayout());

		// North Panel for Transactions
		JPanel northPanel = new JPanel(new GridLayout(3, 1));
		
		JPanel newTrans = new JPanel(new FlowLayout());
		newTrans.add(new JLabel("Make New Transaction:"));
		northPanel.add(newTrans);
		transactionBox = new JComboBox(new String[] { "Leave a Message", "Open Chat Client" });
		northPanel.add(transactionBox);
		btnGo = new JButton("Go!");
		northPanel.add(btnGo);

		transactions.add(northPanel, BorderLayout.NORTH);

		activity.add(transactions);
		
		activity.add(tablePanel);
		JPanel yourTrans = new JPanel(new FlowLayout());
		yourTrans.add(new JLabel("Your Transactions: "));
		tablePanel.add(yourTrans, BorderLayout.NORTH);

		// Displaying Recent Transactions
		TransactionController tran = new TransactionController();
		List<Transaction> cusTrans = tran.getTransactions(customer);

		tableTransactions = this.getTable(cusTrans, columns);
		JScrollPane tablePane = new JScrollPane(tableTransactions);
		originalTablePanel.add(tablePane);
		tablePanel.add(originalTablePanel, BorderLayout.CENTER);
		
		JPanel logoutPanel = new JPanel(new GridLayout(2,1));
		logoutPanel.add(new JLabel("You can logout of your dashboard at any time.\nJust click the button below. :)"));
		JButton logout = new JButton("Logout");
		logoutPanel.add(logout);
		
		activity.add(logoutPanel);
		
		getContentPane().add(activity);
		
		// ----- Filters
		JPanel filters = new JPanel(new GridLayout(5, 1));

		JPanel filterTrans = new JPanel(new FlowLayout());
		filterTrans.add(new JLabel("Filter Your Transactions:"));
		filters.add(filterTrans);

		JPanel Date = new JPanel(new GridLayout(2, 10));
		Date.add(new JLabel("From:"));
		startMonth = new JComboBox(
				new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" });
		Date.add(startMonth);
		startDay = new JComboBox(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
				"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
				"30", "31" });
		Date.add(startDay);
		startYear = new JComboBox(
				new String[] { "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018" });
		Date.add(startYear);
		Date.add(new JLabel(" "));
		Date.add(new JLabel("To:"));
		endMonth = new JComboBox(
				new String[] { "12", "11", "10", "09", "08", "07", "06", "05", "04", "03", "02", "01" });

		Date.add(endMonth);
		endDay = new JComboBox(new String[] { "31", "30", "29", "28", "27", "26", "25", "24", "23", "22", "21", "20",
				"19", "18", "17", "16", "15", "14", "13", "12", "11", "10", "09", "08", "07", "06", "05", "04", "03",
				"02", "01" });

		Date.add(endDay);
		endYear = new JComboBox(
				new String[] { "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010" });
		Date.add(endYear);
		Date.add(new JLabel(" "));

		filters.add(Date);

		filters.add(new JLabel("With Amounts Ranging:"));

		JPanel values = new JPanel(new GridLayout(2, 6));
		values.add(new JLabel("From: JMD"));
		lowestVal = new JComboBox(new String[] { "0", "100", "500", "1000", "2500", "5000", "10000", "25000", "50000",
				"100000", "250000", "500000", "1000000" });
		values.add(lowestVal);
		values.add(new JLabel(" "));
		values.add(new JLabel("To: JMD"));
		highestVal = new JComboBox(new String[] { "1000000", "500000", "250000", "100000", "50000", "25000", "10000",
				"5000", "2500", "1000", "500", "100", "0" });
		values.add(highestVal);
		values.add(new JLabel(" "));

		filters.add(values);

		JPanel search = new JPanel(new GridLayout(1, 2));
		btnFilter = new JButton("Filter Results!");
		search.add(btnFilter);
		btnClrSearch = new JButton("Clear Search");
		search.add(btnClrSearch);
		filters.add(search);

		filters.add(search);

		getContentPane().add(filters);

		setVisible(true);
	}

	public void configureListeners() {
		btnGo.addActionListener(this);
		btnFilter.addActionListener(this);
		btnClrSearch.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(btnGo)) {
			// PERFORM TRanSACTION
			String selection = (String) transactionBox.getSelectedItem();

			// LEAVE A MESSAGE FOR CSR
			if (selection.equals("Leave a Message")) {
				JOptionPane.showMessageDialog(null, "Leave a Message for our CSR!");
				QueryView query = new QueryView(customer, this);
				query.setVisible(true);

				// OPEN CHAT CLIENT
			} else if (selection.equals("Open Chat Client")) {
				JOptionPane.showMessageDialog(null, "LISA is Ready to Help You with your Transaction! :)");
				ChatClientView chatView = new ChatClientView(this);
				chatView.setVisible(true);
			}
		} else if (event.getSource().equals(btnFilter)) {
			// TIME TO FILTER TRANSACTIONS
			String start = startYear.getSelectedItem() + "-" + startMonth.getSelectedItem() + "-"
					+ startDay.getSelectedItem();
			String end = endYear.getSelectedItem() + "-" + endMonth.getSelectedItem() + "-" + endDay.getSelectedItem();

			int lowerVal = Integer.parseInt((String) lowestVal.getSelectedItem());
			int higherVal = Integer.parseInt((String) highestVal.getSelectedItem());

			TransactionController filter = new TransactionController();
			List<Transaction> filteredTrans = filter.filter(start, end, lowerVal, higherVal, customer);
			int length = filteredTrans.size();
		
			JOptionPane.showMessageDialog(null, "Filtering...\nReturned "+length+" record(s).");
			
			tableTransactions = this.getTable(filteredTrans, columns);
			
			filteredPanel = new JPanel();
			JScrollPane scroll = new JScrollPane(tableTransactions);
			filteredPanel.add(scroll);
			
			tablePanel.remove(originalTablePanel);
			tablePanel.add(filteredPanel, BorderLayout.CENTER);
			this.validate();
			
			// Display filtered transactions inside TransactionList Text Area
		} else if (event.getSource().equals(btnClrSearch)) {
			JOptionPane.showMessageDialog(null, "Filters Cleared!\n\nFilters Have Been Reset.");

			// SHOW ALL CUSTOMER TRANSACTIONS
			startMonth.setSelectedItem("01");
			startDay.setSelectedItem("01");
			startYear.setSelectedItem("2010");
			endMonth.setSelectedItem("12");
			endDay.setSelectedItem("31");
			endYear.setSelectedItem("2018");

			lowestVal.setSelectedItem("0");
			highestVal.setSelectedItem("1000000");
		}
	}
	
	public JTable getTable(List<Transaction> data, String[] cols) {
		
		JTable table = null;
		
		int numOfRows = data.size()+10;
		int numOfCols =  columns.length;
		
		Object[][] trans = new Object[numOfRows][numOfCols];	
		int length = data.size();
		int i, n;
		
		for (i = 0; i < length; i++) { // USE "i" AS THE NUMBER OF TRANSACTIONS IN THE LIST
			for (n = 0; n < 6; n++) { // USE "n" AS THE NUMBER OF FIELDS IN EACH TRANSACTION

				switch (n) {
				case 0:
					trans[i][n] = data.get(i).getId(); // FIRST COLUMN - ID
					break;
				case 1:
					trans[i][n] = data.get(i).getTransactionType(); // SECOND COLUMN - TYPE
					break;
				case 2:
					trans[i][n] = data.get(i).getAmount(); // THIRD COLUMN - AMOUNT
					break;
				case 3:
					trans[i][n] = data.get(i).getDescription(); // FOURTH COLUMN - Description
					break;
				case 4:
					trans[i][n] = data.get(i).getCardType(); // FIFTH COLUMN - Debit/Credit
					break;
				case 5:
					trans[i][n] = data.get(i).getDate(); // SIXTH COLUMN - Date
					break;
				}
			}
		}
		
		table = new JTable(trans,cols);
		
		return table;	
	}
}
