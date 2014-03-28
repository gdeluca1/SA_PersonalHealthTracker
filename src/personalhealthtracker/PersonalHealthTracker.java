package personalhealthtracker;

import views.LoginView;
import controllers.LoginController;

public class PersonalHealthTracker
{
    
    // This is the driver class. Display the login view and give control to the user.
    public static void main(String[] args)
    {
        
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
        LoginController loginController = new LoginController(loginView);
    }
    
}
