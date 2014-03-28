package models;

import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ProfileModel
{
    private enum ProfileItem {USERNAME, PASSWORD, QUESTION, ANSWER};
    
    private LinkedList<LinkedList<String>> profileList;
    private String currentUserName;
    
    public ProfileModel()
    {
        currentUserName = null;
        profileList = new LinkedList<>();
    }
    
    /**
     * This method should only be called once (at app startup) to restore
     * the profile list.
     * @param profileList
     */
    public void restoreProfileTable(LinkedList<LinkedList<String>> profileList)
    {
        this.profileList = profileList;
    }
    
    public void createProfile(String userName, String password, String securityQuestion, String securityAnswer)
    {
        // Create a linked list containing the user's password, security question, and security answer.
        LinkedList<String> userProfile = new LinkedList<>();
        userProfile.add(ProfileItem.USERNAME.ordinal(), userName);
        userProfile.add(ProfileItem.PASSWORD.ordinal(), password);
        userProfile.add(ProfileItem.QUESTION.ordinal(), securityQuestion);
        userProfile.add(ProfileItem.ANSWER.ordinal(), securityAnswer);
        
        // Add the user's profile to the linked list.
        profileList.add(userProfile);
    }
    
    public boolean deleteProfile(String userName, String password)
    {
        AtomicBoolean toReturn = new AtomicBoolean(false);
        AtomicInteger numberProfilesDeleted = new AtomicInteger(0);
        profileList
                .parallelStream()
                .filter((userProfile) ->
                {
                    return (userProfile.get(ProfileItem.USERNAME.ordinal()).equals(userName) &&
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
    
    public boolean verifyProfile(String userName, String password)
    {
        // Check to see if any of the user profiles match the input values. 
        // Uses parallel stream for increased speed.
        boolean profileFound = profileList
                .parallelStream()
                .anyMatch((userProfile)->
                {
                    return userProfile.get(ProfileItem.USERNAME.ordinal()).equals(userName) &&
                            userProfile.get(ProfileItem.PASSWORD.ordinal()).equals(password);
                });
        
        if (profileFound)
        {
            currentUserName = userName;
        }
        
        return profileFound;
    }
    
    public void logout()
    {
        currentUserName = null;
    }
    
    public Optional<String> getCurrentUser()
    {
        if (currentUserName != null)
        {
            return Optional.of(currentUserName);
        }
        else
        {
            return Optional.empty();
        }
    }
}
