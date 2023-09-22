public class Book extends Main{

    String title;
    String author;
    int id1;
    /*
     * Constructs a new Book object with the given title, author,
     * and isbn.
     * @param isbn The ISBN/ID # of the book.
     * @param title The tile of the book.
     * @param author The author of the book.
     */
    public Book(int id1, String title, String author) {
        this.id1 = id1;
        this.title = title;
        this.author = author;
    }

    public int getID1() {
        return id1;
    }

    public void setID1(int id1){
        this.id1 = id1;
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
