/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

/**
 * MinHeap: Estructura de Datos Manual: cola de prioridad basada en un monticulo binario minimo.
 * Permite almacenar elementos de clase ElementoHeap y extraer siempre el elemento con menor prioridad (peso W).
 * @param <T> Tipo de dato del identificador del elemento (ElementoHeap).
 */
public class MinHeap<T> {
    private ElementoHeap<T>[] arreglo;
    private int capacidad;
    private int tamano;
    
    /**
     *
     * @param capacidadInicial
     */
    @SuppressWarnings("unchecked") 
    public MinHeap(int capacidadInicial){
        this.capacidad = capacidadInicial;
        this.tamano = 0;
        this.arreglo = (ElementoHeap<T>[]) new ElementoHeap<?>[capacidadInicial];
    }
    
    /**
     *
     * @return
     */
    public boolean esVacio(){
        return this.tamano == 0;
    }
    
    public int getTamano(){
        return this.tamano;
    }
    
    @SuppressWarnings("unchecked")
    public void redimensionar(){
        this.capacidad *= 2;
        
        ElementoHeap<T>[] arregloViejo = this.arreglo;
        
        this.arreglo = (ElementoHeap<T>[]) new ElementoHeap<?>[this.capacidad];
        
        System.arraycopy(arregloViejo, 0, this.arreglo, 0, this.tamano);
         
    }
    
    public void insertar (ElementoHeap<T> nuevoElemento){
        if (this.tamano >= this.capacidad){
            redimensionar();
        }
        this.arreglo[this.tamano] = nuevoElemento;
        this.tamano++;
        flotar(this.tamano - 1);
    }
    
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
    
    private int padre(int i){
        return (i - 1) / 2;
    }
    
    private int hijoIzquierdo(int i) { 
        return (2 * i) + 1; 
    }
    
    private int hijoDerecho(int i) { 
        return (2 * i) + 2; 
    }
    
    private void intercambiar(int i, int j) {
        ElementoHeap<T> temp = this.arreglo[i];
        this.arreglo[i] = this.arreglo[j];
        this.arreglo[j] = temp;
    }
    
    private void flotar(int i) {
        while (i > 0 && this.arreglo[i].compararElementos(this.arreglo[padre(i)]) < 0) {
            intercambiar(i, padre(i));
            i = padre(i);
        }
    }
    
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
