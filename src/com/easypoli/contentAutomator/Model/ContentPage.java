package com.easypoli.contentAutomator.Model;

import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.util.Vector;

/**
 * EasyPOLI's content page abstraction. This class describes what we believe a content page is. It should be described
 * with:
 *  - Page Title (what is shown in browser tab)
 *  - Description
 *  - Large Title (What you see in the page as a title on large screens)
 *  - Medium Title (What you see in the page as a title on medium screens)
 *  - Short Title (What you see in the page as a title on large screens)
 *  - A vector of {@link Content Contents}
 *  - File name
 *  - File reference
 *
 * When a ContentPage is instantiated it opens the HTML related file and parse the code to retrieve those infos we
 * need to rebuild the Content Page we downloaded, but as a Java object. Even Contents are parsed and recreated.
 */
public class ContentPage implements Comparable<ContentPage>{
    /**
     * Page title
     */
    private String pageTitle;
    /**
     * Page description
     */
    private String description;
    /**
     * Large screens title
     */
    private String titleLarge;
    /**
     * Medium screens title
     */
    private String titleMedium;
    /**
     * Small screens title
     */
    private String titleSmall;
    /**
     * Vector of Contents
     */
    private Vector<Content> contents;
    /**
     * HTML File reference of this page
     */
    private File contentFile;
    /**
     * FTPClient to download this page HTML file from server
     */
    private FTPClient ftpClient;
    /**
     * File name
     */
    private String fileName;

