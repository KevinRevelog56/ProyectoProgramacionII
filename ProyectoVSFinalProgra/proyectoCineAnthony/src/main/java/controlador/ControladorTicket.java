package controlador;

import modelo.dao.BoletoDAO;
import modelo.entidad.Boleto;
import java.util.UUID;

public class ControladorTicket {
    private BoletoDAO boletoDAO;

    public ControladorTicket() {
        this.boletoDAO = new BoletoDAO();
    }

    public String generarIdBoleto(String tituloPelicula) {
        String tresPelis = tituloPelicula.length() >= 3 ? tituloPelicula.substring(0, 3).toUpperCase() : "CIN";
        String uuidUnico = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
        return tresPelis + "-" + uuidUnico;
    }

    public void registrarYProcesarBoleto(String id, String pelicula, String funcion, String sala, String asientos, String tipoPago, double total) {
        // Se lo pasamos como último argumento al constructor de Boleto
        Boleto nuevoBoleto = new Boleto(id, pelicula, funcion, sala, asientos, tipoPago, total);
        boletoDAO.guardar(nuevoBoleto);
    }
}