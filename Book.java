import java.time.LocalDate;

//LaVonne Patoir, CEN3024C-14320,10/19/24
//Software Development I
//The purpose of this class is to define the elements of the Book object, and return a string listing an individual book

public class Book {
    String bookID;
    String author;
    String title;
    String bookStatus; // "checked in" or "checked out"
    LocalDate dueDate;

    public Book(String bookID, String title, String author) {
    //Purpose: initializes new book object; Arguments: Strings title, author, bookID; Return Value: none
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.bookStatus = "Checked in";
        this.dueDate = null;
    }

    public void checkinBook() {
    //Purpose: changes the property of a book to being checked in; Arguments: none; Return Value: none
        this.bookStatus = "Checked in";
        this.dueDate = null;
    }

    public String toString() {
    //Purpose: returns a string of the Book object; Arguments: none; Return Value: a string
        return bookID + ", " + author + ", " + title;
    }

    public boolean checkoutBook() {
        if (this.bookStatus.equals("Checked out")) {
            return false;
        }
        this.bookStatus = "Checked out";
        this.dueDate = LocalDate.now().plusWeeks(4);
        return true;
    }
}
