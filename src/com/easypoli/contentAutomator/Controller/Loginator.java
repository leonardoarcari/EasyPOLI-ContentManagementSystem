package com.easypoli.contentAutomator.Controller;

import com.easypoli.contentAutomator.Model.ContentPage;
import com.easypoli.contentAutomator.Model.FTPManager;
import com.easypoli.contentAutomator.View.LoginDialog;

import javax.swing.*;
import java.util.Vector;

/**
 * SwingWorker extension class for handling {@link LoginDialog LoginDialog} UI and background jobs. This way UI wont
 * freeze. Cool uh?
 */
public class Loginator extends SwingWorker<Boolean, Void> {

    /**
     * LoginDialog reference
     */
    private LoginDialog loginDialog;
    /**
     * FTPManager reference
     */
    private FTPManager ftpManager;
    /**
     * Login Button reference
     */
    private JButton loginBtn;
    /**
     * Cancel button reference
     */
    private JButton cancelBtn;
    /**
     * Username String
     */
    private String username;
    /**
     * Password String
     */
    private String password;
    /**
     * ContentPages' vector reference
     */
    private Vector<ContentPage> pages;

    /**
     * Constructor. Sets references up, instantiates FTPManager and evaluates password field
     * @param loginDialog LoginDialog reference
     */
    public Loginator(LoginDialog loginDialog) {
        // Get references
        this.loginDialog = loginDialog;
        this.loginBtn = loginDialog.getLoginBtn();
        this.cancelBtn = loginDialog.getCancelBtn();
        this.username = loginDialog.getUsernameField().getText();

        // Evaluate password from PasswordField and save it in a String
        char[] password = loginDialog.getPasswordField().getPassword();
        String passwordString = "";
        for (char c : password) {
            passwordString = passwordString + c;
        }
        this.password = passwordString;

        ftpManager = new FTPManager(username, this.password); // Instantiate FTPManager
        loginDialog.getMainAppUI().setFtpManager(ftpManager); // Set FTPManager reference to MainAppUI
    }

    /**
     * Jobs to be done in background after login button has been fired
     * @return a Boolean that notifies whether login went fine or not
     * @throws Exception
     */
    @Override
    protected Boolean doInBackground() throws Exception {
        loginBtn.setEnabled(false); // Disable login button
        cancelBtn.setEnabled(false); // Disable cancel button
        ftpManager.login(); // Run login job
        Boolean error = ftpManager.isError(); // Get result value
        if (error) {
            pages = ftpManager.downloadData(); // If logged fine start downloading data and save its reference to "pages"
        }
        return error; // Return result value
    }

    /**
     * What to do after doInBackground() method returns
     */
    @Override
    protected void done() {
        try {
            loginDialog.setLogged(get()); // Set LoginDialog "logged" boolean to returned doInBackground value
            if (loginDialog.isLogged()) { // If login went fine...
                loginDialog.getMainAppUI().getEditContentPageUI().getPages().clear(); // Clear any older pages data
                // Add downloaded data to EditContentPageUI ComboBox data structure
                loginDialog.getMainAppUI().getEditContentPageUI().getPages().addAll(pages);
                // Repaint editContentPageUI comboBox (is that needed?)
                loginDialog.getMainAppUI().getEditContentPageUI().getPagesComboBox().repaint();
                // Disable WelcomeUI login button (as you already logged in)
                loginDialog.getMainAppUI().getWelcomeUI().getLoginBtn().setEnabled(false);
                // Enable "Edit Content Page" feature tab in the MainAppUI
                loginDialog.getMainAppUI()
                        .getjTabbedPane().setEnabledAt(
                            loginDialog.getMainAppUI().getjTabbedPane().indexOfTab("Edit Content Page"),
                            true
                        );
                loginDialog.dispose(); // Close loginDialog
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
