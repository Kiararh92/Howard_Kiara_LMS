import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/*
 * Kiara Howard, Software Dev I, 11/27/23
 * Class Name MainFrame
 * This class handles the frontend logic and GUI of the
 * login menu of the application.
 */
public class MainFrame extends JFrame {
    /*
     * Kiara Howard, Software Dev I, 11/25/23
     * Class Name MenuHandler
     * This class handles the menu selection of the user.
     */
    private JTextField tfTextUpload;
    private JTextField tfLastName;
    private JButton patronButton;
    private JButton staffButton;
    private JLabel lbWelcome;
    private JPanel mainPanel;

    private FileReader fileReader;
    private MenuHandler menuHandler;

    private ArrayList<Book> collection;
    private Library library;
    private TableModel tableModel;

    private Barcode generator;
    private ArrayList<Integer> idList;
    private FWriter fileWriter;
    private FWriter outWrite;
    private FWriter removeWrite;

    public MainFrame(MenuHandler menuHandler,Library library, ArrayList<Book> collection, FileReader fileReader, TableModel tableModel,ArrayList<Integer> idList, Barcode generator,FWriter fileWriter,FWriter outWrite,FWriter removeWrite) {
        this.menuHandler = menuHandler;
        this.fileReader = fileReader;
        this.library = library;
        this.collection = collection;
        this.tableModel = tableModel;
        this.idList = idList;
        this.generator = generator;
        this.fileWriter = fileWriter;
        this.outWrite=outWrite;
        this.removeWrite=removeWrite;

        setContentPane(mainPanel);
        setTitle("LMS");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


        patronButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                User currentUser = new User("Patron", "Patron", 101);

                MainWindow mainWindow = new MainWindow(menuHandler, fileReader, currentUser, library, collection, tableModel, idList, generator,fileWriter,outWrite,removeWrite);
                mainWindow.setVisible(true);

            }
        });

        staffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lbWelcome.setText("");

                User currentUser = new StaffMember("Staff", "Staff", 501);

                MainWindow mainWindow = new MainWindow(menuHandler, fileReader, currentUser, library, collection, tableModel,idList, generator,fileWriter,outWrite,removeWrite);
                mainWindow.setVisible(true);

            }
        });

    }
}

