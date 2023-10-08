//public class Testfile {
//
//    while(true) {
//
//
//        System.out.println("Removal Menu:");
//        System.out.println("1. Remove by title");
//        System.out.println("2. Remove by barcode");
//        System.out.println("3. Return to Main Menu");
//        System.out.println("Choose a option: ");
//        int removalMenu = scanner.nextInt();
//        scanner.nextLine();
//
//        switch (removalMenu) {
//            case 1:
//                //via title
//                break;
//            case 2:
//                //via barcode
//                break;
//            case 3:
//                //return to main menu
//                break;
//            default:
//                System.out.println("Invalid option");
//                break;
//        }
//        break;
//    }
//}
//
//                       while (true) {
//                               System.out.println("What is the barcode of the book you would like to delete:");
//                               removedBarcode = scanner.nextInt();
//                               Iterator<Book> iterator = library.getBooks().iterator();
//        while (iterator.hasNext()) {
//        Book book = iterator.next();
//        if (book.getbarCode() == removedBarcode) {
//        iterator.remove();
//        library.addRemoved(book);
//        askedRemoved = true;
//        } else {
//        askedRemoved = false;
//        }
//        }
//        break;
//        }
//        for (Book book : library.getRemoved()) {
//        fileWriter.writeToFile();
//        outWrite.writeRemoved();
//        System.out.println("Book successfully deleted.");
//        System.out.println(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor());
//        }
