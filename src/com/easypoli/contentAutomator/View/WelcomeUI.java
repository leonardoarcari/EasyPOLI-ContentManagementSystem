package com.easypoli.contentAutomator.View;

import javax.swing.*;
import java.awt.*;

/**
 * Welcome UI. Work is still in progress. Atm I don't like it but it's not that important.
 */
public class WelcomeUI {
    private JFrame parent;
    private JPanel mainPanel;
    private JButton loginBtn;
    private MainAppUI mainAppUI;

    public WelcomeUI(JFrame parent, MainAppUI mainAppUI) {
        this.parent = parent;
        this.mainAppUI = mainAppUI;
    }

    public void draw() {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        String WELCOME_TEXT_1 = "Welcome to EasyPOLI ContentCreator";
        String WELCOME_TEXT_2 = "With this app you can easily manage all the site's contents with like no efforts.";
        String WELCOME_TEXT_3 = "As you can see some tabs are disabled as we need to know if your a friend or an " +
                "enemy";
        String WELCOME_TEXT_4 = "So why don't we skip to the point?";
        String WELCOME_TEXT_5 = "Hit the login button below and unleash the power";
        mainPanel = new JPanel(new GridBagLayout());
        JLabel[] welcomeTexts = new JLabel[5];
        welcomeTexts[0] = new JLabel(WELCOME_TEXT_1, SwingConstants.CENTER);
        welcomeTexts[1] = new JLabel(WELCOME_TEXT_2, SwingConstants.CENTER);
        welcomeTexts[2] = new JLabel(WELCOME_TEXT_3, SwingConstants.CENTER);
        welcomeTexts[3] = new JLabel(WELCOME_TEXT_4, SwingConstants.CENTER);
        welcomeTexts[4] = new JLabel(WELCOME_TEXT_5, SwingConstants.CENTER);

        loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> {
            LoginDialog loginDialog = new LoginDialog(WelcomeUI.this.parent, mainAppUI);
            loginDialog.setVisible(true);
        });

        for (int i = 0; i < 5; i++) {
            welcomePanel.add(welcomeTexts[i]);
        }
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 0.6;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(welcomePanel, gbc);
        gbc.gridy = 1;
        gbc.weighty = 0.4;
        mainPanel.add(loginBtn, gbc);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getLoginBtn() {
        return loginBtn;
    }

    public MainAppUI getMainAppUI() {
        return mainAppUI;
    }

    public void setMainAppUI(MainAppUI mainAppUI) {
        this.mainAppUI = mainAppUI;
    }
}
