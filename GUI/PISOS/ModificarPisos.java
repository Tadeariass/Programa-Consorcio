package GUI.PISOS;

import Entidades.Edificio;
import Entidades.Pisos;
import GUI.DEPARTAMENTOS.ModificarDepartamento;
import GUI.PanelManagerCentral;
import GUI.PantallaPrincipal;
import Service.ServiceEdificio;
import Service.ServiceException;
import Service.ServicePisos;
import exepcion.DAOException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ModificarPisos extends JPanel {
    ServiceEdificio serviceedificio;
    ServicePisos servicePisos;
    PanelManagerCentral panel;
    PantallaPrincipal pantallaPrincipal;
    JPanel formularioedificio;

    JLabel gastoextra;
    JTextField gastoextraPisos;
    JLabel numeroPisos;
    JTextField numeroPiso;
    JLabel elegirpiso;
    JLabel eligiredificio;
    JButton jButtonGrabar;
    JButton jButtonVolver;
    int idViejo;
    String idpiso;

    private ArrayList<Pisos> listaPisos = new ArrayList<>();

    public ModificarPisos(PanelManagerCentral panel) {
        this.panel = panel;
        armarFormularioModificarEdificio();
    }

    public void armarFormularioModificarEdificio() {
        serviceedificio = new ServiceEdificio();
        servicePisos = new ServicePisos();
        formularioedificio = new JPanel();

        JComboBox<String> comboPisos = new JComboBox<>();

        try {
            ArrayList<Edificio> edificios = serviceedificio.buscarTodos();
            JComboBox<Integer> comboEdificios = new JComboBox<>();
            comboEdificios.addItem(null);
            for (Edificio edificio : edificios) {
                comboEdificios.addItem(edificio.getId());
            }

            formularioedificio.setLayout(new GridLayout(6, 2));
            elegirpiso = new JLabel("Seleccione el número del Piso:");
            eligiredificio = new JLabel("Seleccione el código del Edificio:");
            gastoextra = new JLabel("Modificar el gasto extra (%):");
            numeroPisos = new JLabel("Modificar el número del Piso:");

            gastoextraPisos = new JTextField();
            numeroPiso = new JTextField();
            jButtonGrabar = new JButton("Grabar");
            jButtonVolver = new JButton("Volver");

            formularioedificio.add(eligiredificio);
            formularioedificio.add(comboEdificios);
            formularioedificio.add(elegirpiso);
            formularioedificio.add(comboPisos);
            formularioedificio.add(gastoextra);
            formularioedificio.add(gastoextraPisos);
            formularioedificio.add(numeroPisos);
            formularioedificio.add(numeroPiso);
            formularioedificio.add(jButtonGrabar);
            formularioedificio.add(jButtonVolver);


            jButtonVolver.addActionListener(e -> panel.mostrar(new Formulariointpisos(panel)));

            // Combo edificios
            comboEdificios.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    comboPisos.removeAllItems();
                    Integer edificioId = (Integer) comboEdificios.getSelectedItem();
                    if (edificioId == null) return;
                    try {
                        listaPisos = servicePisos.buscarTodos(edificioId); // ✅ lista global
                        for (Pisos piso : listaPisos) {
                            comboPisos.addItem(piso.getNumeropiso());

                        }
                    } catch (DAOException daoException) {
                        JOptionPane.showMessageDialog(null, "Error al cargar los pisos: " + daoException.getMessage());
                    }
                }
            });


            comboPisos.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Integer deptoId = (Integer) comboEdificios.getSelectedItem();
                    String piso = (String) comboPisos.getSelectedItem();
                    if (deptoId != 0 && piso != null) {
                        try {
                            int index = comboPisos.getSelectedIndex();
                            if (index >= 0 && index < listaPisos.size()) {
                                Pisos pisoSeleccionado = listaPisos.get(index);

                                gastoextraPisos.setText(String.valueOf(pisoSeleccionado.getGastosxpiso()));
                                numeroPiso.setText(pisoSeleccionado.getNumeropiso());
                            }

                        } catch (DAOException ex) {
                            JOptionPane.showMessageDialog(ModificarPisos.this, "Error al cargar datos del departamento: " + ex.getMessage());
                        }

                    }
                }});

            jButtonGrabar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Pisos piso = new Pisos();
                    idViejo = (Integer) comboEdificios.getSelectedItem();
                    idpiso = String.valueOf(comboPisos.getSelectedItem());
                    try {
                        piso.setGastosxpiso(Float.parseFloat(gastoextraPisos.getText()));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Error: el gasto extra debe ser un número válido.");
                        return;
                    }
                    piso.setNumeropiso(numeroPiso.getText());

                    try {
                        servicePisos.modificar(piso, idpiso, idViejo);
                        JOptionPane.showMessageDialog(null, "Piso modificado correctamente.");
                        panel.mostrar(new PantallaPrincipal(panel));
                    } catch (ServiceException s) {
                        JOptionPane.showMessageDialog(null, "No se pudo guardar: " + s.getMessage());
                    }
                }
            });

        } catch (DAOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los edificios: " + e.getMessage());
            return;
        }

        setLayout(new BorderLayout());
        add(formularioedificio, BorderLayout.CENTER);
    }
}
