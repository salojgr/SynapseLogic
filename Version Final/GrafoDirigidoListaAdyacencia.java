/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

/**
 * Clase estructural encargada de mantener la red sináptica.
 * @author Luis Velásquez
 */
public class GrafoDirigidoListaAdyacencia { //Declaración de variables
    private TablaHash<String, Neurona> neuronas;
    private TablaHash<String, ListaEnlazada<Sinapsis>> adyacencias;
    private int contadorNeuronas;
    private int contadorSinapsis;
    private ListaEnlazada<String> listaIds;
    
    //Constructor de la clase
    public GrafoDirigidoListaAdyacencia(){
        this.neuronas = new TablaHash<>(100);
        this.adyacencias = new TablaHash<>(100);
        this.contadorNeuronas = 0;
        this.contadorSinapsis = 0;
        this.listaIds = new ListaEnlazada<>();
    }
    
    
    // Obtener adyacencias de una neurona usando una Tabla Hash.
    public ListaEnlazada<Sinapsis> obtenerAdyacentes(String id){
        return adyacencias.buscar(id);
    }
    
    public void agregarNeurona(Neurona n){ //Se agrega una neurona al grafo de la red.
        if (n != null && !neuronas.contieneClave(n.getID())){ //Verificar que el ID de la neurona exista
            neuronas.insertar(n.getID(), n);
            adyacencias.insertar (n.getID(), new ListaEnlazada<Sinapsis>());
            listaIds.insertarFinal(n.getID());
            contadorNeuronas++; 
        }
    }
    
   
    public boolean eliminarNeurona(String id) { //Eliminar una neurona de la red

        if (id == null || !neuronas.contieneClave(id)) {
            return false;
        }

        // Restar las sinapsis salientes de la neurona eliminada
        ListaEnlazada<Sinapsis> salientes = adyacencias.buscar(id);

        if (salientes != null) {
            contadorSinapsis -= salientes.tamano();
        }

        // Eliminar sinapsis entrantes desde otras neuronas hacia esta neurona
        for (int i = 0; i < listaIds.tamano(); i++) {
            String idActual = listaIds.obtener(i);

            if (idActual != null && !idActual.equals(id)) {
                eliminarSinapsis(idActual, id);
            }
        }

        // Eliminar la neurona y su lista de adyacencia
        neuronas.eliminar(id);
        adyacencias.eliminar(id);
        listaIds.eliminar(id);
        contadorNeuronas--;

        return true;
    }
        
        public boolean agregarSinapsis(String idOrigen, String idDestino, double distancia, String neurotransmisor, double k){
            if (!existeNeurona(idOrigen)) {
                agregarNeurona(new Neurona(idOrigen));
                 }

            if (!existeNeurona(idDestino)) {
                agregarNeurona(new Neurona(idDestino));
                }
            
            //Evitar Duplicados
            if (existeSinapsis(idOrigen, idDestino)) {
                return false;
            }
            
            
            /**
            * Crear un objeto Sinapsis con su distancia, neurotransmisor y factor k, 
            * e insertarlo al final de la lista de adyacencias de la neurona origen.
            */
            
            if (neuronas.contieneClave(idOrigen) && neuronas.contieneClave(idDestino)){
                Sinapsis nueva = new Sinapsis(neuronas.buscar(idOrigen), neuronas.buscar(idDestino), distancia, neurotransmisor, k);
                adyacencias.buscar(idOrigen).insertarFinal(nueva);
                contadorSinapsis++; //Aumento en el contador
            }
            return true;
        }
        
        /**
         * Elimina una sinapsis dirigida específica del grafo.
         * @param idOrigen ID de la neurona origen.
         * @param idDestino ID de la neurona destino.
         * @return true si se eliminó la sinapsis; false si no existía.
         */
        public boolean eliminarSinapsis(String idOrigen, String idDestino) {
            if (idOrigen == null || idDestino == null) {
                return false;
            }

            /**
             * Buscar la lista de conexiones de la neurona origen y recorrerla
             * hasta encontrar la que apunta al destino indicado para borrarla.
             */
            
            ListaEnlazada<Sinapsis> lista = adyacencias.buscar(idOrigen);

            if (lista == null || lista.estaVacia()) {
                return false;
            }

            for (int i = 0; i < lista.tamano(); i++) {
                Sinapsis s = lista.obtener(i);

                if (s != null
                        && s.getDestino() != null
                        && idDestino.equals(s.getDestino().getID())) {

                    boolean eliminada = lista.eliminar(s);

                    if (eliminada) {
                        contadorSinapsis--; //Se reduce el contador
                    }

                    return eliminada;
                }
            }

            return false;
        }
        
        //Getters necesarios para ejecutar el código de "SimuladorDeterioro". Ejercen funciones de conteo y consulta.
        public ListaEnlazada<Sinapsis> obtenerSinapsisDe(String id){
            return adyacencias.buscar(id);
        }
        
        public ListaEnlazada<String> getListaIds(){
            return listaIds;
        }
        
        public int getContadorSinapsis(){
            return contadorSinapsis;
        }
        
        public Neurona obtenerNeurona(String id){
            return neuronas.buscar(id);
        }
        
        public int getContadorNeuronas(){
            return contadorNeuronas;
        }
        public boolean existeNeurona(String id) {
            return id != null && neuronas.contieneClave(id);
        }
        
        // Prevención de errores al verificar la existencia de una sinapsis
        public  boolean existeSinapsis(String origen, String destino){
            if(origen == null || destino == null){
                return false;
            }
            if(!existeNeurona(origen)){
                return false;
            }
            
            //Búsqueda de la sinapsis en la lista de adyacencias para verificar si es válida la operación
            ListaEnlazada<Sinapsis> listaAdyacentes = adyacencias.buscar(origen);
            
            if (listaAdyacentes == null || listaAdyacentes.estaVacia()) {
                return false; // La lista está vacía, retorna false
            }
            
            for (int i=0; i < listaAdyacentes.tamano(); i++ ){
                Sinapsis sinapsisActual = listaAdyacentes.obtener(i);
                  
                if(destino.equals(sinapsisActual.getDestino().getID())){ // La sinapsis existe, retorna true
                    return true;
                }
                  
            }
            return false; //La sinapsis no existe, retorna false
        }
}
    
    
        
    

