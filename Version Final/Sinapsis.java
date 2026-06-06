/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

/**
 * Creación de la clase "arista" entre las neuronas (nodos) junto con sus atributos.
 * @author Luis Velásquez
 */
public class Sinapsis {
    private final Neurona origen;
    private final Neurona destino;
    private final double distancia;
    private final String neurotransmisor;
    private double k;
    private boolean activa;
    
    //Constructor de la clase Sinapsis
    public Sinapsis(Neurona origen, Neurona destino, double distancia, String neurotransmisor,double k){
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
        this.neurotransmisor = neurotransmisor;
        this.k = k;
        this.activa = true;
    }
    
    /**
     * Calcular el peso de la arista (W)usando la fórmula: W = d / (v * k) 
     * @param v Velocidad correspondiente de cada neurotransmisor.
     * @return El peso (W) de la conexión (arista).
     */
    
    public double calcularW(double v){        
        return this.distancia / (v * this.k);
    }
    
    
    public void fatiga(){ //Aplicación de la fatiga para generar deterioro cognitivo (multiplicación de k por 0.8).
        this.k *= 0.8;
    // Si la eficiencia cae demasiado, la conexión queda funcionalmente inactiva. Por ahora 0.10
    if (this.k < 0.10) {
        this.activa = false;
    }
    //Condicional que evita que k caiga a 0
        if (this.k < 0.01) {
            this.k = 0.01;
        }
    }
    
    // Getters y Setters
    public Neurona getOrigen(){
        return origen;
    }
    
    public Neurona getDestino(){
        return destino;
    }
    
    public String getNeurotransmisor(){
        return neurotransmisor;
    }
    
    public double getK(){
        return k;
    }
    
    public boolean isActiva(){
        return activa;
    }
    
    public void setActiva(boolean activa){
        this.activa = activa;
    }
    
    public double getDistancia() {
    return distancia;
    }
    @Override
    public String toString(){
        return "Sinapsis [" + neurotransmisor + "]: " + origen.getID()+ " -> " + 
               destino.getID()+ " (d=" + distancia + ", k=" + k + ", activa=" + activa + ")";
    }

}
