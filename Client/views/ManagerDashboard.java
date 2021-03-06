package views;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import connection.Client;
import controllers.AuthController;
import controllers.TransactionController;
import models.ChartVals;



@SuppressWarnings("serial")
public class ManagerDashboard extends JFrame implements ActionListener {
	private JButton btnLogout;
	private JLabel label;
	private TransactionController trans = new TransactionController();
	public ManagerDashboard()
	{
		super("NBG TeleBanking - Manager Dashboard");
		
		  
		   initView();
	}
	
	public CategoryDataset createDatase() 
	{
		List<ChartVals> chartValues = trans.managerChartValues();
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(ChartVals val : chartValues) {
			dataset.addValue(val.getTotal(), val.getCardType(), val.getTransactionType());
		}
		
		return dataset; 
	}
		
	private void initView()
	{
		
		this.setLayout(new BorderLayout());
		
		JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		pnlTop.add( label = new JLabel ("MANAGER DASHBOARD"));
		label.setFont(new Font("Serif", Font.PLAIN, 30) );
		
		this.add(pnlTop, BorderLayout.NORTH);
		

		JFreeChart barChart = ChartFactory.createBarChart(
				"TRANSACTION CHART", 
				"Transaction Type", "Total Amount", 
				createDatase(),PlotOrientation.VERTICAL, 
				true, true, false);


		ChartPanel BarChart = new ChartPanel(barChart);
		ChartPanel chartPanel = new ChartPanel( barChart );        
		chartPanel.setPreferredSize(new java.awt.Dimension( 50 , 30 ) );        
		add(BarChart, BorderLayout.CENTER);




		JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnLogout = new JButton("Logout");

		pnlFooter.add(btnLogout);
		btnLogout.setPreferredSize( new java.awt.Dimension(200, 30) );
		this.add(pnlFooter, BorderLayout.SOUTH);
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
			AuthController.logout();
			SwingUtilities.invokeLater(()->{
				Login login = new Login();
				login.setVisible(true);
				login.repaint();
				login.revalidate();
			});
			
			dispose();
		}
	}
	
	
	
	public static void main(String[] args)
	{
		new ManagerDashboard();
	}
	
}
