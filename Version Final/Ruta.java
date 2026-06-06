/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

/**
 * Clase encargada de representar el resultado producido por el algoritmo de Dijkstra.
 * Guarda la secuencia de neuronas recorridas, el costo total y un mensaje
 * comprensible para la interfaz.
 * @author Daniel Vera
 */
public class Ruta {

    // Declaración de variables
    private final boolean existeRuta;
    private final ListaEnlazada<String> secuenciaNeuronas;
    private final double costoTotal;
    private final String mensaje;

    // Constructor de la clase
    /**
     * Inicializar una instancia de la clase Ruta de forma completa.
     * @param existeRuta Indica si se encontró conexión entre el origen y destino.
     * @param secuenciaNeuronas Secuencia ordenada de las neuronas a recorrer.
     * @param costoTotal Costo calculado (tiempo de propagación).
     * @param mensaje Mensaje o conclusión de la operación.
     */
    public Ruta(boolean existeRuta, ListaEnlazada<String> secuenciaNeuronas, double costoTotal, String mensaje) {
        this.existeRuta = existeRuta;
        this.secuenciaNeuronas = secuenciaNeuronas;
        this.costoTotal = costoTotal;
        this.mensaje = mensaje;
    }

    /**
     * Generar un objeto Ruta indicando un fallo o ausencia de conexión.
     * @param mensaje Motivo del fallo.
     * @return Objeto Ruta con banderas desactivadas y valores infinitos.
     */
    public static Ruta sinRuta(String mensaje) {
        // Retorna un objeto simulado indicando costo infinito al no existir camino
        return new Ruta(false, new ListaEnlazada<>(), Double.POSITIVE_INFINITY, mensaje);
    }

    /**
     * Generar un objeto Ruta indicando una exploración exitosa.
     * @param secuenciaNeuronas Secuencia real de recorrido óptimo.
     * @param costoTotal Cálculo del tiempo total.
     * @return Objeto Ruta funcional y activo.
     */
    public static Ruta conRuta(ListaEnlazada<String> secuenciaNeuronas, double costoTotal) {
        // Retorna el camino exitoso consolidado
        return new Ruta(true, secuenciaNeuronas, costoTotal, "Ruta encontrada correctamente.");
    }

    // Getters y Setters
    public boolean existeRuta() {
        return existeRuta;
    }

    public ListaEnlazada<String> getSecuenciaNeuronas() {
        return secuenciaNeuronas;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public String getMensaje() {
        return mensaje;
    }

    // Uso de @override para permitir representar en lenguaje humano el análisis de ruta final.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Se anexa el mensaje descriptivo inicial
        sb.append(mensaje).append("\n");

        // Si no existe la ruta, se corta la construcción del texto y se retorna el fallo
        if (!existeRuta) {
            return sb.toString();
        }

        sb.append("Ruta: ");

        // Se itera sobre la lista de neuronas para concatenarlas con una flecha visual
        for (int i = 0; i < secuenciaNeuronas.tamano(); i++) {
            sb.append(secuenciaNeuronas.obtener(i));

            if (i < secuenciaNeuronas.tamano() - 1) {
                sb.append(" -> ");
            }
        }

        // Se añade el cálculo matemático final al string
        sb.append("\nCosto total: ").append(costoTotal);

        return sb.toString();
    }
}
