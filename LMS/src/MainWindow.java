import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainWindow extends JFrame{
    private JPanel panel1;
    private JButton bnCheckOut;
    private JButton bnCheckIn;
    private JButton bnUpload;
    private JPanel RJPanel;
    private JPanel LJPanel;
    private JButton bnCollection;
    private JTable table1;
    private JButton bnUploadFile;
    private JLabel lbFilePath;
    private TableModel tableModel;
    private FileReader fileReader;
    private MenuHandler menuHandler;
    private int choice = 0;
    public MainWindow(MenuHandler menuHandler, FileReader fileReader, User currentUser){
        this.menuHandler = menuHandler;
        this.fileReader = fileReader;

    setContentPane(panel1);
    setTitle("Main Window");
    setSize(800, 400);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setVisible(true);

    CardLayout cardLayout = (CardLayout) (LJPanel.getLayout());


        bnCheckIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(LJPanel, "checkInCard");
            }
        });
        bnCheckOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(LJPanel, "checkOutCard");
            }
        });
        bnUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(LJPanel, "uploadCard");
            }
        });
        bnCollection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(LJPanel, "collectionCard");

            }
        });

        createUIComponents();

        bnUploadFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                int returnValue = fileChooser.showOpenDialog(null);

                if(returnValue == JFileChooser.APPROVE_OPTION){
                    try {
                        File selectedFile = fileChooser.getSelectedFile();
                        String filePath = selectedFile.getAbsolutePath();

                        lbFilePath.setText("Selected file: " + filePath);

                        //call my PrintFile()
                        menuHandler.fileReader.readPrintFile();

                        choice = 8;
                        menuHandler.showMenu(choice, currentUser);

                    } catch (Exception ex) {
                        System.out.println("An error occurred.");
                        ex.printStackTrace();
                    }
                }

            }
        });
    }

    void getTables(TableModel tableModel, JTable table2){
        this.tableModel = tableModel;
        this.table1 = table2;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
       // TableModel tableModel = new TableModel();
        table1 = new JTable(tableModel);
    }


}
