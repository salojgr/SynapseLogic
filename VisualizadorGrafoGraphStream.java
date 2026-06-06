/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package synapselogic;
/**
 * Creación del visualizador de la red neuronal.
 * @author Luis Velásquez
 */

// Importar elementos necesarios de la librería GraphStream
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Component;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.camera.Camera;

@SuppressWarnings("serial")
public class VisualizadorGrafoGraphStream extends JPanel {

    private final Graph grafoGS;
    private Viewer visor;
    private View vista;

    // Asignación de atributos CSS de GraphStream
    private final String hojaEstilos = 
        "node {" +
        "   size: 26px;" +
        "   fill-color: #2c3e50;" +
        "   text-mode: normal;" +
        "   text-color: #2c3e50;" +
        "   text-size: 13px;" +
        "   text-style: bold;" +
        "   text-alignment: at-right;" +
        "   stroke-mode: plain;" +
        "   stroke-color: #34495e;" +
        "   stroke-width: 2px;" +
        "}" +
        "edge {" +
            "   fill-color: #bdc3c7;" +
            "   size: 2px;" +
            "   arrow-size: 8px, 4px;" +
            "   text-mode: normal;" +
            "   text-color: #2c3e50;" +
            "   text-size: 11px;" +
            "   text-alignment: above;" +
            "}";

    public VisualizadorGrafoGraphStream() {
        // Indicar a GraphStream que renderice utilizando la extensión de Swing
        System.setProperty("org.graphstream.ui", "swing");
        
        // Configuración para que el grafo esté centrado dentro de su panel, usando BorderLayout
        this.setLayout(new BorderLayout());
        
        // Inicializar el contenedor del grafo visual
        this.grafoGS = new SingleGraph("RedNeuronalVisual");
        this.grafoGS.setAttribute("ui.stylesheet", hojaEstilos);
        this.grafoGS.setAttribute("ui.antialias"); 
        
        inicializarVisor();
    }

