package GUI.GASTOS;

import Entidades.Departamento;
import Entidades.GASTODEPTO;
import Entidades.Pisos;
import GUI.PanelManagerCentral;
import Service.*;
import exepcion.DAOException;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class FormularioCrearExpensa extends JPanel {

    private ServiceEdificio serviceEdificio;
    private ServicePisos servicePisos;
    private ServiceDepartamento serviceDepartamento;
    private ServiceExpensas serviceGastos;
    private PanelManagerCentral panel;

    public FormularioCrearExpensa(PanelManagerCentral panel) {
        this.panel = panel;
        this.serviceEdificio = new ServiceEdificio();
        this.servicePisos = new ServicePisos();
        this.serviceDepartamento = new ServiceDepartamento();
        this.serviceGastos = new ServiceExpensas();
        armarFormulario();
    }

    private void armarFormulario() {
        JPanel panelFormulario = new JPanel(new GridLayout(8, 2));

        JLabel labelEdificio = new JLabel("Edificio:");
        JComboBox<Integer> comboEdificio = new JComboBox<>();

        JLabel labelPiso = new JLabel("Piso:");
        JComboBox<String> comboPiso = new JComboBox<>();

        JLabel labelDpto = new JLabel("Departamento:");
        JComboBox<String> comboDpto = new JComboBox<>();

        JLabel labelDescripcion = new JLabel("Descripción:");
        JTextField textDescripcion = new JTextField();

        JLabel labelValor = new JLabel("Valor:");
        JTextField textValor = new JTextField();

        JLabel labelFecha = new JLabel("Fecha (AAAA-MM-DD):");
        JTextField textFecha = new JTextField(LocalDate.now().toString());

        JButton buttonGuardar = new JButton("Guardar Expensa");
        JButton buttonVolver = new JButton("Volver");

        try {
            comboEdificio.addItem(null);
            for (Entidades.Edificio e : serviceEdificio.buscarTodos()) {
                comboEdificio.addItem(e.getId());
            }
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar edificios: " + e.getMessage());
        }

        comboEdificio.addActionListener(e -> {
            comboPiso.removeAllItems();
            Integer idEdificio = (Integer) comboEdificio.getSelectedItem();
            if (idEdificio != null) {
                try {
                    for (Pisos piso : servicePisos.buscarTodos(idEdificio)) {
                        comboPiso.addItem(piso.getNumeropiso());
                    }
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al cargar pisos: " + ex.getMessage());
                }
            }
        });

        comboPiso.addActionListener(e -> {
            comboDpto.removeAllItems();
            Integer idEdificio = (Integer) comboEdificio.getSelectedItem();
            String piso = (String) comboPiso.getSelectedItem();
            if (idEdificio != null && piso != null) {
                try {
                    for (Departamento d : serviceDepartamento.buscarTodos(idEdificio, piso)) {
                        comboDpto.addItem(d.getId_depto());
                    }
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al cargar departamentos: " + ex.getMessage());
                }
            }
        });

        panelFormulario.add(labelEdificio);
        panelFormulario.add(comboEdificio);
        panelFormulario.add(labelPiso);
        panelFormulario.add(comboPiso);
        panelFormulario.add(labelDpto);
        panelFormulario.add(comboDpto);
        panelFormulario.add(labelDescripcion);
        panelFormulario.add(textDescripcion);
        panelFormulario.add(labelValor);
        panelFormulario.add(textValor);
        panelFormulario.add(labelFecha);
        panelFormulario.add(textFecha);
        panelFormulario.add(buttonGuardar);
        panelFormulario.add(buttonVolver);

        buttonGuardar.addActionListener(e -> {
            GASTODEPTO gasto = new GASTODEPTO();
            gasto.setId_depto((String) comboDpto.getSelectedItem());
            gasto.setDescripcion(textDescripcion.getText());
            gasto.setValor(Double.parseDouble(textValor.getText()));
            gasto.setFecha(LocalDate.parse(textFecha.getText()));
            gasto.setEstado(false);

            serviceGastos.guardarGasto(gasto);
            JOptionPane.showMessageDialog(this, "Expensa guardada con éxito.");
            panel.mostrar(new FormularioIntGastos(panel));

        });

        buttonVolver.addActionListener(e -> panel.mostrar(new FormularioIntGastos(panel)));

        setLayout(new BorderLayout());
        add(panelFormulario, BorderLayout.CENTER);
    }
}
