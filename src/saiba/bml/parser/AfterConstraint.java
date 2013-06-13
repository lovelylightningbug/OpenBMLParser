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
package saiba.bml.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * After constraint
 * @author Herwin
 */
public class AfterConstraint
{
    private SyncPoint ref;
    public SyncPoint getRef()
    {
        return ref;
    }

    private List<SyncPoint> targets = new ArrayList<SyncPoint>();
    
    public void addTarget(SyncPoint target)
    {
        targets.add(target);
    }
    
    public AfterConstraint(SyncPoint ref)
    {
        this.ref = ref;
    }
    
    public List<SyncPoint> getTargets()
    {
        return targets;
    }
    
    @Override
    public String toString()
    {
        StringBuffer retval = new StringBuffer();
        retval.append("Reference: ");
        retval.append(ref);
        retval.append(", Targets: ");
        for (SyncPoint target : targets)
        {
            retval.append(target.toString() + ", ");
        }
        retval.delete(retval.length() - 2, retval.length());

        return retval.toString();
    }
}
