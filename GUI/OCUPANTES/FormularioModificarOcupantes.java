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

public class FormularioModificarOcupantes extends JPanel {

    private ServicePisos servicePiso = new ServicePisos();
    private ServiceEdificio serviceEdificio = new ServiceEdificio();
    private ServiceDepartamento serviceDepartamento = new ServiceDepartamento();
    private ServiceOcupantes serviceOcupantes = new ServiceOcupantes();
    private PanelManagerCentral panel;

    private JComboBox<Integer> comboEdificios;
    private JComboBox<String> comboPisos;
    private JComboBox<String> comboDpto;
    private JComboBox<String> comboOcupantes;
    private JTextField textNombre;
    private JTextField textEdad;
    private JButton buttonActualizar;
    private JButton buttonVolver;

    private ArrayList<Ocupantes> ocupantesActuales = new ArrayList<>();

    public FormularioModificarOcupantes(PanelManagerCentral panel) {
        this.panel = panel;
        inicializarComponentes();
        armarEventos();
        cargarEdificios();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        JPanel formulario = new JPanel(new GridLayout(8, 2, 5, 5));

        comboEdificios = new JComboBox<>();
        comboPisos = new JComboBox<>();
        comboDpto = new JComboBox<>();
        comboOcupantes = new JComboBox<>();
        textNombre = new JTextField();
        textEdad = new JTextField();
        buttonActualizar = new JButton("Actualizar");
        buttonVolver = new JButton("Volver");

        formulario.add(new JLabel("Edificio:"));
        formulario.add(comboEdificios);
        formulario.add(new JLabel("Piso:"));
        formulario.add(comboPisos);
        formulario.add(new JLabel("Departamento:"));
        formulario.add(comboDpto);
        formulario.add(new JLabel("Ocupante:"));
        formulario.add(comboOcupantes);
        formulario.add(new JLabel("Nombre:"));
        formulario.add(textNombre);
        formulario.add(new JLabel("Edad:"));
        formulario.add(textEdad);
        formulario.add(buttonActualizar);
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
            JOptionPane.showMessageDialog(this, "ERROR AL ACUTUALIZAR: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void armarEventos() {
        comboEdificios.addActionListener(e -> {
            textNombre.setText("");
            textEdad.setText("");
            comboOcupantes.removeAllItems();
            comboDpto.removeAllItems();
            comboPisos.removeAllItems();
            ocupantesActuales.clear();


            if (comboEdificios.getSelectedIndex() > -1) {
                try {
                    int idEdificio = (int) comboEdificios.getSelectedItem();
                    ArrayList<Pisos> pisos = servicePiso.buscarTodos(idEdificio);
                    for (Pisos piso : pisos) {
                        comboPisos.addItem(piso.getNumeropiso());
                    }
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(this, "ERROR AL ACUTUALIZAR: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        });

        comboPisos.addActionListener(e -> {
            textNombre.setText("");
            textEdad.setText("");
            comboOcupantes.removeAllItems();
            comboDpto.removeAllItems();
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
                    JOptionPane.showMessageDialog(this, "ERROR AL ACUTUALIZAR: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        });

        comboDpto.addActionListener(e -> {
            textNombre.setText("");
            textEdad.setText("");
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
                    JOptionPane.showMessageDialog(this, "ERROR AL ACUTUALIZAR: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        });

        comboOcupantes.addActionListener(e -> {
            textNombre.setText("");
            textEdad.setText("");

            if (comboOcupantes.getSelectedIndex() > -1) {
                String nombreSeleccionado = (String) comboOcupantes.getSelectedItem();

                for (Ocupantes o : ocupantesActuales) {
                    if (o.getNombre().equals(nombreSeleccionado)) {
                        textNombre.setText(o.getNombre());
                        textEdad.setText(String.valueOf(o.getEdad()));
                        break;
                    }
                }
            }
        });

        buttonActualizar.addActionListener(e -> {
            try {
                int idEdificio = (int) comboEdificios.getSelectedItem();
                String numeroPiso = (String) comboPisos.getSelectedItem();
                String idDepto = (String) comboDpto.getSelectedItem();
                String nombreSeleccionado = (String) comboOcupantes.getSelectedItem();

                Ocupantes ocupanteSeleccionado = null;
                for (Ocupantes o : ocupantesActuales) {
                    if (o.getNombre().equals(nombreSeleccionado)) {
                        ocupanteSeleccionado = o;
                        break;
                    }
                }

                if (ocupanteSeleccionado != null) {
                    ocupanteSeleccionado.setNombre(textNombre.getText());
                    ocupanteSeleccionado.setEdad(Integer.parseInt(textEdad.getText()));

                    serviceOcupantes.actualizar(idEdificio, numeroPiso, idDepto, ocupanteSeleccionado,nombreSeleccionado);

                    JOptionPane.showMessageDialog(this, "Ocupante actualizado correctamente.");
                    panel.mostrar(new FormularioIntOcupante(panel));
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ERROR AL ACUTUALIZAR: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonVolver.addActionListener(e -> panel.mostrar(new FormularioIntOcupante(panel)));
    }
}