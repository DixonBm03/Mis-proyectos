/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentation;

import Entidades.Compra;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author busto
 */
public class Controlador implements ActionListener{
    private Vista view;
    private Modelo mod;

    public Controlador(Vista view, Modelo mod) {
        mod.inicializarDatosQuemados();
        view.jTableMisCompras.setModel(mod.getMisComprasDataModel());
        view.jTableBuscar.setModel(mod.getDataModel());

        this.view = view;
        this.mod = mod;
        view.setVisible(true);
        view.setLocationRelativeTo(null);
        this.view.getJbtnBuscar().addActionListener(this);
        this.view.getJbtnComprar().addActionListener(this);
        this.view.getJbtnDevolver().addActionListener(this);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(view.getJbtnBuscar())) {
            buscarEnTabla();
        }
        if (e.getSource().equals(view.getJbtnComprar())) {
            comprarItemSeleccionado();
        }
        else if (e.getSource().equals(view.getJbtnDevolver())) {
            devolverItemSeleccionado();
        }
    }
    private void comprarItemSeleccionado() {
        int filaSeleccionada = view.getjTableBuscar().getSelectedRow();
        if (filaSeleccionada != -1) {
            try {
                int filaReal = view.getjTableBuscar().convertRowIndexToModel(filaSeleccionada);
                Compra compraSeleccionada = mod.getCompras().get(filaReal);
                if (compraSeleccionada != null) {
                    DefaultTableModel modeloMisCompras = (DefaultTableModel) view.getjTableMisCompras().getModel();
                    modeloMisCompras.addRow(new Object[]{compraSeleccionada.getId(), compraSeleccionada.getNombre(), compraSeleccionada.getPrecio()});
                    DefaultTableModel modeloTablaBuscar = (DefaultTableModel) view.getjTableBuscar().getModel();
                    modeloTablaBuscar.removeRow(filaReal);
                    JOptionPane.showMessageDialog(view, "Compra realizada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view, "No se pudo obtener la compra seleccionada", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Error al realizar la compra", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace(); 
            }
        } else {
            JOptionPane.showMessageDialog(view, "Selecciona un artículo para comprar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void buscarEnTabla() {
        try {
            String filtro = view.getJtxtNombre().getText().trim(); 
            DefaultTableModel modeloTablaBuscar = (DefaultTableModel) view.getjTableBuscar().getModel();
            if (filtro.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Por favor, ingrese un nombre para buscar", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (modeloTablaBuscar != null) {
                TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(modeloTablaBuscar);
                view.getjTableBuscar().setRowSorter(rowSorter);
                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + filtro, 1)); 
                if (view.getjTableBuscar().getRowCount() == 0) {
                    JOptionPane.showMessageDialog(view, "No se encontraron resultados para la búsqueda", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view, "Búsqueda realizada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(view, "Error al acceder al modelo de la tabla de búsqueda", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al realizar la búsqueda", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();  
        }
    }
    private void devolverItemSeleccionado() {
        try {
            int filaSeleccionada = view.getjTableMisCompras().getSelectedRow();
            if (filaSeleccionada != -1) {
                DefaultTableModel modeloMisCompras = (DefaultTableModel) view.getjTableMisCompras().getModel();
                int filaReal = view.getjTableMisCompras().convertRowIndexToModel(filaSeleccionada);
                Object id = modeloMisCompras.getValueAt(filaReal, 0);
                Object nombre = modeloMisCompras.getValueAt(filaReal, 1);
                Object precio = modeloMisCompras.getValueAt(filaReal, 2);
                modeloMisCompras.removeRow(filaReal);
                DefaultTableModel modeloTablaBuscar = (DefaultTableModel) view.getjTableBuscar().getModel();
                modeloTablaBuscar.addRow(new Object[]{id, nombre, precio});
                JOptionPane.showMessageDialog(view, "Compra devuelta con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, "Selecciona una compra para devolver", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al devolver la compra", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); 
        }
    }
    
}
