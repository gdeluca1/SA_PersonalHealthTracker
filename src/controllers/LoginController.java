package controllers;

import views.HealthTrackerView;
import views.LoginView;

public class LoginController
{
    public LoginController(LoginView view)
    {
        view.addLoginButtonListener((e)->
        {
            // The login interface served its purpose. Garbage collect it and display the next window.
            view.dispose();
            HealthTrackerView healthTrackerView = new HealthTrackerView();
            healthTrackerView.setVisible(true);
            HealthTrackerController controller = new HealthTrackerController(healthTrackerView);
        });
    }
}
