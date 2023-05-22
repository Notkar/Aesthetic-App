package net.codejava;

import java.util.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Login extends JFrame {

	//Dichiarazione attributi

	private JLabel nomeUtenteLabel, passwordUtenteLabel;
	private JTextField nomeUtenteField, passwordUtenteField;
	private JButton logButton, registerButton;
	private static final String DB_URL = "jdbc:mysql://localhost/centroestetico";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "BlaBlaBla24.";

	//Costruttore

	public Login() {

		nomeUtenteLabel = new JLabel("Nome Utente ", JLabel.CENTER);
		passwordUtenteLabel = new JLabel("Password ", JLabel.CENTER);
		nomeUtenteField = new JTextField(50);
		passwordUtenteField = new JPasswordField(50);
		logButton = new JButton("Entra");
		registerButton = new JButton("Registrati");

		//Grafica del panello 

		nomeUtenteLabel.setForeground(Color.pink);
		passwordUtenteLabel.setForeground(Color.pink);
		nomeUtenteLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		passwordUtenteLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		logButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		registerButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		nomeUtenteField.setMaximumSize(nomeUtenteField.getMaximumSize());   
		nomeUtenteField.setPreferredSize(new Dimension(200, 100));
		Insets insets = new Insets(20, 20, 20, 20);
		nomeUtenteField.setMargin(insets);
		passwordUtenteField.setPreferredSize(new Dimension(200, 100));
		passwordUtenteField.setMargin(insets);

		nomeUtenteField.setBackground(Color.pink);
		passwordUtenteField.setBackground(Color.pink);

		nomeUtenteField.setFont(new Font("Verdana", Font.PLAIN, 20));
		passwordUtenteField.setFont(new Font("Verdana", Font.PLAIN, 20));

		nomeUtenteField.setHorizontalAlignment(JTextField.CENTER);
		passwordUtenteField.setHorizontalAlignment(JTextField.CENTER);

		JPanel panel = new JPanel(new GridLayout(4,1));

		panel.setBackground(Color.white);


		// Ottieni le dimensioni dello schermo
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Imposta la dimensione della finestra in base alle dimensioni dello schermo
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		setSize(screenWidth, screenHeight);
		
		//setSize(1080, 1920);

		setTitle("Accesso");
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel.add(nomeUtenteLabel);
		panel.add(nomeUtenteField);
		panel.add(passwordUtenteLabel);
		panel.add(passwordUtenteField);


		//Azione onclick

		logButton.addActionListener(new ActionListener() {

			public int getId() {

				String nomeUtente = nomeUtenteField.getText();
				String passwordUtente = passwordUtenteField.getText();
				int id = 0;

				Connection con = null;
				PreparedStatement stmt = null;
				String sql = "SELECT * FROM cliente";

				//Connessione al database

				try {
					con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
					stmt = con.prepareStatement(sql);
					ResultSet rs = stmt.executeQuery("SELECT * FROM cliente");

					while(rs.next()) {
						if(true) {
							String username = rs.getString("username");
							String password = rs.getString("password");

							if(username.equals(nomeUtente) && password.equals(passwordUtente)) {
								id = rs.getInt("id");
							}														
						}  
					}
				}	
				catch(SQLException ex) {
					ex.printStackTrace();
				}
				finally {
					try {
						stmt.close();
						con.close();
					}catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
				return id;				
			}

			public void actionPerformed(ActionEvent e) {

				String nomeUtente = nomeUtenteField.getText();
				String passwordUtente = passwordUtenteField.getText();
				int id = getId();

				Connection con = null;
				PreparedStatement stmt = null;
				String sql = "SELECT cliente.id, cliente.username, cliente.password, proprietario.id, proprietario.username, proprietario.password, dipendente.id, dipendente.username, dipendente.password FROM cliente JOIN proprietario, dipendente";

				try {
					con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
					stmt = con.prepareStatement(sql);
					ResultSet rs = stmt.executeQuery("SELECT cliente.id, cliente.username, cliente.password, proprietario.id, proprietario.username, proprietario.password, dipendente.id, dipendente.username, dipendente.password FROM cliente JOIN proprietario, dipendente");
					boolean flag = false;

					while(rs.next() && !flag) {
						if(true) {
							String username = rs.getString("cliente.username");
							String password = rs.getString("cliente.password");
							String usernameProprietario = rs.getString("proprietario.username");
							String passwordProprietario = rs.getString("proprietario.password");
							String usernameDipendente = rs.getString("dipendente.username");
							String passwordDipendente = rs.getString("dipendente.password");

							if(username.equals(nomeUtente) && password.equals(passwordUtente)) {
								JOptionPane.showMessageDialog(null, "Accesso come utente eseguito.");
								new MenuUtente(id);

								dispose();
								flag = true;
							}	

							

							else if(usernameDipendente.equals(nomeUtente) && passwordDipendente.equals(passwordUtente)) {
								JOptionPane.showMessageDialog(null, "Accesso come dipendente eseguito.");
								new MenuDipendente();

								dispose();
								flag = true;
							}
							
							else if(usernameProprietario.equals(nomeUtente) && passwordProprietario.equals(passwordUtente)) {
								JOptionPane.showMessageDialog(null, "Accesso come proprietario eseguito.");
								new MenuProprietario();

								dispose();
								flag = true;
							}
						}
					}
					if(!flag) {
						JOptionPane.showMessageDialog(null, "Nome utente o password errati.");
					}
					rs.close();
				}
				catch(SQLException ex) {
					ex.printStackTrace();
				}
				finally {
					try {
						
						stmt.close();
						con.close();
					}catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		registerButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				new Registrazione();
				dispose();
			}
		});

		//Pannello per il pulsante invia

		JPanel buttonPanel = new JPanel(new GridLayout(1,2));

		logButton.setPreferredSize(new Dimension(150, 80));
		logButton.setForeground(Color.black);
		logButton.setBackground(Color.pink);
		registerButton.setPreferredSize(new Dimension(150, 80));
		registerButton.setForeground(Color.black);
		registerButton.setBackground(Color.pink);
		buttonPanel.setBackground(Color.white);

		buttonPanel.add(logButton);
		buttonPanel.add(registerButton);

		add(buttonPanel, BorderLayout.SOUTH);
		add(panel);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Login();
	}
}