package GUI;

import GUI.DEPARTAMENTOS.Formularioguardardpto;
import GUI.DEPARTAMENTOS.FormulariointDpto;
import GUI.EDIFICIO.*;
import GUI.GASTOS.FormularioIntGastos;
import GUI.OCUPANTES.FormularioIntOcupante;
import GUI.PISOS.FormularioguardarPisos;
import GUI.PISOS.Formulariointpisos;
import GUI.PISOS.ModificarPisos;

import javax.swing.*;
import java.awt.*;

public class PantallaPrincipal extends JPanel {
    PanelManagerCentral panel;
    Formularint paneledificio;

    JPanel formulariointeraccion;


    JButton jButtonEdificios;
    JButton jButtonDepartamentos;
    JButton jButtonOcupantes;
    JButton jButtonPisos;
    JButton jButtonatras;
    JButton jButtonGastos;


    public PantallaPrincipal (PanelManagerCentral panel) {
        this.panel = panel;
        armarFormularioEdificio();

    }
    public void armarFormularioEdificio()  {
        formulariointeraccion = new JPanel();
        formulariointeraccion.setLayout(new GridLayout(3, 2));
        jButtonEdificios = new JButton("Edificios");
        jButtonDepartamentos = new JButton("Departamentos");
        jButtonOcupantes = new JButton("Ocupantes");
        jButtonPisos = new JButton("Pisos");
        jButtonGastos = new JButton("Gastos");
        jButtonatras = new JButton("Atras");

        formulariointeraccion.add(jButtonEdificios);
        formulariointeraccion.add(jButtonDepartamentos);
        formulariointeraccion.add(jButtonOcupantes);
        formulariointeraccion.add(jButtonPisos);
        formulariointeraccion.add(jButtonatras);
        formulariointeraccion.add(jButtonGastos);


        jButtonEdificios.addActionListener(e -> panel.mostrar(new Formularint(panel)));

        jButtonDepartamentos.addActionListener(e -> panel.mostrar(new FormulariointDpto(panel)));
        jButtonPisos.addActionListener(e -> panel.mostrar(new Formulariointpisos(panel)));
        jButtonatras.addActionListener(e -> System.exit(0));
        jButtonOcupantes.addActionListener(e -> panel.mostrar(new FormularioIntOcupante(panel)));
        jButtonGastos.addActionListener(e -> panel.mostrar(new FormularioIntGastos(panel)));
        setLayout(new BorderLayout());
        add(formulariointeraccion, BorderLayout.CENTER);
    }
}


