import java.time.LocalDate;

/**
 * LaVonne Patoir, CEN3024C-14320, 11/14/24
 * Software Development I
 * The purpose of this class is to define the elements of the Book object, and return a string listing an individual book
 */

public class Book {
    private String bookID;
    private String author;
    private String title;
    private String bookStatus; // "checked in" or "out"
    private LocalDate dueDate;

    /**
     * Initializes a new book object with the provided details.
     *
     * @param bookID  the unique identifier for the book
     * @param title   the title of the book
     * @param author  the author of the book
     * @param status  "checked in" or "checked out"
     * @param dueDate
     */

    public Book(String bookID, String title, String author, String status, LocalDate dueDate) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.bookStatus = status;
        this.dueDate = dueDate;
    }

    /**
     * Initializes a new book object with some default values.
     *
     * @param bookID  the unique identifier for the book
     * @param title   the title of the book
     * @param author  the author of the book
     */
    public Book(String bookID, String title, String author) {
        this(bookID, title, author, "Checked in", null);
    }

    /**
     * @return bookID
     */
    public String getBookID() { return bookID; }
    /**
     * @return title
     */
    public String getTitle() { return title; }
    /**
     * @return author
     */
    public String getAuthor() { return author; }
    /**
     * @return bookStatus
     */
    public String getBookStatus() { return bookStatus; }
    /**
     * @return dueDate
     */
    public LocalDate getDueDate() { return dueDate; }

    /**
     * Marks book as checked in and resets the due date
     */
    public void checkinBook() {
        this.bookStatus = "Checked in";
        this.dueDate = null;
    }

    /**
     * Marks book as checked out and sets a due date for 4 weeks later
     * @return "false" if already checked out, "true" if able to run function
     */
    public boolean checkoutBook() {
        if (this.bookStatus.equals("Checked out")) {
            return false;
        }
        this.bookStatus = "Checked out";
        this.dueDate = LocalDate.now().plusWeeks(4);
        return true;
    }

    /**
     * Returns a string containing the book's details.
     *
     * @return a string containing the book's ID, title, author, status, and due date
     */
    @Override
    public String toString() {
        String dueDateString = (dueDate != null) ? dueDate.toString() : "No due date";
        return bookID + ", " + author + ", " + title + ", " + bookStatus + ", Due: " + dueDateString;
    }
}

