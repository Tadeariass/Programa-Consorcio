package GUI.GASTOS;

import GUI.OCUPANTES.FormularioEliminarOcupantes;
import GUI.OCUPANTES.FormularioModificarOcupantes;
import GUI.PanelManagerCentral;
import GUI.PantallaPrincipal;

import javax.swing.*;
import java.awt.*;

public class FormularioIntGastos  extends JPanel{
    PanelManagerCentral panel;
    JPanel formulariointeraccion;


    JButton jButtonGuardar;
    JButton jButtonEliminar;
    JButton jButtonActualizar;
    JButton jButtonExpensa;
    JButton jButtonatras;


    public FormularioIntGastos (PanelManagerCentral panel) {
        this.panel = panel;
        armarFormularioEdificio();

    }
    public void armarFormularioEdificio()  {
        formulariointeraccion = new JPanel();


        jButtonGuardar = new JButton("Guardar GASTO");
        jButtonEliminar = new JButton("Marcar Expensa Paga");
        jButtonActualizar = new JButton("Tabla GASTOS/ENTRADA");
        jButtonExpensa = new JButton("Crear EXPENSA");
        jButtonatras = new JButton("Atras");

        formulariointeraccion.add(jButtonGuardar);
        formulariointeraccion.add(jButtonEliminar);
        formulariointeraccion.add(jButtonActualizar);
        formulariointeraccion.add(jButtonExpensa);
        formulariointeraccion.add(jButtonatras);

        jButtonGuardar.addActionListener(e -> panel.mostrar(new FormularioCrearGasto(panel)));
        jButtonExpensa.addActionListener(e -> panel.mostrar(new FormularioCrearExpensa(panel)));
        jButtonActualizar.addActionListener(e -> panel.mostrar(new FormularioGastosYEntradas(panel)));

        jButtonEliminar.addActionListener(e -> panel.mostrar(new FormularioPagarExpensas(panel)));
        jButtonatras.addActionListener(e -> panel.mostrar(new PantallaPrincipal(panel)));




        setLayout(new BorderLayout());
        add(formulariointeraccion, BorderLayout.CENTER);
    }

}
