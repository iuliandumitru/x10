/*
 *  This file is part of the X10 project (http://x10-lang.org).
 *
 *  This file is licensed to You under the Eclipse Public License (EPL);
 *  You may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *      http://www.opensource.org/licenses/eclipse-1.0.php
 *
 * This file was originally derived from the Polyglot extensible compiler framework.
 *
 *  (C) Copyright 2000-2007 Polyglot project group, Cornell University
 *  (C) Copyright IBM Corporation 2007-2012.
 */

package polyglot.lex;

import polyglot.util.Position;

/** Token class for boolean literals. */
public class BooleanLiteral extends Literal {
    protected Boolean val;
  public BooleanLiteral(Position position, boolean b, int sym) {
      super(position, sym);
      this.val = Boolean.valueOf(b);
  }
  
  public Boolean getValue() { return val; }

  public String toString() { return "boolean literal " + val.toString(); }
}
