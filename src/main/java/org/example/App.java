package org.example;

import org.example.progressions.Methods;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class App {
    private final DefaultTableModel model;
    private int columns = 2;
    private Methods methods;

    public App() {
        JFrame frame = new JFrame("Поиск прогрессии!");
        frame.setTitle("Поиск прогрессии!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 220);

        frame.setLayout(new BorderLayout());

        model = new DefaultTableModel(1, columns);
        for (int i = 0; i < columns; i++) {
            model.setValueAt(0, 0, i);
        }
        JTable table = new JTable(model);
        table.setRowHeight(50);
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, new AutoSelectCellEditor());
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
        JButton addColumnButton = new JButton("Добавить число");
        JButton deleteColumnButton = new JButton("Удалить число");
        JButton resetButton = new JButton("Пересоздать таблицу");
        JButton showResultButton = new JButton("Найти последовательность");

        buttonPanel.add(readButton);
        buttonPanel.add(outputButton);
        buttonPanel.add(addColumnButton);
        buttonPanel.add(deleteColumnButton);
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
                for (int i = 0; i < columns; i++) {
                    model.setValueAt(null, 0, i);
                }

                Filer.directory = file.getAbsolutePath();
                methods.ReadFile();
                List<Integer> board = Filer.output;

                columns = board.size();
                model.setColumnCount(columns);
                for (int i = 0; i < columns; i++) {
                    int val = board.get(i);
                    model.setValueAt(val, 0, i);
                }
            }
        });

        outputButton.addActionListener(e -> {
            List<Integer> outputList = GetList();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setSelectedFile(new File("output.txt"));
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                Filer.directory = file.getAbsolutePath();
                Filer.output = FindProg(outputList);
                methods.WriteFile();
            }
        });

        addColumnButton.addActionListener(e -> {
            model.setColumnCount(model.getColumnCount() + 1);
            model.setValueAt(0, 0, columns);
            columns++;
        });

        deleteColumnButton.addActionListener(e -> {
            if (columns > 2) {
                model.setColumnCount(model.getColumnCount() - 1);
                columns--;
            }
            else
                label.setText("Минимум два элемента должны остаться!");
        });

        resetButton.addActionListener(e -> {
            for (int j = 0; j < columns; j++) {
                model.setValueAt(0, 0, j);
            }
            model.setRowCount(1);
            model.setColumnCount(2);
            columns = 2;
        });

        showResultButton.addActionListener(e -> {
            List<Integer> list = FindProg(GetList());
            StringBuilder text = new StringBuilder("Результат:");
            for (Integer val : list)
                text.append(" ").append(val);
            label.setText(text.toString());
        });

        frame.setVisible(true);
    }

    public List<Integer> GetList() {
        List<Integer> list = new ArrayList<>();
        for (int j = 0; j < columns; j++) {
            String val = String.valueOf(model.getValueAt(0, j));
            list.add(val.isEmpty() || val.equals("null") ? 0 : Integer.parseInt(val));
        }
        return list;
    }

    public List<Integer> FindProg(List<Integer> list) {
        FindingProgression.process(list);
        return FindingProgression.integerList;
    }
}

class AutoSelectCellEditor extends DefaultCellEditor {

    public AutoSelectCellEditor() {
        super(new JTextField());
        this.getComponent().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField textField = (JTextField) getComponent();
                textField.selectAll();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JTextField textField = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
        textField.setText(value != null ? value.toString() : "");
        return textField;
    }
}
