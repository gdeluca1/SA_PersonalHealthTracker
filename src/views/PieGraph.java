/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import models.Activity;
import models.ActivityModel;
import personalhealthtracker.GraphFactory;

/**
 *
 * @author Sumbhav
 */
public class PieGraph extends JPanel{
    
    public final int 
            CARDIO = 0,
            STRENGTH = 1,
            SEDENTARY = 2;
    
    public final double DEGREES_IN_CIRCLE = 360;
    
    private ArrayList<Activity> activities;
    private boolean autoUpdate;
    
    private String graphTitle;
    
    private double cardioTime,
                strengthTime,
                sedentaryTime,
                totalTime;
    
    private double cardioPercentage,
                    strengthPercentage,
                    sedentaryPercentage;
    
    private boolean update;
    
    public PieGraph(ArrayList<Activity> activities, boolean autoUpdate, String title){
        
        super();
        
        if (autoUpdate)
        {
            title = "Auto Updating";
        }
        
        this.activities = activities;
        this.autoUpdate = autoUpdate;
        graphTitle = title;
        
        cardioTime = 0;
        strengthTime = 0;
        sedentaryTime = 0;
        totalTime = 0;
        
        cardioPercentage = 0;
        strengthPercentage = 0;
        sedentaryPercentage = 0;
        update = true;
        
    }
    
    public ArrayList<Activity> getActivities()
    {
        return activities;
    }

    public boolean isAutoUpdate()
    {
        return autoUpdate;
    }
    
    public String getTitle(){
        return graphTitle;
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        setPreferredSize(new Dimension(300,375));
        
        
        //If autoupdate is enabled, these values need to be reset in order to produce an accurate graph
        if(autoUpdate){
            activities = ActivityModel.getInstance().getVisibleActivities();
            cardioTime = 0;
            strengthTime = 0;
            sedentaryTime = 0;
            totalTime = 0;
            cardioPercentage = 0;
            strengthPercentage = 0;
            sedentaryPercentage = 0;
            update = true;
        }
        
        //If the stats used to create the graph haven't already been generated, generate them
        if(update){
            
            update = false;
            
            //Search the list of activities for the time spent and activity type
            activities
                .parallelStream()
                .forEach((activity) ->
                {
                   switch(activity.getActivity()){
                        case CARDIO:
                            cardioTime += GraphFactory.getSecondsSpent(activity);
                            break;
                        case STRENGTH:
                            strengthTime += GraphFactory.getSecondsSpent(activity);
                            break;
                        case SEDENTARY:
                            sedentaryTime += GraphFactory.getSecondsSpent(activity);
                            break;
                        default:
                            break; 
                   }
                });
        
        totalTime = cardioTime + strengthTime + sedentaryTime;
        
        cardioPercentage = cardioTime/totalTime;
        strengthPercentage = strengthTime/totalTime;
        sedentaryPercentage = sedentaryTime/totalTime;
        
        }
        
        //Unit of measurement for creating the graph
        int widthSegment = getWidth()/12;
        int heightSegment = getHeight()/9;
        
        
        //Draw the portion of the pie for the time spent on cardio
        g.setColor(Color.RED);
        g.fillArc(2*widthSegment, 2*heightSegment, 3*heightSegment, 3*heightSegment, 0, (int) (cardioPercentage * DEGREES_IN_CIRCLE + 1));
        g.fillRect((int) (1.7*widthSegment), (int) (5.7*heightSegment), widthSegment/7, widthSegment/7);
        g.drawString("= Time spent doing Cardio", 2*widthSegment, (int) (5.8*heightSegment));
        
        //Draw the portion of the pie for the time spent on strength
        g.setColor(Color.BLUE);
        g.fillArc(2*widthSegment, 2*heightSegment, 3*heightSegment, 3*heightSegment, (int) (cardioPercentage * DEGREES_IN_CIRCLE + 1), (int) (strengthPercentage * DEGREES_IN_CIRCLE + 1));
        g.fillRect((int) (1.7*widthSegment), (int) (6*heightSegment), widthSegment/7, widthSegment/7);
        g.drawString("= Time spent doing Strength", 2*widthSegment, (int) (6.1*heightSegment));
        
        //Draw the portion of the pie for the time spent being sedentary
        g.setColor(Color.BLACK);
        g.fillArc(2*widthSegment, 2*heightSegment, 3*heightSegment, 3*heightSegment, ((int) (strengthPercentage * DEGREES_IN_CIRCLE) + (int) (cardioPercentage * DEGREES_IN_CIRCLE) + 1), (int) (sedentaryPercentage * DEGREES_IN_CIRCLE));
        g.fillRect((int) (1.7*widthSegment), (int) (6.3*heightSegment), widthSegment/7, widthSegment/7);
        g.drawString("= Time spent being sedentary", 2*widthSegment, (int) (6.4*heightSegment));
        
        //Draw Graph Title
        g.setFont(new Font("TimesRoman", Font.BOLD, (int) (getWidth()/35 + 1)));
        
        if (autoUpdate) g.setColor(Color.BLUE);
        g.drawString("Time Spent per Activity Type: " + graphTitle, widthSegment, (int) (.75*heightSegment));
        g.setColor(Color.BLACK);
        
        
    }
    
}
