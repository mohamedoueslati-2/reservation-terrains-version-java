package org.example;

import org.example.ui.LoginJoueurFrame;

public class MainJoueur {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginJoueurFrame().setVisible(true);
        });
    }
}