/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package neurona;

/**
 * Creación de la neurona (nodo) junto con sus atributos.
 * Dato "id" para representar el identificador único de la neurona
 * Dato "activa" que representa el estado inicial de la neurona (activa).
 */
public class Neurona {
    private final String id;
    private boolean activa;
    
    /**
     * Constructor para inicializar a la Neurona
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
        
    public void setActiva(boolean Activa){
        this.activa = activa;
    }
    
    //Uso de @override para permitir representar en lenguaje humano los atributos correspondientes a la Neurona.
    @Override
    public String toString(){
        return "Neurona{" + "id ='" + id + '\'' + ", activa=" + activa + '}';
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
}