package server;

import server.rmi.ProductService;
import server.rmi.ProductServiceImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {
            // Démarrer le registre RMI
            LocateRegistry.createRegistry(1099);
            System.out.println("Registre RMI démarré.");

            // Enregistrer le service
            ProductService productService = new ProductServiceImpl();
            Naming.rebind("rmi://localhost/ProductService", productService);
            System.out.println("Serveur RMI prêt.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
