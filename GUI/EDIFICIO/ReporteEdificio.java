package GUI.EDIFICIO;

import Entidades.Edificio;
import GUI.PanelManagerCentral;
import Service.ServiceEdificio;
import Service.ServiceException;
import exepcion.DAOException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReporteEdificio extends JPanel {
    private PanelManagerCentral panelManager;
    private JTable jTable;
    private DefaultTableModel contenido;
    private JScrollPane scrollPane;
    private JButton jButton1;

    public ReporteEdificio(PanelManagerCentral panelManager)  {
        this.panelManager = panelManager;
        armarTablaReporte();
    }
    public void armarTablaReporte() {
        setLayout(new BorderLayout());
        contenido= new DefaultTableModel();
        jTable = new JTable(contenido);
        scrollPane=new JScrollPane();
        scrollPane.setViewportView(jTable);
        contenido.addColumn("ID");
        contenido.addColumn("Calle");
        jButton1= new JButton("Volver");



        ServiceEdificio service = new ServiceEdificio();
        try {
            ArrayList<Edificio> edificios = service.buscarTodos();
            for(Edificio edificio:edificios)
            {
                Object [] fila= new Object[2];
                fila[0]=edificio.getId();
                fila[1]=edificio.getCalle();


                contenido.addRow(fila);
            }
            jButton1.addActionListener(e -> panelManager.mostrar(new Formularint(panelManager)));

        }




        catch ( DAOException e)
        {
            JOptionPane.showMessageDialog(null, "Error");
        }
        add(scrollPane, BorderLayout.CENTER);
        add(jButton1, BorderLayout.SOUTH);
    }
}
