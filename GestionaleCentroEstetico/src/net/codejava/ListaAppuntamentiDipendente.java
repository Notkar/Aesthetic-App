package net.codejava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ListaAppuntamentiDipendente extends JFrame {
    private JTable table;
    private JTextField deleteTextField;
    private JButton deleteButton;

    public ListaAppuntamentiDipendente() {
        initializeUI();
        fetchAppointmentsFromDatabase();
    }

    private void initializeUI() {
        setTitle("Lista Appuntamenti");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLayout(new BorderLayout());

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        deleteTextField = new JTextField(10);
        deleteButton = new JButton("Cancella Appuntamento");
        
        JButton tornaAlMenu = new JButton("Torna al menu");
        
        tornaAlMenu.setFont(new Font("Verdana", Font.PLAIN, 20));
		//pulsanteTornaAlMenu.setPreferredSize(new Dimension(200, 80));
		tornaAlMenu.setForeground(Color.pink);
		tornaAlMenu.setBackground(Color.white);

		tornaAlMenu.addActionListener(e -> {
		    new MenuDipendente();
		    dispose();
		});
        
        
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteAppointment();
            }
        });
        
        bottomPanel.add(tornaAlMenu);
        bottomPanel.add(new JLabel("Numero Appuntamento:"));
        bottomPanel.add(deleteTextField);
        bottomPanel.add(deleteButton);
        
        

        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void fetchAppointmentsFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/centroestetico";
        String username = "root";
        String password = "BlaBlaBla24.";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM appuntamento WHERE data >= CURDATE() ORDER BY data ASC";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Numero ID");
            tableModel.addColumn("Data");
            tableModel.addColumn("Ora");
            tableModel.addColumn("Dipendente");
            tableModel.addColumn("Servizio");
            tableModel.addColumn("Nome");
            tableModel.addColumn("Cognome");
            tableModel.addColumn("Telefono");
            tableModel.addColumn("Preferenze");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String data = resultSet.getDate("data").toString();
                String ora = resultSet.getTime("ora").toString();
                String nomeDipendente = resultSet.getString("nome_dipendente");
                String nomeServizio = resultSet.getString("nome_servizio");
                String nomeCliente = resultSet.getString("nome_cliente");
                String cognomeCliente = resultSet.getString("cognome_cliente");
                String numeroTelefonoCliente = resultSet.getString("telefono_cliente");
                String preferenzeCliente = resultSet.getString("preferenze_cliente");

                Object[] rowData = {id, data, ora, nomeDipendente, nomeServizio, nomeCliente, cognomeCliente, numeroTelefonoCliente, preferenzeCliente};
                tableModel.addRow(rowData);
            }

            table.setModel(tableModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteAppointment() {
        String url = "jdbc:mysql://localhost:3306/centroestetico";
        String username = "root";
        String password = "BlaBlaBla24.";
        String deleteID = deleteTextField.getText();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String deleteQuery = "DELETE FROM appuntamento WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(deleteQuery);
            statement.setString(1, deleteID);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Appuntamento cancellato con successo");
                fetchAppointmentsFromDatabase();
            } else {
                JOptionPane.showMessageDialog(this, "Nessun appuntamento trovato con l'ID specificato");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}
