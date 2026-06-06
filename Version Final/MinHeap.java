/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

/**
 * MinHeap implementacion manual de la cola de prioridad
 * Permite almacenar elementos de clase ElementoHeap y extraer siempre el elemento con menor prioridad (peso W).
 * @param <T> Tipo de dato del identificador del elemento (ElementoHeap).
 * @author Salomón González
 */
public class MinHeap<T> {
    private ElementoHeap<T>[] arreglo;
    private int capacidad;
    private int tamano;
    
    /**
     *Constructor de la clase.
     * @param capacidadInicial
     */
    @SuppressWarnings("unchecked") 
    public MinHeap(int capacidadInicial){
        if (capacidadInicial <= 0) {
            capacidadInicial = 10;
        }
        this.capacidad = capacidadInicial;
        this.tamano = 0;
        this.arreglo = (ElementoHeap<T>[]) new ElementoHeap<?>[capacidadInicial];
    }
    
    /**
     * Verifica si esta vacio
     * @return true si esta vacio; false en el caso contrario
     */
    public boolean esVacio(){
        return this.tamano == 0;
    }
    /**
     * Obtiene la cantidad actual de elementos almacenados.
     * 
     * @return numero de elementos 
     */
    public int getTamano(){
        return this.tamano;
    }
    /**
     * Duplica la capacidad del arreglo interno del heap.
     * 
     * Este metodo se ejecuta cuando el arreglo ha alcanzado su capacidad maxima, 
     * y copia los elementos al nuevos arreglo.
     */
    @SuppressWarnings("unchecked")
    public void redimensionar(){
        this.capacidad *= 2;
        
        ElementoHeap<T>[] arregloViejo = this.arreglo;
        
        this.arreglo = (ElementoHeap<T>[]) new ElementoHeap<?>[this.capacidad];
        
        //aqui es donde es copia los elementos
        System.arraycopy(arregloViejo, 0, this.arreglo, 0, this.tamano); 

         
    }
    /**
     * Inserta un elemnto y reordena la estructura.
     * @param nuevoElemento  elemento con dato y prioridad asociada
     */
    public void insertar (ElementoHeap<T> nuevoElemento){
        if (this.tamano >= this.capacidad){
            redimensionar();
        }
        this.arreglo[this.tamano] = nuevoElemento;
        this.tamano++;
        flotar(this.tamano - 1);
    }
    /**
     * 
     * @return elemento con menor prioridad, o null si esta vacio.
     */
    public ElementoHeap<T> extraerMinimo(){
        if(esVacio()){
            return null;
        }
        ElementoHeap<T> minimo = this.arreglo[0];
        this.arreglo[0] = this.arreglo[this.tamano - 1];
        this.arreglo[this.tamano - 1] = null;
        this.tamano--;
        hundir(0);
        return minimo;
    }
    /**
     * Actualiza la prioridad de un elemento si la nueva prioridad es menor a la actual.
     * @param elemento elemento que se va a actualizar la prioridad
     * @param nuevaPrioridad Nueva prioridad asociada
     */
    public void actualizarPrioridad(T elemento, double nuevaPrioridad){
        for (int i = 0; i < this.tamano ; i++){
            if (this.arreglo[i].getElemento().equals(elemento)){
                if (nuevaPrioridad < this.arreglo[i].getPrioridad()){
                    this.arreglo[i] = new ElementoHeap<>(elemento, nuevaPrioridad);
                    flotar(i);
                }
                break;
            }
        }
        
    }
    /**
     * Calcula el indice del nodo padre de una posicion del arreglo
     * @param i indice del nodo hijo
     * @return  indice del nodo padre
     */
    private int padre(int i){
        return (i - 1) / 2;
    }
    /**
     * Calcula el indice del hijo izquierdo de una posicion del arreglo
     * @param i indice del nodo padre.
     * @return indice del hijo izquierdo.
     */
    private int hijoIzquierdo(int i) { 
        return (2 * i) + 1; 
    }
    /**
     * Calcula el indice del hijo derecho de una posicion del arreglo
     * @param i indice del nodo padre.
     * @return indice del hijo derecho.
    */
    private int hijoDerecho(int i) { 
        return (2 * i) + 2; 
    }
    /**
     * Intercambia dos elementos dentro del arreglo.
     * @param i indice del primer elemento
     * @param j indice del segundo elemento
     */
     
    private void intercambiar(int i, int j) {
        ElementoHeap<T> temp = this.arreglo[i];
        this.arreglo[i] = this.arreglo[j];
        this.arreglo[j] = temp;
    }
    /**
     * Desplaza un elemento hacia arriba para reorganizar el arreglo
     * @param i indice actual del elemento que va a flotar
     */
    private void flotar(int i) {
        while (i > 0 && this.arreglo[i].compararElementos(this.arreglo[padre(i)]) < 0) {
            intercambiar(i, padre(i));
            i = padre(i);
        }
    }
    /**
     * Desplaza un elemento hacia abajo para reorganizar el arreglo
     * @param i indice actual del elemento que se va a hundir
     */
    private void hundir(int i) {
        int menor = i;
        int izq = hijoIzquierdo(i);
        int der = hijoDerecho(i);

        if (izq < this.tamano && this.arreglo[izq].compararElementos(this.arreglo[menor]) < 0) {
            menor = izq;
        }
        if (der < this.tamano && this.arreglo[der].compararElementos(this.arreglo[menor]) < 0) {
            menor = der;
        }
        if (menor != i) {
            intercambiar(i, menor);
            hundir(menor);
        }
    }
    
    
}
