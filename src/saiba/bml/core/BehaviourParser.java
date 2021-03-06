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

import hmi.xml.XMLScanException;
import hmi.xml.XMLTokenizer;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import saiba.bml.BMLInfo;
import saiba.bml.parser.SyncPoint;

/**
 * Parses a BML Behaviour
 * @author hvanwelbergen
 */
public final class BehaviourParser
{
    private BehaviourParser()
    {
    }

    /**
     * @param bmlId id of the bml block
     * @param tokenizer
     * @return the parsed behavior, or null if it cannot be constructed
     * @throws IOException
     */
    public static Behaviour parseBehaviour(String bmlId, XMLTokenizer tokenizer) throws IOException
    {
        String tag = tokenizer.getTagName();
        for (String str : BMLInfo.getBehaviourTypes().keySet())
        {
            if (str.equals(tokenizer.getNamespace()+":"+tag))
            {
                tag = tokenizer.getNamespace()+":"+tag;
            }
            if (str.equals(tag))
            {
                Behaviour b = null;
                try
                {
                    Constructor<? extends Behaviour> c = BMLInfo.getBehaviourTypes().get(tag)
                            .getConstructor(new Class[] { String.class, XMLTokenizer.class });
                    b = c.newInstance(bmlId, tokenizer);
                }
                catch (InstantiationException e)
                {
                    throw new XMLScanException(e.getMessage(), e);
                }
                catch (IllegalAccessException e)
                {
                    throw new XMLScanException(e.getMessage(), e);
                }
                catch (IllegalArgumentException e)
                {
                    throw new XMLScanException(e.getMessage(), e);
                }
                catch (InvocationTargetException e)
                {
                    throw new XMLScanException(e.getCause().getMessage(), e.getCause());
                }
                catch (SecurityException e)
                {
                    throw new XMLScanException(e.getMessage(), e);
                }
                catch (NoSuchMethodException e)
                {
                    throw new XMLScanException(e.getMessage(), e);
                }
                if (b != null)
                {
                    if (b.descBehaviour == null)
                    {
                        return b;
                    }
                    else
                    {
                        // move all new syncpoints from the core behavior to
                        // the behavior description if the syncpoint do not exist in the
                        // description, or if it does but contains a null reference
                        ArrayList<SyncPoint> removeList = new ArrayList<SyncPoint>();
                        ArrayList<SyncPoint> addList = new ArrayList<SyncPoint>();
                        for (SyncPoint s : b.getSyncPoints())
                        {
                            boolean add = true;
                            for (SyncPoint sDesc : b.descBehaviour.getSyncPoints())
                            {
                                if (s.getName().equals(sDesc.getName()))
                                {
                                    if (sDesc.getRef() == null)
                                    {
                                        removeList.add(sDesc);
                                        break;
                                    }
                                    else
                                    {
                                        add = false;
                                        break;
                                    }
                                }
                            }
                            if (add)
                            {
                                s.setBehaviourId(b.descBehaviour.id);
                                addList.add(s);
                            }
                        }
                        b.descBehaviour.removeSyncPoints(removeList);
                        b.descBehaviour.addSyncPoints(addList);
                        return b;
                    }
                }
            }
        }
        return null;
    }
}
