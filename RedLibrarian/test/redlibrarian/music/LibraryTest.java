/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.music;

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
public class LibraryTest {
    
    public LibraryTest() {
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
     * Test of getId method, of class Library.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        long l = 0;
        while(l<1000)
        {
            Library instance = new Library();
            instance.setId(l);
            Long expResult = 0l;
            Long result = instance.getId();
            assertEquals(expResult, result);
            l++;
        }
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class Library.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Long id = null;
        Library instance = new Library();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class Library.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Library instance = new Library();
        instance.setName("MARCHING");
        String expResult = "MARCHING";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Library.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "MARCHING";
        Library instance = new Library();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getDesciption method, of class Library.
     */
    @Test
    public void testGetDesciption() {
        System.out.println("getDesciption");
        Library instance = new Library();
        String expResult = "";
        String result = instance.getDesciption();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setDesciption method, of class Library.
     */
    @Test
    public void testSetDesciption() {
        System.out.println("setDesciption");
        String description = "A test library";
        Library instance = new Library();
        instance.setDesciption(description);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addSong method, of class Library.
     */
    @Test
    public void testAddSong() {
        System.out.println("addSong");
        Song item = null;
        Library instance = new Library();
        boolean expResult = false;
        boolean result = instance.addSong(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of removeSong method, of class Library.
     */
    @Test
    public void testRemoveSong_Song() {
        System.out.println("removeSong");
        Song target = null;
        Library instance = new Library();
        boolean expResult = false;
        boolean result = instance.removeSong(target);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of removeSong method, of class Library.
     */
    @Test
    public void testRemoveSong_long() {
        System.out.println("removeSong");
        long targetId = 0L;
        Library instance = new Library();
        boolean expResult = false;
        boolean result = instance.removeSong(targetId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Library.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Library instance = new Library();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
