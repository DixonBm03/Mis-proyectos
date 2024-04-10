/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Persistencia.Modelo;
import Vista.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author busto
 */
public class Controlador implements ActionListener {
    VentanaPrincipal vp;
    Modelo model;
    LinkedList<TipoInstrumento> instrumento =new LinkedList<>();
    DefaultTableModel modelo;
    DefaultTableModel mode;
    public Controlador(VentanaPrincipal vp, Modelo model) {
        this.vp = vp;
        this.model = model;
    }
    
    
    
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
   