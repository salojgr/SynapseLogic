/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;
import java.io.File;
/**
 * 
 * @author Salomón González
 * 
 * Controlador principal de la aplicación SynapseLogic.
 *
 * Esta clase coordina la comunicación entre la interfaz gráfica y la lógica
 * del sistema. Mantiene en memoria el grafo actual, el diccionario de
 * neurotransmisores y el estado de la sesión.
 */
public class SynapseLogicController {

    private GrafoDirigidoListaAdyacencia grafo;
    private TablaHash<String, Neurotransmisor> diccionario;
    private final EstadoRed estado;
    private String ultimoReporteCargaRed = "";
    private String ultimoReporteCargaNeurotransmisores = "";
    
    /**
     * Crea un controlador sin red ni diccionario cargados.
     */
    public SynapseLogicController() {
        this.grafo = null;
        this.diccionario = null;
        this.estado = new EstadoRed();
    }

    /**
     * Carga una red sináptica desde un archivo CSV.
     *
     * @param archivo archivo CSV seleccionado por el usuario.
     * @return mensaje del resultado de la carga.
     * @throws IllegalArgumentException si el archivo es nulo, no existe o no es válido.
     */
    public String cargarRed(File archivo) {
        validarArchivo(archivo, "red sináptica");

        CargadorRedCSV cargador = new CargadorRedCSV();
        GrafoDirigidoListaAdyacencia grafoCargado = cargador.cargar(archivo.getAbsolutePath());
        ultimoReporteCargaRed = cargador.getReporteCarga();

        if (grafoCargado == null) {
            this.estado.setRedCargada(false);
            throw new IllegalStateException("No se pudo cargar la red sináptica.");
        }

        this.grafo = grafoCargado;
        this.estado.setRedCargada(true);
        this.estado.setCambiosSinGuardar(false);

        return """
               Red sináptica cargada correctamente.
               Archivo: """ + archivo.getName() + "\n"
                + "Neuronas: " + grafo.getContadorNeuronas() + "\n"
                + "Sinapsis: " + grafo.getContadorSinapsis();
    }
    /**
     * Obtiene el último reporte generado durante la carga de la Red
     * @return String del reporte
     */
    public String getUltimoReporteCargaRed() {
        return ultimoReporteCargaRed;
    }
    /**
     * Obtiene el último reporte durante la carga del diccionario
     * @return String del reporte del diccionario
     */
    public String getUltimoReporteCargaNeurotransmisores() {
        return ultimoReporteCargaNeurotransmisores;
    }
    /**
     * Carga un diccionario de neurotransmisores desde un archivo CSV.
     *
     * @param archivo archivo CSV seleccionado por el usuario.
     * @return mensaje del resultado de la carga.
     * @throws IllegalArgumentException si el archivo es nulo, no existe o no es válido.
     */
    public String cargarDiccionario(File archivo) {
        validarArchivo(archivo, "diccionario de neurotransmisores");

        CargadorNeurotransmisoresCSV cargador = new CargadorNeurotransmisoresCSV();
        TablaHash<String, Neurotransmisor> diccionarioCargado = cargador.cargar(archivo.getAbsolutePath());
        ultimoReporteCargaNeurotransmisores = cargador.getReporteCarga();
        
        if (diccionarioCargado == null || diccionarioCargado.estaVacia()) {
            this.estado.setDiccionarioCargado(false);
            throw new IllegalStateException("No se pudo cargar el diccionario de neurotransmisores.");
        }

        this.diccionario = diccionarioCargado;
        this.estado.setDiccionarioCargado(true);

        return """
               Diccionario de neurotransmisores cargado correctamente.
               Archivo: """ + archivo.getName() + "\n"
                + "Neurotransmisores: " + diccionario.tamano();
    }

    /**
     * Ejecuta un análisis de conectividad usando BFS desde una neurona fuente.
     *
     * @param idFuente ID de la neurona fuente.
     * @return resultado del análisis de conectividad.
     */
    public ResultadoConectividad analizarBFS(String idFuente) {
        validarRedCargada();

        String fuente = normalizarId(idFuente, "fuente");

        AnalizadorConectividad analizador = new AnalizadorConectividad();
        return analizador.analizarBFS(grafo, fuente);
    }

