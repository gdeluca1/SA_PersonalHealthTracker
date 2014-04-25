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
    private int pulse, 
            bloodPressureSystolic, //Top number in BP
            bloodPressureDystolic, //Bottom number in BP
            bloodSugar;
    private int hoursSpent, minutesSpent, secondsSpent;
    
    public Activity(int activity, Date timeStamp, int hoursSpent, int minutesSpent, int secondsSpent, int pulse, int bloodPressureS, int bloodPressureD, int bloodSugar)
    {
        this.activity = activity;
        this.timeStamp = timeStamp;
        this.hoursSpent = hoursSpent;
        this.minutesSpent = minutesSpent;
        this.secondsSpent = secondsSpent;
                
        this.pulse = pulse;
        this.bloodSugar = bloodSugar;
        
        bloodPressureSystolic = bloodPressureS;
        bloodPressureDystolic = bloodPressureD;
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

    public int getPulse()
    {
        return pulse;
    }

    public int getBloodPressureSystolic()
    {
        return bloodPressureSystolic;
    }
    
    public int getBloodPressureDystolic()
    {
        return bloodPressureDystolic;
    }

    public int getBloodSugar()
    {
        return bloodSugar;
    }

    public Date getTimeStamp()
    {
        return timeStamp;
    }
}
