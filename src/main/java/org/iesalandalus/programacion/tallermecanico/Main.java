package org.iesalandalus.programacion.tallermecanico;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.controlador.IControlador;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.vista.texto.VistaTexto;

public class Main {
    public static void main(String[] args) throws TallerMecanicoExcepcion {
        Modelo modelo = new Modelo();
        VistaTexto vistaTexto = new VistaTexto();
        IControlador IControlador = new Controlador(modelo, vistaTexto);
        IControlador.comenzar();
    }
}
