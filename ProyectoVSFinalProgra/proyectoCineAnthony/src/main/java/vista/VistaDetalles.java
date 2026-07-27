/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista;

import controlador.ControladorDetalles;
import controlador.GestorCine;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.entidad.Funcion;
import modelo.entidad.Pelicula;
import modelo.entidad.Sala;

/**
 *
 * @author Matth
 */
public class VistaDetalles extends javax.swing.JPanel {
    private JButton botonFechaSeleccionado = null;
    private JButton botonHoraSeleccionado = null;
    private final Color COLOR_NORMAL = new Color(255, 255, 255); 
    private final Color COLOR_SELECCIONADO = new Color(174,149,90); 
    private int cantidadEntradas = 0; 
    private Pelicula peliculaActual;
    private String salaSeleccionada = "";
    private ControladorDetalles controladorDetalles;

        public void cargarDatosDetalle(Pelicula p) {
        this.peliculaActual = p;
        lblNombreDetalles.setText(p.getTitulo());
        txtSipnopsis.setText(p.getSipnopsis());
        lblDuracion.setText(String.valueOf(p.getDuracion()));
        lblGenero.setText(p.getGenero());
        lblClasificacion.setText(p.getClasificacion());
        lblPrecioUna.setText(String.format("$%.2f", p.getPrecio()));
        lblPrecioIndividual.setText("0.00");
        btnConfirmar.setEnabled(false);
        btnConfirmar.setBackground(Color.GRAY);
        lblIdPelicula.setText(String.valueOf(p.getId()));
        
        controladorDetalles.cargarPosterPelicula(p, lblPoster);
        
        // Limpiamos selecciones previas
        botonHoraSeleccionado = null;
        salaSeleccionada = "";
        
        // Delegamos la carga de horas al controlador
        controladorDetalles.cargarBotonesHoras(p.getId(), panelHora, COLOR_NORMAL, COLOR_SELECCIONADO, this);
    }
    
        public void seleccionarBotonHora(JButton btn, Color cNormal, Color cSeleccionado) {
        if (botonHoraSeleccionado != null) {
            botonHoraSeleccionado.setBackground(cNormal);
        }
        botonHoraSeleccionado = btn;
        botonHoraSeleccionado.setBackground(cSeleccionado);
        validarSeleccion();
    }
        public void setSalaSeleccionada(String sala) {
        this.salaSeleccionada = sala;
    }
    private void validarSeleccion() {
        if (botonFechaSeleccionado != null && botonHoraSeleccionado != null && cantidadEntradas > 0) {
            btnConfirmar.setEnabled(true);
            btnConfirmar.setBackground(new Color(174,149,90)); 
        } else {
            btnConfirmar.setEnabled(false);
            btnConfirmar.setBackground(Color.GRAY); 
        }
    }
    
