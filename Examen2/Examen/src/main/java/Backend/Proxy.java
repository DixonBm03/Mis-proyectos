/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import Entidades.Compra;
import Presentation.Controlador;

/**
 *
 * @author busto
 */
public class Proxy implements ProxyInterface {

    private final ProxyInterface sujetoReal; //Cliente

    public Proxy(int puerto, Controlador c) {
        sujetoReal = new Cliente(puerto, c);
        Thread clientThread = new Thread((Cliente)sujetoReal);
        clientThread.start();
    }

    @Override
    public void operacion(int operacion, Compra c) {
        sujetoReal.operacion(operacion, c);
    }

}

