package com.easypoli.contentAutomator.Model;

import java.util.Arrays;
import java.util.Vector;

/**
 * EasyPOLI's content abstraction. This class describes what we believe a content is. It should be described with:
 *  - Title
 *  - Description
 *  - Type
 *  - Tags
 *  - Note pdf files that user will be able to download
 */
public class Content {
    /**
     * The title of this Content
     */
    private String title;
    /**
     * The description of this Content
     */
    private String description;
    /**
     * The type of this Content. Notes can be lessons (Teoria), exercises (Esercizi) or sum-ups (Schemi)
     */
    private ContentType type;
    /**
     * Tags that describe what notes contains.
     */
    private String[] tags;
    /**
     * Vector of {@link NoteFile NoteFile}. These are the files that user will be able to download from the site
     */
    private Vector<NoteFile> noteFiles;

    /**
     * Empty constructor. Type is set to a {@link com.easypoli.contentAutomator.Model.Content.ContentType#NULL NULL}
     * value and tags array initialized with 9 cells (as the site's search function looks through 9 tags only, ask
     * Andrea Belli why).
     */
    public Content() {
        title = "";
        description = "";
        type = ContentType.NULL;
        tags = new String[9];
        Arrays.fill(tags, "");
        noteFiles = new Vector<>();
    }

    /**
     * Constructor with almost all details
     * @param title Title String
     * @param description Description String
     * @param type Content type
     * @param tags Tags Strings
     */
    public Content(String title, String description, ContentType type, String ... tags) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.tags = new String[9];
        Arrays.fill(tags, "");
        for (int i = 0; i < tags.length && i < 9; i++) { // Never more than 9 tags
            this.tags[i] = tags[i];
        }
        noteFiles = new Vector<>();
    }

    /**
     * Enum to describe possible content types allowed by EasyPOLI
     */
    public enum ContentType {
        TEORIA("Teoria"), ESERCIZI("Esercizi"), SCHEMA("Schema"), NULL("null");
        /**
         * String representation of ContentType
         */
        private String type;

        /**
         * Enum constructor
         * @param type String representation of ContentType
         */
        ContentType(String type) {
            this.type = type;
        }

        /**
         * Getter for "type" String
         * @return String representation of ContentType
         */
        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return getType();
        }
    }

    /* #####################
     * GETTERS AND SETTERS
     * ##################### */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Vector<NoteFile> getNoteFiles() {
        return noteFiles;
    }

    public void setNoteFiles(Vector<NoteFile> noteFiles) {
        this.noteFiles = noteFiles;
    }

    @Override
    public String toString() {
        return "Content{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", tags=" + Arrays.toString(tags) +
                ", noteFiles=" + noteFiles +
                '}';
    }
}
