import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * LaVonne Patoir, CEN3024C-14320, 11/14/24
 * Software Development I
 * The purpose of this class is to set up action listeners for GUI components
 */


public class LMS_GUI {
    private Library library = new Library();

    private JPanel mainPanel;
    private JLabel fileName;
    private JTextField fileTextField;
    private JButton addFileButton;
    private JTable displayBooksTable;
    private JTextField barcodeTextField;
    private JButton removeBookByBarcodeButton;
    private JLabel checkoutOutBookByTitle;
    private JTextField titleTextField;
    private JButton checkInBookButton;
    private JButton exitButton;
    private JButton checkOutBookButton;
    private JTextField checkInTextField;
    private JLabel removeBookLabel;
    private JTextField removeBookByTitleTextField;
    private JButton removeBookByTitleButton;
    private JLabel checkInLabel;

    /**
     * Constructor to set up GUI components
     */
    public LMS_GUI() {

        library.connect();
        updateBookTable();

        addFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFile();
                clearTextFields();
            }
        });

        removeBookByBarcodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeBookByBarcode();
                clearTextFields();
            }
        });

        removeBookByTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeBookByTitle();
                clearTextFields();
            }
        });

        checkOutBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkoutBookByTitle();
                clearTextFields();
            }
        });

        checkInBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkinBook();
                clearTextFields();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        updateBookTable();
    }

    /**
     * Loads books from a user-given file and updates the GUI table.
     */
    private void loadFile() {
        String fileName = fileTextField.getText();
        try {
            library.addBooksFromFile(fileName);
            updateBookTable();
            showMessage("File upload successful: " + fileName);
        } catch (Exception e) {
            showMessage("File not found: " + fileName);
        }
    }

    /**
     * Displays messages to user via a dialog box
     */
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Removes a book from the library by its barcode (aka 'bookID') and updates the table
     */
    private void removeBookByBarcode() {
        String barcode = barcodeTextField.getText();
        boolean checkedOut = library.removeBook(barcode); // Use the checkout method by barcode

        updateBookTable();

        if (checkedOut) {
            showMessage("The book with ID '" + barcode + "' has been checked out.");
        }else {
            showMessage("Error: The book titled '" + barcode + "' was not found.");
        }
    }

    /**
     * Removes a book from the library by its title and updates the table
     */
    private void removeBookByTitle() {
        String title = removeBookByTitleTextField.getText();
        boolean removed = library.removeBookByTitle(title); // Attempt to remove the book by title from the database

        updateBookTable(); // Refresh the table to reflect changes

        if (removed) {
            showMessage("The book titled '" + title + "' has been removed.");
        } else {
            showMessage("Error: The book titled '" + title + "' was not found.");
        }
    }

    /**
     * Checks in a book by its title and updates the table
     */
    private void checkinBook() {
        String title = checkInTextField.getText();
        boolean checkedIn = library.checkinBook(title);
        updateBookTable();

        if (checkedIn) {
            showMessage("The book titled '" + title + "' has been checked in.");
        }else {
            showMessage("Error: The book titled '" + title + "' was not checked in.");
        }
    }

    /**
     * Checks outu a book by its title and updates the table
     */
    private void checkoutBookByTitle() {
        String title = titleTextField.getText();
        boolean checkedOut = library.checkoutBook(title);
        updateBookTable();

        if (checkedOut) {
            showMessage("Book titled '" + title + "' has been checked out.");
        }else {
            showMessage("Error: The book titled '" + title + "' was not checked out.");
        }
    }

    /**
     * Updates the GUI JTable with the current catalogue from the library
     */
    private void updateBookTable() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"bookID", "title", "author", "bookStatus", "dueDate"}, 0);
        List<Book> books = library.listAllBooks();

        for (Book book : books) {
            model.addRow(new Object[]{book.getBookID(), book.getTitle(), book.getAuthor(), book.getBookStatus(), book.getDueDate()});
        }
        displayBooksTable.setModel(model);
    }

    /**
     * Clears the GUI text fields after each action
     */
    private void clearTextFields() {
        fileTextField.setText("");
        barcodeTextField.setText("");
        titleTextField.setText("");
        checkInTextField.setText("");
        removeBookByTitleTextField.setText("");
    }

    /**
     * The main method to launch the GUI
     *
     * @param args command
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Welcome to the Library Management System!");
        frame.setContentPane(new LMS_GUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
