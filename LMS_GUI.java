import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//LaVonne Patoir, CEN3024C-14320, 11/03/2024
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
        Book userInput = null;
        //This method is used since
        for (Book book : library.catalogue) {
            if (book.getTitle().equals(barcode)) {
                userInput = book;
                break;
            }
        }
        if (userInput != null) {
            library.catalogue.remove(userInput);
            updateBookTable();
            showMessage("The book ID '" + barcode + "' has been removed.");
        } else {
            showMessage("The book ID '" + barcode + "' was not found.");
        }
    }

    private void removeBookByTitle() {
    //Purpose: Removes a book from the library by its title and updates the table; Arguments: none; Return Value: none/void
        String title = removeBookByTitleTextField.getText();
        Book userInput = null;
        for (Book book : library.catalogue) {
            if (book.getTitle().equals(title)) {
                userInput = book;
                break;
            }
        }
        if (userInput != null) {
            library.catalogue.remove(userInput);
            updateBookTable();
            showMessage("The book " + title + " has been removed.");
        } else {
            showMessage("The book " + title + " was not found.");
        }
    }

    private void checkinBook() {
    //Purpose: Checks in a book by its title and updates the table; Arguments: none; Return Value: none/void
        String title = checkInTextField.getText();
        Book book = library.catalogue.stream().filter(b -> b.getTitle().equals(title)).findFirst().orElse(null);
        if (book != null) {
            book.checkinBook();
            updateBookTable();
            showMessage("The book '" + title + "' has been checked in.");
        } else {
            showMessage("The book '" + title + "' was not found.");
        }
    }

    private void checkoutBookByTitle() {
    //Purpose: Checks out a book by its title and updates the table; Arguments: none; Return Value: none/void
        String title = titleTextField.getText();
        boolean checkedOut = library.checkoutBook(title);
        updateBookTable();

        if (checkedOut) {
            showMessage("Book titled '" + title + "' has been checked out.");
        } else {
            showMessage("Book titled '" + title + "' is already checked out or not found.");
        }
    }

    private void updateBookTable() {
    //Purpose: Updates the GUI JTable with the current catalogue from the library; Arguments: none; Return Value: none/void
        DefaultTableModel model = new DefaultTableModel(new Object[]{"bookID", "title", "author", "bookStatus"}, 0);
        for (Book book : library.catalogue) {
            model.addRow(new Object[]{book.getBookID(), book.getTitle(), book.getAuthor(), book.getBookStatus()});
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
