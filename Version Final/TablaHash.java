/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;


/**
 * @author Salomon Gonzalez
 * Tabla hash implementada con encadenamiento separado (Para evitar colisiones)
 * @param <K> tipo de clave
 * @param <V> tipo de valor
 * 
 * 
 */
public class TablaHash<K,V>{
    private ListaEnlazada<EntradaHash<K,V>>[] tabla;
    private int capacidad;
    private int tamano;
    
    private static final double FACTOR_CARGA_MAX = 0.75;
    
    @SuppressWarnings("unchecked")
    public TablaHash(int capacidadInicial){
        // Validamos capacidad
        if(capacidadInicial<= 0){
            capacidadInicial = 50;
        }
        this.capacidad = capacidadInicial;
        this.tamano = 0;
        this.tabla = new ListaEnlazada[capacidadInicial]; //Inicializamos el array de tipo Listas
        
        
        for(int i=0;i<capacidadInicial;i++){
            
            tabla[i] = new ListaEnlazada<>();
        }
    }
    
    public TablaHash(){
        this(50);
    }
    /**
     * Calcula el indice donde debe ubicarse la clave
     * @param clave
     * @return indice correspondiente
     */
    private int obtenerIndice(K clave){
        return (Math.abs(clave.hashCode()) & 0x7fffffff) % capacidad;
        
    }
    /**
     * Verifica si la tabla hash no tiene entradas.
     * @return true si la tabla no contiene elementos; false de lo contrario
     */
    public boolean estaVacia(){
        return tamano ==0;
    }
    /**
     * Obtiene la cantidad de elementos almacenadas en la hash table
     * @return numero de elementos almacenados
     */
    public int tamano(){
        return tamano;
    }
    
    /**
     * Inserta una entrada dentro de la hash table 
     * @param clave clave que identifica al valor
     * @param valor valor asociado
     * @return false si el procedimiento tuvo errores y true en caso contrario
     */
    public boolean insertar(K clave, V valor) {
        if (clave == null || valor == null) {
            return false;
        }

        int indice = obtenerIndice(clave);
        ListaEnlazada<EntradaHash<K, V>> cubeta = tabla[indice];

        for (int i = 0; i < cubeta.tamano(); i++) {
            EntradaHash<K, V> entrada = cubeta.obtener(i);

            if (entrada.mismaClave(clave)) {
                entrada.setValor(valor);
                return true;
            }
        }

        cubeta.insertarFinal(new EntradaHash<>(clave, valor));
        tamano++;

        if ((double) tamano / capacidad > FACTOR_CARGA_MAX) {
            redimensionar();
        }

        return true;
    }
    /**
     * Busca el valor asociado dado una clave dentro de la hash table
     * 
     * @param clave clave que se desea buscar
     * @return valor asociado a la clave o null si no existe.
     */
    public V buscar(K clave) {
        if (clave == null) {
            return null;
        }

        int indice = obtenerIndice(clave);
        ListaEnlazada<EntradaHash<K, V>> cubeta = tabla[indice];

        for (int i = 0; i < cubeta.tamano(); i++) {
            EntradaHash<K, V> entrada = cubeta.obtener(i);

            if (entrada.mismaClave(clave)) {
                return entrada.getValor();
            }
        }

        return null;
    }
    /**
     * Verifica si una clave existe dentro de una hash table
     * @param clave que desea verificar
     * @return true si la clave existe, false en caso contrario
     */
    public boolean contieneClave(K clave) {
        return buscar(clave) != null;
    }
    /**
     * Elimina la entrada a partir de su clave
     * @param clave clave de la entrada que desea eliminar
     * @return true si la clave fue eliminada, false si la clave no existe o es nula.
     */
    public boolean eliminar(K clave) {
        if (clave == null) {
            return false;
        }

        int indice = obtenerIndice(clave);
        ListaEnlazada<EntradaHash<K, V>> cubeta = tabla[indice];

        for (int i = 0; i < cubeta.tamano(); i++) {
            EntradaHash<K, V> entrada = cubeta.obtener(i);

            if (entrada.mismaClave(clave)) {
                cubeta.eliminar(entrada);
                tamano--;
                return true;
            }
        }

        return false;
    }
    /**
     * Elimina todas las entradas almacenadas.
     */
    public void limpiar() {
        for (int i = 0; i < capacidad; i++) {
            tabla[i].limpiar();
        }

        tamano = 0;
    }
    /**
     * Duplica la capacidad de la hash table y reubicar las entradas.
     */
    @SuppressWarnings("unchecked")
    private void redimensionar() {
        ListaEnlazada<EntradaHash<K, V>>[] tablaAnterior = tabla;

        int capacidadAnterior = capacidad;
        capacidad = capacidad * 2;
        tamano = 0;

        tabla = new ListaEnlazada[capacidad];

        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new ListaEnlazada<>();
        }

        for (int i = 0; i < capacidadAnterior; i++) {
            ListaEnlazada<EntradaHash<K, V>> cubeta = tablaAnterior[i];

            for (int j = 0; j < cubeta.tamano(); j++) {
                EntradaHash<K, V> entrada = cubeta.obtener(j);
                insertar(entrada.getClave(), entrada.getValor());
            }
        }
    }
        
    }
    
    

