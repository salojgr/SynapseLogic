/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

/**
 *
 * Cola implementada mediante una lista enlazada.
 * 
 * @param <T> tipo de dato almacenado en la cola.
 * @author Salomón González
 */
public class Cola<T> {
    
    ListaEnlazada<T> elementos;
    /**
     * Crea una cola vacía.
     */
    public Cola(){
        this.elementos = new ListaEnlazada<>();
    }
    /**
     * 
     * Verifica si la cola esta vacía.
     * 
     * @return true si la cola no contiene elementos. 
     */
    public boolean estaVacia(){
        return elementos.estaVacia();
    }
    
    /**
     * Obtiene la cantidad de elementos en la cola.
     * 
     * @return tamaño de la cola
     */
    
    public int tamano(){
        return elementos.tamano();
    }
    /**
     * Inserta un elemento al final de la cola.
     *
     * @param valor elemento a encolar
     * @return true si se insertó correctamente
     */    
     public boolean encolar(T valor){
         if(valor==null){
             return false;
         }
         return elementos.insertarFinal(valor);
         
     }
     

    /**
     * Elimina y retorna el elemento ubicado al frente de la cola.
     *
     * @return elemento eliminado, o null si la cola está vacía
     */     
     
     public T desencolar(){
         return elementos.eliminarInicio();
     }
    /**
     * Obtiene el elemento ubicado al frente de la cola sin eliminarlo.
     *
     * @return elemento del frente, o null si la cola está vacía
     */     
     
     public T frente(){
         if(!this.estaVacia()){
         return elementos.primero().getDato();
         }else{
             return null;
         }
     }

    /**
     * Elimina todos los elementos de la cola.
     */

     
     public void limpiar(){
         this.elementos.limpiar();
     }
}