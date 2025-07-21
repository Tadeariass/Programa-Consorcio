package GUI.EDIFICIO;

import Entidades.Edificio;
import GUI.PanelManagerCentral;
import Service.ServiceEdificio;
import Service.ServiceException;
import exepcion.DAOException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Modificaredificios extends  JPanel {
    ServiceEdificio serviceedificio;
    PanelManagerCentral panel;
    JPanel formularioedificio;
    JPanel formularioedificio1;

    JLabel jLabel_Calle;
    JTextField jTextField_Calle;
    JButton jButtonGrabar;


    public Modificaredificios(PanelManagerCentral panel) {
        this.panel = panel;
        armarFormularioModificarEdificio();
    }

    public void armarFormularioModificarEdificio() {
        serviceedificio = new ServiceEdificio();
        formularioedificio = new JPanel();
        formularioedificio1 = new JPanel();

        try{
            ArrayList<Edificio> edificios = serviceedificio.buscarTodos();
            JComboBox<Integer> comboEdificios = new JComboBox<>();
            comboEdificios.addItem(null);
            for (Edificio edificio : edificios) {
                comboEdificios.addItem(edificio.getId());

            }
            formularioedificio.add(comboEdificios);
            formularioedificio1.setLayout(new GridLayout(3, 2));
            jLabel_Calle = new JLabel("calle edificio");
            jTextField_Calle = new JTextField( 20);

            jButtonGrabar = new JButton("Grabar");


            formularioedificio1.add(jLabel_Calle);
            formularioedificio1.add(jTextField_Calle);
            formularioedificio.add(formularioedificio1);
            formularioedificio.add(jButtonGrabar);

            comboEdificios.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int id_Edificio = comboEdificios.getSelectedIndex();
                    if (id_Edificio >= 0 && id_Edificio < edificios.size()) {
                        Edificio seleccionado = edificios.get(id_Edificio);
                        jTextField_Calle.setText(seleccionado.getCalle());
                    }
                }
            });

            jButtonGrabar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Edificio edificio = new Edificio();
                    int id = (Integer) comboEdificios.getSelectedItem();
                    edificio.setId(id);
                    edificio.setCalle(jTextField_Calle.getText());
                    try {
                        serviceedificio.guardar(edificio);
                    } catch (ServiceException s) {
                        JOptionPane.showMessageDialog(null, "No se pudo guardar");
                    }
                    panel.mostrar(new Formularint(panel));
                }
            });
        }
        catch(DAOException e){
            JOptionPane.showMessageDialog(null, "Error al cargar los edificios: " + e.getMessage());
            return;
        }
        setLayout(new BorderLayout());
        add(formularioedificio, BorderLayout.CENTER);
    }
}
