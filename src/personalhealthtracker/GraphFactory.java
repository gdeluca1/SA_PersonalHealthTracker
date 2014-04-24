/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package personalhealthtracker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JPanel;
import models.Activity;
import models.ActivityModel;

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
    
    public static JPanel getLineChart(int selectedStat)
    {         
        return new JPanel()
        {

            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                
                Graphics2D g2 = (Graphics2D) g;
                BasicStroke stroke2 = new BasicStroke(2),
                        stroke6 = new BasicStroke(6);
                
                g2.setStroke(stroke2);
                
                int widthOffset = getWidth()/12;
                int heightOffset = getHeight()/9;
                
                int graphWidth = (int) (11 * widthOffset);
                int graphHeight = (int) (6 * heightOffset);
                
                g2.drawLine(widthOffset, heightOffset, widthOffset, graphHeight + heightOffset);
                g2.drawLine(widthOffset, graphHeight + heightOffset, graphWidth, graphHeight + heightOffset);
                
                // Create a fake list of points for testing. Make sure the list is sorted.
//                int dummyPoints[] = new int[]{24, 21, 3, 6, 2, 10, 25, 63};
//                int dummyPoints[] = new int[] {22, -1, -16, 0, 10, 20, 30, 42, 23, 14};
                ArrayList<Activity> dummyList = new ArrayList<>();
                ActivityModel.getInstance().getVisibleActivities()
                        .parallelStream()
                        .forEach((activity) ->
                        {
                            if (activity.getActivity() == selectedStat)
                            {
                                dummyList.add(activity);
                            }
                        });
                
//                for (int i : dummyPoints)
//                {
//                    dummyList.add(i);
//                }
                Collections.sort(dummyList, (a, b) ->
                {
                    int timeA = a.getHoursSpent() * 3600 + a.getMinutesSpent() * 60 + a.getSecondsSpent();
                    int timeB = b.getHoursSpent() * 3600 + b.getMinutesSpent() * 60 + b.getSecondsSpent();
                    
                    return Integer.compare(timeA, timeB);
                });
                
                // The max and min value in the list.
//                int max = dummyList.get(dummyList.size() - 1);
//                int min = dummyList.get(0);
                
                int n = dummyList.size();
                int pointOffsetX = (int) graphWidth/(n + 2);
                
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
                
                // Iterate through the list, graphing each point.
//                for (int i = 0; i < n; i++)
//                {
//                    previousPoint = currentPoint;
//                    
//                    xPosition = (i + 1) * pointOffsetX + widthOffset;
//                    
//                    p = dummyPoints[i];
//                    d1 = max - p;
//                    d2 = p - min; 
//                    yPosition = (d1 / (d1 + d2)) * validDistance + graphHeight / 20 + heightOffset;
//                    
//                    // Draw all the points.
//                    g2.setStroke(new BasicStroke(5));
//                    g2.drawLine((int) xPosition, (int) yPosition, (int) xPosition, (int) yPosition);
//                    
//                    currentPoint = new Pair<>(xPosition, yPosition);
//                    
//                    // If this isn't the first point, connect the dots.
//                    if (previousPoint != null)
//                    {
//                        g2.setStroke(new BasicStroke(3));
//                        g2.drawLine(previousPoint.getValue1().intValue(), previousPoint.getValue2().intValue(), 
//                                currentPoint.getValue1().intValue(), currentPoint.getValue2().intValue());
//                    }
//                }
            }
        };
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
    
    // This class represents a pair of any two values of the same type.
     private static class Pair<T>
        {
            private final T value1, value2;
            
            public Pair(T t1, T t2)
            {
                value1 = t1;
                value2 = t2;
            }
            
            public T getValue1()
            {
                return value1;
            }
            
            public T getValue2()
            {
                return value2;
            }
        }
}
