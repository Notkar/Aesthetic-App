package net.codejava;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Fattura {

    private static final String DB_URL = "jdbc:mysql://localhost/centroestetico";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "BlaBlaBla24.";

    public void generaFattura() {
        Connection con = null;
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM fattura";

        try {
            con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery("SELECT * FROM fattura ORDER BY id DESC LIMIT 1");

            while (rs.next()) {

                String nomeCliente = rs.getString("nome_cliente");
                String cognomeCliente = rs.getString("cognome_cliente");
                String telefonoCliente = rs.getString("telefono_cliente");
                String emailCliente = rs.getString("email_cliente");
                String nomeServizio = rs.getString("nome_servizio");
                float prezzoServizio = rs.getFloat("prezzo_servizio");
                Date dataFattura = rs.getDate("data_fattura");

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

                String filePath = "C:\\Users\\Mike\\Desktop\\Fattura_" + nomeCliente + "_" + cognomeCliente + "_" + dataFattura +".pdf";
                document.save(filePath);
                document.close();
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Fattura fattura = new Fattura();
        fattura.generaFattura();
    }
}
