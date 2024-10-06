//LaVonne Patoir, CEN3024C-14320, 10/05/24
//Software Development I
//The purpose of this Library Management System (LMS) is to add, remove, or list all books in a collection.
//Books are inputted by way of a text file.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    //Purpose: prompts for user input and executes library operations, Argument: String[] args, Return value: none/void
        Scanner scanner = new Scanner(System.in);
        Library databaseActions = new Library();

        System.out.println("Enter file name: ");
        String fileName = scanner.nextLine();

        databaseActions.addBooksFromFile(fileName);

        System.out.println("Current database printing...");
        databaseActions.listAllBooks();

        System.out.println("\nEnter a barcode to remove: ");
        String barcodeToRemove = scanner.nextLine();
        databaseActions.removeBook(barcodeToRemove);
        System.out.println("Barcode # " + barcodeToRemove + " removed.");
        System.out.println("Current database printing...");
        databaseActions.listAllBooks();

        System.out.println("\nEnter a title to remove: ");
        String titleToRemove = scanner.nextLine();
        databaseActions.catalogue.removeIf(book -> book.title.equals(titleToRemove));
        System.out.println("The book '" + titleToRemove + "' has been removed.");
        System.out.println("Current database printing...");
        databaseActions.listAllBooks();

        System.out.println("\nEnter a title to check out: ");
        String titleToCheckout = scanner.nextLine();
        boolean checkedOut = databaseActions.checkoutBook(titleToCheckout);
        if (checkedOut) {
            System.out.println("This book has been checked out.");
        } else {
            System.out.println("This book is already checked out.");
        }
        System.out.println("Current database printing...");
        databaseActions.listAllBooks();


        System.out.println("\nEnter title to check in: ");
        String titleToCheckin = scanner.nextLine();
        System.out.println("Enter author and bookID for the book being checked in:");
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Book ID: ");
        String bookID = scanner.nextLine();
        databaseActions.addBook(new Book(bookID, author, titleToCheckin));
        System.out.println("The book '" + titleToCheckin + "' has been checked in.");
        System.out.println("Current database printing...");
        databaseActions.listAllBooks();

        scanner.close();


    }
}
