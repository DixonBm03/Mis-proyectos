/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.examen;

import Presentation.Controlador;
import Presentation.Modelo;
import Presentation.Vista;

/**
 *
 * @author busto
 */
public class Examen {

    public static void main(String[] args) {
        Vista v=new Vista();
        Modelo m=new Modelo();
        Controlador c=new Controlador(v,m);
    }
}
