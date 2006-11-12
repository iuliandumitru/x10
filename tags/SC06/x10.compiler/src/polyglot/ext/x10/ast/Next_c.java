package polyglot.ext.x10.ast;

import java.util.List;

import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.ext.jl.ast.Stmt_c;
import polyglot.ext.x10.types.X10Context;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.TypeChecker;

public class Next_c extends Stmt_c implements Next {


    public Next_c(Position p) {
        super(p);
    }

    


    public Term entry() {
        // TODO:
        return this;
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        // TODO:
        return succs;
    }
    public Node typeCheck(TypeChecker tc) throws SemanticException {    	
    	X10Context c = (X10Context) tc.context();
    	if (c.inNonBlockingCode())
    		throw new SemanticException("The next statement cannot be used in nonblocking code.", position());
    	return super.typeCheck(tc);
    }
}
