package modelo.entidad;

public class Boleto {
    private String id;
    private String pelicula;
    private String funcion;
    private String sala;
    private String asientos;
    private String tipoPago;
    private double total;

    public Boleto(String id, String pelicula, String funcion, String sala, String asientos, String tipoPago, double total) {
        this.id = id;
        this.pelicula = pelicula;
        this.funcion = funcion;
        this.sala = sala;
        this.asientos = asientos;
        this.tipoPago = tipoPago;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPelicula() {
        return pelicula;
    }

    public void setPelicula(String pelicula) {
        this.pelicula = pelicula;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getAsientos() {
        return asientos;
    }

    public void setAsientos(String asientos) {
        this.asientos = asientos;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}