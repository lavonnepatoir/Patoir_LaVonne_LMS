import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//LaVonne Patoir, CEN3024C-14320, 10/19/24
//Software Development I

//The purpose of this class is to execute library actions

public class Library {
    List<Book> catalogue = new ArrayList<>();
    private LocalDate dueDate;
    private String checkedInOut;
    private String bookID;
    private String author;
    private String title;


    public void addBook(Book book) {
    //Purpose: adds a book to the library, Arguments: Book (bookID, author, title), Return Value: none/void
        catalogue.add(book);
    }

    public void removeBook(String bookID) {
    //Purpose: removes book from the library, Arguments: String bookID, Return Value: none/void
        catalogue.removeIf(book -> book.bookID.equals(bookID));
    }

    public boolean checkoutBook(String title) {
    //Purpose: searches the library and removes a book if found, Arguments: String title, Return Value: boolean
        for (Book book : catalogue) {
            if (book.title.equals(title)) {
                return book.checkoutBook();
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
    //Purpose: adds books to library by reading from file, Arguments: String fileName, Return Value: none/void
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                //the comma is used to distinguish the object elements from each other
                catalogue.add(new Book(details[0].trim(), details[1].trim(), details[2].trim()));
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public void checkinBook() {
        //Purpose: changes the property of a book to being checked in; Arguments: none; Return Value: none
        this.checkedInOut = "Checked in";
        this.dueDate = null;
    }

    public String toString() {
        //Purpose: returns a string of the Book object; Arguments: none; Return Value: a string
        return bookID + ", " + author + ", " + title;
    }
}
