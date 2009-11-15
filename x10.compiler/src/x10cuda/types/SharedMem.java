package x10cuda.types;

import java.util.ArrayList;
import java.util.Iterator;

import polyglot.ast.Expr;
import polyglot.ast.LocalDecl;
import polyglot.types.Name;
import polyglot.types.Type;
import x10.types.X10TypeSystem;
import x10.util.ClassifiedStream;

public class SharedMem {
    
    ArrayList<Decl> decls = new ArrayList<Decl>();
    
    private abstract static class Decl {
        public final LocalDecl ast;
        public long bytes() {
            Type t = ast.type().type();
            X10TypeSystem xts = (X10TypeSystem) t.typeSystem();
            Long bytes = xts.size(t);
            assert bytes != null : t;
            return bytes.longValue();
        }
        public Decl (LocalDecl ast) { this.ast = ast; }
        abstract public void generateDef(ClassifiedStream out, String offset);
        abstract public void generateInit(ClassifiedStream out, String offset);
    }
    
    private static class Rail extends Decl {
        public final Expr numElements;
        public final Expr init;
        public long bytes() {
            return super.bytes() * 1; //numElements.constantValue();
        }
        public Rail (LocalDecl ast, Expr numElements, Expr init) {
            super(ast);
            this.numElements = numElements;
            this.init = init;
        }
        public void generateDef(ClassifiedStream out, String offset) {
            String name = ast.name().id().toString();
            out.write("float *"+name+" = (float*) &__shm["+offset+"];"); out.newline();
        }
        public void generateInit(ClassifiedStream out, String offset) {
            out.write("for (int i=0 ; i<CLUSTERS*4 ; ++i) {"); out.newline(4); out.begin(0);
            out.write("clustercache[i] = /**/local_clusters[i]/**/;"); out.newline();
            out.end(); out.newline();
            out.write("}");
        }
    }
    private static class Var extends Decl {
        public Var (LocalDecl ast) { super(ast); }
        public void generateDef(ClassifiedStream out, String offset) {
        }
        public void generateInit(ClassifiedStream out, String offset) {
        }
    }
    
    public void addRail(LocalDecl ast, Expr numElements, Expr init) {
        decls.add(new Rail(ast,numElements,init));
    }

    public void addVar(LocalDecl ast) {
        decls.add(new Var(ast));
    }
    
    public boolean has(Name n) {
        for (Decl d : decls) {
            if (d.ast.name().id() == n) {
                return true;
            }
        }
        return false;
    }

    public void generateCode(ClassifiedStream out) {
        if (decls.size()==0) return;

        out.write("// shm");
        out.newline();
        
        for (SharedMem.Decl d : decls) {
                String offset = "0";
                d.generateDef(out, offset);
                out.write("if  (threadIdx.x == 0) {"); out.newline(4); out.begin(0);
                d.generateInit(out, offset);
                out.end(); out.newline();
                out.write("}"); out.newline();
        }
        
        out.write("__syncthreads();"); out.newline();        
        out.forceNewline();


        /*
         * float *new_clusterv = (float*) dyn_shm; // [DIM*clusterc_odd] int
         * *new_counterv = (int*)&new_clusterv[DIM*clusterc_odd]; // [clusterc]
         */
                
    }
}