     public void limpiarFormularioPelicula() {
        lblContadorEntradas.setText("0");
        cantidadEntradas = 0;
        this.botonFechaSeleccionado = null;
        this.botonHoraSeleccionado = null;

        JButton[] botones = {btnHora14, btnHora15, btnHora16, btnHora17, btnHora18, btnHora19, btnHora20, btnHora21, btnHora22, btnHora23, btnHora24, btnDia1, btnDia2, btnDia3, btnDia4, btnDia5, btnDia6, btnDia7, btnDia8, btnDia9, btnDia10, btnDia11, btnDia12, btnDia13, btnDia14, btnDia15, btnDia16, btnDia17, btnDia18, btnDia19, btnDia20, btnDia21, btnDia22, btnDia23, btnDia24, btnDia25, btnDia26, btnDia27, btnDia28, btnDia29, btnDia30, btnDia31};
        for (JButton btn : botones) {
            if (btn != null) {
                btn.setBackground(COLOR_NORMAL);
                btn.setOpaque(true);
            }
        }
        btnConfirmar.setEnabled(false);
        btnConfirmar.setBackground(Color.GRAY);
    }
    /**
     * Creates new form peliculaIndividual
     */
    public VistaDetalles() {
       initComponents();
        panelFunciones.getHorizontalScrollBar().setUnitIncrement(30);
        panelHora.getHorizontalScrollBar().setUnitIncrement(15);
        controladorDetalles = new ControladorDetalles();
        
        txtSipnopsis.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtSipnopsis.setOpaque(false);
        txtSipnopsis.setLineWrap(true);
        txtSipnopsis.setWrapStyleWord(true);
        
        JButton[] botonesDias = {
            btnDia1, btnDia2, btnDia3, btnDia4, btnDia5, btnDia6, btnDia7, btnDia8, btnDia9, btnDia10,
            btnDia11, btnDia12, btnDia13, btnDia14, btnDia15, btnDia16, btnDia17, btnDia18, btnDia19, btnDia20,
            btnDia21, btnDia22, btnDia23, btnDia24, btnDia25, btnDia26, btnDia27, btnDia28, btnDia29, btnDia30, btnDia31
        };
        
        for (JButton boton : botonesDias) {
            boton.addActionListener(evt -> {
                if (botonFechaSeleccionado == boton) {
                    botonFechaSeleccionado.setBackground(COLOR_NORMAL);
                    botonFechaSeleccionado = null; 
                } else {
                    if (botonFechaSeleccionado != null) {
                        botonFechaSeleccionado.setBackground(COLOR_NORMAL);
                    }
                    botonFechaSeleccionado = boton;
                    botonFechaSeleccionado.setBackground(COLOR_SELECCIONADO);
                }
                validarSeleccion();
            });
        }
        
        lblMes.setText("Cartelera de: " + controladorDetalles.obtenerMesActualFormateado());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNombreDetalles = new javax.swing.JLabel();
        lblPoster = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblDuracion = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblGenero = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblClasificacion = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        panelFunciones = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        btnDia6 = new javax.swing.JButton();
        btnDia1 = new javax.swing.JButton();
        btnDia2 = new javax.swing.JButton();
        btnDia3 = new javax.swing.JButton();
        btnDia4 = new javax.swing.JButton();
        btnDia5 = new javax.swing.JButton();
        btnDia7 = new javax.swing.JButton();
        btnDia8 = new javax.swing.JButton();
        btnDia9 = new javax.swing.JButton();
        btnDia10 = new javax.swing.JButton();
        btnDia11 = new javax.swing.JButton();
        btnDia15 = new javax.swing.JButton();
        btnDia12 = new javax.swing.JButton();
        btnDia13 = new javax.swing.JButton();
        btnDia14 = new javax.swing.JButton();
        btnDia21 = new javax.swing.JButton();
        btnDia16 = new javax.swing.JButton();
        btnDia17 = new javax.swing.JButton();
        btnDia18 = new javax.swing.JButton();
        btnDia19 = new javax.swing.JButton();
        btnDia20 = new javax.swing.JButton();
        btnDia22 = new javax.swing.JButton();
        btnDia23 = new javax.swing.JButton();
        btnDia24 = new javax.swing.JButton();
        btnDia25 = new javax.swing.JButton();
        btnDia26 = new javax.swing.JButton();
        btnDia31 = new javax.swing.JButton();
        btnDia27 = new javax.swing.JButton();
        btnDia28 = new javax.swing.JButton();
        btnDia29 = new javax.swing.JButton();
        btnDia30 = new javax.swing.JButton();
        panelHora = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        btnHora14 = new javax.swing.JButton();
        btnHora15 = new javax.swing.JButton();
        btnHora16 = new javax.swing.JButton();
        btnHora17 = new javax.swing.JButton();
        btnHora18 = new javax.swing.JButton();
        btnHora19 = new javax.swing.JButton();
        btnHora20 = new javax.swing.JButton();
        btnHora21 = new javax.swing.JButton();
        btnHora22 = new javax.swing.JButton();
        btnHora23 = new javax.swing.JButton();
        btnHora24 = new javax.swing.JButton();
        lblMes = new javax.swing.JLabel();
        pnlEntrada = new javax.swing.JPanel();
        lblPrecioIndividual = new javax.swing.JLabel();
        btnMenos = new javax.swing.JButton();
        btnMas = new javax.swing.JButton();
        lblContadorEntradas = new javax.swing.JLabel();
        lblPrecioUna = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSipnopsis = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        lblIdPelicula = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(44, 44, 44));
        setPreferredSize(new java.awt.Dimension(1256, 591));

        lblNombreDetalles.setFont(new java.awt.Font("Sitka Text", 1, 24)); // NOI18N
        lblNombreDetalles.setForeground(new java.awt.Color(201, 176, 102));
        lblNombreDetalles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNombreDetalles.setText("NOMBREE");

        lblPoster.setBackground(new java.awt.Color(255, 255, 255));
        lblPoster.setOpaque(true);
        lblPoster.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPosterMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Género:");

        lblDuracion.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        lblDuracion.setForeground(new java.awt.Color(255, 255, 255));
        lblDuracion.setText("180");

        jLabel4.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Duración:");

        jLabel6.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("min");

        lblGenero.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        lblGenero.setForeground(new java.awt.Color(255, 255, 255));
        lblGenero.setText("Terror");

