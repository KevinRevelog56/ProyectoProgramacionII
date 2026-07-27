package controlador;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import modelo.entidad.Boleto;
import modelo.entidad.IPago;
import modelo.entidad.PagoEfectivo;
import modelo.entidad.PagoTarjeta;
import modelo.entidad.Pelicula;

public class ControladorPago {

    public double[] calcularTotales(Pelicula pelicula, int cantidadAsientos) {
        double precioPorBoleto = (pelicula != null) ? pelicula.getPrecio() : 0.0;
        double subtotal = cantidadAsientos * precioPorBoleto;
        double iva = subtotal * 0.15; // 15% de IVA
        double total = subtotal + iva;
        return new double[]{subtotal, iva, total};
    }

    // Validar campos de la tarjeta
    public boolean validarTarjeta(String numero, String vencimiento, String cvv, java.awt.Component parentComponent) {
        if (numero.length() != 16) {
            JOptionPane.showMessageDialog(parentComponent, "La tarjeta debe tener 16 dígitos.");
            return false;
        }
        if (cvv.length() != 3) {
            JOptionPane.showMessageDialog(parentComponent, "El CVV debe tener 3 dígitos.");
            return false;
        }
        if (vencimiento.length() != 5 || !vencimiento.contains("/")) {
            JOptionPane.showMessageDialog(parentComponent, "La fecha debe tener formato MM/AA (ej: 12/25).");
            return false;
        }
        return true;
    }

    //procesa pago en Mostrador
    public Boleto procesarPagoMostrador(String tituloPelicula, String funcionInfo, String salaInfo, String asientosInfo, double montoTotal) {
        IPago metodoPago = new PagoEfectivo();
        GestorVentas gestorVentas = new GestorVentas();
        return gestorVentas.procesarCompra(tituloPelicula, funcionInfo, salaInfo, asientosInfo, metodoPago, montoTotal);
    }

    //procesa el pago con Tarjeta
    public Boleto procesarPagoTarjeta(String tituloPelicula, String funcionInfo, String salaInfo, String asientosInfo, double montoTotal) {
        IPago metodoPago = new PagoTarjeta();
        GestorVentas gestorVentas = new GestorVentas();
        return gestorVentas.procesarCompra(tituloPelicula, funcionInfo, salaInfo, asientosInfo, metodoPago, montoTotal);
    }

    //Generaciond deventana emergente con el Ticket
    public void mostrarTicketEntrada(java.awt.Component parentComponent, String subtituloTipo, String pelicula, String funcion, String sala, String asientos) {
        try {
            String tresPelis = pelicula.length() >= 3 ? pelicula.substring(0, 3).toUpperCase() : "CIN";
            String uuidUnico = java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
            String idEntrada = tresPelis + "-" + uuidUnico;

            JTextPane paneBoleto = new JTextPane();
            paneBoleto.setContentType("text/html");
            paneBoleto.setEditable(false);

            String htmlTicket = "<html>"
                    + "<head>"
                    + "<style>"
                    + "  body { font-family: sans-serif; width: 320px; padding: 20px; background-color: #f9f9f9; }"
                    + "  .ticket { border: 2px dashed #333; padding: 10px; background-color: #fff; }"
                    + "  h2 { text-align: center; color: #d9534f; margin-bottom: 0; }"
                    + "  .cine { text-align: center; font-size: 12px; color: #555; }"
                    + "  .id-ticket { text-align: center; font-size: 13px; font-weight: bold; background-color: #f1f1f1; color: #333; padding: 5px; margin-top: 8px; border: 1px solid #ddd; }"
                    + "  .detalles { font-size: 12px; margin-top: 10px; }"
                    + "  .asientos { font-weight: bold; font-size: 12px; }"
                    + "  .footer { text-align: center; font-size: 11px; margin-top: 15px; color: #777; }"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<div class='ticket'>"
                    + "  <h2>CINEBOX</h2>"
                    + "  <div class='cine'>Entrada Oficial (" + subtituloTipo + ")</div>"
                    + "  <div class='id-ticket'>ID: " + idEntrada + "</div>"
                    + "  <hr>"
                    + "  <div class='detalles'>"
                    + "    <p><b>Película:</b> " + pelicula + "</p>"
                    + "    <p><b>Función:</b> " + funcion + "</p>"
                    + "    <p><b>Sala:</b> " + sala + "</p>"
                    + "    <p class='asientos'>Asientos: " + asientos + "</p>"
                    + "  </div>"
                    + "  <hr>"
                    + "  <div class='footer'>¡Disfruta tu función!</div>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            paneBoleto.setText(htmlTicket);

            JDialog ventanaTicket = new JDialog((java.awt.Frame) null, "Tu Entrada - CINEBOX", true);
            ventanaTicket.setLayout(new BorderLayout());
            ventanaTicket.add(new JScrollPane(paneBoleto), BorderLayout.CENTER);

            JButton btnAceptar = new JButton("Aceptar");
            btnAceptar.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btnAceptar.addActionListener(e -> ventanaTicket.dispose());

            JPanel pnlBoton = new JPanel();
            pnlBoton.setBorder(EmptyBorder.class.getDeclaredConstructor(int.class, int.class, int.class, int.class).newInstance(10, 10, 10, 10));
            pnlBoton.add(btnAceptar);

            ventanaTicket.add(pnlBoton, BorderLayout.SOUTH);
            ventanaTicket.setSize(470, 540);
            ventanaTicket.setLocationRelativeTo(parentComponent);
            ventanaTicket.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(parentComponent, "Error al mostrar la entrada: " + e.getMessage());
        }
    }
    public void regresarACartelera(javax.swing.JPanel panelActual) {
    vista.VistaPrincipal principal = (vista.VistaPrincipal) javax.swing.SwingUtilities.getWindowAncestor(panelActual);
    if (principal != null) {
        principal.limpiarPantallaDetalle();
        principal.cambiarPantalla("pantallaCartelera");
    }
}
}