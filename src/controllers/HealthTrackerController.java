package controllers;

import javax.swing.JOptionPane;
import models.ActivityModel;
import views.AddActivityPanel;
import views.HealthTrackerView;

public class HealthTrackerController
{
    public HealthTrackerController(HealthTrackerView view, AddActivityPanel view2)
    {
        view.addCalendarButtonListener((e)->
        {
            JOptionPane.showMessageDialog(null,
                    "This button will display a calendar so you can change the viewing date.");
        });
        
        view.addAddActivityButtonListener((e)->
        {
//            JOptionPane.showMessageDialog(null,
//                    "This button will allow you to record activities, blood pressure, etc.");
            // If the add panel is already being displayed, hide it.
            // Otherwise, display the add activity panel.
            view.switchMiddlePanel(
                    view.displayingPanel(HealthTrackerView.ADD_ACTIVITY_PANEL) ? 
                            HealthTrackerView.DEFAULT_PANEL : 
                            HealthTrackerView.ADD_ACTIVITY_PANEL);
        });
        
        view2.addSubmitButtonListener((e) ->
        {
            // Make sure the user actually wants to add this activity.
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you would like to add this activity?");
            if (choice != JOptionPane.YES_OPTION) 
            {
                return;
            }
            
            view.addActivityToPanel(view2.getActivity());
            // Switch back to the default panel and reset the add activity panel.
            view.switchMiddlePanel(HealthTrackerView.DEFAULT_PANEL);
            view2.resetPanel();
        });
        
        view.addViewModeButtonListener((e)->
        {
            JOptionPane.showMessageDialog(null,
                    "Change between daily, weekly, and monthly viewing of your data.");
        });
        
        view.addTrendingButtonListener((e)->
        {
            JOptionPane.showMessageDialog(null,
                    "This button grants access to additional graphing cabailities.");
        });
        
        view.addPrintButtonListener((e)->
        {
            JOptionPane.showMessageDialog(null,
                    "This button will allow you to print your data.");
        });
        
        view.addImportButtonListener((e)->
        {
            JOptionPane.showMessageDialog(null,
                    "This button will allow transfer of data from the Android App to your account.");
        });
    };
}
