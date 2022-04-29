package controller;

import DatabaseAccess.AccessAppointment;
import database.LogOnRecord;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.LogOn;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    public Label ApplicationTitleLabel;
    public Label UserNameLabel;
    public Label PasswordLabel;
    public TextField UsernameText;
    public TextField PasswordText;
    public Button ResetButton;
    public Button SignInButton;
    public Button ExitButton;
    public Label ZoneIDLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale userLocale = Locale.getDefault();
        ZoneIDLabel.setText(ZoneId.systemDefault().toString());
        resourceBundle = ResourceBundle.getBundle("laguage/LoginPage");
        ApplicationTitleLabel.setText(resourceBundle.getString("ApplicationTitleLabel"));
        UserNameLabel.setText(resourceBundle.getString("UserNameLabel"));
        PasswordLabel.setText(resourceBundle.getString("PasswordLabel"));
        SignInButton.setText(resourceBundle.getString("SignInButton"));
        ResetButton.setText(resourceBundle.getString("ResetButton"));
        ExitButton.setText(resourceBundle.getString("ExitButton"));
    }

    public void PressResetButton(ActionEvent actionEvent) throws IOException {
        UsernameText.clear();
        PasswordText.clear();
    }

    public void PressSignInButton(ActionEvent actionEvent) throws SQLException, IOException {
        String username = UsernameText.getText();
        String password = PasswordText.getText();

        boolean logon = LogOn.logOnAttempt(username, password);
        LogOnRecord.generateLogOnFile(username, logon);

        if (logon) {
            ObservableList<Appointment> apptSoon = AccessAppointment.appointmentWithin15MinOfLogOn();
            if (!apptSoon.isEmpty()) {
                for (Appointment soon : apptSoon) {
                    String alert = "Appointment with Appointment_ID: " + soon.getApptID() + " Starts at: " +
                            soon.getBeginDateTime().toString();
                    ButtonType ok = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                    Alert invalid = new Alert(Alert.AlertType.WARNING, alert, ok);
                    invalid.showAndWait();
                }
            } else {
                ButtonType ok = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                Alert invalid = new Alert(Alert.AlertType.CONFIRMATION, "No appointments scheduled within the next 15 minutes.", ok);
                invalid.showAndWait();
            }
            screenChange(actionEvent, "/view/AppointmentsPage.fxml");
        } else {
            Locale userlocale = Locale.getDefault();
            ResourceBundle resourceBundle = ResourceBundle.getBundle("laguage/LoginPage");
            ButtonType ok = new ButtonType(resourceBundle.getString("okButton"), ButtonBar.ButtonData.OK_DONE);
            Alert logOnFail = new Alert(Alert.AlertType.WARNING, resourceBundle.getString("logOnFailButton"), ok);
            logOnFail.showAndWait();
        }
    }


    public void screenChange(ActionEvent actionEvent, String path) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(p);
        Stage newWindow = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        newWindow.setScene(scene);
        newWindow.show();
    }

    public void PressExitButton(ActionEvent actionEvent) throws IOException {
        LogOn.userLogOff();
        System.exit(0);
    }
}