/*
 * Created on Oct 19, 2005
 */
package com.ibm.domo.ast.x10.translator;

import com.ibm.capa.ast.CAstEntity;

public interface X10CAstEntity extends CAstEntity {
    /**
     * Kind constant for a CAstEntity representing the body of an X10 'async' or 'future'.<br>
     * The associated AST is a CAstNode for a statement representing the body (in the case of
     * an 'async') or an expression (in the case of a 'future').
     */
    public static final int ASYNC_BODY = SUB_LANGUAGE_BASE;
}
