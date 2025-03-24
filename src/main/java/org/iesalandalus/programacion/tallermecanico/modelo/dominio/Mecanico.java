package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;
import java.util.Objects;

public class Mecanico extends Trabajo{

    private static final float FACTOR_HORA = 30;
    private static final float FACTOR_PRECIO_MATERIAL = 1.5f;

    private float precioMaterial;

    public Mecanico(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente, vehiculo, fechaInicio);
    }

    public Mecanico(Mecanico mecanico) {
        super(mecanico);
        precioMaterial = mecanico.precioMaterial;
    }

    public float getPrecioMaterial() {
        return precioMaterial;
    }

    public void anadirPrecioMaterial(float precioMaterial) {
        if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        } else if (estaCerrado()) {
            throw new TallerMecanicoExcepcion("No se puede añadir precio del material, ya que el trabajo mecánico está cerrado.");
        }
        this.precioMaterial += precioMaterial;
    }

    @Override
    public float getPrecioEspecifico() {
        if (estaCerrado()) {
            return (getHoras() * FACTOR_HORA) + (precioMaterial * FACTOR_PRECIO_MATERIAL);
        } else {
            return (0);
        }
    }

    @Override
    public String toString() {
        if (!estaCerrado()) {
            return String.format("Mecánico -> %s - %s (%s - ): %d horas, %.2f € en material",getCliente().toString(), getVehiculo().toString(), getFechaInicio().format(FORMATO_FECHA), getHoras(), precioMaterial);
        } else {
            return String.format("Mecánico -> %s - %s (%s - %s): %d horas, %.2f € en material, %.2f € total", getCliente().toString(), getVehiculo().toString(), getFechaInicio().format(FORMATO_FECHA),getFechaFin().format(FORMATO_FECHA), getHoras(), precioMaterial, getPrecio());
        }
    }
}
