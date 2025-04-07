package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import java.util.Objects;

public class Controlador implements IControlador {

    private final Modelo modelo;
    private final Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        Objects.requireNonNull(modelo,"El modelo no puede ser nulo.");
        Objects.requireNonNull(vista, "La vista no puede ser nula.");
        this.modelo = modelo;
        this.vista = vista;
        this.vista.getGestorEventos().suscribir(this, Evento.values());
    }

    @Override
    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    @Override
    public void terminar() {
        vista.terminar();
        modelo.terminar();
    }

    @Override
    public void actualizar(Evento evento) {
        try {
            String resultado = "";
            switch (evento) {
                case INSERTAR_CLIENTE -> { modelo.insertar(vista.leerCliente()); resultado = "Cliente insertado correctamente."; }
                case INSERTAR_VEHICULO -> { modelo.insertar(vista.leerNuevoVehiculo()); resultado = "Vehículo insertado correctamente."; }
                case INSERTAR_TRABAJO -> { modelo.insertar(vista.leerTrabajoVehiculo()); resultado = "Trabajo insertado correctamente."; }
                case BUSCAR_CLIENTE -> { modelo.buscar(vista.leerClienteDni()); resultado = "Cliente encontrado."; }
                case BUSCAR_VEHICULO -> { modelo.buscar(vista.leerVehiculoMatricula()); resultado = "Vehículo encontrado."; }
                case BUSCAR_TRABAJO -> {modelo.buscar(vista.leerTrabajoVehiculo()); resultado = "Trabajo encontrado."; }
                case BORRAR_CLIENTE -> {modelo.borrar(vista.leerClienteDni()); resultado = "Cliente borrado.";}
                case BORRAR_VEHICULO -> {modelo.borrar(vista.leerVehiculoMatricula()); resultado = "Vehículo borrado.";}
                case BORRAR_TRABAJO -> {modelo.borrar(vista.leerTrabajoVehiculo()); resultado = "Trabajo borrado.";}
                case LISTAR_CLIENTES -> modelo.getClientes();
                case LISTAR_VEHICULOS -> modelo.getVehiculos();
                case LISTAR_TRABAJOS -> modelo.getTrabajos();
                case LISTAR_TRABAJOS_CLIENTE -> modelo.getTrabajos(vista.leerClienteDni());
                case LISTAR_TRABAJOS_VEHICULO -> modelo.getTrabajos(vista.leerVehiculoMatricula());
                case MODIFICAR_CLIENTE -> {modelo.modificar(vista.leerCliente(), vista.leerNuevoNombre(), vista.leerNuevoTelefono()); resultado = "Cliente modificado correctamente.";}
                case ANADIR_HORAS_TRABAJO -> {modelo.anadirHoras(vista.leerTrabajoVehiculo(), vista.leerHoras()); resultado = "Horas añadidas correctamente.";}
                case ANADIR_PRECIO_MATERIAL_TRABAJO -> {modelo.anadirPrecioMaterial(vista.leerTrabajoVehiculo(), vista.leerPrecioMaterial()); resultado = "Precio material añadido correctamente.";}
                case CERRAR_TRABAJO -> {modelo.cerrar(vista.leerTrabajoVehiculo(), vista.leerFechaCierre()); resultado = "Trabajo cerrado correctamente.";}
                case SALIR -> modelo.terminar();
            }
            if (resultado.isBlank()) {
                vista.notificarResultado(evento, resultado, true);
            }
        } catch (Exception e) {
            vista.notificarResultado(evento, e.getMessage(), false);
        }
    }
}
