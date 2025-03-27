package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Clientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Trabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Vehiculos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Modelo {

    private Clientes clientes;
    private Vehiculos vehiculos;
    private Trabajos revisiones;

    public Modelo() {
        comenzar();
    }

    public void comenzar() {
        clientes = new Clientes();
        vehiculos = new Vehiculos();
        revisiones = new Trabajos();
    }

    public void terminar() {
        System.out.print("El programa ha terminado");
    }

    public void insertar(Cliente cliente) {
        clientes.insertar(new Cliente(cliente));
    }

    public void insertar(Vehiculo vehiculo) {
        vehiculos.insertar(vehiculo);
    }

    public void insertar(Revision revision) {
        Cliente cliente = clientes.buscar(revision.getCliente());
        Vehiculo vehiculo = vehiculos.buscar(revision.getVehiculo());
        revisiones.insertar(new Revision(cliente, vehiculo, revision.getFechaInicio()));
    }

    public Cliente buscar(Cliente cliente) {
        cliente = Objects.requireNonNull(clientes.buscar(cliente), "No existe un cliente igual.");
        return new Cliente(cliente);
    }
    public Vehiculo buscar(Vehiculo vehiculo) {
        vehiculo = Objects.requireNonNull(vehiculos.buscar(vehiculo), "No existe un vehículo igual.");
        return vehiculo;
    }

    public Revision buscar(Revision revision) {
        revision = Objects.requireNonNull(revisiones.buscar(revision), "No existe una revisión igual.");
        return new Revision(revision);
    }

    public Cliente modificar(Cliente cliente, String nombre, String telefono) {
        return new Cliente(clientes.modificar(cliente, nombre, telefono));
    }

    public Revision anadirHoras(Revision revision, int horas) throws TallerMecanicoExcepcion {
        return new Revision(revisiones.anadirHoras(revision, horas));
    }

    public Revision anadirPrecioMaterial(Revision revision, float precioMaterial) throws TallerMecanicoExcepcion {
        return new Revision(revisiones.anadirPrecioMaterial(revision, precioMaterial));
    }

    public Revision cerrar(Revision revision, LocalDate fechaFin) {
        return new Revision(revisiones.cerrar(revision, fechaFin));
    }

    public void borrar(Cliente cliente) {
        List<Revision> revisionesCliente = revisiones.get(cliente);
        for (Revision revision : revisionesCliente) {
            revisiones.borrar(revision);
        }
        clientes.borrar(cliente);
    }

    public void borrar(Vehiculo vehiculo) {
        List<Revision> revisionesVehiculo = revisiones.get(vehiculo);
        for (Revision revision : revisionesVehiculo) {
            revisiones.borrar(revision);
        }
        vehiculos.borrar(vehiculo);
    }

    public void borrar(Revision revision) {
        revisiones.borrar(revision);
    }

    public List<Cliente> getClientes() {
        List<Cliente> copiaClientes = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            copiaClientes.add(new Cliente(cliente));
        }
        return copiaClientes;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos.get();
    }

    public List<Revision> getRevisiones() {
        List<Revision> copiaRevisiones = new ArrayList<>();
        for (Revision revision : revisiones.get()) {
            copiaRevisiones.add(new Revision(revision));
        }
        return copiaRevisiones;
    }

    public List<Revision> getRevisiones(Cliente cliente) {
        List<Revision> revisionesCliente = new ArrayList<>();
        for (Revision revision : revisiones.get(cliente)) {
            revisionesCliente.add(new Revision(revision));
        }
        return revisionesCliente;
    }

    public List<Revision> getRevisiones(Vehiculo vehiculo) {
        List<Revision> revisionesVehiculo = new ArrayList<>();
        for (Revision revision : revisiones.get(vehiculo)) {
            revisionesVehiculo.add(new Revision(revision));
        }
        return revisionesVehiculo;
    }


}
