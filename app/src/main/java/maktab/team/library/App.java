package maktab.team.library;

import maktab.team.library.database.ConnectionManager;

public class App {

    public static void main(String[] args) {
        ConnectionManager.hikariStart();

    }
}
