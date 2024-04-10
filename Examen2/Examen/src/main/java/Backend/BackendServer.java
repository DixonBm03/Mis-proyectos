/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import Entidades.Compra;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class BackendServer {
    private ArrayList<Compra> productosDisponibles;
    private ArrayList<BackendClientHandler> clientesConectados;
     private final int PORT = 0;
    public BackendServer() {
        productosDisponibles = new ArrayList<>();
        clientesConectados = new ArrayList<>();
        inicializarProductos();
    }
    public BackendServer(int PORT) {
        
    }
    private void inicializarProductos() {
        productosDisponibles.add(new Compra(1, "Producto1", 100));
        productosDisponibles.add(new Compra(2, "Producto2", 200));
    }

    // Método para iniciar el servidor y escuchar conexiones
    public void iniciarServidor() {
        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Puerto de escucha, puedes cambiarlo según tus necesidades

            System.out.println("El servidor se ha iniciado correctamente en el puerto 12345.");

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                BackendClientHandler clienteHandler = new BackendClientHandler(clienteSocket, this);
                clientesConectados.add(clienteHandler);
                Thread hiloCliente = new Thread(clienteHandler);
                hiloCliente.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al iniciar el servidor. Verifica que el puerto no esté ocupado.");
        }
    }

    public ArrayList<Compra> buscarProductos(String textoBusqueda) {
        ArrayList<Compra> productosEncontrados = new ArrayList<>();

    for (Compra producto : productosDisponibles) {
        if (producto.getNombre().toLowerCase().contains(textoBusqueda.toLowerCase())) {
            productosEncontrados.add(producto);
        }
    }

    notificarClientes("Búsqueda: Se encontraron productos que coinciden con '" + textoBusqueda + "'");

    return productosEncontrados;
    }

    
    public void comprarProducto(Compra productoComprado) {
        if (productosDisponibles.contains(productoComprado)) {
        productosDisponibles.remove(productoComprado);
        
        notificarClientes("Compra: Producto '" + productoComprado.getNombre() + "' comprado.");

    } else {
        notificarClientes("Compra: Producto '" + productoComprado.getNombre() + "' no disponible.");
    }
    }

    
    public void devolverProducto(Compra productoDevuelto) {
        productosDisponibles.add(productoDevuelto);
        notificarClientes("Devolución: Producto '" + productoDevuelto.getNombre() + "' devuelto.");
    }

   
    public void notificarClientes(String mensaje) {
        for (BackendClientHandler cliente : clientesConectados) {
            cliente.enviarMensaje(mensaje);
        }
    }
}
