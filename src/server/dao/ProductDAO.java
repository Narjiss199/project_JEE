package server.dao;

import server.models.Product;
import server.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Ajouter un produit
    public void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, category, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            stmt.setInt(3, product.getQuantity());
            stmt.setBigDecimal(4, product.getPrice());
            stmt.executeUpdate();
        }
    }

    // Mettre à jour un produit
    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE products SET name = ?, category = ?, quantity = ?, price = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            stmt.setInt(3, product.getQuantity());
            stmt.setBigDecimal(4, product.getPrice());
            stmt.setInt(5, product.getId());
            stmt.executeUpdate();
        }
    }

    // Supprimer un produit
    public void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Récupérer tous les produits
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("quantity"),
                        rs.getBigDecimal("price")
                );
                products.add(product);
            }
        }
        return products;
    }
}
