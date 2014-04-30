package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.swing.JPanel;
import models.Activity;
import models.ActivityModel;
import static personalhealthtracker.GraphFactory.getSecondsSpent;
import personalhealthtracker.Pair;

public class LineGraph extends JPanel
{
    private ArrayList<Activity> activities;
    private TreeMap<Date, Integer> timePerDay;
    private final int selectedStat;
    private final boolean autoUpdate;
    private final String title;
    
    public LineGraph(ArrayList<Activity> activities, int selectedStat, boolean autoUpdate, String graphTitle)
    {
        super();
        
        if (autoUpdate)
        {
            switch (selectedStat)
            {
                case Activity.ActivityType.CARDIO:
                    graphTitle = "Auto Updating - Cardio";
                    break;
                case Activity.ActivityType.SEDENTARY:
                    graphTitle = "Auto Updating - Sedentary";
                    break;
                case Activity.ActivityType.STRENGTH:
                    graphTitle = "Auto Updating - Strength";
                    break;
                default:
                    graphTitle = "Unknown Graph Type";
            }
        }
        
        this.activities = activities;
        this.selectedStat = selectedStat;
        this.autoUpdate = autoUpdate;
        this.title = graphTitle;
    }

    public ArrayList<Activity> getActivities()
    {
        return activities;
    }

    public int getSelectedStat()
    {
        return selectedStat;
    }

    public boolean isAutoUpdate()
    {
        return autoUpdate;
    }

    public String getTitle()
    {
        return title;
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        // If the user wants their graph auto updated, fetch the activity list each repaint.
        if (autoUpdate)
        {
            activities = ActivityModel.getInstance().getVisibleActivities();
            timePerDay = null;
        }
        
        Graphics2D g2 = (Graphics2D) g;
        BasicStroke stroke2 = new BasicStroke(2),
                stroke6 = new BasicStroke(6);

        g2.setStroke(stroke2);

        int widthOffset = getWidth()/12;
        int heightOffset = getHeight()/9;

        int graphWidth = (int) (11 * widthOffset);
        int graphHeight = (int) (6 * heightOffset);

        // Draw the axes.
        g2.drawLine(widthOffset, heightOffset, widthOffset, graphHeight + heightOffset);
        g2.drawLine(widthOffset, graphHeight + heightOffset, graphWidth, graphHeight + heightOffset);
        
        g2.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        
         // Label the axes.
        String time = "Time";
        
        
        AffineTransform orig = g2.getTransform();
        g2.rotate(-Math.PI/2, 3 * widthOffset/4, 4 * heightOffset);
        g2.drawString(time, 3 * widthOffset/4, 4 * heightOffset);
        g2.setTransform(orig);
        
        String date = "Date";
        g2.drawString(date, graphWidth/2, (int)(graphHeight + 1.5 * heightOffset));
        
        if (autoUpdate) g2.setColor(Color.BLUE);
        
        // Use FontMetrics to draw the string in the middle of the panel.
        FontMetrics fm = g2.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds(title, g2);
        g2.drawString(title, (int) (graphWidth/2 - rect.getWidth()/2), (int) (heightOffset/2 + rect.getHeight()/2));
        g2.setColor(Color.BLACK);

        // This map stores the amount of time the user worked out each day.
        if (timePerDay == null)        
            timePerDay = new TreeMap<>((date1, date2) ->
            {
                return date1.compareTo(date2);
            });
        
        // Only add values for the specified activity.
        if (timePerDay.isEmpty())
            activities
                    .stream()
                    .forEach((activity) ->
                    {
                        if (activity.getActivity() == selectedStat)
                        {
                            // If time has already been done that day, add to it, don't replace it.
                            Integer currentTime = timePerDay.get(activity.getTimeStamp());
                            int offset = 0;
                            if (currentTime != null) offset = currentTime;
                            
                            timePerDay.put(activity.getTimeStamp(), offset + getSecondsSpent(activity));
                        }
                    });
        
        // If we try to paint anything when there's nothing, we'll get null pointer exceptions.
        if (timePerDay.isEmpty())
        {
            return;
        }

        // The max and min value in the list.
        int max = Collections.max(timePerDay.values());
        int min = Collections.min(timePerDay.values());

        int n = timePerDay.size();
        double pointOffsetX = graphWidth/(n + 1);

        int validDistance = (int) 18 * graphHeight / 20;
        double p, d1, d2, yPosition, xPosition;

        Pair<Double> previousPoint, currentPoint = null;

        // Draw margins to show where the min/max points are.
        BasicStroke drawingStroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        Line2D maxLine = new Line2D.Double(widthOffset, heightOffset + 0.05 * graphHeight, graphWidth, 
                heightOffset + 0.05 * graphHeight);
        Line2D minLine = new Line2D.Double(widthOffset, heightOffset + 0.95 * graphHeight, graphWidth, 
                heightOffset + 0.95 * graphHeight);
        g2.setStroke(drawingStroke);
        g2.setColor(Color.GRAY);
        g2.draw(maxLine);
        g2.draw(minLine);
        g2.setColor(Color.BLACK);

        int i = 0;
        // Iterate through the list and draw each point.
        for (Entry<Date, Integer> entry : timePerDay.entrySet())
        {
            previousPoint = currentPoint;

            xPosition = (i + 1) * pointOffsetX + widthOffset;

            p = entry.getValue();
            d1 = max - p;
            d2 = p - min; 

            if (d1 != d2)
                yPosition = (d1 / (d1 + d2)) * validDistance + graphHeight / 20 + heightOffset;
            else
                yPosition = graphHeight / 20 + heightOffset;

            // Draw all the points.
            g2.setStroke(stroke6);
            g2.drawLine((int) xPosition, (int) yPosition, (int) xPosition, (int) yPosition);

            currentPoint = new Pair<>(xPosition, yPosition);

            // If this isn't the first point, connect the dots.
            if (previousPoint != null)
            {
                g2.setStroke(stroke2);
                g2.drawLine(previousPoint.getValue1().intValue(), previousPoint.getValue2().intValue(), 
                        currentPoint.getValue1().intValue(), currentPoint.getValue2().intValue());
            }
            
            // Keep track of which point number this is.
            i++;
        }
    }
}
