package htvbank;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class PayDebt extends JFrame implements ActionListener {
    private int accountId;
    private int currentDebt;
    private JButton payButton, backButton, payAllButton, payMonthlyButton;
    private JComboBox<String> debtSelector;
    private ArrayList<String> debtList;
    private ArrayList<Integer> debtIds;

    public PayDebt(int accountId) {
        this.accountId = accountId;

        setTitle("PAY DEBT");
        setLayout(null);

        debtList = new ArrayList<>();
        debtIds = new ArrayList<>();

        try {
            Connect c = new Connect();
            String query = "SELECT show_debt(" + accountId + ");";
            ResultSet rs = c.s.executeQuery(query);
        
            if (rs.next()) {
                currentDebt = rs.getInt(1);
            }

            String debtQuery = "SELECT debt_id, debt_interest FROM debt WHERE account_id = " + accountId + ";";
            ResultSet debtRs = c.s.executeQuery(debtQuery);
            
            while (debtRs.next()) {
                int debtId = debtRs.getInt("debt_id");
                int debtAmount = debtRs.getInt("debt_interest");
                debtIds.add(debtId);
                debtList.add("Debt ID: " + debtId + " - Amount: $" + debtAmount);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JLabel titleText = new JLabel("Pay Your Debt");
        titleText.setFont(new Font("Osward", Font.BOLD, 32));
        titleText.setBounds(290, 50, 300, 40);
        titleText.setForeground(new Color(0, 51, 153));
        add(titleText);

        JLabel debtLabel = new JLabel("Your Current Debt: " + currentDebt + " VND", SwingConstants.CENTER);
        debtLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        debtLabel.setBounds(0, 160, 800, 30); 
        debtLabel.setForeground(Color.RED);
        add(debtLabel);

        payAllButton = new JButton("Pay All");
        payAllButton.setFont(new Font("Arial", Font.BOLD, 16));
        payAllButton.setBackground(new Color(0, 51, 153));
        payAllButton.setForeground(Color.WHITE);
        payAllButton.setBounds(240, 230, 150, 40);
        payAllButton.addActionListener(this);
        add(payAllButton);

        payMonthlyButton = new JButton("Pay Monthly");
        payMonthlyButton.setFont(new Font("Arial", Font.BOLD, 16));
        payMonthlyButton.setBackground(new Color(0, 51, 153));
        payMonthlyButton.setForeground(Color.WHITE);
        payMonthlyButton.setBounds(420, 230, 150, 40);
        payMonthlyButton.addActionListener(this);
        add(payMonthlyButton);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(0, 51, 153));
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(60, 360, 120, 40);
        backButton.addActionListener(this);
        add(backButton);

        debtSelector = new JComboBox<>(debtList.toArray(new String[0]));
        debtSelector.setFont(new Font("Arial", Font.PLAIN, 18));
        debtSelector.setBounds(250, 300, 300, 30);
        add(debtSelector);


        getContentPane().setBackground(Color.WHITE);
        setSize(800, 480);
        setLocation(350, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedDebtIndex = debtSelector.getSelectedIndex();
        if (selectedDebtIndex == -1) {
            if (e.getSource() == backButton) {
                dispose();
                new Debt(accountId);
                return;
            }
            JOptionPane.showMessageDialog(this, "No debt selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int selectedDebtId = debtIds.get(selectedDebtIndex);
        Connect c = new Connect();
        String query = "SELECT debt_interest, monthly_pay FROM debt WHERE debt_id = " + selectedDebtId + " AND account_id = " + accountId + ";";
    
        int payAmount = 0;
        
        try{   
            if (e.getSource() == payAllButton) {
                    ResultSet rs = c.s.executeQuery(query);

                    if (rs.next()) {
                        payAmount = rs.getInt("debt_interest");
                    }
                    String query1 = "CALL PayDebt(" + accountId + ", " + selectedDebtId + ", " + payAmount + ");";
                    c.s.executeUpdate(query1);

                    JOptionPane.showMessageDialog(this, "Debt ID " + selectedDebtId + " paid off successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new PayDebt(accountId);

            } else if (e.getSource() == payMonthlyButton) {
                    ResultSet rs = c.s.executeQuery(query);

                    if (rs.next()) {
                        payAmount = rs.getInt("monthly_pay");
                    }
                    String query1 = "CALL PayDebt (" + accountId + ", " + selectedDebtId + ", " + payAmount + ");";
                    c.s.executeUpdate(query1);

                    JOptionPane.showMessageDialog(this, "Debt ID " + selectedDebtId + " monthly paid off successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new PayDebt(accountId);
                    
            } else if (e.getSource() == backButton) {
                dispose();
                new Debt(accountId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            
            if (ex.getMessage().contains("Insufficient funds")) {
                JOptionPane.showMessageDialog(this, "You do not have enough funds to pay the debt.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while processing the payment.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    } 
    public static void main(String[] args) {
        new PayDebt(1);
    }
}
