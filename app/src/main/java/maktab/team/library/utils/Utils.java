package maktab.team.library.utils;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

public class Utils {

    @Getter
    public static final Scanner scanner = new Scanner(System.in);
    public static void clearConsole() {
        System.out.print(StringUtils.repeat("\n", 25));
    }
}
