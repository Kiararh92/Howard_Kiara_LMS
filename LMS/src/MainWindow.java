import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
    private JButton bnFindBook;
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


    if(currentUser.getRole().equals("Staff")) {
        bnUpload.setEnabled(true);
    } else {
        bnUpload.setVisible(false);
    }

        bnCheckIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(LJPanel, "checkInCard");
            }
        });

        //check out menu button
        //
        bnCheckOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(LJPanel, "checkOutCard");
                statusLabel.setVisible(false);
            }
        });

        bnUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(LJPanel, "uploadCard");
            }
        });

        //display collection menu button
        // table done
        bnCollection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table1.setModel(tableModel);

                cardLayout.show(LJPanel, "collectionCard");

            }
        });

//      bnUploadFile.addActionListener(new ActionListener() {
//            boolean uploadCompleted = false;
//            // Create the file chooser
//            JFileChooser fileChooser = new JFileChooser();
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                if(!uploadCompleted) {
//
//                    int returnValue = fileChooser.showOpenDialog(null);
//
//                    if (returnValue == JFileChooser.APPROVE_OPTION) {
//                        try {
//                            File selectedFile = fileChooser.getSelectedFile();
//                            String filePath = selectedFile.getAbsolutePath();
//                            lbFilePath.setText("Selected file: " + filePath);
//
//                            FileReader uploadTextFile = new FileReader(filePath, collection, library, idList, generator);
//                            System.out.println("Upload taking place, please wait...");
//
//                            uploadTextFile.readYourText();
//
//                            //processUploadFile(selectedFile);
//
//                            //choice = 8;
//                            //menuHandler.showMenu(choice, currentUser, filePath);
//
//                            uploadCompleted = true;
//
//                            JOptionPane.showMessageDialog(null, "File uploaded successfully.");
//
//
//                            //lbFilePath.setText("Selected file: " + filePath);
//
//                            //call my PrintFile()
//                            //menuHandler.fileReader.readPrintFile();
//
//                            //choice = 8;
//                            //menuHandler.showMenu(choice, currentUser);
//
//                    } catch (Exception ex) {
//                        System.out.println("An error occurred.");
//                        ex.printStackTrace();
//                    }
//                }
//                //uploadCompleted = true;
//            }
//
//            }
//        });
        bnOutProcess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viaTitle.borrowedViaTitle(statusLabel, fieldCheckOut, libraryTextArea);
                //statusLabel.setVisible(false);
                JOptionPane.showMessageDialog(null, statusLabel.getText());
            }
        });
    }


}
