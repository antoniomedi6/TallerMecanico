package org.iesalandalus.programacion.tallermecanico.modelo.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Clientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Trabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Vehiculos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModeloCascada implements org.iesalandalus.programacion.tallermecanico.modelo.Modelo {

        private IClientes clientes;
        private IVehiculos vehiculos;
        private ITrabajos trabajos;

        public ModeloCascada(FabricaFuenteDatos fabricaFuenteDatos) {
            Objects.requireNonNull(fabricaFuenteDatos, "La fuente de datos no puede ser nula.");
            IFuenteDatos fuenteDatos = fabricaFuenteDatos.crear();
            clientes = fuenteDatos.crearClientes();
            vehiculos = fuenteDatos.crearVehiculos();
            trabajos = fuenteDatos.crearTrabajos();
            comenzar();
        }

        @Override
        public void comenzar() {
            clientes = new Clientes();
            vehiculos = new Vehiculos();
            trabajos = new Trabajos();
        }

        @Override
        public void terminar() {
            System.out.print("El programa ha terminado");
        }

        @Override
        public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
            clientes.insertar(new Cliente(cliente));
        }

        @Override
        public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
            vehiculos.insertar(vehiculo);
        }

        @Override
        public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
            Cliente cliente = clientes.buscar(trabajo.getCliente());
            Vehiculo vehiculo = vehiculos.buscar(trabajo.getVehiculo());
            if (trabajo instanceof Mecanico) {
                trabajos.insertar(new Mecanico(cliente, vehiculo, trabajo.getFechaInicio()));
            } else if (trabajo instanceof Revision) {
                trabajos.insertar(new Revision(cliente, vehiculo, trabajo.getFechaInicio()));
            }
        }

        @Override
        public Cliente buscar(Cliente cliente) {
            cliente = Objects.requireNonNull(clientes.buscar(cliente), "No existe un cliente igual.");
            return new Cliente(cliente);
        }
        @Override
        public Vehiculo buscar(Vehiculo vehiculo) {
            vehiculo = Objects.requireNonNull(vehiculos.buscar(vehiculo), "No existe un vehículo igual.");
            return vehiculo;
        }

        @Override
        public Trabajo buscar(Trabajo trabajo) {
            trabajo = Objects.requireNonNull(trabajos.buscar(trabajo), "No existe una revisión igual.");
            return Trabajo.copiar(trabajo);
        }

        @Override
        public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
            return new Cliente(clientes.modificar(cliente, nombre, telefono));
        }

        @Override
        public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
            return trabajos.anadirHoras(trabajo, horas);
        }

        @Override
        public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
            return trabajos.anadirPrecioMaterial(trabajo, precioMaterial);
        }

        @Override
        public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
            return trabajos.cerrar(trabajo, fechaFin);
        }

        @Override
        public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
            List<Trabajo> trabajosCliente = trabajos.get(cliente);
            for (Trabajo trabajo : trabajosCliente) {
                trabajos.borrar(trabajo);
            }
            clientes.borrar(cliente);
        }

        @Override
        public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
            List<Trabajo> trabajosVehiculo = trabajos.get(vehiculo);
            for (Trabajo trabajo : trabajosVehiculo) {
                trabajos.borrar(trabajo);
            }
            vehiculos.borrar(vehiculo);
        }

        @Override
        public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
            trabajos.borrar(trabajo);
        }

        @Override
        public List<Cliente> getClientes() {
            List<Cliente> copiaClientes = new ArrayList<>();
            for (Cliente cliente : clientes.get()) {
                copiaClientes.add(new Cliente(cliente));
            }
            return copiaClientes;
        }

        @Override
        public List<Vehiculo> getVehiculos() {
            return vehiculos.get();
        }

        @Override
        public List<Trabajo> getTrabajos() {
            List<Trabajo> copiaTrabajos = new ArrayList<>();
            for (Trabajo trabajo : trabajos.get()) {
                copiaTrabajos.add(Trabajo.copiar(trabajo));
            }
            return copiaTrabajos;
        }

        @Override
        public List<Trabajo> getTrabajos(Cliente cliente) {
            List<Trabajo> trabajosCliente = new ArrayList<>();
            for (Trabajo trabajo : trabajos.get(cliente)) {
                trabajosCliente.add(Trabajo.copiar(trabajo));
            }
            return trabajosCliente;
        }

        @Override
        public List<Trabajo> getTrabajos(Vehiculo vehiculo) {
            List<Trabajo> trabajosVehiculo = new ArrayList<>();
            for (Trabajo trabajo : trabajos.get(vehiculo)) {
                trabajosVehiculo.add(Trabajo.copiar(trabajo));
            }
            return trabajosVehiculo;
        }
    }

