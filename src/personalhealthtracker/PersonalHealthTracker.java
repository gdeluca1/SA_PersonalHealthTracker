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
public class PersonalHealthTracker
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
        LoginController loginController = new LoginController(loginView);
    }
    
}
