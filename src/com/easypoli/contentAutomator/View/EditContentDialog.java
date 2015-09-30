package com.easypoli.contentAutomator.View;

import com.easypoli.contentAutomator.Model.Content;
import com.easypoli.contentAutomator.Model.NoteFile;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * JDialog for editing {@link Content Content} properties. This crazy bastard has a lot of components to lay down so
 * a big usage of GridBag Layout will be done. In addition we wanna make something a bit dynamic. Fro example we
 * wanna show a "how-to" message to the user in case no PDF files are available for this Content and then replace
 * that message with a list when something is added.
 *
 * Content is not updated until the user fires the Save button.
 */
public class EditContentDialog extends JDialog implements ActionListener {

    /**
     * Reference to the content to edit
     */
    private Content toEdit;
    /**
     * JComboBox for picking the content type
     */
    private JComboBox<Content.ContentType> typeJComboBox;
    /**
     * Content title JTextField
     */
    private JTextField titleField;
    /**
     * Content description JTextField
     */
    private JTextField descriptionField;
    /**
     * Content tags JTextFields
     */
    private JTextField[] tagsField;
    /**
     * JList for listing {@link NoteFile NoteFiles}.
     */
    private JList<NoteFile> noteFileJList;
    /**
     * FileDialog for choosing PDF files to add to the NoteFile list
     */
    private FileDialog fileDialog;
    /**
     * ListModel for noteFileJList
     */
    private DefaultListModel<NoteFile> listModel;
    /**
     * JPanel for resourcesListPanel and + and - buttons
     */
    private JPanel resourcesPanel;
    /**
     * JPanel for NoteFileJList or resourcesLabel
     */
    private JPanel resourcesListPanel;
    /**
     * Tell the user what to do in case no PDF files are available for this content
     */
    private JLabel resourcesLabel;
    /**
     * ScrollPane for NoteFileJList
     */
    private JScrollPane scrollPane;

