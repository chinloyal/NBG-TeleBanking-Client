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
import javax.swing.UIManager;

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

	public CustomerDashboard(User customer) {
		this.customer = customer;
		initView();
		configureListeners();
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {  
			JOptionPane.showMessageDialog(null,"Cannot set UI");
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
		setSize(900, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		getContentPane().setLayout(new GridLayout(0, 2));

		// Top Half of Dashboard
		JPanel heading = new JPanel();
		heading.setLayout(new BorderLayout(0, 0));
		

		getContentPane().add(heading);
		JLabel welcomelbl = new JLabel("Welcome to your Dashboard!");
		welcomelbl.setFont(new Font("SansSerif", Font.BOLD, 15));
		welcomelbl.setHorizontalAlignment(SwingConstants.CENTER);
		welcomelbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		heading.add(welcomelbl, BorderLayout.NORTH);
		
		cusPhoto = new JLabel(new ImageIcon(new ImageIcon(CustomerDashboard.class.getResource("/storage/uploads/" + customer.getPhoto().getName())).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
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
		heading.add(cusAmt, BorderLayout.SOUTH);
		cusAmt.add(new JLabel("<html>Your Current Account Balance is: <span style=\"color: green;\">$" + TransactionController.getBalance() + "</span></html>"));

		// ---------- Bottom Half of Dashboard
		JPanel activity = new JPanel(new GridLayout(2, 1));

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
		getContentPane().add(activity);
		
		// Center Panel for Transactions
		JPanel centerPanel = new JPanel(new BorderLayout());
		activity.add(centerPanel);
		JPanel yourTrans = new JPanel(new FlowLayout());
		yourTrans.add(new JLabel("Your Transactions: "));
		centerPanel.add(yourTrans, BorderLayout.NORTH);
		transactionList = new JTextArea("ID | Type | Amount | Description | Debit/Credit");
		transactionList.setSize(new Dimension(0, 300));
		transactionList.setPreferredSize(new Dimension(257, 300));
		transactionList.setRows(300);
		transactionList.setBounds(100, 300, 120, 30);
		transactionList.setLineWrap(true);
		transactionList.setColumns(20);
		centerPanel.add(transactionList, BorderLayout.CENTER);

		// ----- Filters
		JPanel filters = new JPanel(new GridLayout(5, 1));

		JPanel filterTrans = new JPanel(new FlowLayout());
		filterTrans.add(new JLabel("Filter Your Transactions:"));
		filters.add(filterTrans);

		JPanel Date = new JPanel(new GridLayout(2, 10));
		Date.add(new JLabel("From:"));
		startMonth = new JComboBox(
				new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" });
		Date.add(startMonth);
		startDay = new JComboBox(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31" });
		Date.add(startDay);
		startYear = new JComboBox(
				new String[] { "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018" });
		Date.add(startYear);
		Date.add(new JLabel(" "));
		Date.add(new JLabel("To:"));
		endMonth = new JComboBox(
				new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov" });

		Date.add(endMonth);
		endDay = new JComboBox(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
				"12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
				"29", "30", "31" });

		Date.add(endDay);
		endYear = new JComboBox(
				new String[] {"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018" });
		Date.add(endYear);
		Date.add(new JLabel(" "));

		filters.add(Date);

		filters.add(new JLabel("With Amounts Ranging:"));

		JPanel values = new JPanel(new GridLayout(2, 6));
		values.add(new JLabel("From: JMD"));
		lowestVal = new JComboBox(new String[] { "0", "100", "500", "1,000", "2,500", "5,000", "10,000",
				"25,000", "50,000", "100,000", "250,000", "500,000", "1,000,000" });
		values.add(lowestVal);
		values.add(new JLabel(" "));
		values.add(new JLabel("To: JMD"));
		highestVal = new JComboBox(new String[] { "0", "100", "500", "1,000", "2,500", "5,000", "10,000",
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
			String selection = (String) transactionBox.getSelectedItem();
			if (selection.equals("Leave a Message")) {
				JOptionPane.showMessageDialog(null, "Leave a Message for our CSR!");
				QueryView query = new QueryView(customer, this);
				query.setVisible(true);
			} else if (selection.equals("Open Chat Client")) {
				JOptionPane.showMessageDialog(null, "LISA is Ready to Help You with your Transaction! :)");
				ChatClientView chatView = new ChatClientView(this);
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
