
package application;

import java.sql.SQLException;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        EmploymentApplicationDao dao = new EmploymentApplicationDao();

        primaryStage.setTitle("Employment Application");

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setPadding(new Insets(20));

        Label title = new Label("EMPLOYMENT APPLICATION");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        grid.add(title, 0, 0, 2, 1);

        Label personalTitle = new Label("Personal Information");
        personalTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        grid.add(personalTitle, 0, 1, 2, 1);

        TextField fullNameField = new TextField();
        TextField addressField = new TextField();
        TextField contactField = new TextField();
        TextField emailField = new TextField();
        TextField educationField = new TextField();

        grid.add(new Label("Full Name:"), 0, 2);
        grid.add(fullNameField, 1, 2);

        grid.add(new Label("Current Address:"), 0, 3);
        grid.add(addressField, 1, 3);

        grid.add(new Label("Contact Number:"), 0, 4);
        grid.add(contactField, 1, 4);

        grid.add(new Label("Email Address:"), 0, 5);
        grid.add(emailField, 1, 5);

        grid.add(new Label("Highest Educational Attainment:"), 0, 6);
        grid.add(educationField, 1, 6);

        Label genderLabel = new Label("Gender:");
        RadioButton male = new RadioButton("Male");
        RadioButton female = new RadioButton("Female");
        RadioButton other = new RadioButton("Other");

        ToggleGroup genderGroup = new ToggleGroup();
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        other.setToggleGroup(genderGroup);

        HBox genderBox = new HBox(15, male, female, other);

        grid.add(genderLabel, 0, 7);
        grid.add(genderBox, 1, 7);

        Label employmentTitle = new Label("Employment Eligibility");
        employmentTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        grid.add(employmentTitle, 0, 8, 2, 1);

        DatePicker dateAvailablePicker = new DatePicker();

        TextField positionField = new TextField();
        TextField salaryField = new TextField();

        grid.add(new Label("Date Available:"), 0, 9);
        grid.add(dateAvailablePicker, 1, 9);

        grid.add(new Label("Desired Position:"), 0, 10);
        grid.add(positionField, 1, 10);

        grid.add(new Label("Desired Salary:"), 0, 11);
        grid.add(salaryField, 1, 11);

        Label authorizedLabel = new Label(
                "Are you legally authorized to work in the country?");

        RadioButton authorizedYes = new RadioButton("Yes");
        RadioButton authorizedNo = new RadioButton("No");

        ToggleGroup authorizedGroup = new ToggleGroup();
        authorizedYes.setToggleGroup(authorizedGroup);
        authorizedNo.setToggleGroup(authorizedGroup);

        HBox authorizedBox = new HBox(15, authorizedYes, authorizedNo);

        grid.add(authorizedLabel, 0, 12, 2, 1);
        grid.add(authorizedBox, 0, 13, 2, 1);

        Label relativesLabel = new Label(
                "Do you have relatives working for our company?");

        RadioButton relativesYes = new RadioButton("Yes");
        RadioButton relativesNo = new RadioButton("No");

        ToggleGroup relativesGroup = new ToggleGroup();
        relativesYes.setToggleGroup(relativesGroup);
        relativesNo.setToggleGroup(relativesGroup);

        HBox relativesBox = new HBox(15, relativesYes, relativesNo);

        grid.add(relativesLabel, 0, 14, 2, 1);
        grid.add(relativesBox, 0, 15, 2, 1);

        TextArea explanationArea = new TextArea();
        explanationArea.setPromptText("Please explain further...");
        explanationArea.setPrefRowCount(3);

        grid.add(new Label("If yes, please explain further:"), 0, 16);
        grid.add(explanationArea, 1, 16);

        TextField signatureField = new TextField();

        grid.add(new Label("Applicant's Signature:"), 0, 17);
        grid.add(signatureField, 1, 17);

        Button submitButton = new Button("Submit Application");
        Button clearButton = new Button("Clear");

        HBox buttonBox = new HBox(15, submitButton, clearButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        grid.add(buttonBox, 1, 18);

        submitButton.setOnAction(event -> {

            String fullName = fullNameField.getText().trim();
            String address = addressField.getText().trim();
            String contact = contactField.getText().trim();
            String email = emailField.getText().trim();
            String education = educationField.getText().trim();
            String position = positionField.getText().trim();
            String salary = salaryField.getText().trim();
            String explanation = explanationArea.getText().trim();
            String signature = signatureField.getText().trim();

            if (genderGroup.getSelectedToggle() == null || authorizedGroup.getSelectedToggle() == null|| relativesGroup.getSelectedToggle() == null
  || dateAvailablePicker.getValue() == null
       || fullName.isEmpty()
   || address.isEmpty()
    || contact.isEmpty()
    || email.isEmpty()
         || education.isEmpty()
          || position.isEmpty()
         || salary.isEmpty()
     || signature.isEmpty()) {

                showAlert("Error", "Please complete all required fields.",
                        Alert.AlertType.ERROR);
                return;
            }

            try {

                EmploymentApplication application = new EmploymentApplication();

                application.setFullName(fullName);
                application.setCurrentAddress(address);
                application.setContactNumber(contact);
                application.setEmailAddress(email);
                application.setEducation(education);
                application.setGender(
                        ((RadioButton) genderGroup.getSelectedToggle()).getText());

                LocalDate date = dateAvailablePicker.getValue();
                application.setDateAvailable(date);

                application.setDesiredPosition(position);
                application.setDesiredSalary(salary);

                application.setAuthorizedToWork(
                        ((RadioButton) authorizedGroup.getSelectedToggle()).getText());

                application.setRelativesWorking(
                        ((RadioButton) relativesGroup.getSelectedToggle()).getText());

                application.setExplanation(explanation);
                application.setSignature(signature);

                int applicationId = dao.saveApplication(application);

                if (applicationId > 0) {
                    showAlert("Success",
                            "Application submitted successfully!",
                            Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error",
                            "Application could not be saved.",
                            Alert.AlertType.ERROR);
                }

            } catch (SQLException exception) {
                exception.printStackTrace();
                showAlert("Database Error",
                        exception.getMessage(),
                        Alert.AlertType.ERROR);
            }
        });

        clearButton.setOnAction(event -> {
            fullNameField.clear();
            addressField.clear();
            contactField.clear();
            emailField.clear();
            educationField.clear();
            positionField.clear();
            salaryField.clear();
            explanationArea.clear();
            signatureField.clear();
            dateAvailablePicker.setValue(null);
            genderGroup.selectToggle(null);
            authorizedGroup.selectToggle(null);
            relativesGroup.selectToggle(null);
        });

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 650, 750);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String message,
            Alert.AlertType type) {

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
