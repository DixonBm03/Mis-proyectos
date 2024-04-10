/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author busto
 */
public class MainBackend {
    public static void main(String[] args) {
        BackendServer backendServer=new BackendServer();
        backendServer.iniciarServidor();
        BackendServer b=new BackendServer(5000);
        Thread serverThread = new Thread((Runnable) b);
        serverThread.start();
    }
}
