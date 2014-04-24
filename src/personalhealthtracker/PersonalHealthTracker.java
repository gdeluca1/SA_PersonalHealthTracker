package personalhealthtracker;

import controllers.HealthTrackerController;
import controllers.LoginController;
import models.LogManager;
import models.ProfileModel;
import views.HealthTrackerView;
import views.LoginView;

public class PersonalHealthTracker
{
    private static final boolean DEBUG = true;
    
    // This is the driver class. Display the login view and give control to the user.
    public static void main(String[] args)
    {
        if (DEBUG)
        {
            if (ProfileModel.getInstance().verifyProfile("admin", "password"))
            {
                LogManager.getInstance().loadActivities();
                HealthTrackerView healthTrackerView = new HealthTrackerView();
                healthTrackerView.setVisible(true);
                HealthTrackerController controller = new HealthTrackerController(healthTrackerView);
            }
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
