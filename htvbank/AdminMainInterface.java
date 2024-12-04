package htvbank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMainInterface extends JFrame implements ActionListener {

    private JButton TransactionButton, AccountsButton, UserDebtButton, logoutButton;
    private int accountId;

    AdminMainInterface(int a_id) {
        this.accountId = a_id;

       
        setTitle("HTV BANK ADMIN MANAGEMENT SYSTEM");
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
      
        
        
        ImageIcon i1 = new ImageIcon(Login.class.getResource("/images/logo2.png"));
        Image i2 = i1.getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT);
        JLabel label = new JLabel(new ImageIcon(i2));
        label.setBounds(350, 60, 200, 50); // Adjusted for center alignment
        add(label);


        JLabel titleText = new JLabel("Choose Your Option", SwingConstants.CENTER);
        titleText.setFont(new Font("Osward", Font.BOLD, 32));
        titleText.setBounds(250, 150, 400, 40); // Centered horizontally
        titleText.setForeground(new Color(0, 51, 153));
        add(titleText);
        
        String getName = "";
        try {
            Connect c = new Connect();
            String query = "SELECT account_name FROM accounts WHERE account_id = " +accountId;
            ResultSet rs = c.s.executeQuery(query);
            
            if(rs.next()){
            getName = rs.getString("account_name");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        JLabel welcomeText = new JLabel("<html>Welcome, " + getName +"<br>Your ID: " + accountId + "</html>");
        welcomeText.setFont(new Font("Osward", Font.BOLD, 16));
        welcomeText.setForeground(new Color(0, 51, 153));
        welcomeText.setBounds(80, 22, 300, 40); 
        add(welcomeText);
        
      
        AccountsButton = createOutlinedButton("Accounts Management");
        AccountsButton.setBounds(312, 250, 275, 50); 
        add(AccountsButton);

        TransactionButton = createOutlinedButton("Transactions Management");
        TransactionButton.setBounds(312, 320, 275, 50); 
        add(TransactionButton);

        UserDebtButton = createOutlinedButton("User Debt Management");
        UserDebtButton.setBounds(312, 390, 275, 50); 
        add(UserDebtButton);
        
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
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        button.setForeground(new Color(0, 102, 204));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == AccountsButton) {
            dispose();
            new AccountsList(accountId);
        } else if (e.getSource() == TransactionButton) {
            dispose();
            new TransAdmin(accountId);
        } else if (e.getSource() == UserDebtButton) {
            dispose();
            new ShowCustomersDebt(accountId);
        } else if (e.getSource() == logoutButton){
            JOptionPane.showMessageDialog(this, "You have logged out.");
            dispose();
            new Login(); 
        }
    }

    public static void main(String[] args) {
        new AdminMainInterface(1);
    }
}
