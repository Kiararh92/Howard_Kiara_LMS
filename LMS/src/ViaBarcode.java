import javax.swing.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.*;
import java.io.*;

/*
 * Kiara Howard, Software Dev I, 10/05/23
 * Class Name ViaBarcode
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
    boolean found = true;
    boolean anotherIn = true;
    String option;

    public ViaBarcode(Library library, FWriter fileWriter, FWriter removeWrite, FWriter outWrite) {
        this.library = library;
        this.fileWriter = fileWriter;
        this.removeWrite = removeWrite;
        this.outWrite = outWrite;
    }

    /*
     * Removes a book from the collection via Barcode
     * compares entered barcode to the one in the collection
     * If there's a match in the collection, the deletion process for book
     * begins and adds it to a removal collection.
     * Updates the status of that book.
     * Displays if book was successfully removed from the database
     * with its information.
     *
     * Used an iterator as the error ConcurrentModificationException
     * kept occurring.
     */
    public void removalViaBarcode(JLabel statusReBcLabel, JTextField fieldReBc, JTextArea libraryReBcTextArea){

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("What is the barcode of the book you would like to delete: ");
            if(!fieldReBc.getText().isEmpty()) {
                removedBarcode = Integer.parseInt(fieldReBc.getText());
                Iterator<Book> iterator = library.getBooks().iterator();
                while (iterator.hasNext()) {
                    Book book = iterator.next();
                    if (book.getbarCode() == removedBarcode && book.getStatus().equals("A")) {
                        library.updateStatus(removedBarcode, "R");
                        iterator.remove();
                        library.addRemoved(book);
                        askedRemoved = true;

                        try {
                            Connection con = Main.getConnection();

                            PreparedStatement removed = con.prepareStatement("DELETE FROM books WHERE barcode = ?");

                            removed.setInt(1, book.getbarCode());

                            int rowsUpdated = removed.executeUpdate();

                            if (rowsUpdated > 0) {
                                statusReBcLabel.setText("Book successfully removed.");
                                System.out.println("Record removed successfully!");
                            } else {
                                statusReBcLabel.setText("Book not found.");
                                System.out.println("Record not found or not removed.");
                            }
                        } catch (Exception ex) {

                            ex.printStackTrace();

                        }

                    } else {
                        statusReBcLabel.setText("Barcode not found.");
                        askedRemoved = false;
                    }
                }
            } else {
                statusReBcLabel.setText("Barcode cannot be empty.");
            }
            break;
        }
        for (Book book : library.getRemoved()) {
            fileWriter.writeToFile();
            removeWrite.writeRemoved();
            statusReBcLabel.setText("Book successfully deleted.");
            System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getGenre());
        }
        System.out.println("Book Deletion Finished. \n");
        System.out.println("Here is the current library collection after deletion.");
        libraryReBcTextArea.setText("");
        for(Book book : library.getRemoved()) {
            libraryReBcTextArea.append(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getGenre() + "\n");
            System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getGenre() + "\n");
        }
    }

    /*
     * Check out a book from the collection via Barcode #
     * compares entered barcode to the one in the collection
     * If there's a match in the collection, the check-out process for book
     * begins and adds it to a borrowed collection.
     * Sets a due Date and the status of the book for check out.
     * Displays if book was successfully checked-out with its information.
     *
     * Used an iterator as the error ConcurrentModificationException
     * kept occurring.
     */
    public void checkedoutViaBarcode(){
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("To begin checking out, please provide the book's barcode:");
            borrowedBarcode = scanner.nextInt();
            Iterator<Book> iterator = library.getBooks().iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                if (book.getbarCode() == borrowedBarcode) {
                    library.updateStatus(borrowedBarcode, "C");
                    LocalDate dueDate = LocalDate.now().plusDays(14);
                    //LocalDate dueDate = LocalDate.now().minusDays(2);
                    book.setdueDate(dueDate);
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
            System.out.println(book.getbarCode() + "," + book.getTitle() + "," + book.getGenre() + " by " + book.getAuthor() + " due on " + book.getdueDate());
        }
        System.out.println("Thank you for checking out today. \n");
        System.out.println("Here is the current library collection after checking out.");

        for(Book book : library.getBooks()) {
            System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getGenre());
        }
    }
    /*
     *  Check-in/return a borrowed book via Barcode #.
     * compares entered barcode to barcode in the checked out checked-out List
     * If barcode is in the checked-out List, then removes that book
     * from the checked-out List and adds it back to the book collection.
     * Checks the due date status and returns a messages regarding the due date.
     * Updates the status of the book.
     * Displays if book was successfully checked in.
     * Displays the current book collection after books are returned.
     *
     * Used an iterator as the error ConcurrentModificationException
     * kept occurring.
     */
    public void returnViaBarcode() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please provide the barcode # of the book you're returning today.");
            returnedBarcode = scanner.nextInt();
            scanner.hasNextLine();
            Iterator<Book> iterator = library.getCheckedOut().iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                if (Objects.equals(book.getbarCode(), returnedBarcode)) {
                    iterator.remove();
                    library.addBook(book);
                    library.dueDateStatus(book.getdueDate());
                    library.updateStatus(returnedBarcode, "A");
                    book.setdueDate(null);
                    found = true;
                    System.out.println("Book successfully checked-in.");
                    System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getGenre());
                } else if (!library.getCheckedOut().contains(returnedBarcode)){
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
        System.out.println("Thank you for returning your books.\n");
        System.out.println("Here is the current library collection after returning your books.");
        for (Book book1 : library.getBooks()) {
            fileWriter.writeToFile();
            outWrite.writeCheckedOut();
            System.out.println(book1.getbarCode() + " " + book1.getTitle() + " " + book1.getAuthor() + " " + book1.getGenre());
        }
    }
}
