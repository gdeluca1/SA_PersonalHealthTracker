package models;

import java.io.Serializable;
import java.util.Date;

public class Activity implements Serializable
{
    // Add a serial version UID to prevent deserialization issues.
    private static final long serialVersionUID = 394388108L;
    
    public class ActivityType
    {
        public static final int 
            CARDIO = 0,
            STRENGTH = 1,
            SEDENTARY = 2;
    }
    
    private int activity;
    private Date timeStamp;
    private String pulse, bloodPressure, bloodSugar;
    private int hoursSpent, minutesSpent, secondsSpent;
    
    public Activity(int activity, Date timeStamp, int hoursSpent, int minutesSpent, int secondsSpent, String pulse, String bloodPressure, String bloodSugar)
    {
        this.activity = activity;
        this.timeStamp = timeStamp;
        this.hoursSpent = hoursSpent;
        this.minutesSpent = minutesSpent;
        this.secondsSpent = secondsSpent;
                
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
        String timeSpent = "";
        if (hoursSpent != 0)
        {
            timeSpent += hoursSpent + "h ";
        }
        if (minutesSpent != 0)
        {
            timeSpent += minutesSpent + "m ";
        }
        if (timeSpent.equals("") || secondsSpent != 0)
        {
            timeSpent += secondsSpent + "s";
        }
        
        return timeSpent;
    }

    public int getHoursSpent()
    {
        return hoursSpent;
    }

    public int getMinutesSpent()
    {
        return minutesSpent;
    }

    public int getSecondsSpent()
    {
        return secondsSpent;
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

    public Date getTimeStamp()
    {
        return timeStamp;
    }
}
