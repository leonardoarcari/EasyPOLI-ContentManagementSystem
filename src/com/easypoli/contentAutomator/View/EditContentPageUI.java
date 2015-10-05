package com.easypoli.contentAutomator.View;

import com.easypoli.contentAutomator.Model.Content;
import com.easypoli.contentAutomator.Model.ContentPage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Vector;

/**
 * Class for handling the "Edit Content Page" UI. The main purpose of this feature is to let the user pick a content
 * page and be able to see/edit its properties. In addition he can save modifications, updating the ContentPage
 * object, create a new ContentPage and upload it to the server.
 */
public class EditContentPageUI implements ActionListener {
    /**
     * Main panel for this class UI
     */
    private JPanel mainPanel;
    /**
     * Reference to the mainAppUI class (application HQ)
     */
    private MainAppUI mainAppUI;
    /**
     * JPanel for page combobox and save, update, new buttons
     */
    private JPanel choosePagePanel;
    /**
     * ComboBox for picking contentPage
     */
    private JComboBox<ContentPage> pagesComboBox;
    /**
     * Save button
     */
    private JButton saveBtn;
    /**
     * Update button
     */
    private JButton updateBtn;
    /**
     * New Content page button
     */
    private JButton newBtn;
    /**
     * Page title text field
     */
    private JTextField pageTitleField;
    /**
     * Page description text field
     */
    private JTextField descriptionField;
    /**
     * Page large title text field
     */
    private JTextField largeTitleField;
    /**
     * Page medium title text field
     */
    private JTextField mediumTitleField;
    /**
     * Page short title text field
     */
    private JTextField shortTitleField;
    /**
     * Page file name text field
     */
    private JTextField fileNameField;
    /**
     * Pages vector containing pages can be picked from ComboBox
     */
    private Vector<ContentPage> pages;
    /**
     * JList for showing ContentPage's {@link Content Contents}
     */
    private JList<Content> contentJList;

    /**
     * Constructor.
     * @param mainAppUI MainAppUI reference.
     */
    public EditContentPageUI(MainAppUI mainAppUI) {
        this.mainAppUI = mainAppUI;
    }

