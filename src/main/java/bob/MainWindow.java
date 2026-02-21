package bob;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Bob bob;

    private Image userImage = loadImage("/images/DaUser.png");
    private Image bobImage = loadImage("/images/DaBob.png");

    /**
     * Loads an image resource safely, returning a default image if the resource is not found.
     *
     * @param resourcePath the path to the image resource
     * @return the loaded Image
     */
    private static Image loadImage(String resourcePath) {
        java.io.InputStream stream = MainWindow.class.getResourceAsStream(resourcePath);
        if (stream == null) {
            System.out.println("Warning: Image not found: " + resourcePath);
            return new Image("https://via.placeholder.com/100");
        }
        return new Image(stream);
    }

    /**
     * Initializes the MainWindow by binding the scroll pane and displaying the welcome message.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        String welcomeMessage = "Hello! I'm Bob, your personal task manager.\n\n"
                + "What would you like to do today?";
        dialogContainer.getChildren().add(
                DialogBox.getBobDialog(welcomeMessage, bobImage)
        );
    }

    /**
     * Sets the Bob instance to be used for processing commands.
     *
     * @param bob the Bob application instance
     */
    public void setBob(Bob bob) {
        this.bob = bob;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Bob's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.trim().isEmpty()) {
            userInput.clear();
            return;
        }
        String response = bob.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBobDialog(response, bobImage)
        );
        userInput.clear();
    }
}
