/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package synapselogic;

/**
 * Creación de la neurona (nodo) junto con sus atributos.
 * Dato "id" para representar el identificador único de la neurona
 * Dato "activa" que representa el estado inicial de la neurona (activa).
 * @author Luis Velásquez
 */
public class Neurona {
    private final String id;
    private boolean activa;
    
    /**
     * Constructor para inicializar la Neurona
     * @param id Identificador único proveniente del archivo CSV cargado.
     */
    public Neurona(String id){
        this.id = id;
        this.activa = true;           
    }
    
    //Creación de métodos para acceder (get) y modificar (set) atributos privados de la clase Neurona.
    public String getID(){
        return id;
    }

    public boolean isActiva(){
        return activa;
    }
        
    public void setActiva(boolean activa){
        this.activa = activa;
    }
    
    //Uso de @override para permitir representar en lenguaje comprensible los atributos correspondientes a la Neurona.
    @Override
    public String toString(){
        return "Neurona{" + "id ='" + id + '\'' + ", activa=" + activa + '}';
    }
    
}