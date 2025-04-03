package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.HashMap;
import java.util.Map;

public enum Evento {
    INSERTAR_CLIENTE(11, "Insertar cliente."),
    BUSCAR_CLIENTE(12, "Buscar cliente."),
    BORRAR_CLIENTE(13, "Borrar cliente."),
    LISTAR_CLIENTES(14, "Listar clientes."),
    MODIFICAR_CLIENTE(15,"Modificar cliente."),
    INSERTAR_VEHICULO(21, "Insertar vehículo."),
    BUSCAR_VEHICULO(22, "Buscar vehículo."),
    BORRAR_VEHICULO(23, "Borrar vehículo."),
    LISTAR_VEHICULOS(24, "Listar vehículos."),
    INSERTAR_TRABAJO(31, "Insertar trabajo."),
    BUSCAR_TRABAJO(32, "Buscar trabajos."),
    BORRAR_TRABAJO(33, "Borrar trabajos."),
    LISTAR_TRABAJOS(34, "Listar trabajos."),
    LISTAR_TRABAJOS_CLIENTE(35, "Listar trabajos de un cliente."),
    LISTAR_TRABAJOS_VEHICULO(36, "Listar trabajos de un vehículo."),
    ANADIR_HORAS_TRABAJO(37, "Añadir horas a un trabajo."),
    ANADIR_PRECIO_MATERIAL_TRABAJO(38, "Añadir precio del material a un trabajo."),
    CERRAR_TRABAJO(39, "Cerrar trabajo."),
    SALIR(0, "Salir.");


    private final int numeroOpcion;
    private final String texto;
    private static final Map<Integer, Evento> opciones = new HashMap<>();

    static {
        for (Evento evento : values()) {
            opciones.put(evento.numeroOpcion, evento);
        }
    }

    private Evento(int numeroOpcion, String texto) {
        this.numeroOpcion = numeroOpcion;
        this.texto = texto;
    }

    public static boolean esValida(int numeroOpcion) {
        return opciones.containsKey(numeroOpcion);
    }

    public static Evento get(int numeroOpcion) {
        if (!esValida(numeroOpcion)) {
            throw new IllegalArgumentException("El número de la opción no es correcto.");
        }
        return opciones.get(numeroOpcion);
    }

    @Override
    public String toString() {
        return String.format("%d. - %s%n", numeroOpcion, texto);
    }
}

