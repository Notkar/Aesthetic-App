package net.codejava;

import java.util.*;
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

public class GestioneClienti extends JFrame {
	
	private static final String DB_URL = "jdbc:mysql://localhost/centroestetico";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "BlaBlaBla24.";
	
	public GestioneClienti() {
		
		JPanel panel = new JPanel(new GridLayout(6, 1));
		panel.setBackground(Color.PINK);
		panel.setPreferredSize(new Dimension(1080, 1000)); 

		setTitle("Gestione Clienti");
		setSize(1920,1080);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Crea JLabel personalizzato
		JLabel adminLabel = new JLabel("Gestione clienti", JLabel.CENTER);
		adminLabel.setForeground(Color.white);
		adminLabel.setFont(new Font("Verdana", Font.PLAIN, 20));


		// Crea i bottoni personalizzati
		JButton btnVisualizza = new JButton("Visualizza Clienti");
		JButton btnAggiungi = new JButton("Aggiungi cliente");
		JButton btnRimuovi = new JButton("Rimuovi cliente");
		JButton btnModifica = new JButton("Modifica dati cliente");
		JButton btnEsci = new JButton("Torna al menù");

		// Imposta lo stile dei bottoni
		Font font = new Font("Verdana", Font.BOLD, 20);
		Color foreground = Color.WHITE;
		Color background = Color.PINK;
		btnVisualizza.setPreferredSize(new Dimension(150, 80));
		btnVisualizza.setFont(font);
		btnVisualizza.setForeground(foreground);
		btnVisualizza.setBackground(background);
		btnAggiungi.setPreferredSize(new Dimension(150, 80));
		btnAggiungi.setFont(font);
		btnAggiungi.setForeground(foreground);
		btnAggiungi.setBackground(background);
		btnRimuovi.setPreferredSize(new Dimension(150, 80));
		btnRimuovi.setFont(font);
		btnRimuovi.setForeground(foreground);
		btnRimuovi.setBackground(background);
		btnModifica.setPreferredSize(new Dimension(150, 80));
		btnModifica.setFont(font);
		btnModifica.setForeground(foreground);
		btnModifica.setBackground(background);
		btnEsci.setPreferredSize(new Dimension(150, 80));
		btnEsci.setFont(font);
		btnEsci.setForeground(foreground);
		btnEsci.setBackground(background);

		// Aggiungi i bottoni al pannello
		panel.add(adminLabel);
		panel.add(btnVisualizza);
		panel.add(btnAggiungi);
		panel.add(btnRimuovi);
		panel.add(btnModifica);
		panel.add(btnEsci);

		add(panel);
		setVisible(true);
		
		btnRimuovi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				PreparedStatement stmt = null;
				
				String sql = "DELETE FROM cliente WHERE nome = ? AND cognome = ?";
				
				JTextField nomeField = new JTextField();
				JTextField cognomeField = new JTextField();
				
				Object[] fields = {
					"Nome: ", nomeField,
					"Cognome: ", cognomeField
				};
				
				JOptionPane.showConfirmDialog(null, fields, "Elimina account cliente", JOptionPane.OK_CANCEL_OPTION);
				
				try {
					conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
					
														
							stmt = conn.prepareStatement(sql);
							stmt.setString(1, nomeField.getText());
							stmt.setString(2, cognomeField.getText());

							// Esegue la query
							
							stmt.execute();
							
							int rowsDeleted = stmt.executeUpdate();

			                if (rowsDeleted > 0) {
			                    JOptionPane.showMessageDialog(null, "Account rimosso");
			                } else {
			                    JOptionPane.showMessageDialog(null, "Nessun account corrispondente trovato");
			                }
					
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				} 
				finally {
					try {
						if (stmt != null) {
							stmt.close();
						}
						if (conn != null) {
							conn.close();
						}
					} 
					catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new ModificaCliente();
			}
		});
		
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new AggiungiCliente();
			}
		});
		
		btnVisualizza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				VisualizzaClienti visualizzaClienti = new VisualizzaClienti();
				visualizzaClienti.setVisible(true);
			}
		});
		
		btnEsci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MenuDipendente();
				dispose();	    		
			}
		});
	}
	
	public static void main(String[] args) {
		new GestioneClienti();
	}
}

