/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gennaro
 */
public class ProfileModelTest
{
    
    ProfileModel model;
    
    @Before
    public void setUp()
    {
        // Load the list of users first.
        LogManager.getInstance().loadUserList();
        model = ProfileModel.getInstance();
    }

    @Test
    public void testVerifyProfile1()
    {
        assertTrue(model.verifyProfile("admin", "password"));
    }
    
    @Test
    public void testVerifyProfile2()
    {
        assertFalse(model.verifyProfile("admin", "kitty"));
    }
    
    @Test
    public void testVerifyProfile3()
    {
        assertFalse(model.verifyProfile(null, "password"));
    }
}
