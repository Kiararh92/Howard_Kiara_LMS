import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {

           Scanner scanner = new Scanner(System.in);

           ArrayList <Book> collection = new ArrayList<>();

           Library library = new Library(collection);

           int choice;



           while(true) {

               System.out.println("Menu");
               System.out.println("1. Update text file");
               System.out.println("2. Add a book to the collection");
               //System.out.println("3. Remove a book from the collection");
               System.out.println("4. Display the library collection");
               System.out.println("5. Exit");
               System.out.println("Enter Your Choice: ");

               choice = scanner.nextInt();

               switch (choice) {
                   case 1:


                       break;

                   case 2:
                       scanner.nextLine();
                       System.out.println("isbn:");
                       String id = scanner.nextLine();
                       int id1 = Integer.parseInt(id);
                       System.out.println("title:");
                       String title = scanner.nextLine();
                       System.out.println("author:");
                       String author = scanner.nextLine();

                       Book book = new Book(id1, title, author);
                       library.addBook(book);

                       break;

                   case 3:

                       break;

                   case 4:

                       System.out.println("Book List");
                       for (Book book1: library.getBooks()) {
                           System.out.println(book1.getID1() + " " + book1.getTitle() + " " + book1.getAuthor());
                       }
                       break;

                   case 5:
                       System.out.println("Goodbye");
                       System.exit(0);
               }
           }
    }
}