import java.util.*;
import java.io.*;

/*
 * Kiara Howard, Software Dev I, 10/05/23
 * Class Name Library
 * This class helps reduce redundancy when handling features
 * that deal with finding a book by its barcode.
 * @param removedBarcode User input barcode of a book to be deleted.
 * @param borrowedBarcode User input barcode for the book they want to check out.
 * @param
 */
public class ViaBarcode {
    private final Library library;
    private final FWriter fileWriter;
    private final FWriter removeWrite;
    private final FWriter outWrite;

    int removedBarcode;
    int borrowedBarcode;
    int returnedBarcode;

    boolean askedRemoved;
    boolean askedOut;
    boolean found = false;
    boolean anotherIn = true;
    String option;

    public ViaBarcode(Library library, FWriter fileWriter, FWriter removeWrite, FWriter outWrite) {
        this.library = library;
        this.fileWriter = fileWriter;
        this.removeWrite = removeWrite;
        this.outWrite = outWrite;
    }

    public void removalViaBarcode(){

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("What is the barcode of the book you would like to delete: ");
            removedBarcode = scanner.nextInt();
            Iterator<Book> iterator = library.getBooks().iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                if (book.getbarCode() == removedBarcode) {
                    iterator.remove();
                    library.addRemoved(book);
                    askedRemoved = true;
                } else {
                    askedRemoved = false;
                }
            }
            break;
        }
        for (Book book : library.getRemoved()) {
            fileWriter.writeToFile();
            removeWrite.writeRemoved();
            System.out.println("Book successfully deleted.");
            System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor());
        }
        System.out.println("Book Deletion Finished. \n");
        System.out.println("Here is the current library collection after deletion.");
        for(Book book : library.getBooks()) {
            System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor());
        }
    }

    public void checkedoutViaBarcode(){
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("To begin checking out, please provide the book's barcode:");
            borrowedBarcode = scanner.nextInt();
            Iterator<Book> iterator = library.getBooks().iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                if (book.getbarCode() == borrowedBarcode) {
                    iterator.remove();
                    library.addBorrowed(book);
                    askedOut = true;
                } else {
                    askedOut = false;
                }
            }
            break;
        }
        for (Book book : library.getCheckedOut()) {
            fileWriter.writeToFile();
            outWrite.writeCheckedOut();
            System.out.println("Book successfully checked out.");
            System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor());
        }
        System.out.println("Thank you for checking out today. \n");
        System.out.println("Here is the current library collection after checking out.");
        for(Book book : library.getBooks()) {
            System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor());
        }
    }

    public void returnViaBarcode() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please provide the barcode # of the book you're returning today.");
            returnedBarcode = scanner.nextInt();
            scanner.hasNextLine();

            Iterator<Book> iterator = library.getCheckedOut().iterator();

            while (iterator.hasNext()) {
                Book book = iterator.next();
                if (book.getbarCode() == returnedBarcode) {
                    iterator.remove();
                    library.addBook(book);
                    found = true;
                    System.out.println("Book successfully checked-in.");
                    System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor());
                    scanner.nextLine();
                    System.out.println("Would you like to check-in another book? Yes or No?");
                    option = scanner.nextLine();

                    if (!option.equalsIgnoreCase("Yes")) {
                        anotherIn = false;
                    }
                    //System.out.println("Successful check in.");
                    //break;
                } else {
                    found = false;
                }
                if (!found) {
                    System.out.println("That book is not currently checked-out.");
                    anotherIn = false;
                }
                break;
            }
            break;
        }
        //found = false;
//        for (Book book : library.getBooks()) {
//            fileWriter.writeCheckedOut();
//            outWrite.writeToFile();
//            //System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor());
//        }

        System.out.println("Thank you for returning your books.\n");
        System.out.println("Here is the current library collection after checking out.");
        for (Book book1 : library.getBooks()) {
            fileWriter.writeToFile();
            outWrite.writeCheckedOut();
            System.out.println(book1.getbarCode() + " " + book1.getTitle() + " " + book1.getAuthor());
        }
    }
}
