/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synapselogic;

/**
 * Clase encargada de centralizar las reglas de validación
 * tanto para las entradas manuales de la interfaz de usuario como para
 * el control de calidad de datos durante el parseo de archivos CSV.
 * Garantiza el cumplimiento de los criterios de Tolerancia a Fallos del proyecto.
 * @author Daniel Vera
 */
public class ValidadorDatos {

    /**
     * Valida si un identificador único (ID) es correcto.
     * Un ID no puede ser nulo, estar vacío ni contener únicamente espacios.
     * @param id El identificador a analizar.
     * @return true si el ID es válido, false en caso contrario.
     */
    public static boolean validarId(String id) {
        // Se asegura de que la cadena no sea nula
        return id != null && !id.trim().isEmpty();
    }

    /**
     * Valida que la distancia física de una sinapsis sea un valor estrictamente mayor a cero.
     * @param distancia Valor numérico de la distancia.
     * @return true si cumple con el dominio del problema, false en caso contrario.
     */
    public static boolean validarDistancia(double distancia) {
        return distancia > 0.0;
    }

    /**
     * Valida que la velocidad intrínseca de transmisión de un neurotransmisor 
     * sea estrictamente mayor a cero.
     * @param velocidad Valor numérico de la velocidad.
     * @return true si la velocidad es físicamente válida, false en caso contrario.
     */
    public static boolean validarVelocidad(double velocidad) {
        return velocidad > 0.0;
    }

    /**
     * Valida que el coeficiente de atenuación sináptica (k) se encuentre 
     * en el rango matemático permitido por el enunciado: (0, 1.0].
     * @param k Coeficiente de eficiencia de la arista.
     * @return true si está dentro del rango, false si está fuera de los límites.
     */
    public static boolean validarCoeficienteK(double k) {
        return k > 0.0 && k <= 1.0;
    }

    // Validaciones integrales para registros

    /**
     * Evalúa una línea completa de datos de red sináptica y compila un reporte 
     * comprensible en caso de detectar fallos.
     * @param origen ID de la neurona origen.
     * @param destino ID de la neurona destino.
     * @param distancia Distancia entre ambas neuronas.
     * @param neurotransmisor ID del neurotransmisor asociado.
     * @param k Coeficiente de atenuación inicial.
     * @return Un string con el mensaje de error específico, o null si los datos son impecables.
     */
    public static String verificarRegistroRed(String origen, String destino, double distancia, String neurotransmisor, double k) {
        // Validación del ID de origen
        if (!validarId(origen)) {
            return "El ID de la neurona origen está vacío o es inválido.";
        }
        // Validación del ID de destino
        if (!validarId(destino)) {
            return "El ID de la neurona destino está vacío o es inválido.";
        }
        // Verificación de integridad biológica: evita bucles sobre la misma neurona
        if (origen.trim().equalsIgnoreCase(destino.trim())) {
            return "No se permiten bucles autónomos; el origen '" + origen + "' no puede ser igual al destino.";
        }
        // Validación matemática de la distancia
        if (!validarDistancia(distancia)) {
            return "La distancia asignada (" + distancia + ") es inválida. Debe ser estrictamente mayor a 0.";
        }
        // Validación de existencia de compuesto químico
        if (!validarId(neurotransmisor)) {
            return "El identificador del neurotransmisor asociado no puede estar vacío.";
        }
        // Validación del coeficiente dentro de límites lógicos
        if (!validarCoeficienteK(k)) {
            return "El coeficiente de eficiencia k (" + k + ") está fuera de rango. Debe cumplir con: 0 < k <= 1.0.";
        }
        return null; // Datos listos y sanitizados
    }

    /**
     * Verificar un registro para ser integrado al diccionario de neurotransmisores.
     * @param id Identificador.
     * @param nombre Nombre natural.
     * @param velocidad Velocidad del compuesto.
     * @param efecto Tipo biológico.
     * @return null si el registro es totalmente válido, String con mensaje en caso de error.
     */
    public static String verificarRegistroNeurotransmisor(String id, String nombre, double velocidad, String efecto) {
        if (!validarId(id)) {
            // Verificación estructural del ID
            return "El ID del neurotransmisor está vacío o es inválido.";
        }
        // Verificación de nombre legible
        if (!validarId(nombre)) {
            return "El nombre del neurotransmisor no puede estar vacío.";
        }
        // Verificación matemática de la velocidad
        if (!validarVelocidad(velocidad)) {
            return "La velocidad de transmisión (" + velocidad + ") es inválida. Debe ser estrictamente mayor a 0.";
        }
        // Verificación del campo de efecto funcional
        if (!validarId(efecto)) {
            return "El campo de efecto biológico no puede estar vacío.";
        }
        return null; // Registro completamente sanitizado
    }
}