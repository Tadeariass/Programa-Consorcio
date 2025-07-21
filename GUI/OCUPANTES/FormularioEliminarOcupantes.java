package GUI.OCUPANTES;


import Entidades.Departamento;
import Entidades.Edificio;
import Entidades.Ocupantes;
import Entidades.Pisos;
import GUI.PanelManagerCentral;
import Service.*;
import exepcion.DAOException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FormularioEliminarOcupantes extends JPanel {

    private ServicePisos servicePiso = new ServicePisos();
    private ServiceEdificio serviceEdificio = new ServiceEdificio();
    private ServiceDepartamento serviceDepartamento = new ServiceDepartamento();
    private ServiceOcupantes serviceOcupantes = new ServiceOcupantes();
    private PanelManagerCentral panel;

    private JComboBox<Integer> comboEdificios;
    private JComboBox<String> comboPisos;
    private JComboBox<String> comboDpto;
    private JComboBox<String> comboOcupantes;
    private JButton buttonEliminar;
    private JButton buttonVolver;

    private ArrayList<Ocupantes> ocupantesActuales = new ArrayList<>();

    public FormularioEliminarOcupantes(PanelManagerCentral panel) {
        this.panel = panel;
        inicializarComponentes();
        armarEventos();
        cargarEdificios();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        JPanel formulario = new JPanel(new GridLayout(6, 2, 5, 5));

        comboEdificios = new JComboBox<>();
        comboPisos = new JComboBox<>();
        comboDpto = new JComboBox<>();
        comboOcupantes = new JComboBox<>();
        buttonEliminar = new JButton("Eliminar");
        buttonVolver = new JButton("Volver");

        formulario.add(new JLabel("Edificio:"));
        formulario.add(comboEdificios);
        formulario.add(new JLabel("Piso:"));
        formulario.add(comboPisos);
        formulario.add(new JLabel("Departamento:"));
        formulario.add(comboDpto);
        formulario.add(new JLabel("Ocupante:"));
        formulario.add(comboOcupantes);
        formulario.add(buttonEliminar);
        formulario.add(buttonVolver);

        add(formulario, BorderLayout.CENTER);
    }

    private void cargarEdificios() {
        try {
            ArrayList<Edificio> edificios = serviceEdificio.buscarTodos();
            for (Edificio e : edificios) {
                comboEdificios.addItem(e.getId());
            }
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL CARGAR EDIFICIOS: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void armarEventos() {
        comboEdificios.addActionListener(e -> {
            comboPisos.removeAllItems();
            comboDpto.removeAllItems();
            comboOcupantes.removeAllItems();
            ocupantesActuales.clear();

            if (comboEdificios.getSelectedIndex() > -1) {
                try {
                    int idEdificio = (int) comboEdificios.getSelectedItem();
                    ArrayList<Pisos> pisos = servicePiso.buscarTodos(idEdificio);
                    for (Pisos piso : pisos) {
                        comboPisos.addItem(piso.getNumeropiso());
                    }
                } catch (DAOException ex) {
                    mostrarError(ex);
                }
            }
        });

        comboPisos.addActionListener(e -> {
            comboDpto.removeAllItems();
            comboOcupantes.removeAllItems();
            ocupantesActuales.clear();

            if (comboPisos.getSelectedIndex() > -1 && comboEdificios.getSelectedIndex() > -1) {
                try {
                    int idEdificio = (int) comboEdificios.getSelectedItem();
                    String nroPiso = (String) comboPisos.getSelectedItem();
                    ArrayList<Departamento> deptos = serviceDepartamento.buscarTodos(idEdificio, nroPiso);
                    for (Departamento d : deptos) {
                        comboDpto.addItem(d.getId_depto());
                    }
                } catch (DAOException ex) {
                    mostrarError(ex);
                }
            }
        });

        comboDpto.addActionListener(e -> {
            comboOcupantes.removeAllItems();
            ocupantesActuales.clear();

            if (comboDpto.getSelectedIndex() > -1 && comboEdificios.getSelectedIndex() > -1 && comboPisos.getSelectedIndex() > -1) {
                try {
                    int idEdificio = (int) comboEdificios.getSelectedItem();
                    String nroPiso = (String) comboPisos.getSelectedItem();
                    String idDepto = (String) comboDpto.getSelectedItem();
                    ocupantesActuales = serviceOcupantes.BuscarOcupantes(idEdificio, nroPiso, idDepto);
                    for (Ocupantes o : ocupantesActuales) {
                        comboOcupantes.addItem(o.getNombre());
                    }
                } catch (Exception ex) {
                    mostrarError(ex);
                }
            }
        });

        buttonEliminar.addActionListener(e -> {
            try {
                int idEdificio = (int) comboEdificios.getSelectedItem();
                String numeroPiso = (String) comboPisos.getSelectedItem();
                String idDepto = (String) comboDpto.getSelectedItem();
                String nombreSeleccionado = (String) comboOcupantes.getSelectedItem();


                    serviceOcupantes.eliminar(idEdificio, numeroPiso, idDepto, nombreSeleccionado);
                    JOptionPane.showMessageDialog(this, "Ocupante eliminado correctamente.");
                    panel.mostrar(new FormularioIntOcupante(panel));


            } catch (Exception ex) {
                mostrarError(ex);
            }
        });

        buttonVolver.addActionListener(e -> panel.mostrar(new FormularioIntOcupante(panel)));
    }

    private void mostrarError(Exception e) {
        JOptionPane.showMessageDialog(this, "ERROR: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
