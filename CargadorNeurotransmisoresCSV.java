/**
 *
 * @author Daniel Vera
 */
//Importación de librerías para la apertura, lectura y manejo de errores de archivos
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class CargadorNeurotransmisoresCSV {
    
    public TablaHash<String, Neurotransmisor> cargar(String rutaArchivo){
    
        TablaHash<String, Neurotransmisor> diccionario = new TablaHash<>();
    
        
        try{
            //Abrimos el CSV
            BufferedReader br;
            br = new BufferedReader(new FileReader(rutaArchivo));
            
            String linea;
            //Lee la primera linea y la descarta (el encabezado)
            br.readLine();
            
            while ((linea = br.readLine()) != null){
            
                try{
                    //Crea un array separando todo por comas
                    String[] partes = linea.split(",", 5);

                    if (partes.length < 5){
                    System.out.printf("Error (línea %s): La línea está incompleta (faltan columnas).%n", linea);                        
                    continue;
                    }
                    
                    //Asigna los datos separados a nuevas variables
                    String id = partes[0].trim();
                    String nombre = partes[1].trim();
                    String efecto = partes[2].trim();
                    double velocidad = Double.parseDouble(partes[3].trim());
                    String descripcion = partes[4].trim(); 
                    
                    //Verifica el id, nombre y velocidad
                    if (id.isEmpty()) {
                        System.out.printf("Error (línea %s): El ID está vacío.%n", linea);
                        continue;
                    }

                    if (nombre.isEmpty()) {
                        System.out.printf("Error (línea %s): El nombre está vacío.%n", linea);
                        continue;
                    }
                    
                    if (efecto.isEmpty()){
                        System.out.printf("Error (línea %s): El efecto está vacío.%n", linea);
                        continue;
                    }

                    if (velocidad <= 0) {
                        System.out.printf("Error (línea %s): La velocidad %f es inválida. Debes ser mayor a 0.%n", linea, velocidad);
                        continue;
                    }
                    
                    Neurotransmisor neuro = new Neurotransmisor(id, nombre, efecto, velocidad, descripcion);
                    
                    diccionario.insertar(id, neuro);
                    

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
    
            return diccionario;
        
    }
    
}
