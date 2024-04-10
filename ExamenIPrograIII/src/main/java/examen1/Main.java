package examen1;

import examen1.Logic.Service;
import examen1.Presentation.Controller;
import examen1.Presentation.Model;
import examen1.Presentation.View;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//Dixon Bustos Medina
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ex) {}
        window = new JFrame();

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Service.instance().stop();
            }
        });
        Model modelo = new Model();
        View view = new View();
        controller = new Controller(view,modelo);

        window.setSize(900,450);
        window.setResizable(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //window.setIconImage();
        window.setTitle("SIACT: Sistema de Activos");
        window.setContentPane(view.getPanel());
        window.setVisible(true);
    }
    public static JFrame window;
    public static Controller controller;
    public static int MODE_EDIT=1;
    public static int MODE_CREATE=2;
}