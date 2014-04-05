/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package personalhealthtracker;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Gennaro
 */
public class ActivityReceiver
{
    private static ActivityReceiver instance;
    private ActivityReceiver() {}
    
    public static ActivityReceiver getInstance()
    {
        return (instance != null) ? instance : (instance = new ActivityReceiver());
    }
    
    public void receiveActivites()
    {
//        int choice = JOptionPane.showConfirmDialog(null, "Your IP Address is: " + getAddress().toString() + ". Would you like to receive activities from"
//                + " your Android Personal Health Tracker?");
        
//        if (choice == JOptionPane.YES_OPTION)
        {
            
        }
    }
    
    
}
