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
    private int rows = 1;
    private final Methods methods;
    private final JLabel label;
    private final JTextField inputField;

    public App() {
        this.methods = new Filer();
        JFrame frame = new JFrame("Покупка конфет!");
        frame.setTitle("Покупка конфет!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 660);

        frame.setLayout(new BorderLayout());

        label = new JLabel("Скорее вводи данные!");
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(label, BorderLayout.NORTH);

        model = new DefaultTableModel(rows, 2);

        JTable table = new JTable(model);

        table.getColumnModel().getColumn(0).setHeaderValue("Название конфеты");
        table.getColumnModel().getColumn(1).setHeaderValue("Цена за кг");

        table.setRowHeight(50);
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, new AutoSelectCellEditor());
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        JLabel inputFieldLabel = new JLabel("Твой бюджет:");
        inputFieldLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 12));
        inputFieldLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(inputFieldLabel, BorderLayout.NORTH);

        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(30, 25));
        bottomPanel.add(inputField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2));

        JButton readButton = new JButton("Загрузить конфеты из файла");
        JButton outputButton = new JButton("Сохранить результат");
        JButton addRowButton = new JButton("Добавить конфету");
        JButton deleteRowButton = new JButton("Удалить конфету");
        JButton resetButton = new JButton("Пересоздать таблицу");
        JButton showResultButton = new JButton("Купить конфеты!");

        buttonPanel.add(readButton);
        buttonPanel.add(outputButton);
        buttonPanel.add(addRowButton);
        buttonPanel.add(deleteRowButton);
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
                Filer.directory = file.getAbsolutePath();
                label.setText("Загружен файл " + file.getAbsolutePath());
                methods.ReadFile();
                inputField.setText(Filer.money);
                List<List<String>> candies = Filer.content;
                rows = candies.size();
                model.setRowCount(rows);
                for (int i = 0; i < rows; i++) {
                    model.setValueAt(candies.get(i).getFirst(), i, 0);
                    model.setValueAt(candies.get(i).getLast(), i, 1);
                }
            }
        });

        outputButton.addActionListener(e -> {
            printResult();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setSelectedFile(new File("output.txt"));
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                Filer.directory = file.getAbsolutePath();
                Filer.output = label.getText();
                methods.WriteFile();
            }
        });

        addRowButton.addActionListener(e -> {
            model.setRowCount(model.getRowCount() + 1);
            rows++;
            label.setText("Конфета добавлена!");
        });

        deleteRowButton.addActionListener(e -> {
            if (rows > 1) {
                model.setRowCount(model.getRowCount() - 1);
                rows--;
                label.setText("Конфета удалена!");
            } else label.setText("Хотя бы одна конфета должна остаться!");
        });

        resetButton.addActionListener(e -> {
            model.setRowCount(1);
            rows = 1;
            for (int j = 0; j < 2; j++) {
                model.setValueAt(null, 0, j);
            }
            label.setText("Таблица пересоздана!");
        });

        showResultButton.addActionListener(e -> printResult());

        frame.setVisible(true);
    }


    public void printResult() {
        List<Candy> candies = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            Object name = model.getValueAt(i, 0);
            Object priceStr = model.getValueAt(i, 1);
            double price;

            if (name == null || name.toString().isEmpty()) {
                label.setText("Названия всех конфет должны быть указаны!");
                return;
            }
            if (priceStr == null || priceStr.toString().isEmpty()) {
                label.setText("Цены всех конфет должны быть указаны!");
                return;
            } else {
                try {
                    price = Double.parseDouble(priceStr.toString());
                } catch (NumberFormatException error) {
                    label.setText("Цена должна быть указана числом!");
                    return;
                }
            }
            if (price > 0) candies.add(new Candy(name.toString(), price));
            else {
                label.setText("Мы тут благотворительностью не занимаемся!");
                return;
            }
        }
        String moneyStr = inputField.getText();
        double money;
        if (moneyStr == null || moneyStr.isEmpty()) {
            label.setText("Ты не указал свой бюджет!");
            return;
        }
        try {
            money = Double.parseDouble(moneyStr);
        } catch (NumberFormatException error) {
            label.setText("Твой бюджет должен быть указан числом!");
            return;
        }
        if (money <= 0) {
            label.setText("Поднакопи-ка лучше денег и приходи в другой раз...");
        } else label.setText(Candies.Count(candies, money));
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
