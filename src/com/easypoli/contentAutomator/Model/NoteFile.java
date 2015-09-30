package com.easypoli.contentAutomator.Model;

import java.io.File;

/**
 * NoteFile is a common File with a property we needed: what's file location? Server or local? This property will be
 * useful for deciding what to do with those file (eg: we only need to upload local files on the server)
 */
public class NoteFile extends File {
    /**
     * File location
     */
    private NoteFileLocation location;

    /**
     * Constructor
     * @param filePath File Path
     * @param location {@link com.easypoli.contentAutomator.Model.NoteFile.NoteFileLocation Location} of the file
     */
    public NoteFile(String filePath, NoteFileLocation location) {
        super(filePath);
        this.location = location;
    }

    /* #####################
     * GETTERS AND SETTERS
     * ##################### */

    public NoteFileLocation getLocation() {
        return location;
    }

    public void setLocation(NoteFileLocation location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NoteFile noteFile = (NoteFile) o;

        return location == noteFile.location;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + location.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NoteFile{" +
                "location=" + location +
                '}';
    }

    /**
     * Enum to define possible Locations for a file.
     *  - Server: already available on FTP server
     *  - Local: has to be uploaded
     */
    public enum NoteFileLocation {
        SERVER, LOCAL, NULL;
    }
}

