package GUI.EDIFICIO;

import GUI.DEPARTAMENTOS.FormulariointDpto;
import GUI.PanelManagerCentral;
import Service.ServiceEdificio;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EliminarEdificio extends JPanel {
    ServiceEdificio serviceedificio;
    PanelManagerCentral panel;
    JPanel formularioedificio;
    JLabel jLabel_ID;
    JTextField jTextField_ID;
    JButton jButtonEliminar;

    public EliminarEdificio(PanelManagerCentral panel) {
        this.panel = panel;
        armarFormularioEliminarEdificio();
    }

    public void armarFormularioEliminarEdificio() {
        serviceedificio = new ServiceEdificio();
        formularioedificio = new JPanel();
        formularioedificio.setLayout(new GridLayout(2, 2));
        jLabel_ID = new JLabel("ID del Edificio");
        jTextField_ID = new JTextField(20);
        jButtonEliminar = new JButton("Eliminar");

        formularioedificio.add(jLabel_ID);
        formularioedificio.add(jTextField_ID);
        formularioedificio.add(jButtonEliminar);

        jButtonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(jTextField_ID.getText());
                try {
                    serviceedificio.eliminar(id);
                    JOptionPane.showMessageDialog(null, "Edificio eliminado con éxito");
                } catch (ServiceException s) {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el edificio: " + s.getMessage());
                }
                panel.mostrar(new FormulariointDpto(panel));
            }
        });

        setLayout(new BorderLayout());
        add(formularioedificio, BorderLayout.CENTER);
    }

}
