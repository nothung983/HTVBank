
package htvbank;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;


public class Withdraw extends JFrame implements ActionListener{
    int account_id2;
    JButton r500k, r100k, r200k, r1000k, r2000k, r5000k, r10000k, another, backButton;
    JTextField balanceTextField;
    Connect c = new Connect();
    Withdraw (int account_id) {
        
        setTitle("Withdraw");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);      
        setSize(800, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
                
        account_id2 = account_id;
        
        ImageIcon i1 = new ImageIcon(Login.class.getResource("/images/logo2.png"));
        Image i2 = i1.getImage().getScaledInstance(200, 60, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(300, 50, 200, 60);
        add(label);
        
        JLabel withdraw = new JLabel("Withdraw money");
        withdraw.setFont(new Font("Arial",Font.BOLD,20));
        withdraw.setForeground(Color.BLACK);
        withdraw.setBounds(80,120,200,30);
        add(withdraw);
        
        JLabel text = new JLabel("Please choose the amount you want to withdraw.");
        text.setFont(new Font("Osward",Font.ITALIC, 15));
        text.setBounds(80, 145, 500, 40);
        text.setForeground(Color.BLACK);
        add(text);
        
        JLabel balanceLabel = new JLabel("Current Balance:");
        balanceLabel.setFont(new Font("Raleway", Font.BOLD, 22));
        balanceLabel.setForeground(new Color(0, 51, 153));
        balanceLabel.setBounds(80, 200, 200, 40);
        add(balanceLabel);
        
        balanceTextField = new JTextField();
        balanceTextField.setBounds(300, 205, 250, 30);
        balanceTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        balanceTextField.setEditable(false);
        add(balanceTextField);

        r100k = new JButton("100.000 VND");
        r100k.setFont(new Font("Raleway", Font.BOLD, 18));
        r100k.setBounds(160, 260, 180, 45);
        r100k.setBackground(new Color(0, 51, 153));
        r100k.setForeground(Color.WHITE);
        r100k.addActionListener(this);
        add(r100k);
        
        r200k = new JButton("200.000 VND");
        r200k.setFont(new Font("Raleway", Font.BOLD, 18));
        r200k.setBounds(160, 320, 180, 45);
        r200k.setBackground(new Color(0, 51, 153));
        r200k.setForeground(Color.WHITE);
        r200k.addActionListener(this);
        add(r200k );
        
        r500k = new JButton("500.000 VND");
        r500k.setFont(new Font("Raleway", Font.BOLD, 18));
        r500k.setBounds(450, 260, 180, 45);
        r500k.setBackground(new Color(0, 51, 153));
        r500k.setForeground(Color.WHITE);
        r500k.addActionListener(this);
        add(r500k );

        r1000k = new JButton("1.000.000 VND");
        r1000k.setFont(new Font("Raleway", Font.BOLD, 18));
        r1000k.setBounds(450, 320, 180, 45);
        r1000k.setBackground(new Color(0, 51, 153));
        r1000k.setForeground(Color.WHITE);
        r1000k.addActionListener(this);
        add(r1000k );
        
        r2000k = new JButton("2.000.000 VND");
        r2000k.setFont(new Font("Raleway", Font.BOLD, 18));
        r2000k.setBounds(160, 380, 180, 45);
        r2000k.setBackground(new Color(0, 51, 153));
        r2000k.setForeground(Color.WHITE);
        r2000k.addActionListener(this);
        add(r2000k);
        
        r5000k = new JButton("5.000.000 VND");
        r5000k.setFont(new Font("Raleway", Font.BOLD, 18));
        r5000k.setBounds(450, 380, 180, 45);
        r5000k.setBackground(new Color(0, 51, 153));
        r5000k.setForeground(Color.WHITE);
        r5000k.addActionListener(this);
        add(r5000k);
 
        r10000k = new JButton("10.000.000 VND");
        r10000k.setFont(new Font("Raleway", Font.BOLD, 18));
        r10000k.setBounds(160, 440, 180, 45);
        r10000k.setBackground(new Color(0, 51, 153));
        r10000k.setForeground(Color.WHITE);
        r10000k.addActionListener(this);
        add(r10000k);
        
        another = new JButton("Another number");
        another.setFont(new Font("Raleway", Font.BOLD, 18));
        another.setBounds(450, 440, 180, 45);
        another.setBackground(new Color(0, 51, 153));
        another.setForeground(Color.WHITE);
        another.addActionListener(this);
        add(another );
        
        backButton = new JButton("Back");
        backButton.setFont(new Font("Raleway", Font.BOLD, 18));
        backButton.setBounds(305, 510, 180, 45);
        backButton.setBackground(new Color(0, 51, 153));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);
        
        displayCurrentBalance();
        setVisible(true);
    }
    public void displayCurrentBalance() {
        try {
            Connect c = new Connect();
            String query = "SELECT account_balance FROM accounts WHERE account_id = '" + account_id2 + "'";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                int balance = rs.getInt("account_balance");
                balanceTextField.setText(String.valueOf(balance)+" VND");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching balance.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
     public int enterNumber() {
        String input = JOptionPane.showInputDialog(this, "Enter an amount you want to withdraw:");

        if (input == null) {
            return -1; 
        }

        int amount = 0;
        try {
            amount = Integer.parseInt(input); 
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
                return enterNumber(); 
            }
        } catch (NumberFormatException e) { 
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a numeric amount.");
            return enterNumber(); 
        }

        return amount;
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backButton) {
            dispose();
            new MainInterface(account_id2);
        } else {
            int amount = 0;
            if (ae.getSource() == r100k) {
                amount = 100000;
            }
            else if (ae.getSource() == r200k) {
                amount = 200000;
            }
            else if (ae.getSource() == r500k) {
                amount = 500000;
            }
            else if (ae.getSource() == r1000k) {
                amount = 1000000;
            } 
            else if (ae.getSource() == r2000k) {
                amount = 2000000;
            }
            else if (ae.getSource() == r5000k) {
                amount = 5000000;
            }
            else if (ae.getSource() == r10000k) {
                amount = 10000000;
            }
            else if (ae.getSource() == another) {
                amount = enterNumber();
                if (amount == -1) {
                    return ;
                }
            }
            try {
                String query = "SELECT check_balance(" + account_id2 + ") AS current_balance;";
                ResultSet rs = c.s.executeQuery(query);
                if (rs.next()) {
                    if (rs.getInt("current_balance") < amount) {
                        JOptionPane.showMessageDialog(this, "Your account balance is not enough to withdraw");
                    } else {
                        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to withdraw this amount?", "Confirm Withdrawal", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            String query1 = "CALL WITHDRAW ("+account_id2+", "+amount+");";
                            c.s.executeQuery(query1);
                            JOptionPane.showMessageDialog(this, "Withdraw successful!");
                            ResultSet rs2 = c.s.executeQuery(query);
                            if (rs2.next()) {
                                int updatedBalance = rs2.getInt("current_balance");
                                balanceTextField.setText(String.valueOf(updatedBalance)+" VND");
                            } 
                        } else if (confirm == JOptionPane.NO_OPTION){
                             JOptionPane.showMessageDialog(this, "Withdraw unsuccessful!");
                        }    

                    }
                }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }
    public static void main(String[] args) {
        new Withdraw(3);
    }
            
}