        jLabel5.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Clasificación:");

        lblClasificacion.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        lblClasificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblClasificacion.setText("+15");

        jLabel8.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(201, 176, 102));
        jLabel8.setText("Sipnopsis:");

        jLabel1.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(201, 176, 102));
        jLabel1.setText("Funciones");

        panelFunciones.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelFunciones.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        panelFunciones.setPreferredSize(new java.awt.Dimension(100, 80));

        jPanel1.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanel1.setPreferredSize(new java.awt.Dimension(2190, 80));

        btnDia6.setText("6");
        btnDia6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia1.setText("1");
        btnDia1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDia1.addActionListener(this::btnDia1ActionPerformed);

        btnDia2.setText("2");
        btnDia2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia3.setText("3");
        btnDia3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia4.setText("4");
        btnDia4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia5.setText("5");
        btnDia5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia7.setText("7");
        btnDia7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia8.setText("8");
        btnDia8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia9.setText("9");
        btnDia9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia10.setText("10");
        btnDia10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia11.setText("11");
        btnDia11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia15.setText("15");
        btnDia15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia12.setText("12");
        btnDia12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia13.setText("13");
        btnDia13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia14.setText("14");
        btnDia14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia21.setText("21");
        btnDia21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia16.setText("16");
        btnDia16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia17.setText("17");
        btnDia17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia18.setText("18");
        btnDia18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia19.setText("19");
        btnDia19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia20.setText("20");
        btnDia20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia22.setText("22");
        btnDia22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia23.setText("23");
        btnDia23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia24.setText("24");
        btnDia24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia25.setText("25");
        btnDia25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia26.setText("26");
        btnDia26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia31.setText("31");
        btnDia31.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia27.setText("27");
        btnDia27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia28.setText("28");
        btnDia28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia29.setText("29");
        btnDia29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDia30.setText("30");
        btnDia30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnDia1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia12, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia14, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia15, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia17, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia18, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia19, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia20, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia21, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia22, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia23, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia24, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia25, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia26, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia27, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia28, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia29, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia30, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDia31, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDia1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia12, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia14, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia15, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia17, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia18, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia19, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia20, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia21, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia22, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia23, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia24, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia25, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia26, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia27, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia28, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia29, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia30, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia31, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelFunciones.setViewportView(jPanel1);

        panelHora.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelHora.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        panelHora.setPreferredSize(new java.awt.Dimension(100, 80));

        jPanel2.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanel2.setPreferredSize(new java.awt.Dimension(1006, 80));

        btnHora14.setText("14:00");
        btnHora14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHora14.addActionListener(this::btnHora14ActionPerformed);
        jPanel2.add(btnHora14);

        btnHora15.setText("24:00");
        btnHora15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHora15.addActionListener(this::btnHora15ActionPerformed);
        jPanel2.add(btnHora15);

        btnHora16.setText("15:00");
        btnHora16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHora16.addActionListener(this::btnHora16ActionPerformed);
        jPanel2.add(btnHora16);

        btnHora17.setText("16:00");
        btnHora17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHora17.addActionListener(this::btnHora17ActionPerformed);
        jPanel2.add(btnHora17);

        btnHora18.setText("17:00");
        btnHora18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHora18.addActionListener(this::btnHora18ActionPerformed);
        jPanel2.add(btnHora18);

        btnHora19.setText("18:00");
        btnHora19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHora19.addActionListener(this::btnHora19ActionPerformed);
        jPanel2.add(btnHora19);

        btnHora20.setText("19:00");
        btnHora20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHora20.addActionListener(this::btnHora20ActionPerformed);
        jPanel2.add(btnHora20);

        btnHora21.setText("20:00");
        btnHora21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHora21.addActionListener(this::btnHora21ActionPerformed);
        jPanel2.add(btnHora21);

        btnHora22.setText("21:00");
        btnHora22.setToolTipText("");
        btnHora22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHora22.addActionListener(this::btnHora22ActionPerformed);
        jPanel2.add(btnHora22);

        btnHora23.setText("22:00");
        btnHora23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHora23.addActionListener(this::btnHora23ActionPerformed);
        jPanel2.add(btnHora23);

        btnHora24.setText("23:00");
        btnHora24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHora24.addActionListener(this::btnHora24ActionPerformed);
        jPanel2.add(btnHora24);

        panelHora.setViewportView(jPanel2);

        lblMes.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblMes.setForeground(new java.awt.Color(201, 176, 102));
        lblMes.setText("Julio");

        lblPrecioIndividual.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblPrecioIndividual.setText("$4.5");

        btnMenos.setText("-");
        btnMenos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMenos.addActionListener(this::btnMenosActionPerformed);

        btnMas.setText("+");
        btnMas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMas.addActionListener(this::btnMasActionPerformed);

        lblContadorEntradas.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblContadorEntradas.setText("0");

        lblPrecioUna.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        lblPrecioUna.setText("$0.00");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("TOTAL:");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel7.setText("PRECIO UNITARIO:");

        javax.swing.GroupLayout pnlEntradaLayout = new javax.swing.GroupLayout(pnlEntrada);
        pnlEntrada.setLayout(pnlEntradaLayout);
        pnlEntradaLayout.setHorizontalGroup(
            pnlEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEntradaLayout.createSequentialGroup()
                .addContainerGap(116, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(lblPrecioIndividual)
                .addGap(78, 78, 78)
                .addComponent(btnMenos, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblContadorEntradas)
                .addGap(15, 15, 15)
                .addComponent(btnMas, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(pnlEntradaLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(lblPrecioUna)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlEntradaLayout.setVerticalGroup(
            pnlEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEntradaLayout.createSequentialGroup()
                .addGroup(pnlEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEntradaLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(pnlEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPrecioUna)
                            .addComponent(jLabel7))
                        .addGap(3, 3, 3)
                        .addGroup(pnlEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnMenos, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMas, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblContadorEntradas)))
                    .addGroup(pnlEntradaLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(pnlEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lblPrecioIndividual))))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        btnConfirmar.setBackground(new java.awt.Color(102, 255, 102));
        btnConfirmar.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        btnConfirmar.setText("Confirmar Compra");
        btnConfirmar.setBorderPainted(false);
        btnConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfirmar.addActionListener(this::btnConfirmarActionPerformed);

        txtSipnopsis.setBackground(new java.awt.Color(70, 70, 70));
        txtSipnopsis.setColumns(20);
        txtSipnopsis.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtSipnopsis.setRows(5);
        txtSipnopsis.setBorder(null);
        txtSipnopsis.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtSipnopsis.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtSipnopsis.setFocusable(false);
        jScrollPane1.setViewportView(txtSipnopsis);

        jLabel9.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("ID:");

        lblIdPelicula.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        lblIdPelicula.setForeground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Sitka Text", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(201, 176, 102));
        jLabel10.setText("Detalles de la Pelucula ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(375, 375, 375))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(lblNombreDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblDuracion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblClasificacion))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblGenero))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblIdPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(lblPoster, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel8)))))
                .addGap(111, 111, 111)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMes)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(panelFunciones, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(21, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(panelHora, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(pnlEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addComponent(jLabel1)))
            .addGroup(layout.createSequentialGroup()
                .addGap(415, 415, 415)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(lblMes)
                        .addGap(18, 18, 18)
                        .addComponent(panelFunciones, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(109, 109, 109)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblIdPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGenero))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lblDuracion)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblClasificacion)
                            .addComponent(jLabel5))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(panelHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(pnlEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblNombreDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPoster, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDia1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDia1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDia1ActionPerformed

    private void btnHora14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHora14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHora14ActionPerformed

    private void btnHora15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHora15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHora15ActionPerformed

    private void btnHora16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHora16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHora16ActionPerformed

    private void btnHora17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHora17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHora17ActionPerformed

    private void btnHora18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHora18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHora18ActionPerformed

    private void btnHora19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHora19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHora19ActionPerformed

    private void btnHora20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHora20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHora20ActionPerformed

    private void btnHora21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHora21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHora21ActionPerformed

    private void btnHora22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHora22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHora22ActionPerformed

    private void btnHora23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHora23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHora23ActionPerformed

    private void btnHora24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHora24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHora24ActionPerformed

    private void btnMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenosActionPerformed
       if (cantidadEntradas > 0) {
            cantidadEntradas--; 
            lblContadorEntradas.setText(String.valueOf(cantidadEntradas)); 
            
            //se usa controlador para calcular el subtotal parcial
            double totalParcial = controladorDetalles.calcularTotalParcial(this.peliculaActual, cantidadEntradas);
            lblPrecioIndividual.setText(String.format("$%.2f", totalParcial)); 
            
            validarSeleccion(); 
        }
    }//GEN-LAST:event_btnMenosActionPerformed

    private void btnMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasActionPerformed
       if (cantidadEntradas < 10) { 
            cantidadEntradas++; 
            lblContadorEntradas.setText(String.valueOf(cantidadEntradas)); 
            
            //se usa al controlador para calcular el subtotal parcial
            double totalParcial = controladorDetalles.calcularTotalParcial(this.peliculaActual, cantidadEntradas);
            lblPrecioIndividual.setText(String.format("$%.2f", totalParcial)); 
            
            validarSeleccion();
        }
    }//GEN-LAST:event_btnMasActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        //operadores tenarios para menos codigo
        String diaSeleccionado = botonFechaSeleccionado != null ? botonFechaSeleccionado.getText() : ""; 
        String horaSeleccionada = botonHoraSeleccionado != null ? botonHoraSeleccionado.getText() : "";

        cantidadEntradas = Integer.parseInt(lblContadorEntradas.getText());  

        // se valida
        if (salaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un horario.");
            return;
        }

        JPanel contenedorPrincipal = (javax.swing.JPanel) this.getParent();
        CardLayout layout = (CardLayout) contenedorPrincipal.getLayout();

        for (Component comp : contenedorPrincipal.getComponents()) {
            if (comp instanceof vista.VistaSeleccionAsientos panelAsientos) {
                panelAsientos.setPelicula(this.peliculaActual);
                panelAsientos.setFecha(diaSeleccionado);
                panelAsientos.setHora(horaSeleccionada);
                panelAsientos.setEntradasCompradas(cantidadEntradas);
                panelAsientos.setSala(salaSeleccionada);
                panelAsientos.reiniciarColoresYEstadosBotones();
            }
        }

        layout.show(contenedorPrincipal, "pantallaAsientos");
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void lblPosterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPosterMouseClicked

    }//GEN-LAST:event_lblPosterMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnConfirmar;
    public javax.swing.JButton btnDia1;
    public javax.swing.JButton btnDia10;
    public javax.swing.JButton btnDia11;
    public javax.swing.JButton btnDia12;
    public javax.swing.JButton btnDia13;
    public javax.swing.JButton btnDia14;
    public javax.swing.JButton btnDia15;
    public javax.swing.JButton btnDia16;
    public javax.swing.JButton btnDia17;
    public javax.swing.JButton btnDia18;
    public javax.swing.JButton btnDia19;
    public javax.swing.JButton btnDia2;
    public javax.swing.JButton btnDia20;
    public javax.swing.JButton btnDia21;
    public javax.swing.JButton btnDia22;
    public javax.swing.JButton btnDia23;
    public javax.swing.JButton btnDia24;
    public javax.swing.JButton btnDia25;
    public javax.swing.JButton btnDia26;
    public javax.swing.JButton btnDia27;
    public javax.swing.JButton btnDia28;
    public javax.swing.JButton btnDia29;
    public javax.swing.JButton btnDia3;
    public javax.swing.JButton btnDia30;
    public javax.swing.JButton btnDia31;
    public javax.swing.JButton btnDia4;
    public javax.swing.JButton btnDia5;
    public javax.swing.JButton btnDia6;
    public javax.swing.JButton btnDia7;
    public javax.swing.JButton btnDia8;
    public javax.swing.JButton btnDia9;
    public javax.swing.JButton btnHora14;
    public javax.swing.JButton btnHora15;
    public javax.swing.JButton btnHora16;
    public javax.swing.JButton btnHora17;
    public javax.swing.JButton btnHora18;
    public javax.swing.JButton btnHora19;
    public javax.swing.JButton btnHora20;
    public javax.swing.JButton btnHora21;
    public javax.swing.JButton btnHora22;
    public javax.swing.JButton btnHora23;
    public javax.swing.JButton btnHora24;
    private javax.swing.JButton btnMas;
    private javax.swing.JButton btnMenos;
    public javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lblClasificacion;
    private javax.swing.JLabel lblContadorEntradas;
    public javax.swing.JLabel lblDuracion;
    public javax.swing.JLabel lblGenero;
    private javax.swing.JLabel lblIdPelicula;
    public javax.swing.JLabel lblMes;
    public javax.swing.JLabel lblNombreDetalles;
    public javax.swing.JLabel lblPoster;
    private javax.swing.JLabel lblPrecioIndividual;
    private javax.swing.JLabel lblPrecioUna;
    private javax.swing.JScrollPane panelFunciones;
    private javax.swing.JScrollPane panelHora;
    private javax.swing.JPanel pnlEntrada;
    private javax.swing.JTextArea txtSipnopsis;
    // End of variables declaration//GEN-END:variables
}
