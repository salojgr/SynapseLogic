/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package synapselogic;

/**
 *
 * @author Dell
 */
public class SynapseLogic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Cola<String> cola = new Cola<>();

cola.encolar("A");
cola.encolar("B");
cola.encolar("C");

System.out.println(cola.desencolar()); // A
System.out.println(cola.desencolar()); // B
System.out.println(cola.frente());     // C
System.out.println(cola.desencolar()); // C
System.out.println(cola.estaVacia());  // true

Pila<String> pila = new Pila<>();

pila.apilar("A");
pila.apilar("B");
pila.apilar("C");

System.out.println(pila.desapilar()); // C
System.out.println(pila.desapilar()); // B
System.out.println(pila.cima());      // A
System.out.println(pila.desapilar()); // A
System.out.println(pila.estaVacia()); // true
    }
    
}
