public class Book {
    //defines the element of the Book object
    String bookID;
    String author;
    String title;

    public Book(String title, String author, String bookID) {
    //creates a new object that stores bookID, book author, and book title
        this.bookID = title;
        this.author = author;
        this.title = bookID;
    }

    public String toString() {
    //returns a string of the book
        return bookID + ", " + author + ", " + title;
    }
}
