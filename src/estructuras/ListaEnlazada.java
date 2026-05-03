/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

/**
 *
 * @author Salomon Gonzalez
 * 
 * Esta clase representa la estructura de datos Lista simplemente enlazada
 *
 * @param <T> tipo de dato almacenado en la lista
 */
public class ListaEnlazada<T>{
    private NodoLista<T> pfirst;
    private NodoLista<T> plast;
    private int tamano;

    /**
     * Crea una lista vacía.
     */

    public ListaEnlazada(){
        this.pfirst = null;
        this.plast = null;
        this.tamano = 0;
    }
    
    
    /**
     * Verifica si la lista está vacía.
     *
     * @return true si no contiene elementos
     */        
    public boolean estaVacia(){
        return pfirst==null;
    }
       /**
     * Obtiene el primer nodo.
     *
     * @return primer nodo de la lista
     */
    public NodoLista<T> primero(){
        return pfirst;
    }
    /**
     * Obtiene el último nodo.
     *
     * @return último nodo de la lista
     */    
    public NodoLista<T> ultimo(){
        return plast;
    }
    /**
     * Obtiene el tamaño de la lista.
     *
     * @return cantidad de elementos
     */    
    public int tamano(){
        return tamano;
    }
    /**
     * Inserta un dato al inicio de la lista.
     *
     * @param dato dato a insertar
     * @return true si se insertó correctamente
     */    
    public boolean insertarInicio(T dato){
        if (dato==null){
            return false;
        }
        NodoLista<T> nuevo = new NodoLista<>(dato);
        
        if (this.estaVacia()){
            pfirst = plast = nuevo;
        }
        else{
            nuevo.setSiguiente(pfirst);
            pfirst = nuevo;
        }
        this.tamano +=1;
        return true;
    }
    
    /**
     * Inserta un dato al final de la lista.
     *
     * @param dato dato a insertar
     * @return true si se insertó correctamente
     */
    
    public boolean insertarFinal(T dato){
        if (dato==null){
            return false;
        }
        NodoLista<T> nuevo = new NodoLista<>(dato);
        
        if (this.estaVacia()){
            pfirst = plast = nuevo;
        }
        else{
            plast.setSiguiente(nuevo);
            plast = nuevo;
            
        }
        this.tamano +=1;
        return true;
        
    }
    /*
    *Despues implemento un post-insertar O(n) primero tengo que ver si lo necesito
    */
    
    /**
     * Elimina el primer elemento de la lista.
     *
     * @return dato eliminado, o null si la lista está vacía
     */
    
    public T eliminarInicio(){
        if(this.estaVacia()){
            return null;
        }
        NodoLista<T> eliminado = pfirst;
        T datoEliminado = eliminado.getDato();
        pfirst = pfirst.getSiguiente();
        this.tamano -= 1;
        if (tamano==0){
            plast=null;
        }
        
        eliminado.setSiguiente(null);
        return datoEliminado;  
        
    }
    
     /**
     * Obtiene el dato ubicado en una posición específica.
     *
     * @param indice posición del dato
     * @return dato encontrado, o null si el índice es inválido
     */

    public T obtener(int indice) {
    if (indice < 0 || indice >= tamano) {
        return null;
    }

    NodoLista<T> actual = pfirst;
    int contador = 0;

    while (contador < indice) {
        actual = actual.getSiguiente();
        contador++;
    }

    return actual.getDato();
    } 


    /**
     * Busca un dato dentro de la lista.
     *
     * @param valor dato a buscar
     * @return dato encontrado, o null si no existe
     */
    
    public T buscar(T valor) {
    if (valor == null) {
        return null;
    }

    NodoLista<T> actual = pfirst;

    while (actual != null) {
        if (actual.getDato().equals(valor)) {
            return actual.getDato();
        }

        actual = actual.getSiguiente();
    }

    return null;
    }
    
    /**
     * Elimina la primera aparición de un dato.
     *
     * @param valor dato a eliminar
     * @return true si el dato fue eliminado
     */
    public boolean eliminar(T valor){
         if (valor==null || this.estaVacia()){
             return false;
         }
         if (pfirst.getDato().equals(valor)){
             this.eliminarInicio();
             return true;
         }
         
        NodoLista<T> anterior = pfirst;
        NodoLista<T> actual = pfirst.getSiguiente();
        
        while(actual!=null){
            if(actual.getDato().equals(valor)){
               anterior.setSiguiente(actual.getSiguiente());
               
                if (actual == this.plast) {
                    this.plast = anterior;
                }

               actual.setSiguiente(null);
               this.tamano-=1;
               return true;
               
            }
            
            anterior = actual;
            actual = actual.getSiguiente();
            
        }
        
        return false;
         
     }
    
     /**
     * Elimina todos los elementos de la lista.
     */
    
    public void limpiar(){
        pfirst = plast = null;
        tamano = 0;
    }
    
    
}
