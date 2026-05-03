/**
 *
 * @author Daniel Vera
 */
//Importación de librerías para la apertura, lectura y manejo de errores de archivos
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CargadorRedCSV {
   
    public GrafoDirigidoListaAdyacencia cargar(String rutaArchivo){
    
        GrafoDirigidoListaAdyacencia grafo = new GrafoDirigidoListaAdyacencia();
        
        try {
            //Abrimos el CSV
            BufferedReader br;
            br = new BufferedReader(new FileReader(rutaArchivo));
            
            String linea;
            //Lee la primera linea y la descarta (el encabezado)
            br.readLine();
            
            //Recorre linea por linea
            while ((linea = br.readLine()) != null){
            
                if (linea.trim().isEmpty()) {
                    continue;
                }
                
                try{
                    //Crea un array separando todo por comas
                    String[] partes = linea.split(",");
                    
                    //verifica que estén todos los datos en esa línea
                    if (partes.length != 5){
                        System.out.printf("Error (línea %s): La línea tiene algun dato faltante.%n", linea);
                        continue;
                    }
                    
                    //Asigna los datos separados a nuevas variables. Quita espacios vacíos en caso de que haya
                    String origen = partes[0].trim();
                    String destino = partes[1].trim();
                    double distancia = Double.parseDouble(partes[2].trim());
                    String neurotransmisor = partes[3].trim();
                    double k = Double.parseDouble(partes[4].trim());    
                    
                    //validaciones
                    if (origen.isEmpty() || destino.isEmpty()) {
                        System.out.printf("Error (línea %s): El ID de neurona origen o destino está vacío.%n", linea);
                        continue;
                    }
                        
                    if (distancia <= 0) {
                        System.out.printf("Error (línea %s): La distancia %.2f es inválida. Debe ser mayor a 0.%n", linea, distancia);
                        continue;
                    }
                        
                    if (k <= 0 || k > 1.0) {
                        System.out.printf("Error (línea %s): Coeficiente k (%.2f) fuera de rango. Debe estar entre >0 y 1.0.%n", linea, k);
                        continue;
                    }
                    
                    if (grafo.existeSinapsis(origen, destino)) {
                        System.out.printf("Error (línea %s): La conexión '%s' -> '%s' ya existe. Se ignora duplicado.%n", linea, origen, destino);
                        continue;
                    }
                    
                    // ----> (Creo que aqui va lo de agregar neuronas si no existen)
                    
                    //Agregamos la sinapsis al grafo
                    grafo.agregarSinapsis(origen, destino, distancia, neurotransmisor, k);

                } catch(NumberFormatException e){
                    //Error si k no es un número
                    System.out.printf("Error (línea %s): Formato numérico incorrecto.%n", linea);
                } catch (Exception e){
                    //Errores no identificados
                    System.out.printf("Error (línea %s): Error inesperado. Info: %s%n", linea, e.getMessage());
                }
            
            }
                
            //Cierra el CSV    
            br.close();
            
        } catch (IOException e){
                //Error de apertura del CSV
                System.out.printf("Error: No se pudo leer el archivo. Detalles: %s%n", e.getMessage());
        }
    
        return grafo;
    
    }
    
}
