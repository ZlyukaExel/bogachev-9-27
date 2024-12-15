package org.example;

import java.util.Arrays;
import java.util.MissingFormatArgumentException;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("-a")) {
            if (args[1].equals("console"))
                new ConsoleApp(Arrays.copyOfRange(args, 2, args.length));
            else if (args[1].equals("window"))
                new App();
            else
                throw new IllegalArgumentException("Указанный тип приложения не существует!");
        } else
            throw new MissingFormatArgumentException("Тип приложения не указан! -a <app-type>");
    }
}