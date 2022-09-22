package maktab.team.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Person {
    private int id;
    private String username;
    private String password;
    private boolean admin;
    private boolean verify;
    private int fine;

    public Person(int id, String username, String password, boolean admin, boolean verify, int fine) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.verify = verify;
        this.fine = fine;
    }

    public Person(String username, String password) {
        this(-1, username, password, false, false, 0);
    }
}
