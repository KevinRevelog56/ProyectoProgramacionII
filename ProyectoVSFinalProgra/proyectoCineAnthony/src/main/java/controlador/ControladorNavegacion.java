package controlador;

import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JPanel;
import vista.VistaDetalles;
import vista.VistaPago;
import vista.VistaSeleccionAsientos;
import vista.VistaCartelera;

public class ControladorNavegacion {

    public String procesarRetroceso(JPanel contenedorPrincipal) {
        Component panelActivo = null;
        for (Component comp : contenedorPrincipal.getComponents()) {
            if (comp.isVisible()) {
                panelActivo = comp;
                break;
            }
        }

        if (panelActivo == null) return "pantallaCartelera";

        if (panelActivo instanceof VistaPago) {
            return "pantallaAsientos";
            
        } else if (panelActivo instanceof VistaSeleccionAsientos panelAsientos) {
            panelAsientos.reiniciarColoresYEstadosBotones();
            return "pantallaDetalle";
            
        } else if (panelActivo instanceof VistaDetalles panelPelicula) {
            panelPelicula.limpiarFormularioPelicula();
            
            for (Component comp : contenedorPrincipal.getComponents()) {
                if (comp instanceof VistaSeleccionAsientos panelAsientos) {
                    panelAsientos.reiniciarColoresYEstadosBotones();
                }
            }
            return "pantallaCartelera";
        }
        
        return "pantallaCartelera";
    }

    public boolean debeMostrarBotonRegresar(String nombreCard) {
        return !"pantallaCartelera".equals(nombreCard);
    }
}