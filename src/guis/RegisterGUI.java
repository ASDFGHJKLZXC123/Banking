package guis;

import javax.swing.*;
import java.awt.*;

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
        repasswordField.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(repasswordField);

        // create register button
        JButton loginButton = new JButton("Register");
        loginButton.setBounds(20, 460,getWidth() - 50, 40);
        loginButton.setFont(new Font("Dialog", Font.BOLD, 20));
        add(loginButton);

        // create login label
        JLabel loginLabel = new JLabel("<html><a href=\"#\">Already have an account? Login Here</a></html");
        loginLabel.setBounds(0, 510, getWidth() - 10, 30);
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(loginButton);

    }
}
