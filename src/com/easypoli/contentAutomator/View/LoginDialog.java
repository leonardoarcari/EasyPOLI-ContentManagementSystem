package com.easypoli.contentAutomator.View;

import com.easypoli.contentAutomator.Controller.Loginator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JDialog for logging in. It lets the user to type username and password and start a SwingWorker class that take
 * care of background stuff
 */
public class LoginDialog extends JDialog implements ActionListener{

    /**
     * Reference to the mainAppUI
     */
    private MainAppUI mainAppUI;
    /**
     * Username text file
     */
    private JTextField usernameField;
    /**
     * Password passwordField
     */
    private JPasswordField passwordField;
    /**
     * Login Button
     */
    private JButton loginBtn;
    /**
     * Cancel Button
     */
    private JButton cancelBtn;
    /**
     * Logging in went fine or not. True: logged in. False: error
     */
    private boolean logged;

    /**
     * LoginDialog constructor. Draws the UI.
     * @param parent LoginDialog parent frame
     * @param mainAppUI Reference to MainAppUI
     */
    public LoginDialog(JFrame parent, MainAppUI mainAppUI) {
        super(parent, "Login", true); // Call super constructor

        this.mainAppUI = mainAppUI; // Set mainAppUI Reference

        JPanel loginPanel = new JPanel(new GridBagLayout()); // JPanel for login fields
        GridBagConstraints cs = new GridBagConstraints();

        // Username label constraints
        JLabel usernameLabel = new JLabel("Username");
        cs.insets = new Insets(0, 0, 0, 10);
        loginPanel.add(usernameLabel, cs);

        // Username text field constraints
        usernameField = new JTextField(10);
        cs.gridx = 1;
        cs.weightx = 1;
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.insets = new Insets(0, 0, 0, 0);
        loginPanel.add(usernameField, cs);

        // Password label constraints
        JLabel passwordLabel = new JLabel("Password");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.weightx = 0;
        cs.fill = GridBagConstraints.RELATIVE;
        cs.insets = new Insets(0, 0, 0, 10);
        loginPanel.add(passwordLabel, cs);

        // Password field constraints
        passwordField = new JPasswordField(10);
        cs.gridx = 1;
        cs.weightx = 1;
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.insets = new Insets(0, 0, 0, 0);
        loginPanel.add(passwordField, cs);

        loginPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10))); // Add insets

        // Login and Cancel buttons and set the listener to this class
        loginBtn = new JButton("Login");
        loginBtn.addActionListener(this);
        cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(this);

        JPanel optionsPanel = new JPanel(); // JPanel for login and cancel buttons
        optionsPanel.add(loginBtn);
        optionsPanel.add(cancelBtn);

        // Add everything to login jPanel and set last things up
        getContentPane().add(loginPanel, BorderLayout.CENTER);
        getContentPane().add(optionsPanel, BorderLayout.PAGE_END);
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    /**
     * Action Listener method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Login")) { // If login button is fired
            Loginator loginator = new Loginator(LoginDialog.this); // Run Loginator SwingWorker object
            loginator.execute();
        } else { // If cancel button is fired
            dispose();
        }
    }

    /* #####################
     * GETTERS AND SETTERS
     * ##################### */

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginBtn() {
        return loginBtn;
    }

    public JButton getCancelBtn() {
        return cancelBtn;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public MainAppUI getMainAppUI() {
        return mainAppUI;
    }

    public void setMainAppUI(MainAppUI mainAppUI) {
        this.mainAppUI = mainAppUI;
    }
}
