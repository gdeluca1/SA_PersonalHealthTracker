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
    private static int cardio, strength, sedentary;   //variables to record number of activities
    public static void activity()
    {   //array of user activities
        ArrayList<Activity> activities = ActivityModel.getInstance().getActivities();
        int index=0;
        cardio =0;
        strength = 0;
        sedentary = 0;
        while(index < activities.size())    //goes through activity array and retrieves activities
        {
            Activity log = activities.get(index);
            if(log.getActivity() == 0)  //if the activity is 0, it is a cardio activity
                cardio++;        
            else if(log.getActivity() == 1) //if the activity is 1, it is a strength activity
                strength++;
            else if(log.getActivity() == 2) //if the activity is 2, it is a sedentary activity
                sedentary++;    
            //appropriate activity is incremented 
            
            index++;           
        }
        
    }
    private static void drawPieChart(Graphics g, int x, int y, int r) {
                      
               int total = cardio + strength + sedentary;
               int fromDegree = 0;
               
               if(total > 0)
               {
                   int degrees;
                   g.setColor(Color.RED);   //sets cardio section to red
                   degrees = countToDegrees(cardio, total); //determines the degree to which cardio uses
                   drawSector(g, x, y, r, fromDegree, degrees); //draws cardio section
                   fromDegree += degrees;   //sets fromDegree to start where the degrees ended
                   
                   g.setColor(Color.GREEN); //sets strength section to green
                   degrees = countToDegrees(strength, total);
                   drawSector(g, x, y, r, fromDegree, degrees);
                   fromDegree += degrees;
                   
                   g.setColor(Color.BLUE);  //sets sedentary section to blue
                   degrees = Math.max(360 - fromDegree, 0); //fills in remaining section
                   drawSector(g, x, y, r, fromDegree, degrees);
               }
               else
               {
                   g.setColor(Color.LIGHT_GRAY);    //sets whole pie chart to gray
                   drawSector(g, x, y, r, fromDegree, 360);
                   
               }
            }
                 
            private static int countToDegrees(int count, int total)
            {   //takes activity number and total to determine percentage/degree for chart
                return (int)((360.0 + count) / (total + 0.5));
            }
            
            private static void drawSector(Graphics g, int x, int y, int r, int fromDegree, int degrees)
            {   //draws the section to corresponding activity
                if(degrees > 359)
                    g.fillOval(x-r, y-r, 2*r, 2*r); //fills entire pie chart if degree > 359
                else
                    g.fillArc(x-r, y-r, 2*r, 2*r, fromDegree, degrees); //fills sector 
            }
            
            private static void drawLegend(Graphics g, int x, int y, int r)
            {   //creates legend for pie chart 
                y += (r + 20);
                g.setColor(Color.BLACK);    //sets font as black
                
                //draws strings of activity and prints number of activity
                g.drawString("Cardio" + cardio, x-r, y);
                g.drawString("Strength" + strength, x, y);
                g.drawString("Sedentary" + sedentary, x+r, y);
                
                y+=5;
                x -=2;
                g.setColor(Color.RED);  //creates red rectangle for cardio
                g.fillRect(x-r, y, 10, 10);
                g.setColor(Color.GREEN);    //creates green rectangle for strength
                g.fillRect(x, y, 10, 10);
                g.setColor(Color.BLUE); //creates blue rectangle for sedentary
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
                setBackground(Color.WHITE);
                
                int w = getWidth();
                int h = getHeight();
                int x = w/2;
                int y = h/2;
                int r = Math.min(w,h) / 4;
                activity(); //gets activity information
                drawPieChart(g,x,y,r);  //draws pie chart
                drawLegend(g,x,y,r);    //draws legend
                // Can get list of activities with: ActivityModel.getInstance().getActivities();
            }          
        };
    }
}
