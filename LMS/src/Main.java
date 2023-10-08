import java.util.Scanner;
import java.util.*;
import java.io.*;



public class Main {
    public static void main(String[] args) {

           Scanner scanner = new Scanner(System.in);

           ArrayList <Book> collection = new ArrayList<>();
           ArrayList <Book> checkedOut = new ArrayList<>();
           ArrayList <Book> removedBooks = new ArrayList<>();
           ArrayList <Book> newBooks = new ArrayList<>();

           ArrayList<Integer> idList = new ArrayList<>();

           Library library = new Library(collection, checkedOut, removedBooks, newBooks);
           Barcode generator = new Barcode(idList);

           int choice;

           FileReader fileReader = new FileReader("Books.txt", collection, library, idList, generator);
           FileReader borrowedReader = new FileReader("CheckedOut.txt",checkedOut, library, idList, generator);
           FileReader removedReader = new FileReader("RemovedBooks.txt", removedBooks, library,idList, generator);

           FWriter fileWriter = new FWriter("Books.txt", collection, library);
           FWriter outWrite = new FWriter("CheckedOut.txt", checkedOut, library);
           FWriter removeWrite = new FWriter("RemovedBooks.txt", removedBooks, library);

           ViaTitle viaTitle = new ViaTitle(library, fileWriter, removeWrite, outWrite);
           ViaBarcode viaBarcode = new ViaBarcode(library, fileWriter,removeWrite, outWrite);


           fileReader.readPrintFile();
           borrowedReader.readCheckedOut();

           while(true) {

               System.out.println("Menu");
               System.out.println("1. Update text files");
               System.out.println("2. Add a book to the collection");
               System.out.println("3. Check-out a book");
               System.out.println("4. Display the library collection");
               System.out.println("5. Display books currently checked-out");
               System.out.println("6. Remove a book from the collection");
               System.out.println("7. Check in a book");
               System.out.println("8. Upload a text file");
               System.out.println("9. Exit");
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
                       outWrite.writeCheckedOut();
                       removeWrite.writeRemoved();
                       System.out.println("Your text files has been updated.");
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
                       boolean anotherAdd;
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
                               anotherAdd = true;
                           } else {
                               anotherAdd = false;
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
                       boolean askedOut;

                       System.out.println("Check-out Menu:");
                       System.out.println("1. Check-out by title");
                       System.out.println("2. Check-out by barcode");
                       System.out.println("3. Return to Main Menu");
                       System.out.println("Choose a option: ");
                       int checkedoutMenu = scanner.nextInt();
                       scanner.nextLine();

                       switch (checkedoutMenu) {
                           case 1:
                               //via title
                               viaTitle.borrowedViaTitle();
                               break;
                           case 2:
                               //via Barcode
                               viaBarcode.checkedoutViaBarcode();
                               break;
                           case 3:
                               System.out.println("Returning to the Main Menu, Please wait...");
                               break;
                           default:
                               System.out.println("Invalid option");
                               break;
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
                        * Displays all books that were checked-out from the library.
                        * loops through borrowed collection and returns each book to be printed
                        */
                       System.out.println("Checked-out books:");
                       for (Book book : library.getCheckedOut()) {
                           System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor());
                       }
                       break;

                   case 6:
                       /*
                        *  Option 6
                        *
                        */
                       int removedBarcode;
                       String removedTitle;
                       boolean askedRemoved;

                       System.out.println("Removal Menu:");
                       System.out.println("1. Remove by title");
                       System.out.println("2. Remove by barcode");
                       System.out.println("3. Return to Main Menu");
                       System.out.println("Choose a option: ");
                       int removalMenu = scanner.nextInt();

                       /*
                        *   Sub Menu for book removal options
                        *
                        */
                       switch (removalMenu) {
                           case 1:
                               //via title
                               viaTitle.removalViaTitle();
                           break;
                           case 2:
                               //via barcode
                               viaBarcode.removalViaBarcode();
                               break;
                           case 3:
                               System.out.println("Returning to the Main Menu, Please wait...");
                               break;
                           default:
                               System.out.println("Invalid option");
                               break;
                          }
                       break;

                   case 7:
                       /*
                        * Option 7
                        * Check-in/return a borrowed book via Barcode #.
                        * compares entered barcode to barcode in the checked out checked-out List
                        * If barcode is in the checked-out List, then removes that book
                        * from the checked-out List and adds it back to the book collection.
                        * Displays if book was successfully checked in.
                        * Displays the current book collection after books are returned.
                        *
                        * Used an iterator as the error ConcurrentModificationException
                        * kept occurring.
                        */
                       int returnedBarcode;
                       boolean found = false;
                       boolean anotherIn = true;
                       String option;

                       System.out.println("Check-In Menu:");
                       System.out.println("1. Check-In by title");
                       System.out.println("2. Check-In by barcode");
                       System.out.println("3. Return to Main Menu");
                       System.out.println("Choose a option: ");
                       int returnMenu = scanner.nextInt();

                       /*
                        *   Sub Menu for book check-ins
                        */
                       switch (returnMenu) {
                           case 1:
                               //via title
                               viaTitle.returnViaTitle();
                               break;
                           case 2:
                               //via barcode
                               viaBarcode.returnViaBarcode();
                               break;
                           case 3:
                               System.out.println("Returning to the Main Menu, Please wait...");
                               break;
                           default:
                               System.out.println("Invalid option");
                               break;
                       }
                       break;

                   case 8:
                       scanner.nextLine();
                       System.out.println("Name of text file. ex. Textfile.txt :");
                       String yourBooks = scanner.nextLine();

                       FileReader uploadTextFile = new FileReader(yourBooks, collection, library, idList, generator);
                       System.out.println("Upload taking place, please wait...");
                       uploadTextFile.readYourText();
                       break;

                   case 9:
                       //Option 6
                       //Displays goodbye message and exits application
                       System.out.println("Goodbye!");
                       System.exit(0);
               }

           }
    }
}