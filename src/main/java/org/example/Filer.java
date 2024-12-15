package org.example;

import org.example.progressions.Methods;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Filer implements Methods {
    static String directory;
    static List<Integer> output;

    public void WriteFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(directory))) {
            for(Integer val : output) {
                bw.write(String.valueOf(output) + " ");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void ReadFile() {
        output = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(directory));
            for (String num : br.readLine().trim().split(", ")) {
                output.add(Integer.parseInt(num));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
