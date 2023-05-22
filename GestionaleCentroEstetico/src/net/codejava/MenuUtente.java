package net.codejava;


import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MenuUtente extends JFrame{
	private static int id;
	private static final String DB_URL = "jdbc:mysql://localhost/centroestetico";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "BlaBlaBla24.";	
	
	private JLabel menu, contattiLabel;
	private JButton serviziButton, prenotaButton, appuntamentiButton, aggiornaDatiButton, tornaAlLogin;
	public MenuUtente(int id) {
		
		this.id = id;
		
		menu = new JLabel("Menù Utente");
		
		menu.setHorizontalAlignment(SwingConstants.CENTER);
		menu.setVerticalAlignment(SwingConstants.CENTER);
		menu.setForeground(Color.PINK);
		Font labelFont = menu.getFont();
		int labelFontSize = labelFont.getSize() + 25;
		Font newFont = new Font(labelFont.getName(), labelFont.getStyle(), labelFontSize);
		menu.setFont(newFont);
		
		
		
		serviziButton = new JButton("Servizi");
		Font buttonFont1 = new Font("Arial", Font.BOLD, 35);
		serviziButton.setFont(buttonFont1);
		
		serviziButton.setBackground(Color.PINK);
		
		
		
		prenotaButton = new JButton("Prenota");
		Font buttonFont2 = new Font("Arial", Font.BOLD, 35);
		prenotaButton.setFont(buttonFont2);
		
		prenotaButton.setBackground(Color.WHITE);
		
		
		appuntamentiButton = new JButton("Appuntamenti");
		Font buttonFont3 = new Font("Arial", Font.BOLD, 35);
		appuntamentiButton.setFont(buttonFont3);
		
		appuntamentiButton.setBackground(Color.PINK);
		
				
		aggiornaDatiButton = new JButton("Aggiorna Dati");
		Font buttonFont5 = new Font("Arial", Font.BOLD, 35);
		aggiornaDatiButton.setFont(buttonFont5);
		
		aggiornaDatiButton.setBackground(Color.WHITE);
		
		
		contattiLabel = new JLabel("<html><div style='text-align: center;'>" +
			    "BeautyCamp Salon<br>" +
			    "Via Victor Osimhen, 46<br>" +
			    "065005000</div></html>");
		contattiLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contattiLabel.setVerticalAlignment(SwingConstants.CENTER);
		contattiLabel.setForeground(Color.PINK);
			
		//Font labelFont = contattiLabel.getFont();
		//int labelFontSize = labelFont.getSize();
		//Font newFont = new Font(labelFont.getName(), labelFont.getStyle(), labelFontSize - 2);
		contattiLabel.setFont(newFont);
				
		
		tornaAlLogin = new JButton("Torna al Login");
		Font buttonFont9 = new Font("Arial", Font.BOLD, 35);
		tornaAlLogin.setFont(buttonFont9);
		tornaAlLogin.setBackground(Color.PINK);
		
		JPanel panel = new JPanel(new GridLayout(7,1));
		
		ImageIcon icona = new ImageIcon("C:\\Users\\peppe\\eclipse-workspace\\GestioneRistorante\\logo.jpg");
        setIconImage(icona.getImage());
		setTitle("Menu Utente");
		setSize(1080,1920);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.add(menu);
		panel.add(serviziButton);
		panel.add(prenotaButton);
		panel.add(appuntamentiButton);
		
		panel.add(aggiornaDatiButton);
		
				
		panel.add(tornaAlLogin);
		panel.add(contattiLabel);
		
		
		
		
		serviziButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListaServiziUtente(id);;
				dispose();
			}
		}
				);
		
		prenotaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrenotazioneAppuntamento prenotazioneAppuntamento = new PrenotazioneAppuntamento(id);

		        // Creare e visualizzare il frame della prenotazione
		        JFrame prenotazioneFrame = new JFrame("Prenotazione Appuntamento");
		        prenotazioneFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        prenotazioneFrame.getContentPane().add(prenotazioneAppuntamento);
		        prenotazioneFrame.pack();
		        prenotazioneFrame.setSize(1080, 1920);
		        prenotazioneFrame.setVisible(true);

		        // Nascondi il frame corrente
		        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(prenotaButton);
		        currentFrame.setVisible(false);
			}
		}
				);		
		
		appuntamentiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListaAppuntamentiUtente(id);
				dispose();
			}
		}
				);
		
		
		
		aggiornaDatiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AggiornaDati(id);
				dispose();
			}
		}
				);	
		
		
		
		
		tornaAlLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Login();
				JOptionPane.showMessageDialog(null, "Arrivederci");
				dispose();
			}
		}
				);
		
		
		
		add(panel);
		setVisible(true);
		
	}
}