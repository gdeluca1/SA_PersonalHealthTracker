/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package personalhealthtracker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import models.Activity;
import models.ActivityModel;
import views.LineGraph;

/**
 *
 * @author Gennaro
 */
public class GraphFactory
{
    /**
     * Returns the number of seconds for which an activity was performed.
     * @param activity The activity.
     * @return Time in seconds.
     */
    public static int getSecondsSpent(Activity activity)
    {
        return activity.getHoursSpent() * 3600 + activity.getMinutesSpent() * 60 + activity.getSecondsSpent();
    }
        
    /**
     * Creates a pie chart from the data in the activity list.
     * @return A JPanel with the pie chart drawn on it.
     */
    public static JPanel getPieChart()
    {
        return new JPanel()
        {

            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                // Can get list of activities with: ActivityModel.getInstance().getActivities();
            }
        };
    }
    
    public static JPanel getLineChart(int selectedStat, boolean autoUpdate, String graphTitle)
    {         
        return new LineGraph(ActivityModel.getInstance().getVisibleActivities(), selectedStat, autoUpdate, graphTitle);
    }
    
    public static JPanel getBarChart()
    {
        return new JPanel()
        {

            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                int widthSegment = getWidth()/12;
                int heightSegment = getHeight()/9;
                
                g.drawLine(widthSegment, heightSegment, widthSegment, 7*heightSegment);
                g.drawLine(widthSegment, 7*heightSegment, (int) (11.5*widthSegment), 7*heightSegment);
                
                g.fillRect(2*widthSegment+widthSegment/2, 4*heightSegment, widthSegment/2, 3*heightSegment);
                g.fillRect(4*widthSegment+widthSegment/2, 4*heightSegment, widthSegment/2, 3*heightSegment);
                g.fillRect(6*widthSegment+widthSegment/2, 4*heightSegment, widthSegment/2, 3*heightSegment);
                g.fillRect(8*widthSegment+widthSegment/2, 4*heightSegment, widthSegment/2, 3*heightSegment);
                g.fillRect(10*widthSegment+widthSegment/2, 4*heightSegment, widthSegment/2, 3*heightSegment);
                
                g.setColor(Color.BLUE);
                double user = 110;
                double optimal = 100;
                double boxadder = (user-optimal)/optimal;
                g.fillRect(2*widthSegment, (int) (4*heightSegment-5*boxadder*heightSegment), widthSegment/2, (int) (3*heightSegment+5*boxadder*heightSegment+1));
                g.fillRect(4*widthSegment, 4*heightSegment, widthSegment/2, 3*heightSegment);
                g.fillRect(6*widthSegment, 4*heightSegment, widthSegment/2, 3*heightSegment);
                g.fillRect(8*widthSegment, 4*heightSegment, widthSegment/2, 3*heightSegment);
                g.fillRect(10*widthSegment, 4*heightSegment, widthSegment/2, 3*heightSegment);
                
                g.setFont(new Font("TimesRoman", Font.PLAIN, (int) (getWidth()/70 + 1)));
                
                g.fillRect(widthSegment, (int) (8.5*heightSegment), widthSegment/12, widthSegment/12);
                g.drawString(" = The user's value compared to the optimal", widthSegment + widthSegment/12, (int) (8.5*heightSegment+widthSegment/12));
                g.setColor(Color.BLACK);
                g.fillRect(widthSegment, (int) (8.8*heightSegment), widthSegment/12, widthSegment/12);
                g.drawString(" = The optimal value compared to the optimal", widthSegment + widthSegment/12, (int) (8.8*heightSegment+widthSegment/12));
                
                g.drawString("Time spent on", 2*widthSegment, (int) (7.2*heightSegment));
                g.drawString("activities", 2*widthSegment, (int) (7.35*heightSegment));
                g.drawString("Blood Sugar", 4*widthSegment, (int) (7.2*heightSegment));
                g.drawString("Resting Pulse", 6*widthSegment, (int) (7.2*heightSegment));
                g.drawString("Active Pulse", 8*widthSegment, (int) (7.2*heightSegment));
                g.drawString("Blood Pressure", 10*widthSegment, (int) (7.2*heightSegment));
                
                g.setFont(new Font("TimesRoman", Font.PLAIN, (int) (getWidth()/40 + 1)));
                g.drawString("%", widthSegment/2, 4*heightSegment);
                
                g.setFont(new Font("TimesRoman", Font.BOLD, (int) (getWidth()/20 + 1)));
                g.drawString("Comparisons to Optimal Values", 2*widthSegment, heightSegment/2);
            }
            
        };
    }
}
