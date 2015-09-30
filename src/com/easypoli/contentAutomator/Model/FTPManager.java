package com.easypoli.contentAutomator.Model;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.util.Vector;

/**
 * Utility class for handling FTP actions useful for this program, like logging in or downloading Content Pages.
 */
public class FTPManager {
    /**
     * Username String
     */
    private String username;
    /**
     * Password String
     */
    private String password;
    /**
     * FTPClient reference
     */
    private FTPClient ftp;
    /**
     * Server address
     */
    private String server = "ftp.easypoli.it";
    /**
     * FTP ReplyCode
     */
    private int replyCode;
    /**
     * Login result value
     */
    private boolean error;

    /**
     * Constructor
     * @param username login username
     * @param password login password
     */
    public FTPManager(String username, String password) {
        this.username = username;
        this.password = password;
        this.ftp = new FTPClient();
    }

    /**
     * Login
     * @return true if login went fine. False if error occurred
     */
    public boolean login() {
        try {
            error = false;
            ftp.connect(server); // Try to reach server
            replyCode = ftp.getReplyCode();
            if (FTPReply.isPositiveCompletion(replyCode)) { // If replycode is positive
                error = ftp.login(username, password); // Actually login
                if (error) {
                    ftp.setControlKeepAliveTimeout(300);    // Ping the server every 300 seconds to be sure it doesn't
                                                            // close the connection up
                }
            } else { // If login went wrong
                try {
                    ftp.disconnect(); // Disconnect
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } return error; // Return login result value
    }

    /**
     * Retrieve all the HTML content pages from FTP server, create their java objects representation and return a
     * vector filled with them all
     * @return Vector filled with ContentPage objects from site. {@code null} if no file is downloaded.
     */
    public Vector<ContentPage> downloadData() {
        Vector<ContentPage> pages = new Vector<>();
        if (error) { // if login was fine (we don't know where this method could be called)
            try {
                ftp.enterLocalPassiveMode(); // Apparently it's the only way to be sure to download all the pages
                FTPFile[] ftpFiles = ftp.listFiles(); // List file in ftp server root
                for (FTPFile f : ftpFiles) {
                    if (f.getName().endsWith(".html") && !(f.getName().equals("index.html")) && !(f.getName()
                            .equals("about_us.html"))) { // If it's a content page file
                        ContentPage tmp = new ContentPage(ftp, f.getName()); // Create a new tmp ContentPage
                        pages.add(tmp); // Add it to the vector
                        System.out.println(tmp.toString()); // Debugging
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        // Return statements
        if (pages.isEmpty())
            return null;
        else
            return pages;
    }

    /* ########
     * GETTERS
     * ######## */
    public FTPClient getFtp() {
        return ftp;
    }

    public boolean isError() {
        return error;
    }
}
