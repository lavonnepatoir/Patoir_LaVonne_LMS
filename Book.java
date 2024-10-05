//LaVonne Patoir, CEN3024C-14320,10/05/24
//Software Development I
//The purpose of this class is to define the elements of the Book object, and return a string listing an individual book

public class Book {
    String bookID;
    String author;
    String title;

    public Book(String bookID, String title, String author) {
    //Purpose: initializes new book object; Arguments: Strings title, author, bookID; Return Value: none
        this.bookID = bookID;
        this.title = title;
        this.author = author;
    }

    public String toString() {
    //Purpose: returns a string of the Book object; Arguments: none; Return Value: a string
        return bookID + ", " + author + ", " + title;
    }
}

