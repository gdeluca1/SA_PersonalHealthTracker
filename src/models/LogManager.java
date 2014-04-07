package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogManager
{
    private static LogManager instance = null;
    
    // Set to true to display debugging messages.
    private static final boolean DEBUG = false;
    
    private LogManager() {}
    
    public static LogManager getInstance()
    {
        return (instance != null) ? instance : (instance = new LogManager());
    }
    
    /**
     * Reads from a file that contains a list of all the users, their passwords, their recovery
     * questions, and their recovery answers.
     */
    public void loadUserList()
    {
        File loadFile = new File(new File(System.getProperty("user.home")), "/PersonalHealthTracker/" + "users.dat");
        if (loadFile.exists())
        {
            FileInputStream fIn = null;
            try
            {
                // Load the list of users.
                fIn = new FileInputStream(loadFile);
                ObjectInputStream in = new ObjectInputStream(fIn);
                ProfileModel.getInstance().restoreProfileList((LinkedList<LinkedList<String>>)in.readObject());
                if (DEBUG)
                {
                    System.out.println("Read: " + ProfileModel.getInstance().getProfileList().size() + " profiles.");
                }
            }
            catch (FileNotFoundException ex)
            {
                Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException | ClassNotFoundException ex)
            {
                Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally 
            {
                try 
                {
                    if (fIn != null)
                    {
                        fIn.close();
                    }
                }
                catch (IOException ex) 
                {
                    Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void saveUserList()
    {
        File saveFile = new File(new File(System.getProperty("user.home")), "/PersonalHealthTracker/" + "users.dat");
        saveFile.getParentFile().mkdir();
        if (ProfileModel.getInstance().anyProfilesRegistered())
        {
            FileOutputStream fOut = null;
            try
            {
                fOut = new FileOutputStream(saveFile);
                ObjectOutputStream out = new ObjectOutputStream(fOut);
                out.writeObject(ProfileModel.getInstance().getProfileList());
                if (DEBUG)
                {
                    System.out.println("Wrote: " + ProfileModel.getInstance().getProfileList().size() + " profiles.");
                }
            }
            catch (FileNotFoundException ex)
            {
                Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException ex)
            {
                Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally 
            {
                try 
                {
                    if (fOut != null)
                    {
                        fOut.close();
                    }
                }
                catch (IOException ex) 
                {
                    Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void loadActivities()
    {
        File loadFile = new File(new File(System.getProperty("user.home")), "/PersonalHealthTracker/" + ProfileModel.getInstance().getCurrentUser() + ".dat");
        if (loadFile.exists())
        {
            FileInputStream fIn = null;
            try {
                fIn = new FileInputStream(loadFile);
                ObjectInputStream in = new ObjectInputStream(fIn);
                ActivityModel.getInstance().restoreActivities((ArrayList<Activity>) in.readObject());
                if (DEBUG)
                {
                    System.out.println("Read: " + ActivityModel.getInstance().getActivities().size() + " activities.");
                }
            }
            catch (FileNotFoundException ex) {
                Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException | ClassNotFoundException ex)
            {
                Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally 
            {
                try 
                {
                    if (fIn != null)
                    {
                        fIn.close();
                    }
                }
                catch (IOException ex) 
                {
                    Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void saveActivities()
    {
        // Save the state to a file named after their username on their home directory.
        File saveFile = new File(new File(System.getProperty("user.home")), "/PersonalHealthTracker/" + ProfileModel.getInstance().getCurrentUser() + ".dat");
        saveFile.getParentFile().mkdir();
        if (! ActivityModel.getInstance().getActivities().isEmpty())
        {
            FileOutputStream fOut = null;
            try
            {
                fOut = new FileOutputStream(saveFile);
                ObjectOutputStream out = new ObjectOutputStream(fOut);
                out.writeObject(ActivityModel.getInstance().getActivities());
                if (DEBUG)
                {
                    System.out.println("Wrote: " + ActivityModel.getInstance().getActivities().size() + " activities.");
                }
            }
            catch (FileNotFoundException ex)
            {
                Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException ex)
            {
                Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally 
            {
                try 
                {
                    if (fOut != null)
                    {
                        fOut.close();
                    }
                }
                catch (IOException ex) 
                {
                    Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
