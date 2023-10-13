import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    //adding a book to the database
    @Test
    void addBook() {
        ArrayList<Book> collection = new ArrayList<>();
        ArrayList <Book> checkedOut = new ArrayList<>();
        ArrayList <Book> removedBooks = new ArrayList<>();

        ArrayList<Integer> idList = new ArrayList<>();

        Library libraryTest = new Library(collection, checkedOut, removedBooks);
        Barcode generator = new Barcode(idList);

        Book bookTest = new Book(01,"A Game of Thrones","George R.R. Martin","Fantasy");
        libraryTest.addBook(bookTest);
        assertTrue(libraryTest.getBooks().contains(bookTest));
    }

    @Test
    void removalViaBarcode() {
        ArrayList<Book> collection = new ArrayList<>();
        ArrayList<Book> checkedOut = new ArrayList<>();
        ArrayList<Book> removedBooks = new ArrayList<>();
        Library libraryTest = new Library(collection, checkedOut, removedBooks);
        FWriter fileWriter = new FWriter("Books.txt", collection, libraryTest);
        FWriter outWrite = new FWriter("CheckedOut.txt", checkedOut, libraryTest);
        FWriter removeWrite = new FWriter("RemovedBooks.txt", removedBooks, libraryTest);

        ArrayList<Integer> idList = new ArrayList<>();

        Barcode generator = new Barcode(idList);

        Book bookTest = new Book(01,"A Game of Thrones","George R.R. Martin","Fantasy");
        libraryTest.addBook(bookTest);

        ViaBarcode viaBarcodeTest = new ViaBarcode(libraryTest, fileWriter, removeWrite, outWrite);

        String removedBarcode = "1\n";
        System.setIn(new ByteArrayInputStream(removedBarcode.getBytes()));
        viaBarcodeTest.removalViaBarcode();

        assertFalse(libraryTest.getBooks().stream().anyMatch(book -> book.getbarCode() == 1));
    }

    @Test
    void removalViaTitle(){
        ArrayList<Book> collection = new ArrayList<>();
        ArrayList<Book> checkedOut = new ArrayList<>();
        ArrayList<Book> removedBooks = new ArrayList<>();
        Library libraryTest = new Library(collection, checkedOut, removedBooks);
        FWriter fileWriter = new FWriter("Books.txt", collection, libraryTest);
        FWriter outWrite = new FWriter("CheckedOut.txt", checkedOut, libraryTest);
        FWriter removeWrite = new FWriter("RemovedBooks.txt", removedBooks, libraryTest);

        ArrayList<Integer> idList = new ArrayList<>();

        Barcode generator = new Barcode(idList);

        Book bookTest = new Book(01,"A Game of Thrones","George R.R. Martin","Fantasy");
        libraryTest.addBook(bookTest);

        ViaTitle viaTitleTest = new ViaTitle(libraryTest, fileWriter, removeWrite, outWrite);

        String removedTitle = "A Game of Thrones\n";
        System.setIn(new ByteArrayInputStream(removedTitle.getBytes()));
        viaTitleTest.removalViaTitle();

        assertFalse(libraryTest.getBooks().stream().anyMatch(book -> Objects.equals(book.getTitle(), removedTitle)));
    }

    @Test
    void checkedoutViaBarcode() {
        ArrayList<Book> collection = new ArrayList<>();
        ArrayList<Book> checkedOut = new ArrayList<>();
        ArrayList<Book> removedBooks = new ArrayList<>();

        Library libraryTest = new Library(collection, checkedOut, removedBooks);
        FWriter fileWriter = new FWriter("Books.txt", collection, libraryTest);
        FWriter outWrite = new FWriter("CheckedOut.txt", checkedOut, libraryTest);
        FWriter removeWrite = new FWriter("RemovedBooks.txt", removedBooks, libraryTest);

        ArrayList<Integer> idList = new ArrayList<>();

        Barcode generator = new Barcode(idList);

        Book bookTest = new Book(01,"A Game of Thrones","George R.R. Martin","Fantasy");
        libraryTest.addBook(bookTest);

        ViaBarcode viaBarcodeTest = new ViaBarcode(libraryTest, fileWriter, removeWrite, outWrite);

        String borrowedBarcode = "1\n";
        System.setIn(new ByteArrayInputStream(borrowedBarcode.getBytes()));
        viaBarcodeTest.checkedoutViaBarcode();

        assertFalse(libraryTest.getBooks().stream().anyMatch(book -> book.getbarCode() == 1));
        assertTrue(libraryTest.getCheckedOut().contains(bookTest));
        assertEquals("Checked-Out", bookTest.getStatus());
        assertNotNull(bookTest.getdueDate());
    }

    @Test
    void returnedViaBarcode() {
        ArrayList<Book> collection = new ArrayList<>();
        ArrayList<Book> checkedOut = new ArrayList<>();
        ArrayList<Book> removedBooks = new ArrayList<>();

        Library libraryTest = new Library(collection, checkedOut, removedBooks);
        FWriter fileWriter = new FWriter("Books.txt", collection, libraryTest);
        FWriter outWrite = new FWriter("CheckedOut.txt", checkedOut, libraryTest);
        FWriter removeWrite = new FWriter("RemovedBooks.txt", removedBooks, libraryTest);

        ArrayList<Integer> idList = new ArrayList<>();

        Barcode generator = new Barcode(idList);

        Book bookTest = new Book(01,"A Game of Thrones","George R.R. Martin","Fantasy");
        libraryTest.addBook(bookTest);

        ViaBarcode viaBarcodeTest = new ViaBarcode(libraryTest, fileWriter, removeWrite, outWrite);

        String returnedBarcode = "1\n";
        System.setIn(new ByteArrayInputStream(returnedBarcode.getBytes()));
        viaBarcodeTest.returnViaBarcode();

        assertFalse(libraryTest.getCheckedOut().stream().anyMatch(book -> book.getbarCode() == 1));
        assertTrue(libraryTest.getBooks().contains(bookTest));
        assertEquals("Available", bookTest.getStatus());
        assertNull(bookTest.getdueDate());
    }

}
