/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JPanel;
import models.Activity;
import models.ActivityModel;
import static personalhealthtracker.GraphFactory.getSecondsSpent;
import personalhealthtracker.Pair;

public class LineGraph extends JPanel
{
    private ArrayList<Activity> activities,
            reducedList = null,
            sortedList = null;
    private final int selectedStat;
    private final boolean autoUpdate;
    
    public LineGraph(ArrayList<Activity> activities, int selectedStat, boolean autoUpdate)
    {
        super();
        this.activities = activities;
        this.selectedStat = selectedStat;
        this.autoUpdate = autoUpdate;
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        // If the user wants their graph auto updated, fetch the activity list each repaint.
        if (autoUpdate)
        {
            activities = ActivityModel.getInstance().getVisibleActivities();
            reducedList = null;
            sortedList = null;
        }
        
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

        // This list will store a filtered version of the list, matching the activities which the user wants.
        if (reducedList == null) reducedList = new ArrayList<>();

        if (reducedList.isEmpty())
            activities
                    .stream()
                    .forEach((activity) ->
                    {
                        if (activity.getActivity() == selectedStat)
                        {
                            reducedList.add(activity);
                        }
                    });
        
        Collections.sort(reducedList, (a, b) ->
        {
            return a.getTimeStamp().compareTo(b.getTimeStamp());
        });
        
        // If we try to paint anything when there's nothing, we'll get null pointer exceptions.
        if (reducedList.isEmpty())
        {
            return;
        }

        // Create a parallel list to store the values in a sorted order.
        if (sortedList == null) sortedList = (ArrayList<Activity>) reducedList.clone();
        
//        System.out.println(sortedList.size());
//        long count = sortedList
//                .parallelStream()
//                .filter((activity) ->
//                {
//                    boolean b = (activity == null);
//                    if (b == true) System.out.println(sortedList.indexOf(activity));
//                    return b;
//                })
//                .count();
//        System.out.println(count + " null.");
//        System.out.println();

        // Sort by time spent.
        Collections.sort(sortedList, (a, b) ->
        {
            int timeA = getSecondsSpent(a);
            int timeB = getSecondsSpent(b);

            return Integer.compare(timeA, timeB);
        });

        // The max and min value in the list.
        int max = getSecondsSpent(sortedList.get(sortedList.size() - 1));
        int min = getSecondsSpent(sortedList.get(0));

        int n = sortedList.size();
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

        // Iterate through the list, graphing each point.
        for (int i = 0; i < n; i++)
        {
            previousPoint = currentPoint;

            xPosition = (i + 1) * pointOffsetX + widthOffset;

            p = getSecondsSpent(reducedList.get(i));
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
        }
    }
}
