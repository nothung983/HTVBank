package htvbank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateInformation extends JFrame implements ActionListener {
    JTextField nameField, dobField, genderField, phoneField, addressField, emailField;
    JButton updateButton, backButton;
    int formno; 

    public UpdateInformation(int formno, String name, String dob, String gender, String phone, String address, String email) {
        this.formno = formno;

        setLayout(null);
        setSize(850, 600);
        setLocation(350, 100);
        getContentPane().setBackground(Color.WHITE);
        setTitle("Update Information");

        JLabel header = new JLabel("Update Information");
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setBounds(300, 30, 250, 30);
        header.setForeground(new Color(0, 51, 153));
        add(header);

        JLabel nameLabel = new JLabel("Full name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setBounds(100, 100, 200, 30);
        add(nameLabel);
        
        nameField = new JTextField(name); 
        nameField.setBounds(300, 100, 400, 30);
        add(nameField);

        JLabel dobLabel = new JLabel("Date of Birth (YYYY-MM-DD):");
        dobLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dobLabel.setBounds(100, 150, 200, 30);
        add(dobLabel);
        
        dobField = new JTextField(dob); 
        dobField.setBounds(300, 150, 400, 30);
        add(dobField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Arial", Font.BOLD, 16));
        genderLabel.setBounds(100, 200, 200, 30);
        add(genderLabel);
        
        genderField = new JTextField(gender);
        genderField.setBounds(300, 200, 400, 30);
        add(genderField);

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 16));
        phoneLabel.setBounds(100, 250, 200, 30);
        add(phoneLabel);
        
        phoneField = new JTextField(phone); 
        phoneField.setBounds(300, 250, 400, 30);
        add(phoneField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font("Arial", Font.BOLD, 16));
        addressLabel.setBounds(100, 300, 200, 30);
        add(addressLabel);
        
        addressField = new JTextField(address); 
        addressField.setBounds(300, 300, 400, 30);
        add(addressField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        emailLabel.setBounds(100, 350, 200, 30);
        add(emailLabel);
        
        emailField = new JTextField(email); 
        emailField.setBounds(300, 350, 400, 30);
        add(emailField);

        updateButton = new JButton("Update Information");
        updateButton.setFont(new Font("Arial", Font.BOLD, 16));
        updateButton.setBackground(new Color(0, 51, 153));
        updateButton.setForeground(Color.WHITE);
        updateButton.setBounds(320, 450, 200, 40);
        updateButton.addActionListener(this);
        add(updateButton);
        
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(0, 51, 153));
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(60, 450, 120, 40);
        backButton.addActionListener(this);
        add(backButton);
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            String name = nameField.getText();
            String dob = dobField.getText(); 
            String gender = genderField.getText();
            String phoneNum = phoneField.getText();
            String address = addressField.getText();
            String email = emailField.getText();

            try {
                
                Connect c = new Connect();
                String query = "{CALL UpdateCustomerInfo('" + formno + "', '" 
                                + name + "', '" + dob + "', '" + gender + "', '" 
                                + phoneNum + "', '" + address + "', '" + email + "')}";

                c.s.executeQuery(query);  
                JOptionPane.showMessageDialog(null, "Information updated successfully.");
                dispose();
                new Check_Information(formno);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating information.");
            }
        } else if (e.getSource() == backButton){
            dispose();
            new Check_Information(formno);
        }
    }

}
