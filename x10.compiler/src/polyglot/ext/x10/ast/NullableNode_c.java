/*
 * Created on Nov 26, 2004
 */
package polyglot.ext.x10.ast;

import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.ext.x10.types.X10Context;
import polyglot.ext.x10.types.X10NamedType;
import polyglot.ext.x10.types.X10ParsedClassType;
import polyglot.ext.x10.types.X10Type;
import polyglot.ext.x10.types.X10TypeSystem;
import polyglot.ext.x10.types.X10TypeSystem_c;
import polyglot.main.Report;
import polyglot.types.Context;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.TypeChecker;

/**
 * A NullableNode is an TypeNode that has been marked with a nullable
 * qualifier.  Note that the base TypeNode may be ambiguous and may need to
 * be resolved.
 * TODO: Find a better way of dealing with the construction of the Nullable
 * type. In the code below, this might be done twice. That doesnt seem right.
 *
 * @author vj
 */
public class NullableNode_c extends X10TypeNode_c implements NullableNode {
	
	// Recall the field this.type is defined at the supertype.
	// The Typenode representing the argument type X.
	protected TypeNode base;
	
	public NullableNode_c(Position pos, TypeNode base) {
		super(pos);
		this.base = base;
	}
	
	public TypeNode base() {
		return this.base;
	}
	
	public NullableNode base(TypeNode base) {
		NullableNode_c n = (NullableNode_c) copy();
		n.base = base;
		return n;
	}
	
	protected NullableNode reconstruct(TypeNode base) {
		return (base != this.base) ? base(base) : this;
	}
	
	public Context enterChildScope(Node child, Context c) {
		if (child == this.dep) {
			if (type instanceof X10ParsedClassType)
				c = ((X10Context) c).pushDepType((X10ParsedClassType) type);
		}
		return super.enterChildScope(child, c);
	}
	public Node visitChildren(NodeVisitor v) {
		//Report.report(1, "NullableNode_c: Visiting typeNode  " + this.base + " with " + v);
		TypeNode base = (TypeNode) visitChild(this.base, v);
		//Report.report(1, "NullableNode_c: Visiting typeNode  yielded  " + base);
		NullableNode_c result = (NullableNode_c) reconstruct(base);
		return result.superVisitChildren(v);
	}
	public Node superVisitChildren(NodeVisitor v) {
		return super.visitChildren(v);
	}
	
	/**
	 * Disambiguate the base node. Ensure that it is unambiguous.
	 * Create a NullableType_c and store it in this.type.
	 */
	public Node disambiguateBase(AmbiguityRemover sc) throws SemanticException {
		TypeNode newType = (TypeNode) base.disambiguate(sc);
		// RMF 11/2/2005 - Don't throw a SemanticException if all that's wrong is
		// that disambiguation still needs to be done on the type argument
		if (!newType.type().isCanonical())
			return this;
		NullableNode_c result = (NullableNode_c) base(newType);
		// Have to set the type for TypeNodes at the end of disambiguation.
		// This is the base case for subsequent type checking.
		// Note however that this time is not depType-accurate.
		// TypePropagation must ensure that this type is fixed up.
		return result.propagateTypeFromBase();
	}
	public TypeNode propagateTypeFromBase() {
		X10NamedType baseType = (X10NamedType) base.type();
		assert baseType !=null;
		X10TypeSystem ts = (X10TypeSystem) baseType.typeSystem();
		X10Type resultType = ts.createNullableType(position(), baseType);
		return type(resultType);
	}
	/**
	 * Typecheck the type-argument (in this.base). If it typechecks
	 * (e.g. passes visibility constraints), update type.this if necessary.
	 * Otherwise throw a semantic exception.  TODO: Ensure that visibility of
	 * this node is the same as that of the type argument.
	 */
	public Node typeCheckBase(TypeChecker tc) throws SemanticException {
		 return super.typeCheckBase(tc);
	}
	public Node typeCheck( TypeChecker tc) throws SemanticException {
		X10TypeNode newType = (X10TypeNode) base.typeCheck(tc);
		NullableNode_c result = (NullableNode_c) base(newType);
		return result.propagateTypeFromBase();
	}
	public Node oldTypeCheckBase(TypeChecker tc) throws SemanticException {
		
		if (Report.should_report("debug", 5)) {
			Report.report(5,"[NullableNode_c] Type checking |" + this +"|:");
		}
		
		Node n = base.typeCheck(tc);
		
		if (Report.should_report("debug", 5)) {
			Report.report(5,"[NullableNode_c] ... yields node |" + n +"|.");
		}
		
		if (!(n instanceof TypeNode))
			throw new SemanticException("Argument to nullable type-constructor does not type-check",
					position());
		//if (n == base)
			//return this;
		TypeNode arg = (TypeNode) n;
		X10NamedType argType = (X10NamedType) arg.type();
		X10TypeSystem ts = (X10TypeSystem) argType.typeSystem();
		X10Type resultType = ts.createNullableType(position(), argType);
		
		if (Report.should_report("debug", 5)) {
			Report.report(5, "[NullableNode_c] ... sets type to |" + this.type + "|.");
			Report.report(5, "[NullableNode_c] ... returns |" + this + "|.");
		}
		
		return this.type(resultType);
	}
	
	public String toString() {
		return "nullable<" + base.toString() + ">";
	}
	
	/**
	 * Write out Java code for this node.
	 * Hmmm.. TODO: this will need to output the BoxedType for the primitive types,
	 * and just the reference type (unchanged) otherwise.
	 */
	public void prettyPrint(CodeWriter w, PrettyPrinter pp) {
		w.write("/*nullable*/");
		base.del().prettyPrint(w, pp);
	}
	
	// translate??
	// dump?
}

