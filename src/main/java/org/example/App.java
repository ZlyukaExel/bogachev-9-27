package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

import static org.example.Reader.ReadFile;
import static org.example.Writer.WriteFile;

public class App {
    private final DefaultTableModel model;
    private int rows = 5;
    private int columns = 5;

    public App() {
        JFrame frame = new JFrame("Поиск прогрессии!");
        frame.setTitle("Поиск прогрессии!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        frame.setLayout(new BorderLayout());

        model = new DefaultTableModel(rows, columns);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Скорее вводи числа!");
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(label);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2));

        JButton readButton = new JButton("Загрузить файл");
        JButton outputButton = new JButton("Сохранить результат");
        JButton addColumnButton = new JButton("Добавить столбец");
        JButton addRowButton = new JButton("Добавить строку");
        JButton resetButton = new JButton("Пересоздать таблицу");
        JButton showResultButton = new JButton("Найти победителя");

        buttonPanel.add(readButton);
        buttonPanel.add(outputButton);
        buttonPanel.add(addColumnButton);
        buttonPanel.add(addRowButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(showResultButton);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        readButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        model.setValueAt(null, i, j);
                    }
                }

                int[][] board = ReadFile(file.getAbsolutePath());
                rows = board.length;
                columns = board[0].length;
                model.setRowCount(rows);
                model.setColumnCount(columns);
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        int val = board[i][j];
                        model.setValueAt(val == 2 ? "" : val, i, j); //==================================
                    }
                }
            }
        });


        outputButton.addActionListener(e -> {
            int[][] outputArray = GetArray();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setSelectedFile(new File("output.txt"));
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                WriteFile(file.getAbsolutePath(), FindWinner(outputArray)); //============================
            }
        });

        addColumnButton.addActionListener(e -> {
            model.setColumnCount(model.getColumnCount() + 1);
            columns++;
        });

        addRowButton.addActionListener(e -> {
            model.setRowCount(model.getRowCount() + 1);
            rows++;
        });

        resetButton.addActionListener(e -> {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    model.setValueAt(null, i, j);
                }
            }
            model.setRowCount(5);
            rows = 5;
            model.setColumnCount(5);
            columns = 5;
        });

        showResultButton.addActionListener(e -> {
            int winner = FindWinner(GetArray()); //==========================================
            switch (winner) {
                case 1: {
                    label.setText("Победили 1! Поздравляем!");
                    break;
                }
                case 0: {
                    label.setText("Победила дружба! Поздравляем!");
                    break;
                }
                case -1: {
                    label.setText("Победили 0! Поздравляем!");
                    break;
                }
            }
        });

        frame.setVisible(true);
    }

    public int[][] GetArray() {
        int[][] array = new int[rows][columns]; //===============================================
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                String val = String.valueOf(model.getValueAt(i, j));
                array[i][j] = val.isEmpty() || val.equals("null") ? 2 : Integer.parseInt(val);
            }
        }
        return array;
    }
}
