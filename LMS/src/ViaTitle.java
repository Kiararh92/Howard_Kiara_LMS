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
    public void removalViaTitle() {

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("What is the title of the book you would like to delete: ");
            removedTitle = scanner.nextLine();
            Iterator<Book> iterator = library.getBooks().iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                if (Objects.equals(book.getTitle(), removedTitle)) {
                    int reBarcode = book.getbarCode();
                    library.updateStatus(reBarcode, "Deleted");
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
            System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getGenre());
        }
        System.out.println("Book Deletion Finished. \n");
        System.out.println("Here is the current library collection after deletion.");
        for(Book book : library.getBooks()) {
            System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getGenre());
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
    public void borrowedViaTitle() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please provide the title of the book you would like to check out: ");
            borrowedTitle = scanner.nextLine();
            Iterator<Book> iterator = library.getBooks().iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                if (Objects.equals(book.getTitle(), borrowedTitle)) {
                    int outBarcode = book.getbarCode();
                    library.updateStatus(outBarcode, "Checked-Out");
                    LocalDate dueDate = LocalDate.now().plusDays(14);
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
        System.out.println("Book successfully checked out.");
        for(Book book : library.getCheckedOut()) {
            fileWriter.writeToFile();
            outWrite.writeCheckedOut();
            System.out.println(book.getbarCode() + "," + book.getTitle() + "," + book.getGenre() + " by " + book.getAuthor() + " due on " + book.getdueDate());
        }
        System.out.println("Thank you for checking out today. \n");
        System.out.println("Here is the current library collection after checking out.");
        for(Book book : library.getBooks()) {
            System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getGenre());
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
    public void returnViaTitle() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please provide the title of the book you would like to return today: ");
            returnedTitle = scanner.nextLine();
            Iterator<Book> iterator = library.getCheckedOut().iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                if (Objects.equals(book.getTitle(), returnedTitle)) {
                    int inBarcode = book.getbarCode();
                    iterator.remove();
                    library.addBook(book);
                    library.dueDateStatus(book.getdueDate());
                    library.updateStatus(inBarcode, "Available");
                    book.setdueDate(null);
                    System.out.println("Book successfully checked-in.");
                    askReturn = true;
                } else {
                    System.out.println("That book is not currently checked-out.");
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
        for (Book book : library.getBooks()) {
            System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getGenre());
        }
    }
}

