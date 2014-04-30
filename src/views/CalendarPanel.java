package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import personalhealthtracker.PersonalHealthTracker;

public class CalendarPanel
{
    private static JPanel currentPanel = null;
    private static int month = -1, day = -1, year = -1;
    private static JDialog dialog = null;
    private static HealthTrackerView parent;
    
    public static void showPanel(HealthTrackerView parent)
    {
        CalendarPanel.parent = parent;
        
        // Make the default values today.
        if (month == -1 || year == -1 || day == -1)
        {
            month = Calendar.getInstance().get(Calendar.MONTH);
            day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            year = Calendar.getInstance().get(Calendar.YEAR);
            parent.updateDateLabel(LocalDate.of(year, month + 1, day).format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        }
        if (dialog != null)
        {
            dialog.setVisible(true);
            return;
        }
        
        MonthPanel panel = new MonthPanel(month, year);

        dialog = new JDialog();
        dialog.setTitle("Calendar");
        dialog.add(panel);
        dialog.pack();
        dialog.setResizable(false);
        
        // When closing the dialog, update the visible activities to reflect the date change.
        dialog.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent event) 
            {
                parent.updateVisibleActivities(false);
            }
        });
        
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation(d.width/2 - dialog.getWidth()/2, d.height/2 - dialog.getHeight()/2);
        
        dialog.setModal(true);
        dialog.setVisible(true);
        dialog.setFocusable(true);
    }

    public static int getMonth()
    {
        // Make the default values today.
        if (month == -1 || year == -1 || day == -1)
        {
            month = Calendar.getInstance().get(Calendar.MONTH);
            day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            year = Calendar.getInstance().get(Calendar.YEAR);
        }
        return month;
    }

    public static int getDay()
    {
        // Make the default values today.
        if (month == -1 || year == -1 || day == -1)
        {
            month = Calendar.getInstance().get(Calendar.MONTH);
            day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            year = Calendar.getInstance().get(Calendar.YEAR);
        }
        return day;
    }

    public static int getYear()
    {
        // Make the default values today.
        if (month == -1 || year == -1 || day == -1)
        {
            month = Calendar.getInstance().get(Calendar.MONTH);
            day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            year = Calendar.getInstance().get(Calendar.YEAR);
        }
        return year;
    }
    
    private static class MonthPanel extends JPanel 
    {        
        private final String monthNames[] = { "January", "February", "March", "April", "May", "June", "July", 
            "August", "September", "October", "November", "December"       };

        private final String dayNames[] = { "S", "M", "T", "W", "T", "F", "S"};
        
        private JPanel daysPanel, monthPanel;

        public MonthPanel(int month, int year) 
        {
            CalendarPanel.month = month;
            CalendarPanel.year = year;

            add(createGUI());
        }

        protected final JPanel createGUI() 
        {
            monthPanel = new JPanel(true);
            monthPanel.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption));
            monthPanel.setLayout(new BorderLayout());
            monthPanel.setBackground(Color.WHITE);
            monthPanel.setForeground(Color.BLACK);
            
            monthPanel.add(createTitleGUI(), BorderLayout.NORTH);
            
            daysPanel = createDaysGUI();
            monthPanel.add(daysPanel, BorderLayout.SOUTH);

            return monthPanel;
        }

        protected JPanel createTitleGUI() 
        {
            // Create the panel that displays the title.
            final JPanel titlePanel = new JPanel(true);
            titlePanel.setBorder(BorderFactory
                    .createLineBorder(SystemColor.activeCaption));
            titlePanel.setLayout(new FlowLayout());
            titlePanel.setBackground(Color.WHITE);

            // Display the month and year.
            JLabel label = new JLabel(monthNames[month] + " " + year);
            label.setForeground(SystemColor.activeCaption);

            // Display left and right buttons.
            JButton leftButton = null, rightButton = null;
            try
            {
                leftButton = new JButton(new ImageIcon(ImageIO.read(PersonalHealthTracker.class.getResource("arrow-2-left.png"))));
                rightButton = new JButton(new ImageIcon(ImageIO.read(PersonalHealthTracker.class.getResource("arrow-2-right.png"))));
                
                // When the left button is clicked, go back one month.
                leftButton.addActionListener((e) ->
                {
                    monthPanel.remove(daysPanel);
                    day = 1;
                    month--;
                    
                    // If it was January, make it December of the previous year.
                    if (month < 0)
                    {
                        month += 12;
                        year--;
                    }
                    daysPanel = createDaysGUI();
                    monthPanel.add(daysPanel, BorderLayout.SOUTH);
                    
                    label.setText(monthNames[month] + " " + year);
                    parent.updateDateLabel(LocalDate.of(year, month + 1, day).format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                    
                    // Validate and repaint to make the changes visible to the user.
                    dialog.pack();
                    validate();
                    repaint();
                });
                
                // When the right button is clicked, go forward one month.
                rightButton.addActionListener((e) ->
                {
                    monthPanel.remove(daysPanel);
                    day = 1;
                    month++;
                    
                    // If it was december, make it January of the next year.
                    if (month > 11)
                    {
                        month -= 12;
                        year++;
                    }
                    daysPanel = createDaysGUI();
                    monthPanel.add(daysPanel, BorderLayout.SOUTH);
                    
                    label.setText(monthNames[month] + " " + year);
                    parent.updateDateLabel(LocalDate.of(year, month + 1, day).format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                    
                    // Validate and repaint to make the changes visible to the user.
                    dialog.pack();
                    validate();
                    repaint();
                });
            }
            catch (IOException ex)
            {
                Logger.getLogger(CalendarPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (leftButton != null)
                titlePanel.add(leftButton);
            
            titlePanel.add(label);
            
            if (rightButton != null)
                titlePanel.add(rightButton);

            return titlePanel;
        }

        protected JPanel createDaysGUI() 
        {
            JPanel dayPanel = new JPanel(true);
            dayPanel.setLayout(new GridLayout(0, dayNames.length));

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            
            // Create an iterator to go through the current month's days.
            Calendar iterator = (Calendar) calendar.clone();
            iterator.add(Calendar.DAY_OF_MONTH,
                    -(iterator.get(Calendar.DAY_OF_WEEK) - 1));

            Calendar maximum = (Calendar) calendar.clone();
            maximum.add(Calendar.MONTH, +1);

            // Create a panel for each day of the week.
            for (String dayName : dayNames)
            {
                JPanel dPanel = new JPanel(true);
                dPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabel dLabel = new JLabel(dayName);
                dPanel.add(dLabel);
                dayPanel.add(dPanel);
            }

            int count = 0;
            int limit = dayNames.length * 6;

            while (iterator.getTimeInMillis() < maximum.getTimeInMillis()) {
                int lMonth = iterator.get(Calendar.MONTH);
                int lYear = iterator.get(Calendar.YEAR);

                JPanel dPanel = new JPanel(true);
                dPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabel dayLabel = new JLabel();

                if ((lMonth == month) && (lYear == year)) {
                    int lDay = iterator.get(Calendar.DAY_OF_MONTH);
                    dayLabel.setText(Integer.toString(lDay));
                    dPanel.setBackground(Color.WHITE);
                    
                    if (Integer.parseInt(dayLabel.getText().trim()) == day)
                    {
                        dPanel.setBackground(Color.ORANGE);
                        currentPanel = dPanel;
                    }
                } else {
                    dayLabel.setText(" ");
                    dPanel.setBackground(Color.WHITE);
                }
                dPanel.addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mousePressed(MouseEvent e)
                    {
                        // Find the source panel and the label on it. Change the color and save the date.
                        JPanel previousPanel = currentPanel;
                        currentPanel = (JPanel)e.getSource();
                        JLabel currentLabel = (JLabel)currentPanel.getComponent(0);
                        
                        if (currentLabel.getText().trim().equals(""))
                        {
                            currentPanel = previousPanel;
                            return;
                        }
                        
                        day = Integer.parseInt(currentLabel.getText().trim());
                        parent.updateDateLabel(LocalDate.of(year, month + 1, day).format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                        
                        if (previousPanel != null)
                        {
                            previousPanel.setBackground(Color.WHITE);
                        }
                        currentPanel.setBackground(Color.ORANGE);
                    }
                });
                dPanel.add(dayLabel);
                dayPanel.add(dPanel);
                iterator.add(Calendar.DAY_OF_YEAR, +1);
                count++;
            }

            for (int i = count; i < limit; i++) {
                JPanel dPanel = new JPanel(true);
                dPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabel dayLabel = new JLabel();
                dayLabel.setText(" ");
                dPanel.setBackground(Color.WHITE);
                dPanel.add(dayLabel);
                dayPanel.add(dPanel);
            }

            return dayPanel;
        }
    }
}


