/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentation;

import Entidades.Compra;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author busto
 */
public class Modelo {
    private ArrayList<Compra> compra;
    private DefaultTableModel dataModel;
    private DefaultTableModel misComprasDataModel;  
    private ArrayList<Compra> copiaOculta;
    public Modelo() {
        compra = new ArrayList<>();
        dataModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Precio"}, 0);
        misComprasDataModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Precio"}, 0);
        copiaOculta = new ArrayList<>(compra);
    }
    public void inicializarDatosQuemados() {
        Compra c = new Compra(2222, "Reloj Rolex", 10000);
        Compra c1 = new Compra(3333, "Reloj Casio", 500);
        Compra c2 = new Compra(4444, "Reloj Patito", 20);
        Compra c3 = new Compra(5555, "Automovil Camaro", 50000);
        Compra c4 = new Compra(6666, "Automovil Tesla", 15000);
        Compra c5 = new Compra(7777, "Automovil Geely", 60000);

        compra.add(c);
        compra.add(c1);
        compra.add(c2);
        compra.add(c3);
        compra.add(c4);
        compra.add(c5);

        // Agregar datos quemados al modelo de la tabla de compras disponibles
        for (Compra item : compra) {
            dataModel.addRow(new Object[]{item.getId(), item.getNombre(), item.getPrecio()});
        }
    }
    public DefaultTableModel getDataModel() {
        return dataModel;
    }

    public DefaultTableModel getMisComprasDataModel() {
        return misComprasDataModel;
    }
    public void comprarItem(int filaSeleccionada) {
        
        Compra compraSeleccionada = compra.get(filaSeleccionada);
        misComprasDataModel.addRow(new Object[]{compraSeleccionada.getId(), compraSeleccionada.getNombre(), compraSeleccionada.getPrecio()});

        
    }
    public ArrayList<Compra> getCompras() {
        return compra;
    }
    public DefaultTableModel getMisCompras() {
        return misComprasDataModel;
    }
}
