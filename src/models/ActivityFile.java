package models;

import java.util.ArrayList;

public class ActivityFile
{
    private ArrayList<ActivityLog> activities;
    
    public ActivityFile()
    {
        activities = new ArrayList<>();
    }
    
    public void addEntry(ActivityLog log)
    {
        activities.add(log);
    }

    public void setActivities(ArrayList<ActivityLog> activities)
    {
        this.activities = activities;
    }

    public ArrayList<ActivityLog> getActivities()
    {
        return activities;
    }
}
