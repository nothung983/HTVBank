package htvbank;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    
    JButton login, signup, clear;
    JTextField accountNameTextField;
    JPasswordField passTextField;
   
    Login (){
        setTitle("LOGIN");
        getContentPane().setBackground(Color.white);
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(Login.class.getResource("/images/logo2.png"));
        Image i2 = i1.getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(310, 65, 200, 50);
        add(label);
        
        JLabel text = new JLabel("Welcome to ATM");
        text.setFont(new Font("Osward",Font.BOLD, 38));
        text.setBounds(260, 20, 400, 40);
        text.setForeground(new Color(0, 51, 153));
        add(text);
        
        JLabel accName = new JLabel("Account Name: ");
        accName.setFont(new Font("Raleway",Font.BOLD, 25));
        accName.setForeground(new Color(0, 51, 153));
        accName.setBounds(80, 140, 400, 40);
        add(accName);
        
        accountNameTextField = new JTextField();
        accountNameTextField.setBounds(280,145, 280,30);
        accountNameTextField.setFont(new Font("Arial", Font.BOLD, 14));
        add(accountNameTextField);
        
        JLabel password = new JLabel("Password: ");
        password.setFont(new Font("Raleway",Font.BOLD, 25));
        password.setForeground(new Color(0, 51, 153));
        password.setBounds(80, 200, 400, 40);
        add(password);        
        
        passTextField = new JPasswordField();
        passTextField.setBounds(280,205, 280,30);
        add(passTextField);
        
        login = new JButton("SIGN IN");
        login.setBounds(280, 255, 135, 30);
        login.setBackground(new Color(0, 51, 153));
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);
        
        clear = new JButton("CLEAR");
        clear.setBounds(425, 255, 135, 30);
        clear.setBackground(new Color(0, 51, 153));
        clear.setForeground(Color.WHITE);
        clear.addActionListener(this);
        add(clear);
        
        signup = new JButton("SIGN UP");
        signup.setBounds(280, 295, 280, 30);
        signup.setBackground(new Color(0, 51, 153));
        signup.setForeground(Color.WHITE);
        signup.addActionListener(this);
        add(signup);
        
        
        setSize(800,480);
        setVisible(true);
        setLocation(350,200);
    }

    
    public void actionPerformed(ActionEvent e) {
        String accName = accountNameTextField.getText();
        String password = new String(passTextField.getPassword());

        if (e.getSource() == clear ) {
            accountNameTextField.setText("");
            passTextField.setText("");
        }
        else if (e.getSource() == login) {

           try {
            Connect c = new Connect();
            String query1 = "Select account_id, account_name, is_admin from accounts where account_name = '" + accName + "' and account_passwd = '" + password + "';";
            ResultSet rs1 = c.s.executeQuery(query1);

            if (accName.equals("") || password.equals("")) {
                 JOptionPane.showMessageDialog(null, "You need to fill in all the blanks.", "Sign in", JOptionPane.WARNING_MESSAGE);
            }
            else if (rs1.next()) {
                int account_id = rs1.getInt("account_id");
                boolean isAdmin = rs1.getBoolean("is_admin");
                if (isAdmin) {
                    JOptionPane.showMessageDialog(null, "Sign in Admin successful!", "Sign in", JOptionPane.INFORMATION_MESSAGE);
                    new AdminMainInterface(account_id).setVisible(true);
                    setVisible(false);
                }
                else {
                   
                    JOptionPane.showMessageDialog(null, "Sign in successful!", "Sign in", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    new MainInterface(account_id).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Account name or password is not invalid", "Sign in", JOptionPane.WARNING_MESSAGE);
            }
           } catch (SQLException ex) {
                ex.printStackTrace();
           }
            
        }
        else if (e.getSource() == signup) {
            setVisible(false);
            new SignupZero().setVisible(true);
        }
    }
    public static void main(String[] args) {
        new Login();
    }
}



