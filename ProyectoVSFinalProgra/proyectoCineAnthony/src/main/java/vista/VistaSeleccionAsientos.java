/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import modelo.entidad.Pelicula;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import modelo.dao.BoletoDAO;
import modelo.entidad.Boleto;
import modelo.entidad.Sala;

/**
 *
 * @author Matth
 */
    public class VistaSeleccionAsientos extends javax.swing.JPanel {
        private Pelicula peliculaActual;
        // Cantidad de asientos que el usuario debe seleccionar
        private int entradasCompradas = 0;
        // Contador de cuántos asientos han sido seleccionados en la pantalla de seleccionAsientos
        int asientosSeleccionados = 0;
        private String fechaSeleccionada = "";
        private String horaSeleccionada = ""; 
        private String salaSeleccionada = "";

        public void setFecha(String fecha) {
        this.fechaSeleccionada = fecha;
    }
        
    public void setSala(String sala) {
        this.salaSeleccionada = sala;
    }
    
    public void setHora(String hora) {
        this.horaSeleccionada = hora;
    }

    public void actualizarEstadoBoton() {
        //se compara si los asientos seleccionados coinciden con las entradas compradas
        if (asientosSeleccionados == entradasCompradas) {
            btnSeleccionarAsientos.setEnabled(true);
        } else {
            btnSeleccionarAsientos.setEnabled(false);
        }
    }   
    public void setPelicula(Pelicula p) {
        this.peliculaActual = p;
    }
        
    public void setEntradasCompradas(int cantidad) {
    this.entradasCompradas = cantidad; 
    this.btnSeleccionarAsientos.setEnabled(false);   
    
    //se limpian los asientos y pone todos en gris
    reiniciarColoresYEstadosBotones(); 
    
    //se ponen en rojo los asientos ocupados
    cargarAsientosOcupados();
}
        
   private void inicializarAsientos() {
    String[] filas = {"A", "B", "C", "D", "E", "F", "G"};
    
    for (String fila : filas) {
        int maxAsientos;
        if (fila.equals("A") || fila.equals("B")) {
            maxAsientos = 16;
        } else {
            maxAsientos = 18;
        }
        
        for (int i = 1; i <= maxAsientos; i++) {
            String nombreBoton = "btn" + fila + i;
            
            try {
                Field campo = this.getClass().getDeclaredField(nombreBoton);
                campo.setAccessible(true);
                JButton boton = (JButton) campo.get(this);
                
                if (boton != null) {
                    boton.setMargin(new java.awt.Insets(0, 0, 0, 0));
               
                    boton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            gestionarClicAsiento(boton);
                        }
                    });
                }
            } catch (Exception e) {
            }
        }
    }
}
    public List<String> obtenerAsientosSeleccionados() {
    List<String> listaAsientos = new ArrayList<>();
    String[] filas = {"A", "B", "C", "D", "E", "F", "G"};
    
    for (String fila : filas) {
        int maxAsientos = (fila.equals("A") || fila.equals("B")) ? 16 : 18;
        for (int i = 1; i <= maxAsientos; i++) {
            String nombreBoton = "btn" + fila + i;
            try {
                Field campo = this.getClass().getDeclaredField(nombreBoton);
                campo.setAccessible(true);
                JButton boton = (JButton) campo.get(this);
                
                if (boton != null) {
                    //se comprueba si esta seleccionado
                    String estado = (String) boton.getClientProperty("estadoAsiento");
                    if ("seleccionado".equals(estado)) {
                        listaAsientos.add(fila + i);
                    }
                }
            } catch (Exception e) {
            }
        }
    }
    return listaAsientos;
}
    
      public void cargarAsientosOcupados() {
        if (peliculaActual == null || horaSeleccionada.isEmpty()) {
            return;
        }

    Set<String> asientosOcupados = new HashSet<>();
    BoletoDAO boletoDao = new BoletoDAO();
    List<Boleto> listaBoletos = boletoDao.obtenerBoletos();

    for (modelo.entidad.Boleto b : listaBoletos) {
        if (b.getPelicula().trim().equalsIgnoreCase(peliculaActual.getTitulo().trim()) &&
            b.getFuncion().trim().contains(horaSeleccionada.trim())) {
            
            String[] asientosBoleto = b.getAsientos().split(",");
            for (String asiento : asientosBoleto) {
                asientosOcupados.add(asiento.trim());
            }
        }
    }

    String[] filas = {"A", "B", "C", "D", "E", "F", "G"};
    int botonesProcesados = 0;

    for (String fila : filas) {
        int maxAsientos = (fila.equals("A") || fila.equals("B")) ? 16 : 18;
        
        for (int i = 1; i <= maxAsientos; i++) {
            String nombreBoton = "btn" + fila + i;
            String codigoAsiento = fila + i;

            try {
                Field campo = this.getClass().getDeclaredField(nombreBoton);
                campo.setAccessible(true);
                JButton boton = (JButton) campo.get(this);

                if (boton != null) {
                    botonesProcesados++;
                    boton.setOpaque(true);
                    boton.setContentAreaFilled(false); 
                    boton.setBorderPainted(true);

                    if (asientosOcupados.contains(codigoAsiento)) {
                        boton.setBackground(new java.awt.Color(255, 51, 0)); //sw pinta el boton en rojo
                        boton.setForeground(java.awt.Color.WHITE);
                        boton.putClientProperty("estadoAsiento", "ocupado");
                        boton.setEnabled(true);
                    } else {
                        boton.setBackground(new java.awt.Color(204, 204, 204)); //se pinta el boton en gris
                        boton.setForeground(java.awt.Color.BLACK);
                        boton.putClientProperty("estadoAsiento", "disponible");
                        boton.setEnabled(true);
                    }
                    
                    boton.repaint();
                }
            } catch (Exception e) {}
        }
    }
}

