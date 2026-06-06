/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

/**
 * Clase encargada de representar los resultados de conectividad obtenidos tras 
 * ejecutar los algoritmos BFS o DFS en la red sináptica.
 * @author Daniel Vera
 */
public class ResultadoConectividad {
   
    // Declaración de variables
    private String idFuente;
    private String algoritmo;
    private ListaEnlazada<String> alcanzables;
    private ListaEnlazada<String> inalcanzables;
    private boolean fuertementeConexa;
    private ListaEnlazada<String> secuenciaVisita = new ListaEnlazada<>();
    
    // Constructor de la clase
    /**
     * Inicializar los parámetros base de un resultado de conectividad.
     * @param idFuente Identificador de la neurona fuente.
     * @param algoritmo Nombre del algoritmo de exploración utilizado (BFS/DFS).
     * @param alcanzables Lista de neuronas conectadas.
     * @param inalcanzables Lista de neuronas sin camino.
     * @param fuertementeConexa Estado general de conectividad del grafo.
     */
    public ResultadoConectividad(String idFuente, String algoritmo, ListaEnlazada<String> alcanzables, ListaEnlazada<String> inalcanzables, boolean fuertementeConexa ){
    
      this.idFuente = idFuente;
      this.algoritmo = algoritmo;
      this.alcanzables = alcanzables;
      this.inalcanzables = inalcanzables;
      this.fuertementeConexa = fuertementeConexa;
      
    }
    
    /**
     * Añadir una neurona a la lista de alcanzables.
     * @param id Identificador de la neurona.
     */
    public void agregarAlcanzable(String id){
      this.alcanzables.insertarFinal(id);
    }
      
    /**
     * Añadir una neurona a la lista de inalcanzables.
     * @param id Identificador de la neurona.
     */
    public void agregarInalcanzable(String id){
      this.inalcanzables.insertarFinal(id);
    }
    
    // Getters y Setters
    
    public ListaEnlazada<String> getAlcanzables(){
        return alcanzables;
    }
      
    public ListaEnlazada<String> getInalcanzables(){
        return inalcanzables;
    }
      
    public boolean esFuertementeConexa() {
        return fuertementeConexa;
    }
    
    public void setFuertementeConexa(boolean fuertementeConexa) {
        this.fuertementeConexa = fuertementeConexa;
    }
    public String getIdFuente(){
        return idFuente;
    }
    public String getAlgoritmo(){
        return algoritmo;
    }
    public int cantidadAlcanzables() {
        return alcanzables.tamano();
    }

    public int cantidadInalcanzables() {
        return inalcanzables.tamano();
    }
    
    /**
     * Método GETTER: Permite a la interfaz gráfica (PanelResultados) leer 
     * el orden cronológico en el que se exploraron las neuronas.
     * @return List de Strings con los IDs de las neuronas en orden de visita.
     */
    public ListaEnlazada<String> getSecuenciaVisita() {
        return this.secuenciaVisita;
    }

    /**
     * Método SETTER: Permite al motor analítico (Grafo/Controlador) inyectar
     * la lista con el recorrido final una vez concluido el algoritmo.
     * @param secuenciaVisita Lista con la traza de exploración calculada.
     */
    public void setSecuenciaVisita(ListaEnlazada<String> secuenciaVisita) {
        // Programación defensiva: Si pasan un nulo, inicializa una lista vacía para evitar fallos
        if (secuenciaVisita == null) {
            this.secuenciaVisita = new ListaEnlazada<>();
        } else {
            this.secuenciaVisita = secuenciaVisita;
        }
    }
}
    
    
    

