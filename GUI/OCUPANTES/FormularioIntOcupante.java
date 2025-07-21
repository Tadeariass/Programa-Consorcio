package GUI.OCUPANTES;

import GUI.DEPARTAMENTOS.FormularioEliminarDpto;
import GUI.DEPARTAMENTOS.FormularioReporte;
import GUI.DEPARTAMENTOS.Formularioguardardpto;
import GUI.DEPARTAMENTOS.ModificarDepartamento;
import GUI.PanelManagerCentral;
import GUI.PantallaPrincipal;

import javax.swing.*;
import java.awt.*;

public class FormularioIntOcupante extends JPanel {
        PanelManagerCentral panel;
        JPanel formulariointeraccion;


        JButton jButtonGuardar;
        JButton jButtonEliminar;
        JButton jButtonActualizar;
        JButton jButtonReporte;
        JButton jButtonatras;


    public FormularioIntOcupante (PanelManagerCentral panel) {
            this.panel = panel;
            armarFormularioEdificio();

        }
        public void armarFormularioEdificio()  {
            formulariointeraccion = new JPanel();


            jButtonGuardar = new JButton("Guardar Ocupantes");
            jButtonEliminar = new JButton("Eliminar Ocupantes");
            jButtonActualizar = new JButton("Actualizar Ocupantes");
            jButtonReporte = new JButton("Reporte Ocupantes");
            jButtonatras = new JButton("Atras");

            formulariointeraccion.add(jButtonGuardar);
            formulariointeraccion.add(jButtonEliminar);
            formulariointeraccion.add(jButtonActualizar);
            formulariointeraccion.add(jButtonReporte);
            formulariointeraccion.add(jButtonatras);

            jButtonGuardar.addActionListener(e -> panel.mostrar(new FormularioGuardarOcupantes(panel)));
            jButtonReporte.addActionListener(e -> panel.mostrar(new FormlarioReporteOcupantes(panel)));
            jButtonActualizar.addActionListener(e -> panel.mostrar(new FormularioModificarOcupantes(panel)));

            jButtonEliminar.addActionListener(e -> panel.mostrar(new FormularioEliminarOcupantes(panel)));
            jButtonatras.addActionListener(e -> panel.mostrar(new PantallaPrincipal(panel)));




            setLayout(new BorderLayout());
            add(formulariointeraccion, BorderLayout.CENTER);
        }
}



