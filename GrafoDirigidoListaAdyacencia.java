/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

/**
 * Clase estructural encargada de mantener la red sináptica.
 */
public class GrafoDirigidoListaAdyacencia {
    private TablaHash<String, Neurona> neuronas;
    private TablaHash<String, ListaEnlazada<Sinapsis>> adyacencias;
    private int contadorNeuronas;
    private int contadorSinapsis;
    private ListaEnlazada<String> listaIds;
    
    public GrafoDirigidoListaAdyacencia(){
        this.neuronas = new TablaHash<>(100);
        this.adyacencias = new TablaHash<>(100);
        this.contadorNeuronas = 0;
        this.contadorSinapsis = 0;
        this.listaIds = new ListaEnlazada<>();
    }
    
    public void agregarNeurona(Neurona n){ //Se agrega una neurona al grafo de la red.
        if (n != null && !neuronas.contieneClave(n.getID())){
            neuronas.insertar(n.getID(), n);
            adyacencias.insertar (n.getID(), new ListaEnlazada<Sinapsis>());
            listaIds.insertarFinal(n.getID());
            contadorNeuronas++;
        }
    }
    
   
    public void eliminarNeurona (String id){ //Eliminación de la neurona y sus conexiones (entrantes y salientes).
        if (neuronas.contieneClave(id)){
            
            //Se restan las sinapsis salientes del contador global
            int salientes = adyacencias.buscar(id).tamano();
            contadorSinapsis -= salientes;
            
            //Eliminación de la neurona y su lista de adyacencia
            neuronas.eliminar(id);
            adyacencias.eliminar(id);
            contadorNeuronas--;
            
            //Eliminación de sinapsis entrantes
            for(int i = 0; i < listaIds.tamano(); i++){
                eliminarSinapsis(listaIds.obtener(i), id);
                }
            }
        }
        
        public void agregarSinapsis(String idOrigen, String idDestino, double d, double v, String nt){
            if (neuronas.contieneClave(idOrigen) && neuronas.contieneClave(idDestino)){
                Sinapsis nueva = new Sinapsis(neuronas.buscar(idOrigen), neuronas.buscar(idDestino), d, nt);
                adyacencias.buscar(idOrigen).insertarFinal(nueva);
                contadorSinapsis++;
            }
        }
        
        public void eliminarSinapsis(String idOrigen, String idDestino){
            ListaEnlazada<Sinapsis> lista = adyacencias.buscar(idOrigen);
            if (lista != null){
                for (int i = 0; i < lista.tamano(); i++){
                    Sinapsis s = lista.obtener(i);
                    if (s.getDestino().getID().equals(idDestino)){
                        lista.eliminar(s);
                        contadorSinapsis--;
                        break;
                    }
                }
            }
            
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

}

    
        
    

