import java.io.*;
import java.util.*;

public class FWriter {
    private final Library library;
    private final ArrayList<Book> collection;
    private final String fileUpdate;

    //private final String bfileUpdate;


    public FWriter(String fileUpdate, ArrayList<Book> collection, Library library) {
        this.fileUpdate = fileUpdate;
        this.collection = collection;
        this.library = library;
    }

    public void writeToFile() {
        try(FileWriter myWriter = new FileWriter(fileUpdate)) {
            for (Book book : library.getBooks()) {
                System.out.println(book.getbarCode() + " " + book.getTitle() + " by " + book.getAuthor());
                String line = book.getbarCode() + "," + book.getTitle() + "," + book.getAuthor();
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
                System.out.println(book.getbarCode() + " " + book.getTitle() + " by " + book.getAuthor());
                String line1 = book.getbarCode() + "," + book.getTitle() + "," + book.getAuthor();
                outWrite.write(line1 + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
