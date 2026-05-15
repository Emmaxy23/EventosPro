package com.eventos.utilidades;

import com.eventos.modelo.entidad.Factura;
import com.eventos.modelo.entidad.FacturaDetalle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.OutputStream;

/**
 * Clase de utilidad para la generación de documentos PDF.
 * Utiliza la librería OpenPDF (iText) para construir la estructura de la factura.
 */
public class PdfGenerador {

    /**
     * Genera un archivo PDF con los detalles de una factura y lo escribe en el flujo de salida.
     * @param factura Objeto con los datos de la factura a imprimir.
     * @param out Flujo de salida del navegador (Response Stream).
     */
    public static void generarFacturaPdf(Factura factura, OutputStream out) throws Exception {

        // 1. Configuración del documento (Tamaño A4 y márgenes)
        Document document = new Document(PageSize.A4, 40, 40, 50, 50);
        PdfWriter.getInstance(document, out);
        document.open(); // Inicia la escritura del PDF

        // 2. Definición de fuentes para el texto
        Font titulo = new Font(Font.HELVETICA, 18, Font.BOLD);
        Font subtitulo = new Font(Font.HELVETICA, 12, Font.BOLD);
        Font normal = new Font(Font.HELVETICA, 11);
        Font negrita = new Font(Font.HELVETICA, 11, Font.BOLD);

        // 3. Creación del Encabezado (Logo + Datos de Empresa)
        PdfPTable header = new PdfPTable(2);
        header.setWidthPercentage(100);
        header.setWidths(new int[]{1, 3}); // Proporción de columnas

        // Intento de cargar el logo desde los recursos estáticos
        try {
            Image imagen = Image.getInstance(
                    PdfGenerador.class.getClassLoader().getResource("static/img/logo.png")
            );
            imagen.scaleToFit(80, 80);
            PdfPCell logo = new PdfPCell(imagen);
            logo.setBorder(Rectangle.NO_BORDER);
            logo.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.addCell(logo);
        } catch (Exception e) {
            // Si el logo no carga, se añade una celda vacía o mensaje
            header.addCell(celdaSinBorde("LOGO", negrita));
        }

        // Datos legales de la empresa
        PdfPCell datosEmpresa = new PdfPCell();
        datosEmpresa.setBorder(Rectangle.NO_BORDER);
        datosEmpresa.addElement(new Paragraph("EVENTOS S.A.S", subtitulo));
        datosEmpresa.addElement(new Paragraph("NIT: 123456789", normal));
        datosEmpresa.addElement(new Paragraph("Tel: 3000000000", normal));
        header.addCell(datosEmpresa);

        document.add(header);
        document.add(new Paragraph(" ")); // Espacio en blanco

        // 4. Título Principal
        Paragraph tituloFactura = new Paragraph("COMPROBANTE DE PAGO / FACTURA", titulo);
        tituloFactura.setAlignment(Element.ALIGN_CENTER);
        document.add(tituloFactura);
        document.add(new Paragraph(" "));

        // 5. Información del Cliente y Factura
        PdfPTable info = new PdfPTable(2);
        info.setWidthPercentage(100);

        info.addCell(celdaSinBorde("Factura N°:", negrita));
        info.addCell(celdaSinBorde(String.valueOf(factura.getIdFactura()), normal));

        info.addCell(celdaSinBorde("Fecha de Emisión:", negrita));
        info.addCell(celdaSinBorde(String.valueOf(factura.getFecha()), normal));

        info.addCell(celdaSinBorde("Cliente:", negrita));
        info.addCell(celdaSinBorde(factura.getCliente(), normal));

        document.add(info);
        document.add(new Paragraph(" "));

        // 6. Tabla de Detalles (Productos/Servicios)
        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);

        // Encabezados de la tabla
        String[] headers = {"Descripción", "Cant.", "Precio Unit.", "Subtotal"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, negrita));
            cell.setBackgroundColor(new java.awt.Color(220, 220, 220));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            tabla.addCell(cell);
        }

        // Filas con los datos de los ítems de la factura
        for (FacturaDetalle item : factura.getItems()) {
            tabla.addCell(new Phrase(item.getProducto(), normal));
            tabla.addCell(new Phrase(String.valueOf(item.getCantidad()), normal));
            tabla.addCell(new Phrase("$ " + item.getPrecio(), normal));
            tabla.addCell(new Phrase("$ " + item.getSubtotal(), normal));
        }

        document.add(tabla);
        document.add(new Paragraph(" "));

        // 7. Resumen de Totales
        PdfPTable totales = new PdfPTable(2);
        totales.setWidthPercentage(40);
        totales.setHorizontalAlignment(Element.ALIGN_RIGHT);

        totales.addCell(celdaSinBorde("Subtotal:", negrita));
        totales.addCell(celdaSinBorde("$ " + factura.getSubtotal(), normal));

        totales.addCell(celdaSinBorde("IVA (19%):", negrita));
        totales.addCell(celdaSinBorde("$ " + factura.getIva(), normal));

        totales.addCell(celdaSinBorde("TOTAL A PAGAR:", negrita));
        totales.addCell(celdaSinBorde("$ " + factura.getTotal(), negrita));

        document.add(totales);

        // 8. Mensaje Final
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        Paragraph gracias = new Paragraph("¡Gracias por confiar en EventosPro!", subtitulo);
        gracias.setAlignment(Element.ALIGN_CENTER);
        document.add(gracias);

        document.close(); // Finaliza y guarda el documento
    }

    /**
     * Método auxiliar para crear celdas de tabla sin bordes visibles.
     */
    private static PdfPCell celdaSinBorde(String texto, Font fuente) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, fuente));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        return cell;
    }
}