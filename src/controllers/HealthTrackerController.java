package controllers;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import models.Activity;
import models.ProfileModel;
import personalhealthtracker.GraphFactory;
import views.CalendarPanel;
import views.HealthTrackerView;

public class HealthTrackerController
{
    private static final int PORT = 8989;
    // This boolean tracks whether the receiving activity interface is open.
    // When logging out, this boolean will be set to false so that any
    // running threads will stop.
    private static final AtomicBoolean receivingInterfaceOpen = new AtomicBoolean(false);
    private static HealthTrackerView view;
    private static ServerSocket ss = null;
    
    // Setting this variable to true will display debugging messages.
    private static final boolean DEBUG = false;
    
    public HealthTrackerController(HealthTrackerView view)
    {
        this.view = view;
        
        // Add a window listener. When the user attempts to x out, make sure they want to logout.
        view.addWindowListener(new WindowListener()
        {

            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) 
            {
                // If any data transfers are still running, don't let the user close out.
                if (! HealthTrackerController.close())
                {
                    view.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION)
                {
                    // Logout and save the user data.
                    ProfileModel.getInstance().logout();
                    // Garbage collect.
                    view.dispose();
                    System.exit(0);
                }
                // If the user doesn't want to log out, don't let the window close.
                else
                {
                    view.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        view.addCalendarButtonListener((e)->
        {
            CalendarPanel.showPanel(view);
        });
        
        view.addAddActivityButtonListener((e)->
        {
            // If the add panel is already being displayed, hide it.
            // Otherwise, display the add activity panel.
            view.switchMiddlePanel(
                    view.displayingPanel(HealthTrackerView.ADD_ACTIVITY_PANEL) ? 
                            HealthTrackerView.DEFAULT_PANEL :
                            HealthTrackerView.ADD_ACTIVITY_PANEL);
        });
        
        view.getAddActivityPanel().addSubmitButtonListener((e) ->
        {
            // Can't add a blank activity.
            if (view.getAddActivityPanel().allFieldsEmpty())
            {
                JOptionPane.showMessageDialog(null, "You must enter information in at least one field", "Error", 0);
            }
            
            // Make sure the user actually wants to add this activity.
            else if (JOptionPane.showConfirmDialog(null, "Are you sure you would like to add this activity?") == JOptionPane.YES_OPTION) 
            {
                view.addActivityToPanel(view.getAddActivityPanel().getActivity(view.getDate()));
                view.updateVisibleActivities(false);
                // Switch back to the default panel and reset the add activity panel.
                view.switchMiddlePanel(HealthTrackerView.DEFAULT_PANEL);
                view.getAddActivityPanel().resetPanel();
            }
            
            view.updateVisibleActivities(false);
        });
        
        view.addViewModeButtonListener((e)->
        {
            JRadioButton weeklyButton = new JRadioButton("Weekly Viewing"), 
                    monthlyButton = new JRadioButton("Monthly Viewing"),
                    yearlyButton = new JRadioButton("Yearly Viewing");
            
            weeklyButton.addActionListener((ae) ->
            {
               view.setViewMode(HealthTrackerView.WEEKLY);
            });
            
            monthlyButton.addActionListener((ae) ->
            {
               view.setViewMode(HealthTrackerView.MONTHLY);
            });
            
            yearlyButton.addActionListener((ae) ->
            {
               view.setViewMode(HealthTrackerView.YEARLY);
            });
            
            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(weeklyButton);
            buttonGroup.add(monthlyButton);
            buttonGroup.add(yearlyButton);
            
            switch (view.getViewMode()) 
            {
                case HealthTrackerView.MONTHLY:
                    monthlyButton.setSelected(true);
                    break;
                case HealthTrackerView.YEARLY:
                    yearlyButton.setSelected(true);
                    break;
                default: // WEEKLY
                    weeklyButton.setSelected(true);
                    break;
            }
            
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(weeklyButton);
            buttonPanel.add(monthlyButton);
            buttonPanel.add(yearlyButton);
            
            JDialog dialog = new JDialog();
            dialog.setTitle("Select Viewing Mode");
            dialog.add(buttonPanel);
            dialog.pack();
            dialog.setModal(true);
            
            dialog.addWindowListener(new WindowAdapter() 
            {
                @Override
                public void windowClosing(WindowEvent event) 
                {
                    view.updateVisibleActivities(false);
                }
            });
            
            // Put the dialog in the middle of the screen.
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            dialog.setLocation(d.width/2 - dialog.getWidth()/2, d.height/2 - dialog.getHeight()/2);
            
            dialog.setVisible(true);
        });
        
        view.addTrendingButtonListener((e)->
        {
//            view.addGraphPanel(GraphFactory.getLineChart(1));
            // If the trending panel is already being displayed, hide it.
            // Otherwise, display the trending panel.
            view.switchMiddlePanel(
                    view.displayingPanel(HealthTrackerView.TRENDING_PANEL) ? 
                            HealthTrackerView.DEFAULT_PANEL :
                            HealthTrackerView.TRENDING_PANEL);
        });
        
        view.getTrendingPanel().addBarGraphButtonListener((e) ->
        {
            view.addGraphPanel(GraphFactory.getBarChart());
        });
        
        view.getTrendingPanel().addPieChartButtonListener((e) ->
        {
            view.addGraphPanel(GraphFactory.getPieChart());
        });
        
        view.getTrendingPanel().addLineGraphButtonListener((e) ->
        {
            view.addGraphPanel(GraphFactory.getLineChart(view.getTrendingPanel().getSelectedRadioButton(), 
                    view.getTrendingPanel().autoUpdateLine()));
        });
        
        view.addPrintButtonListener((e)->
        {
            JOptionPane.showMessageDialog(null,
                    "This button will allow you to print your data.");
        });
        
        // Note: The import button is solely for Gennaro's and Sumbhav's CSE 360 Honors Contract.
        // Add a lambda expression as an action listener to the Import button.
        view.addImportButtonListener((e)->
        {
            // Was the data successfully received?
            AtomicBoolean dataReceived = new AtomicBoolean(false);
            
            // This thread takes care of socket communications.
            Thread t = new Thread(()->
            {
                try
                {
                    ss = new ServerSocket(PORT);
                    Socket socket = null;
                    while (socket == null)
                    {
                        // The user is no longer attempting to receive information. Close the sockets and return.
                        if (! receivingInterfaceOpen.get())
                        {
                            ss.close();
                            System.out.println("Closing socket.");
                            return;
                        }
                        socket = ss.accept();
                    }

                    view.startProgressBar();

                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    ArrayList<Activity> activityList = (ArrayList<Activity>) in.readObject();
                    
                    // The data has all been received. Add it to the actual list.
                    activityList
                            .parallelStream()
                            .forEach((activity)->
                            {
                               view.addActivityToPanel(activity);
                            });
                    
                    JOptionPane.showMessageDialog(null, "Data successfully received!");
                    dataReceived.set(true);
                }
                catch (SocketException ex)
                {
                    // This will be an exception which means that the thread ended.
                    if (DEBUG)
                        System.err.println("Socket forcefully stopped.");
                }
                catch (IOException | ClassNotFoundException ex)
                {
                    Logger.getLogger(HealthTrackerController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "An error occurred!");
                }
                finally
                {
                    // Time for cleanup.
                    view.stopProgressBar();
                    if (ss != null)
                    {
                        try
                        {
                            ss.close();
                            ss = null;
                        }
                        catch (IOException ex)
                        {
                            Logger.getLogger(HealthTrackerController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    if (dataReceived.get())
                    {
                        // If the data was successfully received, set it back to false and switch to the main interface.
                        dataReceived.set(false);
                        view.switchMiddlePanel(HealthTrackerView.DEFAULT_PANEL);
                    }
                }
            });
            
            // If the import activites panel is already being displayed, hide it.
            // Otherwise, display the add activity panel.
            if (view.displayingPanel(HealthTrackerView.RECEIVE_ACTIVITY_PANEL))
            {
                view.switchMiddlePanel(HealthTrackerView.DEFAULT_PANEL);
                while (! close())
                {
                    // wait for it to close.
                }
            }
            else
            {
                view.switchMiddlePanel(HealthTrackerView.RECEIVE_ACTIVITY_PANEL);
                while (! receivingInterfaceOpen.get())
                {
                    // wait for this boolean to be set to true before starting the thread.
                    // Putting this line outside a loop will cause the thread to start prematurely.
                    receivingInterfaceOpen.set(true);
                }
                t.start();
            }
        });
    }
    
    /**
     * Make sure that data isn't being transferred. If no data is being transferred, 
     * close any running sockets by setting the atomic boolean to false.
     */
    public static boolean close()
    {
        // Don't let the user quit until the data is done transferring.
        if (view.progressBarIsRunning())
        {
            JOptionPane.showConfirmDialog(null, 
                    "Warning! Data is being transferred betewen devices. Please wait until the transfer is finished to avoid data corruption.", 
                    "Warning!", 
                    JOptionPane.OK_CANCEL_OPTION, 
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        else
        {
            while (receivingInterfaceOpen.get())
                receivingInterfaceOpen.set(false);

            try
            {
                // If the server socket was running, forcefully close it.
                if (ss != null)
                {
                    ss.close();
                    ss = null;
                }
            }
            catch (IOException ex)
            {
                Logger.getLogger(HealthTrackerController.class.getName()).log(Level.SEVERE, null, ex);
            }

            return true;
        }
    }
}
