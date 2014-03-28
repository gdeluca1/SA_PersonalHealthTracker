package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import views.HealthTrackerView;

public class ActivityFile
{
    private ArrayList<ActivityModel> activities;
    
    private static ActivityFile instance = null;
    
    private ActivityFile()
    {
        activities = new ArrayList<>();
        addEntry(new ActivityModel(ActivityModel.ActivityType.CARDIO, LocalDateTime.now(), "10m 20s", "123 bpm", "20/14", "124/23"));
        addEntry(new ActivityModel(ActivityModel.ActivityType.SEDENTARY, LocalDateTime.now(), "20m 30s", "123 bpm", "20/14", "124/23"));
        addEntry(new ActivityModel(ActivityModel.ActivityType.STRENGTH, LocalDateTime.now(), "10m 20s", "123 bpm", "20/14", "124/23"));
        addEntry(new ActivityModel(ActivityModel.ActivityType.SEDENTARY, LocalDateTime.now(), "20m 30s", "123 bpm", "20/14", "124/23"));
    }
    
    public static ActivityFile getInstance()
    {
        return (instance != null) ? instance : (instance = new ActivityFile());
    }
    
    public void addEntry(ActivityModel log)
    {
        activities.add(log);
        HealthTrackerView.addActivityPanel(log);
    }

    public void setActivities(ArrayList<ActivityModel> activities)
    {
        this.activities = activities;
    }

    public ArrayList<ActivityModel> getActivities()
    {
        return activities;
    }
}
