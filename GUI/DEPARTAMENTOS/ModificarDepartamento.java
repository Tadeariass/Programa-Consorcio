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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ModificarDepartamento extends JPanel {
    private ServiceDepartamento serviceDepartamento;
    private ServiceEdificio serviceEdificio;
    private ServicePisos servicePisos;
    private PanelManagerCentral panel;

    private JComboBox<Integer> comboEdificios;
    private JComboBox<String> comboPisos;
    private JComboBox<String> comboDepartamentos;
    private ArrayList<Departamento> departamentos;
    private JTextField txtCuentaCredito;
    private JTextField txtCuentaFavor;
    private JButton btnGrabar;
    private JButton btnVolver;
    private String idDeptoAnterior;
    private String numeroPisoAnterior;

    public ModificarDepartamento(PanelManagerCentral panel) {
        this.panel = panel;
        this.serviceDepartamento = new ServiceDepartamento();
        this.serviceEdificio = new ServiceEdificio();
        this.servicePisos = new ServicePisos();
        armarFormularioModificarDepartamento();
    }

    private void armarFormularioModificarDepartamento() {
        setLayout(new BorderLayout());
        JPanel panelSuperior = new JPanel(new GridLayout(3, 2));
        JPanel panelCentral = new JPanel(new GridLayout(2, 2));
        JPanel panelInferior = new JPanel(new FlowLayout());

        panelSuperior.add(new JLabel("Edificio:"));
        comboEdificios = new JComboBox<>();
        panelSuperior.add(comboEdificios);

        panelSuperior.add(new JLabel("Piso:"));
        comboPisos = new JComboBox<>();
        panelSuperior.add(comboPisos);

        panelSuperior.add(new JLabel("Departamento:"));
        comboDepartamentos = new JComboBox<>();
        panelSuperior.add(comboDepartamentos);

        panelCentral.add(new JLabel("Cuenta Crédito:"));
        txtCuentaCredito = new JTextField();
        panelCentral.add(txtCuentaCredito);
        panelCentral.add(new JLabel("Cuenta a Favor:"));
        txtCuentaFavor = new JTextField();
        panelCentral.add(txtCuentaFavor);

        btnGrabar = new JButton("Guardar Cambios");
        btnVolver = new JButton("Volver");
        panelInferior.add(btnGrabar);
        panelInferior.add(btnVolver);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        try {
            ArrayList<Edificio> edificios = serviceEdificio.buscarTodos();
            for (Edificio edificio : edificios) {
                comboEdificios.addItem(edificio.getId());
            }
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar edificios: " + e.getMessage());
        }

        comboEdificios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboPisos.removeAllItems();
                Integer edificioId = (Integer) comboEdificios.getSelectedItem();
                if (edificioId != null) {
                    try {
                        ArrayList<Pisos> pisos = servicePisos.buscarTodos(edificioId);
                        for (Pisos piso : pisos) {
                            comboPisos.addItem(piso.getNumeropiso());
                        }
                    } catch (DAOException ex) {
                        JOptionPane.showMessageDialog(ModificarDepartamento.this, "Error al cargar pisos: " + ex.getMessage());
                    }
                }
            }
        });

        comboPisos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboDepartamentos.removeAllItems();
                Integer edificioId = (Integer) comboEdificios.getSelectedItem();
                String piso = (String) comboPisos.getSelectedItem();
                if (edificioId != null && piso != null) {
                    try {
                        departamentos = serviceDepartamento.buscarTodos(edificioId, piso);
                        for (Departamento depto : departamentos) {
                            comboDepartamentos.addItem(depto.getId_depto());
                        }
                    } catch (DAOException ex) {
                        JOptionPane.showMessageDialog(ModificarDepartamento.this, "Error al cargar departamentos: " + ex.getMessage());
                    }
                }
            }
        });

        comboDepartamentos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String deptoId = (String) comboDepartamentos.getSelectedItem();
                String piso = (String) comboPisos.getSelectedItem();
                if (deptoId != null && piso != null && departamentos != null) {

                    for (Departamento depto : departamentos) {
                        if (depto.getId_depto().equals(deptoId)) {
                            txtCuentaCredito.setText(String.valueOf(depto.getCuentacredito()));
                            txtCuentaFavor.setText(String.valueOf(depto.getCuentaafavor()));
                            idDeptoAnterior = depto.getId_depto();
                            numeroPisoAnterior = depto.getNumeropiso();
                            break;
                        }
                    }
                }
            }
        });


        btnGrabar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idDeptoAnterior == null || numeroPisoAnterior == null) {
                    JOptionPane.showMessageDialog(ModificarDepartamento.this, "Seleccione un departamento primero");
                    return;
                }

                Departamento depto = new Departamento();
                depto.setNumeropiso((String) comboPisos.getSelectedItem());
                depto.setId_depto((String) comboDepartamentos.getSelectedItem());
                depto.setCuentacredito(Double.parseDouble(txtCuentaCredito.getText()));
                depto.setCuentaafavor(Double.parseDouble(txtCuentaFavor.getText()));

                try {
                    serviceDepartamento.modificarDepartamento(depto, idDeptoAnterior, numeroPisoAnterior);
                    JOptionPane.showMessageDialog(ModificarDepartamento.this, "Departamento modificado exitosamente");
                    panel.mostrar(new FormulariointDpto(panel));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ModificarDepartamento.this, "Los valores de las cuentas deben ser numéricos");
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(ModificarDepartamento.this, "Error al modificar departamento: " + ex.getMessage());
                }
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.mostrar(new FormulariointDpto(panel));
            }
        });
    }
}