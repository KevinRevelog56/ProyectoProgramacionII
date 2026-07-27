package modelo.dao;
import java.util.List;
import modelo.entidad.Pelicula;

public interface IRepositorioPeliculas {
    List<Pelicula> obtenerPeliculas();
    boolean eliminar(int id);
    boolean crear(Pelicula p);
    boolean actualizar(Pelicula pelicula);
}