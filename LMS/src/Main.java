import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {

           Scanner scanner = new Scanner(System.in);

           ArrayList <Book> collection = new ArrayList<>();
           ArrayList <Book> checkedOut = new ArrayList<>();
           ArrayList<Integer> idList = new ArrayList<>();

           Library library = new Library(collection, checkedOut);
           //Library libraryCO = new Library(checkedOut);
           Barcode generator = new Barcode(idList);

           int choice;

           FileReader fileReader = new FileReader("Books.txt", collection, library, idList, generator);
           FWriter fileWriter = new FWriter("Books.txt", collection, library);
           FWriter outWrite = new FWriter("CheckedOut", checkedOut, library);

           fileReader.readPrintFile();

           while(true) {

               System.out.println("Menu");
               System.out.println("1. Update text file");
               System.out.println("2. Add a book to the collection");
               System.out.println("3. Check-out a book");
               System.out.println("4. Display the library collection");
               System.out.println("5. Display books currently checked-out");
               System.out.println("6. Remove a book from the collection");
               System.out.println("7. Check in a book");
               System.out.println("8. Exit");
               System.out.println("Enter Your Choice: ");

               choice = scanner.nextInt();

               switch (choice) {
                   case 1:
                       /*
                        * Option 1
                        * Updates text file with current collection of books.
                        * calls the writeToFile method to write collection
                        * of books to a text file.
                        */
                       fileWriter.writeToFile();
                       System.out.println("Your text file has been updated.");
                       break;

                   case 2:
                       /*
                        * Option 2
                        * Adds a new book to the collection.
                        * Input and scans given barcode, title, and author of book.
                        *
                        * Creates a book instance of new information
                        * Adds book to the library collection
                        * Ability to add another book or exit to main menu.
                        */

                       System.out.println("Add a new book to the collection.");
                       boolean askAdd;
                       while (true) {
                           scanner.nextLine();
                           System.out.println("barcode:");
                           String id = scanner.nextLine();
                           int id1 = Integer.parseInt(id);
                           generator.textbarCode(id1);
                           int barCode = generator.getCurrentbarCode();
                           System.out.println("title:");
                           String title = scanner.nextLine();
                           System.out.println("author:");
                           String author = scanner.nextLine();

                           Book book = new Book(barCode, title, author);
                           library.addBook(book);

                           System.out.println("Would you like to add another book? Yes or No?");
                           String option = scanner.nextLine();
                           if (option.equalsIgnoreCase("Yes")) {
                               askAdd = true;
                           } else {
                               askAdd = false;
                               break;
                           }
                       }
                       break;

                   case 3:
                       /*
                        * Option 3
                        * Removes a book from the collection via Barcode #
                        * compares entered barcode to barcode in the collection
                        * If barcode is in the collection, then removes that book
                        * from the collection and adds it to a borrowed collection.
                        * Displays if book was successfully removed with information.
                        *
                        * Used an iterator as the error ConcurrentModificationException
                        * kept occurring.
                        */
                       int borrowedBarcode;
                       boolean askedRemoved;

                       System.out.println("Which book would you like to check-out?");

                       while (true) {

                           System.out.println("To begin checking out, please provide the book's barcode:");
                           borrowedBarcode = scanner.nextInt();
                           Iterator<Book> iterator = library.getBooks().iterator();
                           while (iterator.hasNext()) {
                               Book book = iterator.next();
                               if (book.getbarCode() == borrowedBarcode) {
                                   iterator.remove();
                                   library.addBorrowed(book);
                                   askedRemoved = true;
                               } else {
                                   askedRemoved = false;
                               }
                           }
                           break;
                       }
                       for (Book book : library.getCheckedOut()) {
                           outWrite.writeCheckedOut();
                           System.out.println("Book successfully checked out.");
                           System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor());
                       }

                       break;

                   case 4:
                       /*
                        * Option 4
                        * Displays a list of all the books in the collection at the moment
                        * loops through library collection and returns each book to be printed
                        */
                       System.out.println("Book List");
                       for (Book book1 : library.getBooks()) {
                           System.out.println(book1.getbarCode() + " " + book1.getTitle() + " " + book1.getAuthor());
                       }
                       break;

                   case 5:
                       /*
                        * Option 5
                        * Displays all books that were removed from the collection at the moment.
                        * loops through borrowed collection and returns each book to be printed
                        */
                       System.out.println("Checked-out books:");
                       for (Book book : library.getCheckedOut()) {
                           System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor());
                       }
                       break;

                   case 6:
                       break;

                   case 7:

                       int returnedBarcode;
                       boolean askedReturned;

                       System.out.println("Please provide the barcode # of the book you're returning today.");
                       returnedBarcode = scanner.nextInt();

                       Iterator<Book> iterator = library.getCheckedOut().iterator();
                       while (iterator.hasNext()) {
                           Book book = iterator.next();
                           if (book.getbarCode() == returnedBarcode) {
                               iterator.remove();
                               library.addBook(book);
                               askedReturned = true;
                           } else {
                               askedReturned = false;
                           }
                   }
                       break;

                   case 8:
                       //Option 6
                       //Displays goodbye message and exits application
                       System.out.println("Goodbye!");
                       System.exit(0);
               }

           }
    }
}