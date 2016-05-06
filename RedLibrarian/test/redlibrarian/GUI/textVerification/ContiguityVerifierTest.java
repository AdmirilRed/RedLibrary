/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.GUI.textVerification;

import javax.swing.JComponent;
import javax.swing.JTextField;
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
public class ContiguityVerifierTest {
    
    public ContiguityVerifierTest() {
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
     * Test of verify method, of class ContiguityVerifier.
     */
    @Test
    public void testVerify() {
        System.out.println("verify");
        JComponent input = new JTextField("Testing");
        ContiguityVerifier instance = new ContiguityVerifier();
        boolean expResult = true;
        boolean result = instance.verify(input);
        assertEquals(expResult, result);
    }
    
}
