package models;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JPanel;
import views.BarGraph;
import views.LineGraph;
import views.PieGraph;

public class Graph implements Serializable
{
    private final int graphType;
    
    public static final int LINE = 0,
            BAR = 1,
            PIE = 2;
    
    private final ArrayList<Activity> displayedActivities;
    private int selectedStat;
    private final boolean autoUpdate;
    private String graphTitle;
    
    public Graph(int graphType, ArrayList<Activity> displayedActivities, boolean autoUpdate, String graphTitle)
    {
        // To instantiate a line graph, we require more information than this constructor accepts.
        assert graphType != LINE;
        
        this.graphType = graphType;
        this.displayedActivities = displayedActivities;
        this.autoUpdate = autoUpdate;
        this.graphTitle = graphTitle;
    }
    
    public Graph(int graphType, ArrayList<Activity> displayedActivities, boolean autoUpdate, int selectedStat, String graphTitle)
    {
        this.graphType = graphType;
        this.displayedActivities = displayedActivities;
        this.autoUpdate = autoUpdate;
        this.selectedStat = selectedStat;
        this.graphTitle = graphTitle;
    }
    
    public JPanel getGraph()
    {
        switch (graphType)
        {
            case LINE:
                return new LineGraph(displayedActivities, selectedStat, autoUpdate, graphTitle);
            case BAR:
                return new BarGraph(displayedActivities, autoUpdate, graphTitle);
            case PIE:
                return new PieGraph(displayedActivities, autoUpdate, graphTitle);
            default:
                return null;
        }
    }
}
