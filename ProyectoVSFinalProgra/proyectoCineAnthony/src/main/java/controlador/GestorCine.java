package controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import modelo.dao.IRepositorioPeliculas;
import modelo.dao.IRepositorioSalas;
import modelo.dao.IRepositorioFunciones;
import modelo.dao.RepositorioPeliculasCSV;
import modelo.dao.RepositorioSalasCSV;
import modelo.dao.RepositorioFuncionesCSV;
import modelo.entidad.Pelicula;
import modelo.entidad.Sala;
import modelo.entidad.Funcion;

public class GestorCine {
    private IRepositorioPeliculas repoPeliculas;
    private IRepositorioSalas repoSalas;
    private IRepositorioFunciones repoFunciones;
    
    private File archivoSalas;
    private File archivoFunciones;

    public GestorCine() {
        File carpetaDatos = new File("Datos");
        if (!carpetaDatos.exists()) {
            carpetaDatos.mkdir();
        }
        
        this.archivoSalas = new File(carpetaDatos, "salas.csv");
        this.archivoFunciones = new File(carpetaDatos, "funciones.csv");

        this.repoPeliculas = new RepositorioPeliculasCSV();
        this.repoSalas = new RepositorioSalasCSV();
        this.repoFunciones = new RepositorioFuncionesCSV();
    }

    public List<Pelicula> obtenerCartelera() {
        return repoPeliculas.obtenerPeliculas();
    }

    public List<Sala> obtenerSalas() {
        return repoSalas.obtenerSalas();
    }

    public List<Funcion> obtenerFunciones() {
        return repoFunciones.obtenerFunciones();
    }

    public List<Funcion> obtenerFuncionesPorPelicula(int idPelicula) {
        List<Funcion> todas = repoFunciones.obtenerFunciones();
        List<Funcion> filtradas = new ArrayList<>();
        for (Funcion f : todas) {
            if (f.getPelicula() != null && f.getPelicula().getId() == idPelicula) {
                filtradas.add(f);
            }
        }
        return filtradas;
    }

    public void actualizarSala(int idSalaBuscado, String nuevoNombre, int nuevaCapacidad) {
        List<String> lineasActualizadas = new ArrayList<>();

        if (archivoSalas.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivoSalas))) {
                String linea;
                boolean encabezado = true;

                while ((linea = br.readLine()) != null) {
                    if (encabezado) {
                        lineasActualizadas.add(linea); 
                        encabezado = false;
                        continue;
                    }

                    if (linea.trim().isEmpty()) continue;
                    String[] datos = linea.split(";");
                    int idActual = Integer.parseInt(datos[0].trim());

                    if (idActual == idSalaBuscado) {
                        String lineaModificada = idSalaBuscado + ";" + nuevoNombre.trim() + ";" + nuevaCapacidad;
                        lineasActualizadas.add(lineaModificada);
                    } else {
                        lineasActualizadas.add(linea);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(archivoSalas, false))) {
            for (String l : lineasActualizadas) {
                pw.println(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarFuncion(int idFuncionBuscado, int nuevoIdPelicula, int nuevoIdSala, String nuevoHorario) {
        List<String> lineasActualizadas = new ArrayList<>();
        
        if (archivoFunciones.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivoFunciones))) {
                String linea;
                boolean encabezado = true;
                
                while ((linea = br.readLine()) != null) {
                    if (encabezado) {
                        lineasActualizadas.add(linea);
                        encabezado = false;
                        continue;
                    }
                    
                    if (linea.trim().isEmpty()) continue;
                    String[] datos = linea.split(";");
                    int idActual = Integer.parseInt(datos[0].trim());
                    
                    if (idActual == idFuncionBuscado) {
                        String lineaModificada = idFuncionBuscado + ";" + nuevoIdPelicula + ";" + nuevoIdSala + ";" + nuevoHorario.trim();
                        lineasActualizadas.add(lineaModificada);
                    } else {
                        lineasActualizadas.add(linea);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivoFunciones, false))) {
            for (String l : lineasActualizadas) {
                pw.println(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean agregarSala(int idSala, String nombre, int capacidad) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivoSalas, true))) {
            pw.println(idSala + ";" + nombre.trim() + ";" + capacidad);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean agregarFuncion(int idFuncion, int idPelicula, int idSala, String horario) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivoFunciones, true))) {
            pw.println(idFuncion + ";" + idPelicula + ";" + idSala + ";" + horario.trim());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}