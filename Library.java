import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//LaVonne Patoir, CEN3024C-14320, 11/03/2024
//Software Development I
//The purpose of this class is to execute library actions

public class Library {
    List<Book> catalogue = new ArrayList<>();

    public void addBook(Book book) {
    //Purpose: adds a book to the library, Arguments: Book (bookID, author, title), Return Value: none/void
        catalogue.add(book);
    }

    public void removeBook(String bookID) {
    //Purpose: removes book from the library, Arguments: String bookID, Return Value: none/void
        catalogue.removeIf(book -> book.getBookID().equals(bookID));
    }

    public boolean removeBookByTitle(String title) {
    //Purpose: removes book from the library, Arguments: String title, Return Value: boolean
        catalogue.removeIf(book -> book.getTitle().equals(title));
        return false;
    }

    public boolean checkoutBook(String title) {
    //Purpose: checks out a book by its title if found and sets its status to "Checked out"; Arguments: String title; Return Value: boolean
        for (Book book : catalogue) {
            if (book.getTitle().equals(title)) {
                return book.checkoutBook();
            }
        }
        return false;
    }

    public boolean checkoutBookByID(String bookID) {
    //Purpose: checks out a book by its bookID if found and sets its status to "Checked out"; Arguments: String bookID; Return Value: boolean
        for (Book book : catalogue) {
            if (book.getBookID().equals(bookID)) {
                return book.checkoutBook();
            }
        }
        return false;
    }

    public boolean checkinBook(String title) {
    //Purpose: checks out a book by its title if found and sets its status to "Checked out"; Arguments: String title; Return Value: boolean
        for (Book book : catalogue) {
            if (book.getTitle().equals(title)) {
                book.checkinBook();
                return true;
            }
        }
        return false;
    }

    public void listAllBooks() {
    //Purpose: displays all books currently in library, Arguments: none, Return Value: none/void
        for (Book book : catalogue) {
            System.out.println(book);
        }
    }

    public void addBooksFromFile(String fileName) {
    //Purpose: Adds books to the library "catalogue" by reading from a file; Arguments: String fileName; Return Value: none
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 3) {  // bookID, title, author
                    catalogue.add(new Book(details[0].trim(), details[1].trim(), details[2].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
