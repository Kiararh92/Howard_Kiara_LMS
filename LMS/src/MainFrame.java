import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private JTextField tfTextUpload;
    private JTextField tfLastName;
    private JButton patronButton;
    private JButton staffButton;
    private JLabel lbWelcome;
    private JPanel mainPanel;

    private FileReader fileReader;
    private MenuHandler menuHandler;

    public MainFrame(MenuHandler menuHandler, FileReader fileReader) {
        this.menuHandler = menuHandler;
        this.fileReader = fileReader;

        setContentPane(mainPanel);
        setTitle("LMS");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


        patronButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//                String firstName = tfTextUpload.getText();
//                String lastName = tfLastName.getText();
//                lbWelcome.setText("Welcome " + firstName + " " + lastName);
//                JOptionPane.showMessageDialog(MainFrame.this, "Thank you. ");

                User currentUser = new User("Patron", "Patron", 101);

//                MenuGui menuGui = new MenuGui(currentUser, menuHandler);
//                menuGui.setVisible(true);

                MainWindow mainWindow = new MainWindow(menuHandler, fileReader, currentUser);
                mainWindow.setVisible(true);


            }
        });

        staffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfTextUpload.setText("");
                tfLastName.setText("");
                lbWelcome.setText("");

                User currentUser = new StaffMember("Staff", "Staff", 501);

                MainWindow mainWindow = new MainWindow(menuHandler, fileReader, currentUser);
                mainWindow.setVisible(true);

            }
        });

    }
}

