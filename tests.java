import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class tests {

    private Library library;
    private Book testBook1;
    private Book testBook2;

    @BeforeEach
    public void setup() {
        library = new Library();
        testBook1 = new Book("1", "The Cat in the Hat", "Dr. Seuss");
        testBook2 = new Book("2", "Madeline", "Ludwig Bemelmans");
    }

    @Test
    public void testAddBook() {
        library.addBook(testBook1);
        library.addBook(testBook2);
        List<Book> books = library.catalogue;
        assertTrue(books.contains(testBook1), "testBook1 should have been added");
        assertTrue(books.contains(testBook2), "testBook2 should have been added");
    }

    @Test
    public void testRemoveBookBarcode() {
        library.addBook(testBook1);
        library.removeBook("1");
        List<Book> books = library.catalogue;
        assertFalse(books.contains(testBook1), "testBook1 should have been removed");
    }

    @Test
    public void testRemoveBookByTitle() {
        library.addBook(testBook2);
        library.catalogue.removeIf(book -> book.title.equals("Madeline"));
        List<Book> books = library.catalogue;
        assertFalse(books.contains(testBook2), "'Madeline' should have been removed");
    }

    @Test
    public void testCheckoutBook() {
        library.addBook(testBook1);
        boolean checkedOut = library.checkoutBook("The Cat in the Hat");
        assertTrue(checkedOut, "testBook1 should be checked out");
        assertEquals("Checked out", testBook1.bookStatus, "testBook1 should be marked as 'Checked out'.");
        assertNotNull(testBook1.dueDate, "testBook1 should have a due date set");
    }

    @Test
    public void testCheckinBook() {
        testBook1.checkoutBook();
        testBook1.checkinBook();
        assertEquals("Checked in", testBook1.bookStatus, "testBook1 should be marked as 'Checked in'.");
        assertNull(testBook1.dueDate, "testBook1 due date should be null after checking in.");
    }
}
