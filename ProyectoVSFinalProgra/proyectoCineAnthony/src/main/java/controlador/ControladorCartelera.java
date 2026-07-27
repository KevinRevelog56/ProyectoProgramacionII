package controlador;

import java.awt.Image;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import modelo.entidad.Pelicula;

public class ControladorCartelera {
    private GestorCine gestorCine;

    public ControladorCartelera() {
        this.gestorCine = new GestorCine();
    }

    public List<Pelicula> obtenerListaPeliculas() {
        return gestorCine.obtenerCartelera();
    }
    
    public void cargarDatosEnVista(List<Pelicula> lista, JButton[] botones, JLabel[] posters) {
        for (int i = 0; i < botones.length; i++) {
            botones[i].setText("");
            posters[i].setIcon(null);
            posters[i].setText("No disponible");
            botones[i].setEnabled(false); 

            if (i < lista.size()) {
                Pelicula p = lista.get(i);
                botones[i].setText(p.getTitulo());
                botones[i].setEnabled(true);

                try {
                    String nombreImagen = p.getRutaImagen().trim();
                    String ruta = "Imagenes/" + nombreImagen;
                    ImageIcon iconoOriginal = new ImageIcon(ruta);

                    if (iconoOriginal.getIconWidth() > 0) {
                        int ancho = posters[i].getWidth();
                        int alto = posters[i].getHeight();

                        if (ancho <= 0) ancho = 200;
                        if (alto <= 0) alto = 300;

                        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                        posters[i].setIcon(new ImageIcon(imagenEscalada));
                        posters[i].setText("");
                    } else {
                        posters[i].setText("No encontrada");
                    }
                } catch (Exception e) {
                    posters[i].setText("Error");
                }
            }
        }
    }
}