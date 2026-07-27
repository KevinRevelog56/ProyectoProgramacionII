package modelo.dao;

import java.io.*;
import java.util.*;
import modelo.entidad.Sala;

public class RepositorioSalasCSV implements IRepositorioSalas {
    private File archivoCSV;

    public RepositorioSalasCSV() {
        File carpetaDatos = new File("Datos");
        if (!carpetaDatos.exists()) {
            carpetaDatos.mkdir();
        }
        this.archivoCSV = new File(carpetaDatos, "salas.csv");
    }

    @Override
    public List<Sala> obtenerSalas() {
        List<Sala> salas = new ArrayList<>();
        if (!archivoCSV.exists()) return salas;

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                
                String[] partes = linea.split(";");
                if (partes.length >= 3) {
                    int idSala = Integer.parseInt(partes[0].trim());
                    String nombre = partes[1].trim();
                    int capacidad = Integer.parseInt(partes[2].trim());
                    salas.add(new Sala(idSala, nombre, capacidad));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer salas.csv: " + e.getMessage());
        }
        return salas;
    }

    public boolean crear(Sala sala) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivoCSV, true))) {
            pw.println(sala.getIdSala() + ";" + sala.getNombre().trim() + ";" + sala.getCapacidad());
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar sala: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idSala) {
        List<Sala> lista = obtenerSalas();
        boolean encontrada = lista.removeIf(s -> s.getIdSala() == idSala);

        if (encontrada) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(archivoCSV, false))) {
                for (int i = 0; i < lista.size(); i++) {
                    Sala s = lista.get(i);
                    String linea = s.getIdSala() + ";" + s.getNombre().trim() + ";" + s.getCapacidad();
                    if (i == lista.size() - 1) {
                        pw.print(linea);
                    } else {
                        pw.println(linea);
                    }
                }
                return true;
            } catch (IOException e) {
                System.err.println("Error al reescribir salas: " + e.getMessage());
                return false;
            }
        }
        return false;
    }
}