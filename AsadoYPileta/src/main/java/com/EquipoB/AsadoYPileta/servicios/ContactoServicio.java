package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Contacto;
import com.EquipoB.AsadoYPileta.entidades.TipoContacto;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.ContactoRepositorio;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactoServicio {

    @Autowired
    private TipoContactoServicio tipoContactoServicio;
    @Autowired
    private ContactoRepositorio contactoRepositorio;

    @Transactional
    public void crearContacto(String valor, TipoContacto tipoContacto) throws MiException, Exception {

        validar(tipoContacto, valor);

        Contacto contacto = new Contacto();
        contacto.setContacto(valor);
        contacto.setTipo(tipoContacto);
        contactoRepositorio.save(contacto);
    }

    @Transactional
    public void modificar(String id, String valor, TipoContacto tipoContacto) throws MiException, Exception {

        validar(tipoContacto, valor);

        Optional<Contacto> respuesta = contactoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Contacto contacto = respuesta.get();
            contacto.setContacto(valor);
            contacto.setTipo(tipoContacto);
            contactoRepositorio.save(contacto);
        }
    }

    @Transactional
    public List<Contacto> guardarVarios(String[] tipoContactoInput, String[] contactosInput) throws MiException, Exception {
        if (contactosInput != null) {
            List<Contacto> contactos = new ArrayList<>();
            for (int i = 0; i < tipoContactoInput.length; i++) {
                TipoContacto tipo = tipoContactoServicio.getOnePorTipo(tipoContactoInput[i]);
                Contacto contacto = new Contacto();
                contacto.setTipo(tipo);
                contacto.setContacto(contactosInput[i]);
                contactoRepositorio.save(contacto);
                contactos.add(contacto);
            }
            return contactos;
        }
        return null;
    }

    private Contacto actualizar(Contacto contacto, String valor) throws MiException, Exception {
        contacto.setContacto(valor);
        return contacto;
    }
   /*
    este método tiene la responsabilidad de filtrar y actualizar una lista de contactos en base a nuevos datos proporcionados,
    manejando la creación de nuevos contactos y la eliminación de contactos que no tienen un tipo correspondiente en la nueva información
    */
    @Transactional
    public List<Contacto> filtrar(List<Contacto> contactoActuales, String[] contactosInputNuevo, String[] tipoContactoInput) throws MiException, Exception {
        List<Contacto> nuevos = new ArrayList<>();
        Iterator<Contacto> iterator = contactoActuales.iterator();
        while (iterator.hasNext()) {
            Contacto contacto = iterator.next();
            boolean tipoEncontrado = false;

            for (int i = 0; i < tipoContactoInput.length; i++) {
                TipoContacto tipoNuevo = tipoContactoServicio.getOnePorTipo(tipoContactoInput[i]);

                if (contacto.getTipo().getTipo().equals(tipoNuevo.getTipo())) {
                    contacto = actualizar(contacto, contactosInputNuevo[i]);
                    nuevos.add(contacto);
                    tipoEncontrado = true;
                    break;
                }
            }

            if (!tipoEncontrado) {
                iterator.remove();
                contactoRepositorio.deleteById(contacto.getId());
            }
        }
        for (int i = 0; i < tipoContactoInput.length; i++) {
            boolean tipoExistente = false;
            for (Contacto contacto : nuevos) {
                if (contacto.getTipo().getTipo().equals(tipoContactoInput[i])) {
                    tipoExistente = true;
                    break;
                }
            }
            if (!tipoExistente) {
                TipoContacto tipoNuevo = tipoContactoServicio.getOnePorTipo(tipoContactoInput[i]);
                Contacto nuevo = new Contacto();
                nuevo.setContacto(contactosInputNuevo[i]);
                nuevo.setTipo(tipoNuevo);
                nuevos.add(nuevo);
            }
        }
        return nuevos;
    }

    public void validar(TipoContacto tipo, String contacto) throws MiException {

        if (tipo == null) {
            throw new MiException("El tipo de contacto no puede ser nulo o estar vacio");
        }

        if (contacto == null || contacto.trim().isEmpty()) {
            throw new MiException("El contacto no puede ser nulo o estar vacio");
        }

    }

}
