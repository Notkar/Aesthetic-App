package net.codejava;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
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

public class AggiornaMagazzinoProprietario extends JFrame {
	private JLabel nomeProdottoLabel;
	private JTextField nomeProdottoField;
	private JLabel quantitaProdottoLabel;
	private JTextField quantitaProdottoField;
	private JLabel prezzoProdottoLabel;
	private JTextField prezzoProdottoField;
	private JLabel nomeFornitoreLabel;
	private JTextField nomeFornitoreField;
	private JLabel telefonoFornitoreLabel;
	private JTextField telefonoFornitoreField;
	private JButton selezionaButton;
	private JButton aggiungiButton;
	private JButton eliminaButton;
	private JButton aggiornaButton;
	private JButton tornaAlMenuDipendenteButton;
	private File selectedFile;
	private JLabel aggiungiFotoLabel;

	public AggiornaMagazzinoProprietario() {

		JPanel panel = new JPanel(new GridLayout(8, 2));
		panel.setBackground(Color.pink);

		setTitle("Aggiorna Magazzino Proprietario");
		setSize(1920, 1080);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		nomeProdottoLabel = new JLabel("Nome Prodotto");
		panel.add(nomeProdottoLabel);

		nomeProdottoField = new JTextField();
		panel.add(nomeProdottoField);

		quantitaProdottoLabel = new JLabel("Quantità Prodotto");
		panel.add(quantitaProdottoLabel);

		quantitaProdottoField = new JTextField();
		panel.add(quantitaProdottoField);

		prezzoProdottoLabel = new JLabel("Prezzo Prodotto");
		panel.add(prezzoProdottoLabel);

		prezzoProdottoField = new JTextField();
		panel.add(prezzoProdottoField);

		nomeFornitoreLabel = new JLabel("Nome Fornitore");
		panel.add(nomeFornitoreLabel);

		nomeFornitoreField = new JTextField();
		panel.add(nomeFornitoreField);

		telefonoFornitoreLabel = new JLabel("Telefono Fornitore");
		panel.add(telefonoFornitoreLabel);
		

		telefonoFornitoreField = new JTextField();
		panel.add(telefonoFornitoreField);
		
		aggiungiFotoLabel = new JLabel("Aggiungi Foto");
		panel.add(aggiungiFotoLabel);

		selezionaButton = new JButton("Seleziona");
		panel.add(selezionaButton);

		aggiungiButton = new JButton("Aggiungi nuovo prodotto");
		panel.add(aggiungiButton);

		eliminaButton = new JButton("Elimina");
		panel.add(eliminaButton);
		
		tornaAlMenuDipendenteButton = new JButton("Torna Al Menù");
		panel.add(tornaAlMenuDipendenteButton);

		aggiornaButton = new JButton("Aggiorna prodotto");
		panel.add(aggiornaButton);
		
		

		add(panel);
		setVisible(true);

		aggiungiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aggiungiProdotto();
			}
		});

		eliminaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminaProdotto();
			}
		});

		aggiornaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aggiornaProdotto();
			}
		});
		
		tornaAlMenuDipendenteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MenuProprietario();
				dispose();
			}
		});

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
	private void aggiungiProdotto() {
	    String nomeProdotto = nomeProdottoField.getText();
	    String quantitaProdotto = quantitaProdottoField.getText();
	    String prezzoProdotto = prezzoProdottoField.getText();
	    String nomeFornitore = nomeFornitoreField.getText();
	    String telefonoFornitore = telefonoFornitoreField.getText();
	    if (selectedFile != null) {
	        try {
	            byte[] fileData = readFile(selectedFile);
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/centroestetico", "root", "892892");

	            String query = "INSERT INTO magazzino(nome_prodotto, quantita_prodotto, prezzo_prodotto, nome_fornitore, telefono_fornitore, foto_prodotto) VALUES(?,?,?,?,?,?)";

	            PreparedStatement statement = conn.prepareStatement(query);
	            statement.setString(1, nomeProdotto);
	            statement.setString(2, quantitaProdotto);
	            statement.setString(3, prezzoProdotto);
	            statement.setString(4, nomeFornitore);
	            statement.setString(5, telefonoFornitore);
	            statement.setBytes(6, fileData);

	            statement.executeUpdate();

	            statement.close();
	            conn.close();

	        } catch (SQLException | IOException ex) {
	            ex.printStackTrace();
	        }
	    }
	}

	private void eliminaProdotto() {
	    String nomeProdotto = nomeProdottoField.getText();
	    try {
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/centroestetico", "root", "892892");

	        String query = "DELETE FROM magazzino WHERE nome_prodotto = ?";

	        PreparedStatement statement = conn.prepareStatement(query);
	        statement.setString(1, nomeProdotto);

	        statement.executeUpdate();

	        statement.close();
	        conn.close();

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}

	private void aggiornaProdotto() {
	    String nomeProdotto = nomeProdottoField.getText();
	    String quantitaProdotto = quantitaProdottoField.getText();
	    String prezzoProdotto = prezzoProdottoField.getText();
	    String nomeFornitore = nomeFornitoreField.getText();
	    String telefonoFornitore = telefonoFornitoreField.getText();
	    if (selectedFile != null) {
	        try {
	            byte[] fileData = readFile(selectedFile);
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/centroestetico", "root", "BlaBlaBla24.");

	            String query = "UPDATE magazzino SET quantita_prodotto = ?, prezzo_prodotto = ?, nome_fornitore = ?, telefono_fornitore = ?, foto_prodotto = ? WHERE nome_prodotto = ?";

	            PreparedStatement statement = conn.prepareStatement(query);
	            statement.setString(1, quantitaProdotto);
	            statement.setString(2, prezzoProdotto);
	            statement.setString(3, nomeFornitore);
	            statement.setString(4, telefonoFornitore);
	            statement.setBytes(5, fileData);
	            statement.setString(6, nomeProdotto);

	            statement.executeUpdate();

	            statement.close();
	            conn.close();

	        } catch (SQLException | IOException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	private byte[] readFile(File file) throws IOException {
	    FileInputStream fis = new FileInputStream(file);
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    byte[] buf = new byte[1024];
	    try {
	        for (int readNum; (readNum = fis.read(buf)) != -1; ) {
	            bos.write(buf, 0, readNum);
	        }
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	    byte[] bytes = bos.toByteArray();
	    fis.close();
	    return bytes;
	}

}
