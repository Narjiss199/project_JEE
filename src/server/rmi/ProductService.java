package server.rmi;

import server.models.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ProductService extends Remote {
    // L'interface définit les méthodes accessibles à distance.
    void addProduct(Product product) throws RemoteException;
    void updateProduct(Product product) throws RemoteException;
    void deleteProduct(int id) throws RemoteException;
    List<Product> getAllProducts() throws RemoteException;
}
