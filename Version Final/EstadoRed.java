/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

/**
 * Clase encargada de representar el estado operativo de la sesión actual de SynapseLogic.
 * Permite saber si ya hay red cargada, diccionario cargado y cambios pendientes.
 * @author Daniel Vera
 */
public class EstadoRed {

    // Declaración de variables
    private boolean redCargada;
    private boolean diccionarioCargado;
    private boolean cambiosSinGuardar;

    // Constructor de la clase
    /**
     * Crea un estado inicial sin red, sin diccionario y sin cambios pendientes.
     */
    public EstadoRed() {
        this.redCargada = false;
        this.diccionarioCargado = false;
        this.cambiosSinGuardar = false;
    }

    // Getters y Setters
    
    public boolean estaRedCargada() {
        return redCargada;
    }

    public void setRedCargada(boolean redCargada) {
        this.redCargada = redCargada;
    }

    public boolean estaDiccionarioCargado() {
        return diccionarioCargado;
    }

    public void setDiccionarioCargado(boolean diccionarioCargado) {
        this.diccionarioCargado = diccionarioCargado;
    }

    public boolean CambiosSinGuardar() {
        return cambiosSinGuardar;
    }

    public void setCambiosSinGuardar(boolean cambiosSinGuardar) {
        this.cambiosSinGuardar = cambiosSinGuardar;
    }
}