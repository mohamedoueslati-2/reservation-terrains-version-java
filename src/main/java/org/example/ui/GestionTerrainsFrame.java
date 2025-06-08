package org.example.ui;

import org.example.dao.TerrainDaoImpl;
import org.example.model.Terrain;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;

public class GestionTerrainsFrame extends JFrame {
    private DefaultTableModel model;
    private JTable table;
    private JTextField nomField, typeField, etatField, tarifField;
    private JLabel imageLabel;
    private String imagePath = null;
    private TerrainDaoImpl dao = new TerrainDaoImpl();

    public GestionTerrainsFrame() {
        setTitle("Gestion des terrains");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations du terrain"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1;
        nomField = new JTextField(15);
        formPanel.add(nomField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Type :"), gbc);
        gbc.gridx = 1;
        typeField = new JTextField(15);
        formPanel.add(typeField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("État :"), gbc);
        gbc.gridx = 1;
        etatField = new JTextField(15);
        formPanel.add(etatField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Tarif Horaire :"), gbc);
        gbc.gridx = 1;
        tarifField = new JTextField(15);
        formPanel.add(tarifField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Image :"), gbc);
        gbc.gridx = 1;
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(120, 80));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        formPanel.add(imageLabel, gbc);
        gbc.gridx = 2;
        JButton browseBtn = new JButton("Parcourir");
        formPanel.add(browseBtn, gbc);

        // Boutons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton addBtn = new JButton("Ajouter");
        JButton updateBtn = new JButton("Modifier");
        JButton deleteBtn = new JButton("Supprimer");
        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);

        // Table
        String[] columns = {"ID", "Nom", "Type", "État", "Tarif Horaire", "Images"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 5) return ImageIcon.class;
                return String.class;
            }
        };
        table = new JTable(model);
        table.setRowHeight(60);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        JScrollPane scrollPane = new JScrollPane(table);

        // Layout principal
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(btnPanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Actions
        browseBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                imagePath = file.getAbsolutePath();
                ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH));
                imageLabel.setIcon(icon);
            }
        });

        addBtn.addActionListener(e -> {
            try {
                Terrain t = new Terrain(
                        nomField.getText(),
                        typeField.getText(),
                        etatField.getText(),
                        new BigDecimal(tarifField.getText()),
                        imagePath
                );
                dao.addTerrain(t);
                refreshTable();
                clearForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur d'ajout : " + ex.getMessage());
            }
        });

        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                try {
                    Terrain t = new Terrain(
                            id,
                            nomField.getText(),
                            typeField.getText(),
                            etatField.getText(),
                            new BigDecimal(tarifField.getText()),
                            imagePath
                    );
                    dao.updateTerrain(t);
                    refreshTable();
                    clearForm();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur de modification : " + ex.getMessage());
                }
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                dao.deleteTerrain(id);
                refreshTable();
                clearForm();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    nomField.setText(model.getValueAt(row, 1).toString());
                    typeField.setText(model.getValueAt(row, 2).toString());
                    etatField.setText(model.getValueAt(row, 3).toString());
                    tarifField.setText(model.getValueAt(row, 4).toString());

                    Terrain t = dao.getAllTerrains().get(row);
                    imagePath = t.getImages();

                    if (imagePath != null) {
                        ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH));
                        imageLabel.setIcon(icon);
                    } else {
                        imageLabel.setIcon(null);
                    }
                }
            }
        });

        refreshTable();

        // Ajout du bouton retour en bas
        JButton btnRetour = new JButton("Retour au dashboard");
        btnRetour.addActionListener(e -> dispose());
        JPanel panelRetour = new JPanel();
        panelRetour.add(btnRetour);
        add(panelRetour, BorderLayout.SOUTH);
    }

    private void refreshTable() {
        model.setRowCount(0);
        List<Terrain> terrains = dao.getAllTerrains();
        for (Terrain t : terrains) {
            ImageIcon icon = null;
            if (t.getImages() != null) {
                icon = new ImageIcon(new ImageIcon(t.getImages()).getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH));
            }
            model.addRow(new Object[]{
                    t.getId(),
                    t.getNom(),
                    t.getType(),
                    t.getEtat(),
                    t.getTarifHoraire(),
                    icon
            });
        }
    }

    private void clearForm() {
        nomField.setText("");
        typeField.setText("");
        etatField.setText("");
        tarifField.setText("");
        imageLabel.setIcon(null);
        imagePath = null;
    }
}