import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
public class Server2 extends Application{
    boolean isPrime;
    String response;

    @Override
    public void start(Stage primaryStage) {
        TextArea ta = new TextArea();

        // Create a scene and place it in the stage
        Scene scene = new Scene(new ScrollPane(ta), 450, 200);
        primaryStage.setTitle("Server"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage


        new Thread( () -> {
        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8000);
            Platform.runLater(() ->
                    ta.appendText("Server started at " + new Date() + '\n'));

            // Listen for a connection request
            Socket socket = serverSocket.accept();

            // Create data input and output streams
            DataInputStream inputFromClient = new DataInputStream(
                    socket.getInputStream());
            DataOutputStream outputToClient = new DataOutputStream(
                    socket.getOutputStream());

            while (true) {
                // Receive radius from the client
                int number = inputFromClient.readInt();
                //boolean isPrime;

                // Compute response
                    if (number <= 1) {
                        isPrime = false;
                    }
                    else if (number == 2) {
                        isPrime = true;
                    }
                    else if (number % 2 == 0) {
                        isPrime = false;
                    } else {

                        // Check potential divisors up to the square root of the number
                        for (int i = 3; i <= Math.sqrt(number); i += 2) {
                            if (number % i == 0) {
                                isPrime = false; // Number is divisible, not prime
                            }
                        }

                        isPrime = true; // Number is prime
                    }

                if(isPrime){
                    response = "The number is Prime";
                } else {
                    response = "the number is NOT Prime";
                }

                // Send response back to the client
                outputToClient.writeUTF(response);

                Platform.runLater(() -> {
                    ta.appendText("Number received from client: "
                            + number + '\n');
                });
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }).start();
}

    public static void main(String[] args) {
        launch(args);
    }
}
