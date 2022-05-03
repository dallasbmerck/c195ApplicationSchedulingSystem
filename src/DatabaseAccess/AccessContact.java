package DatabaseAccess;

import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;

public class AccessContact {

    public static Integer getContactID(String contactName) throws SQLException {
        Integer id = -1;
        PreparedStatement SQLCommand = DatabaseConnection.getConnection().prepareStatement("SELECT Contact_ID, " +
                "Contact_Name FROM contacts WHERE Contact_Name = ?");
        SQLCommand.setString(1, contactName);
        ResultSet resultSet = SQLCommand.executeQuery();

        while (resultSet.next()) {
            id = resultSet.getInt("Contact_ID");
        }
        SQLCommand.close();
        return id;
    }

    public static ObservableList<String> getContactName() throws SQLException {
        ObservableList<String> contactName = FXCollections.observableArrayList();
        PreparedStatement SQLCommand = DatabaseConnection.getConnection().prepareStatement("SELECT DISTINCT " +
                "Contact_Name FROM contacts;");
        ResultSet resultSet = SQLCommand.executeQuery();

        while (resultSet.next()) {
            contactName.add(resultSet.getString("Contact_Name"));
        }
        SQLCommand.close();
        return contactName;
    }

    public static ObservableList<String> getContactAppointments(String contactID) throws SQLException {
        ObservableList<String> appointments = FXCollections.observableArrayList();
        PreparedStatement SQLCommand = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM" +
                " appointments WHERE Contact_ID = ?");
        SQLCommand.setString(1, contactID);
        ResultSet resultSet = SQLCommand.executeQuery();

        while (resultSet.next()) {
            String appointmentID = resultSet.getString("Appointment_ID");
            String title = resultSet.getString("Title");
            String type = resultSet.getString("Type");
            String startTime = resultSet.getString("Start");
            String endTime = resultSet.getString("End");
            String customerID = resultSet.getString("Customer_ID");

            String newString = "Appointment_ID: " + appointmentID + "\n" +                      // May need to review
                               "Title: " + title + "\n" +
                               "Appointment Type: " + type + "\n" +
                               "Start (date/time): " + startTime + "\n" +
                               "End (date/time): " + endTime + "\n" +
                               "Customer_ID; " + customerID + "\n";
            appointments.add(newString);
        }
        SQLCommand.close();
        return appointments;
    }

    public static Integer calculateAppointmentTime(String id) throws SQLException {
        Integer timeSum = 0;
        PreparedStatement SQLCommand = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM " +
                "appointments WHERE Contact_ID = ?");
        SQLCommand.setString(1, id);
        ResultSet resultSet = SQLCommand.executeQuery();

        while (resultSet.next()) {
            LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
            timeSum += (int)Duration.between(start, end).toMinutes();
        }
        SQLCommand.close();
        return timeSum;
    }

    public static ObservableList<Contact> allContacts() throws SQLException {
        ObservableList<Contact> cList = FXCollections.observableArrayList();
        String SQLCommand = "SELECT * from contacts";
        PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(SQLCommand);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int contactID = resultSet.getInt("Contact_ID");
            String contactName = resultSet.getString("Contact_Name");
            String email = resultSet.getString("Email");
            Contact contact = new Contact(contactID, contactName, email);
            cList.add(contact);
        }
        return cList;
    }
}
