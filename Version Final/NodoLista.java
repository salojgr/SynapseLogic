/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

/**
 *
 * @author Salomon Gonzalez
 * Esta clase representa un Nodo y sirve para construir varias estructuras de datos.
 * @param <T> tipo de dato del valor almacenado en el nodo
 */
public class NodoLista <T>{
    private T dato;
    private NodoLista<T> siguiente;
    
    public NodoLista(T dato){
        this.dato = dato;
        this.siguiente = null;
    }
    /**
 *
 * Permite obtener el dato privato a traves de un getter
 * @return el dato que almacena el nodo
 */
    public T getDato(){
        return dato;    
}
    /**
     * Permite cambiar el dato a traves de un setterPermite cambiar el dato a traves de un setter
     * @param dato  dato que se quiere cambiar
     */
    public void setDato(T dato){
        this.dato = dato;
    }
    /**
 *
 * Permite obtener la direccion del siguiente nodo a traves de un getter
 * @return la direccion del siguiente nodo
 */
    public NodoLista<T> getSiguiente(){
        return siguiente;
    }
 /**
 *
 * Permite cambiar la direccion del siguiente nodo a traves de un setter
 * @param siguiente la direccion que se quiere cambiar
 */
    public void setSiguiente(NodoLista<T> siguiente){
        this.siguiente = siguiente;
    }
    
}

