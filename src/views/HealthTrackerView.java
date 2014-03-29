package views;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import models.ActivityModel;
import models.Activity;
import models.ProfileModel;
import personalhealthtracker.PersonalHealthTracker;

public class HealthTrackerView extends javax.swing.JFrame
{
    private static final Dimension screenSize;
    public static final String DEFAULT_PANEL = "Default displayed JPanel";
    public static final String ADD_ACTIVITY_PANEL = "Panel for adding activities.";
    
    private JPanel addPanel;
    private JPanel middlePanel;
    private JPanel defaultMiddlePanel;
    
    /**
     * Creates new form HealthTrackerController2
     */
    public HealthTrackerView()
    {
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
        addPanel = new JPanel();
        
        defaultMiddlePanel.setLayout(new BoxLayout(defaultMiddlePanel, BoxLayout.Y_AXIS));
//        addPanel.add(new JButton("Test 2"));
        
        middlePanel.setLayout(new CardLayout());
        middlePanel.add(defaultMiddlePanel, DEFAULT_PANEL);
        middlePanel.add(addPanel, ADD_ACTIVITY_PANEL);
        
//        ((CardLayout) middlePanel.getLayout()).show(middlePanel, ADD_ACTIVITY_PANEL);
        
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
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION)
                {
                    ProfileModel.getInstance().logout();
                    dispose();
                    System.exit(0);
                }
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
    }
    
    static 
    {
        // Screen size is a non-changing, static constant. Instatiate it here.
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }
    
    public void addActivityPanel(Activity activity)
    {
        ActivityModel.getInstance().addEntry(activity);
        ActivityPanel panel = new ActivityPanel(activity);
        defaultMiddlePanel.add(panel);
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

    
    // All code below this line is generated code.
    
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
                .addContainerGap(315, Short.MAX_VALUE))
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
        );

        addActivityButton.getAccessibleContext().setAccessibleName("addActivityButton");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addActivityButton;
    private javax.swing.JButton calendarButton;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JButton importButton;
    private javax.swing.JButton printButton;
    private javax.swing.JScrollPane scrollPanel;
    private javax.swing.JButton trendingButton;
    private javax.swing.JButton viewModeButton;
    // End of variables declaration//GEN-END:variables
}
