package GUI.PISOS;


import GUI.PanelManagerCentral;
import GUI.PantallaPrincipal;

import javax.swing.*;
import java.awt.*;

public class Formulariointpisos extends JPanel {
    PanelManagerCentral panel;
    JPanel formulariointeraccion;


    JButton jButtonGuardar;
    JButton jButtonEliminar;
    JButton jButtonActualizar;
    JButton jButtonReporte;
    JButton jButtonatras;


    public Formulariointpisos(PanelManagerCentral panel) {
        this.panel = panel;
        armarFormularioEdificio();

    }
    public void armarFormularioEdificio()  {
        formulariointeraccion = new JPanel();


        jButtonGuardar = new JButton("Guardar Piso");
        jButtonEliminar = new JButton("Eliminar Piso");
        jButtonActualizar = new JButton("Actualizar Piso");
        jButtonReporte = new JButton("Reporte Pisos");
        jButtonatras = new JButton("Volver al Menu");

        formulariointeraccion.add(jButtonGuardar);
        formulariointeraccion.add(jButtonEliminar);
        formulariointeraccion.add(jButtonActualizar);
        formulariointeraccion.add(jButtonReporte);
        formulariointeraccion.add(jButtonatras);

        jButtonGuardar.addActionListener(e -> panel.mostrar(new FormularioguardarPisos(panel)));
        jButtonEliminar.addActionListener(e -> panel.mostrar(new EliminarPisos(panel)));
        jButtonActualizar.addActionListener(e -> panel.mostrar(new ModificarPisos(panel)));
        jButtonReporte.addActionListener(e -> panel.mostrar(new ReportePisos(panel)));
        jButtonatras.addActionListener(e -> panel.mostrar(new PantallaPrincipal(panel)));

        setLayout(new BorderLayout());
        add(formulariointeraccion, BorderLayout.CENTER);
    }


}


