import java.util.*;
public class Book{

    String title;
    String author;
    int barCode;
    String genre;
    String status;

    /*
     * Constructs a new Book object with the given title, author,
     * and isbn.
     * @param isbn The ISBN/ID # of the book.
     * @param title The tile of the book.
     * @param author The author of the book.
     */
    public Book(int barCode, String title, String author) {
        this.barCode = barCode;
        this.title = title;
        this.author = author;
    }
    /*
     * Receives the Barcode of the book
     * @return current barCode of the book
     */
    public int getbarCode() {
        return barCode;
    }
    /*
     * Sets the Barcode of the book.
     * @param barCode The new value to set for barCode.
     */
    public void setbarCode(int barCode){
        this.barCode = barCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }
}
