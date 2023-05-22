package net.codejava;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class FatturaListaProprietario extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost/centroestetico";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "BlaBlaBla24.";

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idField;

    public FatturaListaProprietario() {
        setTitle("Lista Fatture Proprietario");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome Cliente");
        tableModel.addColumn("Cognome Cliente");
        tableModel.addColumn("Telefono Cliente");
        tableModel.addColumn("Email Cliente");
        tableModel.addColumn("Nome Servizio");
        tableModel.addColumn("Prezzo Servizio");
        tableModel.addColumn("Data Fattura");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.CENTER);
        
               

        JButton deleteButton = new JButton("Elimina");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteFattura();
            }
        });

        JButton printButton = new JButton("Stampa");
        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printFattura();
            }
        });

        JButton backButton = new JButton("Torna al Menu");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuProprietario();
            }
        });
        
     
        // Create the "Delete" button
        
        deleteButton.setBounds(260, 400, 100, 30);
        deleteButton.setBackground(Color.PINK); // Pink
        

        // Create the "Print" button
        
        printButton.setBounds(370, 400, 100, 30);
        printButton.setBackground(Color.PINK); // Pink
        

        // Create the "Back to Menu" button
        
        backButton.setBounds(480, 400, 150, 30);
        backButton.setBackground(Color.PINK); // Pink
        
        
        

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(backButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(printButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JLabel idLabel = new JLabel("ID Fattura:");
        idField = new JTextField(10);

        JPanel idPanel = new JPanel(new FlowLayout());
        idPanel.add(idLabel);
        idPanel.add(idField);
        panel.add(idPanel, BorderLayout.NORTH);

        add(panel);

        loadFatture();
        setVisible(true);
    }

    private void loadFatture() {
        tableModel.setRowCount(0);

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM fattura ORDER BY data_fattura DESC");

            while (rs.next()) {
                Object[] row = new Object[8];
                row[0] = rs.getString("id");
                row[1] = rs.getString("nome_cliente");
                row[2] = rs.getString("cognome_cliente");
                row[3] = rs.getString("telefono_cliente");
                row[4] = rs.getString("email_cliente");
                row[5] = rs.getString("nome_servizio");
                row[6] = rs.getFloat("prezzo_servizio");
                row[7] = rs.getDate("data_fattura").toString();
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteFattura() {
        String id = idField.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inserisci l'ID della fattura da eliminare", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler eliminare la fattura?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Connection con = null;
            PreparedStatement stmt = null;

            try {
                con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                stmt = con.prepareStatement("DELETE FROM fattura WHERE id = ?");
                stmt.setString(1, id);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Fattura eliminata con successo");
                    loadFatture();
                } else {
                    JOptionPane.showMessageDialog(this, "Nessuna fattura trovata con l'ID specificato", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null)
                        stmt.close();
                    if (con != null)
                        con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void printFattura() {
        String id = idField.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inserisci l'ID della fattura da stampare", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            stmt = con.prepareStatement("SELECT * FROM fattura WHERE id = ?");
            stmt.setString(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String nomeCliente = rs.getString("nome_cliente");
                String cognomeCliente = rs.getString("cognome_cliente");
                String telefonoCliente = rs.getString("telefono_cliente");
                String emailCliente = rs.getString("email_cliente");
                String nomeServizio = rs.getString("nome_servizio");
                float prezzoServizio = rs.getFloat("prezzo_servizio");
                String dataFattura = rs.getDate("data_fattura").toString();

                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Fattura di " + nomeCliente + " " + cognomeCliente);
                contentStream.newLine();
                contentStream.showText("Telefono: " + telefonoCliente);
                contentStream.newLine();
                contentStream.showText("Email: " + emailCliente);
                contentStream.newLine();
                contentStream.showText("Servizio usufruito: " + nomeServizio);
                contentStream.newLine();
                contentStream.showText("Prezzo Servizio: " + prezzoServizio);
                contentStream.newLine();
                contentStream.showText("Data Fattura: " + dataFattura);
                contentStream.endText();
                contentStream.close();

                String filePath = "C:\\Users\\Mike\\Desktop\\Fattura_id" + id + "_" + nomeCliente + "_" + cognomeCliente + "_" + dataFattura +".pdf";
                document.save(filePath);
                document.close();

                JOptionPane.showMessageDialog(this, "Fattura stampata con successo");
            } else {
                JOptionPane.showMessageDialog(this, "Nessuna fattura trovata con l'ID specificato", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
}
