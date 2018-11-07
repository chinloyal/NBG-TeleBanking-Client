package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controllers.CustomerController;
import models.User;

public class CustomerDashboard extends JFrame implements ActionListener{
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
	private JButton btnSearch;
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
		setSize(700,700 );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		setLayout(new GridLayout(2,1));
		
		//Top Half of Dashboard
		JPanel heading = new JPanel (new BorderLayout());
		
		JPanel welcome = new JPanel(new FlowLayout());
		welcome.add(new JLabel("Welcome to your Dashboard!"));
		heading.add(welcome,BorderLayout.NORTH);
	
		CustomerController custControl = new CustomerController(customer);
		String photo = custControl.getPhotoFromDatabase();
		
		cusPhoto = new JLabel((new ImageIcon(CustomerDashboard.class.getResource("/storage/uploads/"+photo).getFile())));
		heading.add(cusPhoto, BorderLayout.WEST);
		
		JPanel cusInfo = new JPanel(new GridLayout(4,1));
		cusInfo.add(new JLabel("Name: "));
		cusInfo.add(new JLabel(customer.getFirstName()+" "+customer.getLastName()));
		cusInfo.add(new JLabel("Account #: "));
		cusInfo.add(new JLabel("CU2018"+customer.getId()));
		heading.add(cusInfo,BorderLayout.EAST);
		
		JPanel cusAmt = new JPanel(new FlowLayout());
		cusAmt.add(new JLabel("Your Current Account Balance is: <Insert Customer Account Balance>"));
		heading.add(cusAmt, BorderLayout.SOUTH);
		
		this.add(heading);
		
		
		// ---------- Bottom Half of Dashboard
		JPanel activity = new JPanel(new GridLayout(1,2));
		
		// ----- Transactions - Make New Transaction, OR View List of Transactions
		JPanel transactions = new JPanel(new BorderLayout());
		
		// North Panel for Transactions
		JPanel northPanel = new JPanel(new GridLayout(3,1));
		JPanel newTrans = new JPanel(new FlowLayout());
		newTrans.add(new JLabel("Make New Transaction:"));
		northPanel.add(newTrans);
		transactionBox = new JComboBox<String>(new String[]{"Leave a Message","Open Chat Client"});
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
		centerPanel.add(transactionList, BorderLayout.CENTER);
		
		transactions.add(centerPanel, BorderLayout.CENTER);
		
		activity.add(transactions);
		this.add(activity);
		
		// ----- Filter Transactions
		JPanel filters = new JPanel(new GridLayout(7,1));
		
		JPanel filterTrans = new JPanel (new FlowLayout());
		filterTrans.add(new JLabel("Filter Your Transactions:"));
		filters.add(filterTrans);
		
		JPanel startDate = new JPanel(new GridLayout(1,4));
		startDate.add(new JLabel("From:"));
		startMonth = new JComboBox<String>(new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"});
		startDate.add(startMonth);
		startDay = new JComboBox<String>(new String[]{"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"});
		startDate.add(startDay);
		startYear = new JComboBox<String>(new String[]{"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018"});
		startDate.add(startYear);
		filters.add(startDate);
		
		JPanel endDate = new JPanel(new GridLayout(1,4));
		endDate.add(new JLabel("To:"));
		endMonth = new JComboBox<String>(new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"});
		endDate.add(endMonth);
		endDay = new JComboBox<String>(new String[]{"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"});
		endDate.add(endDay);
		endYear = new JComboBox<String>(new String[]{"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018"});
		endDate.add(endYear);
		filters.add(endDate);
		
		filters.add(new JLabel("With Amounts Ranging:"));
		
		JPanel least = new JPanel(new GridLayout(1,2));
		least.add(new JLabel("From:\tJMD"));
		lowestVal = new JComboBox<String>(new String[]{"0","100","500","1,000","2,500","5,000","10,000","25,000","50,000"});
		least.add(lowestVal);
		filters.add(least);
		
		JPanel most = new JPanel(new GridLayout(1,2));
		most.add(new JLabel("To:\tJMD"));
		highestVal = new JComboBox<String>(new String[]{"0","100","500","1,000","2,500","5,000","10,000","25,000","50,000"});
		most.add(highestVal);
		filters.add(most);
		
		JPanel search = new JPanel(new GridLayout(1,2));
		btnSearch = new JButton("Filter Results!");
		search.add(btnSearch);
		btnClrSearch = new JButton("Clear Search");
		search.add(btnClrSearch);
		filters.add(search);
		
		activity.add(filters);
		this.add(activity);
		
	}
	
	public void configureListeners() {
		btnGo.addActionListener(this);
		btnSearch.addActionListener(this);
		btnClrSearch.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(btnGo)) {
			String selection = (String)transactionBox.getSelectedItem();
			if (selection.equals("Leave a Message")) {
				JOptionPane.showMessageDialog(null, "Leave a Message for our CSR!");
				//Leave a Message Window appears here
			}else if (selection.equals("Open Chat Client")) {
				JOptionPane.showMessageDialog(null, "Lisa is Ready to Help You with your Transaction! :)");
				//Chat Client appears here!
				setVisible(false);
				new ChatClientView();
			}
		}else if (event.getSource().equals(btnSearch)) {
			
		}else if (event.getSource().equals(btnClrSearch)) {
			
		}
	}
}


