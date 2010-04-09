/*
 *  This file is part of the X10 project (http://x10-lang.org).
 *
 *  This file is licensed to You under the Eclipse Public License (EPL);
 *  You may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *      http://www.opensource.org/licenses/eclipse-1.0.php
 *
 *  (C) Copyright IBM Corporation 2006-2010.
 */

import harness.x10Test;


/**
 * As with a normal method, the closure may have a throws clause
 *
 * @author bdlucas 8/2008
 */


public class ClosureException1j extends ClosureTest {

    class E extends Exception {}

    public def run(): boolean = {
        
        class C[T] {val j = (){T<:Exception} throws T, Exception => 1;}
        check("new C[E]().j()", (new C[E]().j)(), 1);
        check("new C[Exception]().j()", new C[Exception]().j(), 1);

        return result;
    }

    public static def main(var args: Rail[String]): void = {
        new ClosureException1j().execute();
    }
}
