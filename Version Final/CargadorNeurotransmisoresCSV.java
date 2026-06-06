/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase encargada de leer y procesar el archivo CSV para construir el diccionario de neurotransmisores.
 * No imprime mensajes por consola. Los errores de carga se almacenan en un
 * reporte para que la interfaz gráfica pueda mostrarlos.
 *
 * @author Daniel Vera
 */
public class CargadorNeurotransmisoresCSV {

    // Declaración de variables
    private final StringBuilder reporteCarga = new StringBuilder();
    private int lineasValidas;
    private int lineasConError;

    /**
     * Cargar el diccionario de neurotransmisores desde un archivo CSV.
     * Formato esperado: id,nombre,efecto,velocidad,descripcion
     *
     * @param rutaArchivo ruta absoluta o relativa del archivo CSV.
     * @return tabla hash con los neurotransmisores válidos cargados.
     */
    public TablaHash<String, Neurotransmisor> cargar(String rutaArchivo) {

        limpiarReporte();

        // Validación de ruta de archivo
        if (rutaArchivo == null || rutaArchivo.trim().isEmpty()) {
            String mensaje = "No se indicó una ruta válida para el archivo de neurotransmisores.";
            registrarErrorGeneral(mensaje);
            throw new IllegalArgumentException(mensaje);
        }

        TablaHash<String, Neurotransmisor> diccionario = new TablaHash<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {

            String linea;
            int numeroLinea = 1;

            // Lee y descarta el encabezado.
            String encabezado = br.readLine();

            if (encabezado == null) {
                registrarErrorGeneral("El archivo está vacío. No se cargó ningún neurotransmisor.");
                agregarResumen();
                return diccionario;
            }

            while ((linea = br.readLine()) != null) {

                numeroLinea++;

                if (linea.trim().isEmpty()) {
                    continue;
                }

                try {
                    /*
                     * Se usa split(",", 5) para que la descripción pueda contener comas.
                     * Ejemplo:
                     * GLU,Glutamato,Excitatorio,2.5,"Texto con, comas internas"
                     */
                    String[] partes = linea.split(",", 5);

                    if (partes.length < 5) {
                        registrarError(
                                numeroLinea,
                                linea,
                                "La línea está incompleta. Debe tener: id, nombre, efecto, velocidad y descripción."
                        );
                        continue;
                    }

                    String id = partes[0].trim();
                    String nombre = partes[1].trim();
                    String efecto = partes[2].trim();
                    String velocidadTexto = partes[3].trim();
                    String descripcion = partes[4].trim();

                    if (id.isEmpty()) {
                        registrarError(
                                numeroLinea,
                                linea,
                                "El ID del neurotransmisor está vacío."
                        );
                        continue;
                    }

                    if (nombre.isEmpty()) {
                        registrarError(
                                numeroLinea,
                                linea,
                                "El nombre del neurotransmisor está vacío."
                        );
                        continue;
                    }

                    if (efecto.isEmpty()) {
                        registrarError(
                                numeroLinea,
                                linea,
                                "El efecto del neurotransmisor está vacío."
                        );
                        continue;
                    }

                    if (descripcion.isEmpty()) {
                        registrarError(
                                numeroLinea,
                                linea,
                                "La descripción del neurotransmisor está vacía."
                        );
                        continue;
                    }

                    double velocidad = Double.parseDouble(velocidadTexto);

                    if (velocidad <= 0) {
                        registrarError(
                                numeroLinea,
                                linea,
                                "La velocidad " + velocidad + " es inválida. Debe ser mayor a 0."
                        );
                        continue;
                    }

                    Neurotransmisor neuro = new Neurotransmisor(
                            id,
                            nombre,
                            velocidad,
                            efecto,
                            descripcion
                    );

                    diccionario.insertar(id, neuro);
                    lineasValidas++;

                } catch (NumberFormatException e) {
                    registrarError(
                            numeroLinea,
                            linea,
                            "Formato numérico incorrecto. La velocidad debe ser un número válido."
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
            String mensaje = "No se pudo leer el archivo de neurotransmisores. Detalles: " + e.getMessage();
            registrarErrorGeneral(mensaje);
            throw new IllegalArgumentException(mensaje);
        }

        return diccionario;
    }

    // Getters y Setters
    
    /**
     * Obtener el reporte generado durante la última carga.
     * @return reporte de carga como String.
     */
    public String getReporteCarga() {
        return reporteCarga.toString();
    }

    /**
     * Obtener cuántos neurotransmisores válidos se cargaron.
     * @return cantidad de líneas válidas.
     */
    public int getLineasValidas() {
        return lineasValidas;
    }

    /**
     * Indicar si hubo errores durante la última carga.
     * @return true si hubo errores; false si no hubo errores.
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
        lineasConError = 0;
    }

    /**
     * Anexar error de parseo en el bloque correspondiente.
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
     * Anexar falla genérica en el reporte general.
     */
    private void registrarErrorGeneral(String mensaje) {
        lineasConError++;

        reporteCarga.append("Error general: ")
                .append(mensaje)
                .append("\n\n");
    }

    /**
     * Añadir las estadísticas recolectadas.
     */
    private void agregarResumen() {
        reporteCarga.append("Resumen de carga de neurotransmisores:\n")
                .append("- Neurotransmisores cargados correctamente: ")
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