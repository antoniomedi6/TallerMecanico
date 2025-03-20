package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Revision {
    private static final float PRECIO_HORA = 30;
    private static final float PRECIO_DIA = 10;
    private static final float PRECIO_MATERIAL = 1.50f;
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int horas;
    private float precioMaterial;

    private Vehiculo vehiculo;
    private Cliente cliente;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
    }

    public Revision(Revision revision) {
        Objects.requireNonNull(revision, "La revisión no puede ser nula.");
        this.cliente = revision.cliente;
        this.vehiculo = revision.vehiculo;
        this.fechaInicio = revision.fechaInicio;
        this.fechaFin = revision.fechaFin;
        this.precioMaterial = revision.precioMaterial;
        this.horas = revision.horas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        this.vehiculo = vehiculo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        Objects.requireNonNull(fechaInicio, "La fecha de inicio no puede ser nula.");
        if (fechaInicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser futura.");
        }
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        } else if (fechaFin.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser futura.");
        }
        this.fechaFin = fechaFin;
    }

    public int getHoras() {
        return horas;
    }

    public void anadirHoras(int horas) {
        if (horas <= 0) {
            throw new IllegalArgumentException("Las horas a añadir deben ser mayores que cero.");
        } else if (estaCerrada()) {
            throw new TallerMecanicoExcepcion("No se puede añadir horas, ya que la revisión está cerrada.");
        }
        this.horas += horas;
    }

    public float getPrecioMaterial() {
        return precioMaterial;
    }

    public void anadirPrecioMaterial(float precioMaterial) {
        if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        } else if (estaCerrada()) {
            throw new TallerMecanicoExcepcion("No se puede añadir precio del material, ya que la revisión está cerrada.");
        }
        this.precioMaterial += precioMaterial;
    }

    public boolean estaCerrada() {
        return fechaFin != null;
    }

    public void cerrar(LocalDate fechaFin) {
        Objects.requireNonNull(fechaFin, "La fecha de fin no puede ser nula.");
        if (estaCerrada()) {
            throw new TallerMecanicoExcepcion("La revisión ya está cerrada.");
        }
        setFechaFin(fechaFin);
    }

    public float getPrecio() {
        return (horas * PRECIO_HORA) + (getDias() * PRECIO_DIA) + (precioMaterial * PRECIO_MATERIAL);
    }

    private float getDias() {
        if (!estaCerrada()) {
            return (0);
        }
        return (ChronoUnit.DAYS.between(fechaInicio, fechaFin));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Revision revision = (Revision) o;
        return Objects.equals(fechaInicio, revision.fechaInicio) && Objects.equals(vehiculo, revision.vehiculo) && Objects.equals(cliente, revision.cliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaInicio, vehiculo, cliente);
    }

    @Override
    public String toString() {
        String precioMaterialFormateado = String.format("%.2f", precioMaterial);
        String precioTotalFormateado = String.format("%.2f", getPrecio());
        if (fechaFin == null) {
            return String.format("%s - %s (%s) - %s: (%s - ), %d horas, %s € en material", cliente.getNombre(), cliente.getDni(), cliente.getTelefono(), vehiculo.toString(), fechaInicio.format(FORMATO_FECHA), horas, precioMaterialFormateado);
        } else {
            return String.format("%s - %s (%s) - %s: (%s - %s), %d horas, %s € en material, %s € total", cliente.getNombre(), cliente.getDni(), cliente.getTelefono(), vehiculo.toString(), fechaInicio.format(FORMATO_FECHA), fechaFin.format(FORMATO_FECHA), horas, precioMaterialFormateado, precioTotalFormateado);
        }
    }
}