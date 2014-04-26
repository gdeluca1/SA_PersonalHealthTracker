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
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Optional;
import javax.swing.JPanel;
import models.Activity;
import models.ActivityModel;
import models.ProfileModel;
import personalhealthtracker.GraphFactory;

/**
 *
 * @author Sumbhav
 */
public class BarGraph extends JPanel{
    
    public final int 
            CARDIO = 0,
            STRENGTH = 1,
            SEDENTARY = 2;
    
    public final int 
            WEEKLY = -1, 
            MONTHLY = 0, 
            YEARLY = 1;
    
    public final int
            BLOOD_SUGAR_MIN = 70,
            BLOOD_SUGAR_MAX = 100,
            SEDENTARY_PULSE_MIN = 60,
            SEDENTARY_PULSE_MAX = 100,
            ACTIVE_PULSE_MIN = (int) (activePulse()*.75),
            ACTIVE_PULSE_MAX = (int) (activePulse()*.75),
            TIME_SPENT_DAILY = 3600, //Amount of time you should be active per day in seconds
            BLOOD_PRESSURE_SYSTOLIC = 120,
            BLOOD_PRESSURE_DYSTOLIC = 80;
            
    
    private ArrayList<Activity> activities;
    private boolean autoUpdate;
    private boolean update;
    
    private String graphTitle;
    
    double averageActivePulse,
        averageRestingPulse,
        averageTimeSpent,
        averageBloodSugar,
        averageBloodPressureSystolic,
        averageBloodPressureDystolic,
        physicalActivities,
        sedentaryActivities,
        bloodSugarRecorded,
        systolicRecorded,
        dystolicRecorded;
    
    double boxAdderSystolic,
            boxAdderDystolic,
            boxAdderSedentaryPulse,
            boxAdderActivePulse,
            boxAdderBloodSugar,
            boxAdderTimeSpent;
    
