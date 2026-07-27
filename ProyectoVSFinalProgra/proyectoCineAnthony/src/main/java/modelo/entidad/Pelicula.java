package modelo.entidad;

public class Pelicula {
    private int id;
    private String titulo;
    private String sipnopsis;
    private String genero;
    private int duracion;
    private String clasificacion;
    private String rutaImagen;
    private double precio;
    
    public Pelicula(int id, String titulo, String sipnopsis, String genero, int duracion, String clasificacion, String rutaImagen, double precio) {
        this.id = id;
        this.titulo = titulo;
        this.sipnopsis = sipnopsis;
        this.genero = genero;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.rutaImagen = rutaImagen;
        this.precio = precio;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSipnopsis() {
        return sipnopsis;
    }

    public void setSipnopsis(String sinopsis) {
        this.sipnopsis = sinopsis;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
    
    public void setPrecio(double precio) {
    this.precio = precio;
    }

    public double getPrecio() {
        return this.precio;
    }
    
    
}