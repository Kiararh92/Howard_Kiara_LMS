import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;
import java.io.*;

public class MenuHandler {


    public static void showMenu(){
        Scanner scanner = new Scanner(System.in);



        ArrayList<Book> collection = new ArrayList<>();
        ArrayList <Book> checkedOut = new ArrayList<>();
        ArrayList <Book> removedBooks = new ArrayList<>();

        ArrayList<Integer> idList = new ArrayList<>();

        Library library = new Library(collection, checkedOut, removedBooks);
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

        Patron user1 = new Patron("Patron", "Patron",100);
        StaffMember user2 = new StaffMember("Staff", "Staff", 500);

        fileReader.readPrintFile();
        borrowedReader.readCheckedOut();

        System.out.println("1. Patron or 2. Staff Member: ");
        int userChoice = scanner.nextInt();
        scanner.nextLine();
        User currentUser;

        if(userChoice == 1) {
            currentUser = user1;
        } else if (userChoice == 2) {
            currentUser = user2;
        } else {
            System.out.println("Invalid user choice.");
            return;
        }

        while(true) {

            System.out.println("Menu");
            System.out.println("Enter Your Choice: ");
            System.out.println("3. Check-out a book");
            System.out.println("7. Check in a book");
            System.out.println("9. Exit");

            if(currentUser instanceof StaffMember) {
                System.out.println("1. Update text files");
                System.out.println("2. Add a book to the collection");
                System.out.println("4. Display the library collection");
                System.out.println("5. Display books currently checked-out");
                System.out.println("6. Remove a book from the collection");
                System.out.println("8. Upload a text file");
            }
            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    /*
                     * Option 1
                     * Updates text file with current collection of books.
                     * calls the writeToFile method to write collection
                     * of books to a text file.
                     */
                    if(currentUser instanceof StaffMember) {
                        fileWriter.writeToFile();
                        outWrite.writeCheckedOut();
                        removeWrite.writeRemoved();
                        System.out.println("Your text files has been updated.");
                    } else {
                        System.out.print("Invalid option. \n");
                    }
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
                        System.out.println("title: ");
                        String title = scanner.nextLine();
                        System.out.println("author: ");
                        String author = scanner.nextLine();
                        System.out.println("genre: ");
                        String genre = scanner.nextLine();

                        Book book = new Book(barCode, title, author, genre);
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
                     * Check out a book from the collection via Barcode # or title
                     * compares entered barcode or title to the one in the collection
                     * If there's a match in the collection, the check-out process for book
                     * begins and adds it to a borrowed collection.
                     * Displays if book was successfully checked-out with its information.
                     *
                     * Used an iterator as the error ConcurrentModificationException
                     * kept occurring.
                     */

                    System.out.println("Check-out Menu:");
                    System.out.println("1. Check-out by title");
                    System.out.println("2. Check-out by barcode");
                    System.out.println("3. Return to Main Menu");
                    System.out.println("Choose a option: ");
                    int checkedoutMenu = scanner.nextInt();
                    scanner.nextLine();

                    switch (checkedoutMenu) {
                        case 1:
                            //select a book via title
                            viaTitle.borrowedViaTitle();
                            break;
                        case 2:
                            //select a book via Barcode
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
                    System.out.println("Current books available in the library.");
                    for (Book book1 : library.getBooks()) {
                        System.out.println(book1.getbarCode() + " " + book1.getTitle() + " " + book1.getAuthor() + " " + book1.getGenre());
                    }
                    break;

                case 5:
                    /*
                     * Option 5
                     * Displays all books that are currently checked-out from the library.
                     * loops through borrowed collection and returns each book to be printed
                     */
                    System.out.println("Books currently checked-out:");
                    for (Book book : library.getCheckedOut()) {
                        System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getGenre());
                    }
                    break;

                case 6:
                    /*
                     *  Option 6
                     *  Remove/delete a book from the collection.
                     *  Removal submenu: different options
                     *           to search for a book.
                     */
                    System.out.println("Removal Menu:");
                    System.out.println("1. Remove by title");
                    System.out.println("2. Remove by barcode");
                    System.out.println("3. Return to Main Menu");
                    System.out.println("Choose a option: ");
                    int removalMenu = scanner.nextInt();

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
                     * Submenu for returning a borrowed book.
                     */

                    System.out.println("Check-In Menu:");
                    System.out.println("1. Check-In by title");
                    System.out.println("2. Check-In by barcode");
                    System.out.println("3. Return to Main Menu");
                    System.out.println("Choose a option: ");
                    int returnMenu = scanner.nextInt();

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
                    /*
                     * Option 8
                     * Provides the user with the ability to upload their
                     * own text file.
                     */
                    if(user2 instanceof StaffMember) {
                        scanner.nextLine();
                        System.out.println("Name of text file. ex. Textfile.txt :");
                        String yourBooks = scanner.nextLine();

                        FileReader uploadTextFile = new FileReader(yourBooks, collection, library, idList, generator);
                        System.out.println("Upload taking place, please wait...");
                        uploadTextFile.readYourText();
                    } else {
                        System.out.println("You don't have access to do that. Please try again.");
                    }
                    break;

                case 9:
                    //Option 9
                    //Displays goodbye message and exits application
                    System.out.println("Goodbye!");
                    System.exit(0);
            }

        }
    }
}
