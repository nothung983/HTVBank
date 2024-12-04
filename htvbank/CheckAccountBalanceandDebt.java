
package htvbank;

import java.sql.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class CheckAccountBalanceandDebt extends JFrame implements ActionListener {
    JButton Back;
    JTextField DebtTextField, balanceTextField;
    int account_id2;
    CheckAccountBalanceandDebt (int account_id){
        
        setTitle("ACCOUNT BALANCE AND DEBT");
        setLayout(null);
        setSize(580, 500);
        getContentPane().setBackground(Color.WHITE);
        account_id2 = account_id;
        
        ImageIcon i1 = new ImageIcon(Login.class.getResource("/images/logo2.png"));
        Image i2 = i1.getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(180, 80, 200, 50);
        add(label);
        
        JLabel DebtLabel = new JLabel("Current Debt:");
        DebtLabel.setFont(new Font("Arial", Font.ITALIC, 22));
        DebtLabel.setForeground(Color.BLACK);
        DebtLabel.setBounds(50, 140, 200, 40);
        add(DebtLabel);
        
        DebtTextField = new JTextField();
        DebtTextField.setBounds(50, 200, 250, 30);
        DebtTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        DebtTextField.setEditable(false);
        add(DebtTextField);
        
        JLabel balanceLabel = new JLabel("Current Balance:");
        balanceLabel.setFont(new Font("Arial", Font.ITALIC, 22));
        balanceLabel.setForeground(Color.BLACK);
        balanceLabel.setBounds(50, 250, 250, 40);
        add(balanceLabel);
        
        balanceTextField = new JTextField();
        balanceTextField.setBounds(50, 300, 250, 30);
        balanceTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        balanceTextField.setEditable(false);
        add(balanceTextField);
        
        Back = new JButton("Back");
        Back.setBounds(220, 355, 135, 30);
        Back.setBackground(new Color(0, 51, 153));
        Back.setForeground(Color.WHITE);
        Back.addActionListener(this);
        add(Back);
        
        
        displayCurrentBalance();
        displayCurrentDebt();
        setVisible(true);
        setLocation(450,150);
    }

    public void displayCurrentBalance() {
        try {
          Connect c = new Connect();
          String query = "SELECT CHECK_BALANCE(" + account_id2 + ") AS account_balance;";
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
    
    public void displayCurrentDebt() {
        try {
          Connect c = new Connect();
          String query = "SELECT SHOW_DEBT(" + account_id2 + ") AS debt_amount;";
          ResultSet rs = c.s.executeQuery(query);
          if (rs.next()) {
            int debt = rs.getInt("debt_amount");
            DebtTextField.setText(String.valueOf(debt) + " VND");
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching balance.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == Back) {
            new MainInterface(account_id2);
        }
    }
    
    public static void main(String[] args) {
        new CheckAccountBalanceandDebt(8);
    }
}

