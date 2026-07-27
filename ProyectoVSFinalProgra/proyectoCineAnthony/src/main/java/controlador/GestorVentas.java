package controlador;

import modelo.entidad.*;
import java.util.List;

public class GestorVentas {
    private ControladorTicket controladorTicket; 

    public GestorVentas() {
        this.controladorTicket = new ControladorTicket();
    }

    public Boleto procesarCompra(String tituloPelicula, String funcionInfo, String salaInfo, String asientosInfo, IPago pasarelaPago, double montoTotal) {
        
        boolean pagoExitoso = pasarelaPago.pagar(montoTotal);
        
        if (pagoExitoso) {
            String idBoleto = controladorTicket.generarIdBoleto(tituloPelicula);
            String metodoPago = pasarelaPago.obtenerNombreMetodo();
            controladorTicket.registrarYProcesarBoleto(idBoleto, tituloPelicula, funcionInfo, salaInfo, asientosInfo, metodoPago, montoTotal);
 
            return new Boleto(idBoleto, tituloPelicula, funcionInfo, salaInfo, asientosInfo, metodoPago, montoTotal);
        }
        
        return null;
    }
}