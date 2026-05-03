package synapselogic;

/**
 *
 * @author Salomon Gonzalez
 * Esta clase representa un Nodo y sirve para construir varias estructuras de datos.
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
 *
 * Permite cambiar el dato a traves de un setter
 * @params el dato que se quiere cambiar
 */
    public void setDato(T dato){
        this.dato = dato;
    }
    /**
 *
 * Permite obtener la direccion del siguiente nodo a traves de un getter
 * @return la direccion del siguiente nodo
 */
    public NodoLista getSiguiente(){
        return siguiente;
    }
 /**
 *
 * Permite cambiar la direccion del siguiente nodo a traves de un setter
 * @params  la direccion que se quiere cambiar
 */
    public void setSiguiente(NodoLista siguiente){
        this.siguiente = siguiente;
    }
    
}
