package com.easypoli.contentAutomator.View;

import com.easypoli.contentAutomator.Model.Content;

import javax.swing.*;
import java.awt.*;

/**
 * ListCellRenderer for a list that contains "Content" objects. What we wanna show is a sum up of the content so not
 * every property has to be shown. We just want to display content title, description and its type.
 * Everything is well organized thanks to GridBag Layout.
 */
public class ContentsListRenderer implements ListCellRenderer<Content> {

    /**
     * Implemented ListCellRenderer method
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends Content> list, Content value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel jPanel = new JPanel(new GridBagLayout()); // Cell jPanel which will be returned
        GridBagConstraints cs = new GridBagConstraints(); // GridBag constraints

        JLabel titleLabel = new JLabel(value.getTitle()); // Content title JLabel
        titleLabel.setFont(new Font( // Set label font to BOLD
                titleLabel.getFont().getName(),
                Font.BOLD,
                titleLabel.getFont().getSize()
        ));

        JLabel descriptionLabel = new JLabel(value.getDescription()); // Content description JLabel
        descriptionLabel.setFont(new Font( // Set label font to italic
                descriptionLabel.getFont().getName(),
                Font.ITALIC,
                descriptionLabel.getFont().getSize()
        ));

        JLabel typeLabel = new JLabel(value.getType().getType()); // Content type JLabel

        // Title label constrainst
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.weightx = 0.99;
        cs.anchor = GridBagConstraints.WEST;
        jPanel.add(titleLabel, cs);

        // Description label constraints
        cs.gridy = 1;
        jPanel.add(descriptionLabel, cs);

        // Type label constraints
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridheight = 2;
        cs.anchor = GridBagConstraints.EAST;
        cs.weightx = 0.01;
        cs.fill = GridBagConstraints.RELATIVE;
        jPanel.add(typeLabel, cs);

        // Set a border for jPanel
        jPanel.setBorder(BorderFactory.createCompoundBorder( //Code courtesy of Oracle's Java Examples
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(""),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ),
                jPanel.getBorder()
        ));

        // Color references useful for jPanel background. We switch between them in case this cell is selected or not
        Color defaultBackground = jPanel.getBackground();
        final Color SELECTION_COLOR = new JTextField().getSelectionColor(); // Save default selection color for this
                                                                            // Look and feel

        // Change panel background whether it's selected or not.
        if (isSelected) {
            jPanel.setBackground(SELECTION_COLOR);
        } else {
            jPanel.setBackground(defaultBackground);
        }

        // Return cell component
        return jPanel;
    }
}
