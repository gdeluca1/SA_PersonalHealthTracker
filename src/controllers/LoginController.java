package controllers;

import javax.swing.JOptionPane;
import models.LogManager;
import models.ProfileModel;
import views.HealthTrackerView;
import views.LoginView;

public class LoginController
{
    public LoginController(LoginView view)
    {
        view.addLoginButtonListener((e)->
        {
            // The login interface served its purpose. Garbage collect it and display the next window.
            if (ProfileModel.getInstance().verifyProfile(view.getUsername(), view.getPassword()))
            {
                view.dispose();
                // Make sure to load the activites from the last session.
                LogManager.getInstance().loadActivities();
                HealthTrackerView healthTrackerView = new HealthTrackerView();
                healthTrackerView.setVisible(true);
                HealthTrackerController controller = new HealthTrackerController(healthTrackerView, healthTrackerView.getAddActivityPanel());
            }
            
            else
            {
                JOptionPane.showMessageDialog(null, "Incorrect username or password. If you forgot your password, press the Forgot Password button below.");
            }
        });
    }
}
