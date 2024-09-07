import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Library {
    //creates a list where all the books will be stored
    List<Book> library = new ArrayList<>();

    //takes the Book object as input and adds to it
    public void addBook(Book book) {
        library.add(book);
    }

    //removes if a book matches the given bookID
    public void removeBook(String bookID) {
        library.removeIf(book -> book.bookID.equals(bookID));
    }

    //prints all books currently in the library
    public void listAllBooks() {
        for (Book book : library) {
            System.out.println(book);
        }
    }

    public void addBooksFromFile(String fileName) {
    //splits each line input into parts, and creates a Book object respectively
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                //the comma is used to distinguish the object elements from each other
                library.add(new Book(details[0].trim(), details[1].trim(), details[2].trim()));
            }
        } catch (IOException e) {
            System.out.println("Error." + e.getMessage());
            //try-catch in case of an error
        }
    }
}
