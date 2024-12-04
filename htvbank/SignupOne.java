package htvbank;

import com.toedter.calendar.JDateChooser;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignupOne extends JFrame implements ActionListener {
    
    JTextField nameTextField, emailTextField, phoneNumberTextField, addressTextField, No_TextField;
    JButton submit, clear, back;
    JRadioButton male, female, otherGen;
    JDateChooser dateChooser;
    ButtonGroup genderGroup;
    int account_id2;
    SignupOne(int account_id) {
        setLayout(null);
        
        setTitle("SIGN UP FORM");
        
        getContentPane().setBackground(Color.WHITE);
        
        account_id2 = account_id;
        
        JLabel formno = new JLabel("APPLICATION FORM");
        formno.setFont(new Font("Raleway", Font.BOLD,38));
        formno.setBounds(235, 20, 600, 40);
        formno.setForeground(new Color(0, 51, 153));
        add(formno);
   
        JLabel personDetails = new JLabel("Personal Details");
        personDetails.setFont(new Font("Arial", Font.BOLD, 22));
        personDetails.setBounds(320, 80, 400, 30);
        personDetails.setForeground(new Color(0, 51, 153));
        add(personDetails);
        
        JLabel name = new JLabel("Name:");
        name.setFont(new Font("Arial", Font.BOLD, 20));
        name.setBounds(100, 140, 100, 30);
        name.setForeground(new Color(0, 51, 153));
        add(name);
        
        nameTextField = new JTextField();
        nameTextField.setFont(new Font("Raleway", Font.BOLD, 14));
        nameTextField.setBounds(300, 140, 400, 30);
        nameTextField.setForeground(new Color(0, 51, 153));
        add(nameTextField);

        JLabel customer_id = new JLabel("Citizen Number:");
        customer_id.setFont(new Font("Arial", Font.BOLD, 20));
        customer_id.setBounds(100, 180, 200, 30);
        customer_id.setForeground(new Color(0, 51, 153));
        add(customer_id); 
        
        No_TextField = new JTextField();
        No_TextField.setFont(new Font("Raleway", Font.BOLD, 14));
        No_TextField.setBounds(300, 180, 400, 30);
        add(No_TextField);
        
        JLabel dob = new JLabel("Date of birth: ");
        dob.setFont(new Font("Arial", Font.BOLD, 20));
        dob.setBounds(100, 220, 200, 30);
        dob.setForeground(new Color(0, 51, 153));
        add(dob);
        
        dateChooser = new JDateChooser();
        dateChooser.setBounds(300, 220, 400, 30);
        dateChooser.setFont(new Font("Raleway", Font.BOLD,14));
        add(dateChooser);
        
        JLabel address = new JLabel("Address: ");
        address.setFont(new Font("Arial", Font.BOLD, 20));
        address.setBounds(100, 260, 100, 30);
        address.setForeground(new Color(0, 51, 153));
        add(address);
        
        addressTextField = new JTextField();
        addressTextField.setFont(new Font("Raleway", Font.BOLD, 14));
        addressTextField.setBounds(300, 260, 400, 30);
        add(addressTextField);
        
        JLabel gender = new JLabel("Gender:");
        gender.setFont(new Font("Arial", Font.BOLD, 20));
        gender.setBounds(100, 300, 100, 30);
        gender.setForeground(new Color(0, 51, 153));
        add(gender);

        male = new JRadioButton("Male");
        male.setBounds(300, 300, 100, 30);
        male.setBackground(Color.WHITE);
        add(male);
        
        female = new JRadioButton("Female");
        female.setBounds(450, 300, 100, 30);
        female.setBackground(Color.WHITE);
        add(female);
        
        otherGen = new JRadioButton("Others");
        otherGen.setBounds(600, 300, 100, 30);
        otherGen.setBackground(Color.WHITE);
        add(otherGen);
        
        genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        genderGroup.add(otherGen);
        
        JLabel phoneNumber = new JLabel("Phone number: ");
        phoneNumber.setFont(new Font("Arial", Font.BOLD, 20));
        phoneNumber.setBounds(100, 340, 200, 30);
        phoneNumber.setForeground(new Color(0, 51, 153));
        add(phoneNumber);
        
        phoneNumberTextField = new JTextField();
        phoneNumberTextField.setFont(new Font("Raleway", Font.BOLD, 14));
        phoneNumberTextField.setBounds(300, 340, 400, 30);
        add(phoneNumberTextField);
        
        JLabel email = new JLabel("Email: ");
        email.setFont(new Font("Arial", Font.BOLD, 20));
        email.setBounds(100, 380, 100, 30);
        email.setForeground(new Color(0, 51, 153));
        add(email);
        
        emailTextField = new JTextField();
        emailTextField.setFont(new Font("Raleway", Font.BOLD, 14));
        emailTextField.setBounds(300, 380, 400, 30);
        add(emailTextField);
        
        submit = new JButton("Submit");
        submit.setBounds(300, 420, 130, 30);
        submit.setFont(new Font("Raleway",Font.BOLD,18));
        submit.setBackground(new Color(0, 51, 153));
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        add(submit);
        
        clear = new JButton("Clear");
        clear.setBounds(440, 420, 130, 30);
        clear.setFont(new Font("Raleway",Font.BOLD,18));
        clear.setBackground(new Color(0, 51, 153));
        clear.setForeground(Color.WHITE);
        clear.addActionListener(this);
        add(clear);
        
        back = new JButton("Back");
        back.setBounds(580, 420, 120, 30);
        back.setFont(new Font("Raleway",Font.BOLD,18));
        back.setBackground(new Color(0, 51, 153));
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);
        
        setSize(850, 550);
        setLocation(350, 100);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed (ActionEvent ae) {
        Connect c = new Connect();
        if (ae.getSource() == clear) {
            nameTextField.setText("");
            dateChooser.setDate(null);
            emailTextField.setText("");
            No_TextField.setText("");
            genderGroup.clearSelection();
            addressTextField.setText("");
            phoneNumberTextField.setText("");
        }
        if (ae.getSource() == back) {
            try {
                
                String query = "DELETE FROM accounts WHERE account_id = '"+account_id2+"';";
                c.s.executeUpdate(query);
                setVisible(false);
            
            } catch (SQLException e){
                e.printStackTrace();
            }

        }
        else if (ae.getSource() == submit) {
            String name = nameTextField.getText();
            String CiNo = No_TextField.getText();
            String gender = null;
            String email = emailTextField.getText();
            String phoneNum = phoneNumberTextField.getText();
            String address = addressTextField.getText();
            String query2 = "Select customer_id from customers where customer_id =  '"+CiNo+"';";
            String query3 = "Select customer_phone_number from customers where customer_phone_number =  '"+phoneNum+"';";
            String query4 = "Select customer_email from customers where customer_email =  '"+email+"';";
            
            if (male.isSelected()) {
                gender = "Male";
            } else if (female.isSelected()){
                gender = "Female";
            } else if (otherGen.isSelected()){
                gender ="Others";
            }
            try {
                Date dob = dateChooser.getDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formatDob = dateFormat.format(dob);
                
                Boolean Check_CiNo = false, Check_phone_num = false, Check_email = false;
                try {
                    ResultSet rs = c.s.executeQuery(query2);
                    if (rs.next()) {
                    Check_CiNo= true;
                }
                } catch (SQLException se){
                    se.printStackTrace();
                }
                try {
                    ResultSet rs1 = c.s.executeQuery(query3);
                    if (rs1.next()) {
                    Check_phone_num = true;
                }
                } catch (SQLException se){
                    se.printStackTrace();
                }        
                try {
                    ResultSet rs2 = c.s.executeQuery(query4);
                    if (rs2.next()) {
                    Check_email = true;
                }
                } catch (SQLException se){
                    se.printStackTrace();
                }  
                try {
                    ResultSet rs = c.s.executeQuery(query2);
                    if (rs.next()) {
                    Check_CiNo= true;
                }
                } catch (SQLException se){
                    se.printStackTrace();
                }

                if(name.equals("") || CiNo.equals("") || gender == null || email.equals("") || address.equals("") ) {
                    JOptionPane.showMessageDialog(null, "You need to fill in all the blanks.", "Error!!!", JOptionPane.WARNING_MESSAGE);
                } 
                else if (Check_CiNo) {
                    JOptionPane.showMessageDialog(null, "The citizen number is exist", "Error!!!", JOptionPane.WARNING_MESSAGE);
                }
                else if (CiNo.length() != 12 ) {
                    JOptionPane.showMessageDialog(null, "The citizen number must have exactly 12 characters.", "Error!!!", JOptionPane.WARNING_MESSAGE);
                }
                else if (phoneNum.length() != 10) {
                     JOptionPane.showMessageDialog(null, "The phone number must have exactly 10 numbers.", "Error!!!", JOptionPane.WARNING_MESSAGE);
                }
                else if (Check_phone_num) {
                      JOptionPane.showMessageDialog(null, "The phone number is exist", "Error!!!", JOptionPane.WARNING_MESSAGE);
                }
                else if (Check_email) {
                        JOptionPane.showMessageDialog(null, "The email is exist", "Error!!!", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    String query = "CALL ADD_NEW_CUS ('" + account_id2 + "', '" + name + "', '" + CiNo + "', '" + formatDob + "', '" + address  + "', '" + gender + "', '" + phoneNum +"', '"+ email +"');";
                    c.s.execute(query);
                    JOptionPane.showMessageDialog(null, "Sign up successful!", "Sign up", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    new Login().setVisible(true);
                }
            }
            catch (Exception e) {
                  System.out.print(e);
                  JOptionPane.showMessageDialog(null, "You need to fill in all the blanks.", "Error!!!", JOptionPane.WARNING_MESSAGE);
            }
        }
       
    }
public static void main(String[] args) {
        new SignupOne(0);
    }
}


