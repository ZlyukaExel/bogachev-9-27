package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public static void WriteFile(String directory, int output) { //==============================================
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(directory))) {
            bw.write(String.valueOf(output));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