    /**
     * Draw the whole UI. Check below for more details
     */
    public void draw() {

        /* ###############################################
         * DRAWING JCOMBOBOX AND SAVE, UPDATE, NEW BUTTONS
         * ############################################## */

        choosePagePanel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        pages = new Vector<>();

        pagesComboBox = new JComboBox<>(pages);
        pagesComboBox.addActionListener(this);
        newBtn = new JButton("New");
        saveBtn = new JButton("Save");
        updateBtn = new JButton("Update");

        // Combobox constraints
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.weightx = 1;
        choosePagePanel.add(pagesComboBox, cs);

        // Save btn constraints
        cs.fill = GridBagConstraints.RELATIVE;
        cs.gridx = 1;
        cs.weightx = 0;
        choosePagePanel.add(saveBtn, cs);

        // Update btn constraints
        cs.gridx = 2;
        choosePagePanel.add(updateBtn, cs);

        // New btn constraints
        cs.gridx = 3;
        choosePagePanel.add(newBtn, cs);

        choosePagePanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10))); // Set choosePagePanel insets

        /* ################################
         * DRAWING CONTENTPAGE TEXT FIELDS
         * ################################ */

        JPanel pagePropertiesPanel = new JPanel(new GridBagLayout());
        JLabel pageTitleLabel = new JLabel("Page Title");
        JLabel descriptionLabel = new JLabel("Description");
        JLabel largeTitleLabel = new JLabel("Large Title");
        JLabel mediumTitleLabel = new JLabel("Medium Title");
        JLabel shortTitleLabel = new JLabel("Short Title");
        JLabel fileNameLabel = new JLabel("File name");

        pageTitleField = new JTextField(10);
        descriptionField = new JTextField(10);
        largeTitleField = new JTextField(10);
        mediumTitleField = new JTextField(10);
        shortTitleField = new JTextField(10);
        fileNameField = new JTextField(10);

        // Page title Label constraints
        cs.gridx = 0;
        cs.gridy = 0;
        cs.anchor = GridBagConstraints.WEST;
        cs.insets = new Insets(0, 0, 0, 5);
        pagePropertiesPanel.add(pageTitleLabel, cs);

        // Page description Label constraints
        cs.gridy = 1;
        pagePropertiesPanel.add(descriptionLabel, cs);

        // Page large title Label constraints
        cs.gridy = 2;
        pagePropertiesPanel.add(largeTitleLabel, cs);

        // Page medium title Label constraints
        cs.gridy = 3;
        pagePropertiesPanel.add(mediumTitleLabel, cs);

        // Page title text field constraints
        cs.gridx = 1;
        cs.gridy = 0;
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.weightx = 1;
        cs.insets = new Insets(0, 0, 0, 0);
        pagePropertiesPanel.add(pageTitleField, cs);

        // Page description text field constraints
        cs.gridy = 1;
        cs.gridwidth = 3;
        pagePropertiesPanel.add(descriptionField, cs);

        // Page large title text field constraints
        cs.gridy = 2;
        pagePropertiesPanel.add(largeTitleField, cs);

        // Page medium title text field constraints
        cs.gridy = 3;
        cs.gridwidth = 1;
        cs.weightx = 2;
        pagePropertiesPanel.add(mediumTitleField, cs);

        // Page short title Label constraints
        cs.weightx = 0;
        cs.gridx = 2;
        cs.fill = GridBagConstraints.RELATIVE;
        cs.insets = new Insets(0, 5, 0, 5);
        pagePropertiesPanel.add(shortTitleLabel, cs);

        // Page short title text field constraints
        cs.fill =  GridBagConstraints.HORIZONTAL;
        cs.weightx = 1;
        cs.gridx = 3;
        cs.insets = new Insets(0, 0, 0, 0);
        pagePropertiesPanel.add(shortTitleField, cs);

        // File name title Label constraints
        cs.weightx = 0;
        cs.gridx = 2;
        cs.gridy = 0;
        cs.fill = GridBagConstraints.RELATIVE;
        cs.insets = new Insets(0, 5, 0, 5);
        pagePropertiesPanel.add(fileNameLabel, cs);

        // File name title text field constraints
        cs.weightx = 1;
        cs.gridx = 3;
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.insets = new Insets(0, 0, 0, 0);
        pagePropertiesPanel.add(fileNameField, cs);

        pagePropertiesPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10))); // Set insets

        /* ###############################
         * DRAWING TEMP PLACEHOLDER PANEL
         * ############################### */

        JPanel tempPanel = new JPanel(new BorderLayout());
        tempPanel.setName("tempPanel");
        JLabel tmpLabel = new JLabel("");
        tempPanel.add(tmpLabel);

        /* #################################
         * ADD EVERYTHING TO THE MAIN PANEL
         * ################################# */

        mainPanel = new JPanel(new GridBagLayout());

        // ChoosePagePanel constraints
        cs.gridx = 0;
        cs.gridy = 0;
        cs.fill = GridBagConstraints.BOTH;
        cs.weightx = 1;
        cs.weighty = 0;
        cs.anchor = GridBagConstraints.NORTH;
        mainPanel.add(choosePagePanel, cs);

        // Page propertiesPanel constraints
        cs.gridy = 1;
        mainPanel.add(pagePropertiesPanel, cs);

        // TempPanel constraints
        cs.gridy = 2;
        cs.weighty = 1;
        mainPanel.add(tempPanel, cs);
    }

    /**
     * Listen to Combobox selection
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(pagesComboBox)) { // Check if the event comes from Combobox
            ContentPage selected = (ContentPage) pagesComboBox.getSelectedItem();   // Reference to selected ContentPage

            // Set text fields
            pageTitleField.setText(selected.getPageTitle());
            descriptionField.setText(selected.getDescription());
            largeTitleField.setText(selected.getTitleLarge());
            mediumTitleField.setText(selected.getTitleMedium());
            shortTitleField.setText(selected.getTitleSmall());
            fileNameField.setText(selected.getFileName());

            // We wanna check if this is the first time we're picking a Content Page from the combobox. If so instead
            // of a list of contents we have a tempPanel. If not we still want to replace the old list with a new one.
            Component[] components = mainPanel.getComponents();
            for (Component c : components) {
                String name = "";
                if (c.getName() != null) {
                    name = c.getName();
                }
                if (c instanceof JScrollPane || name.equals("tempPanel")) {
                    mainPanel.remove(c);
                }
            }

            // Add scrollPanel to mainPanel
            GridBagConstraints cs = new GridBagConstraints();
            cs.gridy = 2;
            cs.fill = GridBagConstraints.BOTH;
            cs.weightx = 1;
            cs.weighty = 1;
            cs.anchor = GridBagConstraints.NORTH;
            mainPanel.add(drawContentPanel(selected), cs);

            // Revalidate layout as new components have been added
            mainPanel.revalidate();
            mainPanel.repaint();
        }
    }

    /**
     * Private method to draw and return a JScrollPane containing the list of Contents. Only title, description and
     * type of the Content are shown.
     * @param contentPage ContentPage to retrieve Contents from.
     * @return JScrollPane wrapping Contents JList
     */
    private JScrollPane drawContentPanel(ContentPage contentPage) {
        DefaultListModel<Content> listModel = new DefaultListModel<>(); // DefaultListModel (kind of a Vector)
        ContentsListRenderer contentsListRenderer = new ContentsListRenderer(); // CellListRenderer for Contents

        for (Content c : contentPage.getContents()) {
            listModel.addElement(c); // Add elements from ContentPage's Contents vector to ListModel
        }

        // Create JList and set it up
        contentJList = new JList<>(listModel);
        contentJList.setCellRenderer(contentsListRenderer);
        contentJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contentJList.setLayoutOrientation(JList.VERTICAL);
        contentJList.addMouseListener(new MouseInputAdapter() { // Add an event listener for a "double-click"
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // Show a new EditContentDialog to edit the selected Content
                    new EditContentDialog(mainAppUI.getRootFrame(), contentJList.getSelectedValue()).setVisible(true);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(contentJList);
        return scrollPane;
    }

    /* #####################
     * GETTERS AND SETTERS
     * ##################### */

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Vector<ContentPage> getPages() {
        return pages;
    }

    public JTextField getPageTitleField() {
        return pageTitleField;
    }

    public JTextField getDescriptionField() {
        return descriptionField;
    }

    public JTextField getLargeTitleField() {
        return largeTitleField;
    }

    public JTextField getMediumTitleField() {
        return mediumTitleField;
    }

    public JTextField getShortTitleField() {
        return shortTitleField;
    }

    public MainAppUI getMainAppUI() {
        return mainAppUI;
    }

    public JComboBox<ContentPage> getPagesComboBox() {
        return pagesComboBox;
    }

}

