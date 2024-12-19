package server.rmi;

import server.dao.ProductDAO;
import server.models.Product;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ProductServiceImpl extends UnicastRemoteObject implements ProductService {
    private final ProductDAO productDAO;

    public ProductServiceImpl() throws RemoteException {
        super();
        this.productDAO = new ProductDAO();
    }

    @Override
    public void addProduct(Product product) throws RemoteException {
        try {
            productDAO.addProduct(product);
        } catch (Exception e) {
            throw new RemoteException("Erreur lors de l'ajout du produit", e);
        }
    }

    @Override
    public void updateProduct(Product product) throws RemoteException {
        try {
            productDAO.updateProduct(product);
        } catch (Exception e) {
            throw new RemoteException("Erreur lors de la mise à jour du produit", e);
        }
    }

    @Override
    public void deleteProduct(int id) throws RemoteException {
        try {
            productDAO.deleteProduct(id);
        } catch (Exception e) {
            throw new RemoteException("Erreur lors de la suppression du produit", e);
        }
    }

    @Override
    public List<Product> getAllProducts() throws RemoteException {
        try {
            return productDAO.getAllProducts();
        } catch (Exception e) {
            throw new RemoteException("Erreur lors de la récupération des produits", e);
        }
    }
}
