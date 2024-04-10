/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import Entidades.Compra;
import Presentation.Controlador;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author busto
 */
public class Cliente implements Runnable, ProxyInterface {

    private Controlador control;
    int PUERTO;
    final String HOST = "127.0.0.1";
    DataInputStream reader;
    DataOutputStream writer;
    Socket socket;

    public Cliente(int puerto, Controlador c) {
        PUERTO = puerto;
        control = c;
    }

    public void disconnect() { //1
        try {
            socket.close();
        } catch (IOException ex) {
        }
    }

    @Override
    public void run() {
        try {
            socket = new Socket(HOST, PUERTO);
            reader = new DataInputStream(socket.getInputStream());
            writer = new DataOutputStream(socket.getOutputStream());
            //loadDataBase();
            ObjectInputStream objectReader = new ObjectInputStream(socket.getInputStream());

            System.out.println("CONECTADO");
            while (true) {

                String msg = reader.readUTF(); //LE LLEGA UN MENSAJE
                Object obj = objectReader.readObject();
            }

        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("CLIENTE DESCONECTADO");
        }
    }

    @Override
    public void operacion(int numero, Compra c) {
    switch(numero){
            case 1: disconnect(); break;
            case 2: run(); break;
            default: break;
        }
    
    }
}

