package maktab.team.library.database;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
    private static final String PERSON_BOOKS_QUERY = "CREATE TABLE IF NOT EXISTS person_books(id serial unique primary key, " +
            "book_status varchar, date date, book_id int, constraint fk_bookID foreign key (book_id) references library (id), " +
            "person_id int, constraint fk_personID foreign key (person_id) references persons (id));";

    private static final String PERSONS_QUERY = "CREATE TABLE IF NOT EXISTS persons(id serial unique primary key, " +
            "username varchar unique, password varchar, admin bool, verify bool, fine int);";

    private static final String LIBRARY_QUERY = "CREATE TABLE IF NOT EXISTS library(id serial unique primary key, " +
            "title varchar, book_status varchar, is_published bool, feedback bool, isbn int);";

    @SneakyThrows
    public static void hikariStart() {
        ConnectionManager.execute((connection) -> {
            try (Statement statement = connection.createStatement()) {
                statement.addBatch(LIBRARY_QUERY);
                statement.addBatch(PERSONS_QUERY);
                statement.addBatch(PERSON_BOOKS_QUERY);
                statement.executeBatch();
            }
        });
    }

    @SneakyThrows
    public static void execute(ConnectionCallback callback) {
        try (Connection connection = DataBase.getDataSource().getConnection()) {
            callback.doInConnection(connection);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
