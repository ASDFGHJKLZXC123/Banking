package guis;

import db_objs.MyJDBC;
import db_objs.Transaction;
import db_objs.User;
import db_objs.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.*;
import java.util.ArrayList;


/*
    displays a custom dialog for UserGUI
 */
public class UserDialog extends JDialog implements ActionListener {
    private User user;
    private UserGUI userGUI;
    private JLabel balanceLabel, enterAmountLabel, enterUserLabel;
    private JTextField enterAmountField, enterUserField;
    private JButton actionButton;
    private JPanel pastTransactionPanel;
    private ArrayList<Transaction> pastTransactions;


    public UserDialog(UserGUI userGUI, User user) {
        // set the size
        setSize(400, 400);

        // add focus to the dialog
        setModal(true);

        // loads in the center of the user GUI
        setLocationRelativeTo(userGUI);

        // when user closes dialog, it releases its resources that being used.
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // prevents dialog from being resized
        setResizable(false);

        // allows us to manually specify the size and position and component
        setLayout(null);

        // set the reference GUI to the local GUI
        this.userGUI = userGUI;

        // set the local user reference
        this.user = user;
    }

    public void addCurrentBalanceAndAmount() {
        // balance label
        balanceLabel = new JLabel("Balance: $" + user.getCurrentBalance());
        balanceLabel.setBounds(0, 10, getWidth() - 20, 20);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        // enter amount label
        enterAmountLabel = new JLabel("Enter Amount:");
        enterAmountLabel.setBounds(0, 50 ,getWidth() - 20 , 20);
        enterAmountLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountLabel);

        // enter amount field
        enterAmountField = new JTextField();
        enterAmountField .setBounds(15, 80, getWidth() - 50, 40);
        enterAmountField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterAmountField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountField);

    }

    public void addActionButton(String actionButtonType){
        actionButton = new JButton(actionButtonType);
        actionButton.setBounds(15, 300, getWidth() - 50, 40);
        actionButton.setFont(new Font("Dialog", Font.BOLD, 20));
        actionButton.setHorizontalAlignment(SwingConstants.CENTER);
        actionButton.addActionListener(this);
        add(actionButton);
    }

    public void addUserField(){
        enterUserLabel = new JLabel("Enter User:");
        enterUserLabel.setBounds(0, 160, getWidth() - 20, 20);
        enterUserLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserLabel);

        enterUserField = new JTextField();
        enterUserField.setBounds(15, 190, getWidth() - 50, 40);
        enterUserField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterUserField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserField);
    }


    private void handleTransaction(String transactionType, float amountVal){
        Transaction transaction;

        if(transactionType.equalsIgnoreCase("Deposit")){
            user.setCurrentBalance(user.getCurrentBalance().add(new BigDecimal(amountVal)));

            transaction = new Transaction(user.getID(), transactionType, new BigDecimal(amountVal), null);
        }else{
            user.setCurrentBalance(user.getCurrentBalance().subtract(new BigDecimal(amountVal)));

            transaction = new Transaction(user.getID(), transactionType, new BigDecimal(-amountVal), null);
        }

        if(MyJDBC.addTransactionToDatabase(transaction) && MyJDBC.updateCurrentBalance(user)){
            JOptionPane.showMessageDialog(this, transactionType + " Successfully!");
            resetFieldAndUpdateCurrentBalance();
        }else{
            JOptionPane.showMessageDialog(this, transactionType + " Failed!");
        }
    }

    private void resetFieldAndUpdateCurrentBalance(){
        enterAmountField.setText("");
        if(enterUserField != null){
            enterUserField.setText("");
        }

        balanceLabel.setText("Balance: $" + user.getCurrentBalance());

        userGUI.getCurrentBalanceField().setText("$" + user.getCurrentBalance());
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();

        // get amount val
        float amountVal = Float.parseFloat(enterAmountField.getText());

        // pressed deposit
        if(buttonPressed.equalsIgnoreCase("Deposit")){
            // we want to handle the deposit transaction
            handleTransaction(buttonPressed, amountVal);
        }else{
            int result = user.getCurrentBalance().compareTo(BigDecimal.valueOf(amountVal));

            // result < 0 means current balance less than amountVal
            // result = 0 means current balance equals amountVal
            // result > 0 means current balance more than amountVal
            if(result < 0){
                JOptionPane.showMessageDialog(this, "Error: Input value is more than current balance");
                return;
            }

            if(buttonPressed.equalsIgnoreCase("Withdraw")) {
                handleTransaction(buttonPressed, amountVal);
            }else{
                // transfer
                String transferredUser = enterUserField.getText();

                // handle transfer
                handleTransfer(user, transferredUser, amountVal);
            }
        }
    }

    private void handleTransfer(User user, String transferUser, float amount){
        if(MyJDBC.transfer(user, transferUser, amount)){
            JOptionPane.showMessageDialog(this, "Transfer Success!");
            resetFieldAndUpdateCurrentBalance();
        }else{
            JOptionPane.showMessageDialog(this, "Transfer Failed!");
        }
    }

    public void addPastTransactionComponents(){
        pastTransactionPanel = new JPanel();

        pastTransactionPanel.setLayout(new BoxLayout(pastTransactionPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(pastTransactionPanel);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 20, getWidth() - 15, getHeight() - 80);

        pastTransactions = MyJDBC.getPastTransaction(user);

        for(int i=0; i < pastTransactions.size(); i++){
            Transaction pastTransaction = pastTransactions.get(i);

            JPanel pastTransactionContainer = new JPanel();
            pastTransactionContainer.setLayout(new BorderLayout());

            // Add transaction type label
            JLabel transactionTypeLabel = new JLabel(pastTransaction.getTransactionType());
            transactionTypeLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            // Add transaction amount label
            JLabel transactionAmountLabel = new JLabel(String.valueOf(pastTransaction.getTransactionAmount()));
            transactionAmountLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            // Add transaction date label
            JLabel transactionDateLabel = new JLabel(String.valueOf(pastTransaction.getTransactionDate()));
            transactionDateLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            pastTransactionContainer.add(transactionTypeLabel, BorderLayout.WEST);
            pastTransactionContainer.add(transactionAmountLabel, BorderLayout. EAST);
            pastTransactionContainer.add(transactionDateLabel, BorderLayout.SOUTH);

            pastTransactionContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            pastTransactionPanel.add(pastTransactionContainer);
        }

        add(scrollPane, BorderLayout.EAST);
    }


}