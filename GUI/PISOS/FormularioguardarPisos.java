package GUI.PISOS;

import Entidades.Edificio;
import Entidades.Pisos;
import GUI.PanelManagerCentral;
import Service.ServiceEdificio;
import Service.ServiceException;
import Service.ServicePisos;
import exepcion.DAOException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormularioguardarPisos extends JPanel{

   ServicePisos servicepiso;
   ServiceEdificio serviceEdificio;
    PanelManagerCentral panel;
    JPanel formularioedificio;
    JLabel jLabel_Calle;
    JLabel jLabel_Gastoextra;
    JLabel jLabel_mostraredificio;
    JTextField jTextField_Calle;
    JTextField jTextField_Gastoextra;
    JButton jButtonGrabar;
    JButton jButtonVolver;

    public FormularioguardarPisos(PanelManagerCentral panel) {
        this.panel = panel;
        armarFormularioGuardaredificio();

    }

    public void armarFormularioGuardaredificio() {
        servicepiso = new ServicePisos();
        serviceEdificio = new ServiceEdificio();
        formularioedificio = new JPanel();
        JComboBox<String> comboPisos=new JComboBox<>();
        try{
            ArrayList<Edificio> edificios = serviceEdificio.buscarTodos();
            JComboBox<Integer> comboEdificios = new JComboBox<>();
            comboEdificios.addItem(null);
            for (Edificio edificio : edificios) {
                comboEdificios.addItem(edificio.getId());
            }

        formularioedificio.setLayout(new GridLayout(4, 2));
            jLabel_mostraredificio = new JLabel("Selecciona el edificio");
        jLabel_Calle = new JLabel("Numero De Piso:");
        jTextField_Calle = new JTextField(20);
        jLabel_Gastoextra= new JLabel("Gasto Extra");
        jTextField_Gastoextra=new JTextField();

        jButtonGrabar = new JButton("Grabar");
        jButtonVolver= new JButton("Volver");
            jButtonVolver.addActionListener(e -> panel.mostrar(new Formulariointpisos (panel)));
            formularioedificio.add(jLabel_mostraredificio);
            formularioedificio.add(comboEdificios);
        formularioedificio.add(jLabel_Calle);
        formularioedificio.add(jTextField_Calle);
        formularioedificio.add(jLabel_Gastoextra);
        formularioedificio.add(jTextField_Gastoextra);
        formularioedificio.add(jButtonGrabar);
        formularioedificio.add(jButtonVolver);
        jButtonGrabar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pisos piso = new Pisos();
                int id = (Integer) comboEdificios.getSelectedItem();
                piso.setId_edificio(id);
                piso.setNumeropiso(jTextField_Calle.getText());
                piso.setGastosxpiso(Float.parseFloat(jTextField_Gastoextra.getText()));
                try {
                    servicepiso.guardar(piso);
                } catch (ServiceException s) {
                    JOptionPane.showMessageDialog(null, "No se pudo guardar");
                }

                panel.mostrar(new Formulariointpisos (panel));
            }
        });

        setLayout(new BorderLayout());
        add(formularioedificio, BorderLayout.CENTER);
    }catch (DAOException daoException) {
            JOptionPane.showMessageDialog(null, "Error al cargar los pisos: " + daoException.getMessage());
        }
    }
}
