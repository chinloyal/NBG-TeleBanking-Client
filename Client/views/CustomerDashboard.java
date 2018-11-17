package views;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controllers.AuthController;
import controllers.TransactionController;
import models.Transaction;
import models.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class CustomerDashboard extends JFrame implements ActionListener {

	private static User customer = AuthController.getLoggedInUser();
	private JPanel contentPane;
	private JLabel cusPhoto;
	private JLabel lblAccount;
	private JLabel lblBalance;
	private JPanel panel_1;
	private JComboBox<String> startMonth;
	private JComboBox<String> startDay;
	private JComboBox<String> startYear;
	private JComboBox<String> endMonth;
	private JComboBox<String> endDay;
	private JComboBox<String> endYear;
	private JComboBox<String> lowestVal;
	private JComboBox<String> highestVal;
	private JButton logout;
	private JComboBox<String> transactionBox;
	private JSplitPane splitPane;
	private JButton btnFilter;
	private JButton btnClrSearch;
	private JButton btnGo;
	private JTable table;
	private JLabel lblName;
	
	private JPanel originalTablePanel = new JPanel();
	private JPanel filteredPanel = new JPanel();
	
	// Table Heading (Column Names)
	private String[] columns = { "ID", "Type", "Amount", "Description", "Debit/Credit", "Date" };
	private JMenuBar menuBar;
	private JMenu mnHelp;
	private JMenuItem mntmViewUserManual;
	
	private static Logger logger = LogManager.getLogger(CustomerDashboard.class);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Cannot set UI");
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerDashboard frame = new CustomerDashboard(customer);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CustomerDashboard(User customer) {
		initView(customer);
		configureListeners();
	}
	
	public void initView(User customer) {
		setTitle("NBG TeleBanking - Customer Dashboard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 994, 446);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmViewUserManual = new JMenuItem("View User Manual (F1)");
		mnHelp.add(mntmViewUserManual);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[183.00][274.00,left][470.00,grow]", "[grow][grow][][][][]"));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 0 0,grow");
		
		File file = new File("Client/storage/uploads/" + customer.getPhoto().getName());
		URL url = null;
		
		try {
			url = new URL("file:/" + file.getAbsolutePath());
		} catch (MalformedURLException e2) {
			logger.error("Unable to for URL for image.");
		}
//		System.out.println(CustomerDashboard.class.getResource("/storage/uploads/" + customer.getPhoto().getName()));
		
		cusPhoto = new JLabel(
			new ImageIcon(
				new ImageIcon(
						url
				).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)
			)
		);
		panel.add(cusPhoto);
		
		String fullName = (customer.getFirstName() + " " + customer.getLastName()).toUpperCase();
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, "cell 1 0,grow");
		panel_1.setLayout(new MigLayout("", "[74.00,grow][74.00,fill][74.00,grow]", "[][][][][][][][][]"));
		
		JLabel lblF = new JLabel("Filter Your Transactions");
		lblF.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblF.setHorizontalTextPosition(SwingConstants.CENTER);
		lblF.setFont(new Font("Segoe UI Black", Font.BOLD, 12));
		lblF.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblF, "cell 0 0 3 1");
		
		JLabel lblFrom = new JLabel("Date From:");
		panel_1.add(lblFrom, "cell 0 1");
		
		startMonth = new JComboBox<String>();
		startMonth.setModel(new DefaultComboBoxModel<>(new String[] {
				"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
		}));
		
		panel_1.add(startMonth, "cell 0 2,growx");
		
		startDay = new JComboBox<String>();
		startDay.setModel(new DefaultComboBoxModel<>(new String[] {
				"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
				"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", 
				"25", "26", "27", "28", "29", "30", "31"
		}));
		
		
		panel_1.add(startDay, "cell 1 2,growx");
		
		startYear = new JComboBox<String>();
		startYear.setModel(new DefaultComboBoxModel<>(new String[] {
				"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018" 
		}));
		
		panel_1.add(startYear, "cell 2 2,growx");
		
		JLabel lblTo = new JLabel("Date To:");
		panel_1.add(lblTo, "cell 0 3 2 1");
		
		endMonth = new JComboBox<>();
		endMonth.setModel(new DefaultComboBoxModel<>(new String[] {
				"12", "11", "10", "09", "08", "07", "06", "05", "04", "03", "02", "01"
		}));
		
		panel_1.add(endMonth, "cell 0 4,growx");
		
		endDay = new JComboBox<>();
		
		endDay.setModel(new DefaultComboBoxModel<>(new String[] {
				"31", "30", "29", "28", "27", "26", "25", "24", "23", "22", "21", "20",
				"19", "18", "17", "16", "15", "14", "13", "12", "11", "10", "09", "08", 
				"07", "06", "05", "04", "03", "02", "01"
		}));
		panel_1.add(endDay, "cell 1 4,growx");
		
		endYear = new JComboBox<>();
		endYear.setModel(new DefaultComboBoxModel<>(new String[] {
				"2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010"
		}));
		
		panel_1.add(endYear, "cell 2 4,growx");
		
		JLabel lblAmountFrom = new JLabel("Amount From:");
		panel_1.add(lblAmountFrom, "cell 0 5");
		
		lowestVal = new JComboBox<>();
		lowestVal.setModel(new DefaultComboBoxModel<>(new String[] {
				"0", "100", "500", "1000", "2500", "5000", "10000", "25000", "50000", "100000", "250000", "500000", "1000000"
		}));
		
		panel_1.add(lowestVal, "cell 0 6,growx");
		
		JLabel lblAmountTo = new JLabel("Amount To:");
		panel_1.add(lblAmountTo, "cell 0 7");
		
		highestVal = new JComboBox<>();
		highestVal.setModel(new DefaultComboBoxModel<>(new String[] {
				"1000000", "500000", "250000", "100000", "50000", "25000", "10000",
				"5000", "2500", "1000", "500", "100", "0"
		}));
		
		panel_1.add(highestVal, "cell 0 8,growx");
		
		// Displaying Recent Transactions
		TransactionController tran = new TransactionController();
		List<Transaction> cusTrans = tran.getTransactions(customer);
		
		table = getTable(cusTrans, columns);
		JScrollPane tablePane = new JScrollPane(table);
		originalTablePanel.add(tablePane);
		contentPane.add(originalTablePanel, "cell 2 0 1 6,grow");
		
		lblName = new JLabel("Name: " + fullName);
		contentPane.add(lblName, "cell 0 1");
		
		splitPane = new JSplitPane();
		contentPane.add(splitPane, "cell 1 1");
		
		btnFilter = new JButton("Filter");
		splitPane.setLeftComponent(btnFilter);
		
		btnClrSearch = new JButton("Clear Search");
		splitPane.setRightComponent(btnClrSearch);
		
		lblAccount = new JLabel("Account #: CU2018-" + customer.getId());
		contentPane.add(lblAccount, "cell 0 2");
		
		JLabel lblMakeATransaction = new JLabel("Open a Dialog");
		lblMakeATransaction.setFont(new Font("Segoe UI Black", Font.BOLD, 12));
		contentPane.add(lblMakeATransaction, "cell 1 3");
		
		lblBalance = new JLabel("<html>Your Balance is: <span style=\"color: green;\">$"
				+ TransactionController.getBalance() + "</span></html>");
		contentPane.add(lblBalance, "cell 0 4");
		
		transactionBox = new JComboBox<>();
		transactionBox.setModel(new DefaultComboBoxModel<>(new String[] {
				"Leave a Message", "Open Chat Client"
		}));
		
		contentPane.add(transactionBox, "cell 1 4,growx");
		
		logout = new JButton("Logout");
		contentPane.add(logout, "cell 0 5");
		
		btnGo = new JButton("Go");
		contentPane.add(btnGo, "cell 1 5");
	}
	
	public void configureListeners() {
		btnGo.addActionListener(this);
		btnFilter.addActionListener(this);
		btnClrSearch.addActionListener(this);
		logout.addActionListener(this);
		mntmViewUserManual.addActionListener(this);
	}
	
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
				JOptionPane.showMessageDialog(null, "Our Assistant is Ready to Help You with your Transaction! :)");
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
			
			table = this.getTable(filteredTrans, columns);
			JScrollPane scroll = new JScrollPane(table);
			
			filteredPanel.add(scroll);
			
			contentPane.remove(originalTablePanel);
			contentPane.add(filteredPanel, "cell 2 0 1 6,grow");
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
		}else if(event.getSource().equals(logout)) {
			if(AuthController.logout()) {
				dispose();

				SwingUtilities.invokeLater(()->{
					Login login = new Login();

					login.setVisible(true);
					login.repaint();
					login.revalidate();
				});
			
			}else {
				JOptionPane.showMessageDialog(null, "Unable to logout", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}else if(event.getSource().equals(mntmViewUserManual)) {
			//Open user manual
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
