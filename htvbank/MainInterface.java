package htvbank;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class MainInterface extends JFrame implements ActionListener {

    private JButton withdrawButton, transferButton, depositButton, accountInfoButton, balanceButton, debtButton, CheckAccountBalanceandDebtButton, TransferHistoryButton, logoutButton;
    private int accountId;

    MainInterface(int a_id) {
        accountId = a_id;

        setTitle("HTV BANK MANAGEMENT SYSTEM");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        
        ImageIcon customerIcon = new ImageIcon(getClass().getResource("/images/customer.png"));
        Image customerImage = customerIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel customerLabel = new JLabel(new ImageIcon(customerImage));
        customerLabel.setBounds(20, 20, 50, 50);
        add(customerLabel);

        
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/logo2.png"));
        Image logoImage = logoIcon.getImage().getScaledInstance(200, 50, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoLabel.setBounds(350, 50, 200, 50);
        add(logoLabel);

        
        JPanel whitePanel = new JPanel();
        whitePanel.setBackground(Color.WHITE);
        whitePanel.setBounds(60, 150, 750, 580);
        whitePanel.setLayout(null);
        add(whitePanel);

        
        JLabel titleText = new JLabel("Choose Your Option", SwingConstants.CENTER);
        titleText.setFont(new Font("Osward", Font.BOLD, 32));
        titleText.setBounds(10, 10, whitePanel.getWidth(), 40);
        titleText.setForeground(new Color(0, 51, 153));
        titleText.setOpaque(false);
        whitePanel.add(titleText);

        
        String getName = "";
        try {
            Connect c = new Connect();
            String query = "SELECT account_name FROM accounts WHERE account_id = " + accountId;
            ResultSet rs = c.s.executeQuery(query);

            if (rs.next()) {
                getName = rs.getString("account_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JLabel welcomeText = new JLabel("<html>Welcome, " + getName + "<br>Your ID: " + accountId + "</html>");
        welcomeText.setFont(new Font("Osward", Font.BOLD, 16));
        welcomeText.setForeground(new Color(0, 51, 153));
        welcomeText.setBounds(80, 22, 300, 40); 
        add(welcomeText);

        
        withdrawButton = createOutlinedButton("Money Withdraw");
        withdrawButton.setBounds(60, 90, 200, 80);
        whitePanel.add(withdrawButton);

        transferButton = createOutlinedButton("Money Transfer");
        transferButton.setBounds(285, 90, 200, 80);
        whitePanel.add(transferButton);

        depositButton = createOutlinedButton("Money Deposit");
        depositButton.setBounds(510, 90, 200, 80);
        whitePanel.add(depositButton);

        debtButton = createOutlinedButton("Debt");
        debtButton.setBounds(285, 190, 200, 80);
        whitePanel.add(debtButton);

        accountInfoButton = createOutlinedButton("Account Information");
        accountInfoButton.setBounds(510, 190, 200, 80);
        whitePanel.add(accountInfoButton);

        balanceButton = createOutlinedButton("Balance Enquiry");
        balanceButton.setBounds(285, 290, 200, 80);
        whitePanel.add(balanceButton);

        TransferHistoryButton = createOutlinedButton("Transfer History");
        TransferHistoryButton.setBounds(60, 190, 200, 80);
        whitePanel.add(TransferHistoryButton);

        logoutButton = createOutlinedButton("Logout");
        logoutButton.setBounds(750, 20, 100, 40); 
        logoutButton.setBackground(new Color(0, 51, 153)); 
        logoutButton.setForeground(Color.WHITE); 
        logoutButton.setOpaque(true); 
        logoutButton.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 153), 2));
        add(logoutButton);

        setVisible(true);
    }

    private JButton createOutlinedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2)); // Blue border
        button.setForeground(new Color(0, 102, 204)); 
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == withdrawButton) {
            dispose();
            new Withdraw(accountId);
        } else if (e.getSource() == transferButton) {
            dispose();
            new Transfer(accountId);
        } else if (e.getSource() == depositButton) {
            dispose();
            new Deposit(accountId);
        } else if (e.getSource() == accountInfoButton) {
            dispose();
            new Check_Information(accountId);
        } else if (e.getSource() == balanceButton) {
            dispose();
            new CheckAccountBalanceandDebt(accountId);
        } else if (e.getSource() == debtButton) {
            dispose();
            new Debt(accountId);
        } else if (e.getSource() == CheckAccountBalanceandDebtButton) {
            dispose();
            new CheckAccountBalanceandDebt(accountId);
        } else if (e.getSource() == TransferHistoryButton) {
            dispose();
            new TransferHistory(accountId);
        } else if (e.getSource() == logoutButton) {
            dispose();
            new Login(); 
        }
    }

    public static void main(String[] args) {
        new MainInterface(8);
    }
}
