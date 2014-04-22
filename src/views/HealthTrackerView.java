package views;

import controllers.HealthTrackerController;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import models.ActivityModel;
import models.Activity;
import models.ProfileModel;
import personalhealthtracker.GraphFactory;
import personalhealthtracker.PersonalHealthTracker;

public class HealthTrackerView extends javax.swing.JFrame
{
    private static final Dimension screenSize;
    public static final String DEFAULT_PANEL = "Default displayed JPanel";
    public static final String ADD_ACTIVITY_PANEL = "Panel for adding activities.";
    public static final String RECEIVE_ACTIVITY_PANEL = "Panel for receiving activities from the mobile app version.";
    private String currentPanel;
    
    private AddActivityPanel addActivityPanel;
    private JPanel middlePanel;
    private JPanel defaultMiddlePanel;
    
    private ActivityReceiverView activityReceiverView;
    
    public static final int WEEKLY = -1, MONTHLY = 0, YEARLY = 1;
    
    // Either weekly, monthly, or yearly.
    private int viewMode = WEEKLY;
    
    /**
     * Creates new form HealthTrackerController2
     */
    public HealthTrackerView()
    {
        currentPanel = DEFAULT_PANEL;
        middlePanel = new JPanel();
        
        initComponents();
        
        try
        {
             setIconImage(ImageIO.read(PersonalHealthTracker.class.getResource("librarian.jpg")));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.out.println("Failed to set icon image for health tracker view.");
        }
        
        defaultMiddlePanel = new JPanel();
        addActivityPanel = new AddActivityPanel();
        activityReceiverView = new ActivityReceiverView();
        
        defaultMiddlePanel.setLayout(new BoxLayout(defaultMiddlePanel, BoxLayout.Y_AXIS));
        
        middlePanel.setLayout(new CardLayout());
        middlePanel.add(defaultMiddlePanel, DEFAULT_PANEL);
        middlePanel.add(addActivityPanel, ADD_ACTIVITY_PANEL);
        middlePanel.add(activityReceiverView, RECEIVE_ACTIVITY_PANEL);
        
        // Center the frame in the middle of the screen.
        setLocation(screenSize.width/2 - getWidth()/2, screenSize.height/2 - getHeight()/2);
        
        ActivityModel.getInstance();
        
        // Add a window listener. When the user attempts to x out, make sure they want to logout.
        addWindowListener(new WindowListener()
        {

            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) 
            {
                // If any data transfers are still running, don't let the user close out.
                if (! HealthTrackerController.close())
                {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION)
                {
                    // Logout and save the user data.
                    ProfileModel.getInstance().logout();
                    // Garbage collect.
                    dispose();
                    System.exit(0);
                }
                // If the user doesn't want to log out, don't let the window close.
                else
                {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
        
        // If there were activities saved from last time, display them to the user.
        if (! ActivityModel.getInstance().getActivities().isEmpty())
        {
            updateVisibleActivities(true);
//            ActivityModel.getInstance().getActivities().forEach((activity) -> defaultMiddlePanel.add(new ActivityPanel(activity)));
        }
    }
    
    static 
    {
        // Screen size is a non-changing, static constant. Instatiate it here.
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }
    
    /**
     * Creates a new activity panel for the activity AND adds the
     * activity to the activity list in ActivityModel.
     * @param activity 
     */
    public final void addActivityToPanel(Activity activity)
    {
        ActivityModel.getInstance().addEntry(activity);
        ActivityPanel panel = new ActivityPanel(activity);
        defaultMiddlePanel.add(panel);
    }
    
    public final void updateVisibleActivities(boolean firstTime)
    {
        try
        {
            // Remove all the activities from the panel first.
            defaultMiddlePanel.removeAll();
            ActivityModel.getInstance().getVisibleActivities().clear();
            
            int day = CalendarPanel.getDay(), 
                    month = CalendarPanel.getMonth(),
                    year = CalendarPanel.getYear();
            
            // Use a calendar object to get the day of the week.
            Calendar c = Calendar.getInstance();
            
            // Parse the date from the date label.
            c.setTime(new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(dateLabel.getText()));
            // Day = 1 for Sunday, 7 for Saturday.
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            
            // This stream handles determining if an activity should be displayed depending
            // on the selected view mode and that activity's date.
            ActivityModel.
                    getInstance().
                    getActivities()
                    .stream()
                    .forEach((activity) ->
                    {
                        Calendar cal = (Calendar) Calendar.getInstance().clone();
                        cal.setTime(activity.getTimeStamp());
                        int theYear = cal.get(Calendar.YEAR);
                        int theMonth = cal.get(Calendar.MONTH);
                        int theDay = cal.get(Calendar.DAY_OF_MONTH);
                        if (viewMode == WEEKLY)
                        {
                            if ((theDay >= (day - dayOfWeek + 1)) &&
                                    (theDay <= day) &&
                                    (theMonth + 1 == month + 1) &&
                                    (theYear == year))
                            {
                                ActivityPanel panel = new ActivityPanel(activity);
                                defaultMiddlePanel.add(panel);
                                ActivityModel.getInstance().getVisibleActivities().add(activity);
                            }
                        }
                        else if (viewMode == MONTHLY)
                        {
                            if ((theDay <= day) && 
                                    (theMonth + 1 == month + 1) &&
                                    (theYear == year))
                            {
                                ActivityPanel panel = new ActivityPanel(activity);
                                defaultMiddlePanel.add(panel);
                                ActivityModel.getInstance().getVisibleActivities().add(activity);
                            }
                        }
                        else if (viewMode == YEARLY)
                        {
                            // This is the case if the mode is annually and the day is in the current month.
                            if ((theYear == year) &&
                                    (theDay <= day) &&
                                    (theMonth + 1 == month + 1))
                            {
                                ActivityPanel panel = new ActivityPanel(activity);
                                defaultMiddlePanel.add(panel);
                                ActivityModel.getInstance().getVisibleActivities().add(activity);
                            }
                            
                            // It should also display if it's in an earlier month of the year.
                            else if ((theYear == year) &&
                                    (theMonth + 1 <= month))
                            {
                                ActivityPanel panel = new ActivityPanel(activity);
                                defaultMiddlePanel.add(panel);
                                ActivityModel.getInstance().getVisibleActivities().add(activity);
                            }
                        }
                    });
            
            if (ActivityModel.getInstance().getActivities().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "No activities have been added to this account yet!");
            }
            
            else if (ActivityModel.getInstance().getVisibleActivities().isEmpty())
            {
                String time = "";
                if (viewMode == MONTHLY) time = "month.";
                else if (viewMode == YEARLY) time = "year.";
                else time = "week.";
                
                JOptionPane.showMessageDialog(null, "None of the activities have been added within the past " + time
                        + " Change the view mode setting to try to expand this time field.");
            }
            
            if (firstTime) return;
            
            // Toggling the panels forces them to repaint and for the scrollbar to adjust accordingly.
            switchMiddlePanel(RECEIVE_ACTIVITY_PANEL);
            switchMiddlePanel(DEFAULT_PANEL);
        }
        catch (ParseException ex)
        {
            Logger.getLogger(HealthTrackerView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AddActivityPanel getAddActivityPanel()
    {
        return addActivityPanel;
    }
    
    /**
     * Start running the progress bar while receiving activities from the phone.
     */
    public void startProgressBar()
    {
        activityReceiverView.getProgressBar().setEnabled(true);
        activityReceiverView.getProgressBar().setIndeterminate(true);
    }
    
    /**
     * This method stops the progress bar and disables it.
     */
    public void stopProgressBar()
    {
        activityReceiverView.getProgressBar().setEnabled(false);
        activityReceiverView.getProgressBar().setIndeterminate(false);
    }
    
    /**
     * Returns true if the progress bar is running.
     * If this method returns true, you are currently receiving data. Don't allow
     * the user to exit the program if information is being transferred to avoid
     * any information corruption.
     * @return 
     */
    public boolean progressBarIsRunning()
    {
        return activityReceiverView.getProgressBar().isIndeterminate();
    }
    
    /**
     * Switch the card layout to display a new panel.
     * @param newPanel A string from the HealthTrackerView class.
     */
    public void switchMiddlePanel(String newPanel)
    {
        // Make sure to close any open sockets and that no data is being transferred.
        HealthTrackerController.close();
        
        ((CardLayout) middlePanel.getLayout()).show(middlePanel, newPanel);
        currentPanel = newPanel;
    }
    
    /**
     * Is the middle panel current displaying the default panel?
     * @return true if the middle panel is displaying the default panel.
     */
    public boolean displayingPanel(String panel)
    {
        return currentPanel.equals(panel);
    }
    
    public void updateDateLabel(String date)
    {
        dateLabel.setText(date);
    }
    
    public void addCalendarButtonListener(ActionListener listener)
    {
        calendarButton.addActionListener(listener);
    }
    
    public void addAddActivityButtonListener(ActionListener listener)
    {
        addActivityButton.addActionListener(listener);
    }
    
    public void addViewModeButtonListener(ActionListener listener)
    {
        viewModeButton.addActionListener(listener);
    }
     
    public void addTrendingButtonListener(ActionListener listener)
    {
        trendingButton.addActionListener(listener);
    }
      
    public void addPrintButtonListener(ActionListener listener)
    {
        printButton.addActionListener(listener);
    }
    
    public void addImportButtonListener(ActionListener listener)
    {
        importButton.addActionListener(listener);
    }

    public void setViewMode(int viewMode)
    {
        this.viewMode = viewMode;
    }

    public int getViewMode()
    {
        return viewMode;
    }
    
    public void addGraphPanel(JPanel graph)
    {
        graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));
        System.out.println("adding graph.");
        JPanel newGraph = GraphFactory.getBarChart(1);
        newGraph.setPreferredSize(new Dimension(300, 200));
        graphPanel.add(newGraph);
        newGraph = GraphFactory.getLineChart(1);
        newGraph.setPreferredSize(new Dimension(300, 200));
        graphPanel.add(newGraph);
        graphPanel.revalidate();
        graphPanel.repaint();
    }
    
    // Code below this line is generated code.
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        addActivityButton = new javax.swing.JButton();
        viewModeButton = new javax.swing.JButton();
        trendingButton = new javax.swing.JButton();
        printButton = new javax.swing.JButton();
        importButton = new javax.swing.JButton();
        dateLabel = new javax.swing.JLabel();
        calendarButton = new javax.swing.JButton();
        scrollPanel = new javax.swing.JScrollPane(middlePanel);
        jScrollPane1 = new javax.swing.JScrollPane();
        graphPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Personal Health Tracker");

        addActivityButton.setText("Add Activity");

        viewModeButton.setText("View Mode");

        trendingButton.setText("Trending");

        printButton.setText("Print");

        importButton.setText("Import");

        dateLabel.setText(null);
        //Date date = new Date();
        //String dateString = new SimpleDateFormat("MM-dd-yyyy").format(date);
        String dateString = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        dateLabel.setText(dateString);

        try
        {
            calendarButton.setIcon(new ImageIcon(ImageIO.read(PersonalHealthTracker.class.getResource("calendar.png"))));
        }
        catch (IOException ex) {ex.printStackTrace();}

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 308, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(graphPanel);
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addActivityButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(viewModeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(trendingButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(printButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(importButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(dateLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calendarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(scrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(calendarButton)
                    .addComponent(dateLabel))
                .addGap(26, 26, 26)
                .addComponent(addActivityButton)
                .addGap(18, 18, 18)
                .addComponent(viewModeButton)
                .addGap(18, 18, 18)
                .addComponent(trendingButton)
                .addGap(18, 18, 18)
                .addComponent(printButton)
                .addGap(18, 18, 18)
                .addComponent(importButton)
                .addContainerGap(118, Short.MAX_VALUE))
            .addComponent(scrollPanel)
            .addComponent(jScrollPane1)
        );

        addActivityButton.getAccessibleContext().setAccessibleName("addActivityButton");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addActivityButton;
    private javax.swing.JButton calendarButton;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JButton importButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton printButton;
    private javax.swing.JScrollPane scrollPanel;
    private javax.swing.JButton trendingButton;
    private javax.swing.JButton viewModeButton;
    // End of variables declaration//GEN-END:variables
}
