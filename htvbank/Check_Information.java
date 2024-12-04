package htvbank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Check_Information extends JFrame implements ActionListener {
    Connect c = new Connect();
    JButton updateInfoButton, backButton, changepassButton;
    int accountId;
    String r_name, r_dob, r_gender, r_phoneNum, r_address, r_email; 

    Check_Information(int formno) {
        accountId = formno;
        setLayout(null);
        setSize(850, 550);
        setLocation(350, 120);
        
        getContentPane().setBackground(Color.WHITE);
        setTitle("User's Information");
        
        JLabel user_name = new JLabel("User's Information");
        user_name.setFont(new Font("Arial", Font.BOLD, 20));
        user_name.setBounds(325, 50, 200, 30);
        user_name.setForeground(new Color(0, 51, 153));
        add(user_name);

        
        r_name = "";
        r_dob = "";
        r_gender = "";
        r_phoneNum = "";
        r_address = "";
        r_email = "";

        
        try {
            String query = "CALL CheckCustomerInfo(" + formno + ");";
            ResultSet rs = c.s.executeQuery(query);
            
            if (rs.next()) {
                r_name = rs.getString("customer_name");
                r_dob = rs.getString("customer_DOB");
                r_gender = rs.getString("customer_gender");
                r_phoneNum = rs.getString("customer_phone_number");
                r_address = rs.getString("customer_address");
                r_email = rs.getString("customer_email");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        
        JLabel nameLabel = new JLabel("Full name: ");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setBounds(100, 110, 200, 30);
        add(nameLabel);

        JLabel name = new JLabel(r_name);
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setBounds(300, 110, 400, 30);
        add(name);

        JLabel dobLabel = new JLabel("Date of birth: ");
        dobLabel.setFont(new Font("Arial", Font.BOLD, 20));
        dobLabel.setBounds(100, 150, 200, 30);
        add(dobLabel);

        JLabel dob = new JLabel(r_dob);
        dob.setFont(new Font("Arial", Font.PLAIN, 20));
        dob.setBounds(300, 150, 400, 30);
        add(dob);

        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setFont(new Font("Arial", Font.BOLD, 20));
        genderLabel.setBounds(100, 190, 200, 30);
        add(genderLabel);

        JLabel gender = new JLabel(r_gender);
        gender.setFont(new Font("Arial", Font.PLAIN, 20));
        gender.setBounds(300, 190, 400, 30);
        add(gender);

        JLabel phoneLabel = new JLabel("Phone number: ");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 20));
        phoneLabel.setBounds(100, 230, 200, 30);
        add(phoneLabel);

        JLabel phoneNum = new JLabel(r_phoneNum);
        phoneNum.setFont(new Font("Arial", Font.PLAIN, 20));
        phoneNum.setBounds(300, 230, 400, 30);
        add(phoneNum);

        JLabel addressLabel = new JLabel("Address: ");
        addressLabel.setFont(new Font("Arial", Font.BOLD, 20));
        addressLabel.setBounds(100, 270, 200, 30);
        add(addressLabel);

        JLabel address = new JLabel(r_address);
        address.setFont(new Font("Arial", Font.PLAIN, 20));
        address.setBounds(300, 270, 400, 30);
        add(address);

        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 20));
        emailLabel.setBounds(100, 310, 200, 30);
        add(emailLabel);

        JLabel email = new JLabel(r_email);
        email.setFont(new Font("Arial", Font.PLAIN, 20));
        email.setBounds(300, 310, 400, 30);
        add(email);

        updateInfoButton = new JButton("Update Information");
        updateInfoButton.setFont(new Font("Arial", Font.BOLD, 16));
        updateInfoButton.setBackground(new Color(0, 51, 153));
        updateInfoButton.setForeground(Color.WHITE);
        updateInfoButton.setBounds(320, 400, 200, 40);
        updateInfoButton.addActionListener(this);
        add(updateInfoButton);
        
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(0, 51, 153));
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(60, 400, 120, 40);
        backButton.addActionListener(this);
        add(backButton);
        
        changepassButton = new JButton("Change Password");
        changepassButton.setFont(new Font("Arial", Font.BOLD, 16));
        changepassButton.setBackground(new Color(0, 51, 153));
        changepassButton.setForeground(Color.WHITE);
        changepassButton.setBounds(580, 400, 200, 40);
        changepassButton.addActionListener(this);
        add(changepassButton);

        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateInfoButton) {
            dispose();
            new UpdateInformation(accountId, r_name, r_dob, r_gender, r_phoneNum, r_address, r_email);
        } else if(e.getSource() == backButton){
            dispose();
            new MainInterface(accountId);
        } else if (e.getSource() == changepassButton){
            dispose();
            new ChangePassword(accountId);
        }
    }

    public static void main(String[] args) {
        new Check_Information(8); 
    }
}
