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

public class ListaDipendenti extends JFrame {
	
	private static final String DB_URL = "jdbc:mysql://localhost/centroestetico";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "BlaBlaBla24.";
	
	public ListaDipendenti() {
		
		JPanel panel = new JPanel(new GridLayout(6, 1));
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(1920, 1080)); 

		setTitle("Visualizza dipendenti");
		setSize(1920,1080);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Crea JLabel personalizzato
		JLabel adminLabel = new JLabel("Opzioni dipendenti", JLabel.CENTER);
		adminLabel.setForeground(Color.black);
		adminLabel.setFont(new Font("Verdana", Font.PLAIN, 20));


		// Crea i bottoni personalizzati
		JButton btnVisualizza = new JButton("Visualizza Dipendenti");
		JButton btnAggiungi = new JButton("Aggiungi dipendente");
		JButton btnRimuovi = new JButton("Rimuovi dipendente");
		JButton btnModifica = new JButton("Modifica dati dipendente");
		JButton btnEsci = new JButton("Esci");

		// Imposta lo stile dei bottoni
		Font font = new Font("Verdana", Font.BOLD, 20);
		Color foreground = Color.WHITE;
		Color background = Color.PINK;
		Color foreground1 = Color.BLACK;
		Color background1 = Color.WHITE;
		btnVisualizza.setPreferredSize(new Dimension(150, 80));
		btnVisualizza.setFont(font);
		btnVisualizza.setForeground(foreground);
		btnVisualizza.setBackground(background);
		btnAggiungi.setPreferredSize(new Dimension(150, 80));
		btnAggiungi.setFont(font);
		btnAggiungi.setForeground(foreground1);
		btnAggiungi.setBackground(background1);
		btnRimuovi.setPreferredSize(new Dimension(150, 80));
		btnRimuovi.setFont(font);
		btnRimuovi.setForeground(foreground);
		btnRimuovi.setBackground(background);
		btnModifica.setPreferredSize(new Dimension(150, 80));
		btnModifica.setFont(font);
		btnModifica.setForeground(foreground1);
		btnModifica.setBackground(background1);
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
				
				String sql = "DELETE FROM dipendente WHERE nome = ? AND cognome = ?";
				
				JTextField nomeField = new JTextField();
				JTextField cognomeField = new JTextField();
				
				Object[] fields = {
					"Nome: ", nomeField,
					"Cognome: ", cognomeField
				};
				
				JOptionPane.showConfirmDialog(null, fields, "Elimina account dipendente", JOptionPane.OK_CANCEL_OPTION);
				
				try {
					conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
					
														
							stmt = conn.prepareStatement(sql);
							stmt.setString(1, nomeField.getText());
							stmt.setString(2, cognomeField.getText());

							// Esegue la query
							
							stmt.execute();
							
							int rowsDeleted = stmt.executeUpdate();

			                if (rowsDeleted > 0) {
			                    JOptionPane.showMessageDialog(null, "Dipendente rimosso");
			                } else {
			                    JOptionPane.showMessageDialog(null, "Nessun dipendente corrispondente trovato");
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
				new ModificaDipendente();
			}
		});
		
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new AggiungiDipendente();
				
			}
		});
		
		btnVisualizza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VisualizzaDipendenti();
				dispose();
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
		new ListaDipendenti();
	}
}
