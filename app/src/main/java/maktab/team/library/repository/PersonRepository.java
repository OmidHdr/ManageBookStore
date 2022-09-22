package maktab.team.library.repository;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import maktab.team.library.database.ConnectionManager;
import maktab.team.library.database.DataBase;
import maktab.team.library.model.Person;
import maktab.team.library.utils.exceptions.DuplicateElement;
import maktab.team.library.utils.exceptions.InvalidElement;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@UtilityClass
public class PersonRepository {

    /**
     * adds the person to the database
     * provide the persons parameters to the method
     *
     * @param person provide the person
     */
    public void addPerson(Person person) {
        ConnectionManager.execute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO persons VALUES (DEFAULT, ?, ?, ?, ?, ?)")) {
                statement.setString(1, person.getUsername());
                statement.setString(2, person.getPassword());
                statement.setBoolean(3, false);
                statement.setBoolean(4, false);
                statement.setInt(5, 0);
                statement.executeUpdate();

            } catch (SQLException exception) {
                throw new DuplicateElement("In User Dar Database Vojod Darad!");
            }
        });
    }

    /**
     * returns the query results
     *
     * @param option an option of the person (username, id, password)
     * @return query results
     */
    @SneakyThrows
    public <T> ResultSet getPerson(T datatype, String option) {
        final CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();

        try (Connection connection = DataBase.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     String.format("SELECT * FROM persons WHERE %s = ?", option))) {

            statement.setObject(1, datatype);
            final ResultSet resultSet = statement.executeQuery();

            cachedRowSet.setTableName("persons");
            cachedRowSet.populate(resultSet);

            return cachedRowSet;

        } catch (SQLException exception) {
            throw new InvalidElement("In Element Dar Database Peida Nashod!");
        }
    }

    /**
     * returns if the persons credentials are correct
     *
     * @param person the person
     * @return boolean
     */
    @SneakyThrows
    public boolean validatePerson(Person person) {
        try (Connection connection = DataBase.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM persons WHERE username ILIKE ? AND password LIKE ?")) {
            statement.setString(1, person.getUsername());
            statement.setString(2, person.getPassword());

            return statement.executeQuery().next();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * checks if these types are in the database
     *
     * @param option the option the user selects (username, password)
     * @param type   the input from the user
     */
    public <T> boolean validatePersonData(String option, T type) {
        final String VALIDATE_QUERY = String.format("SELECT * FROM persons WHERE %s ilike ?", option);
        try (Connection connection = DataBase.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(VALIDATE_QUERY)) {
            statement.setObject(1, type);
            return statement.executeQuery().next();

        } catch (SQLException exception) {
            return false;
        }
    }
}
