package personalhealthtracker;

import java.awt.Graphics;
import javax.swing.JPanel;
import models.Activity;
import models.ActivityModel;
import views.BarGraph;
import views.LineGraph;
import views.PieGraph;

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
    
    public static JPanel getBarChart(boolean autoUpdate, String graphTitle)
    {
        return new BarGraph(ActivityModel.getInstance().getVisibleActivities(), autoUpdate, graphTitle);
    }
    
    public static JPanel getPieChart(boolean autoUpdate, String graphTitle){
        return new PieGraph(ActivityModel.getInstance().getVisibleActivities(), autoUpdate, graphTitle);
    }
}
