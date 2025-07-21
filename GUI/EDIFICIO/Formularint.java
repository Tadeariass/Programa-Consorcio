package GUI.EDIFICIO;

import GUI.PanelManagerCentral;
import GUI.PantallaPrincipal;

import javax.swing.*;
import java.awt.*;

public class Formularint extends JPanel{
    PanelManagerCentral panel;
    JPanel formulariointeraccion;


    JButton jButtonGuardar;
    JButton jButtonEliminar;
    JButton jButtonActualizar;
    JButton jButtonReporte;
    JButton jButtonatras;


    public Formularint (PanelManagerCentral panel) {
        this.panel = panel;
        armarFormularioEdificio();

    }
    public void armarFormularioEdificio()  {
        formulariointeraccion = new JPanel();


        jButtonGuardar = new JButton("Guardar Edificio");
        jButtonEliminar = new JButton("Eliminar Edificio");
        jButtonActualizar = new JButton("Actualizar Edificio");
        jButtonReporte = new JButton("Reporte Edificio");
        jButtonatras = new JButton("Atras");

        formulariointeraccion.add(jButtonGuardar);
        formulariointeraccion.add(jButtonEliminar);
        formulariointeraccion.add(jButtonActualizar);
        formulariointeraccion.add(jButtonReporte);
        formulariointeraccion.add(jButtonatras);

        jButtonGuardar.addActionListener(e -> panel.mostrar(new FormularioEdificio(panel)));
        jButtonEliminar.addActionListener(e -> panel.mostrar(new EliminarEdificio(panel)));
        jButtonActualizar.addActionListener(e -> panel.mostrar(new Modificaredificios(panel)));
        jButtonReporte.addActionListener(e -> panel.mostrar(new ReporteEdificio(panel)));
        jButtonatras.addActionListener(e -> panel.mostrar(new PantallaPrincipal(panel)));

        setLayout(new BorderLayout());
        add(formulariointeraccion, BorderLayout.CENTER);
    }


}
