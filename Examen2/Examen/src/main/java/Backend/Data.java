/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import Entidades.Compra;
import java.awt.List;
import java.util.ArrayList;

/**
 *
 * @author busto
 */
public class Data {
    private ArrayList<Compra> compra;
    
    public Data(){
        this.compra = new ArrayList<>();

        Compra c=new Compra(0001, "Rejoj rolex",10000);
        Compra c1=new Compra(0002, "Reloj Casio",500);
        Compra c2=new Compra(0003, "Rejoj patito",20);
        Compra c3=new Compra(0004, "carro Camaro",50000);
        Compra c4=new Compra(0005, "Rejol Tesla",15000);
        Compra c5=new Compra(0006, "carro Montero",60000);

        this.compra.add(c);
        this.compra.add(c1);
        this.compra.add(c2);
        this.compra.add(c3);
        this.compra.add(c4);
        this.compra.add(c5);
    }
    public ArrayList<Compra> getCompra(){
        return this.compra;
    }
}
