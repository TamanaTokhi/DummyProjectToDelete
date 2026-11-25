
package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

public class EmploymentApplicationDao {

    public int saveApplication(EmploymentApplication application) throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);

            String query = "INSERT INTO employment_applications "
                    + "(full_name, current_address, contact_number, email_address, "
                    + "education, gender, date_available, desired_position, "
                    + "desired_salary, authorized_to_work, relatives_working, "
                    + "explanation, signature) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS);

            int counter = 1;

            statement.setString(counter++, application.getFullName());
            statement.setString(counter++, application.getCurrentAddress());
            statement.setString(counter++, application.getContactNumber());
            statement.setString(counter++, application.getEmailAddress());
            statement.setString(counter++, application.getEducation());
            statement.setString(counter++, application.getGender());

            if (application.getDateAvailable() != null) {
                statement.setDate(counter++, Date.valueOf(application.getDateAvailable()));
            } else {
                statement.setNull(counter++, java.sql.Types.DATE);
            }

            statement.setString(counter++, application.getDesiredPosition());
            statement.setString(counter++, application.getDesiredSalary());
            statement.setString(counter++, application.getAuthorizedToWork());
            statement.setString(counter++, application.getRelativesWorking());
            statement.setString(counter++, application.getExplanation());
            statement.setString(counter++, application.getSignature());

            statement.executeUpdate();

            connection.commit();

            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException exception) {

            if (connection != null) {
                connection.rollback();
            }

            throw exception;

        } finally {

            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return 0;
    }
}