    /**
     * Constructor overload that loads a page from a FTP server where we already logged in
     * @param ftpClient FTPClient reference to download HTML file of this Content Page
     * @param fileName name of the file (e.g.: analisi_1.html)
     */
    public ContentPage(FTPClient ftpClient, String fileName) {
        // Setting references
        this.ftpClient = ftpClient;
        this.fileName = fileName;
        this.contents = new Vector<>();
        contentFile = new File("/Users/leonardoarcari/Desktop/test/" + fileName); // Create file reference

        // Try opening an outputstream to the file reference just created.
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(contentFile))) {
            ftpClient.retrieveFile(fileName, outputStream); // If fine, we copy the file from the server
        } catch (IOException e) {
            e.printStackTrace();
        }
        parsePageInfo(); // Parse page info
        parseContents(); // Parse contents
    }

    /**
     *  Constructor overload that loads a page from a local file.
     * @param file HTML file reference of this content page
     */
    public ContentPage(File file) {
        contentFile = file;
        this.contents = new Vector<>();
        parsePageInfo(); // Parse page info
        parseContents(); // Parse contents
    }

    /**
     * Parsing HTML file line by line looking for Content Page information
     */
    private void parsePageInfo() {
        String currentLine; // Line of text we evaluate
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(contentFile))) {
            while ((currentLine = bufferedReader.readLine()) != null) { // Until the end of file
                if (currentLine.contains("<title>")) { // Page Title
                    this.pageTitle = currentLine
                            .replace("<title>", "") // Remove unnecessary html code
                            .replace("</title>", "")
                            .trim();
                } else if (currentLine.contains("<meta name=\"description\"")) {
                    this.description = currentLine
                            .replace("<meta name=\"description\" content=\"", "") // Remove unnecessary html code
                            .replace("\"/>", "")
                            .trim();
                } else if (currentLine.contains("<h3 class=\"hidden-sm hidden-md")) {
                    this.titleLarge = currentLine
                            .replace("<h3 class=\"hidden-sm hidden-md hidden-xs\">", "") // Remove unnecessary html code
                            .replace("</h3>", "")
                            .trim();
                } else if (currentLine.contains("<h3 class=\"hidden-sm hidden-lg")) {
                    this.titleMedium = currentLine
                            .replace("<h3 class=\"hidden-sm hidden-lg hidden-xs\">", "") // Remove unnecessary html code
                            .replace("</h3>", "")
                            .trim();
                } else if (currentLine.contains("<h3 class=\"hidden-md hidden-lg\">")) {
                    this.titleSmall = currentLine
                            .replace("<h3 class=\"hidden-md hidden-lg\">", "") // Remove unnecessary html code
                            .replace("</h3>", "")
                            .trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parsing HTML file line by line looking for Content info
     */
    private void parseContents() {
        String currentLine; // Line of text we evaluate
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(contentFile))) {
            while ((currentLine = bufferedReader.readLine()) != null) { // Until the end of file
                if (currentLine.trim().contains("<ul class=\"list\">")) { // We're in Contents block
                    // End of contents block
                    while (!(currentLine = bufferedReader.readLine()).trim().contains("<!-- MIAO -->")) {
                        if (currentLine.trim().equals("<li>")) { // Beginning of a single content block
                            Content tmp = new Content(); // Create a temp content
                            // Until we reach end of signle content block
                            while(!(currentLine = bufferedReader.readLine()).trim().equals("</li>")) {
                                // Content title
                                if (currentLine.contains("<h3 class=\"nome panel-title\">")) {
                                    tmp.setTitle(currentLine // Remove unnecessary html code
                                                    .replace("<h3 class=\"nome panel-title\">", "")
                                                    .replace("</h3>", "")
                                                    .trim()
                                    );
                                // Content description
                                } else if (currentLine.contains("<article class=\"descrizione\">")) {
                                    tmp.setDescription(currentLine // Remove unnecessary html code
                                                        .replace("<article class=\"descrizione\">", "")
                                                        .replace("</article>", "")
                                                        .trim()
                                    );
                                // Content type
                                } else if (currentLine.contains("<strong>Tipologia</strong>")) {
                                    currentLine = currentLine // Remove unnecessary html code
                                            .replace("<article><strong>Tipologia</strong>: <span class=\"tipologia\">", "")
                                            .replace("</span></article>", "")
                                            .trim();
                                    if (currentLine.equals("Teoria")) {
                                        tmp.setType(Content.ContentType.TEORIA);
                                    } else if (currentLine.equals("Esercizi")) {
                                        tmp.setType(Content.ContentType.ESERCIZI);
                                    } else {
                                        tmp.setType(Content.ContentType.SCHEMA);
                                    }
                                // Content Tags
                                } else if (currentLine.contains("<article><strong>Tag contenuti</strong>:")) {
                                    int tagCounter = 0; // Array index iterator
                                    while ((currentLine = bufferedReader.readLine()).contains("<span class=\"tag")
                                            && tagCounter < 9) {
                                        tmp.getTags()[tagCounter] = currentLine // Remove unnecessary html code
                                                                        .trim()
                                                                        .substring(19)
                                                                        .replace("</span>", "")
                                                                        .replace(",", "");
                                        tagCounter++;
                                    }
                                // Content single resource NoteFile
                                } else if (currentLine.contains("<a target=\"_blank\" class=\"btn")) {
                                    // Remove unnecessary html code
                                    currentLine = currentLine.substring(currentLine.indexOf("href="));
                                    currentLine = currentLine.replace("href=\"", "");
                                    currentLine = currentLine.substring(0, currentLine.indexOf("\">Scarica"));
                                    // Add NoteFile to Content
                                    tmp.getNoteFiles().add(new NoteFile(currentLine, NoteFile.NoteFileLocation.SERVER));
                                // Content multiple resource file
                                } else if (currentLine.contains("<ul class=\"nav nav-pills\">")) {
                                    // Check if we're still reading through resources links
                                    while ((currentLine = bufferedReader.readLine()).contains
                                            ("AnalyticsEserciziBlocco")) {
                                        // Remove unnecessary html code
                                            currentLine = currentLine.trim().toLowerCase();
                                            currentLine = currentLine.substring(
                                                            currentLine.indexOf("href="),
                                                            currentLine.indexOf("\">pdf"));
                                            currentLine = currentLine.replace("href=\"", "");
                                            tmp.getNoteFiles().add(new NoteFile( // Add NoteFiles to Content
                                                    currentLine,
                                                    NoteFile.NoteFileLocation.SERVER));
                                    }
                                }
                            } System.out.println(tmp); // Debugging
                            contents.add(tmp); // Add to contents Vector the temp content we recreated
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* #####################
     * GETTERS AND SETTERS
     * ##################### */

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitleLarge() {
        return titleLarge;
    }

    public void setTitleLarge(String titleLarge) {
        this.titleLarge = titleLarge;
    }

    public String getTitleMedium() {
        return titleMedium;
    }

    public void setTitleMedium(String titleMedium) {
        this.titleMedium = titleMedium;
    }

    public String getTitleSmall() {
        return titleSmall;
    }

    public void setTitleSmall(String titleSmall) {
        this.titleSmall = titleSmall;
    }

    public File getContentFile() {
        return contentFile;
    }

    public void setContentFile(File contentFile) {
        this.contentFile = contentFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Vector<Content> getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return titleLarge;
    }

    @Override
    public int compareTo(ContentPage o) {
        if (o == null) return 1;
        return getTitleLarge().compareTo(o.getTitleLarge());
    }
}
