package maktab.team.library;

import maktab.team.library.database.ConnectionManager;
import maktab.team.library.panels.MainPanel;

public class App {

    public static void main(String[] args) {
        ConnectionManager.hikariStart();
        MainPanel.setupMainPanel();
    }
}
