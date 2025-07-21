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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class FormlarioReporteOcupantes extends JPanel {

    private ServicePisos servicePiso;
    private ServiceEdificio serviceEdificio;
    private ServiceDepartamento serviceDepartamento;
    private ServiceOcupantes serviceOcupantes;
    private PanelManagerCentral panel;
    private JTable tableOcupantes;
    private DefaultTableModel contenido;
    private JComboBox<Integer> comboEdificios;
    private JComboBox<String> comboPisos;
    private JComboBox<String> comboDepartamentos;

    public FormlarioReporteOcupantes(PanelManagerCentral panel) {
        this.panel = panel;
        this.servicePiso = new ServicePisos();
        this.serviceEdificio = new ServiceEdificio();
        this.serviceDepartamento = new ServiceDepartamento();
        this.serviceOcupantes = new ServiceOcupantes();

        armarFormularioReporte();
    }

    private void armarFormularioReporte() {
        setLayout(new BorderLayout());


        JPanel panelFiltros = new JPanel(new GridLayout(2, 3));
        JLabel labelEdificio = new JLabel("Seleccione el edificio:");
        comboEdificios = new JComboBox<>();
        JLabel labelPiso = new JLabel("Seleccione el piso:");
        comboPisos = new JComboBox<>();
        JLabel labelDepto = new JLabel("Seleccione departamento:");
        comboDepartamentos = new JComboBox<>();

        comboEdificios.addItem(null);
        comboPisos.addItem(null);
        comboDepartamentos.addItem(null);

        try {
            ArrayList<Edificio> edificios = serviceEdificio.buscarTodos();
            for (Edificio edificio : edificios) {
                comboEdificios.addItem(edificio.getId());
            }
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar edificios: " + e.getMessage());
        }

        panelFiltros.add(labelEdificio);
        panelFiltros.add(labelPiso);
        panelFiltros.add(labelDepto);
        panelFiltros.add(comboEdificios);
        panelFiltros.add(comboPisos);
        panelFiltros.add(comboDepartamentos);


        JPanel panelBotones = new JPanel(new FlowLayout());

        JButton buttonVolver = new JButton("Volver");

        panelBotones.add(buttonVolver);

        contenido = new DefaultTableModel();
        contenido.addColumn("Departamento");
        contenido.addColumn("Nombre");
        contenido.addColumn("Edad");

        tableOcupantes = new JTable(contenido);
        JScrollPane scrollPane = new JScrollPane(tableOcupantes);

        add(panelFiltros, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);


        comboEdificios.addActionListener(e -> {
            comboPisos.removeAllItems();
            comboDepartamentos.removeAllItems();
            comboPisos.addItem(null);
            comboDepartamentos.addItem(null);

            Integer edificioId = (Integer) comboEdificios.getSelectedItem();
            if (edificioId != null) {
                try {

                    ArrayList<Pisos> pisos = servicePiso.buscarTodos(edificioId);
                    for (Pisos piso : pisos) {
                        comboPisos.addItem(piso.getNumeropiso());
                    }


                    cargarOcupantes(edificioId, null, null);

                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al cargar pisos: " + ex.getMessage());
                }
            }
        });

        comboPisos.addActionListener(e -> {
            comboDepartamentos.removeAllItems();
            comboDepartamentos.addItem(null);

            Integer edificioId = (Integer) comboEdificios.getSelectedItem();
            String pisoId = (String) comboPisos.getSelectedItem();

            if (edificioId != null && pisoId != null) {
                try {

                    ArrayList<Departamento> departamentos = serviceDepartamento.buscarTodos(edificioId, pisoId);
                    for (Departamento dpto : departamentos) {
                        comboDepartamentos.addItem(dpto.getId_depto());
                    }


                    cargarOcupantes(edificioId, pisoId, null);

                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al cargar departamentos: " + ex.getMessage());
                }
            }
        });

        comboDepartamentos.addActionListener(e -> {
            Integer edificioId = (Integer) comboEdificios.getSelectedItem();
            String pisoId = (String) comboPisos.getSelectedItem();
            String deptoId = (String) comboDepartamentos.getSelectedItem();

            if (edificioId != null && pisoId != null && deptoId != null) {

                cargarOcupantes(edificioId, pisoId, deptoId);
            }
        });

        buttonVolver.addActionListener(e -> panel.mostrar(new FormularioIntOcupante(panel)));

    }

    private void cargarOcupantes(Integer edificioId, String pisoId, String deptoId) {
        try {
            contenido.setRowCount(0);

            ArrayList<Ocupantes> ocupantes = serviceOcupantes.BuscarOcupantes(edificioId, pisoId, deptoId);

            for (Ocupantes ocupante : ocupantes) {
                Object[] rowData = {

                        ocupante.getDeptonombre(),
                        ocupante.getNombre(),
                        ocupante.getEdad()
                };
                contenido.addRow(rowData);
            }

            if (ocupantes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron ocupantes con los filtros seleccionados.");
            }
        } catch (ServiceException | DAOException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar ocupantes: " + ex.getMessage());
        }
    }
}

