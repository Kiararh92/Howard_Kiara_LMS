import javax.swing.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.*;

/*
 * Kiara Howard, Software Dev I, 10/05/23
 * Class Name ViaTitle
 * This class helps reduce redundancy when handling features
 * that deal with finding a book via a title.
 * @param removedTitle User input title of a book to be deleted.
 * @param borrowedTitle User input title for the book they want to check out.
 * @param returnedTitle User input title for the book they
 *                          wish to return/check in.
 */
public class ViaTitle {
    private final Library library;
    private final FWriter fileWriter;
    private final FWriter removeWrite;
    private final FWriter outWrite;
    String removedTitle;
    String borrowedTitle;
    String returnedTitle;
    boolean askedRemoved;
    boolean askedOut;
    boolean askReturn;


    public ViaTitle(Library library,FWriter fileWriter,FWriter removeWrite,FWriter outWrite) {
        this.library = library;
        this.fileWriter = fileWriter;
        this.removeWrite = removeWrite;
        this.outWrite = outWrite;
    }
    /*
     * Removes a book from the collection via Title
     * compares entered title to the one in the collection
     * If there's a match in the collection, the deletion process for book
     * begins and adds it to a removal collection.
     * Updates the status of that book.
     * Displays if book was successfully removed from the database
     * with its information.
     *
     * Used an iterator as the error ConcurrentModificationException
     * kept occurring.
     */
    public void removalViaTitle(JLabel statusReBcLabel, JTextField fieldReT, JTextArea libraryReBcTextArea) {

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("What is the title of the book you would like to delete: ");
            removedTitle = fieldReT.getText();
            Iterator<Book> iterator = library.getBooks().iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                if (removedTitle.equalsIgnoreCase(book.getTitle()) && book.getStatus().equals("A")) {
                    int reBarcode = book.getbarCode();
                    library.updateStatus(reBarcode, "R");
                    iterator.remove();
                    library.addRemoved(book);
                    askedRemoved = true;
                    statusReBcLabel.setText("Book successfully removed.");

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
                    askedRemoved = false;
                }
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
        }
    }

    /*
     * Check out a book from the collection via Title
     * compares entered title to the one in the collection
     * If there's a match in the collection, the check-out process for book
     * begins and adds it to a borrowed collection.
     * Sets a due Date and the status of the book for check out.
     * Displays if book was successfully checked-out with its information.
     *
     * Used an iterator as the error ConcurrentModificationException
     * kept occurring.
     */
    public void borrowedViaTitle(JLabel statusLabel, JTextField fieldCheckOut, JTextArea libraryTextArea) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please provide the title of the book you would like to check out: ");
            borrowedTitle = fieldCheckOut.getText(); //GUI get text instead of scanner to console.
            Iterator<Book> iterator = library.getBooks().iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                //borrowedTitle.equalsIgnoreCase(book.getTitle());
                if (borrowedTitle.equalsIgnoreCase(book.getTitle()) && book.getStatus().equals("A")) {
                    int outBarcode = book.getbarCode();
                    library.updateStatus(outBarcode, "O");
                    LocalDate dueDate = LocalDate.now().plusDays(14);
                    book.setdueDate(dueDate);
                    iterator.remove();
                    library.addBorrowed(book);
                    askedOut = true;
                    statusLabel.setText("Book successfully checked out.");


                    try {
                        Connection con = Main.getConnection();

                        PreparedStatement updated = con.prepareStatement("UPDATE books SET status = ?, due_date = ? WHERE barcode = ?");

                        updated.setString(1, book.getStatus());
                        if(book.getdueDate() != null) {
                            updated.setDate(2, Date.valueOf(book.getdueDate()));
                        }else {
                            updated.setNull(2, java.sql.Types.DATE);
                        }
                        updated.setInt(3, book.getbarCode());


                        int rowsUpdated = updated.executeUpdate();

                        if (rowsUpdated > 0) {
                            System.out.println("Record updated successfully!");
                        } else {
                            System.out.println("Record not found or not updated.");
                        }
                    } catch (Exception ex) {

                        ex.printStackTrace();

                    }
                }

                if (!askedOut) {
                    statusLabel.setText("Book not found or not available for checkout.");
                }
            }
            break;
        }

        for(Book book : library.getCheckedOut()) {
            fileWriter.writeToFile();
            outWrite.writeCheckedOut();
            System.out.println(book.getbarCode() + "," + book.getTitle() + "," + book.getGenre() + " by " + book.getAuthor() + " due on " + book.getdueDate());
        }

        System.out.println("Thank you for checking out today. \n");
        System.out.println("Here is the current library collection after checking out.");

        //Display to GUI Text area instead of console.
        libraryTextArea.setText("");
        for(Book book : library.getBooks()) {
            libraryTextArea.append(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getGenre() + "\n");
        }
    }
    /*
     * Check-in/return a borrowed book via Title.
     * compares entered title to title in the checked out checked-out List
     * If title is in the checked-out List, then removes that book
     * from the checked-out List and adds it back to the book collection.
     * Checks the due date status and returns a messages regarding the due date.
     * Updates the status of the book.
     * Displays if book was successfully checked in.
     * Displays the current book collection after books are returned.
     *
     * Used an iterator as the error ConcurrentModificationException
     * kept occurring.
     */
    public void returnViaTitle(JLabel statusChckInLabel, JTextField fieldCheckIn, JTextArea libraryInTextArea) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please provide the title of the book you would like to return today: ");
            returnedTitle = fieldCheckIn.getText();
            Iterator<Book> iterator = library.getCheckedOut().iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                if (returnedTitle.equalsIgnoreCase(book.getTitle()) && book.getStatus().equals("O")) {
                    int inBarcode = book.getbarCode();
                    iterator.remove();
                    library.addBook(book);
                    library.dueDateStatus(book.getdueDate());
                    library.updateStatus(inBarcode, "A");
                    book.setdueDate(null);
                    statusChckInLabel.setText("Book successfully checked-in.");
                    askReturn = true;

                    try {
                        Connection con = Main.getConnection();

                        PreparedStatement updated = con.prepareStatement("UPDATE books SET status = ?, due_date = ? WHERE barcode = ?");

                        updated.setString(1, book.getStatus());
                        if(book.getdueDate() != null) {
                            //updated.setDate(2, Date.valueOf(book.getdueDate()));
                            updated.setNull(2, java.sql.Types.DATE);
                        }else {
                            updated.setNull(2, java.sql.Types.DATE);
                        }
                        updated.setInt(3, book.getbarCode());


                        int rowsUpdated = updated.executeUpdate();

                        if (rowsUpdated > 0) {
                            System.out.println("Record updated successfully!");
                        } else {
                            System.out.println("Record not found or not updated.");
                        }
                    } catch (Exception ex) {

                        ex.printStackTrace();

                    }

                } else {
                    statusChckInLabel.setText("That book is not currently checked-out.");
                    askReturn = false;
                }
            }
            break;
        }
        for (Book book : library.getBooks()) {
            outWrite.writeCheckedOut();
            fileWriter.writeToFile();
        }
        System.out.println("Thank you for returning your books today. \n");
        System.out.println("Here is the current library collection after returning your books.");
        libraryInTextArea.setText("");
        for (Book book : library.getBooks()) {
            libraryInTextArea.append(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getGenre() + "\n");
            System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getGenre());
        }
    }
}

