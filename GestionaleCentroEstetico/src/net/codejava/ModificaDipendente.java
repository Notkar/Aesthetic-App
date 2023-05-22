package net.codejava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModificaDipendente extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost/centroestetico";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "BlaBlaBla24.";

    public ModificaDipendente() {
        JPanel panelModifica = new JPanel(new GridLayout(8, 2));

        setTitle("Modifica dati di un dipendente");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelModifica.setBackground(Color.PINK);
        
        JLabel casoLabel = new JLabel();
        JLabel modificaLabel = new JLabel("Modifica dati dipendente da ID", JLabel.CENTER);        
        JLabel idLabel = new JLabel("ID: ", JLabel.CENTER);
        JTextField idField = new JTextField();
        JLabel nomeLabel = new JLabel("Nome: ", JLabel.CENTER);
        JTextField nomeField = new JTextField();
        JLabel cognomeLabel = new JLabel("Cognome: ", JLabel.CENTER);
        JTextField cognomeField = new JTextField();        
        JLabel telefonoLabel = new JLabel("Telefono: ", JLabel.CENTER);
        JTextField telefonoField = new JTextField();
        JLabel emailLabel = new JLabel("Email: ", JLabel.CENTER);
        JTextField emailField = new JTextField();
             
        
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
        
        telefonoLabel.setFont(font);
        telefonoLabel.setForeground(foreground);
        emailLabel.setFont(font);
        emailLabel.setForeground(foreground);
        
        

        panelModifica.add(casoLabel);
        panelModifica.add(modificaLabel);
        panelModifica.add(idLabel);
        panelModifica.add(idField);        
        panelModifica.add(nomeLabel);
        panelModifica.add(nomeField);
        panelModifica.add(cognomeLabel);
        panelModifica.add(cognomeField);        
        panelModifica.add(telefonoLabel);
        panelModifica.add(telefonoField);
        panelModifica.add(emailLabel);
        panelModifica.add(emailField);              
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
                new ListaDipendenti();
                
            }
        });
        
        
        modificaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Connection conn = null;
        		PreparedStatement stmt = null;
                try {
        			conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
        			String sql = "UPDATE dipendente SET nome = ?, cognome = ?, telefono = ?, email = ? WHERE id=?";                    
        			
        			
        			stmt = conn.prepareStatement(sql);
        			stmt.setString(1, nomeField.getText());
        			stmt.setString(2, cognomeField.getText());        			
        			stmt.setString(3, telefonoField.getText());
        			stmt.setString(4, emailField.getText());        			
        			stmt.setInt(5, Integer.parseInt(idField.getText()));
        			
        			
        			stmt.executeUpdate();
        			
        			int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        // Il cliente è stato modificato con successo
                        JOptionPane.showMessageDialog(null, "Dipendente modificato con successo!");
                    } else {
                        // Nessun cliente trovato con l'ID specificato
                        JOptionPane.showMessageDialog(null, "Nessun dipendente trovato con l'ID specificato", "Errore", JOptionPane.ERROR_MESSAGE);
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
