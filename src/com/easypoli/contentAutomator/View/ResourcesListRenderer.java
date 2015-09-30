package com.easypoli.contentAutomator.View;

import com.easypoli.contentAutomator.Model.NoteFile;

import javax.swing.*;
import java.awt.*;

/**
 * ListCellRenderer implementation for NoteFiles (we call em resources too). We wanna show if the file has to be
 * uploaded on the server or will be on next update. In addition we show the file path.
 */
public class ResourcesListRenderer implements ListCellRenderer<NoteFile> {

    /**
     * ListCellRenderer implemented method
     * @param list
     * @param value
     * @param index
     * @param isSelected
     * @param cellHasFocus
     * @return JPanel to be added in a JList
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends NoteFile> list, NoteFile value, int index, boolean isSelected, boolean cellHasFocus) {

        JPanel filePanel = new JPanel(new GridBagLayout()); // Cell JPanel
        GridBagConstraints cs = new GridBagConstraints();

        JLabel messageLabel = new JLabel(); // Show a message based on File Location property
        JLabel filePathLabel = new JLabel(); // File path
        Color defaultColor = filePanel.getBackground(); // Get a reference to default background color when not selected

        // Change JPanel background if selected
        if (isSelected) {
            filePanel.setBackground(new JTextField().getSelectionColor());
        } else {
            filePanel.setBackground(defaultColor);
        }

        // Notify the user on what will happen on each NoteFile.
        if (value.getLocation() == NoteFile.NoteFileLocation.SERVER) { // If already uploaded nothing will be done
            messageLabel.setText("PDF File already on the server:");
        } else {
            messageLabel.setText("Will be uploaded on next Update:"); // If not it will be uploaded
        }

        filePathLabel.setText(value.getAbsolutePath()); // Set Label text with file path

        // Message label constraints
        cs.weightx = 1;
        cs.anchor = GridBagConstraints.WEST;
        filePanel.add(messageLabel, cs);

        // Path label constraints
        cs.gridy = 1;
        filePanel.add(filePathLabel, cs);

        //Add a border (and insets)
        filePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(""),
                        BorderFactory.createEmptyBorder(3, 3, 3, 3)
                ),
                filePanel.getBorder()
        ));

        return filePanel; // Return cell JPanel
    }
}