    /**
     * Ejecuta un análisis de conectividad usando DFS desde una neurona fuente.
     *
     * @param idFuente ID de la neurona fuente.
     * @return resultado del análisis de conectividad.
     */
    public ResultadoConectividad analizarDFS(String idFuente) {
        validarRedCargada();

        String fuente = normalizarId(idFuente, "fuente");

        AnalizadorConectividad analizador = new AnalizadorConectividad();
        return analizador.analizarDFS(grafo, fuente);
    }

    /**
     * Calcula la ruta de menor tiempo de transmisión entre dos neuronas usando
     * el algoritmo de Dijkstra.
     *
     * @param origen ID de la neurona origen.
     * @param destino ID de la neurona destino.
     * @return objeto Ruta con la secuencia, costo total y mensaje asociado.
     */
    public Ruta calcularRuta(String origen, String destino) {
        validarRedCargada();
        validarDiccionarioCargado();

        String idOrigen = normalizarId(origen, "origen");
        String idDestino = normalizarId(destino, "destino");

        CalculadorRutaDijkstra calculador = new CalculadorRutaDijkstra();
        return calculador.calcularRuta(grafo, diccionario, idOrigen, idDestino);
    }

    /**
     * Aplica un ciclo de deterioro cognitivo sobre todas las sinapsis de la red.
     *
     * @return mensaje de la operación.
     */
    public String simularDeterioro() {
        validarRedCargada();

        SimuladorDeterioro simulador = new SimuladorDeterioro(grafo);
        simulador.ejecutarCicloFatiga();

        estado.setCambiosSinGuardar(true);

        return """
               Deterioro aplicado correctamente.
               Los coeficientes k de las sinapsis fueron actualizados.""";
    }

    /**
    * Simula un traumatismo cerebral desactivando una neurona específica en la red.
    * @param idNeurona El ID de la Neurona.
    * @return String que indica el resultado de la operación.
    */
    public String simularTraumatismo(String idNeurona) {

        if (idNeurona == null || idNeurona.trim().isEmpty()) {
            return "Error: El ID de la neurona no puede estar vacío.";
        }

        validarRedCargada();

        String id = normalizarId(idNeurona, "neurona");

        boolean eliminada = grafo.eliminarNeurona(id);

        if (!eliminada) {
            return "Error: La neurona '" + id + "' no existe en la red.";
        }

        estado.setCambiosSinGuardar(true);

        return """
               Traumatismo simulado correctamente.
               La neurona '""" + id + "' fue eliminada junto con sus sinapsis entrantes y salientes.\n"
                + "Neuronas restantes: " + grafo.getContadorNeuronas() + "\n"
                + "Sinapsis restantes: " + grafo.getContadorSinapsis();
    }

    

    /**
     * Agrega una neurona aislada al grafo actual.
     *
     * @param id ID de la nueva neurona.
     * @return mensaje del resultado de la operación.
     */
    public String agregarNeurona(String id) {
        validarRedCargada();

        String idNeurona = normalizarId(id, "neurona");

        if (grafo.existeNeurona(idNeurona)) {
            return "La neurona '" + idNeurona + "' ya existe.";
        }

        grafo.agregarNeurona(new Neurona(idNeurona));
        estado.setCambiosSinGuardar(true);

        return "Neurona '" + idNeurona + "' agregada correctamente.\n"
                + "Neuronas totales: " + grafo.getContadorNeuronas();
    }