    //Configurar el visor interno e instalar el lienzo interactivo dentro del Panel.
    private void inicializarVisor() {
        this.visor = new SwingViewer(grafoGS, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        this.visor.enableAutoLayout(); // Los nodos se repelen de forma elástica automáticamente

        this.vista = visor.addDefaultView(false); // false indica que no es una ventana independiente

        // Agregar controles básicos de zoom para redes grandes
        this.add(crearBarraZoom(), BorderLayout.NORTH);

        // Asegurar el lienzo en el centro del panel
        this.add((Component) vista, BorderLayout.CENTER);
    }
    
    // Opciones de Zoom para el visualizador
    private JToolBar crearBarraZoom() {
        JToolBar barraZoom = new JToolBar();
        barraZoom.setFloatable(false);

        JButton btnZoomMas = new JButton("Acercar vista");
        JButton btnZoomMenos = new JButton("Alejar vista");
        JButton btnCentrar = new JButton("Vista inicial");

        btnZoomMas.addActionListener(e -> acercarZoom());
        btnZoomMenos.addActionListener(e -> alejarZoom());
        btnCentrar.addActionListener(e -> centrarVista());

        barraZoom.add(btnZoomMas);
        barraZoom.add(btnZoomMenos);
        barraZoom.add(btnCentrar);

        return barraZoom;
    }

    //Factores de acercamiento y alejamiento
    private void acercarZoom() {
        aplicarZoom(0.80);
    }

    private void alejarZoom() {
        aplicarZoom(1.25);
    }

    private void aplicarZoom(double factor) {
        if (vista == null) {
            return;
        }
        // Aplicación del zoom al visualizador
        Camera camara = vista.getCamera();
        camara.setAutoFitView(false);

        double nuevoPorcentaje = camara.getViewPercent() * factor;

        if (nuevoPorcentaje < 0.05) {
            nuevoPorcentaje = 0.05;
        }

        if (nuevoPorcentaje > 5.0) {
            nuevoPorcentaje = 5.0;
        }

        camara.setViewPercent(nuevoPorcentaje);
    }

    // Volver al estado original de la vista a la red sináptica
    private void centrarVista() {
        if (vista == null) {
            return;
        }

        Camera camara = vista.getCamera();
        camara.resetView();
        camara.setAutoFitView(true);
    }

    /**
     * Limpiar la interfaz visual y proyectar las estructuras de GrafoDirigidoListaAdyacencia
     * @param grafoBackend Instancia con los datos cargados desde el controlador.
     */
    public void actualizarGrafo(GrafoDirigidoListaAdyacencia grafoBackend) {
        if (grafoBackend == null) return;

        // Limpiar el lienzo para evitar duplicaciones o colisiones de IDs al recargar
        grafoGS.clear();
        grafoGS.setAttribute("ui.stylesheet", hojaEstilos);
        grafoGS.setAttribute("ui.antialias");

        // Recorrer la lista de identificadores únicos del backend
        ListaEnlazada<String> ids = grafoBackend.getListaIds();
        
        // Mapear y registrar todas las Neuronas (Nodos)
        for (int i = 0; i < ids.tamano(); i++) {
            String idNeurona = ids.obtener(i);
            if (idNeurona != null) {
                Node nodeGS = grafoGS.addNode(idNeurona);
                nodeGS.setAttribute("ui.label", idNeurona);
            }
        }

        // Mapear y registrar todas las Sinapsis (Bordes dirigidos)
        for (int i = 0; i < ids.tamano(); i++) {
            String idOrigen = ids.obtener(i);
            ListaEnlazada<Sinapsis> sinapsisLista = grafoBackend.obtenerSinapsisDe(idOrigen);
            
            if (sinapsisLista != null) {
                for (int j = 0; j < sinapsisLista.tamano(); j++) {
                    Sinapsis sinapsisActual = sinapsisLista.obtener(j);
                    if (sinapsisActual != null && sinapsisActual.getDestino() != null) {
                        String idDestino = sinapsisActual.getDestino().getID();
                        String idBorde = idOrigen + "-" + idDestino;
                        
                       Edge bordeGS = grafoGS.getEdge(idBorde);

                        if (bordeGS == null) {
                            bordeGS = grafoGS.addEdge(idBorde, idOrigen, idDestino, true); 
                        }

                        String etiqueta = (sinapsisActual.getNeurotransmisor() + " k=" + String.format(java.util.Locale.US, "%.2f", sinapsisActual.getK()));

                        if (!sinapsisActual.isActiva()) {
                            etiqueta += " | OFF";
                        }

                        bordeGS.setAttribute("ui.label", etiqueta);

                        if (!sinapsisActual.isActiva()) {
                            bordeGS.setAttribute("ui.style", "fill-color: #c0392b; size: 3px;");
                        }
                    }
                }
            }
        }
        this.revalidate(); // Reorganización del espacio (Layout)
        this.repaint(); // Realizar cambios visuales
    }
    /**
     * Resaltar en el grafo las neuronas alcanzables e inalcanzables
     * según el resultado de BFS o DFS.
     * Verde: neuronas alcanzables desde la fuente.
     * Rojo: neuronas inalcanzables o zonas aisladas.
     * Azul: neurona fuente.
     * @param resultado resultado generado por BFS o DFS.
     */
    public void resaltarZonasAisladas(ResultadoConectividad resultado) {
        if (resultado == null) {
            return;
        }

        // Restaurar estilo base de todos los nodos
        grafoGS.nodes().forEach(n -> {
            n.setAttribute(
                "ui.style",
                "fill-color: #2c3e50; size: 26px; stroke-color: #34495e; stroke-width: 2px;"
            );
        });

        // Restaurar estilo base de todas las aristas
        grafoGS.edges().forEach(e -> {
            e.setAttribute(
                "ui.style",
                "fill-color: #bdc3c7; size: 2px;"
            );
        });

        // Pintar alcanzables en verde
        ListaEnlazada<String> alcanzables = resultado.getAlcanzables();

        if (alcanzables != null) {
            for (int i = 0; i < alcanzables.tamano(); i++) {
                String id = alcanzables.obtener(i);
                Node nodo = grafoGS.getNode(id);

                if (nodo != null) {
                    nodo.setAttribute(
                        "ui.style",
                        "fill-color: #27ae60; size: 28px; stroke-color: #145a32; stroke-width: 3px;"
                    );
                }
            }
        }

        // Pintar inalcanzables en rojo
        ListaEnlazada<String> inalcanzables = resultado.getInalcanzables();

        if (inalcanzables != null) {
            for (int i = 0; i < inalcanzables.tamano(); i++) {
                String id = inalcanzables.obtener(i);
                Node nodo = grafoGS.getNode(id);

                if (nodo != null) {
                    nodo.setAttribute(
                        "ui.style",
                        "fill-color: #c0392b; size: 32px; stroke-color: #641e16; stroke-width: 4px;"
                    );
                }
            }
        }

        // Pintar la neurona fuente en azul para que se entienda desde dónde salió el recorrido
        String fuente = resultado.getIdFuente();

        if (fuente != null) {
            Node nodoFuente = grafoGS.getNode(fuente);

            if (nodoFuente != null) {
                nodoFuente.setAttribute(
                    "ui.style",
                    "fill-color: #2980b9; size: 34px; stroke-color: #1b4f72; stroke-width: 4px;"
                );
            }
        }

        this.revalidate(); // Reorganización del espacio (Layout)
        this.repaint(); // Realizar cambios visuales
    }
    
    /**
     * Resaltar visualmente un camino específico (secuencia de IDs de una Ruta de Dijkstra)
     * pintando las aristas involucradas de un color llamativo (Naranja).
     * @param secuenciaIds Lista enlazada con la secuencia de nodos recorridos en la ruta Dijkstra.
     */
    public void resaltarRuta(ListaEnlazada<String> secuenciaIds) {
        if (secuenciaIds == null || secuenciaIds.tamano() < 2) return;
 
        //Implementar elemento gráfico para visualizar la sinapsis (aristas) entre neuronas (nodos).
        grafoGS.edges().forEach(e -> {
            e.setAttribute("ui.style", "fill-color: #bdc3c7; size: 2px;");
        });

        // Pintar la secuencia de la nueva ruta calculada
        for (int i = 0; i < secuenciaIds.tamano() - 1; i++) {
            String origen = secuenciaIds.obtener(i);
            String destino = secuenciaIds.obtener(i + 1);
            String idBorde = origen + "-" + destino;
            
            Edge e = grafoGS.getEdge(idBorde);
            if (e != null) {
                // Hacer la arista más gruesa y cambiar su color a naranja
                e.setAttribute("ui.style", "fill-color: #e67e22; size: 5px;");
            }
        }
    }
}