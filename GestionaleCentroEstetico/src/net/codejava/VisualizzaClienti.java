package net.codejava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class VisualizzaClienti extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton backButton;
    private JTextField searchField;
    private JButton searchButton;
    private JButton showAllButton;

    private static final String DB_URL = "jdbc:mysql://localhost/centroestetico";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "BlaBlaBla24.";

    public VisualizzaClienti() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Lista Clienti");
        setSize(1920, 1080);

        // Create the main content pane with a custom layout
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Set the background color
                g.setColor(new Color(255, 192, 203)); // Pink
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Create the table
        table = new JTable();
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 1850, 700);
        contentPane.add(scrollPane);

        // Create the "Torna Indietro" button
        backButton = new JButton("Torna Indietro");
        backButton.setBounds(50, 800, 150, 30);
        backButton.setBackground(new Color(255, 192, 203)); // Pink
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the current window and open the GestioneClienti window
                dispose();
                new GestioneClienti();
                
            }
        });
        contentPane.add(backButton);

        // Create the search field
        searchField = new JTextField();
        searchField.setBounds(250, 800, 200, 30);
        contentPane.add(searchField);

        // Create the search button
        searchButton = new JButton("Cerca per nome");
        searchButton.setBounds(500, 800, 150, 30);
        searchButton.setBackground(new Color(255, 192, 203)); // Pink
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Fetch and display the client data based on the search query
                String query = searchField.getText();
                fetchClientData(query);
            }
        });
        contentPane.add(searchButton);

        // Create the show all button
        showAllButton = new JButton("Mostra tutti");
        showAllButton.setBounds(700, 800, 150, 30);
        showAllButton.setBackground(new Color(255, 192, 203)); // Pink
        showAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Fetch and display all client data
                fetchClientData(null);
            }
        });
        contentPane.add(showAllButton);

        // Fetch and display all client data initially
        fetchClientData(null);
    }

    private void fetchClientData(String query) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            stmt = con.createStatement();

            // Execute the SQL query to retrieve client data
            String sql;
            if (query != null && !query.isEmpty()) {
                sql = "SELECT * FROM cliente WHERE nome LIKE '%" + query + "%'";
            } else {
                sql = "SELECT * FROM cliente";
            }
            rs = stmt.executeQuery(sql);

            // Create the table model with column names and data
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("id");
            tableModel.addColumn("Nome");
            tableModel.addColumn("Cognome");
            tableModel.addColumn("Data di Nascita");
            tableModel.addColumn("Telefono");
            tableModel.addColumn("Email");
            tableModel.addColumn("Preferenze");

            while (rs.next()) {
                // Retrieve data from the result set
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String dataNascita = rs.getString("data_nascita");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                String preferenze = rs.getString("preferenze");

                // Add row to the table model
                tableModel.addRow(new Object[]{id, nome, cognome, dataNascita, telefono, email, preferenze});
            }

            // Set the table model to the table
            table.setModel(tableModel);

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
