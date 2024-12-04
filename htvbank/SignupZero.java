package htvbank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignupZero extends JFrame implements ActionListener {
    JTextField accountNameTextField;
    JPasswordField passTextField, re_passTextField;
    JButton submit, clear, backButton;

    SignupZero() {
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setTitle("User's Informations");
        setSize(850, 500);
        setLocation(350, 140);

        JLabel titleCreate = new JLabel("Create new account");
        titleCreate.setFont(new Font("Raleway", Font.BOLD, 38));
        titleCreate.setBounds(250, 20, 600, 40);
        titleCreate.setForeground(new Color(0, 51, 153));
        add(titleCreate);

        JLabel titleQuick = new JLabel("Quick and easy.");
        titleQuick.setFont(new Font("Raleway", Font.HANGING_BASELINE, 20));
        titleQuick.setBounds(350, 60, 600, 40);
        titleQuick.setForeground(new Color(0, 51, 153));
        add(titleQuick);

        JLabel accountName = new JLabel("Account Name: ");
        accountName.setFont(new Font("Arial", Font.BOLD, 20));
        accountName.setBounds(80, 150, 200, 30);
        accountName.setForeground(new Color(0, 51, 153));
        add(accountName);

        accountNameTextField = new JTextField();
        accountNameTextField.setFont(new Font("Arial", Font.BOLD, 18));
        accountNameTextField.setBounds(270, 150, 450, 40);
        add(accountNameTextField);

        JLabel password = new JLabel("Password: ");
        password.setFont(new Font("Arial", Font.BOLD, 20));
        password.setBounds(80, 220, 250, 30);
        password.setForeground(new Color(0, 51, 153));
        add(password);

        passTextField = new JPasswordField();
        passTextField.setFont(new Font("Arial", Font.BOLD, 18));
        passTextField.setBounds(270, 220, 450, 40);
        add(passTextField);

        JLabel rePassword = new JLabel("Retype Password: ");
        rePassword.setFont(new Font("Raleway", Font.BOLD, 20));
        rePassword.setBounds(80, 290, 250, 30);
        rePassword.setForeground(new Color(0, 51, 153));
        add(rePassword);

        re_passTextField = new JPasswordField();
        re_passTextField.setFont(new Font("Arial", Font.BOLD, 18));
        re_passTextField.setBounds(270, 290, 450, 40);
        add(re_passTextField);

        submit = new JButton("Submit");
        submit.setFont(new Font("Raleway", Font.BOLD, 18));
        submit.setBounds(270, 350, 140, 40);
        submit.setBackground(new Color(0, 51, 153));
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        add(submit);

        clear = new JButton("Clear");
        clear.setFont(new Font("Raleway", Font.BOLD, 18));
        clear.setBounds(425, 350, 140, 40);
        clear.setBackground(new Color(0, 51, 153));
        clear.setForeground(Color.WHITE);
        clear.addActionListener(this);
        add(clear);
        
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setBackground(new Color(0, 51, 153));
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(580, 350, 140, 40);
        backButton.addActionListener(this);
        add(backButton);
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == clear) {
            accountNameTextField.setText("");
            passTextField.setText("");
            re_passTextField.setText("");
        }
        if (ae.getSource() == submit) {
            String accName = accountNameTextField.getText();
            String password = new String(passTextField.getPassword());
            String re_password = new String(re_passTextField.getPassword());
            Connect c = new Connect();
            Boolean check_acc = false;
            try {
                String query1 = "SELECT account_name FROM accounts WHERE account_name = '" + accName + "'";
                ResultSet rs1 = c.s.executeQuery(query1);
                if (rs1.next()) {
                    check_acc=true;
                }
                if (accName.equals("")) {
                    JOptionPane.showMessageDialog(null, "Account name is required", "Error!!!", JOptionPane.WARNING_MESSAGE);
                } else if (check_acc) {
                    JOptionPane.showMessageDialog(null, "The account name already exists.", "Error!!!", JOptionPane.WARNING_MESSAGE);
                } else if (!password.equals(re_password)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match", "Error!!!", JOptionPane.WARNING_MESSAGE);
                } else {

                    String querry = "insert into accounts (account_name, account_passwd, account_balance) values ('"+accName+"', '" + password+"', 0);";
                    c.s.execute(querry);
                    String getAccountIdQuery = "SELECT MAX(account_id) AS account_id FROM accounts;";
                    ResultSet rs = c.s.executeQuery(getAccountIdQuery);
                    if (rs.next()) {
                        int account_id = rs.getInt("account_id");
                        setVisible(false);
                        new SignupOne(account_id).setVisible(true);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
        if (ae.getSource() == backButton){
            dispose();
            new Login();
        }
    }

    public static void main(String[] args) {
        new SignupZero();
    }
}






