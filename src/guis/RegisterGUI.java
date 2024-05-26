package guis;

import db_objs.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterGUI extends BaseFrame{
    public RegisterGUI(){
        super("Banking Register");
    }
    @Override
    protected void addGuiComponents() {
        // create banking label
        JLabel bankingLabel = new JLabel("Banking Register");

        // set the location and the size of the gui component
        bankingLabel.setBounds(0, 20, super.getWidth(), 40);

        // change the font style
        bankingLabel.setFont(new Font("Dialog", Font.BOLD, 32));

        // center text in JLabel
        bankingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // add to gui
        add(bankingLabel);

        //user name Label
        JLabel usernameLabel = new JLabel("Username:");

        // set the user's JLabel location and size
        usernameLabel.setBounds(20, 120, getWidth() - 30, 24);

        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameLabel);

        //create JTextField for username input
        JTextField usernameField = new JTextField();
        usernameField.setBounds(20, 160, getWidth() - 50, 40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(usernameField);

        //create JLabel for password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 220, getWidth(), 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        //create JPasswordField for password input
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 260, getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordField);

        //create JLabel for re-type password
        JLabel repasswordLabel = new JLabel("Re-type Password:");
        repasswordLabel.setBounds(20, 320, getWidth(), 24);
        repasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(repasswordLabel);

        //create PasswordField for re-type password
        JPasswordField repasswordField = new JPasswordField();
        repasswordField.setBounds(20, 360, getWidth() - 50, 40);
        repasswordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(repasswordField);

        // create register button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(20, 460,getWidth() - 50, 40);
        registerButton.setFont(new Font("Dialog", Font.BOLD, 20));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get username
                String username = usernameField.getText();

                // get password
                String password = String.valueOf(passwordField.getPassword());

                // get rePassword
                String rePassword = String.valueOf(repasswordField.getPassword());

                // validate the user input
                if(validateUserInput(username, password, rePassword)){
                    // if register success dispose the gui and launch the login gui
                    if(MyJDBC.register(username,password)){
                        // dispose the register gui
                        RegisterGUI.this.dispose();

                        // launch the login gui
                        LoginGUI loginGUI = new LoginGUI();
                        loginGUI.setVisible(true);

                        // create a result dialog
                        JOptionPane.showMessageDialog(loginGUI, "Register Account Successful!");

                    }else{
                        JOptionPane.showMessageDialog(RegisterGUI.this, "ERROR: Username already taken");
                    }
                }else{
                    // invalid user input
                    JOptionPane.showMessageDialog(RegisterGUI.this,
                            "Error: Username must be at least 6 characters\n" +
                            "and/or Password must match");
                }

            }
        });
        add(registerButton);

        // create login label
        JLabel loginLabel = new JLabel("<html><a href=\"#\">Already have an account? Login Here</a></html");
        loginLabel.setBounds(0, 510, getWidth() - 10, 30);
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // dispose the register GUI
                RegisterGUI.this.dispose();

                // launch the login gui
                new LoginGUI().setVisible(true);
            }
        });

        add(loginLabel);

    }

    // if the user input is valid, this method will return true otherwise false.
    private boolean validateUserInput(String username, String password, String rePassword){
        // all fields must have a value
        if(username.length() == 0 || password.length() == 0 || rePassword.length() == 0)
            return false;

        // username must have 6 characters
        if(username.length() < 6){
            return false;
        }

        // password and repassword must be the same
        if(!password.equals(rePassword)){
            return false;
        }

        return true;
    }
}