    /**
     * Agrega una sinapsis dirigida al grafo actual.
     *
     * @param origen ID de la neurona origen.
     * @param destino ID de la neurona destino.
     * @param distancia distancia sináptica.
     * @param idNeurotransmisor ID del neurotransmisor asociado.
     * @param k coeficiente de eficiencia sináptica.
     * @return mensaje descriptivo de la operación.
     */
    public String agregarSinapsis(String origen, String destino, double distancia, String idNeurotransmisor, double k) {
        validarRedCargada();
        validarDiccionarioCargado();

        String idOrigen = normalizarId(origen, "origen");
        String idDestino = normalizarId(destino, "destino");
        String nt = normalizarId(idNeurotransmisor, "neurotransmisor");

        if (distancia <= 0) {
            throw new IllegalArgumentException("La distancia debe ser mayor que 0.");
        }

        if (k <= 0 || k > 1) {
            throw new IllegalArgumentException("El coeficiente k debe estar entre > 0 y 1.");
        }

        if (!diccionario.contieneClave(nt)) {
            throw new IllegalArgumentException("El neurotransmisor '" + nt + "' no existe en el diccionario cargado.");
        }

        if (!grafo.existeNeurona(idOrigen)) {
            throw new IllegalArgumentException("La neurona origen '" + idOrigen + "' no existe. Agréguela primero.");
        }

        if (!grafo.existeNeurona(idDestino)) {
            throw new IllegalArgumentException("La neurona destino '" + idDestino + "' no existe. Agréguela primero.");
        }

        if (grafo.existeSinapsis(idOrigen, idDestino)) {
            return "La sinapsis '" + idOrigen + "' -> '" + idDestino + "' ya existe.";
        }

        boolean agregada = grafo.agregarSinapsis(idOrigen, idDestino, distancia, nt, k);

        if (!agregada || !grafo.existeSinapsis(idOrigen, idDestino)) {
            throw new IllegalStateException("No se pudo agregar la sinapsis.");
        }

        estado.setCambiosSinGuardar(true);

        return "Sinapsis agregada correctamente:\n"
                + idOrigen + " -> " + idDestino + "\n"
                + "Neurotransmisor: " + nt + "\n"
                + "Distancia: " + distancia + "\n"
                + "k: " + k;
    }

    /**
    * Elimina una sinapsis dirigida existente en la red actual.
    *
    * @param origen ID de la neurona origen.
    * @param destino ID de la neurona destino.
    * @return mensaje de resultado de la operación.
    */
    public String eliminarSinapsis(String origen, String destino) {
       validarRedCargada();

       String idOrigen = normalizarId(origen, "origen");
       String idDestino = normalizarId(destino, "destino");

       if (!grafo.existeNeurona(idOrigen)) {
           throw new IllegalArgumentException("La neurona origen '" + idOrigen + "' no existe.");
       }

       if (!grafo.existeNeurona(idDestino)) {
           throw new IllegalArgumentException("La neurona destino '" + idDestino + "' no existe.");
       }

       if (!grafo.existeSinapsis(idOrigen, idDestino)) {
           return "No existe una sinapsis dirigida desde '" + idOrigen + "' hasta '" + idDestino + "'.";
       }

       boolean eliminada = grafo.eliminarSinapsis(idOrigen, idDestino);

       if (!eliminada) {
           throw new IllegalStateException("No se pudo eliminar la sinapsis seleccionada.");
       }

       estado.setCambiosSinGuardar(true);

       return "Sinapsis eliminada correctamente:\n"
               + idOrigen + " -> " + idDestino + "\n"
               + "Sinapsis restantes: " + grafo.getContadorSinapsis();
    }
    /**
     * Guarda la red sináptica actual en un archivo CSV.
     *
     * @param archivo archivo destino seleccionado desde la interfaz.
     * @return mensaje de resultado de la operación.
     */
    public String guardarRed(File archivo) {
        validarRedCargada();

        if (archivo == null) {
            throw new IllegalArgumentException("No se seleccionó ningún archivo para guardar.");
        }

        File destino = asegurarExtensionCSV(archivo);
        File carpeta = destino.getParentFile();

        if (carpeta != null && !carpeta.exists()) {
            throw new IllegalArgumentException("La carpeta de destino no existe.");
        }

        GuardadorCSV guardador = new GuardadorCSV();
        String mensaje = guardador.guardarRed(destino.getAbsolutePath(), grafo);

        if (mensaje != null && mensaje.startsWith("Éxito")) {
            estado.setCambiosSinGuardar(false);
        }

        return mensaje + "\nArchivo: " + destino.getAbsolutePath();
    }
    
