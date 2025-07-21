package GUI;

import javax.swing.*;
import java.awt.*;

public class PanelManagerCentral {
    PantallaPrincipal pantallaPrincipal;
    JFrame jFrame;
    JPanel formulariointeraccion;


    public PanelManagerCentral()
    {
        jFrame=new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Edificio Management");
        jFrame.setSize(600, 400);
        pantallaPrincipal=new PantallaPrincipal (this);
        mostrar(pantallaPrincipal);
}
    public void mostrar(JPanel panel)
    {
        jFrame.getContentPane().removeAll();
        jFrame.getContentPane().add(BorderLayout.SOUTH,panel);
        jFrame.getContentPane().validate();
        jFrame.getContentPane().repaint();
        jFrame.show();
        jFrame.pack();
    }
    public void muestrar(int codigoPantalla)
    {
        switch (codigoPantalla)
        {
            case 1:
                mostrar(pantallaPrincipal);
            case 2:
                mostrar(formulariointeraccion);
        }
    }
}
