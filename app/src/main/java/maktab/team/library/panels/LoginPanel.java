package maktab.team.library.panels;

import lombok.SneakyThrows;
import maktab.team.library.model.Person;

public class LoginPanel {

    @SneakyThrows
    public static void setupMainPanel() {
        final Person person = MainPanel.getCurrentPerson();

        if (person.isAdmin()) {
            System.out.println("admin");
            return;
        }

        System.out.println("Normal User");
    }
}
