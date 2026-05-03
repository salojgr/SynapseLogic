package sinapsis;
import neurona.Neurona;
/**
 * Creación de la clase "arista" entre las neuronas (nodos) junto con sus atributos.
 */
public class Sinapsis {
    private final Neurona origen;
    private final Neurona destino;
    private final double distancia;
    private final String neurotransmisor;
    private double k;
    private boolean activa;
    
    //Constructor de la clase Sinapsis
    public Sinapsis(Neurona origen, Neurona destino, double distancia, String neurotransmisor){
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
        this.neurotransmisor = neurotransmisor;
        this.k = 1.0;
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
    
    
    public void fatiga(){ //Aplicación de la fatiga para generar deterioro cognitivo (multiplicación de k por 1.2).
        this.k *= 1.2;
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
    
    @Override
    public String toString(){
        return "Sinapsis [" + neurotransmisor + "]: " + origen.getID()+ " -> " + 
               destino.getID()+ " (d=" + distancia + ", k=" + k + ", activa=" + activa + ")";
    }

}
