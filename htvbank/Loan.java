package htvbank;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Loan extends JFrame implements ActionListener {
    private JTextField loanAmountField;
    private JButton applyButton, backButton;
    private JComboBox<String> interestDurationComboBox;
    private int accountId;

    Loan(int accountId) {
        this.accountId = accountId;
        setTitle("Apply for Loan");
        setLayout(null);

        JLabel titleLabel = new JLabel("Apply for a Loan");
        titleLabel.setFont(new Font("Osward", Font.BOLD, 32));
        titleLabel.setBounds(250, 50, 300, 40);
        titleLabel.setForeground(new Color(0, 51, 153));
        add(titleLabel);

        JLabel instructionLabel = new JLabel("Enter Loan Amount:");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        instructionLabel.setBounds(150, 150, 200, 30);
        add(instructionLabel);

        loanAmountField = new JTextField();
        loanAmountField.setFont(new Font("Arial", Font.PLAIN, 18));
        loanAmountField.setBounds(375, 150, 200, 30);
        add(loanAmountField);

        JLabel interestDurationLabel = new JLabel("Select Interest Duration:");
        interestDurationLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        interestDurationLabel.setBounds(150, 200, 300, 30);
        add(interestDurationLabel);

        String[] durations = {"12 Months", "24 Months"};
        interestDurationComboBox = new JComboBox<>(durations);
        interestDurationComboBox.setFont(new Font("Arial", Font.PLAIN, 18));
        interestDurationComboBox.setBounds(375, 200, 200, 30);
        add(interestDurationComboBox);

        applyButton = new JButton("Apply");
        applyButton.setFont(new Font("Arial", Font.BOLD, 16));
        applyButton.setBackground(new Color(0, 51, 153));
        applyButton.setForeground(Color.WHITE);
        applyButton.setBounds(370, 260, 100, 40);
        applyButton.addActionListener(this);
        add(applyButton);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(0, 51, 153));
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(480, 260, 100, 40);
        backButton.addActionListener(this);
        add(backButton);

        getContentPane().setBackground(Color.WHITE);
        setSize(800, 480);
        setLocation(350, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == applyButton) {
            String loanAmountText = loanAmountField.getText();
            String selectedDuration = (String) interestDurationComboBox.getSelectedItem();

            try {
                int loanAmount = Integer.parseInt(loanAmountText);

                if (loanAmount <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter a positive amount.");
                    return;
                }
                
                int debt_duration = selectedDuration.equals("12 Months") ? 12 : 24;
                double debt_rate = selectedDuration.equals("12 Months") ? 0.1 : 0.2;
                
                int totalPay = (int)(loanAmount + (loanAmount * debt_rate));
                int monthlyPay = totalPay / debt_duration;
                
                Connect c = new Connect();
                String query = "CALL LOAN (" + accountId + ", " + loanAmount + ", " + debt_rate + ");";
                c.s.executeQuery(query);

                JOptionPane.showMessageDialog(
                        this,
                        "Loan Application Submitted!\n" +
                        "Total Payable Amount: " + totalPay + "\n" +
                        "Monthly Payment: " + monthlyPay
                );
                
                dispose();
                new Loan(accountId);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred. Please try again.");
            }
        } else if (e.getSource() == backButton) {
            dispose();
            new Debt(accountId);
        }
    }
}
