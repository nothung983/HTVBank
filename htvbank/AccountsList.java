package htvbank;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AccountsList extends JFrame implements ActionListener {
    JTable table;
    JButton backButton;
    int account_id2;

    AccountsList(int accountID) {
        account_id2 = accountID;

        setTitle("User Accounts Information");
        setLayout(null);
        setSize(1080, 720);

     
        ImageIcon i1 = new ImageIcon(Login.class.getResource("/images/logo2.png"));
        Image i2 = i1.getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(430, 40, 200, 50);
        add(label);

       
        JLabel titleLabel = new JLabel("User Accounts Information");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(350, 100, 400, 40);
        titleLabel.setForeground(new Color(0, 51, 153));
        add(titleLabel);

        
        table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(0, 51, 153));
        table.getTableHeader().setForeground(Color.WHITE);
        sp.setBounds(50, 160, 980, 400);
        add(sp);

        
        backButton = new JButton("Back");
        backButton.setBounds(480, 590, 120, 30);
        backButton.setBackground(new Color(0, 51, 153));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 15));
        backButton.addActionListener(this);
        add(backButton);

        displayAccounts();
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
        setLocation(250, 50);
    }

   
    public void displayAccounts() {
    try {
        Connect c = new Connect();
        String query = "CALL GET_ALL_ACCOUNTS();"; 
        ResultSet rs = c.s.executeQuery(query);

        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Account ID");
        model.addColumn("Account Name");
        model.addColumn("Balance");
        model.addColumn("Is Admin");
        model.addColumn("Customer ID");
        model.addColumn("Date Of Birth");
        model.addColumn("Address");
        model.addColumn("Gender");
        model.addColumn("Phone Number");
        model.addColumn("Email");

       
        while (rs.next()) {
            model.addRow(new Object[] {
                rs.getLong("account_id"), 
                rs.getString("account_name"),
                rs.getDouble("account_balance"),
                rs.getInt("is_admin") == 1 ? "Yes" : "No",
                rs.getLong("customer_id"), 
                rs.getDate("customer_dob"),
                rs.getString("customer_address"),
                rs.getString("customer_gender"),
                rs.getString("customer_phone_number"),
                rs.getString("customer_email")
            });
        }
        table.setModel(model);
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error fetching account data.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backButton) {
           dispose();
           new AdminMainInterface(account_id2);
        }
    }

    public static void main(String[] args) {
        new AccountsList(0);
    }
}


