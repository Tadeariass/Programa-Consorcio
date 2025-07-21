package GUI.DEPARTAMENTOS;

import Entidades.Departamento;
import Entidades.Edificio;
import Entidades.Pisos;
import GUI.PanelManagerCentral;
import Service.ServiceDepartamento;
import Service.ServiceEdificio;
import Service.ServiceException;
import Service.ServicePisos;
import exepcion.DAOException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class FormularioReporte extends JPanel {

    private ServicePisos servicePiso;
    private ServiceEdificio serviceEdificio;
    private ServiceDepartamento serviceDepartamento;
    private PanelManagerCentral panel;
    private JTable tableDepartamentos;
    private DefaultTableModel contenido;
    private JComboBox<Integer> comboEdificios;
    private JComboBox<String> comboPisos;

    public FormularioReporte(PanelManagerCentral panel) {
        this.panel = panel;
        this.servicePiso = new ServicePisos();
        this.serviceEdificio = new ServiceEdificio();
        this.serviceDepartamento = new ServiceDepartamento();
        armarFormularioReporte();
    }

    private void armarFormularioReporte() {
        setLayout(new BorderLayout());

        JPanel panelFiltros = new JPanel(new GridLayout(2, 2));
        JLabel labelEdificio = new JLabel("Seleccione el edificio:");
        comboEdificios = new JComboBox<>();
        JLabel labelPiso = new JLabel("Seleccione el piso:");
        comboPisos = new JComboBox<>();

        try {
            comboEdificios.removeAllItems();
            comboEdificios.addItem(null);
            ArrayList<Edificio> edificios = serviceEdificio.buscarTodos();
            for (Edificio edificio : edificios) {
                comboEdificios.addItem(edificio.getId());
            }
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar edificios: " + e.getMessage());
        }

        panelFiltros.add(labelEdificio);
        panelFiltros.add(comboEdificios);
        panelFiltros.add(labelPiso);
        panelFiltros.add(comboPisos);

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton buttonGrabar = new JButton("Normalizar Cuentas");
        JButton buttonVolver = new JButton("Volver");
        panelBotones.add(buttonGrabar);
        panelBotones.add(buttonVolver);

        contenido = new DefaultTableModel();
        contenido.addColumn("ID Departamento");
        contenido.addColumn("Piso");
        contenido.addColumn("Cuenta Crédito");
        contenido.addColumn("Cuenta a Favor");

        tableDepartamentos = new JTable(contenido);
        JScrollPane scrollPane = new JScrollPane(tableDepartamentos);

        add(panelFiltros, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

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
            Integer edificioId = (Integer) comboEdificios.getSelectedItem();
            String pisoId = (String) comboPisos.getSelectedItem();
            if (edificioId != null && pisoId != null) {
                try {
                    contenido.setRowCount(0);
                    ArrayList<Departamento> departamentos = serviceDepartamento.buscarTodos(edificioId, pisoId);

                    for (Departamento dpto : departamentos) {
                        Object[] rowData = {
                                dpto.getId_depto(),
                                dpto.getNumeropiso(),
                                dpto.getCuentacredito(),
                                dpto.getCuentaafavor()
                        };
                        contenido.addRow(rowData);
                    }

                    if (departamentos.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "No se encontraron departamentos para este piso.");
                    }
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al cargar departamentos: " + ex.getMessage());
                }
            }
        });

        buttonVolver.addActionListener(e -> panel.mostrar(new FormulariointDpto(panel)));

        buttonGrabar.addActionListener(e -> {
            Integer edificioId = (Integer) comboEdificios.getSelectedItem();
            String pisoId = (String) comboPisos.getSelectedItem();

            if (edificioId != null && pisoId != null) {
                try {
                    serviceDepartamento.normalizarCuentas(edificioId, pisoId);
                    JOptionPane.showMessageDialog(this, "Cuentas normalizadas exitosamente");

                    contenido.setRowCount(0);
                    ArrayList<Departamento> departamentos = serviceDepartamento.buscarTodos(edificioId, pisoId);
                    for (Departamento dpto : departamentos) {
                        Object[] rowData = {
                                dpto.getId_depto(),
                                dpto.getNumeropiso(),
                                dpto.getCuentacredito(),
                                dpto.getCuentaafavor()
                        };
                        contenido.addRow(rowData);
                    }
                } catch (ServiceException | DAOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al normalizar cuentas: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un edificio y un piso primero");
            }
        });
    }
}



