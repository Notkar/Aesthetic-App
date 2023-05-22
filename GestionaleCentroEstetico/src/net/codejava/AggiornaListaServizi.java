package net.codejava;


import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

public class AggiornaListaServizi extends JFrame {
    private JLabel fotoServiziLabel;
    private JLabel nomeServizioLabel;
    private JTextField nomeServiziField;
    private JLabel tempoLabel;
    private JTextField tempoField;
    private JLabel prezzoLabel;
    private JTextField prezzoField;
    private JButton eliminaButton;
    private JButton aggiungiButton;
    private JLabel quantitaProdottoLabel;
    private JTextField quantitaProdottoField;
    private JLabel nomeProdottoLabel;
    private JTextField nomeProdottoField;
    private JButton selezionaButton;
    private JButton aggiornaButton;
    private JButton tornaAlMenuButton;
    private File selectedFile;

    public AggiornaListaServizi() {
    	
    	JPanel panel = new JPanel(new GridLayout(9,2));
    	panel.setBackground(Color.PINK);

    	setTitle("Aggiorna Lista Servizi");
		setSize(1920, 1080);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        nomeServizioLabel = new JLabel("Nome Servizio");
        panel.add(nomeServizioLabel);

        nomeServiziField = new JTextField();
        panel.add(nomeServiziField);

        tempoLabel = new JLabel("Tempo");
        panel.add(tempoLabel);

        tempoField = new JTextField();
        panel.add(tempoField);

        prezzoLabel = new JLabel("Prezzo");
        panel.add(prezzoLabel);

        prezzoField = new JTextField();
        panel.add(prezzoField);
        
        quantitaProdottoLabel = new JLabel("Quantità Prodotto");
        panel.add(quantitaProdottoLabel);

        quantitaProdottoField = new JTextField();
        panel.add(quantitaProdottoField);

        nomeProdottoLabel = new JLabel("Nome Prodotto");
        panel.add(nomeProdottoLabel);

        nomeProdottoField = new JTextField();
        panel.add(nomeProdottoField);
        
        fotoServiziLabel = new JLabel("Foto Servizi");
        panel.add(fotoServiziLabel);
        
        selezionaButton = new JButton("Seleziona");
        panel.add(selezionaButton);

        eliminaButton = new JButton("Elimina");
        panel.add(eliminaButton);

        aggiungiButton = new JButton("Aggiungi");
        panel.add(aggiungiButton);
        
        aggiornaButton = new JButton("Aggiorna");
        panel.add(aggiornaButton);
        
        tornaAlMenuButton = new JButton("Torna al Menù");
        panel.add(tornaAlMenuButton);
        
        add(panel);
    	setVisible(true);
    	
    	 aggiungiButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 aggiungiServizio();
             }
         });
    	 
    	 eliminaButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 eliminaServizio();
             }
         });

        aggiornaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateServizio();
            }
        });
        tornaAlMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
				new MenuDipendente();
							
				
			}
		}
				);
    
    selezionaButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
            }
        }
    });
    
    }

    private void updateServizio() {
        String nomeServizio = nomeServiziField.getText();
        String tempo = tempoField.getText();
        String prezzo = prezzoField.getText();
        String nomeProdotto = nomeProdottoField.getText();
        String quantitaProdotto = quantitaProdottoField.getText();
        if (selectedFile != null) {
        try {
        	byte[] fileData = readFile(selectedFile);
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/centroestetico", "root", "892892");

            String query = "UPDATE Servizio SET tempo = ?, prezzo = ?, foto_servizio = ?, prodotto_servizio = ?, nome_prodotto = ? WHERE nome = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, tempo);
            statement.setString(2, prezzo);
            statement.setBytes(3, fileData);
            statement.setString(4, quantitaProdotto);
            statement.setString(5, nomeProdotto);
            statement.setString(6, nomeServizio);

            statement.executeUpdate();

            statement.close();
            conn.close();

        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
       }
    }
    
    private void aggiungiServizio() {
        String nomeServizio = nomeServiziField.getText();
        String tempo = tempoField.getText();
        String prezzo = prezzoField.getText();
        String nomeProdotto = nomeProdottoField.getText();
        String quantitaProdotto = quantitaProdottoField.getText();
        if (selectedFile != null) {
        try {
        	byte[] fileData = readFile(selectedFile);
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/centroestetico", "root", "892892");

            String query = "INSERT INTO Servizio(nome, tempo, prezzo, foto_servizio, prodotto_servizio, nome_prodotto) VALUES(?,?,?,?,?,?)";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, nomeServizio);
            statement.setString(2, tempo);
            statement.setString(3, prezzo);
            statement.setBytes(4, fileData);
            statement.setString(5, quantitaProdotto);
            statement.setString(6, nomeProdotto);

            statement.executeUpdate();

            statement.close();
            conn.close();

        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
       }
    }
    
    
    private void eliminaServizio() {
        String nomeServizio = nomeServiziField.getText();
        if (selectedFile != null) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/centroestetico", "root", "BlaBlaBla24.");

            String query = "DELETE FROM Servizio WHERE nome = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, nomeServizio);

            statement.executeUpdate();

            statement.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       }
    }
    
    
    
    private byte[] readFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return data;
    }
 

    
}	