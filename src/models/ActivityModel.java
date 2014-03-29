package models;

import java.util.ArrayList;

public class ActivityModel
{
    private ArrayList<Activity> activities;
    
    private static ActivityModel instance = null;
    
    private ActivityModel()
    {
        activities = new ArrayList<>();
    }
    
    public static ActivityModel getInstance()
    {
        return (instance != null) ? instance : (instance = new ActivityModel());
    }
    
    public void addEntry(Activity log)
    {
        activities.add(log);
    }

    public void restoreActivities(ArrayList<Activity> activities)
    {
        this.activities = activities;
    }

    public ArrayList<Activity> getActivities()
    {
        return activities;
    }
}
