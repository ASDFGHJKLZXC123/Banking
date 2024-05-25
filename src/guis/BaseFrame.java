package guis;

import javax.swing.*;

/*Creating an abstract class helps us setup the blueprint that our GUIS will follows. */
public abstract class BaseFrame extends JFrame{
    public BaseFrame(String title) {
        initialize(title);
    }

    private void initialize(String title){
        // instantiate jframe properties and adda a title to the bar
        setTitle(title);

        // set size (in pixels)
        setSize(420, 600);

        // terminate program when the gui is close
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // set layout to null to have absolute layout which allows us to manually specify the size and position of each gui component
        setLayout(null);

        // prevent gui from being resized
        setResizable(false);

        // launch the gui in the center of the screen
        setLocationRelativeTo(null);

        // call on the subclass' addGuiComponent()
        addGuiComponents();
    }

    // this method will need to be defined by subclasses when this class i s being inherited from
    protected abstract void addGuiComponents();

}