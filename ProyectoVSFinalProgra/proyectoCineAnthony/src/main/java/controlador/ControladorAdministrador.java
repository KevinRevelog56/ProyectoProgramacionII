package controlador;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.dao.RepositorioPeliculasCSV;
import modelo.entidad.Pelicula;
import modelo.entidad.Sala;
import modelo.entidad.Funcion;

public class ControladorAdministrador {
    
    private RepositorioPeliculasCSV repositorio;
    private GestorCine gestor;

    public ControladorAdministrador() {
        this.repositorio = new RepositorioPeliculasCSV();
        this.gestor = new GestorCine();
    }

    public List<Pelicula> obtenerListaPeliculas() {
        return repositorio.obtenerPeliculas();
    }

    public boolean eliminarPelicula(int id) {
        return repositorio.eliminar(id);
    }

    public boolean crearPelicula(int id, String titulo, String sinopsis, String genero, int duracion, String clasificacion, String entradaImagen, double precio) {
        String rutaImagenFinal = procesarRutaImagen(entradaImagen);
        Pelicula nueva = new Pelicula(id, titulo, sinopsis, genero, duracion, clasificacion, rutaImagenFinal, precio);
        return repositorio.crear(nueva);
    }

    public boolean actualizarPelicula(int id, String titulo, String sinopsis, String genero, int duracion, String clasificacion, String entradaImagen, double precio) {
        String rutaImagenFinal = procesarRutaImagen(entradaImagen);
        Pelicula modificada = new Pelicula(id, titulo, sinopsis, genero, duracion, clasificacion, rutaImagenFinal, precio);
        return repositorio.actualizar(modificada);
    }

    private String procesarRutaImagen(String entradaTexto) {
        if (entradaTexto == null || entradaTexto.trim().isEmpty()) {
            return "";
        }
        entradaTexto = entradaTexto.trim();
        if (entradaTexto.toLowerCase().endsWith(".jpeg") || entradaTexto.toLowerCase().endsWith(".jpg")) {
            return entradaTexto;
        }
        return entradaTexto + ".jpeg";
    }

    public void editarSala(int idSala, String nuevoNombre, int nuevaCapacidad) {
        gestor.actualizarSala(idSala, nuevoNombre, nuevaCapacidad);
    }

    public void editarFuncion(int idFuncion, int nuevoIdPelicula, int nuevoIdSala, String nuevoHorario) {
        gestor.actualizarFuncion(idFuncion, nuevoIdPelicula, nuevoIdSala, nuevoHorario);
    }

        public void cambiarVistaTabla(String seleccion, JTable tabla) {
            DefaultTableModel modeloTabla;

            switch (seleccion) {
                case "Peliculas":
                    modeloTabla = new DefaultTableModel(new Object[]{"ID", "Título", "Género", "Duración", "Precio", "Clasificación"}, 0);
                    List<Pelicula> peliculas = obtenerListaPeliculas();
                    for (Pelicula p : peliculas) {
                        modeloTabla.addRow(new Object[]{
                            p.getId(), p.getTitulo(), p.getGenero(), p.getDuracion(), p.getPrecio(), p.getClasificacion()
                        });
                    }
                    break;

                case "Salas":
                    modeloTabla = new DefaultTableModel(new Object[]{"ID Sala", "Nombre Sala", "Capacidad"}, 0);
                    List<Sala> salas = gestor.obtenerSalas(); // Asegúrate de tener este método en GestorCine
                    for (Sala s : salas) {
                        modeloTabla.addRow(new Object[]{
                            s.getIdSala(), s.getNombre(), s.getCapacidad()
                        });
                    }
                    break;

                case "Funciones":
                    modeloTabla = new DefaultTableModel(new Object[]{"ID Función", "ID Película", "ID Sala", "Horario"}, 0);
                    List<Funcion> funciones = gestor.obtenerFunciones();
                    for (Funcion f : funciones) {
                        modeloTabla.addRow(new Object[]{
                            f.getIdFuncion(), 
                            f.getPelicula() != null ? f.getPelicula().getId() : "N/A", // <-- Aquí está el cambio correcto
                            f.getSala() != null ? f.getSala().getIdSala() : "N/A", 
                            f.getHorario()
                        });
                    }
                    break;

                default:
                    modeloTabla = new DefaultTableModel();
                    break;
            }

            tabla.setModel(modeloTabla);
        }
        public Pelicula buscarPeliculaPorId(int id) {
            for (Pelicula p : obtenerListaPeliculas()) {
                if (p.getId() == id) {
                    return p;
                }
            }
            return null;
        }
        public String limpiarRutaImagen(String rutaActual) {
            if (rutaActual == null) return "";
            if (rutaActual.toLowerCase().endsWith(".jpeg")) {
                return rutaActual.substring(0, rutaActual.length() - 5);
            } else if (rutaActual.toLowerCase().endsWith(".jpg")) {
                return rutaActual.substring(0, rutaActual.length() - 4);
            }
            return rutaActual;
        }
        public boolean eliminarElementoPorSeccion(String seccion, int id) {
            switch (seccion) {
                case "Peliculas":
                    return repositorio.eliminar(id);
                case "Salas": {
                    modelo.dao.RepositorioSalasCSV repoSalas = new modelo.dao.RepositorioSalasCSV();
                    return repoSalas.eliminar(id);
                }
                case "Funciones": {
                    modelo.dao.RepositorioFuncionesCSV repoFunciones = new modelo.dao.RepositorioFuncionesCSV();
                    return repoFunciones.eliminar(id);
                    }
                    default:
                    return false;
                }
            }
            public boolean crearSala(int idSala, String nombre, int capacidad) {
            GestorCine gestor = new GestorCine();
            return gestor.agregarSala(idSala, nombre, capacidad); // Asegúrate de tener este método en GestorCine
        }

        public boolean crearFuncion(int idFuncion, int idPelicula, int idSala, String horario) {
            GestorCine gestor = new GestorCine();
            return gestor.agregarFuncion(idFuncion, idPelicula, idSala, horario); // Asegúrate de tener este método en GestorCine
        }

        public List<Funcion> obtenerListaFunciones() {
                return gestor.obtenerFunciones();
            }
        }