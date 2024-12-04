package htvbank;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Debt extends JFrame implements ActionListener {
    JButton payDebtButton, applyLoanButton, backButton;
    int currentDebt;
    private int accountId;
    
    Debt(int accountId) {
        this.accountId = accountId;
        setTitle("DEBT");
        setLayout(null);

        try {
            Connect c = new Connect();
           
            String query = "SELECT show_debt(" + accountId + ")";  
            ResultSet rs = c.s.executeQuery(query);

            if (rs.next()) {
                currentDebt = rs.getInt(1);  
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        JLabel debtLabel = new JLabel("Your Current Debt: " + currentDebt + " VND", SwingConstants.CENTER);
        debtLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        debtLabel.setBounds(0, 160, 800, 30); 
        debtLabel.setForeground(Color.RED);
        add(debtLabel);

        JLabel titleText = new JLabel("Choose Your Option");
        titleText.setFont(new Font("Osward", Font.BOLD, 32));
        titleText.setBounds(250, 100, 500, 40);
        titleText.setForeground(new Color(0, 51, 153));
        add(titleText);

        payDebtButton = new JButton("Pay Debt");
        payDebtButton.setFont(new Font("Arial", Font.BOLD, 16));
        payDebtButton.setBackground(new Color(0, 51, 153));
        payDebtButton.setForeground(Color.WHITE);
        payDebtButton.setBounds(300, 200, 200, 40);
        payDebtButton.addActionListener(this);
        add(payDebtButton);

        applyLoanButton = new JButton("Apply for Loan");
        applyLoanButton.setFont(new Font("Arial", Font.BOLD, 16));
        applyLoanButton.setBackground(new Color(0, 51, 153));
        applyLoanButton.setForeground(Color.WHITE);
        applyLoanButton.setBounds(300, 260, 200, 40);
        applyLoanButton.addActionListener(this);
        add(applyLoanButton);
        
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(0, 51, 153));
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(60, 360, 120, 40);
        backButton.addActionListener(this);
        add(backButton);
        
        getContentPane().setBackground(Color.WHITE);
        setSize(800, 480);
        setLocation(350, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == payDebtButton) {
            new PayDebt(accountId);
        } else if (e.getSource() == applyLoanButton) {
            new Loan(accountId);
        } else if (e.getSource() == backButton){
            dispose();
            new MainInterface(accountId);
        }
    }
    public static void main(String[] args) {
        new Debt(1);
    }
}
