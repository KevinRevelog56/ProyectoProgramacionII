package modelo.dao;

import java.util.*;
import java.io.*;
import modelo.entidad.Pelicula;

public class RepositorioPeliculasCSV implements IRepositorioPeliculas {
    private File archivoCSV;

    public RepositorioPeliculasCSV() {
        File carpetaDatos = new File("Datos");
        if (!carpetaDatos.exists()) {
            carpetaDatos.mkdir();
        }
        this.archivoCSV = new File(carpetaDatos, "peliculas.csv");
    }

    @Override
    public List<Pelicula> obtenerPeliculas() {
        List<Pelicula> lista = new ArrayList<>();
        if (!archivoCSV.exists()) {
            return lista;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                
                String[] d = linea.split(";"); 
                lista.add(new Pelicula(
                    Integer.parseInt(d[0].trim()),
                    d[1].trim(),                    
                    d[2].trim(),
                    d[3].trim(),                    
                    Integer.parseInt(d[4].trim()), 
                    d[5].trim(),                    
                    d[6].trim(),                    
                    Double.parseDouble(d[7].trim())
                ));
            }
        } catch (Exception e) {
            System.err.println("Error al leer el CSV: " + e.getMessage());
            e.printStackTrace(); 
        }
        return lista;
    }

    @Override
    public boolean eliminar(int id) {
        List<Pelicula> lista = obtenerPeliculas();
        boolean encontrada = false;

        Iterator<Pelicula> iterator = lista.iterator();
        while (iterator.hasNext()) {
            Pelicula p = iterator.next();
            if (p.getId() == id) {
                iterator.remove();
                encontrada = true;
                break;
            }
        }

        if (encontrada) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(archivoCSV, false))) {
                for (int i = 0; i < lista.size(); i++) {
                    Pelicula p = lista.get(i);
                    String linea = p.getId() + ";" + 
                                   p.getTitulo().trim() + ";" + 
                                   p.getSipnopsis().trim() + ";" + 
                                   p.getGenero().trim() + ";" + 
                                   p.getDuracion() + ";" + 
                                   p.getClasificacion().trim() + ";" + 
                                   p.getRutaImagen().trim() + ";" + 
                                   p.getPrecio();
                                   
                    if (i == lista.size() - 1) {
                        pw.print(linea);
                    } else {
                        pw.println(linea);
                    }
                }
                return true;
            } catch (IOException e) {
                System.err.println("Error al actualizar el archivo CSV tras eliminar: " + e.getMessage());
                return false;
            }
        }
        return false;
    }
        
    @Override
    public boolean crear(Pelicula p) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivoCSV, true))) {
            pw.println(p.getId() + ";" + 
                       p.getTitulo().trim() + ";" + 
                       p.getSipnopsis().trim() + ";" + 
                       p.getGenero().trim() + ";" + 
                       p.getDuracion() + ";" + 
                       p.getClasificacion().trim() + ";" + 
                       p.getRutaImagen().trim() + ";" + 
                       p.getPrecio());
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar la nueva película en el CSV: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Pelicula peliculaActualizada) {
        List<Pelicula> lista = obtenerPeliculas();
        boolean encontrada = false;
        
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == peliculaActualizada.getId()) {
                lista.set(i, peliculaActualizada);
                encontrada = true;
                break;
            }
        }
        
        if (encontrada) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(archivoCSV, false))) {
                for (Pelicula p : lista) {
                    pw.println(p.getId() + ";" + 
                               p.getTitulo().trim() + ";" + 
                               p.getSipnopsis().trim() + ";" + 
                               p.getGenero().trim() + ";" + 
                               p.getDuracion() + ";" + 
                               p.getClasificacion().trim() + ";" + 
                               p.getRutaImagen().trim() + ";" + 
                               p.getPrecio());
                }
                return true;
            } catch (IOException e) {
                System.err.println("Error al actualizar el archivo CSV: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}