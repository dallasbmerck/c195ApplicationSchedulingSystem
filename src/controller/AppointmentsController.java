package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {
    public TableView appointmentsTableView;
    public TableColumn apptIDCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn customerIDCol;
    public TableColumn userIDCol;
    public RadioButton filterMonthButton;
    public RadioButton filterWeekButton;
    public RadioButton showAllButton;
    public Label selectedTimeLabel;
    public Label showSelectedTimeLabel;
    public Button previousButton;
    public Button nextButton;
    public Button logoutButton;
    public Button customersButton;
    public Button reportsButton;
    public Button deleteApptButton;
    public Button editApptButton;
    public Button newApptButton;

    public void toggleGroup() {
        setToggle = new ToggleGroup();

    }
    public void clickFilterMonth(ActionEvent actionEvent) throws SQLException {
        w
    }

    public void clickFilterWeek(ActionEvent actionEvent) {
    }

    public void clickShowAll(ActionEvent actionEvent) {
    }

    public void clickPreviousButton(ActionEvent actionEvent) {
    }

    public void clickNextButton(ActionEvent actionEvent) {
    }

    public void clickLogoutButton(ActionEvent actionEvent) {
    }

    public void clickCustomersButton(ActionEvent actionEvent) {
    }

    public void clickReportsButton(ActionEvent actionEvent) {
    }

    public void clickDeleteApptButton(ActionEvent actionEvent) {
    }

    public void clickEditApptButton(ActionEvent actionEvent) {
    }

    public void clickNewApptButton(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