    /**
     * EditContentDialog Constructor. This is responsible for the JDialog drawing
     * @param parent JDialog parent
     * @param content Content to edit/display
     */
    public EditContentDialog(JFrame parent, Content content) {

        /* #######################################
         * CREATING OBJECTS AND SETTING UP JLIST
         * ####################################### */
        super(parent, "Edit Content", true); // Call super constructor
        this.toEdit = content; // Save Content reference
        fileDialog = new FileDialog(this, "Choose a PDF File"); // Instantiate a new FileDialog
        listModel = new DefaultListModel<>(); // Instantiate a new ListModel
        ResourcesListRenderer resourcesListRenderer = new ResourcesListRenderer(); // Instantiate a new CellRenderer
        noteFileJList = new JList<>(listModel); // Instantiate a new JList
        noteFileJList.setCellRenderer(resourcesListRenderer); // Set JList cell renderer (the one we coded)
        noteFileJList.setLayoutOrientation(JList.VERTICAL); // Set JList orientation
        noteFileJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Set JList selection model

        /* ####################################################
         * SETTING UP COMBOBOX, TEXT FIELDS AND DIALOG BUTTONS
         * #################################################### */

        GridBagConstraints cs = new GridBagConstraints(); // Constraints

        Content.ContentType[] types = { // Define possible type for the Content. These will be the options for ComboBox
                Content.ContentType.TEORIA,
                Content.ContentType.ESERCIZI,
                Content.ContentType.SCHEMA
        };
        typeJComboBox = new JComboBox<>(types); // Instantiate JComboBox
        JLabel titleLabel = new JLabel("Title"); // Title Label
        JLabel descriptionLabel = new JLabel("Description"); // Desctiption Label

        titleField = new JTextField(10); // Set JTextField max column count
        descriptionField = new JTextField(10); // Set JTextField max column count

        JButton saveBtn = new JButton("Save"); // Save Button
        saveBtn.addActionListener(this); // Set its listener to this class
        JButton cancelBtn = new JButton("Cancel"); // Cancel Button
        cancelBtn.addActionListener(this); // Set its listener to this class

        tagsField = new JTextField[9]; // Instantiate tags fields
        for (int i = 0; i < 9; i++) {
            tagsField[i] = new JTextField(10); //Set JTextField max column count
        }

        /* #######################
         * SETTING UP TAGS JPANEL
         * ####################### */

        // JPanel for tags text fields
        JPanel tagsPanel = new JPanel(new GridBagLayout());

        // Tags text fields constraints
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.weightx = 1;
        int counter = 0;
        for (int i = 0; i < 3 && counter < 9; i++) {
            for (int k = 0; k < 3 && counter < 9; k++) {
                cs.gridx = k;
                cs.gridy = i;
                tagsPanel.add(tagsField[counter], cs);
                counter++;
            }
        }

        // Set a border for the Tags JPanel
        tagsPanel.setBorder(BorderFactory.createCompoundBorder( //Code courtesy of Oracle's Java Examples
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Tags"),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ),
                tagsPanel.getBorder()
        ));

        /* ###############################
         * SETTING UP RESOURCES LIST PANEL
         * ############################### */

        resourcesPanel = new JPanel(new BorderLayout(5, 0)); // See docs above
        resourcesListPanel = new JPanel(new BorderLayout()); // See docs above

        // Display different components depending on NoteFiles availability. If no file is available show a message
        // to the user to tell him/her what he can do, otherwise show and populate a JList with those files.
        if (toEdit.getNoteFiles().isEmpty()) {
            resourcesLabel = new JLabel("Click on + button to add a PDF file");
            resourcesListPanel.add(resourcesLabel, BorderLayout.CENTER);
        } else {
            for (NoteFile n : toEdit.getNoteFiles()) {  // Add NoteFiles from content to edit to the list model. This way
                listModel.addElement(n);                // if we change something content info are not immediately
            }                                           // updated.

            scrollPane = new JScrollPane(noteFileJList); // Wrap the list in a scroll pane
            scrollPane.setPreferredSize(new Dimension(500, 100)); // Set preferred size
            resourcesListPanel.add(scrollPane, BorderLayout.CENTER); // Add it to resourcesListPanel
        }

        JPanel actionsPanel = new JPanel(); // JPanel for + and - buttons
        actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
        JButton addBtn = new JButton("+"); // Add file button
        JButton removeBtn = new JButton("-"); // Remove file button
        addBtn.addActionListener(this); // Set listener to this class
        removeBtn.addActionListener(this); // Set listener to this class

        actionsPanel.add(addBtn);
        actionsPanel.add(removeBtn); // Add 'em

        resourcesPanel.add(resourcesListPanel, BorderLayout.CENTER); // Add resourcesListPanel to resourcesPanel
        resourcesPanel.add(actionsPanel, BorderLayout.EAST); // Add + and - buttons panel to resourcesPanel

        resourcesPanel.setBorder(BorderFactory.createCompoundBorder( //Code courtesy of Oracle's Java Examples
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Resources"),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ),
                resourcesPanel.getBorder()
        ));

        /* ##################################
         * SETTING UP THE JDIALOG MAIN PANEL
         * ################################## */

        JPanel editPanel = new JPanel(new GridBagLayout()); // JPanel for Content info

        // Title Label constraints
        cs.gridx = 0;
        cs.gridy = 0;
        cs.weightx = 0;
        cs.fill = GridBagConstraints.RELATIVE;
        cs.anchor = GridBagConstraints.WEST;
        editPanel.add(titleLabel, cs);

        // Description Label constraints
        cs.gridy = 1;
        editPanel.add(descriptionLabel, cs);

        // Title text field constraints
        cs.gridy = 0;
        cs.gridx = 1;
        cs.weightx = 1;
        cs.anchor = GridBagConstraints.CENTER;
        cs.fill = GridBagConstraints.HORIZONTAL;
        editPanel.add(titleField, cs);

        // Type JComboBox constraints
        cs.gridx = 2;
        cs.weightx = 0;
        cs.fill = GridBagConstraints.RELATIVE;
        editPanel.add(typeJComboBox, cs);

        // Description text field constraints
        cs.gridx = 1;
        cs.gridy = 1;
        cs.weightx = 1;
        cs.gridwidth = 2;
        cs.fill = GridBagConstraints.HORIZONTAL;
        editPanel.add(descriptionField, cs);

        // tagsPanel constraints
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 3;
        cs.fill = GridBagConstraints.BOTH;
        editPanel.add(tagsPanel, cs);

        // resourcesPanel constraints
        cs.gridy = 3;
        cs.weighty = 1;
        editPanel.add(resourcesPanel, cs);

        editPanel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5))); // Set insets

        /* ################################################
         * FILL TEXT FIELDS AND COMBOBOX WITH CONTENT INFO
         * ################################################ */

        titleField.setText(toEdit.getTitle()); // Set title text
        descriptionField.setText(toEdit.getDescription()); // Set description text
        for (int i = 0; i < toEdit.getTags().length; i++) {
            tagsField[i].setText(toEdit.getTags()[i]); // Set tags text
        }
        if (toEdit.getType() != Content.ContentType.NULL) {
            typeJComboBox.setSelectedItem(toEdit.getType()); // Set a type if available
        } else {
            typeJComboBox.setSelectedIndex(-1); // Select nothing from type combobox if no type (NULL) is available
                                                // for the content
        }

        /* ###################################
         * ADD SAVE/CANCEL BUTTONS TO JDIALOG
         * ################################### */

        JPanel selectionPanel = new JPanel();
        selectionPanel.add(saveBtn);
        selectionPanel.add(cancelBtn);

        /* ############################################
         * ADD EVERYTHING TO JDIALOG AND LAST SETTINGS
         * ############################################ */

        setLayout(new BorderLayout());
        getContentPane().add(editPanel, BorderLayout.CENTER);
        getContentPane().add(selectionPanel, BorderLayout.PAGE_END);
        pack();
        setLocationRelativeTo(parent);
    }

    /**
     * Methods to run depending on what button is fired
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Save":
                SaveAction();
                break;
            case "Cancel":
                dispose(); // Close everything and see ya
                break;
            case "+":
                AddPDF();
                break;
            case "-":
                RemovePDF();
                break;
            default:
                break;
        }
    }

    /**
     * Method to be invoked when Save button is fired. It's time so save what we did in this dialog to its original
     * Content.
     */
    private void SaveAction() {
        toEdit.setTitle(titleField.getText()); // Change title.
        toEdit.setDescription(descriptionField.getText()); // Change description
        toEdit.setType((Content.ContentType) typeJComboBox.getSelectedItem()); // Change type
        for (int i = 0; i < 9; i++) { // Change tags
            if (!(tagsField[i].getText().isEmpty()) && tagsField[i].getText() != null) {
                toEdit.getTags()[i] = tagsField[i].getText(); // If the tag field isn't empty update it
            } else {
                toEdit.getTags()[i] = ""; // otherwise write and empty String.
            }
        }

        // Check if new resources have been added and add them to content object if so
        for (int i = 0; i < listModel.getSize(); i++) {
            if (!toEdit.getNoteFiles().contains(listModel.get(i))) {
                toEdit.getNoteFiles().add(listModel.get(i));
            }
        }

        // Check if resources have been removed and remove them from content object if so
        Iterator<NoteFile> iterator = toEdit.getNoteFiles().iterator();
        ArrayList<NoteFile> toRemove = new ArrayList<>();
        while (iterator.hasNext()) {
            NoteFile tmp = iterator.next();
            if (!listModel.contains(tmp)) {
                toRemove.add(tmp);
            }
        } toEdit.getNoteFiles().removeAll(toRemove);

        dispose(); // Close editContentDialog
    }

    /**
     * Method to be invoked when "+" button is fired. It shows a fileDialog filtering PDF files. Multiple selection
     * is supported. In case no PDF were available before button is fired we suppose that no JList is shown in dialog
     * UI. So we remove the "how to" message Label and replace it with a scroll pane wrapping the jlist.
     */
    private void AddPDF() {
        fileDialog.setFilenameFilter((dir, name) -> name.endsWith(".pdf"));
        fileDialog.setMultipleMode(true);
        fileDialog.setVisible(true); // Show file dialog

        if (fileDialog.getFiles().length != 0) { // Check if user selected something
            for (File f : fileDialog.getFiles()) { // If so get every of them and add it to the list model
                listModel.addElement(new NoteFile(f.getAbsolutePath(), NoteFile.NoteFileLocation.LOCAL));
            }
            if (resourcesLabel != null) { // Check if any how-to label is shown. If so remove it and set it to null
                resourcesListPanel.remove(resourcesLabel);
                resourcesLabel = null;
                scrollPane = new JScrollPane(noteFileJList); // Then add a new scrollpane and wrap the list
                scrollPane.setPreferredSize(new Dimension(500, 100));
                resourcesListPanel.add(scrollPane, BorderLayout.CENTER);
                resourcesListPanel.revalidate(); // Re-validate the layout and repaint
                resourcesListPanel.repaint();
            }
        }
    }

    /**
     * Method to be invoked when "-" button is fired. This removes the selected PDF. In case it was the last one
     * replace the scroll pane with a message label to tell the user what he can do
     */
    private void RemovePDF() {
        if (noteFileJList.getSelectedIndex() != -1) { // Check if something is selected
            // Check if selected file is already on the server:
            if (noteFileJList.getSelectedValue().getLocation() != NoteFile.NoteFileLocation.SERVER) {
                listModel.remove(noteFileJList.getSelectedIndex()); // If not remove it from list model
            } else { // If so don't do anything and show a not supported warning dialog
                JOptionPane.showMessageDialog(
                        this,
                        "Removing already uploaded files feature is currently not supported",
                        "",
                        JOptionPane.WARNING_MESSAGE);
            }

        }
        if (listModel.getSize() == 0) { // Check if no files are available. If so remove scrollpane and add howto label
            resourcesListPanel.remove(scrollPane);
            scrollPane = null;
            resourcesLabel = new JLabel("Click on + button to add a PDF file");
            resourcesListPanel.add(resourcesLabel, BorderLayout.CENTER);
            resourcesListPanel.revalidate(); // Re-validate the layout and repaint
            resourcesListPanel.repaint();
        }
    }
}
