import java.time.LocalDate;
import java.util.*;
/*
 * Kiara Howard, Software Dev I, 9/10/23
 * Class Name Book
 * This class gathers and sets the author, title, status,
 * genre, and barcode of a book.
 */
public class Book{

    String title;
    String author;
    int barCode;
    String genre;
    String status;
    LocalDate dueDate;

    /*
     * Constructs a new Book object with the given title, author,
     * status, genre, and isbn.
     * @param isbn The ISBN/ID # of the book.
     * @param title The tile of the book.
     * @param author The author of the book.
     * @param status The current status of the book.
     * @param genre The genre of the book.
     */
    public Book(int barCode, String title, String author, String genre,String status,LocalDate dueDate) {
        this.barCode = barCode;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.dueDate = dueDate;

        if(status == null || status.isEmpty()) {
            this.status = "Available";
        } else {
            this.status = status;
        }
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

    public String getStatus(){
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getGenre(){
        return genre;
    }
    public void setGenre(String genre){
        this.genre = genre;
    }

    public LocalDate getdueDate(){
        return dueDate;
    }
    public void setdueDate(LocalDate dueDate){
        this.dueDate = dueDate;
    }
}
