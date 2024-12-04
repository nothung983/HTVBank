package htvbank;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ShowCustomersDebt extends JFrame implements ActionListener {
    JTable table;
    JButton back;
    int accountId;
    
    ShowCustomersDebt(int a_id) {
        accountId = a_id;
        
        setTitle("Customers' Debt Details");
        setLayout(null);
        setSize(1080, 700);
        getContentPane().setBackground(Color.WHITE);

        ImageIcon i1 = new ImageIcon(ShowCustomersDebt.class.getResource("/images/logo2.png"));
        Image i2 = i1.getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(430, 40, 200, 50);
        add(label);

        JLabel title = new JLabel("Customers' Debt Details");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(50, 100, 300, 30);
        title.setForeground(new Color(0, 51, 153));
        add(title);
        
        table = new JTable();
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(0, 51, 153));
        table.getTableHeader().setForeground(Color.WHITE);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 130, 960, 400);
        add(sp);
        
        back = new JButton("BACK");
        back.setFont(new Font("Arial", Font.BOLD, 20));
        back.setBackground(new Color(0, 51, 153));
        back.setForeground(Color.WHITE);
        back.setBounds(50, 560, 140, 40);
        back.addActionListener(this);
        add(back);
        
        display_Customers_Debt();

        setLocation(250, 60);
        setVisible(true);
    }

    public void display_Customers_Debt() {
        Connect c = new Connect();
        try {
            String query = "CALL GET_CUS_DEBT();";
            ResultSet rs = c.s.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel(
                new String[] { "Debt ID", "Name", "Phone Number", "Debt Amount", "Debt Date", "Debt Rate", "Debt Interest", "Monthly Pay" },
                0
            );


            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("debt_id"),
                    rs.getString("customer_name"),
                    rs.getString("customer_phone_number"),
                    rs.getString("debt_amount"),
                    rs.getString("debt_date"),
                    rs.getString("debt_rate"),
                    rs.getString("debt_interest"),
                    rs.getString("monthly_pay")
                });
            }

            table.setModel(model);

        } catch (SQLException se) {
            se.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data: " + se.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
      public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            dispose();
            new AdminMainInterface(accountId);
        }
    }
    public static void main(String[] args) {
        new ShowCustomersDebt(8);
    }
}


