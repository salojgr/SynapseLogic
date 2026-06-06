/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

/**
 * Clase encargada de analizar la conectividad de la red sináptica.
 * Implementa recorridos BFS y DFS sobre un grafo dirigido para detectar
 * neuronas alcanzables e inalcanzables desde una fuente.
 * @author Daniel Vera
 */
public class AnalizadorConectividad {
    
    /**
     * Validar que los parámetros de entrada requeridos para los algoritmos sean consistentes.
     * @param grafo Estructura de la red en la que se buscará.
     * @param idFuente Identificador de la neurona de partida.
     */
    private void validarEntrada(GrafoDirigidoListaAdyacencia grafo, String idFuente) {
         
        // Se previene la ejecución sobre redes nulas
        if (grafo == null) {
            throw new IllegalArgumentException("El grafo no puede ser nulo.");
        }
        // Se verifica que la fuente proporcionada sea válida
        if (idFuente == null || idFuente.trim().isEmpty()) {
            throw new IllegalArgumentException("La neurona fuente no puede estar vacía.");
        }
        // Se confirma que el punto de partida exista realmente en el grafo
        if (!grafo.existeNeurona(idFuente   )) {
            throw new IllegalArgumentException("La neurona fuente no existe en el grafo.");
        }
    }
    
    // Algoritmos analíticos y lógicos
    
    /**
     * Ejecutar internamente la exploración del grafo mediante el algoritmo de Amplitud (BFS).
     * @param grafo Estructura de red.
     * @param idFuente Nodo inicial.
     * @return TablaHash de las neuronas que fueron visitadas.
     */
       private TablaHash<String, Boolean> recorridoBFS(GrafoDirigidoListaAdyacencia grafo, String idFuente) {

        TablaHash<String, Boolean> visitados = new TablaHash<>();
        Cola<String> cola = new Cola<>();

        visitados.insertar(idFuente, true);
        cola.encolar(idFuente);

        while (!cola.estaVacia()) {

            String actual = cola.desencolar();

            ListaEnlazada<Sinapsis> adyacentes = grafo.obtenerAdyacentes(actual);

            if (adyacentes != null && !adyacentes.estaVacia()) {

                for (int i = 0; i < adyacentes.tamano(); i++) {

                    Sinapsis sinapsis = adyacentes.obtener(i);
                    //Verificamos si la sinapsis es apta para ser recorrerla
                    if (sinapsis == null || !sinapsis.isActiva() || sinapsis.getDestino() == null) {
                        continue;
                    }

                    String idDestino = sinapsis.getDestino().getID();

                    if (!visitados.contieneClave(idDestino)) {
                        visitados.insertar(idDestino, true);
                        cola.encolar(idDestino);
                    }
                    
                }
            }
        }

        return visitados;
    }
     
    /**
     * Ejecutar internamente la exploración del grafo mediante el algoritmo de Profundidad (DFS).
     * @param grafo Estructura de red.
     * @param idFuente Nodo inicial.
     * @return TablaHash de las neuronas que fueron visitadas.
     */
    private TablaHash<String, Boolean> recorridoDFS(GrafoDirigidoListaAdyacencia grafo, String idFuente) {

        TablaHash<String, Boolean> visitados = new TablaHash<>();
        Pila<String> pila = new Pila<>();

        pila.apilar(idFuente);

        while (!pila.estaVacia()) {

            String actual = pila.desapilar();

            if (!visitados.contieneClave(actual)) {

                visitados.insertar(actual, true);

                ListaEnlazada<Sinapsis> adyacentes = grafo.obtenerAdyacentes(actual);

                if (adyacentes != null && !adyacentes.estaVacia()) {

                    for (int i = 0; i < adyacentes.tamano(); i++) {

                        Sinapsis sinapsis = adyacentes.obtener(i);
                        // Verificamos si la sinapsis es apta para recorrerla
                        if (sinapsis == null || !sinapsis.isActiva() || sinapsis.getDestino() == null) {
                            continue;
                        }

                        String idDestino = sinapsis.getDestino().getID();

                        if (!visitados.contieneClave(idDestino)) {
                            pila.apilar(idDestino);
                        }
                        
                    }
                }
            }
        }

        return visitados;
    }
        
        /**
     * Agrupar y empaquetar los hallazgos algorítmicos en el objeto ResultadoConectividad.
     * @param grafo Red explorada.
     * @param idFuente Neurona origen.
     * @param algoritmo Etiqueta del algoritmo ejecutado.
     * @param visitados Registro de nodos explorados.
     * @return Objeto formateado con toda la métrica.
     */
        private ResultadoConectividad construirResultado(
            GrafoDirigidoListaAdyacencia grafo,
            String idFuente,
            String algoritmo,
            TablaHash<String, Boolean> visitados) {

        ListaEnlazada<String> alcanzables = new ListaEnlazada<>();
        ListaEnlazada<String> inalcanzables = new ListaEnlazada<>();

        ListaEnlazada<String> ids = grafo.getListaIds();

        for (int i = 0; i < ids.tamano(); i++) {

            String idActual = ids.obtener(i);

            if (visitados.contieneClave(idActual)) {
                alcanzables.insertarFinal(idActual);
            } else {
                inalcanzables.insertarFinal(idActual);
            }
        }

        boolean fuertementeConexa = esFuertementeConexa(grafo);

        ResultadoConectividad resultado =  new ResultadoConectividad(idFuente,algoritmo,alcanzables,inalcanzables,fuertementeConexa);
        return resultado;
    }

    /**
     * Verificar de manera holística si todos los nodos de la red son capaces de alcanzarse entre ellos.
     * @param grafo Estructura gráfica de la red actual.
     * @return true si es fuertemente conexa, de lo contrario false.
     */ 
    public boolean esFuertementeConexa(GrafoDirigidoListaAdyacencia grafo) {

        if (grafo == null || grafo.getContadorNeuronas() == 0) {
            return false;
        }

        ListaEnlazada<String> ids = grafo.getListaIds();

        for (int i = 0; i < ids.tamano(); i++) {

            String idFuente = ids.obtener(i);

            TablaHash<String, Boolean> visitados = recorridoBFS(grafo, idFuente);

            for (int j = 0; j < ids.tamano(); j++) {

                String idDestino = ids.obtener(j);

                if (!visitados.contieneClave(idDestino)) {
                    return false;
                }
            }
        }

        return true;
    }
    
    /**
     * Iniciar el análisis por Búsqueda en Amplitud (BFS) público.
     * @param grafo Estructura de red a iterar.
     * @param idFuente Nodo central inicial.
     * @return Paquete de resultados extraído del motor algorítmico.
     */
    public ResultadoConectividad analizarBFS(GrafoDirigidoListaAdyacencia grafo, String idFuente) {
        validarEntrada(grafo, idFuente);

        TablaHash<String, Boolean> visitados = recorridoBFS(grafo, idFuente);

        return construirResultado(grafo, idFuente, "BFS", visitados);
    }

    /**
     * Iniciar el análisis por Búsqueda en Profundidad (DFS) público.
     * @param grafo Estructura de red a iterar.
     * @param idFuente Nodo central inicial.
     * @return Paquete de resultados extraído del motor algorítmico.
     */
    public ResultadoConectividad analizarDFS(GrafoDirigidoListaAdyacencia grafo, String idFuente) {
        validarEntrada(grafo, idFuente);

        TablaHash<String, Boolean> visitados = recorridoDFS(grafo, idFuente);

        return construirResultado(grafo, idFuente, "DFS", visitados);
    }

}
