
package htvbank;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ChangePassword extends JFrame implements ActionListener{
    JPasswordField passTextField, re_passTextField;
    JButton submit, backButton;
    int account_id2;
    ChangePassword(int account_id) {
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setTitle("Change Password");
        setSize(800, 400);
        setLocation(350, 200);
        account_id2 = account_id;
        
        JLabel user_name = new JLabel("Change Password");
        user_name.setFont(new Font("Arial",Font.BOLD,20));
        user_name.setBounds(170,50,200,30);
        user_name.setForeground(new Color(0, 51, 153));
        add(user_name);
        
        JLabel text = new JLabel("Please enter new password.");
        text.setFont(new Font("Osward",Font.ITALIC, 15));
        text.setBounds(170, 70, 500, 40);
        text.setForeground(Color.BLACK);
        add(text);
        
        JLabel password = new JLabel("Password: ");
        password.setFont(new Font("Arial", Font.BOLD, 20));
        password.setBounds(170, 110, 200, 30);
        password.setForeground(new Color(0, 51, 153));
        add(password);
        
        passTextField = new JPasswordField();
        passTextField.setFont(new Font("Arial", Font.BOLD, 18));
        passTextField.setBounds(170, 145, 450, 30);
        add(passTextField);
        
        JLabel re_password = new JLabel("Retype Password: ");
        re_password.setFont(new Font("Arial", Font.BOLD, 20));
        re_password.setBounds(170, 180, 250, 30);
        re_password.setForeground(new Color(0, 51, 153));
        add(re_password);
        
        re_passTextField = new JPasswordField();
        re_passTextField.setFont(new Font("Arial", Font.BOLD, 18));
        re_passTextField.setBounds(170, 215, 450, 30);
        add(re_passTextField);
        
        submit = new JButton("Submit");
        submit.setFont(new Font("Raleway", Font.BOLD, 18));
        submit.setBounds(500, 250, 120, 30);
        submit.setBackground(new Color(0, 51, 153));
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        add(submit);
        
        backButton = new JButton("Back");
        backButton.setFont(new Font("Raleway", Font.BOLD, 18));
        backButton.setBackground(new Color(0, 51, 153));
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(370, 250, 120, 30);
        backButton.addActionListener(this);
        add(backButton);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        String password = new String(passTextField.getPassword());
        String re_password = new String(re_passTextField.getPassword());
        if (ae.getSource() == submit) {
            try {
                if (password.equals("") || re_password.equals("")) {
                    JOptionPane.showMessageDialog(null, "You need to fill in all the blanks.", "Change password", JOptionPane.WARNING_MESSAGE);
                }
                if (!password.equalsIgnoreCase(re_password)) {
                     JOptionPane.showMessageDialog(null, "Password do not match", "Error!!", JOptionPane.WARNING_MESSAGE);
                } else {
                    Connect c = new Connect();
                    String query = "CALL UPDATE_PASS(" + account_id2 + ", '" + password + "');";
                    c.s.executeQuery(query);
                    JOptionPane.showMessageDialog(null, "Change password successful", "Change password", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new Check_Information(account_id2);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if(ae.getSource() == backButton){
            dispose();
            new Check_Information(account_id2);
        }
    }
    public static void main(String[] args) {
        new ChangePassword(8);
    }
}


