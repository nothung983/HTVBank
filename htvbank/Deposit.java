package htvbank;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Deposit extends JFrame implements ActionListener {
    JTextField amountTextField, balanceTextField;
    JButton confirmButton, clearButton, Back;
    private int accountId;

    Deposit(int a_id) {
        accountId = a_id;

        setTitle("Deposit");
        setLayout(null);

        ImageIcon i1 = new ImageIcon(Login.class.getResource("/images/logo2.png"));
        Image i2 = i1.getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT);
        JLabel label = new JLabel(new ImageIcon(i2));
        label.setBounds(300, 30, 200, 50);
        add(label);

        JLabel titleText = new JLabel("Money Deposit");
        titleText.setFont(new Font("Osward", Font.BOLD, 32));
        titleText.setBounds(280, 100, 300, 40);
        titleText.setForeground(new Color(0, 51, 153));
        add(titleText);

        JLabel amountLabel = new JLabel("Enter the Amount:");
        amountLabel.setFont(new Font("Raleway", Font.BOLD, 22));
        amountLabel.setForeground(new Color(0, 51, 153));
        amountLabel.setBounds(150, 180, 200, 40);
        add(amountLabel);

        amountTextField = new JTextField();
        amountTextField.setBounds(370, 185, 250, 30);
        amountTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        add(amountTextField);

        JLabel balanceLabel = new JLabel("Current Balance:");
        balanceLabel.setFont(new Font("Raleway", Font.BOLD, 22));
        balanceLabel.setForeground(new Color(0, 51, 153));
        balanceLabel.setBounds(150, 240, 200, 40);
        add(balanceLabel);

        balanceTextField = new JTextField();
        balanceTextField.setBounds(370, 245, 250, 30);
        balanceTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        balanceTextField.setEditable(false);
        add(balanceTextField);

        confirmButton = new JButton("Confirm");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setBackground(new Color(0, 51, 153));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setBounds(370, 300, 120, 30);
        confirmButton.addActionListener(this);
        add(confirmButton);

        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 16));
        clearButton.setBackground(new Color(204, 0, 0));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBounds(500, 300, 120, 30);
        clearButton.addActionListener(this);
        add(clearButton);
        
        Back = new JButton("Back");
        Back.setFont(new Font("Arial", Font.BOLD, 20));
        Back.setBackground(new Color(0, 51, 153));
        Back.setForeground(Color.WHITE);
        Back.setBounds(60, 360, 100, 40);
        Back.addActionListener(this);
        add(Back);

        getContentPane().setBackground(Color.WHITE);
        setSize(800, 480);
        setLocation(350, 200);
        setVisible(true);

        displayCurrentBalance();
    }

    public void displayCurrentBalance() {
        try {
            Connect c = new Connect();
            String query = "SELECT check_balance(" + accountId + ") AS account_balance;";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                int balance = rs.getInt("account_balance");
                balanceTextField.setText(String.valueOf(balance) + " VND");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching balance.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            String amountText = amountTextField.getText();
            try {
                int amount = Integer.parseInt(amountText);

                Connect c = new Connect();

                String query1 = "SELECT CHECK_BALANCE (" + accountId + ");";
                ResultSet rs = c.s.executeQuery(query1);

                if (rs.next()) {                   
                    String query2 = "CALL DEPOSIT (" + accountId + ", " + amount + ")";
                    c.s.executeQuery(query2); 

                    displayCurrentBalance(); 
                    JOptionPane.showMessageDialog(null, "Depositing amount: " + amount + " VND");
                } else {
                    JOptionPane.showMessageDialog(null, "Account not found!");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid amount.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
            }
        } else if (e.getSource() == clearButton) {
            amountTextField.setText("");
        } else if (e.getSource() == Back) {
            dispose();
            new MainInterface(accountId);
        }
    }
    public static void main(String[] args) {
        new Deposit(8);
    }
}
