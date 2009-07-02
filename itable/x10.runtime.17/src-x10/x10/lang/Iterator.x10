/*
 *
 * (C) Copyright IBM Corporation 2006-2008.
 *
 *  This file is part of X10 Language.
 *
 */

package x10.lang;

import x10.compiler.NativeRep;
import x10.compiler.Native;

@NativeRep("java", "x10.core.Iterator<#1>", null, null)
@NativeRep("c++", "x10aux::ref<x10::lang::Iterator<#1 > >", "x10::lang::Iterator<#1 >", null)
public interface Iterator[+T] {
    @Native("java", "#0.hasNext()")
    @Native("c++", "(__extension__ ({x10aux::ref<x10::lang::Iterator<FMGL(T)> > _ = (#0); x10aux::findITable<x10::lang::Iterator<FMGL(T)> >(_)->hasNext(_);}))")
    public def hasNext(): boolean;
    
    @Native("java", "#0.next()")
    @Native("c++", "(__extension__ ({x10aux::ref<x10::lang::Iterator<FMGL(T)> > _ = (#0); x10aux::findITable<x10::lang::Iterator<FMGL(T)> >(_)->next(_);}))")
    public def next():T;
}
