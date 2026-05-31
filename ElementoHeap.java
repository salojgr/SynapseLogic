/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

/**
 *
 * @author Raiza
 * @param <T> Tipo de dato del elemento agregado.
 */
public class ElementoHeap<T> {
    private final T elemento;
    private final double prioridad;
    
   
    /**
     * 
     * @param elemento Objeto almacenado.
     * @param prioridad Valor numerico para ordenacion de la prioridad (menor valor = mayor prioridad). 
     */
    public ElementoHeap(T elemento, double prioridad){
        this.elemento = elemento;
        this.prioridad = prioridad;
    }
    
    public T getElemento(){
        return this.elemento;
    }
    
    public double getPrioridad(){
        return this.prioridad;
    }
    
    /**
     * Creacion de la funcion que compara el valor numerico "prioridad" entre objetos almacenados.
     * @param otro Se refiere al otro elemento a comparar
     * @return 
     */
    
    public int compararElementos(ElementoHeap<T> otro){
        if (this.prioridad < otro.prioridad){
            return -1;
        }else if (this.prioridad > otro.prioridad){
            return 1;
        }
        return 0;
    }
    
    @Override
    public String toString(){
        return "[" + elemento + " (W: " + prioridad + ")]";
    }
    
    
}
