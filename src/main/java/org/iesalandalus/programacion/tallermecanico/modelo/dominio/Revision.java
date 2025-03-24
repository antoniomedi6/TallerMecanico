package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;

public class Revision extends Trabajo {

    public static final int FACTOR_HORA = 35;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente, vehiculo, fechaInicio);
    }

    public Revision(Revision revision) {
        super(revision);
    }

    @Override
    public float getPrecioEspecifico() {
        if (estaCerrado()) {
            return (getHoras() * FACTOR_HORA);
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        if (!estaCerrado()) {
            return String.format("Revisión -> %s - %s (%s - ): %d horas",getCliente().toString(), getVehiculo().toString(), getFechaInicio().format(FORMATO_FECHA), getHoras());
        } else {
            return String.format("Revisión -> %s - %s (%s - %s): %d horas, %.2f € total", getCliente().toString(), getVehiculo().toString(), getFechaInicio().format(FORMATO_FECHA),getFechaFin().format(FORMATO_FECHA), getHoras(), getPrecio());
        }
    }
}