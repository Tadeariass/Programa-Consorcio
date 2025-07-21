package GUI.GASTOS;

import Entidades.Edificio;
import Entidades.GASTOSEDIFICIO;
import GUI.PanelManagerCentral;
import Service.ServiceEdificio;
import Service.ServiceExpensas;
import Service.ServiceExpensas;
import exepcion.DAOException;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class FormularioCrearGasto extends JPanel {
    private ServiceEdificio serviceEdificio;
    private ServiceExpensas serviceGastos;
    private PanelManagerCentral panel;

    public FormularioCrearGasto(PanelManagerCentral panel) {
        this.panel = panel;
        this.serviceEdificio = new ServiceEdificio();
        this.serviceGastos = new ServiceExpensas();
        armarFormulario();
    }

    private void armarFormulario() {
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2));

        JLabel labelEdificio = new JLabel("Seleccione el Edificio:");
        JComboBox<Integer> comboEdificios = new JComboBox<>();

        JLabel labelDescripcion = new JLabel("Descripción:");
        JTextField textDescripcion = new JTextField();

        JLabel labelMonto = new JLabel("Monto:");
        JTextField textMonto = new JTextField();



        JButton buttonGuardar = new JButton("Guardar Gasto");
        JButton buttonVolver = new JButton("Volver");

        try {
            ArrayList<Edificio> edificios = serviceEdificio.buscarTodos();
            comboEdificios.addItem(null);
            for (Entidades.Edificio edificio : edificios) {
                comboEdificios.addItem(edificio.getId());
            }
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar edificios: " + e.getMessage());
        }

        panelFormulario.add(labelEdificio);
        panelFormulario.add(comboEdificios);
        panelFormulario.add(labelDescripcion);
        panelFormulario.add(textDescripcion);
        panelFormulario.add(labelMonto);
        panelFormulario.add(textMonto);

        panelFormulario.add(buttonGuardar);
        panelFormulario.add(buttonVolver);

        buttonGuardar.addActionListener(e -> {
            GASTOSEDIFICIO gasto = new GASTOSEDIFICIO();
            gasto.setIdEdificio((Integer) comboEdificios.getSelectedItem());
            gasto.setDescripcion(textDescripcion.getText());
            gasto.setValor(Double.parseDouble(textMonto.getText()));
            gasto.setFecha(LocalDate.now());

            serviceGastos.guardarGasto(gasto);
            JOptionPane.showMessageDialog(this, "Gasto guardado con éxito.");
            panel.mostrar(new FormularioIntGastos(panel));
        });

        buttonVolver.addActionListener(e -> panel.mostrar(new FormularioIntGastos(panel)));

        setLayout(new BorderLayout());
        add(panelFormulario, BorderLayout.CENTER);
    }
}

