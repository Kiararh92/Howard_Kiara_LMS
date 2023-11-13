import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

/*
 * Kiara Howard, Software Dev I, 9/10/23
 * Class Name FileReader
 * This class handles uploading, scanning, and printing from
 * a text file either by a predefined text file or a custom one
 * that is input by the user.
 */
public class FileReader {
    Scanner scanner = new Scanner(System.in);
    private final Library library;
    private final Barcode generator;
    private ArrayList<Book> collection;
    private ArrayList<Integer> idList;
    private String filePath;

    public FileReader(String filePath, ArrayList<Book> collection, Library library, ArrayList<Integer> idList, Barcode generator) {
        this.filePath = filePath;
        this.collection = collection;
        this.library = library;
        this.idList = idList;
        this.generator = generator;
    }

    public void readPrintFile() {
        try {
            File fileBooks = new File("Books.txt");
            Scanner myReader = new Scanner(fileBooks);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.split(",");
                if (parts.length >= 4) {
                    String id = parts[0];
                    String title = parts[1];
                    String author = parts[2];
                    String genre = parts[3];

                        String status = (parts.length >= 5) ? parts[4] : "Available";


                    //String status = (parts.length >= 5) ? parts[4] : "Available";
                    LocalDate dueDate = null;
                    if(parts.length >= 6 && !parts[5].isEmpty() && !"null".equals(parts[5])) {
                        dueDate = LocalDate.parse(parts[5]);
                    }

                    int id1 = Integer.parseInt(id);

                    generator.textbarCode(id1);
                    int barCode = generator.getCurrentbarCode();

                    Book book = new Book(barCode, title, author, genre, status, dueDate);

                    library.addBook(book);
                }
            }
            myReader.close();
            System.out.println("Updated library collection.");
            //Handle file not found error
        } catch (FileNotFoundException e) {
            System.out.println("Ann error has occurred.");
            e.printStackTrace();
        }

        for (Book book : library.getBooks()) {
            System.out.println(book.getbarCode() + " " + book.getTitle() + " by " + book.getAuthor() + " " + book.getGenre() + " " + book.getStatus() + " "  + book.getdueDate());
        }
    }

    public void readCheckedOut() {
        try {
            File borrowedBooks = new File("CheckedOut.txt");
            Scanner borrowedReader = new Scanner(borrowedBooks);
            while (borrowedReader.hasNextLine()) {
                String data = borrowedReader.nextLine();
                String[] parts = data.split(",");
                if (parts.length >= 4) {
                    String id = parts[0];
                    String title = parts[1];
                    String author = parts[2];
                    String genre = parts[3];

                    String status = (parts.length >= 5) ? parts[4] : "Available";

                    LocalDate dueDate = null;
                    if(parts.length >= 6 && !parts[5].isEmpty()) {
                        dueDate = LocalDate.parse(parts[5]);
                    }
                    int id1 = Integer.parseInt(id);

                    generator.textbarCode(id1);
                    int barCode = generator.getCurrentbarCode();

                    Book book = new Book(barCode, title, author, genre, status, dueDate);

                    library.addBorrowed(book);
                }
            }
            borrowedReader.close();
            System.out.println("Updated Checked-out collection.");
            //Handle file not found error
        } catch (FileNotFoundException e) {
            System.out.println("Ann error has occurred.");
            e.printStackTrace();
        }
        for (Book book : library.getCheckedOut()) {
            System.out.println(book.getbarCode() + " " + book.getTitle() + " by " + book.getAuthor() + " " + book.getGenre());
        }
    }

    public void readYourText(String FilePath, JTextArea libraryUpTextArea, JLabel statusUpLabel) {

        try {
            File yourtextFile = new File(filePath);
            if (!yourtextFile.exists()) {
                statusUpLabel.setText("The file " + yourtextFile.getAbsolutePath() + " does not exist.");
                return;
            }
            Scanner customFile = new Scanner(yourtextFile);
            while (customFile.hasNextLine()) {
                String data1 = customFile.nextLine();
                String[] parts1 = data1.split(",");
                if (parts1.length >= 4) {
                    String id2 = parts1[0];
                    String title1 = parts1[1];
                    String author1 = parts1[2];
                    String genre1 = parts1[3];

                    String status1 = (parts1.length >= 5) ? parts1[4] : "Available";

                    LocalDate dueDate1 = null;
                    if(parts1.length >= 6 && !parts1[5].isEmpty()) {
                        dueDate1 = LocalDate.parse(parts1[5]);
                    }

                    int id3 = Integer.parseInt(id2);

                    generator.textbarCode(id3);
                    int barCode1 = generator.getCurrentbarCode();

                    Book book = new Book(barCode1, title1, author1, genre1, status1, dueDate1);

                    library.addBook(book);
                }
            }
            customFile.close();
            statusUpLabel.setText("Updated library collection.");
            //Handle file not found error
        } catch (FileNotFoundException ex) {
            statusUpLabel.setText("An error has occurred. You will be returned to the Main Menu to try again.");
            ex.printStackTrace();
        }
        libraryUpTextArea.setText("");
        for (Book book : library.getBooks()) {
            libraryUpTextArea.append(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getGenre() + "\n");
        }
    }
}