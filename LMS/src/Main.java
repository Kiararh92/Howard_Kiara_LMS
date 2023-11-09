import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.*;
import java.io.*;

/*
 * Kiara Howard, Software Dev I, 9/10/23
 * Class Name Main
 * This class houses everything need to run the LMS program.
 */
public class Main {
    /*
     * Main Method for a Library Management System Consoled Based Application.
     * This program will allow users to add, remove, and display the lists of
     * books owned by the library.
     *
     * Displays the front end menu for user interaction
     *
     * Creates instances for the barCode # and the library.
     * Creates an arraylist for the books in different stages.
     *
     * Calls the varies reader and writer methods to edit text files.
     */

//    public FileReader(String filePath, ArrayList<Book> collection, Library library, ArrayList<Integer> idList, Barcode generator) {
//        this.filePath = filePath;
//        this.collection = collection;
//        this.library = library;
//        this.idList = idList;
//        this.generator = generator;
//    }
    public static void main(String[] args) {

        ArrayList<Book> collection = new ArrayList<>();
        ArrayList <Book> checkedOut = new ArrayList<>();
        ArrayList <Book> removedBooks = new ArrayList<>();

        ArrayList<Integer> idList = new ArrayList<>();

        Library library = new Library(collection, checkedOut, removedBooks);
        Barcode generator = new Barcode(idList);

        FileReader fileReader = new FileReader("Books.txt", collection, library, idList, generator);
        FileReader borrowedReader = new FileReader("CheckedOut.txt",checkedOut, library, idList, generator);
        FileReader removedReader = new FileReader("RemovedBooks.txt", removedBooks, library,idList, generator);


        FWriter fileWriter = new FWriter("Books.txt", collection, library);
        FWriter outWrite = new FWriter("CheckedOut.txt", checkedOut, library);
        FWriter removeWrite = new FWriter("RemovedBooks.txt", removedBooks, library);

        ViaTitle viaTitle = new ViaTitle(library, fileWriter, removeWrite, outWrite);
        ViaBarcode viaBarcode = new ViaBarcode(library, fileWriter,removeWrite, outWrite);

        Patron user1 = new Patron("Patron", "Patron",100);
        StaffMember user2 = new StaffMember("Staff", "Staff", 500);

        fileReader.readPrintFile();
        borrowedReader.readCheckedOut();

        MenuHandler menuHandler = new MenuHandler(library, generator ,collection, idList, checkedOut, removedBooks, fileReader,borrowedReader,
                                                    removedReader,fileWriter,outWrite,removeWrite, viaTitle, viaBarcode, user1, user2);

//        fileReader.readPrintFile();
//        borrowedReader.readCheckedOut();

        //TableModel tableModel = new TableModel(collection, library);

        TableModel tableModel = new TableModel(collection, library);
        JTable table1 = new JTable(tableModel);

//        int testchoice = 0;
//        User testUser = null;
//        String testFilePath = "";
//
//        menuHandler.showMenu(testchoice, testUser, testFilePath);

        try {
            MainFrame mainFrame = new MainFrame(menuHandler,library, collection, fileReader, tableModel, idList, generator, fileWriter,outWrite,removeWrite);
            mainFrame.setVisible(true);
        } catch (Exception e) {
            System.out.println("An error has occurred. You will be returned to the Main Menu to try again.");
            e.printStackTrace();
        }

    }
}

