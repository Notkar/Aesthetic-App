package net.codejava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AggiungiDipendente extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost/centroestetico";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "BlaBlaBla24.";

    public AggiungiDipendente() {
        JPanel panelAggiungi = new JPanel(new GridLayout(8, 2));

        setTitle("Inserisci un nuovo Dipendente");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelAggiungi.setBackground(Color.PINK);

        JLabel modificaLabel = new JLabel("Inserisci nuovo Dipendente", JLabel.CENTER);
        JLabel caso1Label = new JLabel();
        JLabel nomeLabel = new JLabel("Nome: ", JLabel.CENTER);
        JTextField nomeField = new JTextField();
        JLabel cognomeLabel = new JLabel("Cognome: ", JLabel.CENTER);
        JTextField cognomeField = new JTextField();        
        JLabel telefonoLabel = new JLabel("Telefono: ", JLabel.CENTER);
        JTextField telefonoField = new JTextField();
        JLabel emailLabel = new JLabel("Email: ", JLabel.CENTER);
        JTextField emailField = new JTextField();        
        JLabel usernameLabel =  new JLabel("Username: ", JLabel.CENTER);
        JTextField usernameField =  new JTextField();
        JLabel passwordLabel =  new JLabel("Password: ", JLabel.CENTER);
        JTextField passwordField = new JTextField();

        Font font = new Font("Verdana", Font.BOLD, 16);
        Color foreground = Color.WHITE;
        modificaLabel.setFont(font);
        modificaLabel.setForeground(foreground);
        caso1Label.setForeground(foreground);
        nomeLabel.setFont(font);
        nomeLabel.setForeground(foreground);
        cognomeLabel.setFont(font);
        cognomeLabel.setForeground(foreground);        
        telefonoLabel.setFont(font);
        telefonoLabel.setForeground(foreground);
        emailLabel.setFont(font);
        emailLabel.setForeground(foreground);        
        usernameLabel.setFont(font);
        usernameLabel.setForeground(foreground);
        passwordLabel.setFont(font);
        passwordLabel.setForeground(foreground);

        panelAggiungi.add(modificaLabel);
        panelAggiungi.add(caso1Label);
        panelAggiungi.add(nomeLabel);
        panelAggiungi.add(nomeField);
        panelAggiungi.add(cognomeLabel);
        panelAggiungi.add(cognomeField);        
        panelAggiungi.add(telefonoLabel);
        panelAggiungi.add(telefonoField);
        panelAggiungi.add(emailLabel);
        panelAggiungi.add(emailField);        
        panelAggiungi.add(usernameLabel);
        panelAggiungi.add(usernameField);
        panelAggiungi.add(passwordLabel);
        panelAggiungi.add(passwordField);
        
        

        JButton btnAggiungi = new JButton("Aggiungi Dipendente");
        btnAggiungi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Connection conn = null;
                PreparedStatement stmt = null;

                try {
                    conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                    String sql = "INSERT INTO dipendente(nome, cognome, telefono, email, username, password) VALUES (?, ?, ?, ?, ?, ?)";

                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, nomeField.getText());
                    stmt.setString(2, cognomeField.getText());                    
                    stmt.setString(3, telefonoField.getText());
                    stmt.setString(4, emailField.getText());                    
                    stmt.setString(5, usernameField.getText());
                    stmt.setString(6, passwordField.getText());

                    stmt.executeUpdate();
                    
                    int rowsAggiunte = stmt.executeUpdate();

	                if (rowsAggiunte > 0) {
	                    JOptionPane.showMessageDialog(null, "Dipendente aggiunto con successo");
	                } else {
	                    JOptionPane.showMessageDialog(null, "Nessun account corrispondente trovato");
	                }

                    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (stmt != null) {
                            stmt.close();
                        }
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JButton btnIndietro = new JButton("Indietro");
        btnIndietro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ListaDipendenti();
                dispose(); // Chiude la finestra corrente
            }
        });

        panelAggiungi.add(btnIndietro);
        panelAggiungi.add(btnAggiungi);
        
        getContentPane().add(panelAggiungi);
        setVisible(true);
    }

    
}
