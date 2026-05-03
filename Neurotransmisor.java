package neurotransmisor;

/**
 * Creación de clase Neurotransmisor con sus respectivos atributos.
 */
public class Neurotransmisor {
    private final String id;
    private final String nombre;
    private final double velocidad;
    private final String efecto;
    private final String descripcion;
    
    // Constructor de la clase
    public Neurotransmisor(String id, String nombre, double velocidad, String efecto, String descripcion){
        this.id = id;
        this.nombre = nombre;
        this.velocidad = velocidad;
        this.efecto = efecto;
        this.descripcion = descripcion;    
    }
    
    //Getters y Setters
    public String getId(){
        return id;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public double getVelocidad(){
        return velocidad;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    
    public String getEfecto(){
        return efecto;
    }
    
    //Representacuión textual de los atributos
    @Override
    public String toString(){
        return "Neurotransmisor {" +
                "ID='" + id + '\'' +
                ", Nombre='" + nombre + '\'' +
                ", Velocidad=" + velocidad +
                ", Efecto='" + efecto + '\'' +
                ", Descripcion='" + descripcion + '\'' +
                '}';
    }
}
