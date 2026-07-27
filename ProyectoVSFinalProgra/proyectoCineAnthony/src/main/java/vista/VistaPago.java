package vista;
import controlador.ControladorPago;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import modelo.entidad.Boleto;
import modelo.entidad.Pelicula;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Matth
 */
public class VistaPago extends javax.swing.JPanel {
    private boolean panelTarjetaVisible = false;
    private ControladorPago controladorPago;
  
    public VistaPago() {
        initComponents();
        btnPagar.setEnabled(false);
        btnPagar.setBackground(java.awt.Color.GRAY);

        //listener para leer en timpo real y habilitar el boton de pago
        DocumentListener listenerTexto = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            validarCamposTarjeta(); 
        }
        @Override
        public void removeUpdate(DocumentEvent e) { 
            validarCamposTarjeta(); 
        }
        @Override
        public void changedUpdate(DocumentEvent e) {
            validarCamposTarjeta(); 
        }
};

        // Se lo aplicamos a cada cuadro de texto de la tarjeta
        txtNumeroTarjeta.getDocument().addDocumentListener(listenerTexto);
        txtVencimiento.getDocument().addDocumentListener(listenerTexto);
        txtCvv.getDocument().addDocumentListener(listenerTexto);
        pnlTarjeta.setVisible(false);
        controladorPago = new ControladorPago();
    }
    public void cargarResumenCompleto(Pelicula pelicula, String fecha, String hora, String sala, List<String> asientos) {
    lblNombrePeliculaResumen.setText(pelicula.getTitulo());
        
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatoMes = DateTimeFormatter.ofPattern("MMMM", new Locale("es", "ES"));
        String mesActual = fechaActual.format(formatoMes).toUpperCase();
        
        lblFuncionResumen.setText(fecha + " de " + mesActual + " - " + hora);
        lblSalaResumen.setText(sala);
        lblAsientosResumen.setText(String.join(", ", asientos));
        
        //se designa al controlador los calculos
        double[] totales = controladorPago.calcularTotales(pelicula, asientos.size());
        
        lblSubtotal.setText(String.format(Locale.US, "$%.2f", totales[0]));
lblIva.setText(String.format(Locale.US, "$%.2f", totales[1]));
lblTotal.setText(String.format(Locale.US, "$%.2f", totales[2]));
}

    private void validarCamposTarjeta() {
        String numero = txtNumeroTarjeta.getText().trim();
        String vencimiento = txtVencimiento.getText().trim();
        String cvv = txtCvv.getText().trim();
    
    //se estan con texto original se considera vacio
    if (numero.contains("INGRESE LOS 16 DIGITOS") || numero.length() != 16) {
        btnPagar.setEnabled(false);
        btnPagar.setBackground(java.awt.Color.GRAY);
        return;
    }
    if (vencimiento.contains("DD/MM") || vencimiento.length() != 5) {
        btnPagar.setEnabled(false);
        btnPagar.setBackground(java.awt.Color.GRAY);
        return;
    }
    if (cvv.contains("CVV") || cvv.length() != 3) {
        btnPagar.setEnabled(false);
        btnPagar.setBackground(java.awt.Color.GRAY);
        return;
    }
    
    //se pasa las validaciones, se habilita el boton
    btnPagar.setEnabled(true);
    btnPagar.setBackground(new java.awt.Color(46, 204, 113)); // Verde activo
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlResumen = new javax.swing.JPanel();
        lblNombrePeliculaResumen = new javax.swing.JLabel();
        lblFuncionResumen = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblAsientosResumen = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblSubtotal = new javax.swing.JLabel();
        lblIva = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblSalaResumen = new javax.swing.JLabel();
        lblTicket = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnMetodoTarjeta = new javax.swing.JButton();
        btnMetodoMostrador = new javax.swing.JButton();
        pnlTarjeta = new javax.swing.JPanel();
        txtNumeroTarjeta = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtVencimiento = new javax.swing.JTextField();
        txtCvv = new javax.swing.JTextField();
        btnPagar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(17, 17, 17));
        setPreferredSize(new java.awt.Dimension(1256, 591));

        pnlResumen.setBackground(new java.awt.Color(255, 255, 255));
        pnlResumen.setPreferredSize(new java.awt.Dimension(450, 435));

        lblNombrePeliculaResumen.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblNombrePeliculaResumen.setText("NOMBREE");

        lblFuncionResumen.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblFuncionResumen.setText("12 Julio - 16:00");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel2.setText("PELICULA:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel3.setText("FUNCION:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel4.setText("ASIENTOS:");

        lblAsientosResumen.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblAsientosResumen.setText("G1, G2, G3");

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel6.setText("SUBTOTAL:");

        jLabel7.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel7.setText("IVA (15%):");

        jLabel8.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel8.setText("TOTAL:");

        lblSubtotal.setText("$13.00");

        lblIva.setText("$2.00");

        lblTotal.setText("$15.00");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel12.setText("SALA:");

        lblSalaResumen.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblSalaResumen.setText("3");

        lblTicket.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblTicket.setText("BOLETO TICKET");

        javax.swing.GroupLayout pnlResumenLayout = new javax.swing.GroupLayout(pnlResumen);
        pnlResumen.setLayout(pnlResumenLayout);
        pnlResumenLayout.setHorizontalGroup(
            pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlResumenLayout.createSequentialGroup()
                .addGroup(pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlResumenLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlResumenLayout.createSequentialGroup()
                                .addGroup(pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblIva)
                                    .addComponent(lblSubtotal)
                                    .addComponent(lblTotal)))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlResumenLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(lblAsientosResumen))
                            .addGroup(pnlResumenLayout.createSequentialGroup()
                                .addGroup(pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel12))
                                .addGap(18, 18, 18)
                                .addGroup(pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSalaResumen)
                                    .addComponent(lblNombrePeliculaResumen)
                                    .addComponent(lblFuncionResumen)))))
                    .addGroup(pnlResumenLayout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(lblTicket)))
                .addGap(0, 56, Short.MAX_VALUE))
        );
        pnlResumenLayout.setVerticalGroup(
            pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlResumenLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lblTicket)
                .addGap(44, 44, 44)
                .addGroup(pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombrePeliculaResumen)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFuncionResumen)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSalaResumen, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12))
                .addGap(10, 10, 10)
                .addGroup(pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblAsientosResumen))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblSubtotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblIva))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblTotal))
                .addContainerGap(125, Short.MAX_VALUE))
        );

        jLabel14.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(201, 176, 102));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("SELECCIONA TU METODO DE PAGO");
        jLabel14.setPreferredSize(new java.awt.Dimension(570, 30));

        btnMetodoTarjeta.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        btnMetodoTarjeta.setText("TARJETA");
        btnMetodoTarjeta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnMetodoTarjeta.addActionListener(this::btnMetodoTarjetaActionPerformed);

        btnMetodoMostrador.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        btnMetodoMostrador.setText("MOSTRADOR");
        btnMetodoMostrador.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnMetodoMostrador.addActionListener(this::btnMetodoMostradorActionPerformed);

        pnlTarjeta.setPreferredSize(new java.awt.Dimension(270, 150));

        txtNumeroTarjeta.setFont(new java.awt.Font("sansserif", 0, 12)); // NOI18N
        txtNumeroTarjeta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumeroTarjeta.setText("INGRESE LOS 16 DIGITOS");
        txtNumeroTarjeta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumeroTarjetaFocusGained(evt);
            }
        });
        txtNumeroTarjeta.addActionListener(this::txtNumeroTarjetaActionPerformed);
        txtNumeroTarjeta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroTarjetaKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel15.setText("NUMERO DE TARJETA:");

        jLabel16.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel16.setText("VENCIMIENTO:");

        jLabel17.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel17.setText("CVV:");

        txtVencimiento.setText("DD/MM");
        txtVencimiento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtVencimientoFocusGained(evt);
            }
        });
        txtVencimiento.addActionListener(this::txtVencimientoActionPerformed);
        txtVencimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtVencimientoKeyTyped(evt);
            }
        });

        txtCvv.setText("CVV");
        txtCvv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCvvFocusGained(evt);
            }
        });
        txtCvv.addActionListener(this::txtCvvActionPerformed);
        txtCvv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCvvKeyTyped(evt);
            }
        });

        btnPagar.setBackground(new java.awt.Color(153, 153, 153));
        btnPagar.setText("PAGAR");
        btnPagar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPagar.addActionListener(this::btnPagarActionPerformed);

        btnCancelar.setText("CANCELAR");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        javax.swing.GroupLayout pnlTarjetaLayout = new javax.swing.GroupLayout(pnlTarjeta);
        pnlTarjeta.setLayout(pnlTarjetaLayout);
        pnlTarjetaLayout.setHorizontalGroup(
            pnlTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTarjetaLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(pnlTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTarjetaLayout.createSequentialGroup()
                        .addComponent(btnPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCvv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumeroTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        pnlTarjetaLayout.setVerticalGroup(
            pnlTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTarjetaLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtNumeroTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(txtCvv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(pnlTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        jLabel18.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(201, 176, 102));
        jLabel18.setText("RESUMEN DE COMPRA");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(pnlResumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnMetodoMostrador, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnMetodoTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(pnlTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 99, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel18)
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlResumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnMetodoMostrador, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMetodoTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addComponent(pnlTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumeroTarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroTarjetaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroTarjetaActionPerformed

    private void txtVencimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVencimientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVencimientoActionPerformed

    private void txtCvvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCvvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCvvActionPerformed

    private void btnMetodoTarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMetodoTarjetaActionPerformed
        panelTarjetaVisible = !panelTarjetaVisible;
        pnlTarjeta.setVisible(panelTarjetaVisible);
    }//GEN-LAST:event_btnMetodoTarjetaActionPerformed
    private void btnMetodoMostradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMetodoMostradorActionPerformed
        pnlTarjeta.setVisible(false);
        panelTarjetaVisible = false;
        
        String tituloPelicula = lblNombrePeliculaResumen.getText().trim();
        String funcionInfo = lblFuncionResumen.getText().trim();
        String salaInfo = lblSalaResumen.getText().trim();
        String asientosInfo = lblAsientosResumen.getText().trim();
        double montoTotal = Double.parseDouble(lblTotal.getText().replace("$", "").trim());

        //se llama al controlador de pago
        Boleto boletoGenerado = controladorPago.procesarPagoMostrador(tituloPelicula, funcionInfo, salaInfo, asientosInfo, montoTotal);

        if (boletoGenerado != null) {
            Object[] opciones = {"Sí", "No"};
            int opcion = JOptionPane.showOptionDialog(
                this, 
                "Por favor, presenta tu entrada en el mostrador para realizar el pago.\n¿Deseas ver tu entrada ahora?", 
                "Método en Mostrador", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.INFORMATION_MESSAGE, 
                null, 
                opciones,  
                opciones[0]  
            );
            
            if (opcion == 0) {
                controladorPago.mostrarTicketEntrada(this, "Pago en Mostrador", tituloPelicula, funcionInfo, salaInfo, asientosInfo);
            }
            controladorPago.regresarACartelera(this);
        }
    }//GEN-LAST:event_btnMetodoMostradorActionPerformed

    private void txtNumeroTarjetaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumeroTarjetaFocusGained
        if (txtNumeroTarjeta.getText().equals("INGRESE LOS 16 DIGITOS")) {
             txtNumeroTarjeta.setText("");
        }
    }//GEN-LAST:event_txtNumeroTarjetaFocusGained

    private void txtVencimientoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVencimientoFocusGained
        if (txtVencimiento.getText().equals("DD/MM")) {
             txtVencimiento.setText("");
        }
    }//GEN-LAST:event_txtVencimientoFocusGained

    private void txtCvvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCvvFocusGained
        if (txtCvv.getText().equals("CVV")) {
             txtCvv.setText("");
        }
    }//GEN-LAST:event_txtCvvFocusGained

    private void txtNumeroTarjetaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroTarjetaKeyTyped
        if (txtNumeroTarjeta.getText().length() >= 16) evt.consume();
        if (!Character.isDigit(evt.getKeyChar())) evt.consume();
    }//GEN-LAST:event_txtNumeroTarjetaKeyTyped

    private void txtVencimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVencimientoKeyTyped
        if (txtVencimiento.getText().length() >= 5) evt.consume();
        if (!Character.isDigit(evt.getKeyChar()) && evt.getKeyChar() != '/') evt.consume();
    }//GEN-LAST:event_txtVencimientoKeyTyped

    private void txtCvvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCvvKeyTyped
        if (txtCvv.getText().length() >= 3) evt.consume();
        if (!Character.isDigit(evt.getKeyChar())) evt.consume();
    }//GEN-LAST:event_txtCvvKeyTyped

    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
       if (pnlTarjeta.isVisible()) {
            if (!controladorPago.validarTarjeta(txtNumeroTarjeta.getText(), txtVencimiento.getText(), txtCvv.getText(), this)) {
                return; 
            }
        }
        
        String tituloPelicula = lblNombrePeliculaResumen.getText().trim();
        String funcionInfo = lblFuncionResumen.getText().trim();
        String salaInfo = lblSalaResumen.getText().trim();
        String asientosInfo = lblAsientosResumen.getText().trim();
        double montoTotal = Double.parseDouble(lblTotal.getText().replace("$", "").trim());

        //usamos al controlador de pago
        Boleto boletoGenerado = controladorPago.procesarPagoTarjeta(tituloPelicula, funcionInfo, salaInfo, asientosInfo, montoTotal);

        if (boletoGenerado != null) {
            txtNumeroTarjeta.setText("INGRESE LOS 16 DIGITOS");
            txtVencimiento.setText("DD/MM");
            txtCvv.setText("CVV");

            Object[] opcionesTarjeta = {"Aceptar", "Imprimir Entrada"};
            int seleccion = JOptionPane.showOptionDialog(
                this, 
                "¡Pago procesado con éxito! La transacción se ha completado correctamente.", 
                "Pago Exitoso", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.INFORMATION_MESSAGE, 
                null, 
                opcionesTarjeta,  
                opcionesTarjeta[0]
            );

            if (seleccion == 1) {
                controladorPago.mostrarTicketEntrada(this, "Pagado con Tarjeta", tituloPelicula, funcionInfo, salaInfo, asientosInfo);
            }
            controladorPago.regresarACartelera(this);
        } else {
            JOptionPane.showMessageDialog(this, "El pago con tarjeta no pudo ser autorizado.", "Error de Pago", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnPagarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        int opcion = javax.swing.JOptionPane.showConfirmDialog(
    this, "¿Estás seguro de cancelar?", "Confirmar cancelación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
);

if (opcion == JOptionPane.YES_OPTION) {
    controladorPago.regresarACartelera(this);
}
    }//GEN-LAST:event_btnCancelarActionPerformed
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCancelar;
    public javax.swing.JButton btnMetodoMostrador;
    public javax.swing.JButton btnMetodoTarjeta;
    public javax.swing.JButton btnPagar;
    private javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel16;
    public javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JLabel lblAsientosResumen;
    public javax.swing.JLabel lblFuncionResumen;
    private javax.swing.JLabel lblIva;
    public javax.swing.JLabel lblNombrePeliculaResumen;
    public javax.swing.JLabel lblSalaResumen;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblTicket;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel pnlResumen;
    private javax.swing.JPanel pnlTarjeta;
    public javax.swing.JTextField txtCvv;
    public javax.swing.JTextField txtNumeroTarjeta;
    public javax.swing.JTextField txtVencimiento;
    // End of variables declaration//GEN-END:variables
}
