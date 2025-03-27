package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

public class FuenteDatosMemoria implements org.iesalandalus.programacion.tallermecanico.modelo.negocio.IFuenteDatos {

    @Override
    public Clientes crearClientes() {
        return new Clientes();
    }

    @Override
    public Vehiculos crearVehiculos() {
        return new Vehiculos();
    }

    @Override
    public Trabajos crearTrabajos() {
        return new Trabajos();
    }
}
