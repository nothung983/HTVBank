package htvbank;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TransferHistory extends JFrame implements ActionListener {
    JButton Back, CheckFilter, FindButton;
    JTable table;
    JTextField findField;
    JComboBox<String> filterComboBox;
    int account_id2;

    TransferHistory(int account_id) {
        account_id2 = account_id;

        setTitle("Transfer History");
        setLayout(null);
        setSize(1080, 720);

        ImageIcon i1 = new ImageIcon(Login.class.getResource("/images/logo2.png"));
        Image i2 = i1.getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(430, 40, 200, 50);
        add(label);

        JLabel title = new JLabel("Your Transfer History");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(50, 120, 300, 30);
        add(title);

        
        table = new JTable();
        table.setBounds(50, 170, 980, 400);
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(0, 51, 153));
        table.getTableHeader().setForeground(Color.WHITE);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 170, 960, 400);
        add(sp);

        // Find section
        JLabel findLabel = new JLabel("Search by:");
        findLabel.setFont(new Font("Arial", Font.BOLD, 16));
        findLabel.setBounds(50, 600, 100, 30);
        add(findLabel);

        filterComboBox = new JComboBox<>(new String[] { 
            "Transaction ID", 
            "Transaction Type", 
            "Sender Account", 
            "Receiver Account", 
            "Transaction Time" 
        });
        filterComboBox.setBounds(140, 600, 150, 30);
        add(filterComboBox);

        findField = new JTextField();
        findField.setBounds(300, 600, 200, 30);
        add(findField);

        FindButton = new JButton("Find");
        FindButton.setFont(new Font("Arial", Font.BOLD, 16));
        FindButton.setBackground(new Color(0, 51, 153));
        FindButton.setForeground(Color.WHITE);
        FindButton.setBounds(520, 600, 100, 30);
        FindButton.addActionListener(this);
        add(FindButton);

       
        CheckFilter = new JButton("Check Income per Month");
        CheckFilter.setFont(new Font("Arial", Font.BOLD, 16));
        CheckFilter.setBackground(new Color(0, 51, 153));
        CheckFilter.setForeground(Color.WHITE);
        CheckFilter.setBounds(720, 120, 230, 30);
        CheckFilter.addActionListener(this);
        add(CheckFilter);

        
        Back = new JButton("Back");
        Back.setFont(new Font("Arial", Font.BOLD, 20));
        Back.setBackground(new Color(0, 51, 153));
        Back.setForeground(Color.WHITE);
        Back.setBounds(870, 600, 120, 40);
        Back.addActionListener(this);
        add(Back);


        displayTransferHistory();

        setVisible(true);
        setLocation(250, 50);
    }

    public void displayTransferHistory() {
        try {
            Connect c = new Connect();
            String query = "CALL GET_TRANS_BY_USER(" + account_id2 + ");";
            ResultSet rs = c.s.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel(
                new String[] { "Transaction ID", "Transaction Type", "Sender Account", "Receiver Account", "Transaction Time", "Amount" },
                0
            );

            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("transaction_ID"),
                    rs.getString("transaction_type"),
                    rs.getString("sender_account_name"),
                    rs.getString("receiver_account_name"),
                    rs.getString("transaction_time"),
                    rs.getInt("transaction_amount")
                });
            }
            table.setModel(model);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading transaction history.");
        }
    }

    public void searchTransaction(String filter, String value) {
        try {
            Connect c = new Connect();
            String query = "CALL GET_TRANS_BY_USER(" + account_id2 + ");";
            ResultSet rs = c.s.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel(
                new String[] { "Transaction ID", "Transaction Type", "Sender Account", "Receiver Account", "Transaction Time", "Transaction Amount" },
                0
            );

            while (rs.next()) {
                String transactionId = String.valueOf(rs.getInt("transaction_ID"));
                String transactionType = rs.getString("transaction_type");
                String senderAccount = rs.getString("sender_account_name");
                String receiverAccount = rs.getString("receiver_account_name");
                String transactionTime = rs.getString("transaction_time");

                boolean matches = false;

                switch (filter) {
                    case "Transaction ID":
                        matches = transactionId.contains(value);
                        break;
                    case "Transaction Type":
                        matches = transactionType.toLowerCase().contains(value.toLowerCase());
                        break;
                    case "Sender Account":
                        matches = senderAccount.toLowerCase().contains(value.toLowerCase());
                        break;
                    case "Receiver Account":
                        matches = receiverAccount.toLowerCase().contains(value.toLowerCase());
                        break;
                    case "Transaction Time":
                        matches = transactionTime.toLowerCase().contains(value.toLowerCase());
                        break;
                }

                if (matches) {
                    model.addRow(new Object[] {
                        rs.getInt("transaction_ID"),
                        rs.getString("transaction_type"),
                        rs.getString("sender_account_name"),
                        rs.getString("receiver_account_name"),
                        rs.getString("transaction_time"),
                        rs.getInt("transaction_amount")
                    });
                }
            }
            table.setModel(model);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error performing search.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == Back) {
            dispose();
            new MainInterface(account_id2);
        } else if (ae.getSource() == CheckFilter) {
            new CheckIncomeExpense(account_id2);
        } else if (ae.getSource() == FindButton) {
            String filter = (String) filterComboBox.getSelectedItem();
            String value = findField.getText();
            searchTransaction(filter, value);
        }
    }

    public static void main(String[] args) {
        new TransferHistory(5);
    }
}
