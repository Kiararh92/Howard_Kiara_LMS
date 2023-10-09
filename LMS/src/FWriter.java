import java.io.*;
import java.util.*;
/*
 * Kiara Howard, Software Dev I, 9/14/23
 * Class Name FWriter
 * This class handles the writing to a specific text file, when
 * the status of a book is changed and saved.
 */
public class FWriter {
    private final Library library;
    private final ArrayList<Book> collection;
    private final String fileUpdate;


    public FWriter(String fileUpdate, ArrayList<Book> collection, Library library) {
        this.fileUpdate = fileUpdate;
        this.collection = collection;
        this.library = library;
    }

    public void writeToFile() {
        try(FileWriter myWriter = new FileWriter(fileUpdate)) {
            for (Book book : library.getBooks()) {
                String line = book.getbarCode() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getGenre();
                myWriter.write(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeCheckedOut() {
        try(FileWriter outWrite = new FileWriter(fileUpdate)) {
            for (Book book : library.getCheckedOut()) {
                String line1 = book.getbarCode() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getGenre();
                outWrite.write(line1 + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeRemoved() {
        try (FileWriter removeWrite = new FileWriter(fileUpdate)) {
            for (Book book : library.getRemoved()) {
                String line2 = book.getbarCode() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getGenre();
                removeWrite.write(line2 + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        }
    }
}
