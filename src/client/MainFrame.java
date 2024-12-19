package client;

import server.models.Product;
import server.rmi.ProductService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.rmi.Naming;
import java.util.List;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {
    private ProductService productService;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public MainFrame() {
        // Configuration de la fenêtre
        setTitle("Mini projet JEE de Narjiss Zahr");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Titre personnalisé
        JLabel titleLabel = new JLabel("Mini projet JEE de Narjiss Zahr", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0x0044cc)); // Couleur bleue
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Connexion au serveur RMI
        try {
            productService = (ProductService) Naming.lookup("rmi://localhost/ProductService");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur de connexion au serveur RMI", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }

        // Initialisation des composants
        initUI();

        // Charger les produits
        loadProducts();
    }

    private void initUI() {
        // Panel de formulaire avec GridBagLayout pour centrer les éléments
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Ajouter un produit",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16), new Color(0x0044cc)));
        formPanel.setBackground(new Color(0xf5f5f5)); // Fond gris clair

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Marges autour des composants

        JLabel nameLabel = new JLabel("Nom :");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField nameField = new JTextField(20);

        JLabel categoryLabel = new JLabel("Catégorie :");
        categoryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField categoryField = new JTextField(20);

        JLabel quantityLabel = new JLabel("Quantité :");
        quantityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField quantityField = new JTextField(20);

        JLabel priceLabel = new JLabel("Prix :");
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField priceField = new JTextField(20);

        JButton addButton = new JButton("Ajouter");
        addButton.setBackground(new Color(0x4CAF50)); // Vert
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String category = categoryField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                BigDecimal price = new BigDecimal(priceField.getText());

                Product product = new Product(0, name, category, quantity, price);
                productService.addProduct(product);

                JOptionPane.showMessageDialog(this, "Produit ajouté avec succès !");
                loadProducts(); // Rechargez les produits après ajout

                // Vider les champs
                nameField.setText("");
                categoryField.setText("");
                quantityField.setText("");
                priceField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du produit", "Erreur", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Positionner les composants avec GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(categoryLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(categoryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(quantityLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(priceLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(priceField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(addButton, gbc);

        // Tableau pour afficher les produits
        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Catégorie", "Quantité", "Prix"}, 0);
        productTable = new JTable(tableModel);

        // Ajouter le tableau dans un JScrollPane pour permettre le défilement
        JScrollPane tableScrollPane = new JScrollPane(productTable);

        // Ajouter les composants à la fenêtre
        add(formPanel, BorderLayout.NORTH);

        // Ajouter le champ de recherche
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Bouton de recherche
        JButton searchButton = new JButton("Rechercher");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setBackground(new Color(0x2196F3)); // Bleu
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(e -> filterProducts()); // Recherche au clic du bouton

        searchPanel.add(new JLabel("Rechercher :"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Ajouter le champ de recherche en haut de la table
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        // Panel des actions pour les boutons Modifier et Supprimer
        JPanel actionPanel = new JPanel();
        JButton editButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");

        editButton.setBackground(new Color(0x2196F3)); // Bleu
        deleteButton.setBackground(new Color(0xF44336)); // Rouge
        editButton.setForeground(Color.WHITE);
        deleteButton.setForeground(Color.WHITE);

        // Action de modification
        editButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                int productId = (int) tableModel.getValueAt(selectedRow, 0);
                String name = (String) tableModel.getValueAt(selectedRow, 1);
                String category = (String) tableModel.getValueAt(selectedRow, 2);
                int quantity = (int) tableModel.getValueAt(selectedRow, 3);
                BigDecimal price = (BigDecimal) tableModel.getValueAt(selectedRow, 4);

                // Mettre à jour les champs de formulaire avec les informations du produit
                JTextField nameFieldEdit = new JTextField(name);
                JTextField categoryFieldEdit = new JTextField(category);
                JTextField quantityFieldEdit = new JTextField(String.valueOf(quantity));
                JTextField priceFieldEdit = new JTextField(price.toString());

                int option = JOptionPane.showConfirmDialog(this, new Object[]{
                        "Nom", nameFieldEdit,
                        "Catégorie", categoryFieldEdit,
                        "Quantité", quantityFieldEdit,
                        "Prix", priceFieldEdit
                }, "Modifier le produit", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    try {
                        Product updatedProduct = new Product(productId, nameFieldEdit.getText(), categoryFieldEdit.getText(),
                                Integer.parseInt(quantityFieldEdit.getText()), new BigDecimal(priceFieldEdit.getText()));
                        productService.updateProduct(updatedProduct);
                        loadProducts();
                        JOptionPane.showMessageDialog(this, "Produit modifié avec succès !");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erreur lors de la modification du produit", "Erreur", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Action de suppression
        deleteButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                int productId = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    productService.deleteProduct(productId);
                    loadProducts();
                    JOptionPane.showMessageDialog(this, "Produit supprimé avec succès !");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du produit", "Erreur", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // Ajouter les boutons au panel d'actions
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);

        // Ajouter le panel d'actions au bas de la fenêtre
        add(actionPanel, BorderLayout.SOUTH);
    }

    private void loadProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            tableModel.setRowCount(0); // Effacer les anciennes données
            for (Product p : products) {
                tableModel.addRow(new Object[]{p.getId(), p.getName(), p.getCategory(), p.getQuantity(), p.getPrice()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des produits", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void filterProducts() {
        String query = searchField.getText().toLowerCase();
        try {
            List<Product> products = productService.getAllProducts();
            List<Product> filteredProducts = products.stream()
                    .filter(p -> p.getName().toLowerCase().contains(query) || p.getCategory().toLowerCase().contains(query))
                    .collect(Collectors.toList());

            tableModel.setRowCount(0);
            for (Product p : filteredProducts) {
                tableModel.addRow(new Object[]{p.getId(), p.getName(), p.getCategory(), p.getQuantity(), p.getPrice()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du filtrage des produits", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
