package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Propietario;
import com.EquipoB.AsadoYPileta.entidades.Servicio;
import com.EquipoB.AsadoYPileta.entidades.TipoPropiedad;
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.PropiedadRepositorio;
import com.EquipoB.AsadoYPileta.repositorios.PropietarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PropiedadServicio {

    @Autowired
    private PropiedadRepositorio propiedadRepositorio;

    @Autowired
    private PropietarioServicio propietarioServicio;

    @Autowired
    private PropietarioRepositorio propietarioRepositorio;

    @Autowired
    private ServicioServicio servicioServicio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    private ReservaServicio reservaServicio;

    @Autowired
    private UbicacionServicio ubicacionServicio;

    @Autowired
    private TipoPropiedadServicio tipoPropiedadServicio;

    @Autowired
    private FiltroDisponibilidadServicio filtroDisponibilidadServicio;

    @Transactional
    public void crearPropiedad(String titulo, String descripcion, String tipo,
            String[] serviciosInput, MultipartFile[] imagenesInput, Double valor, Usuario usuario,
            String pais, String provincia, String departamento, String localidad, String calle,
            String numeracion, String observaciones, Double latitud, Double longitud,
            String fechaInicioReserva, String fechaFinReserva, String[] mensualReserva,
            String[] diarioReserva, String[] porFechaReserva) throws MiException, Exception {

        validar(titulo, descripcion, tipo, imagenesInput, valor);

        Optional<Propietario> respuesta = propietarioRepositorio.findById(usuario.getId());

        Propietario propietario = null;

        if (respuesta.isPresent()) {
            propietario = respuesta.get();
        } else {
            propietario = propietarioServicio.crearPropietario(usuario);
        }

        List<Servicio> servicios = new ArrayList<>();
        if (serviciosInput != null) {
            servicios = servicioServicio.listarServiciosArray(serviciosInput);
        }
        List<Imagen> imagenes = new ArrayList<>();
        imagenes = imagenServicio.guardarVarias(imagenesInput);

        Propiedad propiedad = new Propiedad();

        propiedad.setTitulo(titulo);
        propiedad.setDescripcion(descripcion);
        propiedad.setUbicacion(ubicacionServicio.crearUbicacion(pais, provincia, departamento, localidad, calle, numeracion, observaciones, latitud, longitud));
        propiedad.setTipo(tipoPropiedadServicio.getOnePorTipo(tipo));
        propiedad.setEstado(true);
        propiedad.setServicios(servicios);
        propiedad.setImagenes(imagenes);
        propiedad.setValor(valor);
        int[] mesParseado = parsearArregloString(mensualReserva);
        int[] diaParseado = parsearArregloString(diarioReserva);
        int[] porFechaParsado = parsearArregloString(porFechaReserva);
        propiedad.setFiltroDisponibilidad(filtroDisponibilidadServicio.crearFiltro(fechaInicioReserva,
                fechaFinReserva, mesParseado, diaParseado, porFechaParsado));
        propiedad.setPuntuacion(0.00);
        propiedadRepositorio.save(propiedad);
        if (propietario.getPropiedades() != null) {
            propietario.getPropiedades().add(propiedad);
        } else {
            List<Propiedad> propiedades = new ArrayList<>();
            propiedades.add(propiedad);
            propietario.setPropiedades(propiedades);
        }
        propietarioRepositorio.save(propietario);
    }

    @Transactional
    public void modificarPropiedad(String id, String titulo, String descripcion, String tipo, String[] serviciosInput, MultipartFile[] imagenesInput,
            Double valor, String[] imagenesViejas, String estado, String pais, String provincia, String departamento, String localidad, String calle, String numeracion,
            String observaciones, Double latitud, Double longitud, String fechaInicioReserva, String fechaFinReserva, String[] mensualReserva,
            String[] diarioReserva, String[] porFechaReserva) throws MiException, Exception {

        validar(titulo, descripcion, tipo, imagenesInput, valor);

        Optional<Propiedad> propiedadRepo = propiedadRepositorio.findById(id);

        List<Servicio> servicios = new ArrayList<>();
        if (serviciosInput != null) {
            servicios = servicioServicio.listarServiciosArray(serviciosInput);
        }

        if (propiedadRepo.isPresent()) {

            Propiedad propiedad = propiedadRepo.get();
            List<Imagen> imagenes = propiedad.getImagenes();

            if (imagenesViejas != null) {
                if (imagenesViejas.length != 0) {
                    imagenes = imagenServicio.filtrar(imagenes,
                            imagenesViejas);
                }
            }
            if (imagenesInput != null) {
                if (imagenesInput.length != 0) {
                    imagenes.addAll(imagenServicio.guardarVarias(imagenesInput));
                }
            }

            propiedad.setTitulo(titulo);
            propiedad.setDescripcion(descripcion);

            if ("true".equals(estado)) {
                propiedad.setEstado(true);
            } else {
                boolean busqueda = reservaServicio.validarReservasPropiedad(id);
                if (busqueda == true) {
                    throw new MiException("No puede darse de baja si tiene reservas activas!");
                } else {
                    propiedad.setEstado(false);
                }

            }
            propiedad.setEstado(Boolean.valueOf(estado));
            propiedad.setUbicacion(ubicacionServicio.modificarUbicacion(propiedad.getUbicacion().getId(), pais, provincia, departamento, localidad, calle, numeracion, observaciones, latitud, longitud, estado));
            propiedad.setTipo(tipoPropiedadServicio.getOnePorTipo(tipo));
            propiedad.setServicios(servicios);
            propiedad.setImagenes(imagenes);
            propiedad.setValor(valor);
            int[] mesParseado = parsearArregloString(mensualReserva);
            int[] diaParseado = parsearArregloString(diarioReserva);
            int[] porFechaParsado = parsearArregloString(porFechaReserva);
            propiedad.setFiltroDisponibilidad(filtroDisponibilidadServicio.modificarFiltro(propiedad.getFiltroDisponibilidad().getId(),
                    fechaInicioReserva, fechaFinReserva, mesParseado, diaParseado, porFechaParsado));
            propiedadRepositorio.save(propiedad);
        }
    }

    @Transactional
    public void setPuntuacion(Double puntuacion, String id) {
        Propiedad propiedad = propiedadRepositorio.getOne(id);
        propiedad.setPuntuacion(puntuacion);
        propiedadRepositorio.save(propiedad);
    }

    public List<Propiedad> listarPropiedades() {
        List<Propiedad> propiedades = new ArrayList<>();
        propiedades = propiedadRepositorio.findAll();
        return propiedades;
    }

    public List<Propiedad> listarPropiedadesActivas() {
        List<Propiedad> propiedades = new ArrayList<>();
        propiedades = propiedadRepositorio.buscarPropiedadesActivas();
        return propiedades;
    }

    public List<Propiedad> listarPropiedadesPorTipo(String tipo) throws MiException {
        List<Propiedad> propiedades = new ArrayList<>();
        try {
            TipoPropiedad tipoPropiedad = tipoPropiedadServicio.getOnePorTipo(tipo);
            propiedades = propiedadRepositorio.buscarPorTipo(tipoPropiedad.getId());
        } catch (MiException ex) {
            throw ex;
        }
        return propiedades;
    }

    public Propiedad getOne(String id) {
        return propiedadRepositorio.getOne(id);
    }

    @Transactional
    public void darDeBaja(String id) throws MiException {
        Propiedad propiedad = propiedadRepositorio.getOne(id);
        propiedad.setEstado(false);
        ubicacionServicio.cambiarEstadoUbicacion(propiedad.getUbicacion().getId(), "false");
        propiedadRepositorio.save(propiedad);
    }

    @Transactional
    public void darDeAlta(String id) throws MiException {
        Propiedad propiedad = propiedadRepositorio.getOne(id);
        propiedad.setEstado(true);
        ubicacionServicio.cambiarEstadoUbicacion(propiedad.getUbicacion().getId(), "true");
        propiedadRepositorio.save(propiedad);
    }

    @Transactional
    public void eliminar(String id, Usuario logueado) {
        Optional<Propiedad> propiedadRepo = propiedadRepositorio.findById(id);
        Optional<Propietario> propietarioRepo = propietarioRepositorio.findById(logueado.getId());
        if (propiedadRepo.isPresent() && propietarioRepo.isPresent()) {
            Propiedad propiedad = propiedadRepo.get();
            Propietario propietario = propietarioRepo.get();
            List<Propiedad> propiedades = propietario.getPropiedades();
            if (propiedades.contains(propiedad)) {
                propiedades.remove(propiedad);
                propietario.setPropiedades(propiedades);
                propietarioRepositorio.save(propietario);
                propiedadRepositorio.delete(propiedad);
            }
        }
    }

    public int[] parsearArregloString(String[] arreglo) {
        if (arreglo != null) {
            int[] enteros = new int[arreglo.length];
            for (int i = 0; i < arreglo.length; i++) {
                enteros[i] = Integer.parseInt(arreglo[i]);
            }
            return enteros;
        }
        return null;
    }

    public void validar(String titulo, String descripcion,
            String tipo, MultipartFile[] imagenes, Double valor) throws MiException {

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new MiException("La descripcion no puede ser nula o estar vacio");
        }

        if (tipo == null || descripcion.trim().isEmpty()) {
            throw new MiException("El tipo no puede ser nulo o estar vacio");
        }

        if (imagenes.length == 0 || imagenes == null) {
            throw new MiException("Las imagenes no puede ser nulas o estar vacias");
        }

        if (valor == null) {
            throw new MiException("El valor no puede ser 0");
        }
    }

}
