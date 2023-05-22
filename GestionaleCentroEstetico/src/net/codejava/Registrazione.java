package net.codejava;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Registrazione extends JFrame {
    private JTextField campoNome, campoCognome, campoTelefono, campoEmail, campoUsername, campoDataNascita;
    private JPasswordField campoPassword;

    public Registrazione() {
    	
    	// Ottieni le dimensioni dello schermo
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


		// Imposta la dimensione della finestra in base alle dimensioni dello schermo
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		setSize(screenWidth, screenHeight);
    	
        setTitle("Registrazione Nuovo Utente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel pannelloInput = new JPanel(new GridLayout(8, 1));
        pannelloInput.setBackground(Color.WHITE);
        
        JLabel etichettaNome = createFormattedLabel("Nome");
        campoNome = createTextField();
        
        JLabel etichettaCognome = createFormattedLabel("Cognome");
        campoCognome = createTextField();
        
        JLabel etichettaDataNascita = createFormattedLabel("Data di nascita");
        campoDataNascita = createTextField();
        campoDataNascita.setText("yyyy/mm/dd");
        
        JLabel etichettaTelefono = createFormattedLabel("Telefono");
        campoTelefono = createTextField();
        
        JLabel etichettaEmail = createFormattedLabel("Email");
        campoEmail = createTextField();

        JLabel etichettaUsername = createFormattedLabel("Username");
        campoUsername = createTextField();

        JLabel etichettaPassword = createFormattedLabel("Password");
        campoPassword = new JPasswordField();

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

        JButton tornaIndietro = createButton("Torna al Login", Color.PINK);
        tornaIndietro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
            }
        });
        pannelloInput.add(tornaIndietro);

        JButton pulsanteRegistrazione = createButton("Registrazione", Color.WHITE);
        pulsanteRegistrazione.addActionListener(e -> {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/centroestetico", "root", "BlaBlaBla24.");

                String query = "INSERT INTO cliente (nome, cognome, data_nascita, telefono, email, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, campoNome.getText());
                stmt.setString(2, campoCognome.getText());
                stmt.setString(3, campoDataNascita.getText());
                stmt.setString(4, campoTelefono.getText());
                stmt.setString(5, campoEmail.getText());
                stmt.setString(6, campoUsername.getText());
                stmt.setString(7, new String(campoPassword.getPassword()));

                stmt.executeUpdate();

                stmt.close();
                conn.close();

                JOptionPane.showMessageDialog(this, "Registrazione effettuata con successo");

                new Login();
                dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Errore durante la registrazione");
            }
        });
        
        // Aggiungi un listener di focus al campo di testo per la data di nascita
        campoDataNascita.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Rimuovi il testo del placeholder quando il campo riceve il focus
                if (campoDataNascita.getText().equals("yyyy/mm/dd")) {
                    campoDataNascita.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Non fare nulla quando il campo perde il focus
            }

			
        });

        pannelloInput.add(pulsanteRegistrazione);

        JPanel pannelloPrincipale = new JPanel(new BorderLayout());
        pannelloPrincipale.add(pannelloInput, BorderLayout.CENTER);
        add(pannelloPrincipale);

        
        setVisible(true);
    }

    private JLabel createFormattedLabel(String labelText) {
        JLabel label = new JLabel(labelText, JLabel.CENTER);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Verdana", Font.PLAIN, 20));
        label.setBorder(new LineBorder(Color.PINK, 1));
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 100));
        textField.setMargin(new Insets(20, 20, 20, 20));
        return textField;
    }

    private JButton createButton(String buttonText, Color backgroundColor) {
        JButton button = new JButton(buttonText);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Verdana", Font.PLAIN, 20));
        button.setPreferredSize(new Dimension(200, 80));
        button.setBackground(backgroundColor);
        return button;
    }
    
}
