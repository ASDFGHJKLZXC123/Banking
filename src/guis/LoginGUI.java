package guis;


import javax.swing.*;
import java.awt.*;

/*
    This gui will allow user to login or launch the register GUI
    This extends from the BaseFrame
 */
public class LoginGUI extends BaseFrame{
    public LoginGUI(){
        super("Banking Login");
    }

    @Override
    protected void addGuiComponents(){
        // create banking label
        JLabel bankingLabel = new JLabel("Banking");

        // set the location and the size of the gui component
        bankingLabel.setBounds(0,20,super.getWidth(), 40);

        // change the font style
        bankingLabel.setFont(new Font("Dialog", Font.BOLD, 32));

        // center text in JLabel
        bankingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // add to gui
        add(bankingLabel);
    }
}
