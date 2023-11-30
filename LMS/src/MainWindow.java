import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
/*
 * Kiara Howard, Software Dev I, 11/29/23
 * Class Name MainWindow
 * This class handles the frontend logic and GUI of the
 * main and sub menus of the application.
 */
public class MainWindow extends JFrame{
    private JPanel panel1;
    private JButton bnCheckOut;
    private JButton bnCheckIn;
    private JButton bnUpload;
    private JPanel RJPanel;
    private JPanel LJPanel;
    private JButton bnCollection;
    private JButton bnUploadFile;
    private JLabel lbFilePath;
    private JTable table1;
    private javax.swing.JScrollPane JScrollPane;
    private JLabel checkoutTitleLabel;
    private JTextField fieldCheckOut;
    private JButton bnOutProcess;
    private JLabel statusLabel;
    private JTextArea libraryTextArea;
    private JLabel ChckInTitleLabel;
    private JTextArea libraryInTextArea;
    private JButton bnInProcess;
    private JLabel statusChckInLabel;
    private JTextField fieldChckIn;
    private JButton bnRemove;
    private JTextField fieldReBc;
    private JTextArea libraryReBcTextArea;
    private JButton bnReProcess;
    private JLabel statusReBcLabel;
    private JLabel removeBcLabel;
    private JTextArea libraryUpTextArea;
    private JLabel statusUpLabel;
    private JButton bnUpDB;
    private JLabel removeTLabel;
    private JTextField fieldReT;
    private JButton bnReBcProcess;
    private TableModel tableModel;
    private FileReader fileReader;
    private MenuHandler menuHandler;
    private User currentUser;
    private ArrayList<Book> collection;
    private Library library;
    private TableModel table;

    private Barcode generator;
    private ArrayList<Integer> idList;

    private FWriter fileWriter;
    private FWriter outWrite;
     private FWriter removeWrite;

    private int choice = 0;
    String filePath;

    public MainWindow(MenuHandler menuHandler, FileReader fileReader, User currentUser, Library library, ArrayList<Book> collection, TableModel tableModel,ArrayList<Integer> idList, Barcode generator,FWriter fileWriter,FWriter outWrite,FWriter removeWrite){
        this.menuHandler = menuHandler;
        this.fileReader = fileReader;
        this.currentUser = currentUser;
        this.library = library;
        this.collection = collection;
        this.tableModel = tableModel;
        this.idList = idList;
        this.generator = generator;
        this.fileWriter = fileWriter;
        this.outWrite=outWrite;
        this.removeWrite=removeWrite;


    setContentPane(panel1);
    setTitle("Main Window");
    setSize(800, 400);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setVisible(true);


    CardLayout cardLayout = (CardLayout) (LJPanel.getLayout());
    ViaTitle viaTitle = new ViaTitle(library, fileWriter, removeWrite, outWrite);
    ViaBarcode viaBarcode = new ViaBarcode(library, fileWriter,removeWrite, outWrite);



        if(currentUser.getRole().equals("Staff")) {

        bnUpload.setEnabled(true);
        bnRemove.setEnabled(true);
    } else {
        bnUpload.setVisible(false);
        bnRemove.setVisible(false);
    }


        bnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            cardLayout.show(LJPanel, "removeCard");
            statusReBcLabel.setVisible(false);
            libraryReBcTextArea.setText("");
            }
        });

        bnCheckIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(LJPanel, "checkInCard");
                statusChckInLabel.setVisible(false);
                libraryInTextArea.setText("");
            }
        });

        //check out menu button
        bnCheckOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(LJPanel, "checkOutCard");
                statusLabel.setVisible(false);
                libraryTextArea.setText("");
            }
        });

        bnUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(LJPanel, "uploadCard");
                statusUpLabel.setVisible(false);
                libraryUpTextArea.setText("");
            }
        });

        //display collection menu button
        bnCollection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(LJPanel, "collectionCard");
                table1.setModel(tableModel);
                tableModel.refreshTable(collection);
                //tableModel.setColumnVisibility(currentUser, table1);
            }
        });

        bnUploadFile.addActionListener(new ActionListener() {
            JFileChooser fileChooser = new JFileChooser();
            @Override
            public void actionPerformed(ActionEvent e) {
                //shows the JFileChoose dialog box and wait for user to select a file.
                int returnValue = fileChooser.showOpenDialog(null);

                //if the user selects a file, open it
                if(returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePath = selectedFile.getAbsolutePath();
                    try {

                        lbFilePath.setText("Selected file: " + filePath);
                        FileReader uploadTextFile = new FileReader(filePath, collection, library, idList, generator);
                        uploadTextFile.readYourText(filePath, libraryUpTextArea, statusUpLabel);

                    } catch (Exception ex) {
                        System.out.println("An error occurred.");
                        ex.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(null, statusUpLabel.getText());
                }
            }
        });
        bnOutProcess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viaTitle.borrowedViaTitle(statusLabel, fieldCheckOut, libraryTextArea);
                //statusLabel.setVisible(false);
                JOptionPane.showMessageDialog(null, statusLabel.getText());
            }
        });
        //Finalize Check In Process
        bnInProcess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viaTitle.returnViaTitle(statusChckInLabel, fieldChckIn, libraryInTextArea);
                JOptionPane.showMessageDialog(null, statusChckInLabel.getText());
            }
        });

        bnReProcess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

              viaTitle.removalViaTitle(statusReBcLabel, fieldReT, libraryReBcTextArea);
              JOptionPane.showMessageDialog(null, statusReBcLabel.getText());

            }
        });

        bnReBcProcess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viaBarcode.removalViaBarcode(statusReBcLabel, fieldReBc, libraryReBcTextArea);
                JOptionPane.showMessageDialog(null, statusReBcLabel.getText());
            }
        });

        bnUpDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // if (currentUser instanceof StaffMember) {
                fileWriter.writeToFile();
                outWrite.writeCheckedOut();
                removeWrite.writeRemoved();

                    //System.out.println("Your text files has been updated.");
                    JOptionPane.showMessageDialog(null, "Your text files has been updated.");

                try{
                    Connection con = Main.getConnection();
                    //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS_DB23");
                    //PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM books");
                    String query = "SELECT * FROM books";
                    PreparedStatement statement = con.prepareStatement(query);

                    ResultSet result = statement.executeQuery();

                    //ArrayList<String> array = new ArrayList<String>();
                    //libraryUpTextArea.setText("");
                    while(result.next()) {
                        int barcode = result.getInt("barcode");
                        String title = result.getString("title");
                        String author = result.getString("author");
                        String genre = result.getString("genre");
                        String status = result.getString("status");

                        //checks if the due date is null before attempting to parse.
                        LocalDate dueDate = null;
                        String dueDateString = result.getString("due_Date");
                        if(dueDateString != null) {
                            dueDate = LocalDate.parse(dueDateString);
                        }
                        //LocalDate dueDate= LocalDate.parse(result.getString("due_Date"));


                        Book book = new Book(barcode,title,author,genre,status,dueDate);

                        library.addBook(book);
                    }

                    statusUpLabel.setText("Updated library collection.");
                    System.out.println("All records have been selected!");

                }catch(Exception ex){
                    statusUpLabel.setText("An error has occurred. You will be returned to the Main Menu to try again.");
                    System.out.println(e);
                    ex.printStackTrace();
                }
                libraryUpTextArea.setText("");
                for (Book book : library.getBooks()) {
                    libraryUpTextArea.append(book.getbarCode() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getGenre() + "\n");
                }

                fileWriter.writeToFile();
                outWrite.writeCheckedOut();
                removeWrite.writeRemoved();
            }
        });
    }


}
