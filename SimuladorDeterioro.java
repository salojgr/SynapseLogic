/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

public class SimuladorDeterioro {
    private GrafoDirigidoListaAdyacencia grafo;
    
    public SimuladorDeterioro(GrafoDirigidoListaAdyacencia grafo){
        this.grafo = grafo;
    }
    
    
    public void ejecutarCicloFatiga(){   //Se recorre la red y se aplica la fatiga en cada conexión(sinapsis).
        ListaEnlazada<String> ids = grafo.getListaIds();
        for (int i = 0; i < ids.tamano(); i++){
            ListaEnlazada<Sinapsis> lista = grafo.obtenerSinapsisDe(ids.obtener(i));
            for (int j = 0; j < lista.tamano(); j++){
                lista.obtener(j).fatiga();
            }
        }
    }
    
    //Se simula un traumatismo, lo cual causa que se elimine una neurona de la red sináptica
    public void simularTraumatismo (String idNeurona){ 
        grafo.eliminarNeurona(idNeurona);
    }
    
}
