package personalhealthtracker;

import controllers.LoginController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import models.LogManager;
import views.LoginView;

public class PersonalHealthTracker
{
    // Setting DEBUG to true enables testing of the Line Chart.
    private static final boolean DEBUG = true;
    
    // This is the driver class. Display the login view and give control to the user.
    public static void main(String[] args)
    {
        if (DEBUG)
        {
            JFrame testFrame = new JFrame();
            testFrame.add(GraphFactory.getLineChart(1));
            testFrame.setSize(700, 360);
            testFrame.setVisible(true);
            testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            return;
        }
        
        // Load the list of users into the list.
        LogManager.getInstance().loadUserList();
        // Instantitate and display the login user interface.
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
        // Instantiate the controller which will automatically add listeners to the loginView.
        LoginController loginController = new LoginController(loginView);
    }
    
}
