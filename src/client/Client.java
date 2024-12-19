package client;

import server.models.Product;
import server.rmi.ProductService;

import java.math.BigDecimal;
import java.rmi.Naming;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        try {
            // Connexion au service distant
            ProductService productService = (ProductService) Naming.lookup("rmi://localhost/ProductService");

            // Ajouter un produit
            Product product = new Product(0, "Produit RMI", "Catégorie RMI", 50, new BigDecimal("100.00"));
            productService.addProduct(product);
            System.out.println("Produit ajouté via RMI.");

            // Récupérer tous les produits
            List<Product> products = productService.getAllProducts();
            System.out.println("Produits récupérés via RMI : ");
            for (Product p : products) {
                System.out.println(p.getName() + " - " + p.getPrice());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
