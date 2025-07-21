package GUI.DEPARTAMENTOS;

import Entidades.Departamento;
import Entidades.Edificio;
import Entidades.Pisos;
import GUI.PISOS.Formulariointpisos;
import GUI.PanelManagerCentral;
import Service.ServiceDepartamento;
import Service.ServiceEdificio;
import Service.ServiceException;
import Service.ServicePisos;
import exepcion.DAOException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FormularioEliminarDpto extends JPanel {

    private ServicePisos servicePiso;
    private ServiceEdificio serviceEdificio;
    private ServiceDepartamento serviceDepartamento;
    private PanelManagerCentral panel;
    JLabel labelEdificio;
    JLabel labelPiso;
    JLabel labelNumDpto;
    JTextField textNumDpto;
    JTextField textcuentacredito;
    JLabel labeLcuentacredito;
    JTextField textcuentaafavor;
    JLabel labeLcuentaafavor;
    JButton buttonGrabar;
    JButton buttonVolver;

    public FormularioEliminarDpto(PanelManagerCentral panel) {
        this.panel = panel;
        this.servicePiso = new ServicePisos();
        this.serviceEdificio = new ServiceEdificio();
        this.serviceDepartamento = new ServiceDepartamento();
        armarFormularioGuardarDpto();
    }

    private void armarFormularioGuardarDpto() {
        JPanel formularioDpto = new JPanel(new GridLayout(7, 2));

        labelEdificio = new JLabel("Seleccione el edificio:");
        JComboBox<Integer> comboEdificios = new JComboBox<>();

        labelPiso = new JLabel("Seleccione el piso:");
        JComboBox<String> comboPisos = new JComboBox<>();

        labelNumDpto = new JLabel("Seleccione NUmero de Departamento:");
        JComboBox<String> comboDpto = new JComboBox<>();


        buttonGrabar = new JButton("Eliminar Departamento");
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
        formularioDpto.add(buttonGrabar);
        formularioDpto.add(buttonVolver);

        comboEdificios.addActionListener(e -> {
            comboPisos.removeAllItems();
            Integer edificioId = (Integer) comboEdificios.getSelectedItem();

                try {
                    ArrayList<Pisos> pisos = servicePiso.buscarTodos(edificioId);
                    for (Pisos piso : pisos) {
                        comboPisos.addItem(piso.getNumeropiso());
                    }
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al cargar pisos: " + ex.getMessage());
                }

        });

        comboPisos.addActionListener(e -> {
            comboDpto.removeAllItems();
            Integer edificioId = (Integer) comboEdificios.getSelectedItem();
            String pisoId = (String) comboPisos.getSelectedItem();
                try {
                    ArrayList<Departamento> departamentos = serviceDepartamento.buscarTodos(edificioId,pisoId);
                    for (Departamento dpto : departamentos) {
                        System.out.println(dpto.getId_depto());
                        comboDpto.addItem(dpto.getId_depto());
                    }
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al cargar Departamentos: " + ex.getMessage());
                }

        });



        buttonVolver.addActionListener(e -> panel.mostrar(new FormulariointDpto(panel)));

        buttonGrabar.addActionListener(e -> {
            try {

                Departamento dpto = new Departamento();
                dpto.setNumeropiso((String) comboPisos.getSelectedItem());
                dpto.setId_depto((String) (comboDpto.getSelectedItem()));


//int id,String piso, int dpto
                Integer edificioId = (Integer) comboEdificios.getSelectedItem();
                String pisoId = (String) comboPisos.getSelectedItem();
                serviceDepartamento.eliminar(edificioId,pisoId,dpto.getId_depto());
                JOptionPane.showMessageDialog(this, "Departamento ELIMINADO exitosamente");
                panel.mostrar(new FormulariointDpto(panel));

            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(this, "Error al ELIMINAR departamento: " + ex.getMessage());
            }
        });

        setLayout(new BorderLayout());
        add(formularioDpto, BorderLayout.CENTER);
    }

}
