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

public class AggiornaDati extends JFrame {
	private static int id;
	private JTextField campoNome, campoCognome, campoTelefono, campoEmail, campoUsername, campoDataNascita;
	private JPasswordField campoPassword;

	public AggiornaDati(int id) {
		this.id = id;
		// Imposta le dimensioni della finestra
		setSize(1080, 1920);

		// Imposta il titolo della finestra
		setTitle("Aggiorna dati");
		setLocationRelativeTo(null);

		// Crea un pannello per i campi di input
		JPanel pannelloInput = new JPanel(new GridLayout(14, 1));

		// Crea un campo di testo per il nome 
		JLabel etichettaNome = new JLabel("Nome", JLabel.CENTER);
		campoNome = new JTextField();

		//Crea un campo di testo per il cognome
		JLabel etichettaCognome = new JLabel("Cognome", JLabel.CENTER);
		campoCognome = new JTextField();

		//Crea un campo di testo per la data di nascita
		JLabel etichettaDataNascita = new JLabel("Data di nascita", JLabel.CENTER);
		campoDataNascita = new JTextField();

		//Crea un campo di testo per il telefono
		JLabel etichettaTelefono = new JLabel("Telefono", JLabel.CENTER);
		campoTelefono = new JTextField();

		//Crea un campo di testo per l'email
		JLabel etichettaEmail = new JLabel("Email", JLabel.CENTER);
		campoEmail = new JTextField();

		// Crea un campo di testo per l'username
		JLabel etichettaUsername = new JLabel("Username", JLabel.CENTER);
		campoUsername = new JTextField();

		// Crea un campo di testo per la password
		JLabel etichettaPassword = new JLabel("Password", JLabel.CENTER);
		campoPassword = new JPasswordField();


		etichettaNome.setForeground(Color.white);
		etichettaCognome.setForeground(Color.white);
		etichettaNome.setFont(new Font("Verdana", Font.PLAIN, 20));
		etichettaCognome.setFont(new Font("Verdana", Font.PLAIN, 20));
		etichettaDataNascita.setForeground(Color.white);
		etichettaDataNascita.setFont(new Font("Verdana", Font.PLAIN, 20));

		etichettaTelefono.setForeground(Color.white);
		etichettaEmail.setForeground(Color.white);
		etichettaTelefono.setFont(new Font("Verdana", Font.PLAIN, 20));
		etichettaEmail.setFont(new Font("Verdana", Font.PLAIN, 20));

		etichettaPassword.setForeground(Color.white);
		etichettaUsername.setForeground(Color.white);
		etichettaPassword.setFont(new Font("Verdana", Font.PLAIN, 20));
		etichettaUsername.setFont(new Font("Verdana", Font.PLAIN, 20));

		

		Insets insets = new Insets(20, 20, 20, 20);
		campoNome.setMargin(insets);
		campoCognome.setMargin(insets);
		campoDataNascita.setMargin(insets);
		campoTelefono.setMargin(insets);
		campoEmail.setMargin(insets);
		campoUsername.setMargin(insets);
		campoPassword.setMargin(insets);

		pannelloInput.setBackground(Color.pink); 
		pannelloInput.add(etichettaNome);
		pannelloInput.add(campoNome);
		pannelloInput.add(etichettaCognome);
		pannelloInput.add(campoCognome);
		pannelloInput.add(etichettaDataNascita);
		pannelloInput.add(campoDataNascita);
		pannelloInput.add(etichettaTelefono);
		pannelloInput.add(campoTelefono);
		pannelloInput.add(etichettaEmail);
		pannelloInput.add(campoEmail);
		pannelloInput.add(etichettaUsername);
		pannelloInput.add(campoUsername);
		pannelloInput.add(etichettaPassword);
		pannelloInput.add(campoPassword);
		
		JButton pulsanteTornaAlMenu = new JButton("Torna al menu");
		pulsanteTornaAlMenu.setFont(new Font("Verdana", Font.PLAIN, 20));
		//pulsanteTornaAlMenu.setPreferredSize(new Dimension(200, 80));
		pulsanteTornaAlMenu.setForeground(Color.pink);
		pulsanteTornaAlMenu.setBackground(Color.white);

		pulsanteTornaAlMenu.addActionListener(e -> {
		    new MenuUtente(id);
		    dispose();
		});

		


		// Crea un pulsante per la registrazione
		JButton pulsanteRegistrazione = new JButton("Aggiorna dati");

		pulsanteRegistrazione.setFont(new Font("Verdana", Font.PLAIN, 20));
		//pulsanteRegistrazione.setPreferredSize(new Dimension(200, 80));
		pulsanteRegistrazione.setForeground(Color.pink);
		pulsanteRegistrazione.setBackground(Color.white);

		pulsanteRegistrazione.addActionListener(e -> {
			try {
				// Carica il driver JDBC per MySQL
				Class.forName("com.mysql.cj.jdbc.Driver");

				// Crea la connessione al database
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/centroestetico", "root", "BlaBlaBla24.");

				// Crea la query per l'inserimento dei dati
				String query = "UPDATE cliente SET nome = ?, cognome = ?, data_nascita = ?, telefono = ?, email = ?, username = ?, password = ? WHERE id = ?";

				// Crea lo statement e imposta i parametri
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setString(1, campoNome.getText());
				stmt.setString(2, campoCognome.getText());
				stmt.setString(3, campoDataNascita.getText());
				stmt.setString(4, campoTelefono.getText());
				stmt.setString(5, campoEmail.getText());
				stmt.setString(6, campoUsername.getText());
				stmt.setString(7, new String(campoPassword.getPassword()));
				stmt.setInt(8, id);

				// Esegue la query
				stmt.executeUpdate();

				// Chiude la connessione al database
				stmt.close();
				conn.close();

				// Mostra un messaggio di conferma
				JOptionPane.showMessageDialog(this, "Modifica effettuata con successo");

				new Login();
				dispose();

			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Errore durante la modifica");
			}
		});

		// Crea un pannello per il pulsante di registrazione
		JPanel pannelloRegistrazione = new JPanel();
		pannelloRegistrazione.setBackground(Color.pink);
		pannelloRegistrazione.add(pulsanteTornaAlMenu);
		pannelloRegistrazione.add(pulsanteRegistrazione);
		

		// Crea un pannello principale per la finestra
		JPanel pannelloPrincipale = new JPanel(new BorderLayout());
		pannelloPrincipale.setBackground(Color.pink);
		pannelloPrincipale.add(pannelloInput, BorderLayout.CENTER);
		pannelloPrincipale.add(pannelloRegistrazione, BorderLayout.SOUTH);

		// Aggiungi il pannello principale alla finestra
		add(pannelloPrincipale);

		// Imposta la chiusura della finestra
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Mostra la finestra
		setVisible(true);
	}

	public static void main(String[] args) {
		new AggiornaDati(id);
	}
}
