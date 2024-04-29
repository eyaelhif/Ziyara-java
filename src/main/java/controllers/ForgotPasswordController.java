package controllers;

import com.mysql.cj.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
/*import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;*/

import java.util.Properties;

public class ForgotPasswordController {

    @FXML
    private TextField code;

    @FXML
    private TextField email;

    @FXML
    private Button send;

    @FXML
    private Button verify;

    private String generatedCode;

    @FXML
    void sendCode(ActionEvent event) {
        // Generate a random code
        generatedCode = generateRandomCode(6);

        // Send the random code via email
        sendEmail(email.getText(), generatedCode);

        // Inform the user that the code has been sent
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Code Sent");
        alert.setHeaderText(null);
        alert.setContentText("A verification code has been sent to your email.");
        alert.showAndWait();
    }

    @FXML
    void verifyCode(ActionEvent event) {
        String enteredCode = code.getText();

        // Check if the entered code matches the generated code
        if (enteredCode.equals(generatedCode)) {
            // If the code is correct, proceed to password change scene
            // Implement your logic here to switch scenes or perform other actions
            System.out.println("Code verified. Proceed to password change scene.");
        } else {
            // If the code is incorrect, inform the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Code");
            alert.setHeaderText(null);
            alert.setContentText("The entered code is incorrect. Please try again.");
            alert.showAndWait();
        }
    }

    // Method to generate a random code
    private String generateRandomCode(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }



    // Method to send email with the code
    private void sendEmail(String recipientEmail, String generatedCode) {




    }



    }




