package views;

import java.awt.BorderLayout; 
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controllers.CustomerController;
import controllers.TransactionController;
import models.Transaction;
import models.User;

public class CustomerDashboard extends JFrame implements ActionListener {
	private static User customer;
	private JLabel cusPhoto;
	private JComboBox transactionBox;
	private JButton btnGo;
	private JTextArea transactionList;
	private JComboBox startMonth;
	private JComboBox startDay;
	private JComboBox startYear;
	private JComboBox endMonth;
	private JComboBox endDay;
	private JComboBox endYear;
	private JComboBox lowestVal;
	private JComboBox highestVal;
	private JButton btnFilter;
	private JButton btnClrSearch;

	public CustomerDashboard(Object customer) {
		this.customer = (User) customer;
		initView();
		configureListeners();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				CustomerDashboard frame = new CustomerDashboard(customer);
				frame.setVisible(true);
			}
		});
	}

	public void initView() {
		setTitle("NBG TeleBanking - Customer Dashboard");
		setSize(900, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		setLayout(new GridLayout(3, 1));

		// Top Half of Dashboard
		JPanel heading = new JPanel(new BorderLayout());

		JPanel welcome = new JPanel(new FlowLayout());
		welcome.add(new JLabel("Welcome to your Dashboard!"));
		heading.add(welcome, BorderLayout.NORTH);

		CustomerController custControl = new CustomerController(customer);
		String photo = custControl.getPhotoFromDatabase();

		cusPhoto = new JLabel(
				(new ImageIcon(CustomerDashboard.class.getResource("/storage/uploads/" + photo).getFile())));
		heading.add(cusPhoto, BorderLayout.WEST);

		JPanel cusInfo = new JPanel(new GridLayout(4, 1));
		
		JPanel cusInfo1 = new JPanel(new FlowLayout());
		cusInfo1.add(new JLabel("\tName: "));
		cusInfo.add(cusInfo1);
		
		JPanel cusInfo2 = new JPanel(new FlowLayout());
		String fullName = (customer.getFirstName() + " " + customer.getLastName()).toUpperCase();
		cusInfo2.add(new JLabel("\t" +fullName));
		cusInfo.add(cusInfo2);
		
		JPanel cusInfo3 = new JPanel(new FlowLayout());
		cusInfo3.add(new JLabel("\tAccount #: "));
		cusInfo.add(cusInfo3);
		
		JPanel cusInfo4 = new JPanel(new FlowLayout());
		cusInfo4.add(new JLabel("\tCU2018" + customer.getId()));
		cusInfo.add(cusInfo4);
		
		heading.add(cusInfo, BorderLayout.CENTER);

		JPanel cusAmt = new JPanel(new FlowLayout());
		cusAmt.add(new JLabel("Your Current Account Balance is: <Insert Customer Account Balance>"));
		heading.add(cusAmt, BorderLayout.SOUTH);

		this.add(heading);

		// ---------- Bottom Half of Dashboard
		JPanel activity = new JPanel(new GridLayout(2, 1));

		// ----- Transactions - Make New Transaction, OR View List of Transactions
		JPanel transactions = new JPanel(new BorderLayout());

		// North Panel for Transactions
		JPanel northPanel = new JPanel(new GridLayout(3, 1));
		JPanel newTrans = new JPanel(new FlowLayout());
		newTrans.add(new JLabel("Make New Transaction:"));
		northPanel.add(newTrans);
		transactionBox = new JComboBox<String>(new String[] { "Leave a Message", "Open Chat Client" });
		northPanel.add(transactionBox);
		btnGo = new JButton("Go!");
		northPanel.add(btnGo);

		transactions.add(northPanel, BorderLayout.NORTH);

		// Center Panel for Transactions
		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel yourTrans = new JPanel(new FlowLayout());
		yourTrans.add(new JLabel("Your Transactions: "));
		centerPanel.add(yourTrans, BorderLayout.NORTH);
		transactionList = new JTextArea("ID | Type | Amount | Description | Debit/Credit");
		transactionList.setBounds(100, 300, 120, 30);
		centerPanel.add(transactionList, BorderLayout.CENTER);

		transactions.add(centerPanel, BorderLayout.CENTER);

		activity.add(transactions);
		this.add(activity);

		// ----- Filters
		JPanel filters = new JPanel(new GridLayout(5, 1));

		JPanel filterTrans = new JPanel(new FlowLayout());
		filterTrans.add(new JLabel("Filter Your Transactions:"));
		filters.add(filterTrans);

		JPanel Date = new JPanel(new GridLayout(1, 10));
		Date.add(new JLabel("From:"));
		startMonth = new JComboBox<String>(
				new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" });
		Date.add(startMonth);
		startDay = new JComboBox<String>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31" });
		Date.add(startDay);
		startYear = new JComboBox<String>(
				new String[] { "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018" });
		Date.add(startYear);
		Date.add(new JLabel(" "));
		Date.add(new JLabel("To:"));
		endMonth = new JComboBox<String>(
				new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov" });

		Date.add(endMonth);
		endDay = new JComboBox<String>(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
				"12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
				"29", "30", "31" });

		Date.add(endDay);
		endYear = new JComboBox<String>(
				new String[] {"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018" });
		Date.add(endYear);
		Date.add(new JLabel(" "));

		filters.add(Date);

		filters.add(new JLabel("With Amounts Ranging:"));

		JPanel values = new JPanel(new GridLayout(1, 6));
		values.add(new JLabel("From: JMD"));
		lowestVal = new JComboBox<String>(new String[] { "0", "100", "500", "1,000", "2,500", "5,000", "10,000",
				"25,000", "50,000", "100,000", "250,000", "500,000", "1,000,000" });
		values.add(lowestVal);
		values.add(new JLabel(" "));
		values.add(new JLabel("To: JMD"));
		highestVal = new JComboBox<String>(new String[] { "0", "100", "500", "1,000", "2,500", "5,000", "10,000",
				"25,000", "50,000", "100,000", "250,000", "500,000", "1,000,000" });
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

		this.add(filters);
	}

	public void configureListeners() {
		btnGo.addActionListener(this);
		btnFilter.addActionListener(this);
		btnClrSearch.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(btnGo)) {
			String selection = (String) transactionBox.getSelectedItem();
			if (selection.equals("Leave a Message")) {
				JOptionPane.showMessageDialog(null, "Leave a Message for our CSR!");
				// 'Leave a Message' Window appears here
			} else if (selection.equals("Open Chat Client")) {
				JOptionPane.showMessageDialog(null, "Lisa is Ready to Help You with your Transaction! :)");
				setVisible(false);
				ChatClientView chatView = new ChatClientView();
				chatView.setVisible(true);
			}
		} else if (event.getSource().equals(btnFilter)) {
			String[] start = {
					(String) startMonth.getSelectedItem(), 
					(String) startDay.getSelectedItem(),
					(String) startYear.getSelectedItem()
			};
			String[] end = {
					(String) endMonth.getSelectedItem(), 
					(String) endDay.getSelectedItem(),
					(String) endYear.getSelectedItem()
			};
			
			TransactionController filter = new TransactionController();
			List<Transaction> filteredTransactions = filter.filter(start, end);
			
			//Display filtered transactions inside TransactionList Text Area
			
		} else if (event.getSource().equals(btnClrSearch)) {
			// Set default values for fields
		}
	}
}
