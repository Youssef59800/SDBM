package fr.fs.sdbm;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import fr.fs.sdbm.metier.Ticket;


import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TicketPDF {
    private Ticket ticket;
    private PdfDocument pdfDocument;
    private Document document;
    private Table table;
    private PdfWriter pdfWriter;
    private Image image;
    private BigDecimal totalHT = new BigDecimal(0);
    private static final int PADDING = 2;
    private static final int HEIGHT = 16;
    private static final Color EVEN = ColorConstants.LIGHT_GRAY;
    private static final Color ODD = ColorConstants.GRAY;
    private static final Color HEADER = ColorConstants.DARK_GRAY;
    private static final int LIGNES_PAR_PAGE = 25;


    public TicketPDF(Ticket ticket) throws Exception {
        this.ticket = ticket;

        // Création d'une instance de PDFWriter
        String nomFichierPDF = "C:\\Users\\33782\\Desktop\\tickets" + ticket.toString() + ".pdf";
        pdfWriter = new PdfWriter(nomFichierPDF);
        pdfDocument = new PdfDocument(pdfWriter);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        document = new Document(pdfDocument);
        document.setFont(font);
        String logoFile = "W:/logo.png";
        ImageData imageData = ImageDataFactory.create(logoFile);
        image = new Image(imageData);
        image.setHeight(80);
        image.setFixedPosition(20, 740);
    }


    /*public void imprime() {
        for (int i = 0; i < ticket.getLignes().size(); i++) {
            Vendre ligne = ticket.getLignes().get(i);
            if (i == 0) header();
            else if (i % LIGNES_PAR_PAGE == 0) addNewPage();
            columnContent(ligne.getArticle().getId().toString(), i, TextAlignment.RIGHT);
            columnContent(ligne.getArticle().getLibelle(), i, TextAlignment.LEFT);
            columnContent(ligne.getPrixVente().toString(), i, TextAlignment.RIGHT);
            columnContent(ligne.getQuantité().toString(), i, TextAlignment.RIGHT);
            BigDecimal totalLigne = ligne.getPrixVente().multiply(new BigDecimal(ligne.getQuantité())).setScale(2);
            totalHT = totalHT.add(totalLigne);
            columnContent(totalLigne.toString(), i, TextAlignment.RIGHT);
        }
        totalTicket();
    }*/

    private void header() {

        title("Ticket n° " + ticket.getId(), 24);
        title("Date  : " + ticket.getDate(), 16);
        title("Heure : " + ticket.getHeure(), 16);
        document.add(image);
        float[] pointColumnWidths = {50, 250, 70, 70, 100};
        table = new Table(pointColumnWidths);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setMarginTop(30);
        columnHeader("Code");
        columnHeader("Désignation");
        columnHeader("Prix Unitaire");
        columnHeader("Quantité");
        columnHeader("Total");

    }

    private void title(String texte, int fontSize) {
        Paragraph paragraph = new Paragraph(texte);
        paragraph.setFontSize(fontSize);
        paragraph.setFontColor(ODD);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        document.add(paragraph);
    }

    public void columnHeader(String text) {
        Cell cell = newCell(text);
        cell.setBackgroundColor(HEADER);
        cell.setFontColor(ColorConstants.WHITE);
        cell.setTextAlignment(TextAlignment.CENTER);
        table.addCell(cell);
    }

    public Cell newCell(String text) {
        Cell cell = new Cell();
        cell.setHeight(HEIGHT);
        cell.setPadding(PADDING);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add(new Paragraph(text));
        return cell;
    }

    public void columnContent(String text, int parity, TextAlignment textAlignment) {
        Cell cell = newCell(text);
        if (parity % 2 == 0) cell.setBackgroundColor(EVEN);
        else cell.setBackgroundColor(ODD);
        cell.setTextAlignment(textAlignment);
        table.addCell(cell);
    }

    private void totalTicket() {
        noCells(3);
        table.addCell(totalLibCell("Total H.T.", ColorConstants.WHITE));
        table.addCell(totalLibCell(totalHT.toString(), ColorConstants.WHITE));
        noCells(3);
        BigDecimal totalTVA = totalHT.multiply(BigDecimal.valueOf(0.2)).setScale(2, RoundingMode.HALF_EVEN);
        table.addCell(totalLibCell("T.V.A.", ColorConstants.WHITE));
        table.addCell(totalLibCell(totalTVA.toString(), ColorConstants.WHITE));
        noCells(3);
        table.addCell(totalLibCell("Net à payer", ODD));
        table.addCell(totalLibCell(totalHT.add(totalTVA).toString(), ODD));
        document.add(table);
        footer();
        pdfDocument.close();
    }

    public Cell totalLibCell(String text, Color color) {
        Cell cell = newCell(text);
        cell.setTextAlignment(TextAlignment.RIGHT);
        cell.setBackgroundColor(color);
        cell.setHeight(HEIGHT + 4F);
        return cell;
    }

    public void noCells(int nombre) {
        for (int i = 0; i < nombre; i++)
            table.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
    }

    private void addNewPage() {
        noCells(4);
        table.addCell(totalLibCell(". . . / . . .", ColorConstants.WHITE).setTextAlignment(TextAlignment.CENTER));
        document.add(table);
        footer();
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        header();
    }

    private void footer() {
        // Largeur de page 0 à 599
        // bottom = 0 => bas de page ; bottom = 824 => haut de page
        Paragraph paragraph = new Paragraph("S.D.B.M. vous remercie de votre achat.");
        paragraph.setTextAlignment(TextAlignment.CENTER);
        paragraph.setFixedPosition(0, 20, 599);
        document.add(paragraph);
    }


}
