import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

//LaVonne Patoir, CEN3024C-14320, 11/14/24
//Software Development I
//The purpose of this class is to set up action listeners for GUI components

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

        public LMS_GUI() {
            //Purpose: Constructor to set up GUI components; Arguments: none; Return Value: none

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

        private void loadFile() {
        //Purpose: Loads books from a user-given file into the library and updates the table; Arguments: none; Return Value: none/void
            String fileName = fileTextField.getText();
            try {
                library.addBooksFromFile(fileName);
                updateBookTable();
                showMessage("File upload successful: " + fileName);
            } catch (Exception e) {
                showMessage("File not found: " + fileName);
            }
        }

        private void showMessage(String message) {
        //Purpose: Displays messages to user via a dialog box; Arguments: String message; Return Value: none/void
            JOptionPane.showMessageDialog(null, message);
        }

        private void removeBookByBarcode() {
        //Purpose: Removes a book from the library by its barcode (aka 'bookID') and updates the table; Arguments: none; Return Value: none/void
            String barcode = barcodeTextField.getText();
            boolean checkedOut = library.removeBook(barcode); // Use the checkout method by barcode

            updateBookTable();

            if (checkedOut) {
                showMessage("The book with ID '" + barcode + "' has been checked out.");
            }
        }

        private void removeBookByTitle() {
        //Purpose: Removes a book from the library by its title and updates the table; Arguments: none; Return Value: none/void
            String title = removeBookByTitleTextField.getText();
            boolean removed = library.removeBookByTitle(title); // Remove book by title in database

            updateBookTable();

            if (removed) {
                showMessage("The book titled '" + title + "' has been removed.");
            }
        }

        private void checkinBook() {
        //Purpose: Checks in a book by its title and updates the table; Arguments: none; Return Value: none/void
            String title = checkInTextField.getText();
            boolean checkedIn = library.checkinBook(title);
            updateBookTable();

            if (checkedIn) {
                showMessage("The book titled '" + title + "' has been checked in.");
            }
        }

        private void checkoutBookByTitle() {
        //Purpose: Checks out a book by its title and updates the table; Arguments: none; Return Value: none/void
            String title = titleTextField.getText();
            boolean checkedOut = library.checkoutBook(title);
            updateBookTable();

            if (checkedOut) {
                showMessage("Book titled '" + title + "' has been checked out.");
            }
        }

        private void updateBookTable() {
        //Purpose: Updates the GUI JTable with the current catalogue from the library; Arguments: none; Return Value: none/void
            DefaultTableModel model = new DefaultTableModel(new Object[]{"bookID", "title", "author", "bookStatus", "dueDate"}, 0);
            List<Book> books = library.listAllBooks();

            for (Book book : books) {
                model.addRow(new Object[]{book.getBookID(), book.getTitle(), book.getAuthor(), book.getBookStatus(), book.getDueDate()});
            }
            displayBooksTable.setModel(model);
        }

        private void clearTextFields() {
        //Purpose: Clears the GUI text fields after each action; Arguments: none; Return Value: none/void
            fileTextField.setText("");
            barcodeTextField.setText("");
            titleTextField.setText("");
            checkInTextField.setText("");
            removeBookByTitleTextField.setText("");
        }

        public static void main(String[] args) {
        //Purpose: Main method to initialize and display the GUI window; Arguments: String[] args; Return Value: none/void
            JFrame frame = new JFrame("Welcome to the Library Management System!");
            frame.setContentPane(new LMS_GUI().mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }

}
