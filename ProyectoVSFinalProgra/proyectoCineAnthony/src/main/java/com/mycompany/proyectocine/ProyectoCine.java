package com.mycompany.proyectocine;

import vista.VistaPrincipal;

/**
 *
 * @author Matth
 */
public class ProyectoCine {

    public static void main(String[] args) {
        // Usamos EventQueue para asegurar que la interfaz gráfica de Swing se ejecute correctamente en su propio hilo
        java.awt.EventQueue.invokeLater(() -> {
            new VistaPrincipal().setVisible(true);
        });
    }
}