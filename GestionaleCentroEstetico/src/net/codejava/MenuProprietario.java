package net.codejava;


import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MenuProprietario extends JFrame{
	
	private static final String DB_URL = "jdbc:mysql://localhost/centroestetico";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "BlaBlaBla24.";	
	
	private JLabel menu;
	private JButton dipendentiButton, magazzinoButton, aggiornaMagazzinoButton, serviziButton, aggiornaServiziButton, stampaFatturaButton, visualizzaFattureButton, tornaAlLogin;
	public MenuProprietario() {
		menu = new JLabel("Menù Proprietario");
		menu.setHorizontalAlignment(SwingConstants.CENTER);
		menu.setVerticalAlignment(SwingConstants.CENTER);
		Font labelFont = menu.getFont();
		int labelFontSize = labelFont.getSize() + 25;
		Font newFont = new Font(labelFont.getName(), labelFont.getStyle(), labelFontSize);
		menu.setFont(newFont);
		
		
		
		dipendentiButton = new JButton("Lista Dipendenti");
		Font buttonFont1 = new Font("Arial", Font.BOLD, 35);
		dipendentiButton.setFont(buttonFont1);
		
		dipendentiButton.setBackground(Color.PINK);
		
		
		
		magazzinoButton = new JButton("Magazzino");
		Font buttonFont2 = new Font("Arial", Font.BOLD, 35);
		magazzinoButton.setFont(buttonFont2);
		
		magazzinoButton.setBackground(Color.WHITE);
		
		
		aggiornaMagazzinoButton = new JButton("Aggiorna Magazzino");
		Font buttonFont3 = new Font("Arial", Font.BOLD, 35);
		aggiornaMagazzinoButton.setFont(buttonFont3);
		
		aggiornaMagazzinoButton.setBackground(Color.PINK);
		
		serviziButton = new JButton("Lista Servizi");
		Font buttonFont4 = new Font("Arial", Font.BOLD, 35);
		serviziButton.setFont(buttonFont4);
		
		serviziButton.setBackground(Color.WHITE);
		
		
		aggiornaServiziButton = new JButton("Aggiorna Servizi");
		Font buttonFont5 = new Font("Arial", Font.BOLD, 35);
		aggiornaServiziButton.setFont(buttonFont5);
		
		aggiornaServiziButton.setBackground(Color.PINK);
		
		stampaFatturaButton = new JButton("Stampa Fattura");
		Font buttonFont8 = new Font("Arial", Font.BOLD, 35);
		stampaFatturaButton.setFont(buttonFont8);
		
		stampaFatturaButton.setBackground(Color.WHITE);
		
		
		
		
		
		
		
		visualizzaFattureButton = new JButton("Fatture");
		Font buttonFont6 = new Font("Arial", Font.BOLD, 35);
		visualizzaFattureButton.setFont(buttonFont6);
		
		visualizzaFattureButton.setBackground(Color.PINK);
		
		
		
		
		
		
		
		
		tornaAlLogin = new JButton("Torna al Login");
		Font buttonFont7 = new Font("Arial", Font.BOLD, 35);
		tornaAlLogin.setFont(buttonFont7);
		tornaAlLogin.setBackground(Color.WHITE);
		
		JPanel panel = new JPanel(new GridLayout(9,1));
		
		ImageIcon icona = new ImageIcon("C:\\Users\\peppe\\eclipse-workspace\\GestioneRistorante\\logo.jpg");
        setIconImage(icona.getImage());
		setTitle("Menu Proprietario");
		setSize(1920,1080);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.add(menu);
		panel.add(dipendentiButton);
		panel.add(magazzinoButton);		
		panel.add(aggiornaMagazzinoButton);
		panel.add(serviziButton);
		panel.add(aggiornaServiziButton);
		panel.add(stampaFatturaButton);
		panel.add(visualizzaFattureButton);		
		panel.add(tornaAlLogin);
		
		
		
		
		
		dipendentiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListaDipendenti();;
				dispose();
			}
		}
				);
		
		magazzinoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VisualizzaMagazzinoProprietario();
				dispose();
			}
		}
				);		
		
		
				
		
		aggiornaMagazzinoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AggiornaMagazzinoProprietario();
				dispose();
			}
		}
				);
		
		serviziButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListaServiziProprietario();
				dispose();
			}
		}
				);
		
		
		aggiornaServiziButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AggiornaListaServiziProprietario();
				dispose();
			}
		}
				);	
		
		stampaFatturaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new StampaFatturaProprietario();
				dispose();
			}
		}
				);
		
		
		
		visualizzaFattureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FatturaListaProprietario();;
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
	    new MenuProprietario();
	}

}
