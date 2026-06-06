/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase encargada de leer y procesar el archivo CSV para construir la red sináptica.
 * Almacena los errores de carga en un reporte interno para la interfaz gráfica.
 * @author Daniel Vera
 */
public class CargadorRedCSV {

    // Declaración de variables
    private final StringBuilder reporteCarga = new StringBuilder();
    private int lineasValidas;
    private int neuronasValidas;
    private int lineasConError;

    /**
     * Cargar una red sináptica en memoria desde un archivo CSV físico.
     *
     * @param rutaArchivo ruta absoluta o relativa del archivo CSV.
     * @return grafo dirigido construido con las sinapsis válidas del archivo.
     */
    public GrafoDirigidoListaAdyacencia cargar(String rutaArchivo) {

    limpiarReporte();

    if (rutaArchivo == null || rutaArchivo.trim().isEmpty()) {
        String mensaje = "No se indicó una ruta válida para el archivo de red.";
        registrarErrorGeneral(mensaje);
        throw new IllegalArgumentException(mensaje);
    }

    GrafoDirigidoListaAdyacencia grafo = new GrafoDirigidoListaAdyacencia();

    try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {

        String linea;
        int numeroLinea = 1;

        // Lee y descarta el encabezado.
        String encabezado = br.readLine();

        if (encabezado == null) {
            registrarErrorGeneral("El archivo está vacío. No se cargó ninguna red.");
            agregarResumen();
            throw new IllegalArgumentException(
                    "El archivo está vacío. No se cargó ninguna red."
            );
        }

        while ((linea = br.readLine()) != null) {

            numeroLinea++;

            if (linea.trim().isEmpty()) {
                continue;
            }

            try {
                // split(",", -1) conserva campos vacíos.
                String[] partes = linea.split(",", -1);

                if (partes.length != 5) {
                    registrarError(
                            numeroLinea,
                            linea,
                            "La línea debe tener exactamente 5 datos: origen, destino, distancia, neurotransmisor y k."
                    );
                    continue;
                }

                String origen = partes[0].trim();
                String destino = partes[1].trim();
                String distanciaTexto = partes[2].trim();
                String neurotransmisor = partes[3].trim();
                String kTexto = partes[4].trim();

                if (origen.isEmpty()) {
                    registrarError(
                            numeroLinea,
                            linea,
                            "El ID de la neurona origen está vacío."
                    );
                    continue;
                }

                // Identificación del patrón de neurona sin sinapsis activas
                boolean filaSoloNeurona = destino.isEmpty()
                        && distanciaTexto.isEmpty()
                        && neurotransmisor.isEmpty()
                        && kTexto.isEmpty();

                // Integración exclusiva de neurona individual
                if (filaSoloNeurona) {
                    if (!grafo.existeNeurona(origen)) {
                        grafo.agregarNeurona(new Neurona(origen));
                        neuronasValidas++;
                    }
                    continue;
                }

                if (destino.isEmpty()) {
                    registrarError(
                            numeroLinea,
                            linea,
                            "El ID de la neurona destino está vacío."
                    );
                    continue;
                }

                if (neurotransmisor.isEmpty()) {
                    registrarError(
                            numeroLinea,
                            linea,
                            "El ID del neurotransmisor está vacío."
                    );
                    continue;
                }

                // Transformación de los textos a valores operacionales
                double distancia = Double.parseDouble(distanciaTexto);
                double k = Double.parseDouble(kTexto);

                if (distancia <= 0) {
                    registrarError(
                            numeroLinea,
                            linea,
                            "La distancia " + distancia + " es inválida. Debe ser mayor a 0."
                    );
                    continue;
                }

                if (k <= 0 || k > 1.0) {
                    registrarError(
                            numeroLinea,
                            linea,
                            "El coeficiente k " + k + " está fuera de rango. Debe estar entre > 0 y 1.0."
                    );
                    continue;
                }

                // Verificación que prevé la duplicidad en la creación de aristas (Tolerancia a fallos)
                if (grafo.existeSinapsis(origen, destino)) {
                    registrarError(
                            numeroLinea,
                            linea,
                            "La conexión '" + origen + "' -> '" + destino + "' ya existe. Se ignoró el duplicado."
                    );
                    continue;
                }

                // Inserción de la nueva sinapsis al modelo matemático general
                grafo.agregarSinapsis(origen, destino, distancia, neurotransmisor, k);
                lineasValidas++;

            } catch (NumberFormatException e) {
                registrarError(
                        numeroLinea,
                        linea,
                        "Formato numérico incorrecto. La distancia y el coeficiente k deben ser números válidos."
                );

            } catch (Exception e) {
                registrarError(
                        numeroLinea,
                        linea,
                        "Error inesperado: " + e.getMessage()
                );
            }
        }

        agregarResumen();

    } catch (IOException e) {
        String mensaje = "No se pudo leer el archivo de red. Detalles: " + e.getMessage();
        registrarErrorGeneral(mensaje);
        throw new IllegalArgumentException(mensaje);
    }

    if (lineasValidas == 0 && neuronasValidas == 0) {
        throw new IllegalArgumentException(
                "El archivo fue leído, pero no se cargó ninguna neurona ni sinapsis válida. Revise el formato del CSV."
        );
    }

    return grafo;
}

    // Getters y Setters

    /**
     * Obtener el reporte generado durante la última carga.
     * @return reporte de carga.
     */
    public String getReporteCarga() {
        return reporteCarga.toString();
    }

    /**
     * Indicar si la última carga tuvo errores.
     * @return true si hubo errores; false en caso contrario.
     */
    public boolean tieneErrores() {
        return lineasConError > 0;
    }

    // Métodos para el procesamiento de errores y métricas

    /**
     * Reiniciar contadores del reporte.
     */
    private void limpiarReporte() {
        reporteCarga.setLength(0);
        lineasValidas = 0;
        neuronasValidas = 0;
        lineasConError = 0;
    }

    /**
     * Anexar error de parseo en la línea correspondiente.
     */
    private void registrarError(int numeroLinea, String linea, String mensaje) {
        lineasConError++;

        reporteCarga.append("Error en línea ")
                .append(numeroLinea)
                .append(": ")
                .append(mensaje)
                .append("\n")
                .append("Contenido: ")
                .append(linea)
                .append("\n\n");
    }

    /**
     * Anexar falla de IO o global al reporte.
     */
    private void registrarErrorGeneral(String mensaje) {
        lineasConError++;

        reporteCarga.append("Error general: ")
                .append(mensaje)
                .append("\n\n");
    }

    /**
     * Añadir estadísticas recolectadas durante la inserción de las ramas.
     */
    private void agregarResumen() {
        reporteCarga.append("Resumen de carga de red:\n")
        .append("- Neuronas cargadas explícitamente: ")
        .append(neuronasValidas)
        .append("\n")
        .append("- Sinapsis cargadas correctamente: ")
        .append(lineasValidas)
        .append("\n")
        .append("- Líneas con error: ")
        .append(lineasConError)
        .append("\n");

        if (lineasConError == 0) {
            reporteCarga.append("- Estado: carga completada sin errores.\n");
        } else {
            reporteCarga.append("- Estado: carga completada con advertencias. Las líneas inválidas fueron ignoradas.\n");
        }
    }
}