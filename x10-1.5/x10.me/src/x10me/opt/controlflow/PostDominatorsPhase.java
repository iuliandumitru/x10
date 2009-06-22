/*
 *  This file is part of the X10 project (http://x10-lang.org).
 *
 *  This file was derived from code developed by the
 *  Jikes RVM project (http://jikesrvm.org).
 *
 *  This file is licensed to You under the Eclipse Public License (EPL);
 *  You may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.opensource.org/licenses/eclipse-1.0.php
 *
 *  See the COPYRIGHT.txt file distributed with this work for information
 *  regarding copyright ownership.
 */

package x10me.opt.controlflow;

import x10me.opt.driver.CompilerPhase;
import x10me.opt.driver.OptOptions;
import x10me.opt.ir.IR;

/**
 * Driver routine for post-dominator computation.  This phase invokes
 * the Lengauer-Tarjan dominator calculation.
 */
public final class PostDominatorsPhase extends CompilerPhase {

  /**
   * Should we unfactor the CFG?
   */
  private final boolean unfactor;

  /**
   * @param unfactor Should we unfactor the CFG before computing
   * dominators?
   */
  public PostDominatorsPhase(IR ir, boolean unfactor) {
    super(ir);
    this.unfactor = unfactor;
  }

  /**
   * Should this phase be performed?  This is a member of a composite
   * phase, so always return true.  The parent composite phase will
   * dictate.
   * @param options controlling compiler options
   */
  public boolean shouldPerform(OptOptions options) {
    return true;
  }

  /**
   * Return a string representation of this phase
   * @return "Post-Dominators"
   */
  public String getName() {
    return "Post-Dominators";
  }

  /**
   * Should the IR be printed before and/or after this phase?
   * @param options controlling compiler options
   * @param before query control
   * @return true or false
   */
  public boolean printingEnabled(OptOptions options, boolean before) {
    return false;
  }

  /**
   * Main driver for the post-dominator calculation.
   */
  public void perform() {
    // reset flags in case an exception is thrown inside "perform"
    // and it doesn't return normally
    ir.postDominatorTree = null;

    // compute post-dominators,
    // leaves info in scratch object of basic blocks
    LTDominators.perform(ir, false, unfactor);

    // create the dominator tree, relies on dominator info being
    // in scratch object of basic blocks
    DominatorTree.perform(ir, false);
  }
}
