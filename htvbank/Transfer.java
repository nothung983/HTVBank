package htvbank;

import java.sql.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.*;

public class Transfer extends JFrame implements ActionListener {
    JButton submit, clear, backButton;
    JTextField recipientPhoneTextField, transferAmountTextField, balanceTextField;
    int senderAccountID;

    Transfer(int accountID) {
        this.senderAccountID = accountID;

        setTitle("Money Transfer");
        setLayout(null);
        setSize(800, 500);
        getContentPane().setBackground(Color.WHITE);
        
        
        ImageIcon logoIcon = new ImageIcon(Login.class.getResource("/images/logo2.png"));
        Image logoImage = logoIcon.getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoLabel.setBounds(320, 80, 200, 50);
        add(logoLabel);

        
        JLabel titleLabel = new JLabel("Money Transfer");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(300, 40, 300, 40);
        titleLabel.setForeground(new Color(0, 51, 153));
        add(titleLabel);

        
        JLabel recipientInfoLabel = new JLabel("Recipient Information");
        recipientInfoLabel.setFont(new Font("Arial", Font.BOLD, 25));
        recipientInfoLabel.setBounds(50, 145, 300, 50);
        recipientInfoLabel.setForeground(new Color(0, 51, 153));
        add(recipientInfoLabel);

       
        JLabel recipientPhoneLabel = new JLabel("Recipient Phone Number:");
        recipientPhoneLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        recipientPhoneLabel.setForeground(Color.BLACK);
        recipientPhoneLabel.setBounds(50, 180, 250, 50);
        add(recipientPhoneLabel);

        recipientPhoneTextField = new JTextField();
        recipientPhoneTextField.setBounds(50, 230, 280, 30);
        recipientPhoneTextField.setFont(new Font("Arial", Font.BOLD, 20));
        add(recipientPhoneTextField);

      
        JLabel transferAmountLabel = new JLabel("Amount to Transfer:");
        transferAmountLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        transferAmountLabel.setForeground(Color.BLACK);
        transferAmountLabel.setBounds(50, 255, 200, 50);
        add(transferAmountLabel);

        transferAmountTextField = new JTextField();
        transferAmountTextField.setBounds(50, 305, 280, 30);
        transferAmountTextField.setFont(new Font("Arial", Font.BOLD, 20));
        add(transferAmountTextField);

       
        JLabel balanceLabel = new JLabel("Current Balance:");
        balanceLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        balanceLabel.setForeground(Color.BLACK);
        balanceLabel.setBounds(370, 180, 200, 40);
        add(balanceLabel);

        balanceTextField = new JTextField();
        balanceTextField.setBounds(370, 230, 250, 30);
        balanceTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        balanceTextField.setEditable(false);
        add(balanceTextField);

       
        submit = new JButton("SUBMIT");
        submit.setBounds(280, 355, 135, 30);
        submit.setBackground(new Color(0, 51, 153));
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        add(submit);

        clear = new JButton("CLEAR");
        clear.setBounds(425, 355, 135, 30);
        clear.setBackground(new Color(0, 51, 153));
        clear.setForeground(Color.WHITE);
        clear.addActionListener(this);
        add(clear);
        
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(0, 51, 153));
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(60, 380, 120, 40);
        backButton.addActionListener(this);
        add(backButton);
        

        displayCurrentBalance();
        setVisible(true);
        setLocation(350, 200);
    }

    private void displayCurrentBalance() {
        try {
            Connect c = new Connect();
            String query = "SELECT check_balance(" + senderAccountID + ") AS account_balance;";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                int balance = rs.getInt("account_balance");
                balanceTextField.setText(balance + " VND");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching balance.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == clear) {
            recipientPhoneTextField.setText("");
            transferAmountTextField.setText("");
        } else if (ae.getSource() == submit) {
            String recipientPhone = recipientPhoneTextField.getText();
            String transferAmount = transferAmountTextField.getText();

            if (recipientPhone.isEmpty() || transferAmount.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            try {
                String receiverPhone = recipientPhone;
                double amount = Double.parseDouble(transferAmount);

                if (amount <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid transfer amount.");
                    return;
                }

                // Fetch the sender's phone number based on sender's account ID
                Connect c = new Connect();
                String senderPhoneQuery = "SELECT customer_phone_number FROM customers WHERE account_id = " + senderAccountID + ";";
                ResultSet rsSender = c.s.executeQuery(senderPhoneQuery);

                String senderPhone = "";
                if (rsSender.next()) {
                    senderPhone = rsSender.getString("customer_phone_number");
                }

                String query = "CALL TRANSFER('" + senderPhone + "', '" + receiverPhone + "', " + amount + ");";
                c.s.executeUpdate(query); // Use executeUpdate instead of executeQuery for a procedure call
                JOptionPane.showMessageDialog(this, "Successfully transferred!", "Transfer", JOptionPane.INFORMATION_MESSAGE);
                displayCurrentBalance();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter numeric values for Amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Transfer Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource()== backButton){
            dispose();
            new MainInterface(senderAccountID);
        }
    }

    public static void main(String[] args) {
        new Transfer(3);
    }
}
