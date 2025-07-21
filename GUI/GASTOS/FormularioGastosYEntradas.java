package GUI.GASTOS;

import Entidades.ENTRADAEDIFICIO;
import Entidades.Edificio;
import Entidades.GASTOSEDIFICIO;
import GUI.PanelManagerCentral;
import Service.ServiceEdificio;
import Service.ServiceExpensas;
import exepcion.DAOException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class FormularioGastosYEntradas extends JPanel {
    private JComboBox<Integer> comboEdificios;
    private JTable tablaGastos;
    private JTable tablaEntradas;
    private ServiceEdificio serviceEdificio;
    private ServiceExpensas serviceGastoEntrada;

    public FormularioGastosYEntradas(PanelManagerCentral panel) {
        serviceEdificio = new ServiceEdificio();
        serviceGastoEntrada = new ServiceExpensas();

        setLayout(new BorderLayout());

        comboEdificios = new JComboBox<>();
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Seleccione Edificio:"));
        panelSuperior.add(comboEdificios);
        add(panelSuperior, BorderLayout.NORTH);
        JPanel panelInferior = new JPanel(new GridLayout(2, 1));
        panelInferior.add(new JLabel("Gastos:"));
        panelInferior.add(new JLabel("Entradas:"));
        add(panelInferior, BorderLayout.SOUTH);
        tablaGastos = new JTable();
        tablaEntradas = new JTable();

        JPanel tablasPanel = new JPanel(new GridLayout(1, 2));
        tablasPanel.add(new JScrollPane(tablaGastos));
        tablasPanel.add(new JScrollPane(tablaEntradas));
        add(tablasPanel, BorderLayout.CENTER);

        try {
            ArrayList<Edificio> edificios = serviceEdificio.buscarTodos();
            for (Edificio edificio : edificios) {
                comboEdificios.addItem(edificio.getId());
            }
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar edificios: " + e.getMessage());
        }

        comboEdificios.addActionListener(e -> actualizarTablas());
    }

    private void actualizarTablas() {
        Integer id = (Integer) comboEdificios.getSelectedItem();
        if (id == null) return;

        try {

            ArrayList<GASTOSEDIFICIO> gastos = serviceGastoEntrada.obtenerGastosEdificio(id);
            ArrayList<ENTRADAEDIFICIO> entradas = serviceGastoEntrada.obtenerEntradasEdificio(id);


            DefaultTableModel modelGastos = new DefaultTableModel();
            modelGastos.addColumn("ID");
            modelGastos.addColumn("Descripción");
            modelGastos.addColumn("Monto");
            modelGastos.addColumn("Fecha");

            for (GASTOSEDIFICIO gasto : gastos) {
                modelGastos.addRow(new Object[]{
                        gasto.getIdEdificio(),
                        gasto.getDescripcion(),
                        gasto.getValor(),
                        gasto.getFecha()
                });
            }
            tablaGastos.setModel(modelGastos);


            DefaultTableModel modelEntradas = new DefaultTableModel();
            modelEntradas.addColumn("ID");
            modelEntradas.addColumn("Descripción");
            modelEntradas.addColumn("Monto");
            modelEntradas.addColumn("Fecha");

            for (ENTRADAEDIFICIO entrada : entradas) {
                modelEntradas.addRow(new Object[]{
                        entrada.getIdEdificio(),
                        entrada.getDescripcion(),
                        entrada.getValor(),
                        entrada.getFecha()
                });
            }
            tablaEntradas.setModel(modelEntradas);

        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }
}