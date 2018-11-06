package views;


import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;



@SuppressWarnings("serial")
public class ManagerDashboard extends JFrame implements ActionListener {
	private JButton btnLogout;
	private JLabel label;
	
	public ManagerDashboard()
	{
		super("NBG TeleBanking - Manager Dashboard");
		
		  
		   initView();
	}
	
 CategoryDataset createDatase() // creating the values and attributes to generate the bar chart
		{
		   	final String AddFunds = "Addition of Funds";
		      final String TransferFunds = "Transfer of Funds";
		      final String BillPay = "Bill Payment";
		      final String Debit = "Debit";
		      final String Credit = "Credit";
		   
// where the double values are, write a function that will generate the total from the database and return the values there for each 
		      
		      final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		      dataset.addValue(1.0, Debit, BillPay);
		      dataset.addValue(2.0, Debit, TransferFunds);
		      dataset.addValue(3.0, Debit, AddFunds);
		      
		      
		      dataset.addValue(2.0, Credit, BillPay);
		      dataset.addValue(3.0, Credit, TransferFunds);
		      dataset.addValue(4.0, Credit, AddFunds);
		     return dataset; 
		}
		
	private void initView()
	{
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		pnlTop.add( label = new JLabel ("MANAGER DASHBOARD"));
		label.setFont(new Font("Serif", Font.PLAIN, 30) );
		
		this.add(pnlTop);
		

		JFreeChart barChart = ChartFactory.createBarChart(
		         "TRANSACTION CHART", 
		         "Transaction Type", "Total Amount", 
		         createDatase(),PlotOrientation.VERTICAL, 
		         true, true, false);
		         
		
			ChartPanel BarChart = new ChartPanel(barChart);
			ChartPanel chartPanel = new ChartPanel( barChart );        
		    chartPanel.setPreferredSize(new java.awt.Dimension( 50 , 30 ) );        
		    add(BarChart);
		
		      
		
		
		JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnLogout = new JButton("Logout");
		
		pnlFooter.add(btnLogout);
		btnLogout.setPreferredSize( new java.awt.Dimension(200, 30) );
		this.add(pnlFooter);
		btnLogout.addActionListener(this);

		pack();
		
		setSize(800, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		
		
	}
	
	

	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(btnLogout))
		{
			//Return to login page
			 JOptionPane.showMessageDialog(this, "Back to the Login Page");
			
		}
	}
	
	
	
	public static void main(String[] args)
	{
		new ManagerDashboard();
	}
	
}
