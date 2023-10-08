import java.util.*;

/*
 * Kiara Howard, Software Dev I, 10/05/23
 * Class Name Library
 * This class helps reduce redundancy when handling features
 * that deal with finding a book via a title.
 * @param removedTitle User input title of a book to be deleted.
 * @param borrowedTitle User input title for the book they want to check out.
 * @param
 */
public class ViaTitle {
    private final Library library;
    private final FWriter fileWriter;
    private final FWriter removeWrite;
    private final FWriter outWrite;
    String removedTitle;
    String borrowedTitle;
    boolean askedRemoved;


    public ViaTitle(Library library,FWriter fileWriter,FWriter removeWrite,FWriter outWrite) {
        this.library = library;
        this.fileWriter = fileWriter;
        this.removeWrite = removeWrite;
        this.outWrite = outWrite;
    }

    public void removalViaTitle() {

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("What is the title of the book you would like to delete: ");
            removedTitle = scanner.nextLine();
            Iterator<Book> iterator = library.getBooks().iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                if (Objects.equals(book.getTitle(), removedTitle)) {
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
        System.out.println("\n");
        System.out.println("Here is the current library collection after deletion.");
        for(Book book : library.getBooks()) {
            System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor());
        }
    }

    public void borrowedViaTitle() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please provide the title of the book you would like to check out: ");
            borrowedTitle = scanner.nextLine();
            Iterator<Book> iterator = library.getBooks().iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                if (Objects.equals(book.getTitle(), borrowedTitle)) {
                    iterator.remove();
                    library.addBorrowed(book);
                    askedRemoved = true;
                } else {
                    askedRemoved = false;
                }
            }
            break;
        }
        System.out.println("Book successfully checked out.");
        for(Book book : library.getCheckedOut()) {
            fileWriter.writeToFile();
            outWrite.writeCheckedOut();
            System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor());
        }
        System.out.println("\n");
        System.out.println("Here is the current library collection after checking out.");
        for(Book book : library.getBooks()) {
            System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor());
        }
    }

}

