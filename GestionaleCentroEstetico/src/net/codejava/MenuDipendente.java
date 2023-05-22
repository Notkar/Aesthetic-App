package net.codejava;


import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MenuDipendente extends JFrame{
	
	private static final String DB_URL = "jdbc:mysql://localhost/centroestetico";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "BlaBlaBla24.";	
	
	private JLabel menu;
	private JButton appuntamentiButton, magazzinoButton, clientiButton, prenotaButton, aggiornaMagazzinoButton, serviziButton,aggiornaServiziButton, stampaFatturaButton,fatturaListaButton, tornaAlLogin;
	public MenuDipendente() {
		
		
		menu = new JLabel("Menù dipendenti");
		menu.setHorizontalAlignment(SwingConstants.CENTER);
		menu.setVerticalAlignment(SwingConstants.CENTER);
		Font labelFont = menu.getFont();
		int labelFontSize = labelFont.getSize() + 25;
		Font newFont = new Font(labelFont.getName(), labelFont.getStyle(), labelFontSize);
		menu.setFont(newFont);
		
		
		
		appuntamentiButton = new JButton("Appuntamenti");
		Font buttonFont1 = new Font("Arial", Font.BOLD, 35);
		appuntamentiButton.setFont(buttonFont1);
		
		appuntamentiButton.setBackground(Color.PINK);
		
		
		
		magazzinoButton = new JButton("Magazzino");
		Font buttonFont2 = new Font("Arial", Font.BOLD, 35);
		magazzinoButton.setFont(buttonFont2);
		
		magazzinoButton.setBackground(Color.WHITE);
		
		
		clientiButton = new JButton("Lista Clienti");
		Font buttonFont3 = new Font("Arial", Font.BOLD, 35);
		clientiButton.setFont(buttonFont3);
		
		clientiButton.setBackground(Color.PINK);
		
		
		
		prenotaButton = new JButton("Prenota Appuntamento");
		Font buttonFont4 = new Font("Arial", Font.BOLD, 35);
		prenotaButton.setFont(buttonFont4);
		
		prenotaButton.setBackground(Color.WHITE);
		
		aggiornaMagazzinoButton = new JButton("Aggiorna Magazzino");
		Font buttonFont5 = new Font("Arial", Font.BOLD, 35);
		aggiornaMagazzinoButton.setFont(buttonFont5);
		
		aggiornaMagazzinoButton.setBackground(Color.PINK);
		
		
		aggiornaServiziButton = new JButton("Aggiorna Servizi");
		Font buttonFont6 = new Font("Arial", Font.BOLD, 35);
		aggiornaServiziButton.setFont(buttonFont6);
		
		aggiornaServiziButton.setBackground(Color.PINK);
		
		
		serviziButton = new JButton("Lista Servizi");
		Font buttonFont7 = new Font("Arial", Font.BOLD, 35);
		serviziButton.setFont(buttonFont7);
		
		serviziButton.setBackground(Color.WHITE);
		
		
		
		
		stampaFatturaButton = new JButton("Stampa Fattura");
		Font buttonFont8 = new Font("Arial", Font.BOLD, 35);
		stampaFatturaButton.setFont(buttonFont8);
		
		stampaFatturaButton.setBackground(Color.WHITE);
		
		
		fatturaListaButton = new JButton("Lista Fatture");
		Font buttonFont9 = new Font("Arial", Font.BOLD, 35);
		fatturaListaButton.setFont(buttonFont9);
		
		fatturaListaButton.setBackground(Color.PINK);
		
		
		
		
		
		
		tornaAlLogin = new JButton("Torna al Login");
		Font buttonFont10 = new Font("Arial", Font.BOLD, 35);
		tornaAlLogin.setFont(buttonFont10);
		tornaAlLogin.setBackground(Color.WHITE);
		
		JPanel panel = new JPanel(new GridLayout(11,1));
		
		ImageIcon icona = new ImageIcon("C:\\Users\\logo.jpg");
        setIconImage(icona.getImage());
		setTitle("Menu Dipendente");
		setSize(1920,1080);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.add(menu);
		panel.add(appuntamentiButton);
		panel.add(magazzinoButton);
		panel.add(clientiButton);
		panel.add(prenotaButton);
		panel.add(aggiornaMagazzinoButton);
		panel.add(serviziButton);
		panel.add(aggiornaServiziButton);
		panel.add(stampaFatturaButton);
		panel.add(fatturaListaButton);
	
		
		panel.add(tornaAlLogin);
		
		
		
		
		
		appuntamentiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListaAppuntamentiDipendente();;
				dispose();
			}
		}
				);
		
		magazzinoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VisualizzaMagazzino();
				dispose();
			}
		}
				);		
		
		clientiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GestioneClienti();
				dispose();
			}
		}
				);
		
		prenotaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PannelloPrenotazioneDipendente();
				dispose();
			}
		}
				);
		
		aggiornaMagazzinoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AggiornaMagazzino();
				dispose();
			}
		}
				);	
		
		aggiornaServiziButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AggiornaListaServizi();
				dispose();
			}
		}
				);	
		
		
		
		stampaFatturaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new StampaFattura();
				dispose();
			}
		}
				);
		
		fatturaListaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FatturaLista();
				dispose();
			}
		}
				);
		
		serviziButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListaServiziDipendente();
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
	public static void main(String[] args) {
	    new MenuDipendente();
	}

}