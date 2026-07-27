package modelo.entidad;

public interface IPago {
    boolean pagar(double monto);
    String obtenerNombreMetodo();
}