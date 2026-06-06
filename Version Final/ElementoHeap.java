/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

/**
 * Representa un elemento almacenado dentro de la cola de prioridad.
 * @author Salomón González
 * @param <T> Tipo de dato del elemento agregado.
 */
public class ElementoHeap<T> {
    private final T elemento;
    private final double prioridad;
    
   
    /**
     * 
     * @param elemento Objeto almacenado.
     * @param prioridad Valor para ordenar: a menor valor mayor prioridad. 
     */
    public ElementoHeap(T elemento, double prioridad){
        this.elemento = elemento;
        this.prioridad = prioridad;
    }
    /**
     * Obtiene el dato almacenado.
     * @return  dato almacenado
     */
    public T getElemento(){
        return this.elemento;
    }
    /**
     * Obtiene la prioridad del elemento.
     * 
     * @return prioridad del elemento
     */
    public double getPrioridad(){
        return this.prioridad;
    }
    
    /**
     * Compara la prioridad entre objetos.
     * @param otro Se refiere al otro elemento a comparar
     * @return -1 si este elemento tiene menor prioridad, 1 si tiene mayor y 0 si son iguales.
     */
    
    public int compararElementos(ElementoHeap<T> otro){
        if (this.prioridad < otro.prioridad){
            return -1;
        }else if (this.prioridad > otro.prioridad){
            return 1;
        }
        return 0;
    }
    /**
     * Representacion en texto del elemento y su prioridad
     * @return string con el elemento y su prioridad asociada
     */
    @Override
    public String toString(){
        return "[" + elemento + " (W: " + prioridad + ")]";
    }
    
    
}

