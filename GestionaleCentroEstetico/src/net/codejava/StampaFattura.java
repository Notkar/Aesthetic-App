package net.codejava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

public class StampaFattura {
    private static JTextField nameField;
    private static JTextField surnameField;
    private static JTextField serviceNameField;
    private static JTextField dateField;
    private static JLabel nameLabel;
    private static JLabel surnameLabel;
    private static JLabel phoneLabel;
    private static JLabel emailLabel;
    private static JLabel serviceNameLabel;
    private static JLabel priceLabel;

    // Dati di connessione al database
    private static final String url = "jdbc:mysql://localhost:3306/centroestetico";
    private static final String username = "root";
    private static final String password = "BlaBlaBla24.";

    public StampaFattura() {
        // Creazione del frame
        JFrame frame = new JFrame("Pannello Fattura");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);

        // Creazione del pannello
        JPanel panel = new JPanel(new GridLayout(9, 2));
        panel.setBackground(Color.PINK);

        // Etichetta e campo per il nome del cliente
        nameLabel = new JLabel("Nome cliente:");
        nameField = new JTextField();

        // Etichetta e campo per il cognome del cliente
        surnameLabel = new JLabel("Cognome cliente:");
        surnameField = new JTextField();

        // Etichetta e campo per il nome del servizio
        serviceNameLabel = new JLabel("Nome servizio:");
        serviceNameField = new JTextField();

        // Etichetta e campo per la data della fattura
        JLabel dateLabel = new JLabel("Data fattura (AAAA-MM-GG):");
        dateField = new JTextField();

        // Etichette per il telefono, l'email e il prezzo del cliente
        JLabel phoneLabelTitle = new JLabel("Telefono cliente:");
        phoneLabel = new JLabel();
        JLabel emailLabelTitle = new JLabel("Email cliente:");
        emailLabel = new JLabel();
        JLabel priceLabelTitle = new JLabel("Prezzo servizio:");
        priceLabel = new JLabel();

        // Bottone per ottenere le informazioni del cliente
        JButton getInfoButton = new JButton("Ottieni informazioni");
        getInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCustomerInfo();
            }
        });

        // Bottone per inviare i dati alla tabella "fattura"
        JButton sendButton = new JButton("Invia dati");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendInvoiceData();
                Fattura fattura = new Fattura();
                fattura.generaFattura();
            }
        });
        
        // Bottone per tornare al menu
        JButton backButton = new JButton("Torna al menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Chiudi il frame corrente
                new MenuDipendente();
            }
        });

        // Aggiunta degli elementi al pannello
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(surnameLabel);
        panel.add(surnameField);
        panel.add(serviceNameLabel);
        panel.add(serviceNameField);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(phoneLabelTitle);
        panel.add(phoneLabel);
        panel.add(emailLabelTitle);
        panel.add(emailLabel);
        panel.add(priceLabelTitle);
        panel.add(priceLabel);
        panel.add(getInfoButton);
        panel.add(sendButton);
        panel.add(backButton);

        // Aggiunta del pannello al frame
        frame.getContentPane().add(panel);

        // Visualizzazione del frame
        frame.setVisible(true);
    }

    private static void getCustomerInfo() {
        String nomeCliente = nameField.getText();
        String cognomeCliente = surnameField.getText();

        // Connessione al database e recupero delle informazioni del cliente
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Query per recuperare il telefono e l'email del cliente
            String queryCliente = "SELECT telefono, email FROM cliente WHERE nome = ? AND cognome = ?";
            PreparedStatement statementCliente = conn.prepareStatement(queryCliente);
            statementCliente.setString(1, nomeCliente);
            statementCliente.setString(2, cognomeCliente);

            // Esecuzione della query del cliente
            ResultSet resultSetCliente = statementCliente.executeQuery();

            // Controllo se sono presenti risultati del cliente
            if (resultSetCliente.next()) {
                String telefonoCliente = resultSetCliente.getString("telefono");
                String emailCliente = resultSetCliente.getString("email");

                phoneLabel.setText(telefonoCliente);
                emailLabel.setText(emailCliente);
            } else {
                phoneLabel.setText("N/A");
                emailLabel.setText("N/A");
            }

            // Chiusura delle risorse del cliente
            resultSetCliente.close();
            statementCliente.close();

            // Recupero del prezzo del servizio
            String nomeServizio = serviceNameField.getText();
            String queryServizio = "SELECT prezzo FROM servizio WHERE nome = ?";
            PreparedStatement statementServizio = conn.prepareStatement(queryServizio);
            statementServizio.setString(1, nomeServizio);

            // Esecuzione della query del servizio
            ResultSet resultSetServizio = statementServizio.executeQuery();

            // Controllo se sono presenti risultati del servizio
            if (resultSetServizio.next()) {
                double prezzoServizio = resultSetServizio.getDouble("prezzo");
                priceLabel.setText(Double.toString(prezzoServizio));
            }

            // Chiusura delle risorse del servizio
            resultSetServizio.close();
            statementServizio.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void sendInvoiceData() {
        String nomeCliente = nameField.getText();
        String cognomeCliente = surnameField.getText();
        String telefonoCliente = phoneLabel.getText();
        String emailCliente = emailLabel.getText();
        String nomeServizio = serviceNameField.getText();
        double prezzoServizio = Double.parseDouble(priceLabel.getText());
        String dataFattura = dateField.getText();

        // Connessione al database e inserimento dei dati nella tabella "fattura"
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Query per inserire i dati nella tabella "fattura"
            String insertQuery = "INSERT INTO fattura (nome_cliente, cognome_cliente, telefono_cliente, email_cliente, nome_servizio, prezzo_servizio, data_fattura) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(insertQuery);
            statement.setString(1, nomeCliente);
            statement.setString(2, cognomeCliente);
            statement.setString(3, telefonoCliente);
            statement.setString(4, emailCliente);
            statement.setString(5, nomeServizio);
            statement.setDouble(6, prezzoServizio);
            statement.setString(7, dataFattura);

            // Esecuzione della query di inserimento
            statement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Dati inviati correttamente alla tabella 'fattura'");

            // Chiusura delle risorse
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore durante l'invio dei dati alla tabella 'fattura'");
        }
    }
}