public void reiniciarColoresYEstadosBotones() {
    this.asientosSeleccionados = 0;
    this.btnSeleccionarAsientos.setEnabled(false);

    //se obtiene de nuevo la lista de ocupados actual para que el reseteo no borre el rojo
    java.util.Set<String> asientosOcupados = new java.util.HashSet<>();
    if (peliculaActual != null && !horaSeleccionada.isEmpty()) {
        modelo.dao.BoletoDAO boletoDao = new modelo.dao.BoletoDAO();
        java.util.List<modelo.entidad.Boleto> listaBoletos = boletoDao.obtenerBoletos();
        for (modelo.entidad.Boleto b : listaBoletos) {
            if (b.getPelicula().trim().equalsIgnoreCase(peliculaActual.getTitulo().trim()) &&
                b.getFuncion().trim().contains(horaSeleccionada.trim())) {
                for (String asiento : b.getAsientos().split(",")) {
                    asientosOcupados.add(asiento.trim());
                }
            }
        }
    }

    String[] filas = {"A", "B", "C", "D", "E", "F", "G"};
    for (String fila : filas) {
        int maxAsientos = (fila.equals("A" ) || fila.equals("B")) ? 16 : 18;
        for (int col = 1; col <= maxAsientos; col++) {
            try {
                String nombreBoton = "btn" + fila + col;
                String codigoAsiento = fila + col;
                Field field = this.getClass().getDeclaredField(nombreBoton);
                field.setAccessible(true);
                JButton boton = (JButton) field.get(this);

                if (boton != null) {
                    boton.setSelected(false);
                    boton.setOpaque(true);
                    boton.setContentAreaFilled(false);
                    boton.setBorderPainted(true);

                    //de a ceurdo al estado s epinta de un color u otro
                    if (asientosOcupados.contains(codigoAsiento)) {
                        boton.setBackground(new java.awt.Color(255, 51, 0)); //rojo
                        boton.setForeground(java.awt.Color.WHITE);
                        boton.putClientProperty("estadoAsiento", "ocupado");
                        boton.setEnabled(true);
                    } else {
                        boton.setBackground(new java.awt.Color(204, 204, 204)); //gris disponible
                        boton.setForeground(java.awt.Color.BLACK);
                        boton.putClientProperty("estadoAsiento", "disponible");
                        boton.setEnabled(true);
                    }
                    boton.repaint();
                }
            } catch (Exception e) {}
        }
    }
}

   
private void gestionarClicAsiento(javax.swing.JButton botonAsiento) {
    //se verifica si el boton estya en rojo
    String estado = (String) botonAsiento.getClientProperty("estadoAsiento");
    if ("ocupado".equals(estado)) {
        JOptionPane.showMessageDialog(this, "Este asiento ya está ocupado.");
        return;
    }

    Color colorSeleccionado = new Color(153, 255, 102); 
    Color colorDisponible = new Color(204, 204, 204);   

    boolean yaSeleccionado = colorSeleccionado.equals(botonAsiento.getBackground());

    botonAsiento.setOpaque(true);
    botonAsiento.setContentAreaFilled(true);

    if (!yaSeleccionado) {
        if (asientosSeleccionados >= entradasCompradas) {
            JOptionPane.showMessageDialog(this, 
                "Ya seleccionaste el máximo de asientos permitidos (" + entradasCompradas + ").");
            return;
        }
        asientosSeleccionados++;
        botonAsiento.setBackground(colorSeleccionado);
        botonAsiento.putClientProperty("estadoAsiento", "seleccionado");
    } else {
        asientosSeleccionados--;
        botonAsiento.setBackground(colorDisponible);
        botonAsiento.putClientProperty("estadoAsiento", "disponible");
    }

    actualizarEstadoBoton();
}
    
    public VistaSeleccionAsientos() {
        initComponents();
        inicializarAsientos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contenedorPrincipal = new javax.swing.JPanel();
        lbSelecion = new javax.swing.JLabel();
        lbReservados = new javax.swing.JLabel();
        lbRojo = new javax.swing.JLabel();
        lbTusAsientos = new javax.swing.JLabel();
        lbVerde = new javax.swing.JLabel();
        lbDisponilbles = new javax.swing.JLabel();
        lbGris = new javax.swing.JLabel();
        lbPantalla = new javax.swing.JLabel();
        lbA1 = new javax.swing.JLabel();
        lbB1 = new javax.swing.JLabel();
        lbC1 = new javax.swing.JLabel();
        lbD1 = new javax.swing.JLabel();
        lbE1 = new javax.swing.JLabel();
        lbF1 = new javax.swing.JLabel();
        lbG1 = new javax.swing.JLabel();
        lbH1 = new javax.swing.JLabel();
        btnA1 = new javax.swing.JButton();
        btnA4 = new javax.swing.JButton();
        btnA3 = new javax.swing.JButton();
        btnA5 = new javax.swing.JButton();
        btnA15 = new javax.swing.JButton();
        btnA2 = new javax.swing.JButton();
        btnA6 = new javax.swing.JButton();
        btnA7 = new javax.swing.JButton();
        btnA8 = new javax.swing.JButton();
        btnA9 = new javax.swing.JButton();
        btnA10 = new javax.swing.JButton();
        btnA11 = new javax.swing.JButton();
        btnA12 = new javax.swing.JButton();
        btnA13 = new javax.swing.JButton();
        btnA14 = new javax.swing.JButton();
        btnA16 = new javax.swing.JButton();
        btnH6 = new javax.swing.JButton();
        btnH7 = new javax.swing.JButton();
        btnH8 = new javax.swing.JButton();
        btnH9 = new javax.swing.JButton();
        btnH10 = new javax.swing.JButton();
        btnH11 = new javax.swing.JButton();
        btnH12 = new javax.swing.JButton();
        btnH13 = new javax.swing.JButton();
        btnH14 = new javax.swing.JButton();
        btnH16 = new javax.swing.JButton();
        btnH1 = new javax.swing.JButton();
        btnH4 = new javax.swing.JButton();
        btnH3 = new javax.swing.JButton();
        btnH5 = new javax.swing.JButton();
        btnH15 = new javax.swing.JButton();
        btnH17 = new javax.swing.JButton();
        btnH18 = new javax.swing.JButton();
        btnH2 = new javax.swing.JButton();
        btnG6 = new javax.swing.JButton();
        btnG7 = new javax.swing.JButton();
        btnG8 = new javax.swing.JButton();
        btnG9 = new javax.swing.JButton();
        btnG10 = new javax.swing.JButton();
        btnG11 = new javax.swing.JButton();
        btnG12 = new javax.swing.JButton();
        btnG13 = new javax.swing.JButton();
        btnG14 = new javax.swing.JButton();
        btnG16 = new javax.swing.JButton();
        btnG1 = new javax.swing.JButton();
        btnG4 = new javax.swing.JButton();
        btnG3 = new javax.swing.JButton();
        btnG5 = new javax.swing.JButton();
        btnG15 = new javax.swing.JButton();
        btnG17 = new javax.swing.JButton();
        btnG18 = new javax.swing.JButton();
        btnG2 = new javax.swing.JButton();
        btnF4 = new javax.swing.JButton();
        btnF3 = new javax.swing.JButton();
        btnF5 = new javax.swing.JButton();
        btnF15 = new javax.swing.JButton();
        btnF17 = new javax.swing.JButton();
        btnF18 = new javax.swing.JButton();
        btnF2 = new javax.swing.JButton();
        btnE6 = new javax.swing.JButton();
        btnE7 = new javax.swing.JButton();
        btnE8 = new javax.swing.JButton();
        btnE9 = new javax.swing.JButton();
        btnE10 = new javax.swing.JButton();
        btnE11 = new javax.swing.JButton();
        btnE12 = new javax.swing.JButton();
        btnE13 = new javax.swing.JButton();
        btnE14 = new javax.swing.JButton();
        btnE16 = new javax.swing.JButton();
        btnE1 = new javax.swing.JButton();
        btnE4 = new javax.swing.JButton();
        btnE3 = new javax.swing.JButton();
        btnE5 = new javax.swing.JButton();
        btnE15 = new javax.swing.JButton();
        btnE17 = new javax.swing.JButton();
        btnE18 = new javax.swing.JButton();
        btnE2 = new javax.swing.JButton();
        btnF6 = new javax.swing.JButton();
        btnF7 = new javax.swing.JButton();
        btnF8 = new javax.swing.JButton();
        btnF9 = new javax.swing.JButton();
        btnF10 = new javax.swing.JButton();
        btnF11 = new javax.swing.JButton();
        btnF12 = new javax.swing.JButton();
        btnF13 = new javax.swing.JButton();
        btnF14 = new javax.swing.JButton();
        btnF16 = new javax.swing.JButton();
        btnF1 = new javax.swing.JButton();
        btnB6 = new javax.swing.JButton();
        btnB7 = new javax.swing.JButton();
        btnB8 = new javax.swing.JButton();
        btnB9 = new javax.swing.JButton();
        btnB10 = new javax.swing.JButton();
        btnB11 = new javax.swing.JButton();
        btnB12 = new javax.swing.JButton();
        btnB13 = new javax.swing.JButton();
        btnB14 = new javax.swing.JButton();
        btnB16 = new javax.swing.JButton();
        btnB1 = new javax.swing.JButton();
        btnB4 = new javax.swing.JButton();
        btnB3 = new javax.swing.JButton();
        btnB5 = new javax.swing.JButton();
        btnB15 = new javax.swing.JButton();
        btnB2 = new javax.swing.JButton();
        btnC18 = new javax.swing.JButton();
        btnC16 = new javax.swing.JButton();
        btnC2 = new javax.swing.JButton();
        btnC5 = new javax.swing.JButton();
        btnC9 = new javax.swing.JButton();
        btnC7 = new javax.swing.JButton();
        btnC1 = new javax.swing.JButton();
        btnC4 = new javax.swing.JButton();
        btnC11 = new javax.swing.JButton();
        btnC14 = new javax.swing.JButton();
        btnC13 = new javax.swing.JButton();
        btnC6 = new javax.swing.JButton();
        btnC10 = new javax.swing.JButton();
        btnC15 = new javax.swing.JButton();
        btnC8 = new javax.swing.JButton();
        btnC17 = new javax.swing.JButton();
        btnC12 = new javax.swing.JButton();
        btnC3 = new javax.swing.JButton();
        btnD16 = new javax.swing.JButton();
        btnD2 = new javax.swing.JButton();
        btnD5 = new javax.swing.JButton();
        btnD9 = new javax.swing.JButton();
        btnD7 = new javax.swing.JButton();
        btnD1 = new javax.swing.JButton();
        btnD4 = new javax.swing.JButton();
        btnD11 = new javax.swing.JButton();
        btnD14 = new javax.swing.JButton();
        btnD13 = new javax.swing.JButton();
        btnD6 = new javax.swing.JButton();
        btnD10 = new javax.swing.JButton();
        btnD15 = new javax.swing.JButton();
        btnD8 = new javax.swing.JButton();
        btnD17 = new javax.swing.JButton();
        btnD12 = new javax.swing.JButton();
        btnD3 = new javax.swing.JButton();
        btnD18 = new javax.swing.JButton();
        lbA2 = new javax.swing.JLabel();
        lbB2 = new javax.swing.JLabel();
        lbC2 = new javax.swing.JLabel();
        lbD2 = new javax.swing.JLabel();
        lbE2 = new javax.swing.JLabel();
        lbF2 = new javax.swing.JLabel();
        lbG2 = new javax.swing.JLabel();
        btnSeleccionarAsientos = new javax.swing.JButton();

        setBackground(new java.awt.Color(17, 17, 17));

        contenedorPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        contenedorPrincipal.setForeground(new java.awt.Color(255, 255, 255));
        contenedorPrincipal.setPreferredSize(new java.awt.Dimension(1256, 591));

        lbSelecion.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 18)); // NOI18N
        lbSelecion.setText("Seleccionar Accientos ");

        lbReservados.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        lbReservados.setText("Reservados");

        lbRojo.setBackground(new java.awt.Color(255, 51, 0));
        lbRojo.setOpaque(true);

        lbTusAsientos.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        lbTusAsientos.setText("Elegidos");

        lbVerde.setBackground(new java.awt.Color(153, 255, 102));
        lbVerde.setOpaque(true);

        lbDisponilbles.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        lbDisponilbles.setText("Disponible");

        lbGris.setBackground(new java.awt.Color(204, 204, 204));
        lbGris.setOpaque(true);

        lbPantalla.setBackground(new java.awt.Color(0, 0, 0));
        lbPantalla.setFont(new java.awt.Font("Segoe UI Symbol", 1, 24)); // NOI18N
        lbPantalla.setForeground(new java.awt.Color(255, 255, 255));
        lbPantalla.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPantalla.setText("Pantalla");
        lbPantalla.setOpaque(true);

        lbA1.setText("A");

        lbB1.setText("B");

        lbC1.setText("C");

        lbD1.setText("D");

        lbE1.setText("E");

        lbF1.setText("F");

        lbG1.setText("G");

        lbH1.setText("H");

        btnA1.setText("A1");
        btnA1.addActionListener(this::btnA1ActionPerformed);

        btnA4.setText("A4");
        btnA4.addActionListener(this::btnA4ActionPerformed);

        btnA3.setText("A3");
        btnA3.addActionListener(this::btnA3ActionPerformed);

        btnA5.setText("A5");
        btnA5.addActionListener(this::btnA5ActionPerformed);

        btnA15.setText("A15");
        btnA15.addActionListener(this::btnA15ActionPerformed);

        btnA2.setText("A2");
        btnA2.addActionListener(this::btnA2ActionPerformed);

        btnA6.setText("A6");
        btnA6.addActionListener(this::btnA6ActionPerformed);

        btnA7.setText("A7");
        btnA7.addActionListener(this::btnA7ActionPerformed);

        btnA8.setText("A8");
        btnA8.addActionListener(this::btnA8ActionPerformed);

        btnA9.setText("A9");
        btnA9.addActionListener(this::btnA9ActionPerformed);

        btnA10.setText("A10");
        btnA10.addActionListener(this::btnA10ActionPerformed);

        btnA11.setText("A11");
        btnA11.addActionListener(this::btnA11ActionPerformed);

        btnA12.setText("A12");
        btnA12.addActionListener(this::btnA12ActionPerformed);

        btnA13.setText("A13");
        btnA13.addActionListener(this::btnA13ActionPerformed);

        btnA14.setText("A14");
        btnA14.addActionListener(this::btnA14ActionPerformed);

        btnA16.setText("A16");
        btnA16.addActionListener(this::btnA16ActionPerformed);

        btnH6.setText("H6");
        btnH6.addActionListener(this::btnH6ActionPerformed);

        btnH7.setText("H7");
        btnH7.addActionListener(this::btnH7ActionPerformed);

        btnH8.setText("H8");
        btnH8.addActionListener(this::btnH8ActionPerformed);

        btnH9.setText("H9");
        btnH9.addActionListener(this::btnH9ActionPerformed);

        btnH10.setText("H10");
        btnH10.addActionListener(this::btnH10ActionPerformed);

        btnH11.setText("H11");
        btnH11.addActionListener(this::btnH11ActionPerformed);

        btnH12.setText("H12");
        btnH12.addActionListener(this::btnH12ActionPerformed);

        btnH13.setText("H13");
        btnH13.addActionListener(this::btnH13ActionPerformed);

        btnH14.setText("H14");
        btnH14.addActionListener(this::btnH14ActionPerformed);

        btnH16.setText("H16");
        btnH16.addActionListener(this::btnH16ActionPerformed);

        btnH1.setText("H1");
        btnH1.addActionListener(this::btnH1ActionPerformed);

        btnH4.setText("H4");
        btnH4.addActionListener(this::btnH4ActionPerformed);

        btnH3.setText("H3");
        btnH3.addActionListener(this::btnH3ActionPerformed);

        btnH5.setText("H5");
        btnH5.addActionListener(this::btnH5ActionPerformed);

        btnH15.setText("H15");
        btnH15.addActionListener(this::btnH15ActionPerformed);

        btnH17.setText("H17");
        btnH17.addActionListener(this::btnH17ActionPerformed);

        btnH18.setText("H18");
        btnH18.addActionListener(this::btnH18ActionPerformed);

        btnH2.setText("H2");
        btnH2.addActionListener(this::btnH2ActionPerformed);

        btnG6.setText("G6");
        btnG6.addActionListener(this::btnG6ActionPerformed);

        btnG7.setText("G7");
        btnG7.addActionListener(this::btnG7ActionPerformed);

        btnG8.setText("G8");
        btnG8.addActionListener(this::btnG8ActionPerformed);

        btnG9.setText("G9");
        btnG9.addActionListener(this::btnG9ActionPerformed);

        btnG10.setText("G10");
        btnG10.addActionListener(this::btnG10ActionPerformed);

        btnG11.setText("G11");
        btnG11.addActionListener(this::btnG11ActionPerformed);

        btnG12.setText("G12");
        btnG12.addActionListener(this::btnG12ActionPerformed);

        btnG13.setText("G13");
        btnG13.addActionListener(this::btnG13ActionPerformed);

        btnG14.setText("G14");
        btnG14.addActionListener(this::btnG14ActionPerformed);

        btnG16.setText("G16");
        btnG16.addActionListener(this::btnG16ActionPerformed);

        btnG1.setText("G1");
        btnG1.addActionListener(this::btnG1ActionPerformed);

        btnG4.setText("G4");
        btnG4.addActionListener(this::btnG4ActionPerformed);

        btnG3.setText("G3");
        btnG3.addActionListener(this::btnG3ActionPerformed);

        btnG5.setText("G5");
        btnG5.addActionListener(this::btnG5ActionPerformed);

        btnG15.setText("G15");
        btnG15.addActionListener(this::btnG15ActionPerformed);

        btnG17.setText("G17");
        btnG17.addActionListener(this::btnG17ActionPerformed);

        btnG18.setText("G18");
        btnG18.addActionListener(this::btnG18ActionPerformed);

        btnG2.setText("G2");
        btnG2.addActionListener(this::btnG2ActionPerformed);

        btnF4.setText("F4");
        btnF4.addActionListener(this::btnF4ActionPerformed);

        btnF3.setText("F3");
        btnF3.addActionListener(this::btnF3ActionPerformed);

        btnF5.setText("F5");
        btnF5.addActionListener(this::btnF5ActionPerformed);

        btnF15.setText("F15");
        btnF15.addActionListener(this::btnF15ActionPerformed);

        btnF17.setText("F17");
        btnF17.addActionListener(this::btnF17ActionPerformed);

        btnF18.setText("F18");
        btnF18.addActionListener(this::btnF18ActionPerformed);

        btnF2.setText("F2");
        btnF2.addActionListener(this::btnF2ActionPerformed);

        btnE6.setText("E6");
        btnE6.addActionListener(this::btnE6ActionPerformed);

        btnE7.setText("E7");
        btnE7.addActionListener(this::btnE7ActionPerformed);

        btnE8.setText("E8");
        btnE8.addActionListener(this::btnE8ActionPerformed);

        btnE9.setText("E9");
        btnE9.addActionListener(this::btnE9ActionPerformed);

        btnE10.setText("E10");
        btnE10.addActionListener(this::btnE10ActionPerformed);

        btnE11.setText("E11");
        btnE11.addActionListener(this::btnE11ActionPerformed);

        btnE12.setText("E12");
        btnE12.addActionListener(this::btnE12ActionPerformed);

        btnE13.setText("E13");
        btnE13.addActionListener(this::btnE13ActionPerformed);

        btnE14.setText("E14");
        btnE14.addActionListener(this::btnE14ActionPerformed);

        btnE16.setText("E16");
        btnE16.addActionListener(this::btnE16ActionPerformed);

        btnE1.setText("E1");
        btnE1.addActionListener(this::btnE1ActionPerformed);

        btnE4.setText("E4");
        btnE4.addActionListener(this::btnE4ActionPerformed);

        btnE3.setText("E3");
        btnE3.addActionListener(this::btnE3ActionPerformed);

        btnE5.setText("E5");
        btnE5.addActionListener(this::btnE5ActionPerformed);

        btnE15.setText("E15");
        btnE15.addActionListener(this::btnE15ActionPerformed);

        btnE17.setText("E17");
        btnE17.addActionListener(this::btnE17ActionPerformed);

        btnE18.setText("E18");
        btnE18.addActionListener(this::btnE18ActionPerformed);

        btnE2.setText("E2");
        btnE2.addActionListener(this::btnE2ActionPerformed);

        btnF6.setText("F6");
        btnF6.addActionListener(this::btnF6ActionPerformed);

        btnF7.setText("F7");
        btnF7.addActionListener(this::btnF7ActionPerformed);

        btnF8.setText("F8");
        btnF8.addActionListener(this::btnF8ActionPerformed);

        btnF9.setText("F9");
        btnF9.addActionListener(this::btnF9ActionPerformed);

        btnF10.setText("F10");
        btnF10.addActionListener(this::btnF10ActionPerformed);

        btnF11.setText("F11");
        btnF11.addActionListener(this::btnF11ActionPerformed);

        btnF12.setText("F12");
        btnF12.addActionListener(this::btnF12ActionPerformed);

        btnF13.setText("F13");
        btnF13.addActionListener(this::btnF13ActionPerformed);

        btnF14.setText("F14");
        btnF14.addActionListener(this::btnF14ActionPerformed);

        btnF16.setText("F16");
        btnF16.addActionListener(this::btnF16ActionPerformed);

        btnF1.setText("F1");
        btnF1.addActionListener(this::btnF1ActionPerformed);

        btnB6.setText("B6");
        btnB6.addActionListener(this::btnB6ActionPerformed);

        btnB7.setText("B7");
        btnB7.addActionListener(this::btnB7ActionPerformed);

        btnB8.setText("B8");
        btnB8.addActionListener(this::btnB8ActionPerformed);

        btnB9.setText("B9");
        btnB9.addActionListener(this::btnB9ActionPerformed);

        btnB10.setText("B10");
        btnB10.addActionListener(this::btnB10ActionPerformed);

        btnB11.setText("B11");
        btnB11.addActionListener(this::btnB11ActionPerformed);

        btnB12.setText("B12");
        btnB12.addActionListener(this::btnB12ActionPerformed);

        btnB13.setText("B13");
        btnB13.addActionListener(this::btnB13ActionPerformed);

        btnB14.setText("B14");
        btnB14.addActionListener(this::btnB14ActionPerformed);

        btnB16.setText("B16");
        btnB16.addActionListener(this::btnB16ActionPerformed);

        btnB1.setText("B1");
        btnB1.addActionListener(this::btnB1ActionPerformed);

        btnB4.setText("B4");
        btnB4.addActionListener(this::btnB4ActionPerformed);

        btnB3.setText("B3");
        btnB3.addActionListener(this::btnB3ActionPerformed);

        btnB5.setText("B5");
        btnB5.addActionListener(this::btnB5ActionPerformed);

        btnB15.setText("B15");
        btnB15.addActionListener(this::btnB15ActionPerformed);

        btnB2.setText("B2");
        btnB2.addActionListener(this::btnB2ActionPerformed);

        btnC18.setText("C18");
        btnC18.addActionListener(this::btnC18ActionPerformed);

        btnC16.setText("C16");
        btnC16.addActionListener(this::btnC16ActionPerformed);

        btnC2.setText("C2");
        btnC2.addActionListener(this::btnC2ActionPerformed);

        btnC5.setText("C5");
        btnC5.addActionListener(this::btnC5ActionPerformed);

        btnC9.setText("C9");
        btnC9.addActionListener(this::btnC9ActionPerformed);

        btnC7.setText("C7");
        btnC7.addActionListener(this::btnC7ActionPerformed);

        btnC1.setText("C1");
        btnC1.addActionListener(this::btnC1ActionPerformed);

        btnC4.setText("C4");
        btnC4.addActionListener(this::btnC4ActionPerformed);

        btnC11.setText("C11");
        btnC11.addActionListener(this::btnC11ActionPerformed);

        btnC14.setText("C14");
        btnC14.addActionListener(this::btnC14ActionPerformed);

        btnC13.setText("C13");
        btnC13.addActionListener(this::btnC13ActionPerformed);

        btnC6.setText("C6");
        btnC6.addActionListener(this::btnC6ActionPerformed);

        btnC10.setText("C10");
        btnC10.addActionListener(this::btnC10ActionPerformed);

        btnC15.setText("C15");
        btnC15.addActionListener(this::btnC15ActionPerformed);

        btnC8.setText("C8");
        btnC8.addActionListener(this::btnC8ActionPerformed);

        btnC17.setText("C17");
        btnC17.addActionListener(this::btnC17ActionPerformed);

        btnC12.setText("C12");
        btnC12.addActionListener(this::btnC12ActionPerformed);

        btnC3.setText("C3");
        btnC3.addActionListener(this::btnC3ActionPerformed);

        btnD16.setText("D16");
        btnD16.addActionListener(this::btnD16ActionPerformed);

        btnD2.setText("D2");
        btnD2.addActionListener(this::btnD2ActionPerformed);

        btnD5.setText("D5");
        btnD5.addActionListener(this::btnD5ActionPerformed);

        btnD9.setText("D9");
        btnD9.addActionListener(this::btnD9ActionPerformed);

        btnD7.setText("D7");
        btnD7.addActionListener(this::btnD7ActionPerformed);

        btnD1.setText("D1");
        btnD1.addActionListener(this::btnD1ActionPerformed);

        btnD4.setText("D4");
        btnD4.addActionListener(this::btnD4ActionPerformed);

        btnD11.setText("D11");
        btnD11.addActionListener(this::btnD11ActionPerformed);

        btnD14.setText("D14");
        btnD14.addActionListener(this::btnD14ActionPerformed);

        btnD13.setText("D13");
        btnD13.addActionListener(this::btnD13ActionPerformed);

        btnD6.setText("D6");
        btnD6.addActionListener(this::btnD6ActionPerformed);

        btnD10.setText("D10");
        btnD10.addActionListener(this::btnD10ActionPerformed);

        btnD15.setText("D15");
        btnD15.addActionListener(this::btnD15ActionPerformed);

        btnD8.setText("D8");
        btnD8.addActionListener(this::btnD8ActionPerformed);

        btnD17.setText("D17");
        btnD17.addActionListener(this::btnD17ActionPerformed);

        btnD12.setText("D12");
        btnD12.addActionListener(this::btnD12ActionPerformed);

        btnD3.setText("D3");
        btnD3.addActionListener(this::btnD3ActionPerformed);

        btnD18.setText("D18");
        btnD18.addActionListener(this::btnD18ActionPerformed);

        lbA2.setText("A");

        lbB2.setText("B");

        lbC2.setText("C");

        lbD2.setText("D");

        lbE2.setText("E");

        lbF2.setText("F");

        lbG2.setText("G");

        javax.swing.GroupLayout contenedorPrincipalLayout = new javax.swing.GroupLayout(contenedorPrincipal);
        contenedorPrincipal.setLayout(contenedorPrincipalLayout);
        contenedorPrincipalLayout.setHorizontalGroup(
            contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                        .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbRojo, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                                .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbD1)
                                        .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lbA1)
                                            .addComponent(lbC1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 9, Short.MAX_VALUE)
                                            .addComponent(lbB1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(lbE1)
                                        .addComponent(lbF1)
                                        .addComponent(lbG1))
                                    .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                                        .addComponent(lbH1)
                                        .addGap(1, 1, 1)))
                                .addGap(5, 5, 5)))
                        .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contenedorPrincipalLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbReservados, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(lbGris, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbDisponilbles, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(lbVerde, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbTusAsientos, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(857, Short.MAX_VALUE))
                            .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(lbPantalla, javax.swing.GroupLayout.PREFERRED_SIZE, 1125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                                        .addComponent(btnE1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnE2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnE3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnE4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnE5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnE6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnE7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnE8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnE9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnE10, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnE11, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnE12, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnE13, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnE14, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnE15, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnE16, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnE17, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnE18, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                                        .addComponent(btnD1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnD2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnD3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnD4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnD5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnD6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnD7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnD8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnD9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnD10, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnD11, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnD12, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnD13, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnD14, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnD15, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnD16, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnD17, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnD18, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                                        .addComponent(btnH1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnH2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnH3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnH4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnH5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnH6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnH7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnH8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnH9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnH10, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnH11, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnH12, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnH13, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnH14, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnH15, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnH16, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnH17, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnH18, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                                            .addComponent(btnG1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnG2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnG3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnG4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnG5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnG6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnG7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnG8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnG9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnG10, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnG11, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnG12, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnG13, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnG14, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnG15, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnG16, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnG17, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnG18, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                                            .addComponent(btnF1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnF2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnF3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnF4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnF5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnF6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnF7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnF8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnF9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnF10, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnF11, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnF12, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnF13, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnF14, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnF15, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnF16, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnF17, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnF18, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                                        .addComponent(btnC1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                                                .addComponent(btnB1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnB2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnB3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnB4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnB5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnB6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnB7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnB8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnB9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnB10, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnB11, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnB12, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnB13, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnB14, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnB15, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnB16, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                                                .addComponent(btnA1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnA2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnA3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnA4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnA5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnA6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnA7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnA8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnA9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnA10, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnA11, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnA12, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnA13, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnA14, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnA15, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnA16, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                                                .addComponent(btnC2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnC3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnC4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnC5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnC6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnC7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnC8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnC9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnC10, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnC11, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnC12, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnC13, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnC14, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnC15, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnC16, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnC17, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnC18, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contenedorPrincipalLayout.createSequentialGroup()
                                        .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lbD2, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(lbA2)
                                                    .addComponent(lbC2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(lbB2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addComponent(lbE2, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addGap(1, 1, 1))
                                    .addComponent(lbF2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbG2, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(25, 25, 25))))
                    .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                        .addComponent(lbSelecion)
                        .addContainerGap())))
        );
        contenedorPrincipalLayout.setVerticalGroup(
            contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbSelecion, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbReservados, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbDisponilbles, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbTusAsientos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbRojo, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbGris, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(lbVerde, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbPantalla, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contenedorPrincipalLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbA1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, contenedorPrincipalLayout.createSequentialGroup()
                                .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnA1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnA3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnA4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnA5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnA6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnA7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnA8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnA9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnA10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnA11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnA12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnA13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnA14, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnA15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnA16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnA2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnB1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnB3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnB4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnB5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnB6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnB7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnB8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnB9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnB10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnB11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnB12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnB13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnB14, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnB15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnB16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnB2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbB1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnC1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC14, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC17, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbC1)
                                    .addComponent(lbC2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnD1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnD3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnD4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnD5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnD6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnD7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnD8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnD9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnD10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnD11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnD12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnD13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnD14, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnD15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnD16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnD17, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnD18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnD2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbD1)
                                    .addComponent(lbD2))
                                .addGap(10, 10, 10)
                                .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnE1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE14, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE17, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbE1)
                                    .addComponent(lbE2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnF1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnF3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnF4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnF5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnF6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnF7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnF8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnF9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnF10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnF11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnF12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnF13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnF14, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnF15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnF16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnF17, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnF18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnF2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbF1)
                                    .addComponent(lbF2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnG1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnG3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnG4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnG5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnG6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnG7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnG8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnG9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnG10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnG11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnG12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnG13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnG14, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnG15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnG16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnG17, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnG18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnG2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbG1)
                                    .addComponent(lbG2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbH1)
                                    .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnH1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnH2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnH3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnH4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnH5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnH6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnH7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnH8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnH9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnH10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnH11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnH12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnH13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnH14, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnH15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnH16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnH18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnH17, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contenedorPrincipalLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(contenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbA2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, contenedorPrincipalLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(lbB2)))
                        .addGap(352, 352, 352))))
        );

        btnSeleccionarAsientos.setBackground(new java.awt.Color(201, 176, 102));
        btnSeleccionarAsientos.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        btnSeleccionarAsientos.setText("Seleccionar");
        btnSeleccionarAsientos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSeleccionarAsientos.addActionListener(this::btnSeleccionarAsientosActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contenedorPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 1211, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSeleccionarAsientos, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(contenedorPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSeleccionarAsientos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnD18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD18ActionPerformed
        // TODO add your handling code here:
        gestionarClicAsiento(btnD18);
    }//GEN-LAST:event_btnD18ActionPerformed

    private void btnD3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnD3ActionPerformed

    private void btnD12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnD12ActionPerformed

    private void btnD17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnD17ActionPerformed

    private void btnD8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnD8ActionPerformed

    private void btnD15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnD15ActionPerformed

    private void btnD10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnD10ActionPerformed

    private void btnD6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnD6ActionPerformed

    private void btnD13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnD13ActionPerformed

    private void btnD14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnD14ActionPerformed

    private void btnD11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnD11ActionPerformed

    private void btnD4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnD4ActionPerformed

    private void btnD1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnD1ActionPerformed

    private void btnD7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnD7ActionPerformed

    private void btnD9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnD9ActionPerformed

    private void btnD5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnD5ActionPerformed

    private void btnD2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnD2ActionPerformed

    private void btnD16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnD16ActionPerformed

    private void btnC3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC3ActionPerformed

    private void btnC12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC12ActionPerformed

    private void btnC17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC17ActionPerformed

    private void btnC8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC8ActionPerformed

    private void btnC15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC15ActionPerformed

    private void btnC10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC10ActionPerformed

    private void btnC6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC6ActionPerformed

    private void btnC13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC13ActionPerformed

    private void btnC14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC14ActionPerformed

    private void btnC11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC11ActionPerformed

    private void btnC4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC4ActionPerformed

    private void btnC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC1ActionPerformed

    private void btnC7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC7ActionPerformed

    private void btnC9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC9ActionPerformed

    private void btnC5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC5ActionPerformed

    private void btnC2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC2ActionPerformed

    private void btnC16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC16ActionPerformed

    private void btnC18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnC18ActionPerformed

    private void btnB2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB2ActionPerformed

    private void btnB18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB18ActionPerformed

    private void btnB17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB17ActionPerformed

    private void btnB15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB15ActionPerformed

    private void btnB5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB5ActionPerformed

    private void btnB3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB3ActionPerformed

    private void btnB4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB4ActionPerformed

    private void btnB1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB1ActionPerformed

    private void btnB16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB16ActionPerformed

    private void btnB14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB14ActionPerformed

    private void btnB13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB13ActionPerformed

    private void btnB12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB12ActionPerformed

    private void btnB11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB11ActionPerformed

    private void btnB10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB10ActionPerformed

    private void btnB9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB9ActionPerformed

    private void btnB8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB8ActionPerformed

    private void btnB7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB7ActionPerformed

    private void btnB6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnB6ActionPerformed

    private void btnF1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF1ActionPerformed

    private void btnF16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF16ActionPerformed

    private void btnF14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF14ActionPerformed

    private void btnF13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF13ActionPerformed

    private void btnF12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF12ActionPerformed

    private void btnF11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF11ActionPerformed

    private void btnF10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF10ActionPerformed

    private void btnF9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF9ActionPerformed

    private void btnF8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF8ActionPerformed

    private void btnF7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF7ActionPerformed

    private void btnF6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF6ActionPerformed

    private void btnE2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE2ActionPerformed

    private void btnE18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE18ActionPerformed

    private void btnE17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE17ActionPerformed

    private void btnE15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE15ActionPerformed

    private void btnE5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE5ActionPerformed

    private void btnE3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE3ActionPerformed

    private void btnE4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE4ActionPerformed

    private void btnE1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE1ActionPerformed

    private void btnE16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE16ActionPerformed

    private void btnE14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE14ActionPerformed

    private void btnE13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE13ActionPerformed

    private void btnE12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE12ActionPerformed

    private void btnE11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE11ActionPerformed

    private void btnE10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE10ActionPerformed

    private void btnE9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE9ActionPerformed

    private void btnE8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE8ActionPerformed

    private void btnE7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE7ActionPerformed

    private void btnE6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnE6ActionPerformed

    private void btnF2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF2ActionPerformed

    private void btnF18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF18ActionPerformed

    private void btnF17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF17ActionPerformed

    private void btnF15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF15ActionPerformed

    private void btnF5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF5ActionPerformed

    private void btnF3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF3ActionPerformed

    private void btnF4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnF4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnF4ActionPerformed

    private void btnG2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG2ActionPerformed

    private void btnG18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG18ActionPerformed

    private void btnG17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG17ActionPerformed

    private void btnG15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG15ActionPerformed

    private void btnG5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG5ActionPerformed

    private void btnG3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG3ActionPerformed

    private void btnG4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG4ActionPerformed

    private void btnG1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG1ActionPerformed

    private void btnG16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG16ActionPerformed

    private void btnG14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG14ActionPerformed

    private void btnG13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG13ActionPerformed

    private void btnG12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG12ActionPerformed

    private void btnG11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG11ActionPerformed

    private void btnG10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG10ActionPerformed

    private void btnG9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG9ActionPerformed

    private void btnG8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG8ActionPerformed

    private void btnG7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG7ActionPerformed

    private void btnG6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnG6ActionPerformed

    private void btnH2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH2ActionPerformed

    private void btnH18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH18ActionPerformed

    private void btnH17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH17ActionPerformed

    private void btnH15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH15ActionPerformed

    private void btnH5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH5ActionPerformed

    private void btnH3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH3ActionPerformed

    private void btnH4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH4ActionPerformed

    private void btnH1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH1ActionPerformed

    private void btnH16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH16ActionPerformed

    private void btnH14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH14ActionPerformed

    private void btnH13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH13ActionPerformed

    private void btnH12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH12ActionPerformed

    private void btnH11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH11ActionPerformed

    private void btnH10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH10ActionPerformed

    private void btnH9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH9ActionPerformed

    private void btnH8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH8ActionPerformed

    private void btnH7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH7ActionPerformed

    private void btnH6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnH6ActionPerformed

    private void btnA16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA16ActionPerformed

    private void btnA14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA14ActionPerformed

    private void btnA13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA13ActionPerformed

    private void btnA12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA12ActionPerformed

    private void btnA11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA11ActionPerformed

    private void btnA10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA10ActionPerformed

    private void btnA9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA9ActionPerformed

    private void btnA8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA8ActionPerformed

    private void btnA7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA7ActionPerformed

    private void btnA6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA6ActionPerformed

    private void btnA2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA2ActionPerformed

    private void btnA18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA18ActionPerformed

    private void btnA17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA17ActionPerformed

    private void btnA15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA15ActionPerformed

    private void btnA5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA5ActionPerformed

    private void btnA3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA3ActionPerformed

    private void btnA4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA4ActionPerformed

    private void btnA1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnA1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnA1ActionPerformed

    private void btnSeleccionarAsientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarAsientosActionPerformed
                // Se obtienen los asientos que se seleccionaron
        List<String> asientosElegidos = obtenerAsientosSeleccionados();
        
        // Se coge la ventana principal
        Component ancestro = SwingUtilities.getWindowAncestor(this);
        
        if (ancestro instanceof VistaPrincipal principal) {

            for (Component comp : principal.contenedorPrincipal.getComponents()) {
                if (comp instanceof VistaPago pantallaPago) {
                    
                    pantallaPago.cargarResumenCompleto(
                        this.peliculaActual, 
                        this.fechaSeleccionada, 
                        this.horaSeleccionada, 
                        this.salaSeleccionada, // <--- ¡Usamos la variable dinámica en lugar de "3"!
                        asientosElegidos
                    );
                    break;
                }
            }
            
            // Se cambia a la pantalla de pago
            principal.cambiarPantalla("pantallaPago");
        }
    }//GEN-LAST:event_btnSeleccionarAsientosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnA1;
    public javax.swing.JButton btnA10;
    public javax.swing.JButton btnA11;
    public javax.swing.JButton btnA12;
    public javax.swing.JButton btnA13;
    public javax.swing.JButton btnA14;
    public javax.swing.JButton btnA15;
    public javax.swing.JButton btnA16;
    public javax.swing.JButton btnA2;
    public javax.swing.JButton btnA3;
    public javax.swing.JButton btnA4;
    public javax.swing.JButton btnA5;
    public javax.swing.JButton btnA6;
    public javax.swing.JButton btnA7;
    public javax.swing.JButton btnA8;
    public javax.swing.JButton btnA9;
    public javax.swing.JButton btnB1;
    public javax.swing.JButton btnB10;
    public javax.swing.JButton btnB11;
    public javax.swing.JButton btnB12;
    public javax.swing.JButton btnB13;
    public javax.swing.JButton btnB14;
    public javax.swing.JButton btnB15;
    public javax.swing.JButton btnB16;
    public javax.swing.JButton btnB2;
    public javax.swing.JButton btnB3;
    public javax.swing.JButton btnB4;
    public javax.swing.JButton btnB5;
    public javax.swing.JButton btnB6;
    public javax.swing.JButton btnB7;
    public javax.swing.JButton btnB8;
    public javax.swing.JButton btnB9;
    public javax.swing.JButton btnC1;
    public javax.swing.JButton btnC10;
    public javax.swing.JButton btnC11;
    public javax.swing.JButton btnC12;
    public javax.swing.JButton btnC13;
    public javax.swing.JButton btnC14;
    public javax.swing.JButton btnC15;
    public javax.swing.JButton btnC16;
    public javax.swing.JButton btnC17;
    public javax.swing.JButton btnC18;
    public javax.swing.JButton btnC2;
    public javax.swing.JButton btnC3;
    public javax.swing.JButton btnC4;
    public javax.swing.JButton btnC5;
    public javax.swing.JButton btnC6;
    public javax.swing.JButton btnC7;
    public javax.swing.JButton btnC8;
    public javax.swing.JButton btnC9;
    public javax.swing.JButton btnD1;
    public javax.swing.JButton btnD10;
    public javax.swing.JButton btnD11;
    public javax.swing.JButton btnD12;
    public javax.swing.JButton btnD13;
    public javax.swing.JButton btnD14;
    public javax.swing.JButton btnD15;
    public javax.swing.JButton btnD16;
    public javax.swing.JButton btnD17;
    public javax.swing.JButton btnD18;
    public javax.swing.JButton btnD2;
    public javax.swing.JButton btnD3;
    public javax.swing.JButton btnD4;
    public javax.swing.JButton btnD5;
    public javax.swing.JButton btnD6;
    public javax.swing.JButton btnD7;
    public javax.swing.JButton btnD8;
    public javax.swing.JButton btnD9;
    public javax.swing.JButton btnE1;
    public javax.swing.JButton btnE10;
    public javax.swing.JButton btnE11;
    public javax.swing.JButton btnE12;
    public javax.swing.JButton btnE13;
    public javax.swing.JButton btnE14;
    public javax.swing.JButton btnE15;
    public javax.swing.JButton btnE16;
    public javax.swing.JButton btnE17;
    public javax.swing.JButton btnE18;
    public javax.swing.JButton btnE2;
    public javax.swing.JButton btnE3;
    public javax.swing.JButton btnE4;
    public javax.swing.JButton btnE5;
    public javax.swing.JButton btnE6;
    public javax.swing.JButton btnE7;
    public javax.swing.JButton btnE8;
    public javax.swing.JButton btnE9;
    public javax.swing.JButton btnF1;
    public javax.swing.JButton btnF10;
    public javax.swing.JButton btnF11;
    public javax.swing.JButton btnF12;
    public javax.swing.JButton btnF13;
    public javax.swing.JButton btnF14;
    public javax.swing.JButton btnF15;
    public javax.swing.JButton btnF16;
    public javax.swing.JButton btnF17;
    public javax.swing.JButton btnF18;
    public javax.swing.JButton btnF2;
    public javax.swing.JButton btnF3;
    public javax.swing.JButton btnF4;
    public javax.swing.JButton btnF5;
    public javax.swing.JButton btnF6;
    public javax.swing.JButton btnF7;
    public javax.swing.JButton btnF8;
    public javax.swing.JButton btnF9;
    public javax.swing.JButton btnG1;
    public javax.swing.JButton btnG10;
    public javax.swing.JButton btnG11;
    public javax.swing.JButton btnG12;
    public javax.swing.JButton btnG13;
    public javax.swing.JButton btnG14;
    public javax.swing.JButton btnG15;
    public javax.swing.JButton btnG16;
    public javax.swing.JButton btnG17;
    public javax.swing.JButton btnG18;
    public javax.swing.JButton btnG2;
    public javax.swing.JButton btnG3;
    public javax.swing.JButton btnG4;
    public javax.swing.JButton btnG5;
    public javax.swing.JButton btnG6;
    public javax.swing.JButton btnG7;
    public javax.swing.JButton btnG8;
    public javax.swing.JButton btnG9;
    public javax.swing.JButton btnH1;
    public javax.swing.JButton btnH10;
    public javax.swing.JButton btnH11;
    public javax.swing.JButton btnH12;
    public javax.swing.JButton btnH13;
    public javax.swing.JButton btnH14;
    public javax.swing.JButton btnH15;
    public javax.swing.JButton btnH16;
    public javax.swing.JButton btnH17;
    public javax.swing.JButton btnH18;
    public javax.swing.JButton btnH2;
    public javax.swing.JButton btnH3;
    public javax.swing.JButton btnH4;
    public javax.swing.JButton btnH5;
    public javax.swing.JButton btnH6;
    public javax.swing.JButton btnH7;
    public javax.swing.JButton btnH8;
    public javax.swing.JButton btnH9;
    public javax.swing.JButton btnSeleccionarAsientos;
    private javax.swing.JPanel contenedorPrincipal;
    public javax.swing.JLabel lbA1;
    public javax.swing.JLabel lbA2;
    public javax.swing.JLabel lbB1;
    public javax.swing.JLabel lbB2;
    public javax.swing.JLabel lbC1;
    public javax.swing.JLabel lbC2;
    public javax.swing.JLabel lbD1;
    public javax.swing.JLabel lbD2;
    public javax.swing.JLabel lbDisponilbles;
    public javax.swing.JLabel lbE1;
    public javax.swing.JLabel lbE2;
    public javax.swing.JLabel lbF1;
    public javax.swing.JLabel lbF2;
    public javax.swing.JLabel lbG1;
    public javax.swing.JLabel lbG2;
    public javax.swing.JLabel lbGris;
    public javax.swing.JLabel lbH1;
    public javax.swing.JLabel lbPantalla;
    public javax.swing.JLabel lbReservados;
    public javax.swing.JLabel lbRojo;
    public javax.swing.JLabel lbSelecion;
    public javax.swing.JLabel lbTusAsientos;
    public javax.swing.JLabel lbVerde;
    // End of variables declaration//GEN-END:variables
}
