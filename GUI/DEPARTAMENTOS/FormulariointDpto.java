package GUI.DEPARTAMENTOS;

import GUI.PanelManagerCentral;
import GUI.PantallaPrincipal;

import javax.swing.*;
import java.awt.*;

public class FormulariointDpto extends JPanel {
    PanelManagerCentral panel;
    JPanel formulariointeraccion;


    JButton jButtonGuardar;
    JButton jButtonEliminar;
    JButton jButtonActualizar;
    JButton jButtonReporte;
    JButton jButtonatras;


    public FormulariointDpto (PanelManagerCentral panel) {
        this.panel = panel;
        armarFormularioEdificio();

    }
    public void armarFormularioEdificio()  {
        formulariointeraccion = new JPanel();


        jButtonGuardar = new JButton("Guardar Departamento");
        jButtonEliminar = new JButton("Eliminar Departamento");
        jButtonActualizar = new JButton("Actualizar Departamento");
        jButtonReporte = new JButton("Reporte Departamento");
        jButtonatras = new JButton("Atras");

        formulariointeraccion.add(jButtonGuardar);
        formulariointeraccion.add(jButtonEliminar);
        formulariointeraccion.add(jButtonActualizar);
        formulariointeraccion.add(jButtonReporte);
        formulariointeraccion.add(jButtonatras);

        jButtonGuardar.addActionListener(e -> panel.mostrar(new Formularioguardardpto(panel)));
        jButtonEliminar.addActionListener(e -> panel.mostrar(new FormularioEliminarDpto(panel)));
        jButtonReporte.addActionListener(e -> panel.mostrar(new FormularioReporte(panel)));
        jButtonatras.addActionListener(e -> panel.mostrar(new PantallaPrincipal(panel)));
        jButtonActualizar.addActionListener(e -> panel.mostrar(new ModificarDepartamento(panel)));



        setLayout(new BorderLayout());
        add(formulariointeraccion, BorderLayout.CENTER);
    }}