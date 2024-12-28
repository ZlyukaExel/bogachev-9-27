package org.example;

import org.example.progressions.Methods;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Filer implements Methods {
    static String directory;
    static String output;
    static List<List<String>> content;
    static String money;

    public void WriteFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(directory))) {
            bw.write(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void ReadFile() {
        content = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(directory));
            money = br.readLine();
            String line = br.readLine();
            while (line != null) {
                content.add(Arrays.stream(line.split(" ")).toList());
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
