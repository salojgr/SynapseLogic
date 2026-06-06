/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package synapselogic;

/**
 * @author Salomon Gonzalez
 * Calcula la ruta de menor tiempo de transmisión entre dos neuronas
 * usando el algoritmo de Dijkstra sobre un grafo dirigido.
 */
public class CalculadorRutaDijkstra {
        /**
     * Valida los datos necesarios para ejecutar el algoritmo
     * @param grafo red sinaptica
     * @param diccionario neurotransmisores
     * @param origen id de la neurona origen
     * @param destino id de la neurona destino
     */
    private void validarEntrada(
            GrafoDirigidoListaAdyacencia grafo,
            TablaHash<String, Neurotransmisor> diccionario,
            String origen,
            String destino) {

        if (grafo == null) {
            throw new IllegalArgumentException("El grafo no puede ser null.");
        }

        if (diccionario == null || diccionario.estaVacia()) {
            throw new IllegalArgumentException("Debe cargar el diccionario de neurotransmisores.");
        }

        if (origen == null || origen.trim().isEmpty()) {
            throw new IllegalArgumentException("El origen no puede estar vacío.");
        }

        if (destino == null || destino.trim().isEmpty()) {
            throw new IllegalArgumentException("El destino no puede estar vacío.");
        }

        if (!grafo.existeNeurona(origen)) {
            throw new IllegalArgumentException("La neurona origen no existe.");
        }

        if (!grafo.existeNeurona(destino)) {
            throw new IllegalArgumentException("La neurona destino no existe.");
        }
    }
    /**
     * Inicializa las distancia con un infinito 
     * 
     * @param grafo red sinaptica
     * @param distancias tabla hash que almacena las distancias
     */
    private void inicializarDistancias(
            GrafoDirigidoListaAdyacencia grafo,
            TablaHash<String, Double> distancias) {

        ListaEnlazada<String> ids = grafo.getListaIds();

        for (int i = 0; i < ids.tamano(); i++) {
            distancias.insertar(ids.obtener(i), Double.POSITIVE_INFINITY);
        }
    }
    /**
     * Reconstruye la ruta encontrada desde el destino hasta el origen utilizando la tabla de anteriores
     * luego invirtiendo el orden para obtener la ruta.
     * @param origen id de la neurona origen
     * @param destino id de la neurona destino
     * @param anteriores tabla hash que guarda el nodo anterior de cada neurona
     * @return lista enlazada con las neuronas que forman parte de la ruta
     */
    private ListaEnlazada<String> reconstruirRuta(
            String origen,
            String destino,
            TablaHash<String, String> anteriores) {

        ListaEnlazada<String> rutaInvertida = new ListaEnlazada<>();

        String actual = destino;

        while (actual != null) {
            rutaInvertida.insertarFinal(actual);

            if (actual.equals(origen)) {
                break;
            }

            actual = anteriores.buscar(actual);
        }

        ListaEnlazada<String> ruta = new ListaEnlazada<>();

        for (int i = rutaInvertida.tamano() - 1; i >= 0; i--) {
            ruta.insertarFinal(rutaInvertida.obtener(i));
        }

        return ruta;
    }
    /**
     * Inicializa distancias en infinito, asigna 0 al origen y usa un objeto MinHeap para ir 
     * eligiendo primero la neurona con menor peso. Si encuentra una ruta, 
     * retorna una instancia del objeto Ruta, sino existe retorna Ruta marcada como inexistente.
     * 
     * @param grafo La red de neuronas
     * @param diccionario Informacion de los Neutransmisores
     * @param origen ID de la neurona de origen 
     * @param destino ID de la neurona destino
     * @return Objeto Ruta con la secuencia encontrada, el costo total
     */
    public Ruta calcularRuta(
            GrafoDirigidoListaAdyacencia grafo,
            TablaHash<String, Neurotransmisor> diccionario,
            String origen,
            String destino) {

        validarEntrada(grafo, diccionario, origen, destino);

        TablaHash<String, Double> distancias = new TablaHash<>();
        TablaHash<String, String> anteriores = new TablaHash<>();
        TablaHash<String, Boolean> visitados = new TablaHash<>();

        inicializarDistancias(grafo, distancias);

        distancias.insertar(origen, 0.0);

        MinHeap<String> heap = new MinHeap<>(grafo.getContadorNeuronas() + 5);
        heap.insertar(new ElementoHeap<>(origen, 0.0));

        while (!heap.esVacio()) {

            ElementoHeap<String> elementoActual = heap.extraerMinimo();
            String idActual = elementoActual.getElemento();

            if (visitados.contieneClave(idActual)) {
                continue;
            }

            visitados.insertar(idActual, true);

            if (idActual.equals(destino)) {
                break;
            }

            ListaEnlazada<Sinapsis> adyacentes = grafo.obtenerAdyacentes(idActual);

            if (adyacentes == null || adyacentes.estaVacia()) {
                continue;
            }

            for (int i = 0; i < adyacentes.tamano(); i++) {

                Sinapsis sinapsis = adyacentes.obtener(i);
                // Verificamos si la sinapsis es apta
                if (sinapsis == null || !sinapsis.isActiva() || sinapsis.getDestino() == null) {
                    continue;
                }

                String idVecino = sinapsis.getDestino().getID();

                if (visitados.contieneClave(idVecino)) {
                    continue;
                }

                Neurotransmisor neurotransmisor = diccionario.buscar(sinapsis.getNeurotransmisor());

                if (neurotransmisor == null) {
                    continue;
                }

                double peso = sinapsis.calcularW(neurotransmisor.getVelocidad());
                double nuevaDistancia = distancias.buscar(idActual) + peso;

                Double distanciaVecino = distancias.buscar(idVecino);

                if (distanciaVecino == null || nuevaDistancia < distanciaVecino) {
                    distancias.insertar(idVecino, nuevaDistancia);
                    anteriores.insertar(idVecino, idActual);
                    heap.insertar(new ElementoHeap<>(idVecino, nuevaDistancia));
                }
            }
        }

        Double costoFinal = distancias.buscar(destino);

        if (costoFinal == null || costoFinal == Double.POSITIVE_INFINITY) {
            return Ruta.sinRuta("No existe una ruta desde " + origen + " hasta " + destino + ".");
        }

        ListaEnlazada<String> secuencia = reconstruirRuta(origen, destino, anteriores);

        return Ruta.conRuta(secuencia, costoFinal);
    }

}