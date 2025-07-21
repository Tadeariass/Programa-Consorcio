package GUI.PISOS;

import Entidades.Edificio;
import Entidades.Pisos;
import GUI.EDIFICIO.Formularint;
import GUI.PanelManagerCentral;
import GUI.PantallaPrincipal;
import Service.ServiceEdificio;
import Service.ServicePisos;
import Service.ServiceException;
import exepcion.DAOException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EliminarPisos extends JPanel {
    ServicePisos servicePisos;
    PanelManagerCentral panel;
    JPanel formularioedificio;
    JLabel jLabel_ID;
    JTextField jTextField_ID;
    JLabel jLabel_IDpiso;
    JTextField jTextField_IDpiso;
    JButton jButtonEliminar;
    JButton jButtonVolver;
    ServiceEdificio serviceedificio;

    public EliminarPisos(PanelManagerCentral panel) {
        this.panel = panel;
        armarFormularioEliminarpiso();
    }

    public void armarFormularioEliminarpiso() {
        JComboBox<String> comboPisos=new JComboBox<>();
        serviceedificio= new ServiceEdificio();
        ArrayList<Edificio> edificios = serviceedificio.buscarTodos();
        JComboBox<Integer> comboEdificios = new JComboBox<>();
        comboEdificios.addItem(null);
        for (Edificio edificio : edificios) {
            comboEdificios.addItem(edificio.getId());
        }
        servicePisos = new ServicePisos();
        formularioedificio = new JPanel();
        formularioedificio.setLayout(new GridLayout(4, 4));
        jLabel_ID = new JLabel("ID del Edificio");

        jLabel_IDpiso = new JLabel("ID del Edificio");

        jButtonEliminar = new JButton("Eliminar");
        jButtonVolver= new JButton("Volver");
        jButtonVolver.addActionListener(e -> panel.mostrar(new Formulariointpisos (panel)));

        formularioedificio.add(jLabel_ID);
        formularioedificio.add(comboEdificios);
        formularioedificio.add(jLabel_IDpiso);
        formularioedificio.add(comboPisos);
        formularioedificio.add(jButtonEliminar);
        formularioedificio.add(jButtonVolver);

        comboEdificios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboPisos.removeAllItems();
                int edificioId;
                edificioId= (Integer) comboEdificios.getSelectedItem();
                try {
                    ArrayList<Pisos> pisos = servicePisos.buscarTodos(edificioId);
                    for (Pisos piso : pisos) {
                        comboPisos.addItem(piso.getNumeropiso());
                    }
                } catch (DAOException daoException) {
                    JOptionPane.showMessageDialog(null, "Error al cargar los pisos: " + daoException.getMessage());
                }
            }
        });


        jButtonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = (Integer) comboEdificios.getSelectedItem();
                String piso = String.valueOf(comboPisos.getSelectedItem());
                try {
                    servicePisos.eliminar(id,piso);
                    JOptionPane.showMessageDialog(null, "Edificio eliminado con éxito");
                } catch (ServiceException s) {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el edificio: " + s.getMessage());
                }
                panel.mostrar(new Formulariointpisos(panel));
            }
        });

        setLayout(new BorderLayout());
        add(formularioedificio, BorderLayout.CENTER);
    }

}

