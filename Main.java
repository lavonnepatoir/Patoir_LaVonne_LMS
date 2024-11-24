import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * LaVonne Patoir, CEN3024C-14320, 11/24/24
 * Software Development I
 * The purpose of this Library Management System (LMS) is to add, remove, or list all books in a collection.
 *
 * All actions (adding and removing of books) are committed through a GUI
 */

public class Main {
    /**
     * Main method to launch the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Library library = new Library();
        library.connect();
        library.listAllBooks();
    }
}
