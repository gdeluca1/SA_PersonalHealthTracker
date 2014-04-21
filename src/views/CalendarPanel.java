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
import java.io.IOException;
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

// TODO: Add comments
public class CalendarPanel
{
    private static JPanel currentPanel = null;
    private static int month = -1, day = -1, year = -1;
    private static JDialog frame = null;
    
    public static void showPanel()
    {
        // Make the default values today.
        if (month == -1 || year == -1 || day == -1)
        {
            month = Calendar.getInstance().get(Calendar.MONTH);
            day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            year = Calendar.getInstance().get(Calendar.YEAR);
        }
        if (frame != null)
        {
            frame.setVisible(true);
            return;
        }
        
        MonthPanel panel = new MonthPanel(month, year);

        frame = new JDialog();
        frame.setTitle("Calendar");
        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
        
        frame.setModal(true);
        frame.setVisible(true);
        frame.setFocusable(true);
    }

    public static int getMonth()
    {
        return month;
    }

    public static int getDay()
    {
        return day;
    }

    public static int getYear()
    {
        return year;
    }
    
    private static class MonthPanel extends JPanel 
    {        
        private final String monthNames[] = { "January", "February", "March", "April", "May", "June", "July", 
            "August", "September", "October", "November", "December"       };

        private final String dayNames[] = { "S", "M", "T", "W", "T", "F", "S"};
        
        private JPanel daysPanel, monthPanel;

        public MonthPanel(int month, int year) {
            CalendarPanel.month = month;
            CalendarPanel.year = year;

            add(createGUI());
        }

        protected final JPanel createGUI() {
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

        protected JPanel createTitleGUI() {
            final JPanel titlePanel = new JPanel(true);
            titlePanel.setBorder(BorderFactory
                    .createLineBorder(SystemColor.activeCaption));
            titlePanel.setLayout(new FlowLayout());
            titlePanel.setBackground(Color.WHITE);

            JLabel label = new JLabel(monthNames[month] + " " + year);
            label.setForeground(SystemColor.activeCaption);

            JButton leftButton = null, rightButton = null;
            try
            {
                leftButton = new JButton(new ImageIcon(ImageIO.read(PersonalHealthTracker.class.getResource("arrow-2-left.png"))));
                rightButton = new JButton(new ImageIcon(ImageIO.read(PersonalHealthTracker.class.getResource("arrow-2-right.png"))));
                
                leftButton.addActionListener((e) ->
                {
                    monthPanel.remove(daysPanel);
                    day = 1;
                    month--;
                    if (month < 0)
                    {
                        month += 12;
                        year--;
                    }
                    daysPanel = createDaysGUI();
                    monthPanel.add(daysPanel, BorderLayout.SOUTH);
                    
                    label.setText(monthNames[month] + " " + year);
                    
                    // Validate and repaint to make the changes visible to the user.
                    frame.pack();
                    validate();
                    repaint();
                });
                
                rightButton.addActionListener((e) ->
                {
                    monthPanel.remove(daysPanel);
                    day = 1;
                    month++;
                    if (month > 11)
                    {
                        month -= 12;
                        year++;
                    }
                    daysPanel = createDaysGUI();
                    monthPanel.add(daysPanel, BorderLayout.SOUTH);
                    
                    label.setText(monthNames[month] + " " + year);
                    
                    // Validate and repaint to make the changes visible to the user.
                    frame.pack();
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

        protected JPanel createDaysGUI() {
            JPanel dayPanel = new JPanel(true);
            dayPanel.setLayout(new GridLayout(0, dayNames.length));

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            Calendar iterator = (Calendar) calendar.clone();
            iterator.add(Calendar.DAY_OF_MONTH,
                    -(iterator.get(Calendar.DAY_OF_WEEK) - 1));

            Calendar maximum = (Calendar) calendar.clone();
            maximum.add(Calendar.MONTH, +1);

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


