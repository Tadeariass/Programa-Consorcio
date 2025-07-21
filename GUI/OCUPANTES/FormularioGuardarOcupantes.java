package GUI.OCUPANTES;

import Entidades.Departamento;
import Entidades.Edificio;
import Entidades.Ocupantes;
import Entidades.Pisos;
import GUI.DEPARTAMENTOS.FormulariointDpto;
import GUI.PanelManagerCentral;
import Service.*;
import exepcion.DAOException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FormularioGuardarOcupantes extends JPanel {

    private ServicePisos servicePiso;
    private ServiceEdificio serviceEdificio;
    private ServiceDepartamento serviceDepartamento;
    private ServiceOcupantes serviceOcupantes;
    private ServiceDepartamento service;
    private PanelManagerCentral panel;
    JLabel labelEdificio;
    JLabel labelPiso;
    JLabel labelNumDpto;
    JTextField textNumDpto;
    JTextField textNYAPE;
    JLabel labeLNYAPE;
    JTextField textEdad;
    JLabel labelEdad;
    JButton buttonGrabar;
    JButton buttonVolver;

    public FormularioGuardarOcupantes(PanelManagerCentral panel) {
        this.panel = panel;
        this.servicePiso = new ServicePisos();
        this.serviceEdificio = new ServiceEdificio();
        this.serviceDepartamento = new ServiceDepartamento();
        this.serviceOcupantes = new ServiceOcupantes();
        armarFormularioGuardarDpto();
    }

    private void armarFormularioGuardarDpto() {
        JPanel formularioDpto = new JPanel(new GridLayout(7, 2));

        labelEdificio = new JLabel("Seleccione el Edificio:");
        JComboBox<Integer> comboEdificios = new JComboBox<>();

        labelPiso = new JLabel("Seleccione el Piso:");
        JComboBox<String> comboPisos = new JComboBox<>();

        labelNumDpto = new JLabel("Selecciones Numero de Departamento:");
        JComboBox<String> comboDpto = new JComboBox<>();

        labeLNYAPE = new JLabel("Nombre y Apellido:");
        textNYAPE = new JTextField();

        labelEdad = new JLabel("EDAD:");
        textEdad = new JTextField();


        buttonGrabar = new JButton("Guardar Ocupante");
        buttonVolver = new JButton("Volver");


        try {
            ArrayList<Edificio> edificios = serviceEdificio.buscarTodos();
            comboEdificios.addItem(null);
            for (Edificio edificio : edificios) {
                comboEdificios.addItem(edificio.getId());
            }
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar edificios: " + e.getMessage());
        }

        formularioDpto.add(labelEdificio);
        formularioDpto.add(comboEdificios);
        formularioDpto.add(labelPiso);
        formularioDpto.add(comboPisos);
        formularioDpto.add(labelNumDpto);
        formularioDpto.add(comboDpto);
        formularioDpto.add(labeLNYAPE);
        formularioDpto.add(textNYAPE);
        formularioDpto.add(labelEdad);
        formularioDpto.add(textEdad);
        formularioDpto.add(buttonGrabar);
        formularioDpto.add(buttonVolver);

        comboEdificios.addActionListener(e -> {
            comboPisos.removeAllItems();
            Integer edificioId = (Integer) comboEdificios.getSelectedItem();
            if (edificioId != null) {
                try {
                    ArrayList<Pisos> pisos = servicePiso.buscarTodos(edificioId);
                    for (Pisos piso : pisos) {
                        comboPisos.addItem(piso.getNumeropiso());
                    }
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al cargar pisos: " + ex.getMessage());
                }
            }
        });
        comboPisos.addActionListener(e -> {
            comboDpto.removeAllItems();
            Integer edificioId = (Integer) comboEdificios.getSelectedItem();
            String idpiso = (String) comboPisos.getSelectedItem();

            if (edificioId != null && idpiso != null) {
                try {
                    ArrayList<Departamento> departamentos = serviceDepartamento.buscarTodos(edificioId,idpiso);
                    for (Departamento departamento : departamentos) {
                        comboDpto.addItem(departamento.getId_depto());
                    }
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al cargar pisos: " + ex.getMessage());
                }
            }
        });

        buttonVolver.addActionListener(e -> panel.mostrar(new FormularioIntOcupante(panel)));

        buttonGrabar.addActionListener(e -> {
            try {
                Integer edificioId = (Integer) comboEdificios.getSelectedItem();
                String idpiso = (String) comboPisos.getSelectedItem();
                String iddepartamento = (String) comboDpto.getSelectedItem();
                Ocupantes ocupante=new Ocupantes();
                ocupante.setNombre(textNYAPE.getText());
                ocupante.setEdad(Integer.parseInt(textEdad.getText()));

                serviceOcupantes.guardar(edificioId,idpiso,iddepartamento, ocupante);
                JOptionPane.showMessageDialog(this, "Departamento guardado exitosamente");
                panel.mostrar(new FormularioIntOcupante(panel));

            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar departamento: " + ex.getMessage());
            }
        });

        setLayout(new BorderLayout());
        add(formularioDpto, BorderLayout.CENTER);
    }
}

