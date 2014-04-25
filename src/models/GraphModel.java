package models;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JPanel;
import views.BarGraph;
import views.HealthTrackerView;
import views.LineGraph;

public class GraphModel
{
    private ArrayList<Graph> graphs;
    
    private static GraphModel instance = null;
    
    private GraphModel()
    {
        graphs = new ArrayList<>();
    }
    
    public static GraphModel getInstance()
    {
        return (instance != null) ? instance : (instance = new GraphModel());
    }
    
    public void addEntry(Graph graph)
    {
        graphs.add(graph);
    }

    public int restoreGraphs(ArrayList<Graph> graphs, HealthTrackerView view)
    {
        AtomicInteger toReturn = new AtomicInteger(0);
        
        graphs
                .parallelStream()
                .forEach((graph) ->
                {
                    view.addGraphPanel(graph.getGraph());
                    toReturn.getAndIncrement();
                });
        
        return toReturn.get();
    }

    public ArrayList<Graph> getGraphs(JPanel graphPanel)
    {
        graphs.clear();
        
        for (int i = 1; i < graphPanel.getComponentCount(); i++)
        {
            if (graphPanel.getComponent(i) instanceof LineGraph)
            {
                LineGraph graph = (LineGraph) graphPanel.getComponent(i);
                graphs.add(new Graph(Graph.LINE, graph.getActivities(), graph.isAutoUpdate(), graph.getSelectedStat(), graph.getTitle()));
            }
            
            else if (graphPanel.getComponent(i) instanceof BarGraph)
            {
                BarGraph graph = (BarGraph) graphPanel.getComponent(i);
                graphs.add(new Graph(Graph.BAR, graph.getActivities(), graph.isAutoUpdate()));
            }
        }
        return graphs;
    }
}
