/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

/**
 *
 * Pila implementada mediante una lista enlazada.
 * 
 * @param <T> tipo de dato almacenado en la pila.
 * @author Salomon Gonzalez
 */
public class Pila<T>{
    ListaEnlazada<T> elementos;
    /**
     * Crea una pila vacia.
     */
    public Pila(){
        this.elementos = new ListaEnlazada<>();
        
    }
    /**
     * Verifica si la pila esta vacia.
     * 
     * @return true si no contiene elementos.
     */
    
    public boolean estaVacia(){
        return this.elementos.estaVacia();
    }
    /**
     * Contiene la cantidad de elemenetos de la pila.
     * @return  tamano de a pila
     */
    public int tamano(){
        return elementos.tamano();
    }
    /**
     * Inserta un dato en la cima de la pila.
     * 
     * @param dato a apilar
     * @return true si se insertó correctamente
     */
    
    public boolean apilar(T dato){
        return elementos.insertarInicio(dato);
    }
    /**
     * Elimina y retorna el dato ubicado en la cima.
     * @return dato eliminado, o null si la pila esta vacia.
     */
    public T desapilar(){
        return elementos.eliminarInicio();
    }
    
    /**
     * Obtiene el dato de la cima sin eliminarlo.
     * 
     * @return dato de la cima, o null si la pila esta vacia. 
     */
    public T cima(){
        return elementos.primero().getDato();
    }
    /**
     * Elimina todos los elementos de la pila.
     */
    public void limpiar(){
        elementos.limpiar();
    }
    
    
}
