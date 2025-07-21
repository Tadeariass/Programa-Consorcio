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

public class FormularioPagarExpensas extends JPanel {

    private ServiceEdificio serviceEdificio;
    private ServicePisos servicePisos;
    private ServiceDepartamento serviceDepartamento;
    private ServiceExpensas serviceGastos;
    private PanelManagerCentral panel;

    public FormularioPagarExpensas(PanelManagerCentral panel) {
        this.panel = panel;
        this.serviceEdificio = new ServiceEdificio();
        this.servicePisos = new ServicePisos();
        this.serviceDepartamento = new ServiceDepartamento();
        this.serviceGastos = new ServiceExpensas();
        armarFormulario();
    }

    private void armarFormulario() {
        JPanel panelFormulario = new JPanel(new GridLayout(10, 2));

        JLabel labelEdificio = new JLabel("Edificio:");
        JComboBox<Integer> comboEdificio = new JComboBox<>();

        JLabel labelPiso = new JLabel("Piso:");
        JComboBox<String> comboPiso = new JComboBox<>();

        JLabel labelDpto = new JLabel("Departamento:");
        JComboBox<String> comboDpto = new JComboBox<>();

        JLabel labelExpensa = new JLabel("Expensas:");
        JComboBox<Integer> expensas = new JComboBox<>();

        JLabel labelDescripcion = new JLabel("Descripción:");
        JTextField textDescripcion = new JTextField();


        JLabel labelValor = new JLabel("Valor:");
        JTextField textValor = new JTextField();
        textDescripcion.setEditable(false);
        textValor.setEditable(false);

        JLabel labelFecha = new JLabel("Fecha (AAAA-MM-DD):");
        JTextField textFecha = new JTextField(LocalDate.now().toString());
        textFecha.setEditable(false);

        JButton buttonGuardar = new JButton("Pagar Expensa");
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

        comboDpto.addActionListener(e -> {

            Integer idEdificio = (Integer) comboEdificio.getSelectedItem();
            String piso = (String) comboPiso.getSelectedItem();
            String dpto = (String) comboDpto.getSelectedItem();
            if (idEdificio != null && piso != null && dpto != null) {
                try {
                    for (GASTODEPTO d : serviceGastos.obtenerExpensasDepartamento(idEdificio,piso,dpto)) {
                        expensas.addItem(d.getId_expensa());
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
        panelFormulario.add(labelExpensa);
        panelFormulario.add(expensas);
        panelFormulario.add(labelDescripcion);
        panelFormulario.add(textDescripcion);
        panelFormulario.add(labelValor);
        panelFormulario.add(textValor);
        panelFormulario.add(labelFecha);
        panelFormulario.add(textFecha);
        panelFormulario.add(buttonGuardar);
        panelFormulario.add(buttonVolver);


        buttonGuardar.addActionListener(e -> {
             int edificio = (int) comboEdificio.getSelectedItem();
             int expensa = (int) expensas.getSelectedItem();

            serviceGastos.marcarExpensaComoPagada(expensa, edificio);
            JOptionPane.showMessageDialog(this, "Expensa guardada con éxito.");
            panel.mostrar(new FormularioIntGastos(panel));

        });

        buttonVolver.addActionListener(e -> panel.mostrar(new FormularioIntGastos(panel)));

        setLayout(new BorderLayout());
        add(panelFormulario, BorderLayout.CENTER);
    }
}
