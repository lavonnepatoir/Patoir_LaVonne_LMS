public class Main {
    public static void main(String[] args) {
        //creates new Library object to process new book input
        Library bookInput = new Library();

        //adds books from given text file and lists all books
        bookInput.addBooksFromFile("bookTextFile.txt");
        bookInput.listAllBooks();

        //removes bookID "1" and relists all books
        bookInput.removeBook("1");
        bookInput.listAllBooks();


    }
}
