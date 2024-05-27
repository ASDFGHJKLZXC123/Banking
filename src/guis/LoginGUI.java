package guis;

import db_objs.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
    This gui will allow user to login or launch the register GUI
    This extends from the BaseFrame
 */

public class LoginGUI extends BaseFrame{
    public LoginGUI(){
        super("Banking Login");
    }

    @Override
    protected void addGuiComponents() {
        // create banking label
        JLabel bankingLabel = new JLabel("Banking");

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
        passwordLabel.setBounds(20, 280, getWidth(), 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        //create JTextField for password input
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 320, getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordField);

        // create login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(20, 460,getWidth() - 50, 40);
        loginButton.setFont(new Font("Dialog", Font.BOLD, 20));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // get username
                String username = usernameField.getText();

                // get password
                String password = String.valueOf(passwordField.getPassword());

                // validate login
                User user = MyJDBC.validateLogin(username, password);

                // if user is null it means invalid otherwise it is a valid account
                if(user != null){
                    // means valid login

                    //dispose this gui
                    LoginGUI.this.dispose();

                    // launch bank app gui
                    UserGUI UserGUI = new UserGUI(user);
                    UserGUI.setVisible(true);

                    //show success dialog
                    JOptionPane.showMessageDialog(UserGUI, "Login Successfully!");
                }else{
                    // invalid login
                    JOptionPane.showMessageDialog(LoginGUI.this, "Login failed...");
                }
            }
        });
        add(loginButton);

        // create register label
        JLabel registerLabel = new JLabel("<html><a href=\"#\">Don't have an account? Register Here</a></html");
        registerLabel.setBounds(0, 510, getWidth() - 10, 30);
        registerLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // add event listener to the register button
        registerLabel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                // dispose of this gui
                LoginGUI.this.dispose();

                // launch the register gui
                new RegisterGUI().setVisible(true);            }
        });

        add(registerLabel);













    }
}
