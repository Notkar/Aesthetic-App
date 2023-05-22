package net.codejava;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ListaServiziDipendente extends JFrame{
	private JButton tornaindietro;

    public ListaServiziDipendente() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(Color.pink);
        setTitle("Lista Servizi");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JButton tornaIndietro = new JButton("Torna indietro");
        panel.add(tornaIndietro);
        
        tornaIndietro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MenuDipendente();
                dispose();
            }
        });


        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/centroestetico", "root", "BlaBlaBla24.");

            String query = "SELECT nome, prezzo, foto_servizio FROM Servizio";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nomeServizio = resultSet.getString("nome");
                double prezzoServizio = resultSet.getDouble("prezzo");
                String prezzoFormatted = String.format("%.2f €", prezzoServizio);
                String servizioLine = nomeServizio + " - " + prezzoFormatted;

                JLabel nameLabel = new JLabel(servizioLine);
                panel.add(nameLabel);

                byte[] imagePath = resultSet.getBytes("foto_servizio");
                if (imagePath != null) {
                    try {
                        ImageIcon imageIcon = new ImageIcon(imagePath);
                        Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        JLabel imageLabel = new JLabel(new ImageIcon(image));
                        panel.add(imageLabel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        add(panel);
        setVisible(true);
    }
}
