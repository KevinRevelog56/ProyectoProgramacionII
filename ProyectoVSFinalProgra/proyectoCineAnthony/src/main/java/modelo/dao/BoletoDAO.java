package modelo.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import modelo.entidad.Boleto;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BoletoDAO implements IRepositorioBoleto {
    private static List<Boleto> listaBoletos = new ArrayList<>();
    private static final String RUTA_ARCHIVO = "Datos/boletos_vendidos.csv";

    @Override
    public void guardar(Boleto boleto) {
        //guardar en memoria RAM
        listaBoletos.add(boleto);
        
        //guardar en archivo CSV
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_ARCHIVO, true))) {
            //se incluye el total
            String linea = String.format(Locale.US, "%s;%s;%s;%s;%s;%s;%.2f",
                boleto.getId(),
                boleto.getPelicula(),
                boleto.getFuncion(),
                boleto.getSala(),
                boleto.getAsientos(),
                boleto.getTipoPago(),
                boleto.getTotal()
            );
            pw.println(linea);
            System.out.println("Boleto guardado con éxito en " + RUTA_ARCHIVO);
        } catch (Exception e) {
            System.err.println("Error al escribir el boleto en el archivo: " + e.getMessage());
        }
    }

    public List<Boleto> obtenerBoletos() {
    List<Boleto> listaLeida = new java.util.ArrayList<>();
    File archivo = new File("Datos/boletos_vendidos.csv");
    
    // Si el archivo todavía no existe, devolvemos la lista vacía
    if (!archivo.exists()) {
        return listaLeida;
    }
    
    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) {
                continue;
            }
            
            //se hace separacion por ;
            String[] partes = linea.split(";");
            
            //se verifica que tenga las 7 partes para evitar errores
            if (partes.length >= 7) {
                String id = partes[0].trim();
                String pelicula = partes[1].trim();
                String funcion = partes[2].trim();
                String Sala = partes[3].trim();
                String asientos = partes[4].trim();
                String metodoPago = partes[5].trim();
                double total = Double.parseDouble(partes[6].trim());
                
                //se crea el objeto Boleto
                Boleto b = new Boleto(id, pelicula, funcion, Sala, asientos, metodoPago, total);
                listaLeida.add(b);
            }
        }
    } catch (java.io.IOException e) {
        System.err.println("Error al leer el archivo de boletos: " + e.getMessage());
    }
    
    return listaLeida;
}
}