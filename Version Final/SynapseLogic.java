/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package synapselogic;

import javax.swing.UIManager;
import javax.swing.SwingUtilities;

/**
 * Clase principal de ejecución (Entry Point) para SynapseLogic.
 * Centraliza el arranque del sistema bajo el patrón de diseño MVC de forma segura.
 * * @author Daniel Vera
 */
public class SynapseLogic {

    /**
     * Método de entrada al sistema.
     * @param args Argumentos de consola (no utilizados).
     */
    public static void main(String[] args) {
        
        // Configurar un Look and Feel moderno
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
           
        }

        // Ejecutar en el Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            try {
                // Inicializar el cerebro lógico del Backend (Controlador)
                SynapseLogicController controlador = new SynapseLogicController();
                
                // Inicializar la interfaz principal pasando el controlador como dependencia
                MainFrame ventanaPrincipal = new MainFrame(controlador);
                
                // Centrar la ventana automáticamente en la pantalla del usuario
                ventanaPrincipal.setLocationRelativeTo(null);
                
                // Desplegar el Dashboard
                ventanaPrincipal.setVisible(true);
                
            } catch (Exception e) {
                
            }
        });
    }
}