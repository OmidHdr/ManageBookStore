package maktab.team.library.database;

import maktab.team.library.utils.exceptions.DuplicateElement;
import maktab.team.library.utils.exceptions.InvalidElement;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionCallback {
    void doInConnection(Connection connection) throws SQLException, InvalidElement, DuplicateElement;
}