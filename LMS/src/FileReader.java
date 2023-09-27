import java.io.*;
import java.util.*;
public class FileReader {
    Scanner scanner = new Scanner(System.in);
    private final Library library;
    private final Barcode generator;
    private ArrayList<Book> collection;
    private ArrayList<Integer> idList;
    private String filePath;

    public FileReader(String filePath, ArrayList<Book> collection, Library library, ArrayList<Integer> idList, Barcode generator) {
        this.filePath = filePath;
        this.collection = collection;
        this.library = library;
        this.idList = idList;
        this.generator = generator;
    }

    public void readPrintFile() {
        try{
            File fileBooks = new File("Books.txt");
            Scanner myReader = new Scanner(fileBooks);
            while(myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.split(",");
                if(parts.length == 3) {
                    String id = parts[0];
                    String title = parts[1];
                    String author = parts[2];
                    int id1 = Integer.parseInt(id);

                    generator.textbarCode(id1);
                    int barCode = generator.getCurrentbarCode();

                    Book book = new Book(barCode, title, author);

                    library.addBook(book);
                }
            }
            myReader.close();
            System.out.println("Updated library collection.");
            //Handle file not found error
        } catch (FileNotFoundException e) {
            System.out.println("Ann error has occurred.");
            e.printStackTrace();
        }

        for (Book book : library.getBooks()) {
            System.out.println(book.getbarCode() + " " + book.getTitle() + " by " + book.getAuthor());
        }
    }

}
