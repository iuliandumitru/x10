/*
 *
 * (C) Copyright IBM Corporation 2006-2008
 *
 *  This file is part of X10 Language.
 *
 */
package x10.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Christian Grothoff
 */
public class MultipleExceptions extends java.lang.RuntimeException {
    public final ArrayList<Throwable> exceptions; // <Throwable>
    
    public MultipleExceptions(ValRail<Throwable> s) {
        exceptions = toList(s);
    }
    
    public MultipleExceptions(List<Throwable> s) {
        exceptions = toList(s);
    }
    
    public MultipleExceptions(Stack<Throwable> s) {
        exceptions = toList(s);
    }
    
    private static ArrayList<Throwable> toList(Iterable<Throwable> s) {
        ArrayList<Throwable> s_actual = new ArrayList<Throwable>();

        // cvp: make sure that there are no MultipleExceptions in the stack -- 
        // otherwise toString might result in a stack-overflow.
        for (Throwable t : s) {
            if (t instanceof MultipleExceptions) {
                MultipleExceptions me = (MultipleExceptions) t;
                s_actual.addAll(me.exceptions);
            }
            else {
                s_actual.add(t);
            }
        }
        
        return s_actual;
    }
    public ValRail<Exception> exceptions() {
    	Exception[] a = (Exception[]) exceptions.toArray();
    	return RailFactory.makeValRailFromJavaArray(a);
    }
    public String toString() {
        return exceptions.toString();
    }
    
    public void printStackTrace() {
        super.printStackTrace();
        for (Throwable t: exceptions) {
            t.printStackTrace();
        }
    }
}