    /**
     * Indica si existen cambios pendientes por guardar.
     *
     * @return true si hay cambios sin guardar o false en caso contrario.
     */
    
    public boolean hayCambiosSinGuardar() {
        return estado.CambiosSinGuardar();
    }
    /**
     * Asegura que el archivo recibido tenga extension CSV
     * @param archivo archivo seleccionado por usuario
     * @return archivo original si termina en .csv, o uno nuevo con la extension agregada
     */
    private File asegurarExtensionCSV(File archivo) {
    String ruta = archivo.getAbsolutePath();

        if (!ruta.toLowerCase().endsWith(".csv")) {
            return new File(ruta + ".csv");
        }

        return archivo;
    }
    
    /**
     * Devuelve un resumen de la red cargada.
     *
     * @return String de resumen de neuronas, sinapsis y estado.
     */
    public String resumenRed() {
        validarRedCargada();

        return """
               Resumen de la red actual:
               Neuronas: """ + grafo.getContadorNeuronas() + "\n"
                + "Sinapsis: " + grafo.getContadorSinapsis() + "\n"
                + "Diccionario cargado: " + estado.estaDiccionarioCargado() + "\n"
                + "Cambios sin guardar: " + estado.CambiosSinGuardar();
    }

    /**
     * Marca los cambios actuales como guardados.
     * 
     */
    public void marcarComoGuardado() {
        estado.setCambiosSinGuardar(false);
    }

    /**
     * Indica si debería pedirse confirmación antes de reemplazar la red actual.
     *
     * @return true si hay red cargada y cambios sin guardar.
     */
    public boolean requiereConfirmacionParaNuevaRed() {
        return estado.estaRedCargada() && estado.CambiosSinGuardar();
    }

    /**
     * Devuelve el estado de la sesión.
     *
     * @return estado actual.
     */
    public EstadoRed getEstado() {
        return estado;
    }

    /**
     * Devuelve el grafo actualmente cargado.
     *
     * @return grafo actual.
     */
    public GrafoDirigidoListaAdyacencia getGrafo() {
 
        return grafo;
    }
    /**
     * Devuelve el diccionario de neurotransmisores actualmente cargado.
     *
     * @return tabla hash de neurotransmisores. 
     */
    public TablaHash<String, Neurotransmisor> getDiccionario() {
        return diccionario; 
    }

    /**
     * Valida que exista una red cargada antes de ejecutar operaciones sobre el grafo.
     */
    private void validarRedCargada() {
        if (grafo == null || !estado.estaRedCargada()) {
            throw new IllegalStateException("Debe cargar una red sináptica primero.");
        }
    }

    /**
     * Valida que exista un diccionario cargado antes de calcular rutas ponderadas.
     */
    private void validarDiccionarioCargado() {
        if (diccionario == null || diccionario.estaVacia() || !estado.estaDiccionarioCargado()) {
            throw new IllegalStateException("Debe cargar el diccionario de neurotransmisores primero.");
        }
    }

    /**
     * Valida que un archivo sea utilizable.
     *
     * @param archivo archivo recibido.
     * @param tipoArchivo descripción del tipo de archivo.
     */
    private void validarArchivo(File archivo, String tipoArchivo) {
        if (archivo == null) {
            throw new IllegalArgumentException("No se seleccionó ningún archivo de " + tipoArchivo + ".");
        }

        if (!archivo.exists()) {
            throw new IllegalArgumentException("El archivo de " + tipoArchivo + " no existe.");
        }

        if (!archivo.isFile()) {
            throw new IllegalArgumentException("La ruta seleccionada no corresponde a un archivo válido.");
        }
    }

    /**
     * Limpia y valida un ID recibido desde la interfaz o desde otro servicio.
     *
     * @param id valor recibido.
     * @param nombreCampo nombre del campo para mensajes de error.
     * @return ID limpio.
     */
    private String normalizarId(String id, String nombreCampo) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de " + nombreCampo + " no puede ser nulo.");
        }

        String limpio = id.trim();

        if (limpio.isEmpty()) {
            throw new IllegalArgumentException("El ID de " + nombreCampo + " no puede estar vacío.");
        }

        return limpio;
    }
    
}





