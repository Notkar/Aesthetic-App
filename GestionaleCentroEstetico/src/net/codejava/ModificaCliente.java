package net.codejava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModificaCliente extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost/centroestetico";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "BlaBlaBla24.";

    public ModificaCliente() {
        JPanel panelModifica = new JPanel(new GridLayout(10, 2));

        setTitle("Modifica dati di un cliente");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelModifica.setBackground(Color.PINK);
        
        JLabel casoLabel = new JLabel();
        JLabel modificaLabel = new JLabel("Modifica dati cliente da ID", JLabel.CENTER);        
        JLabel idLabel = new JLabel("ID: ", JLabel.CENTER);
        JTextField idField = new JTextField();
        JLabel nomeLabel = new JLabel("Nome: ", JLabel.CENTER);
        JTextField nomeField = new JTextField();
        JLabel cognomeLabel = new JLabel("Cognome: ", JLabel.CENTER);
        JTextField cognomeField = new JTextField();
        JLabel dataNascitaLabel = new JLabel("Data di nascita: ", JLabel.CENTER);
        JTextField dataNascitaField = new JTextField("yyyy/mm/dd");
        dataNascitaField.setText("yyyy/mm/dd");
        JLabel telefonoLabel = new JLabel("Telefono: ", JLabel.CENTER);
        JTextField telefonoField = new JTextField();
        JLabel emailLabel = new JLabel("Email: ", JLabel.CENTER);
        JTextField emailField = new JTextField();
        JLabel preferenzeLabel = new JLabel("Preferenze cliente: ", JLabel.CENTER);
        JTextField preferenzeField = new JTextField();     
        
        JButton modificaButton = new JButton("Modifica");
        JButton backButton = new JButton("Torna Indietro");
        
        
        

        Font font = new Font("Verdana", Font.BOLD, 16);
        Color foreground = Color.WHITE;
        modificaLabel.setFont(font);
        modificaLabel.setForeground(foreground);
        
        idLabel.setFont(font);
        idLabel.setForeground(foreground);
        casoLabel.setForeground(foreground);
        nomeLabel.setFont(font);
        nomeLabel.setForeground(foreground);
        cognomeLabel.setFont(font);
        cognomeLabel.setForeground(foreground);
        dataNascitaLabel.setFont(font);
        dataNascitaLabel.setForeground(foreground);
        telefonoLabel.setFont(font);
        telefonoLabel.setForeground(foreground);
        emailLabel.setFont(font);
        emailLabel.setForeground(foreground);
        preferenzeLabel.setFont(font);
        preferenzeLabel.setForeground(foreground);
        

        panelModifica.add(casoLabel);
        panelModifica.add(modificaLabel);
        panelModifica.add(idLabel);
        panelModifica.add(idField);        
        panelModifica.add(nomeLabel);
        panelModifica.add(nomeField);
        panelModifica.add(cognomeLabel);
        panelModifica.add(cognomeField);
        panelModifica.add(dataNascitaLabel);
        panelModifica.add(dataNascitaField);
        panelModifica.add(telefonoLabel);
        panelModifica.add(telefonoField);
        panelModifica.add(emailLabel);
        panelModifica.add(emailField);
        panelModifica.add(preferenzeLabel);
        panelModifica.add(preferenzeField);        
        panelModifica.add(backButton);
        panelModifica.add(modificaButton);
        

        

        //panelModifica.setAlignmentX(Component.CENTER_ALIGNMENT);
        //panelModifica.setAlignmentY(Component.CENTER_ALIGNMENT);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelModifica, BorderLayout.CENTER);

        setVisible(true);
        
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GestioneClienti();
                
            }
        });
        
        
        modificaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Connection conn = null;
        		PreparedStatement stmt = null;
                try {
        			conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
        			String sql = "UPDATE cliente SET nome = ?, cognome = ?, data_nascita = ?, telefono = ?, email = ?, preferenze = ? WHERE id=?";                    
        			
        			
        			stmt = conn.prepareStatement(sql);
        			stmt.setString(1, nomeField.getText());
        			stmt.setString(2, cognomeField.getText());
        			stmt.setString(3, dataNascitaField.getText());
        			stmt.setString(4, telefonoField.getText());
        			stmt.setString(5, emailField.getText());
        			stmt.setString(6, preferenzeField.getText());
        			stmt.setInt(7, Integer.parseInt(idField.getText()));
        			
        			
        			stmt.execute();
        			
        			int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        // Il cliente è stato modificato con successo
                        JOptionPane.showMessageDialog(null, "Cliente modificato con successo!");
                    } else {
                        // Nessun cliente trovato con l'ID specificato
                        JOptionPane.showMessageDialog(null, "Nessun cliente trovato con l'ID specificato", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
        		}
        		catch(SQLException ex) {
        			ex.printStackTrace();
        		}finally {
        			try {
        				stmt.close();
        				conn.close();
        			}catch(SQLException ex) {
        				ex.printStackTrace();
        			}
        		}
                
            }
        });
        
        
    }

    
}
