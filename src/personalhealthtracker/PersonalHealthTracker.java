package personalhealthtracker;

import controllers.LoginController;
import models.LogManager;
import views.LoginView;

public class PersonalHealthTracker
{
    
    // This is the driver class. Display the login view and give control to the user.
    public static void main(String[] args)
    {
        // Load the list of users into the list.
        LogManager.getInstance().loadUserList();
        // Instantitate and display the login user interface.
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
        // Instantiate the controller which will automatically add listeners to the loginView.
        LoginController loginController = new LoginController(loginView);
    }
    
}
