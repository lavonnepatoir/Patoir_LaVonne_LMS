import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;


/**
 * LaVonne Patoir, CEN3024C-14320, 11/24/24
 * Software Development I
 * The purpose of this class is to execute library actions.
 */

public class Library {
    private Connection conn;

    /**
         * Connects program to a database hosted on my local computer
         * @return none/void
     */
    public void connect() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=LMSDatabase;encrypt=false"; //Encryption set to false for development purposes
            String user = "valencia2024";
            String password = "valencia2024";
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    /**
     * Adds a book to the library
     *
     * @param book (bookID, author, title)
     * @return none
     */
    public void addBook(Book book) {
        connect();
        String query = "INSERT INTO Books (barcode, title, author, status, due_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, book.getBookID());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getBookStatus());
            pstmt.setDate(5, book.getDueDate() != null ? Date.valueOf(book.getDueDate()) : null);
            pstmt.executeUpdate();
            System.out.println("Book added to the database.");
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    /**
     * Removes a book from the library by its barcode.
     *
     * @param bookID the unique identifier (barcode) of the book to be removed
     * @return true if the book was successfully removed, false otherwise
     */

    public boolean removeBook(String bookID) {
        connect();
        String query = "DELETE FROM Books WHERE barcode = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, bookID);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error removing book: " + e.getMessage());
        }
        return false;
    }

    /**
     * Removes book from the library by its title
     *
     * @param title
     * @return boolean
     */
    public boolean removeBookByTitle(String title) {
        connect();
        String query = "DELETE FROM Books WHERE title = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, title);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error removing book: " + e.getMessage());
        }
        return false;
    }

    /**
     * Changes status of a book to "checked out" if found
     *
     * @param title
     * @return If title is found, table is updated. If false, error message.
     */
    public boolean checkoutBook(String title) {
        connect();
        String query = "UPDATE Books SET status = 'Checked out', due_date = ? WHERE title = ? AND status = 'Checked in'";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDate(1, Date.valueOf(LocalDate.now().plusWeeks(4)));
            pstmt.setString(2, title);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error checking out book: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks out a book by looking for its unique barcode/bookID and changing its status
     *
     * @param bookID
     * @return If found, status is changed. If false, error message.
     */
    public boolean checkoutBookByID(String bookID) {
        connect();
        String query = "UPDATE Books SET status = 'Checked out', due_date = ? WHERE barcode = ? AND status = 'Checked in'";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDate(1, Date.valueOf(LocalDate.now().plusWeeks(4)));
            pstmt.setString(2, bookID);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error checking out book: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks in a book by its title by changing its status
     *
     * @param title
     * @return If false (book is not found), error message is displayed.
     */
    public boolean checkinBook(String title) {
        connect();
        String query = "UPDATE Books SET status = 'Checked in', due_date = NULL WHERE title = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, title);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error checking in book: " + e.getMessage());
            return false;
        }
    }

    /**
     * Displays all books currently in library
     *
     * @return a list of books currently stored in the database
     */
    public List<Book> listAllBooks() {
        List<Book> books = new ArrayList<>();
        if (conn == null) {
            System.out.println("No database connection, call connect() first.");
            return books;
        }

        String query = "SELECT * FROM Books";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String bookID = rs.getString("barcode");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String status = rs.getString("status");
                LocalDate dueDate = (rs.getDate("due_date") != null) ? rs.getDate("due_date").toLocalDate() : null;
                books.add(new Book(bookID, title, author, status, dueDate));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving books: " + e.getMessage());
        }
        return books;
    }

    /**
     * Adds a book to the library "catalogue" by reading froma file
     *
     * @param fileName
     * @return none
     */
    public void addBooksFromFile(String fileName) {
        connect();
        String query = "INSERT INTO Books (barcode, title, author, genre, status, due_date) VALUES (?, ?, ?, ?, ?, ?)";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            //ensures that details in a file are imported into the database by order of appearance
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length >= 5) {
                    String barcode = details[0].trim();
                    String title = details[1].trim();
                    String author = details[2].trim();
                    String genre = details[3].trim();
                    String status = details[4].trim();
                    LocalDate due_date = null;

                    if (details.length > 5 && !details[5].trim().isEmpty()) {
                        due_date = LocalDate.parse(details[5].trim(), dateFormatter);
                    }

                    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                        pstmt.setString(1, barcode);
                        pstmt.setString(2, title);
                        pstmt.setString(3, author);
                        pstmt.setString(4, genre);
                        pstmt.setString(5, status);
                        if (due_date != null) {
                            pstmt.setDate(6, Date.valueOf(due_date));
                        } else {
                            pstmt.setNull(6, Types.DATE);
                        }
                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println("Error adding book to the database: " + e.getMessage());
                    }
                }
            }
            System.out.println("Books from file " + fileName + " added to the database.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
