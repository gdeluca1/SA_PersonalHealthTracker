/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package personalhealthtracker;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import models.ActivityModel;
import models.Activity;


/**
 *
 * @author Gennaro
 */
public class GraphFactory extends JPanel
{
    /**
     * Creates a pie chart from the data in the activity list.
     * @return A JPanel with the pie chart drawn on it.
     */
    private static int cardio, strength, sedentary;
    public GraphFactory()
    {
        setBackground(Color.WHITE);
        cardio = 0;
        strength = 0;
        sedentary = 0;
        
    }
    public void activity()
    {
        ArrayList<Activity> activities = ActivityModel.getInstance().getActivities();
        int index=0;
        while(index < activities.size())
        {
            Activity log = activities.get(index);
            if(log.getActivity() == 0)
                cardio++;        
            else if(log.getActivity() == 1)
                strength++;
            else if(log.getActivity() == 2)
                sedentary++;
            
            
        }
        
    }
    private static void drawPieChart(Graphics g, int x, int y, int r) {
                      
               int total = cardio + strength + sedentary;
               int fromDegree = 0;
               
               if(total > 0)
               {
                   int degrees;
                   g.setColor(Color.RED);
                   degrees = countToDegrees(GraphFactory.cardio, total);
                   drawSector(g, x, y, r, fromDegree, degrees);
                   fromDegree += degrees;
                   
                   g.setColor(Color.GREEN);
                   degrees = countToDegrees(GraphFactory.strength, total);
                   drawSector(g, x, y, r, fromDegree, degrees);
                   fromDegree += degrees;
                   
                   g.setColor(Color.BLUE);
                   degrees = Math.max(360 - fromDegree, 0);
                   drawSector(g, x, y, r, fromDegree, degrees);
               }
               else
               {
                   g.setColor(Color.LIGHT_GRAY);
                   drawSector(g, x, y, r, fromDegree, 360);
                   
               }
            }
                 
            private static int countToDegrees(int count, int total)
            {
                return (int)((360.0 + count) / (total + 0.5));
            }
            
            private static void drawSector(Graphics g, int x, int y, int r, int fromDegree, int degrees)
            {
                if(degrees > 359)
                    g.fillOval(x-r, y-r, 2*r, 2*r);
                else
                    g.fillArc(x-r, y-r, 2*r, 2*r, fromDegree, degrees);
            }
            
            private static void drawLegend(Graphics g, int x, int y, int r)
            {
                y += (r + 20);
                g.setColor(Color.BLACK);
                
                g.drawString("Cardio" + cardio, x-r, y);
                g.drawString("Strength" + strength, x, y);
                g.drawString("Sedentary" + sedentary, x+r, y);
                
                y+=5;
                x -=2;
                g.setColor(Color.RED);
                g.fillRect(x-r, y, 10, 10);
                g.setColor(Color.GREEN);
                g.fillRect(x, y, 10, 10);
                g.setColor(Color.BLUE);
                g.fillRect(x+r,y,10,10);
            }
    public static JPanel getPieChart()
    {

        return new JPanel()
        {
            
            @Override
            protected void paintComponent(Graphics g)
            {                 
                super.paintComponent(g);
                
                int w = getWidth();
                int h = getHeight();
                int x = w/2;
                int y = h/2;
                int r = Math.min(w,h) / 4;
                drawPieChart(g,x,y,r);
                drawLegend(g,x,y,r);
                // Can get list of activities with: ActivityModel.getInstance().getActivities();
            }          
        };
    }
}
