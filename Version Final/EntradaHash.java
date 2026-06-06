/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

/**
 *
 * @author Salomon Gonzalez
 * Representa una entrada clave-valor dentro de una TablaHash.
 *
 * @param <K> tipo de la clave
 * @param <V> tipo del valor
 */
public class EntradaHash<K,V>{
    private final K clave;
    private V valor;
    
    public EntradaHash(K clave, V valor){
        this.clave = clave;
        this.valor = valor;
        
    }
      /**
     * Obtiene la clave.
     *
     * @return clave de la entrada
     */
    public K getClave(){
        return this.clave;
    }
      /**
     * Obtiene el valor.
     *
     * @return el valor asociado.
     */
    
    public V getValor(){
        return this.valor;
    }
        /**
     * Modifica el valor asociado.
     *
     * @param valor nuevo valor
     */
    public void setValor(V valor){
        this.valor = valor;
    }
    
    /**
     * Compara una clave con la clave de la entrada.
     *
     * @param claveBuscada clave a comparar
     * @return true si las claves son iguales, false en caso contrario
     */
     public boolean mismaClave(K claveBuscada){
         if (claveBuscada==null || this.clave==null){
             return false;
         }       
         return this.clave.equals(claveBuscada);
     }
}

