package models;

import java.time.LocalDateTime;

public class ActivityLog
{
    public enum LogType {CARDIO, STRENGTH, SLEEP, WORK}
    
    private int activity;
    private LocalDateTime timeStamp;
    private float timeSpent, pulse, bloodPressure, bloodSugar;
    
    public ActivityLog(int activity, LocalDateTime timeStamp, float timeSpent, float pulse, float bloodPressure, float bloodSugar)
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
}
