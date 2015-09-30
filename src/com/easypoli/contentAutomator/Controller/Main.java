package com.easypoli.contentAutomator.Controller;

import com.easypoli.contentAutomator.View.MainAppUI;

import javax.swing.*;

public class Main {
    /**
     * Running our beautiful application
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainAppUI().draw());
    }
}