    public BarGraph(ArrayList<Activity> activities, boolean autoUpdate, String title){
        super();
        this.activities = activities;
        this.autoUpdate = autoUpdate;
        averageActivePulse = 0;
        averageRestingPulse = 0;
        averageTimeSpent = 0;
        averageBloodSugar = 0;
        averageBloodPressureSystolic = 0;
        averageBloodPressureDystolic = 0;
        physicalActivities = 0;
        sedentaryActivities = 0;
        bloodSugarRecorded = 0;
        systolicRecorded = 0;
        dystolicRecorded = 0;
        update = true;
        graphTitle = title;
        
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
        
        setPreferredSize(new Dimension(300,600));
        
        //If autoupdate is enabled, these values need to be reset to calcualte new statistics
        if(autoUpdate){

                activities = ActivityModel.getInstance().getVisibleActivities();
                averageActivePulse = 0;
                averageRestingPulse = 0;
                averageTimeSpent = 0;
                averageBloodSugar = 0;
                averageBloodPressureSystolic = 0;
                averageBloodPressureDystolic = 0;
                physicalActivities = 0;
                sedentaryActivities = 0;
                bloodSugarRecorded = 0;
                systolicRecorded = 0;
                dystolicRecorded = 0;
                update = true;
                
            }
        
        if(!activities.isEmpty()){
            if(update){
                
                update = false;
                
                //Search the list of activities for the various statistics, and compile them into averages used to create the bars for the graph
                activities
                        .parallelStream()
                        .forEach((activity) ->
                        {
                            if(activity.getBloodPressureSystolic() > 0){
                                averageBloodPressureSystolic += activity.getBloodPressureSystolic();
                                systolicRecorded++;
                            }
                                
                            if(activity.getBloodPressureDystolic() > 0){
                                averageBloodPressureDystolic += activity.getBloodPressureDystolic();
                                dystolicRecorded++;
                            }
                            
                            if(activity.getBloodSugar() > 0){
                                averageBloodSugar += activity.getBloodSugar();
                                bloodSugarRecorded++;
                            }

                            switch(activity.getActivity()){
                                case CARDIO:
                                    if(activity.getPulse() > 0){
                                        averageActivePulse += activity.getPulse();
                                        physicalActivities++;
                                    }
                                    if(GraphFactory.getSecondsSpent(activity) > 0){
                                        averageTimeSpent += GraphFactory.getSecondsSpent(activity);
                                    }
                                    break;
                                case STRENGTH:
                                    if(activity.getPulse() > 0){
                                        averageActivePulse += activity.getPulse();
                                        physicalActivities++;
                                    }
                                    if(GraphFactory.getSecondsSpent(activity) > 0){
                                        averageTimeSpent += GraphFactory.getSecondsSpent(activity);
                                    }
                                    break;
                                case SEDENTARY:
                                    if(activity.getPulse() > 0){
                                        averageRestingPulse += activity.getPulse();
                                        sedentaryActivities++;
                                    }
                                    break;
                                default:
                                    break;
                            }

                        });
                
                
                //Compute the averages for each statistic
                averageBloodPressureSystolic /= activities.size();
                averageBloodPressureDystolic /= activities.size();
                averageBloodSugar /= activities.size();
                if(physicalActivities > 0){
                    averageActivePulse /= physicalActivities;
                }
                if(sedentaryActivities > 0){
                    averageRestingPulse /= sedentaryActivities;
                }
                
                
                //Calculate the size of the rectangle that needs to be added to the standard size of a bar (the size of the bar for the optimal value of a stat)
                //in order to accurately reflect the difference between the user value and the optimal value
                boxAdderSystolic = (averageBloodPressureSystolic-BLOOD_PRESSURE_SYSTOLIC)/BLOOD_PRESSURE_SYSTOLIC;
//                System.out.println("BloodPressureSystolic: " + boxAdderSystolic);
                boxAdderDystolic = (averageBloodPressureDystolic-BLOOD_PRESSURE_DYSTOLIC)/BLOOD_PRESSURE_DYSTOLIC;
//                System.out.println("BloodPressureDystolic: " + boxAdderDystolic);
                
                boxAdderBloodSugar = 0;
                if(averageBloodSugar < BLOOD_SUGAR_MIN){
                    boxAdderBloodSugar = (averageBloodSugar - BLOOD_SUGAR_MIN)/BLOOD_SUGAR_MIN;
                }
                else if(averageBloodSugar > BLOOD_SUGAR_MAX){
                    boxAdderBloodSugar = (averageBloodSugar - BLOOD_SUGAR_MAX)/BLOOD_SUGAR_MAX;
                }
                
                boxAdderActivePulse = 0;
                if(averageActivePulse < ACTIVE_PULSE_MIN){
                    boxAdderActivePulse = (averageActivePulse - ACTIVE_PULSE_MIN)/ACTIVE_PULSE_MIN;
                }
                else if(averageActivePulse > ACTIVE_PULSE_MAX){
                    boxAdderActivePulse = (averageActivePulse - ACTIVE_PULSE_MAX)/ACTIVE_PULSE_MAX;
                }
                
                boxAdderSedentaryPulse = 0;
                if(averageRestingPulse < SEDENTARY_PULSE_MIN){
                    boxAdderSedentaryPulse = (averageRestingPulse - SEDENTARY_PULSE_MIN)/SEDENTARY_PULSE_MIN;
                }
                else if(averageRestingPulse > SEDENTARY_PULSE_MAX){
                    boxAdderSedentaryPulse = (averageRestingPulse - SEDENTARY_PULSE_MAX)/SEDENTARY_PULSE_MAX;
                }
                
                if(HealthTrackerView.getViewMode() == WEEKLY){
                    boxAdderTimeSpent = (averageTimeSpent - 7*TIME_SPENT_DAILY)/(7*TIME_SPENT_DAILY);
                }
                else if(HealthTrackerView.getViewMode() == MONTHLY){
                    boxAdderTimeSpent = (averageTimeSpent - 31*TIME_SPENT_DAILY)/(31*TIME_SPENT_DAILY);
                }
                else{
                    boxAdderTimeSpent = (averageTimeSpent - 365*TIME_SPENT_DAILY)/(365*TIME_SPENT_DAILY);
                }
                
                
                //Ensure that bars never get so big they leave the graph area
                if(boxAdderSystolic > 1){
                    boxAdderSystolic = 1;
                }
                
                if(boxAdderDystolic > 1){
                    boxAdderDystolic = 1;
                }
                
                if(boxAdderBloodSugar > 1){
                    boxAdderBloodSugar = 1;
                }
                
                if(boxAdderTimeSpent > 1){
                    boxAdderTimeSpent = 1;
                }
                
                if(boxAdderActivePulse > 1){
                    boxAdderActivePulse = 1;
                }
                
                if(boxAdderSedentaryPulse > 1){
                    boxAdderSedentaryPulse = 1;
                }
                    
            }
                
                //Standard units of measurement
                int widthSegment = getWidth()/14;
                int heightSegment = getHeight()/9;
                
                //Draw the axes
                g.drawLine(widthSegment, heightSegment, widthSegment, 7*heightSegment);
                g.drawLine(widthSegment, 7*heightSegment, (int) (13.5*widthSegment), 7*heightSegment);
                
                //Draw the bars representing the optimal value for each stat
                g.fillRect(2*widthSegment+widthSegment/2, 4*heightSegment, widthSegment/2, 3*heightSegment);
                g.fillRect(4*widthSegment+widthSegment/2, 4*heightSegment, widthSegment/2, 3*heightSegment);
                g.fillRect(6*widthSegment+widthSegment/2, 4*heightSegment, widthSegment/2, 3*heightSegment);
                g.fillRect(8*widthSegment+widthSegment/2, 4*heightSegment, widthSegment/2, 3*heightSegment);
                g.fillRect(10*widthSegment+widthSegment/2, 4*heightSegment, widthSegment/2, 3*heightSegment);
                g.fillRect(12*widthSegment+widthSegment/2, 4*heightSegment, widthSegment/2, 3*heightSegment);
                
                //Draw the bars representing the user's difference from the optimal values
                g.setColor(Color.BLUE);
                g.fillRect(2*widthSegment, (int) (4*heightSegment-3*boxAdderTimeSpent*heightSegment), widthSegment/2, (int) (3*heightSegment+3*boxAdderTimeSpent*heightSegment+1));
                g.fillRect(4*widthSegment, (int) (4*heightSegment-3*boxAdderBloodSugar*heightSegment), widthSegment/2, (int) (3*heightSegment+3*boxAdderBloodSugar*heightSegment+1));
                g.fillRect(6*widthSegment, (int) (4*heightSegment-3*boxAdderSedentaryPulse*heightSegment), widthSegment/2, (int) (3*heightSegment+3*boxAdderSedentaryPulse*heightSegment+1));
                g.fillRect(8*widthSegment, (int) (4*heightSegment-3*boxAdderActivePulse*heightSegment), widthSegment/2, (int) (3*heightSegment+3*boxAdderActivePulse*heightSegment+1));
                g.fillRect(10*widthSegment, (int) (4*heightSegment-3*boxAdderSystolic*heightSegment), widthSegment/2, (int) (3*heightSegment+3*boxAdderSystolic*heightSegment+1));
                g.fillRect(12*widthSegment, (int) (4*heightSegment-3*boxAdderDystolic*heightSegment), widthSegment/2, (int) (3*heightSegment+3*boxAdderDystolic*heightSegment+1));
                
                //Draw the Legend
                g.setFont(new Font("TimesRoman", Font.PLAIN, (int) (getWidth()/70 + 1)));
                
                g.fillRect(widthSegment, (int) (8.5*heightSegment), widthSegment/12, widthSegment/12);
                g.drawString(" = The user's value compared to the optimal", widthSegment + widthSegment/12, (int) (8.5*heightSegment+widthSegment/12));
                g.setColor(Color.BLACK);
                g.fillRect(widthSegment, (int) (8.8*heightSegment), widthSegment/12, widthSegment/12);
                g.drawString(" = The optimal value compared to the optimal", widthSegment + widthSegment/12, (int) (8.8*heightSegment+widthSegment/12));
                
                //Text explaning what stat each set of bars represents
                g.drawString("Time spent on", 2*widthSegment, (int) (7.3*heightSegment));
                g.drawString("activities", 2*widthSegment, (int) (7.55*heightSegment));
                g.drawString("Blood Sugar", 4*widthSegment, (int) (7.3*heightSegment));
                g.drawString("Resting Pulse", 6*widthSegment, (int) (7.3*heightSegment));
                g.drawString("Active Pulse", 8*widthSegment, (int) (7.3*heightSegment));
                g.drawString("Blood Pressure", 10*widthSegment, (int) (7.3*heightSegment));
                g.drawString("Systolic", 10*widthSegment, (int) (7.55*heightSegment));
                g.drawString("Blood Pressure", 12*widthSegment, (int) (7.3*heightSegment));
                g.drawString("Dystolic", 12*widthSegment, (int) (7.55*heightSegment));
                
                //Y-Axis Label
                g.setFont(new Font("TimesRoman", Font.PLAIN, (int) (getWidth()/40 + 1)));
                g.drawString("%", widthSegment/2, 4*heightSegment);
                
                //Graph title
                g.setFont(new Font("TimesRoman", Font.BOLD, (int) (getWidth()/35 + 1)));
                g.drawString("Comparisons to Optimal Values: " + graphTitle, widthSegment, (int) (.75*heightSegment));
        }
    }
    
    //Calculates the user's expected active pulse based on their age
    private int activePulse(){
        int activePulse = 180;
        Optional<LinkedList<String>> currentUser = ProfileModel.getInstance().getProfile(ProfileModel.getInstance().getCurrentUser());
        if(currentUser.isPresent()){
            int birthMonth = Integer.parseInt(currentUser.get().get(ProfileModel.ProfileItem.BIRTHMONTH.ordinal()));
            int birthDay = Integer.parseInt(currentUser.get().get(ProfileModel.ProfileItem.BIRTHDAY.ordinal()));
            int birthYear = Integer.parseInt(currentUser.get().get(ProfileModel.ProfileItem.BIRTHYEAR.ordinal()));
            
            Calendar cal = Calendar.getInstance();
            int year = cal.get(cal.YEAR);
            int month = cal.get(cal.MONTH + 1);
            int day = cal.get(cal.DAY_OF_MONTH);
            
            int age = year - birthYear;
            if(age > 0){
                if((month == birthMonth && day >= birthDay) || month > birthMonth){
                    activePulse = 220 - age;
                }
                else{
                    activePulse = 220 - age - 1;
                }
            }
        }
        return activePulse;
    }
    
}
