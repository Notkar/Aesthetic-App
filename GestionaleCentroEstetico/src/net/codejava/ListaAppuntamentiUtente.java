package net.codejava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ListaAppuntamentiUtente extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/centroestetico";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "BlaBlaBla24.";
    private static int id;
    private JTable table;
    private String telefonoC;
    private JTextField txtNumeroAppuntamento;
    

    public ListaAppuntamentiUtente(int id) {
        this.id = id;
        initializeUI();
        fetchAppointmentsFromDatabase();
    }

    private void initializeUI() {
        setTitle("Lista Appuntamenti Utente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 1920);
        setLayout(new BorderLayout());
        

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        JLabel lblNumeroAppuntamento = new JLabel("ID Numero Appuntamento da Cancellare:");
        txtNumeroAppuntamento = new JTextField(10);
        JButton btnCancellaAppuntamento = new JButton("Rimuovi");
        JButton tornaAlMenu = new JButton("Torna al menu");
        
        tornaAlMenu.setFont(new Font("Verdana", Font.PLAIN, 20));
		//pulsanteTornaAlMenu.setPreferredSize(new Dimension(200, 80));
		tornaAlMenu.setForeground(Color.pink);
		tornaAlMenu.setBackground(Color.white);

		tornaAlMenu.addActionListener(e -> {
		    new MenuUtente(id);
		    dispose();
		});

        
        btnCancellaAppuntamento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int numeroAppuntamento = Integer.parseInt(txtNumeroAppuntamento.getText());
                if (verifyAppointment(id, numeroAppuntamento)) {
                    deleteAppointment(numeroAppuntamento);
                    fetchAppointmentsFromDatabase();
                } else {
                    JOptionPane.showMessageDialog(ListaAppuntamentiUtente.this, "Il numero di appuntamento non corrisponde.");
                }
            }
        });
        bottomPanel.add(tornaAlMenu);
        bottomPanel.add(lblNumeroAppuntamento);
        bottomPanel.add(txtNumeroAppuntamento);
        bottomPanel.add(btnCancellaAppuntamento);

        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private String getTelefonoC(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM cliente WHERE id = ?")) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    telefonoC = resultSet.getString("telefono");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return telefonoC;
        
    }

    private void fetchAppointmentsFromDatabase() {
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM appuntamento WHERE data >= CURDATE() AND telefono_cliente = ? ORDER BY data ASC";
            PreparedStatement statement = conn.prepareStatement(query);
            telefonoC = getTelefonoC(id);
            statement.setString(1, telefonoC);
            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Data");
            tableModel.addColumn("Ora");
            tableModel.addColumn("Dipendente");
            tableModel.addColumn("Servizio");

            while (resultSet.next()) {
            	int ida =resultSet.getInt("id");
                String data = resultSet.getDate("data").toString();
                String ora = resultSet.getTime("ora").toString();
                String nomeDipendente = resultSet.getString("nome_dipendente");
                String nomeServizio = resultSet.getString("nome_servizio");

                Object[] rowData = { ida, data, ora, nomeDipendente, nomeServizio };
                tableModel.addRow(rowData);
            }

            table.setModel(tableModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private boolean verifyAppointment(int id, int numeroAppuntamento) {
        String telefonoCliente = getTelefonoC(id);
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM cliente WHERE id = ? AND telefono_cliente = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, numeroAppuntamento);
            statement.setString(2, telefonoCliente);
            ResultSet resultSet = statement.executeQuery();
            
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    private void deleteAppointment(int numeroAppuntamento) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "DELETE FROM appuntamento WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, numeroAppuntamento);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}