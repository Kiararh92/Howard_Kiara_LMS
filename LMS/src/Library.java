import java.util.*;

public class Library extends Main{

    public ArrayList <Book> collection;

    public Library(ArrayList <Book> collection) {
        this.collection = collection;
    }

    public void addBook(Book book) {
        collection.add(book);
    }

    public ArrayList <Book> getBooks() {
        return collection;
    }
}
