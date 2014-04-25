package controllers;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.Optional;
import javax.swing.JOptionPane;
import models.LogManager;
import models.ProfileModel;
import views.ChangePasswordPanel;
import views.CreateAccountDialog;
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
                LogManager.getInstance().loadGraphs(healthTrackerView);
                healthTrackerView.setVisible(true);
                HealthTrackerController controller = new HealthTrackerController(healthTrackerView);
            }
            
            else
            {
                JOptionPane.showMessageDialog(null, "Incorrect username or password. If you forgot your password, press the Forgot Password button below.");
            }
        });
        
        view.addCreateAccountButtonListener((e) ->
        {
            CreateAccountDialog dialog = new CreateAccountDialog(null, true);
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            dialog.setLocation(d.width/2 - dialog.getWidth()/2, d.height/2 - dialog.getHeight()/2);
            
            dialog.addCancelButtonListener((e2)->
            {
                dialog.setVisible(false);
                dialog.dispose();
            });
                    
            dialog.addCreateAccountButtonListener((e2) ->
            {
                if (dialog.verifyFields())
                {
                    boolean successful = ProfileModel.getInstance().createProfile(dialog.getUsername(),
                            dialog.getPassword(), 
                            dialog.getRecoveryQuestion(), 
                            dialog.getRecoveryAnswer(),
                            dialog.getBirthMonth(),
                            dialog.getBirthDay(),
                            dialog.getBirthYear());
                    
                    if (! successful)
                    {
                        JOptionPane.showMessageDialog(null, "Failed to create account! Username is already in use. Please choose a new name.");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Account successfully created!");
                        LogManager.getInstance().saveUserList();
                        dialog.setVisible(false);
                        dialog.dispose();
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Please complete all fields.");
                }
            });
            
            dialog.addMonthBoxListener((e2) ->
            {
                if(dialog.getMonthBox().getSelectedItem().equals("January") || dialog.getMonthBox().getSelectedItem().equals("March") ||
                        dialog.getMonthBox().getSelectedItem().equals("May") || dialog.getMonthBox().getSelectedItem().equals("July") ||
                                dialog.getMonthBox().getSelectedItem().equals("August") || dialog.getMonthBox().getSelectedItem().equals("October") ||
                        dialog.getMonthBox().getSelectedItem().equals("December")){
                    dialog.setDayBoxList(new String[] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"});
                }
                else if(dialog.getMonthBox().getSelectedItem().equals("February")){
                    dialog.setDayBoxList(new String[] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29"});
                }
                else{
                    dialog.setDayBoxList(new String[] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"});
                }
                
            });
            
            dialog.setVisible(true);
        });
        
        view.addDeleteAccountButtonListener((e) -> 
        {
            if (view.getUsername().trim().equals("") || view.getPassword().trim().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Please enter your username and password in the dialog box.");
                return;
            }
            
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + view.getUsername() + "'s account? "
                    + "This action cannot be undone.");
            if (result != JOptionPane.YES_OPTION)
            {
                return;
            }
            boolean deletedSuccessfully = ProfileModel.getInstance().deleteProfile(view.getUsername(), view.getPassword());
            if (! deletedSuccessfully)
            {
                JOptionPane.showMessageDialog(null, "That username/password were not recognized.");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Account successfully deleted!");
                LogManager.getInstance().saveUserList();
                LogManager.getInstance().deleteAccountFile(view.getUsername());
                view.clearFields();
            }
        });
        
        view.addForgotPasswordButtonListener((e) ->
        {
            if (view.getUsername().trim().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Please enter your username first.");
                return;
            }
            
            Optional<LinkedList<String>> profile = ProfileModel.getInstance().getProfile(view.getUsername());
            if (! profile.isPresent())
            {
                JOptionPane.showMessageDialog(null, "Username not recognized.");
                return;
            }
            
            String answer = JOptionPane.showInputDialog(profile.get().get(ProfileModel.ProfileItem.QUESTION.ordinal()));
            if (answer == null) return;
            
            if (answer.equals(profile.get().get(ProfileModel.ProfileItem.ANSWER.ordinal())))
            {
                // User correctly entered the answer to the question. Prompt for a new password.
                ChangePasswordPanel panel = new ChangePasswordPanel();
                String[] options = new String[] {"Confirm", "Cancel"};
                int choice = JOptionPane.showOptionDialog(null, panel, "Change password",
                        JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
                
                if (choice == 0)
                {
                    String newPassword = panel.getPassword();
                    ProfileModel.getInstance().changePassword(view.getUsername(), newPassword);
                    JOptionPane.showMessageDialog(null, "Successfully changed password!");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Your answer was incorrect.");
            }
        });
    }
}
