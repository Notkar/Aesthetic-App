package net.codejava;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

public class PrenotazioneAppuntamento extends JFrame {
    private static int id;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/centroestetico";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "BlaBlaBla24.";
    private JComboBox<String> dipendentiComboBox;
    private JComboBox<String> serviziComboBox;
    private JDateChooser dateChooser;
    private JSpinner timeSpinner;
    private JButton confirmButton, menuButton;
    private String nomeC;
    private String cognomeC;
    private String telefonoC;
    private String preferenzeC;

    public PrenotazioneAppuntamento(int id) {
        this.id = id;

        // Imposta il layout del pannello
        setLayout(new BorderLayout());

        // Crea i componenti
        JLabel dipendentiLabel = createFormattedLabel("Seleziona Dipendente:");
        JLabel serviziLabel = createFormattedLabel("Seleziona Servizio:");
        JLabel dataLabel = createFormattedLabel("Seleziona Data:");
        JLabel oraLabel = createFormattedLabel("Seleziona Orario:");
        confirmButton = new JButton("Conferma");
        menuButton = new JButton("Menu");

        dipendentiComboBox = new JComboBox<>();
        serviziComboBox = new JComboBox<>();
        dateChooser = new JDateChooser();
        timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);

        // Carica i dipendenti dal database
        loadDipendenti();

        // Carica i servizi dal database
        loadServizi();
        

        // Aggiungi i componenti al pannello
        JPanel panel = new JPanel(new GridLayout(5, 2));
        setSize(1080, 1920);
        setBackground(Color.pink);
        setTitle("Prenotazione Appuntamento");
        panel.add(dipendentiLabel);
        panel.add(dipendentiComboBox);
        panel.add(serviziLabel);
        panel.add(serviziComboBox);
        panel.add(dataLabel);
        panel.add(dateChooser);
        panel.add(oraLabel);
        panel.add(timeSpinner);
        panel.add(menuButton);
        panel.add(confirmButton);

        
        add(panel);
        
        
        setVisible(true);
        
        

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmAppointment(id);
            }
        });
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	dispose();
                new MenuUtente(id);
                
                
            }
        });
    }

    private String getNomeC(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM cliente WHERE id = ?")) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    nomeC = resultSet.getString("nome");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nomeC;
    }

    private String getCognomeC(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM cliente WHERE id = ?")) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cognomeC = resultSet.getString("cognome");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cognomeC;
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

    private String getPreferenzeC(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM cliente WHERE id = ?")) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    preferenzeC = resultSet.getString("preferenze");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return preferenzeC;
    }

    private void loadDipendenti() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement statement = connection.prepareStatement("SELECT nome FROM dipendente");
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String nomeDipendente = resultSet.getString("nome");
                dipendentiComboBox.addItem(nomeDipendente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadServizi() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement statement = connection.prepareStatement("SELECT nome FROM servizio");
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String nomeServizio = resultSet.getString("nome");
                serviziComboBox.addItem(nomeServizio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void confirmAppointment(int id) {
        String selectedDipendente = (String) dipendentiComboBox.getSelectedItem();
        String selectedServizio = (String) serviziComboBox.getSelectedItem();
        Date selectedDate = dateChooser.getDate();
        Date selectedTime = (Date) timeSpinner.getValue();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            // Insert the appointment details into the appuntamento table
            String query = "INSERT INTO appuntamento (nome_dipendente, nome_servizio, data, ora, nome_cliente, cognome_cliente, telefono_cliente, preferenze_cliente) VALUES (?, ?, ?, ?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, selectedDipendente);
            statement.setString(2, selectedServizio);
            statement.setDate(3, new java.sql.Date(selectedDate.getTime()));
            statement.setTime(4, new java.sql.Time(selectedTime.getTime()));
            statement.setString(5, getNomeC(id));
            statement.setString(6, getCognomeC(id));
            statement.setString(7, getTelefonoC(id));
            statement.setString(8, getPreferenzeC(id));

            statement.executeUpdate();

            JOptionPane.showMessageDialog(PrenotazioneAppuntamento.this, "Appuntamento confermato!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(PrenotazioneAppuntamento.this,
                    "Error saving the appointment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private JLabel createFormattedLabel(String labelText) {
        JLabel label = new JLabel(labelText, JLabel.CENTER);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Verdana", Font.PLAIN, 20));
        label.setBorder(new LineBorder(Color.PINK, 1));
        return label;
    }    
}