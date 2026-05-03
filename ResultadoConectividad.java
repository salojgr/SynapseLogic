/**
 *
 * @author Daniel Vera
 */
public class ResultadoConectividad {
   
    private String idFuente;
    private String algoritmo;
    private ListaEnalzada<String> alcanzables;
    private ListaEnlazada<String> inalcanzables;
    private boolean fuertementeConexa;
    
    public ResultadoConectividad(String idFuente, String algoritmo){
    
      this.idFuente = idFuente;
      this.algoritmo = algoritmo;
      this.alcanzables = new ListaEnlazada<>();
      this.inalcanzables = new ListaEnlazada<>();
      this.fuertementeConexa = false;
      
    }
    
    public void agregarAlcanzable(String id){
      this.alcanzables.insertarFinal(id);
    }
      
    public void agregarInalcanzable(String id){
      this.inalcanzables.insertarFinal(id);
    }
    
    public ListaEnlazada<String> getAlcanzables(){
        return alcanzables;
    }
      
    public ListaEnlazada<String> getInalcanzables(){
        return inalcanzables;
    }
      
    public boolean esFuertementeConexa() {
        return fuertementeConexa;
    }
    
    public void setFuertementeConexa(boolean fuertementeConexa) {
        this.fuertementeConexa = fuertementeConexa;
    }
    
}
