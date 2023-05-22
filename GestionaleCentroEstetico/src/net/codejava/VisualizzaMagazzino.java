package net.codejava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class VisualizzaMagazzino extends JFrame {
    private JButton tornaAlMenu;

    public VisualizzaMagazzino() {

        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Visualizza Magazzino");

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBackground(Color.pink);

        tornaAlMenu = new JButton("Torna al Menù");
        tornaAlMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MenuDipendente();
                dispose();
            }
        });

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/centroestetico", "root", "BlaBlaBla24.");

            String query = "SELECT * FROM magazzino";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nomeProdotto = resultSet.getString("nome_prodotto");
                int quantitaProdotto = resultSet.getInt("quantita_prodotto");
                double prezzoProdotto = resultSet.getDouble("prezzo_prodotto");
                String nomeFornitore = resultSet.getString("nome_fornitore");
                String telefonoFornitore = resultSet.getString("telefono_fornitore");
                String prezzoFormatted = String.format("%.2f €", prezzoProdotto);
                String prodottoLine = nomeProdotto + " - Quantità: " + quantitaProdotto + " - Prezzo: " + prezzoFormatted
                        + " - Fornitore: " + nomeFornitore + " - Telefono: " + telefonoFornitore;

                JLabel prodottoLabel = new JLabel(prodottoLine);

                prodottoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(prodottoLabel);

                byte[] imagePath = resultSet.getBytes("foto_prodotto");
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

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);
        add(tornaAlMenu, BorderLayout.NORTH);

        setVisible(true);
    }
    
}