package PackageExplorer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ExpenseTracker extends JFrame {
    private JTextField expenseField;
    private JTextField descriptionField;
    private JTable expenseTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;

    public ExpenseTracker() {
        setTitle("Expense Tracker");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        descriptionField = new JTextField(15);
        expenseField = new JTextField(10);
        JButton addButton = new JButton("Add Expense");

        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descriptionField);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(expenseField);
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table for Expenses
        String[] columnNames = {"Description", "Amount"};
        tableModel = new DefaultTableModel(columnNames, 0);
        expenseTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(expenseTable);

        add(scrollPane, BorderLayout.CENTER);

        // Total Expenses Label
        totalLabel = new JLabel("Total Expenses: $0.00");
        add(totalLabel, BorderLayout.SOUTH);

        // Button Action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExpense();
            }
        });
    }

    private void addExpense() {
        String description = descriptionField.getText();
        String amountText = expenseField.getText();

        if (description.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            tableModel.addRow(new Object[]{description, amount});
            updateTotal();
            descriptionField.setText("");
            expenseField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTotal() {
        double total = 0.0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            total += (double) tableModel.getValueAt(i, 1);
        }
        totalLabel.setText(String.format("Total Expenses: $%.2f", total));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExpenseTracker tracker = new ExpenseTracker();
            tracker.setVisible(true);
        });
    }
}