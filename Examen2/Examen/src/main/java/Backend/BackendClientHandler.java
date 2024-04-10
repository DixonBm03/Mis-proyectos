/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BackendClientHandler implements Runnable {
    private Socket clienteSocket;
    private BackendServer backendServer;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    public BackendClientHandler(Socket clienteSocket, BackendServer backendServer) {
        this.clienteSocket = clienteSocket;
        this.backendServer = backendServer;
    }

    @Override
    public void run() {
        try {
            salida = new ObjectOutputStream(clienteSocket.getOutputStream());
            entrada = new ObjectInputStream(clienteSocket.getInputStream());

            System.out.println("Nuevo cliente conectado desde " + clienteSocket.getInetAddress());

            while (true) {
                // Implementar la lógica de comunicación con el cliente aquí
                // Puedes recibir mensajes desde el cliente y realizar las acciones correspondientes
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }

    // Método para enviar mensajes al cliente
    public void enviarMensaje(String mensaje) {
        try {
            salida.writeObject(mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cerrar la conexión con el cliente
    public void cerrarConexion() {
        try {
            salida.close();
            entrada.close();
            clienteSocket.close();
            System.out.println("Cliente desconectado desde " + clienteSocket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
