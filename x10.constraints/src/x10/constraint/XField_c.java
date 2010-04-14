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

package x10.constraint;

import java.util.List;
import java.util.Set;

public class XField_c extends XVar_c implements XField {
	public XVar receiver;
	public XName field;

	public XField_c(XVar receiver, XName field) {
		super();
		this.receiver = receiver;
		this.field = field;
	}

	public XTermKind kind() { return XTermKind.FIELD_ACCESS;}
	public XTerm subst(XTerm y, XRoot x, boolean propagate) {
		XTerm r = super.subst(y, x, propagate);
		if (! equals(r)) 
			return r;
		XVar newReceiver = (XVar) receiver.subst(y, x);
		XField_c result= clone();
		if (newReceiver != receiver) {
			result.receiver = newReceiver;
		}
		return result;
		
		/*
		XVar newReceiver = (XVar) receiver.subst(y, x);
		XField_c n = (XField_c) super.subst(y, x, propagate);
		if (newReceiver == receiver)
		    return n;
		if (n == this) n = clone();
		n.receiver = newReceiver;
		return n;
		*/
	}
	
	public List<XEQV> eqvs() {
	    return receiver().eqvs();
	}

	public XName field() {
		return field;
	}

	public String name() {
		return field.toString();
	}
	
	public boolean hasVar(XVar v) {
		return equals(v) || receiver.hasVar(v);
	}

	public XVar receiver() {
		return receiver;
	}

	public int hashCode() {
		return receiver.hashCode() + field.hashCode();
	}

	public boolean equals(Object o) {
		if (o instanceof XField_c) {
			XField_c other = (XField_c) o;
			return receiver.equals(other.receiver) && field.equals(other.field);
		}
		return false;
	}

	public String toString() {
		return (receiver == null ? "" : receiver.toString() + ".") + field;
	}

	public boolean hasEQV() {
		if (receiver() == null)
			assert false;
		return receiver().hasEQV();
	}
	

	@Override
	public XField_c clone() {
		XField_c n = (XField_c) super.clone();
		n.vars = null;
		return n;
	}
	
	// memoize rootVar and path.
	protected XVar[] vars;

	public XVar[] vars() {
		if (vars == null)
			initVars();
		return vars;
	}

	public XVar rootVar() {
		if (vars == null)
			initVars();
		return vars[0];
	}

	public boolean prefixes(XTerm t) {
		if (equals(t))
			return true;
		if (!(t instanceof XVar))
			return false;
		XVar[] vars = ((XVar) t).vars();
		boolean result = false;
		for (int i = 0; (!result) && i < vars.length; i++) {
			result = equals(vars[i]);
		}
		return result;
	}

	protected void initVars() {
		int count = 0;
		for (XVar source = this; source instanceof XField; source = ((XField) source).receiver())
			count++;
		vars = new XVar[count + 1];
		XVar f = this;
		for (int i = count; i >= 0; i--) {
			vars[i] = f;
			if (i > 0)
				f = ((XField) f).receiver();
		}

	}
}
