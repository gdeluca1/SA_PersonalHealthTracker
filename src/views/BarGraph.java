/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import models.Activity;

/**
 *
 * @author Sumbhav
 */
public class BarGraph extends JPanel{
    
    private ArrayList<Activity> activities;
    private boolean update;
    
    public BarGraph(ArrayList<Activity> activities){
        super();
        this.activities = activities;
        update = true;
    }
    
    @Override
    protected void paintComponent(Graphics g){
        
        super.paintComponent(g);
        
        if(update){
            activities
                    .parallelStream()
                    .forEach((activity) ->
                    {
                        
                    });
        }
    }
    
}
