import java.util.*;

public class Library{
    /*
     * Kiara Howard, Software Dev I, 9/10/23
     * Class Name Library
     * This class adds and removes books to two different ArrayLists that was
     * created to host all available and borrowed books.
     */
    public ArrayList <Book> collection;

    public ArrayList <Book> checkedOut;
    /*
     * Constructs a new Library object.
     *
     * @param collection An ArrayList containing the collection of all the books.
     *                   Pass an existing ArrayList or creat a new one to initialize
     *                   the library's collection.
     */
    public Library(ArrayList <Book> collection, ArrayList <Book> checkedOut) {
        this.collection = collection;
        //checkedOut = new ArrayList<Book>();
        this.checkedOut = checkedOut;
    }
    /*
     * Adds a new book to the collection.
     *
     * @param Book book A book object containing the Barcode, Title, and Author
     *                       of the book.
     */
    public void addBook(Book book) {
        collection.add(book);
    }
    /*
     * Adds a new book to the checkedOut ArrayList.
     *
     * @param Book book A book object containing the Barcode, Title, and Author
     *                       of the book.
     */
    public void addBorrowed(Book book) {
        checkedOut.add(book);
    }

/*    public void checkIn(Book book) {
        collection.add(book);
        checkedOut.remove(book);
    }
 */
    /*
     * Retrieves the collection of books in the library.
     *
     * @return An ArrayList containing all the books in the library.
     */
    public ArrayList <Book> getBooks() {
        return collection;
    }
    /*
     * Retrieves the collection of books borrowed from the library.
     *
     * @return An ArrayList containing all books that have been checked out.
     */
    public ArrayList<Book> getCheckedOut() {
        return checkedOut;
    }
}
