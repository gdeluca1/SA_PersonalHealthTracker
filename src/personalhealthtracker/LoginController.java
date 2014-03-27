/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package personalhealthtracker;

/**
 *
 * @author Gennaro
 */
public class LoginController
{
    public LoginController(LoginView view)
    {
        view.addLoginButtonListener((e)->
        {
//            JOptionPane.showMessageDialog(null, "You pressed the login button. Or enter.");
            // The login interface served its purpose. Garbage collect it and display the next window.
            view.dispose();
            HealthTrackerView healthTrackerView = new HealthTrackerView();
            healthTrackerView.setVisible(true);
            HealthTrackerController controller = new HealthTrackerController(healthTrackerView);
        });
    }
}
