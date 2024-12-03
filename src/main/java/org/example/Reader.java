package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {

    public static int[][] ReadFile(String file) {
        int[][] res;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String[] line = br.readLine().trim().split(" "); //===================================
            int v = Integer.parseInt(line[0]);
            int h = Integer.parseInt(line[1]);
            res = new int[v][h];
            for (int i = 0; i < v; i++) {
                line = br.readLine().trim().split("\\s*,\\s*",-1);
                for (int j = 0; j < h; j++) {
                    String str = line[j];
                    int val = Integer.parseInt(str.isEmpty() ? "2" : str);
                    res[i][j] = val;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
