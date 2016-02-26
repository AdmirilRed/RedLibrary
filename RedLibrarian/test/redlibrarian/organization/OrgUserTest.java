/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.organization;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author admir
 */
public class OrgUserTest {
    
    public OrgUserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getUsername method, of class OrgUser.
     */
    @Test
    public void testOrgUser() {
        OrgUser user = new OrgUser("root","rootbeer");
        boolean expResult = false;
        boolean result = user.verifyPassword("rootbeers");
        assertEquals(expResult, result);

    }
    
}
