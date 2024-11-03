import java.time.LocalDate;

//LaVonne Patoir, CEN3024C-14320, 11/03/2024
//Software Development I
//The purpose of this class is to define the elements of the Book object, and return a string listing an individual book

public class Book {
    private String bookID;
    private String author;
    private String title;
    private String bookStatus; // "checked in" or "out"
    private LocalDate dueDate;

    public String getBookID() { return bookID; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getBookStatus() { return bookStatus; }
    public LocalDate getDueDate() { return dueDate; }

    public Book(String bookID, String title, String author) {
    //Purpose: initializes new book object; Arguments: Strings title, author, bookID; Return Value: none
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.bookStatus = "Checked in";
        this.dueDate = null;
    }


    public void checkinBook() {
    //Purpose: Marks book as checked in and resets the due date; Arguments: none; Return Value: none
        this.bookStatus = "Checked in";
        this.dueDate = null;
    }

    public boolean checkoutBook() {
    //Purpose: Marks book as checked out and sets a due date 4 weeks later; Arguments: none; Return Value: boolean
        if (this.bookStatus.equals("Checked out")) {
            return false;
        }
        this.bookStatus = "Checked out";
        this.dueDate = LocalDate.now().plusWeeks(4);
        return true;
    }

    @Override
    public String toString() {
    //Purpose: Returns a string containing a book's details; Arguments: none; Return Value: String
        String dueDateString = (dueDate != null) ? dueDate.toString() : "No due date";
        return bookID + ", " + author + ", " + title + ", " + bookStatus + ", Due: " + dueDateString;
    }
}

