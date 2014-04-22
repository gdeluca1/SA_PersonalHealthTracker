package models;

import java.util.ArrayList;

public class ActivityModel
{
    private ArrayList<Activity> activities;
    private ArrayList<Activity> visibleActivities;
    
    private static ActivityModel instance = null;
    
    private ActivityModel()
    {
        activities = new ArrayList<>();
        visibleActivities = new ArrayList<>();
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

    public ArrayList<Activity> getVisibleActivities()
    {
        return visibleActivities;
    }
}
