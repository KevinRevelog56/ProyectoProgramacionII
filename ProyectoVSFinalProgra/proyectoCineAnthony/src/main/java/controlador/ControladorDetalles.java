package controlador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import modelo.entidad.Funcion;
import modelo.entidad.Pelicula;
import vista.VistaDetalles;

public class ControladorDetalles {

    public String obtenerMesActualFormateado() {
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatoMes = DateTimeFormatter.ofPattern("MMMM", new Locale("es", "ES"));
        String mesCrudo = fechaActual.format(formatoMes).toLowerCase();
        return mesCrudo.substring(0, 1).toUpperCase() + mesCrudo.substring(1);
    }

    public double calcularTotalParcial(Pelicula pelicula, int cantidadEntradas) {
        if (pelicula != null) {
            return cantidadEntradas * pelicula.getPrecio();
        }
        return 0.0;
    }

    public void cargarPosterPelicula(Pelicula pelicula, JLabel lblPoster) {
        try {
            String ruta = "Imagenes/" + pelicula.getRutaImagen();
            ImageIcon iconoOriginal = new ImageIcon(ruta);
            
            int ancho = lblPoster.getWidth() > 0 ? lblPoster.getWidth() : 150;
            int alto = lblPoster.getHeight() > 0 ? lblPoster.getHeight() : 200;
            
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            lblPoster.setIcon(new ImageIcon(imagenEscalada));
            lblPoster.setText(""); 
        } catch (Exception e) {
            lblPoster.setText("Sin imagen");
        }
    }
    public void cargarBotonesHoras(int idPelicula, JScrollPane panelHora, 
                                   Color colorNormal, Color colorSeleccionado,
                                   VistaDetalles vistaDetalles) {
        
        JPanel panelInterno = (JPanel) panelHora.getViewport().getView();

        if (panelInterno == null) {
            panelInterno = new JPanel();
            panelHora.setViewportView(panelInterno);
        }

        panelInterno.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelInterno.removeAll();

        //se busca las funciones mediante el GestorCine
        GestorCine gestor = new GestorCine();
        List<Funcion> funcionesPelicula = gestor.obtenerFuncionesPorPelicula(idPelicula);

        if (funcionesPelicula.isEmpty()) {
            JLabel lblSinFunciones = new JLabel("No hay funciones disponibles");
            panelInterno.add(lblSinFunciones);
        } else {
            for (Funcion func : funcionesPelicula) {
                JButton btnHora = new JButton(func.getHorario());

                btnHora.setPreferredSize(new Dimension(70, 50));
                btnHora.setBackground(colorNormal);
                btnHora.setOpaque(true);
                btnHora.setFocusPainted(false);
                btnHora.setFocusable(true);

                //se guarda el id de la funcion
                btnHora.putClientProperty("idFuncion", func.getIdFuncion());

                btnHora.addActionListener(e -> {
                    vistaDetalles.seleccionarBotonHora(btnHora, colorNormal, colorSeleccionado);
                    
                    //se almacena la sala
                    int idFuncionSeleccionada = (int) btnHora.getClientProperty("idFuncion");
                    Funcion funcionSeleccionada = null;
                    
                    for (Funcion f : funcionesPelicula) {
                        if (f.getIdFuncion() == idFuncionSeleccionada) {
                            funcionSeleccionada = f;
                            break;
                        }
                    }

                    if (funcionSeleccionada != null && funcionSeleccionada.getSala() != null) {
                        vistaDetalles.setSalaSeleccionada(funcionSeleccionada.getSala().getNombre());
                    }
                });

                panelInterno.add(btnHora);
            }
        }

        panelInterno.revalidate();
        panelInterno.repaint();
        panelHora.revalidate();
        panelHora.repaint();
    }
}