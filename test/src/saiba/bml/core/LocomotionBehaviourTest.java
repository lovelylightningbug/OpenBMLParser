/*******************************************************************************
 * Copyright (c) 2013 University of Twente, Bielefeld University
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ******************************************************************************/
package saiba.bml.core;

import static org.junit.Assert.assertEquals;
import hmi.xml.XMLTokenizer;

import java.io.IOException;

import org.junit.Test;

import saiba.utils.TestUtil;

/**
 * Unit test cases for the locomotion behavior
 * @author welberge
 */
public class LocomotionBehaviourTest extends AbstractBehaviourTest
{
    @Override
    protected Behaviour createBehaviour(String bmlId, String extraAttributeString) throws IOException
    {
        String str = "<locomotion "+TestUtil.getDefNS()+"id=\"loc1\" target=\"bluebox\" " +
            extraAttributeString+"/>";
        return new LocomotionBehaviour("bml1", new XMLTokenizer(str));
    }
    
    @Override
    protected Behaviour parseBehaviour(String bmlId, String bmlString) throws IOException
    {
        return new LocomotionBehaviour("bml1", new XMLTokenizer(bmlString));
    }  
    
    @Test
    public void testReadLocomotion() throws IOException
    {
        String str = "<locomotion "+TestUtil.getDefNS()+"id=\"loc1\" target=\"bluebox\" start=\"1\" manner=\"RUN\"/>";
        LocomotionBehaviour beh = new LocomotionBehaviour("bml1", new XMLTokenizer(str));
        assertEquals("loc1", beh.id);
        assertEquals("bml1", beh.bmlId);
        assertEquals("bluebox",beh.getStringParameterValue("target"));
        assertEquals("RUN", beh.getStringParameterValue("manner"));
    }
    
    @Test
    public void writeReadXML() throws IOException
    {
        String str = "<locomotion "+TestUtil.getDefNS()+"id=\"loc1\" target=\"bluebox\" start=\"1\" manner=\"RUN\"/>";
        LocomotionBehaviour behIn = new LocomotionBehaviour("bml1", new XMLTokenizer(str));
        StringBuilder buf = new StringBuilder();
        
        behIn.appendXML(buf); 
        LocomotionBehaviour behOut = new LocomotionBehaviour("bml1", new XMLTokenizer(buf.toString()));
        assertEquals("loc1", behOut.id);
        assertEquals("bml1", behOut.bmlId);
        assertEquals("bluebox",behOut.getStringParameterValue("target"));
        assertEquals("RUN", behOut.getStringParameterValue("manner"));
    }

      
}
