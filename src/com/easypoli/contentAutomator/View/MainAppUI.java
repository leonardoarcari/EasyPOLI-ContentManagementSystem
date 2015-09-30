package com.easypoli.contentAutomator.View;

import com.easypoli.contentAutomator.Model.FTPManager;

import javax.swing.*;
import java.awt.*;

/**
 * This class is intended to be the UI Head-Quarter. Every UI responsible class has its reference in this class. This
 * way we can always reach another point of the UI easily through getters and setters. This at this as Rome for Italy
 * as we Italians say "Every road gets you to Rome".
 */
public class MainAppUI {
    /**
     * App JFrame
     */
    private JFrame rootFrame;
    /**
     * ContentCreatorUI reference
     */
    private ContentCreatorUI contentCreatorUI;
    /**
     * WelcomeUI reference
     */
    private WelcomeUI welcomeUI;
    /**
     * EditContentPageUI reference
     */
    private EditContentPageUI editContentPageUI;
    /**
     * JTabbed pane where you can choose different application features
     */
    private JTabbedPane jTabbedPane;
    /**
     * FTPManager reference
     */
    private FTPManager ftpManager;

    /**
     * Draw the MainAppUI.
     */
    public void draw() {
        rootFrame = new JFrame("Content Automator"); // MainApp JFrame
        rootFrame.setPreferredSize(new Dimension(800, 600)); // Preferred size
        rootFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Instantiate UI parts, sending references to this class
        contentCreatorUI = new ContentCreatorUI(rootFrame);
        welcomeUI = new WelcomeUI(rootFrame, this);
        editContentPageUI = new EditContentPageUI(this);

        // Draw UI parts
        welcomeUI.draw();
        editContentPageUI.draw();

        // Add em to a JTabbedPane for a cute selection user interface
        jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
        jTabbedPane.addTab("Welcome", welcomeUI.getMainPanel());
        jTabbedPane.addTab("Content Creator", contentCreatorUI.getMainPanel());
        jTabbedPane.addTab("Edit Content Page", editContentPageUI.getMainPanel());

        // Disable EditContentPage feature by default. Will be unlocked after login
        jTabbedPane.setEnabledAt(jTabbedPane.indexOfTab("Edit Content Page"), false);

        // Last setups
        rootFrame.add(jTabbedPane);
        rootFrame.pack();
        rootFrame.setVisible(true); // Show this
    }

    /* #####################
    * GETTERS AND SETTERS
    * ##################### */

    public WelcomeUI getWelcomeUI() {
        return welcomeUI;
    }

    public ContentCreatorUI getContentCreatorUI() {
        return contentCreatorUI;
    }

    public EditContentPageUI getEditContentPageUI() {
        return editContentPageUI;
    }

    public JTabbedPane getjTabbedPane() {
        return jTabbedPane;
    }

    public FTPManager getFtpManager() {
        return ftpManager;
    }

    public void setFtpManager(FTPManager ftpManager) {
        this.ftpManager = ftpManager;
    }

    public JFrame getRootFrame() {
        return rootFrame;
    }
}
