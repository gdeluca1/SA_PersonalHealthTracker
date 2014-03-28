package models;

import java.time.LocalDateTime;

public class ActivityModel
{
    public class ActivityType
    {
        public static final int CARDIO = 0,
            STRENGTH = 1,
            SEDENTARY = 2;
    }
    
    private int activity;
    private LocalDateTime timeStamp;
    private String timeSpent, pulse, bloodPressure, bloodSugar;
    
    public ActivityModel(int activity, LocalDateTime timeStamp, String timeSpent, String pulse, String bloodPressure, String bloodSugar)
    {
        this.activity = activity;
        this.timeStamp = timeStamp;
        this.timeSpent = timeSpent;
        this.pulse = pulse;
        this.bloodPressure = bloodPressure;
        this.bloodSugar = bloodSugar;
    }
    
    public int getActivity()
    {
        return activity;
    }

    public String getTimeSpent()
    {
        return timeSpent;
    }

    public String getPulse()
    {
        return pulse;
    }

    public String getBloodPressure()
    {
        return bloodPressure;
    }

    public String getBloodSugar()
    {
        return bloodSugar;
    }

    public LocalDateTime getTimeStamp()
    {
        return timeStamp;
    }
}
