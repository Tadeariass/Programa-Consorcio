package GUI.PISOS;

import Entidades.Edificio;
import Entidades.Pisos;
import GUI.EDIFICIO.Formularint;
import GUI.PanelManagerCentral;
import Service.ServiceEdificio;
import Service.ServicePisos;
import exepcion.DAOException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReportePisos extends JPanel {
    private PanelManagerCentral panelManager;
    private JTable jTable;
    private DefaultTableModel contenido;
    private JScrollPane scrollPane;
    private JButton botonVolver;
    private JComboBox<Integer> comboEdificios;
    private JComboBox<String> comboPisos;

    private ServicePisos servicePisos;
    private ServiceEdificio serviceEdificio;

    public ReportePisos(PanelManagerCentral panelManager) {
        this.panelManager = panelManager;
        this.serviceEdificio = new ServiceEdificio();
        this.servicePisos = new ServicePisos();

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());


        contenido = new DefaultTableModel();
        contenido.addColumn("ID_EDIFICIO");
        contenido.addColumn("ID_PISO");
        contenido.addColumn("GASTO EXTRA X PISO");

        jTable = new JTable(contenido);
        scrollPane = new JScrollPane(jTable);
        add(scrollPane, BorderLayout.CENTER);


        JPanel panelSuperior = new JPanel(new GridLayout(1, 2));
        JLabel label = new JLabel("Seleccione el código de Edificio:");
        comboEdificios = new JComboBox<>();
        comboEdificios.addItem(null);
        comboPisos = new JComboBox<>();

        cargartodosLosPisos();

        try {
            ArrayList<Edificio> edificios = serviceEdificio.buscarTodos();
            for (Edificio edificio : edificios) {
                comboEdificios.addItem(edificio.getId());
            }
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar edificios: " + e.getMessage());
        }

        panelSuperior.add(label);
        panelSuperior.add(comboEdificios);
        add(panelSuperior, BorderLayout.NORTH);


        comboEdificios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboPisos.removeAllItems();
                contenido.setRowCount(0);

                Integer edificioId = (Integer) comboEdificios.getSelectedItem();
                if (edificioId == null) {
                    cargartodosLosPisos();
                    return;
                }

                try {
                    ArrayList<Pisos> pisos = servicePisos.buscarTodos(edificioId);
                    if (pisos.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No se encontraron pisos para este edificio.");
                        return;
                    }
                    for (Pisos piso : pisos) {
                        Object[] fila = new Object[3];
                        fila[0] = piso.getId_edificio();
                        fila[1] = piso.getNumeropiso();
                        fila[2] = piso.getGastosxpiso();
                        contenido.addRow(fila);
                    }
                } catch (DAOException daoException) {
                    JOptionPane.showMessageDialog(null, "Error al cargar los pisos: " + daoException.getMessage());
                }
            }
        });


        botonVolver = new JButton("Volver");
        botonVolver.addActionListener(e -> panelManager.mostrar(new Formulariointpisos(panelManager)));
        add(botonVolver, BorderLayout.SOUTH);
    }

    private void cargartodosLosPisos() {
        try {
            ArrayList<Pisos> pisos = servicePisos.buscarTodosSinFiltro();
            contenido.setRowCount(0);

            if (pisos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron pisos registrados.");
                return;
            }

            for (Pisos piso : pisos) {
                Object[] fila = new Object[3];
                fila[0] = piso.getId_edificio();
                fila[1] = piso.getNumeropiso();
                fila[2] = piso.getGastosxpiso();
                contenido.addRow(fila);
            }
        } catch (DAOException daoException) {
            JOptionPane.showMessageDialog(null, "Error al cargar los pisos: " + daoException.getMessage());
        }
    }
}

