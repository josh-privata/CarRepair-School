/**
 *
 * @author josh
 */

import javax.swing.JFrame;

public class Start {
    
    // Implement gui and run program
    public static void main( String[] args ) {
        Gui frame = new Gui();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize(575, 400);
        frame.setVisible(true);
        frame.setResizable(true);       
    }
    
} // End class Start
