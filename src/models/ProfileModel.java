package models;

import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ProfileModel
{
    private enum ProfileItem {USERNAME, PASSWORD, QUESTION, ANSWER};
    
    private LinkedList<LinkedList<String>> profileList;
    private String currentUsername;
    
    private static ProfileModel instance;
    
    /**
     * Setting this variable to true adds the admin account to the profileList.
     */
    private static final boolean DEBUG = true;
    
    /**
     * This class is a singleton class. Instances may only be obtained using 
     * the getInstance() method.
     */
    private ProfileModel()
    {
        currentUsername = null;
        profileList = new LinkedList<>();
        if (DEBUG)
        {
            LinkedList<String> adminAccount = new LinkedList<>();
            adminAccount.add(ProfileItem.USERNAME.ordinal(), "admin");
            adminAccount.add(ProfileItem.PASSWORD.ordinal(), "password");
            adminAccount.add(ProfileItem.QUESTION.ordinal(), null);
            adminAccount.add(ProfileItem.ANSWER.ordinal(), null);
            profileList.add(adminAccount);
        }
    }
    
    public static ProfileModel getInstance()
    {
        return (instance != null) ? instance : (instance = new ProfileModel());
    }
    
    /**
     * This method should only be called once (at app startup) to restore
     * the profile list.
     * @param profileList
     */
    public void restoreProfileList(LinkedList<LinkedList<String>> profileList)
    {
        this.profileList = profileList;
    }

    public LinkedList<LinkedList<String>> getProfileList()
    {
        return profileList;
    }
    
    public void createProfile(String username, String password, String securityQuestion, String securityAnswer)
    {
        // Create a linked list containing the user's password, security question, and security answer.
        LinkedList<String> userProfile = new LinkedList<>();
        userProfile.add(ProfileItem.USERNAME.ordinal(), username);
        userProfile.add(ProfileItem.PASSWORD.ordinal(), password);
        userProfile.add(ProfileItem.QUESTION.ordinal(), securityQuestion);
        userProfile.add(ProfileItem.ANSWER.ordinal(), securityAnswer);
        
        // Add the user's profile to the linked list.
        profileList.add(userProfile);
    }
    
    public boolean deleteProfile(String username, String password)
    {
        AtomicBoolean toReturn = new AtomicBoolean(false);
        AtomicInteger numberProfilesDeleted = new AtomicInteger(0);
        profileList
                .parallelStream()
                .filter((userProfile) ->
                {
                    return (userProfile.get(ProfileItem.USERNAME.ordinal()).equals(username) &&
                            userProfile.get(ProfileItem.PASSWORD.ordinal()).equals(password));
                })
                .forEach((userProfile) ->
                {
                    profileList.remove(userProfile);
                    numberProfilesDeleted.addAndGet(1);
                    toReturn.set(true);
                });
        
        // This should never happen (there should not be any username overlap).
        // If it does happen, there is some sort of security issue with the software.
        // Throw an exception to notify the user.
        if (numberProfilesDeleted.get() > 1)
        {
            throw new SecurityException(numberProfilesDeleted.get() + " profiles had the same username and password and were deleted.");
        }
        
        return toReturn.get();
    }
    
    public boolean verifyProfile(String username, String password)
    {
        if (username == null || password == null)
        {
            return false;
        }
        
        // Check to see if any of the user profiles match the input values. 
        // Uses parallel stream for increased speed.
        boolean profileFound = profileList
                .parallelStream()
                .anyMatch((userProfile)->
                {
                    return userProfile.get(ProfileItem.USERNAME.ordinal()).equals(username) &&
                            userProfile.get(ProfileItem.PASSWORD.ordinal()).equals(password);
                });
        
        if (profileFound)
        {
            currentUsername = username;
        }
        
        return profileFound;
    }
    
    public void logout()
    {
        LogManager.getInstance().saveActivities();
        LogManager.getInstance().saveUserList();
        currentUsername = null;
    }
    
    public String getCurrentUser()
    {
        return currentUsername;
    }
    
    /**
     * Returns true if there are any profiles in
     * the profile list.
     * @return 
     */
    public boolean anyProfilesRegistered()
    {
        return ! profileList.isEmpty();
    }
}
