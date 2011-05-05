/* Current test harness gets confused by packages, but it would be in package Functions5g5m;
*/
// Warning: This file is auto-generated from the TeX source of the language spec.
// If you need it changed, work with the specification writers.


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



public class Functions5g5m extends x10Test {
   public def run() : boolean = (new Hook()).run();
   public static def main(var args: Array[String](1)): void = {
        new Functions5g5m().execute();
    }


// file Functions line 276
 static  class clogua {
  public static def main(argv:Array[String](1)) {
    val n = 3;
    val f : (x:Int){x != n} => Int
          = (x:Int){x != n} => (12/(n-x));
    Console.OUT.println("f(5)=" + f(5));
}}

 static class Hook {
   def run():Boolean = true;
}

}