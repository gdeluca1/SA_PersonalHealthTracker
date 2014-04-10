/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package personalhealthtracker;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Gennaro
 */
public class GraphFactory
{
    /**
     * Creates a pie chart from the data in the activity list.
     * @return A JPanel with the pie chart drawn on it.
     */
    public static JPanel getPieChart()
    {
        return new JPanel()
        {

            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                // Can get list of activities with: ActivityModel.getInstance().getActivities();
            }
        };
    }
}
