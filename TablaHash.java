/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;


/**
 *Tabla hash implementada con encadenamiento separado
 * 
 * @author Salomon Gonzalez
 */
public class TablaHash<K,V>{
    private ListaEnlazada<EntradaHash<K,V>>[] tabla;
    private int capacidad;
    private int tamano;
    
    private static final double FACTOR_CARGA_MAX = 0.75;
    
    @SuppressWarnings("unchecked")
    public TablaHash(int capacidadInicial){
        this.capacidad = capacidadInicial;
        this.tamano = 0;
        this.tabla = new ListaEnlazada[capacidadInicial];//Inicializamos el array de tipo Listas
        
        
        for(int i=0;i<capacidadInicial;i++){
            
            tabla[i] = new ListaEnlazada<>();
        }
    }
    
    public TablaHash(){
        this(50);
    }
    
    private int obtenerIndice(K clave){
        return (Math.abs(clave.hashCode()) & 0x7fffffff) % capacidad;
        
    }
    
    public boolean estaVacia(){
        return tamano ==0;
    }
    
    public int tamano(){
        return tamano;
    }
    
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

    public boolean contieneClave(K clave) {
        return buscar(clave) != null;
    }

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

    public void limpiar() {
        for (int i = 0; i < capacidad; i++) {
            tabla[i].limpiar();
        }

        tamano = 0;
    }

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
    
    

