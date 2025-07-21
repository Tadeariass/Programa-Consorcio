package GUI.EDIFICIO;

import Entidades.Edificio;
import GUI.PanelManagerCentral;
import Service.ServiceEdificio;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioEdificio extends JPanel {
    ServiceEdificio serviceedificio;
    PanelManagerCentral panel;
    JPanel formularioedificio;
    JLabel jLabel_Calle;

    JTextField jTextField_Calle;

    JButton jButtonGrabar;

    public FormularioEdificio(PanelManagerCentral panel) {
        this.panel = panel;
        armarFormularioGuardaredificio();

    }

    public void armarFormularioGuardaredificio() {
        serviceedificio = new ServiceEdificio();
        formularioedificio = new JPanel();
        formularioedificio.setLayout(new GridLayout(2, 2));
        jLabel_Calle = new JLabel("calle edificio");
        jTextField_Calle = new JTextField(20);

        jButtonGrabar = new JButton("Grabar");


        formularioedificio.add(jLabel_Calle);
        formularioedificio.add(jTextField_Calle);
        formularioedificio.add(jButtonGrabar);

        jButtonGrabar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Edificio edificio = new Edificio();

                edificio.setCalle(jTextField_Calle.getText());
                try {
                    serviceedificio.guardar(edificio);
                } catch (ServiceException s) {
                    JOptionPane.showMessageDialog(null, "No se pudo guardar");
                }
                panel.mostrar(new Formularint(panel));
            }
        });

        setLayout(new BorderLayout());
        add(formularioedificio, BorderLayout.CENTER);
    }
}




