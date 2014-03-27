package controllers;
// This is a modification test.
import javax.swing.JOptionPane;
import views.HealthTrackerView;

public class HealthTrackerController
{
    public HealthTrackerController(HealthTrackerView view)
    {
        view.addCalendarButtonListener((e)->
        {
            JOptionPane.showMessageDialog(null,
                    "This button will display a calendar so you can change the viewing date.");
        });
        
        view.addAddActivityButtonListener((e)->
        {
            JOptionPane.showMessageDialog(null,
                    "This button will allow you to record activities, blood pressure, etc.");
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
    }
}
