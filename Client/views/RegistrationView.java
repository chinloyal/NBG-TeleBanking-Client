package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jgoodies.forms.layout.FormLayout;
import com.fasterxml.classmate.util.ResolvedTypeCache.Key;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import connection.Client;
import controllers.AuthController;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

import models.User;
import models.Photo;

public class RegistrationView extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField txtFname;
	private JTextField txtLname;
	private JTextField txtEmail;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JRadioButton rdbtnCustomer;
	private JRadioButton rdbtnManager;
	private JPanel pnlFileUpload;
	private JButton btnOpenFileChooser;
	private JButton btnRegister;
	private final ButtonGroup rdbtnTypeGroup = new ButtonGroup();
	
	private Logger logger = LogManager.getLogger(RegistrationView.class);
	
	private User user = new User();
	private File uploadedImage;
	private String uploadedImageName;
	private AuthController auth;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {  
			JOptionPane.showMessageDialog(null,"Cannot set UI");
		}
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				RegistrationView frame = new RegistrationView();
				frame.setVisible(true);
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public RegistrationView() {
		super("NBG TeleBanking - Registration");
		
		logger.info("Initializing Registration View");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(156dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JPanel pnlRadioBtns = new JPanel();
		contentPane.add(pnlRadioBtns, "10, 2, fill, fill");
		pnlRadioBtns.setLayout(new GridLayout(0, 2, 0, 0));
		
		rdbtnCustomer = new JRadioButton("Customer");
		rdbtnTypeGroup.add(rdbtnCustomer);
		pnlRadioBtns.add(rdbtnCustomer);
		rdbtnCustomer.setSelected(true);
		
		rdbtnManager = new JRadioButton("Manager");
		rdbtnTypeGroup.add(rdbtnManager);
		pnlRadioBtns.add(rdbtnManager);
		
		JLabel lblFirstName = new JLabel("First Name:");
		contentPane.add(lblFirstName, "10, 4");
		
		txtFname = new JTextField();
		contentPane.add(txtFname, "10, 6, fill, default");
		txtFname.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name");
		contentPane.add(lblLastName, "10, 8");
		
		txtLname = new JTextField();
		contentPane.add(txtLname, "10, 10, fill, default");
		txtLname.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		contentPane.add(lblEmail, "10, 12");
		
		txtEmail = new JTextField();
		contentPane.add(txtEmail, "10, 14, fill, default");
		txtEmail.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		contentPane.add(lblPassword, "10, 16");
		
		passwordField = new JPasswordField();
		contentPane.add(passwordField, "10, 18, fill, default");
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		contentPane.add(lblConfirmPassword, "10, 20");
		
		confirmPasswordField = new JPasswordField();
		contentPane.add(confirmPasswordField, "10, 22, fill, default");
		
		pnlFileUpload = new JPanel();
		contentPane.add(pnlFileUpload, "10, 24, fill, fill");
		pnlFileUpload.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblUploadAProfile = new JLabel("Upload a Profile Photo");
		pnlFileUpload.add(lblUploadAProfile);
		
		btnOpenFileChooser = new JButton("Open File Chooser");
		pnlFileUpload.add(btnOpenFileChooser);
		
		btnRegister = new JButton("REGISTER");
		contentPane.add(btnRegister, "10, 28, 1, 3");
		
		contentPane.getParent().setFocusable(true);
		
		// Configure Listeners
		configureListeners();
		
	}
	
	public void configureListeners() {
		logger.info("Configuring Listeners");
		rdbtnCustomer.addActionListener(this);
		
		rdbtnManager.addActionListener(this);
		
		btnOpenFileChooser.addActionListener(this);
		
		btnRegister.addActionListener(this);
		
		
		contentPane.getParent().addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent event) {
				if(event.getKeyCode() == KeyEvent.VK_ENTER) {
					btnRegister.doClick();
				}
			}
			
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(rdbtnCustomer)) {// radio button event
			user.setType(rdbtnCustomer.getText());
			pnlFileUpload.setVisible(true);
			
		}else if(event.getSource().equals(rdbtnManager)) {// radio button event
			user.setType(rdbtnManager.getText());
			pnlFileUpload.setVisible(false);
			
		}else if(event.getSource().equals(btnOpenFileChooser)) {//file chooser
			JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			fileChooser.setDialogTitle("Upload a Profile Photo");
			FileNameExtensionFilter filter = new FileNameExtensionFilter ("Image types", "jpg", "png", "jpeg");
			
			fileChooser.setFileFilter(filter);
			fileChooser.setAcceptAllFileFilterUsed(false);
			
			if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				uploadedImage = fileChooser.getSelectedFile();
				
				uploadedImageName = uploadedImage.getName();
				btnOpenFileChooser.setText(uploadedImage.getName());
				
				uploadedImageName = renameImage(uploadedImageName) + "-" + (new Date()).getTime() + "." + FilenameUtils.getExtension(uploadedImage.getName());
				
			}
			
		}else if(event.getSource().equals(btnRegister)) { // register user
			btnRegister.setEnabled(false);
			String password = new String(passwordField.getPassword());
			
			
			if(password.equals(new String(confirmPasswordField.getPassword()))) {
				user.setFirstName(txtFname.getText());
				user.setLastName(txtLname.getText());
				user.setEmail(txtEmail.getText());
				user.setPassword(password);
				
				if(user.getType().equals("") || user.getType().equals("customer")) {
					user.setType("customer");
					
					if(uploadedImage != null) {
						Pattern extensions = Pattern.compile("([^\\s]+(\\.(?i)(jpg|jpeg|png))$)");
						if(!extensions.matcher(uploadedImage.getName()).matches()) {
							JOptionPane.showMessageDialog(null, "Unacceptable file format.", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						File destination = new File("Client/storage/uploads", uploadedImageName);
						
						logger.debug("Moving image to images folder");
						if(uploadedImage.renameTo(destination)) {
							Photo profilePhoto = new Photo(uploadedImageName);
							
							user.setPhoto(profilePhoto);
						}else {
							uploadedImageName = "";
							logger.warn("Profile photo failed to upload");
						}
					}
					
				}
				
				auth = new AuthController(new Client());
				
				if(auth.register(user)) {
					JOptionPane.showMessageDialog(null, "You were registered successfully.");
					txtEmail.setText("");
					txtFname.setText("");
					txtLname.setText("");
					passwordField.setText("");
					confirmPasswordField.setText("");
					rdbtnCustomer.setSelected(true);
					rdbtnCustomer.doClick();
					btnOpenFileChooser.setText("Open File Chooser");
				}else {
					JOptionPane.showMessageDialog(null, "Failed to register you. Please try again.");
				}					
				
				
			}else {
				JOptionPane.showMessageDialog(null, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
				passwordField.setText("");
				confirmPasswordField.setText("");
			}
			
			btnRegister.setEnabled(true);
			
		}
	}
	
	public String renameImage(String imageName) {
		StringBuilder sb = new StringBuilder();
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			byte[] hash = md5.digest(imageName.getBytes(StandardCharsets.UTF_8));
			
			for(byte b : hash) {
				sb.append(String.format("%02x", b));
			}
		}catch(NoSuchAlgorithmException e) {
			logger.error("No algorithm found for 'md5'", e.getMessage());
		}
		
		return sb.toString();
	}

}
