/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

/**
 * Clase encargada de persistir el estado actual en memoria de la red sináptica 
 * y el diccionario de neurotransmisores, exportándolos a formato de archivo CSV.
 * Permite cumplir con el criterio de Información Fiel de la rúbrica.
 * @author Daniel Vera
 */
public class GuardadorCSV {

    /**
     * Exportar la red sináptica actual a un archivo CSV.
     * Guarda la topología vigente preservando las alteraciones de los coeficientes k.
     * @param rutaArchivo Ruta física del sistema de archivos donde se guardará.
     * @param grafo Estructura del grafo manual que contiene la red en memoria.
     * @return Mensaje descriptivo con el resultado de la operación.
     */
    public String guardarRed(String rutaArchivo, GrafoDirigidoListaAdyacencia grafo) {
    if (grafo == null) {
        return "Error: No existe ninguna estructura de red en memoria para exportar.";
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
        // Encabezado compatible con el cargador actual.
        // Las filas de neurona aislada se guardan como: ID,,,,
        bw.write("origen,destino,distancia,neurotransmisor,k");
        bw.newLine();

        ListaEnlazada<String> idsNeuronas = grafo.getListaIds();
        int neuronasGuardadas = 0;
        int sinapsisGuardadas = 0;

        // Primero guardamos todas las neuronas activas.
        // Esto permite conservar neuronas que no tengan ninguna sinapsis.
        for (int i = 0; i < idsNeuronas.tamano(); i++) {
            String idNeurona = idsNeuronas.obtener(i);
            Neurona neurona = grafo.obtenerNeurona(idNeurona);

            if (neurona != null && neurona.isActiva()) {
                bw.write(neurona.getID() + ",,,,");
                bw.newLine();
                neuronasGuardadas++;
            }
        }

        // Luego guardamos las sinapsis activas.
        for (int i = 0; i < idsNeuronas.tamano(); i++) {
            String idOrigen = idsNeuronas.obtener(i);
            Neurona neuronaOrigen = grafo.obtenerNeurona(idOrigen);

            if (neuronaOrigen == null || !neuronaOrigen.isActiva()) {
                continue;
            }

            ListaEnlazada<Sinapsis> listaAdyacentes = grafo.obtenerAdyacentes(idOrigen);

            if (listaAdyacentes == null) {
                continue;
            }

            for (int j = 0; j < listaAdyacentes.tamano(); j++) {
                Sinapsis sinapsisActual = listaAdyacentes.obtener(j);

                if (sinapsisActual != null
                        && sinapsisActual.isActiva()
                        && sinapsisActual.getDestino() != null
                        && sinapsisActual.getDestino().isActiva()) {

                    String linea = String.format(Locale.US, "%s,%s,%.2f,%s,%.6f",
                            sinapsisActual.getOrigen().getID(),
                            sinapsisActual.getDestino().getID(),
                            sinapsisActual.getDistancia(),
                            sinapsisActual.getNeurotransmisor(),
                            sinapsisActual.getK()
                    );

                    bw.write(linea);
                    bw.newLine();
                    sinapsisGuardadas++;
                }
            }
        }

        return "Éxito: Red sináptica respaldada correctamente. ("
                + neuronasGuardadas + " neuronas y "
                + sinapsisGuardadas + " conexiones guardadas).";

    } catch (IOException e) {
        return "Error crítico de escritura al guardar la red: " + e.getMessage();
    }
}

    /**
     * Exportar el diccionario de neurotransmisores actual a un archivo CSV.
     * @param rutaArchivo Ruta física del sistema de archivos.
     * @param diccionario TablaHash manual que almacena las sustancias químicas.
     * @param grafo Referencia a la red para extraer de manera ordenada las claves.
     * @return Mensaje descriptivo con el resultado de la operación.
     */
    public String guardarNeurotransmisores(String rutaArchivo, TablaHash<String, Neurotransmisor> diccionario, GrafoDirigidoListaAdyacencia grafo) {
        if (diccionario == null || diccionario.estaVacia()) {
            return "Error: El diccionario de neurotransmisores se encuentra vacío o no ha sido inicializado.";
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            // Escritura del encabezado estándar
            bw.write("id,nombre,efecto,velocidad,descripcion");
            bw.newLine();

            int sustanciasGuardadas = 0;
            
            // Recorremos la red sináptica para recolectar e identificar de manera única los neurotransmisores usados
            ListaEnlazada<String> idsNeuronas = grafo != null ? grafo.getListaIds() : new ListaEnlazada<>();
            TablaHash<String, Boolean> yaEscritos = new TablaHash<>();

            // Primero aseguramos exportar todos los que estén mapeados en la red activa
            for (int i = 0; i < idsNeuronas.tamano(); i++) {
                ListaEnlazada<Sinapsis> adyacentes = grafo.obtenerAdyacentes(idsNeuronas.obtener(i));
                if (adyacentes != null) {
                    for (int j = 0; j < adyacentes.tamano(); j++) {
                        String ntId = adyacentes.obtener(j).getNeurotransmisor();
                        
                        if (!yaEscritos.contieneClave(ntId)) {
                            Neurotransmisor nt = diccionario.buscar(ntId);
                            if (nt != null) {
                                String linea = String.format("%s,%s,%s,%.2f,%s",
                                        nt.getId(), nt.getNombre(), nt.getEfecto(), nt.getVelocidad(), nt.getDescripcion()
                                );
                                bw.write(linea);
                                bw.newLine();
                                yaEscritos.insertar(ntId, true);
                                sustanciasGuardadas++;
                            }
                        }
                    }
                }
            }
            
            return "Éxito: Diccionario de neurotransmisores guardado correctamente. (" + sustanciasGuardadas + " registrados).";

        } catch (IOException e) {
            return "Error crítico de escritura al guardar el diccionario: " + e.getMessage();
        }
        
    }
    
}