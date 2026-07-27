package modelo.dao;
import java.util.List;
import modelo.entidad.Sala;

public interface IRepositorioSalas {
    List<Sala> obtenerSalas();
}