package htvbank;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;

public class CheckIncomeExpense extends JFrame implements ActionListener {
    int account_id2;
    JTextField incomeTextField, expenseTextField;
    JComboBox<String> monthDropdown;
    JButton back;

    CheckIncomeExpense(int account_id) {
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setTitle("Check Income and Expense");
        setSize(800, 300);
        setLocation(350, 140);

        account_id2 = account_id;

        JLabel check = new JLabel("Income and expenses of month: ");
        check.setFont(new Font("Arial", Font.BOLD, 20));
        check.setForeground(new Color(0, 51, 153));
        check.setBounds(100, 40, 350, 30);
        add(check);

        String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };

        monthDropdown = new JComboBox<>(months);
        monthDropdown.setFont(new Font("Arial", Font.PLAIN, 16));
        monthDropdown.setBounds(420, 40, 100, 30);
        monthDropdown.setBackground(Color.WHITE);
        monthDropdown.setSelectedIndex(0);
        add(monthDropdown);

        JLabel income = new JLabel("Income: ");
        income.setFont(new Font("Arial", Font.PLAIN, 20));
        income.setForeground(Color.BLACK);
        income.setBounds(100, 80, 100, 30);
        add(income);

        incomeTextField = new JTextField();
        incomeTextField.setBounds(220, 80, 500, 30);
        incomeTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        incomeTextField.setEditable(false);
        add(incomeTextField);

        JLabel expense = new JLabel("Expenses: ");
        expense.setFont(new Font("Arial", Font.PLAIN, 20));
        expense.setForeground(Color.BLACK);
        expense.setBounds(100, 130, 100, 30);
        add(expense);

        expenseTextField = new JTextField();
        expenseTextField.setBounds(220, 130, 500, 30);
        expenseTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        expenseTextField.setEditable(false);
        add(expenseTextField);

        back = new JButton("BACK");
        back.setFont(new Font("Raleway", Font.BOLD, 18));
        back.setBounds(220, 170, 150, 30);
        back.setBackground(new Color(0, 51, 153));
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);
        
        monthDropdown.addActionListener(e -> displayIncome());
        monthDropdown.addActionListener(e ->  displayExpense());
        
        displayIncome();
        displayExpense();
        setVisible(true);
    }

    public void displayIncome() {
        try {
            // Map selected month to corresponding numeric value
            int monthIndex = monthDropdown.getSelectedIndex() + 1;
            String month2 = String.valueOf(monthIndex); 

            Connect c = new Connect();
            String query = "SELECT check_income(" + account_id2 + ", '" + month2 + "') as income;";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                int income = rs.getInt("income");
                incomeTextField.setText(income + " VND");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void displayExpense() {
        try {
            int monthIndex = monthDropdown.getSelectedIndex() + 1;
            String month2 = String.valueOf(monthIndex); 

            Connect c = new Connect();
            String query = "SELECT check_expense(" + account_id2 + ", '" + month2 + "') as expense;";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                int expense = rs.getInt("expense");
                expenseTextField.setText(expense + " VND");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            dispose();
            new TransferHistory(account_id2);
        }
    }
    public static void main(String[] args) {
        new CheckIncomeExpense(1);
    }
}


