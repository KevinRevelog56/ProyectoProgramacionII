package modelo.dao;

import java.io.*;
import java.util.*;
import modelo.entidad.Funcion;
import modelo.entidad.Pelicula;
import modelo.entidad.Sala;

public class RepositorioFuncionesCSV implements IRepositorioFunciones {
    private File archivoCSV;
    private IRepositorioPeliculas repoPeliculas;
    private IRepositorioSalas repoSalas;

    public RepositorioFuncionesCSV() {
        File carpetaDatos = new File("Datos");
        if (!carpetaDatos.exists()) {
            carpetaDatos.mkdir();
        }
        this.archivoCSV = new File(carpetaDatos, "funciones.csv");
        this.repoPeliculas = new RepositorioPeliculasCSV();
        this.repoSalas = new RepositorioSalasCSV();
    }

    @Override
    public List<Funcion> obtenerFunciones() {
        List<Funcion> funciones = new ArrayList<>();
        if (!archivoCSV.exists()) return funciones;

        List<Pelicula> peliculas = repoPeliculas.obtenerPeliculas();
        List<Sala> salas = repoSalas.obtenerSalas();

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split(";");
                if (partes.length >= 4) {
                    int idFuncion = Integer.parseInt(partes[0].trim());
                    int idPelicula = Integer.parseInt(partes[1].trim());
                    int idSala = Integer.parseInt(partes[2].trim());
                    String horario = partes[3].trim();

                    Pelicula peliculaEncontrada = peliculas.stream()
                            .filter(p -> p.getId() == idPelicula)
                            .findFirst().orElse(null);

                    Sala salaEncontrada = salas.stream()
                            .filter(s -> s.getIdSala() == idSala)
                            .findFirst().orElse(null);

                    if (peliculaEncontrada != null && salaEncontrada != null) {
                        funciones.add(new Funcion(idFuncion, peliculaEncontrada, salaEncontrada, horario));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer funciones.csv: " + e.getMessage());
        }
        return funciones;
    }

    public boolean crear(Funcion funcion) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivoCSV, true))) {
            pw.println(funcion.getIdFuncion() + ";" + 
                       funcion.getPelicula().getId() + ";" + 
                       funcion.getSala().getIdSala() + ";" + 
                       funcion.getHorario().trim());
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar función: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idFuncion) {
        List<Funcion> lista = obtenerFunciones();
        boolean encontrada = lista.removeIf(f -> f.getIdFuncion() == idFuncion);

        if (encontrada) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(archivoCSV, false))) {
                for (int i = 0; i < lista.size(); i++) {
                    Funcion f = lista.get(i);
                    String linea = f.getIdFuncion() + ";" + f.getPelicula().getId() + ";" + f.getSala().getIdSala() + ";" + f.getHorario().trim();
                    if (i == lista.size() - 1) {
                        pw.print(linea);
                    } else {
                        pw.println(linea);
                    }
                }
                return true;
            } catch (IOException e) {
                System.err.println("Error al reescribir funciones: " + e.getMessage());
                return false;
            }
        }
        return false;
    }
}