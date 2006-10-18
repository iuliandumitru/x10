package polyglot.ext.x10.extension;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.Local;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.frontend.ExtensionInfo;
import polyglot.ext.jl.ast.Block_c;
import polyglot.ext.jl.ast.Call_c;
import polyglot.ext.jl.ast.ClassBody_c;
import polyglot.ext.jl.ast.Formal_c;
import polyglot.ext.jl.ast.MethodDecl_c;
import polyglot.ext.jl.types.ClassType_c;
import polyglot.ext.x10.types.X10TypeSystem;
import polyglot.ext.x10.types.X10TypeSystem_c;
import polyglot.types.MethodInstance;
import polyglot.types.ClassType;
import polyglot.types.PrimitiveType;
import polyglot.types.Type;
import polyglot.types.Package;
import polyglot.types.SemanticException;
import polyglot.util.Position;


/**
 * Implementation of extern (previously known as native) calls.
 * Check class bodies for 'extern' keyword (aka native)
 * and generate approriate wrappers and stubs to support
 * the simplified JNI-like interface to native code from X10.
 *
 * @author donawa
 * @author igor
 */
public class X10ClassBodyExt_c extends X10Ext_c {

       private final boolean useSunMiscUnsafe=false;
	private BufferedWriter wrapperFile;
	X10TypeSystem typeSystem;

	/* name of unsafe array method used to pass address to native calls */
	private final String KgetUnsafeAddressMethod = "getUnsafeAddress";
	private final String KgetBackingArrayMethod = "getBackingArray";

	/* name of unsafe array descriptor (itself an array) */
	private final String KgetUnsafeDescriptorMethod = "getUnsafeDescriptor";
       /* alternative descriptor used when not implemented with sun.misc.unsafe */
        private final String KgetDescriptorMethod =  "getDescriptor";
	private final String KdescriptorNameSuffix = "_x10DeScRiPtOr";
	private final String KPtrNameSuffix = "_x10PoInTeR";
	String[] wrapperPrologue = {
			"/*Automatically generated -- DO NOT EDIT THIS FILE */\n",
			"#include <sys/types.h>\n", "#include <jni.h>\n",
			"#ifdef __cplusplus\n", "extern \"C\" {\n", "#endif\n", "" };

	String[] wrapperEpilogue = { "\n", "#ifdef __cplusplus\n", "}\n",
			"#endif\n" };

	private void generateWrapperPrologue() {

		try {
			for (int i = 0; i < wrapperPrologue.length; ++i) {
				wrapperFile.write(wrapperPrologue[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error("Problems writing to " + wrapperFile);
		}
	}

	private void generateWrapperEpilogue() {
		try {
			for (int i = 0; i < wrapperEpilogue.length; ++i) {
				wrapperFile.write(wrapperEpilogue[i]);
			}
			wrapperFile.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error("Problems with " + wrapperFile );
		}
	}

	/**
	 * Create a text file with suffix _x10stub.c, one for each outermost class which
	 * contains x10 extern methods
	 * @param containingClassName
	 */
	private void createWrapperFile(String containingClassName, File output_dir) {
		String fileName = containingClassName + "_x10stub.c";
		Date timeStamp = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat();

		try {
			wrapperFile = new BufferedWriter(new FileWriter(new File(output_dir, fileName)));
			wrapperFile.write("/*\n * Filename:"+fileName +
					"\n * Generated: "+formatter.format(timeStamp)+" */\n");

		} catch (IOException e) {
			e.printStackTrace();
			throw new Error("Problems writing to "+wrapperFile);
		}
	}

	private String typeToCType(Type theType) {
		if (theType.isPrimitive()) {
			return typeToCString(theType);
		}
		else if (theType.isArray()){
	 	   return typeToCType(theType.toArray().base())+"*";
                }
		else // theType.isClass()
		{
			if (typeSystem.isX10Array(theType)) {
				Type base = typeSystem.baseType(theType);
				if (!base.isPrimitive())
					return "jobject*";
				return typeToCString(base)+"*";
			}
			return "jobject";
		}
	}

	private String typeToCString(Type theType) {
		if (theType.isPrimitive()) {
			//System.out.println(theType.toString() + " is primitive");
                        if (theType.isInt())
				return "signed int";
			if (theType.isChar())
				return "signed short";
			if (theType.isBoolean())
				return "unsigned char";
			if (theType.isByte())
				return "signed char";
			if (theType.isShort())
				return "signed short";
			if (theType.isLong())
				return "jlong"; // best to use jni.h's defn
			if (theType.isFloat())
				return "float";
			if (theType.isDouble())
				return "double";
			if (theType.isVoid())
				return "void";

			throw new Error("Unexpected type" + theType.toString());
		}
		else {
                   if(useSunMiscUnsafe)
			return "jlong";//xlated to long getUnsafeAddress();
                   else {
                      if(!theType.isArray()) throw new Error("Unexpected type"+theType.toString());
                      Type baseType = theType.toArray().base();
                      if(!baseType.isPrimitive()) throw new Error("Only primitive arrays are supported, not "+theType.toString());
                      return typeToCString(baseType)+"Array";
                   }
                }
	}

	private String typeToJavaSigString(Type theType) {
		if (theType.isPrimitive()) {
			//System.out.println(theType.toString() + " is primitive");
		
			if (theType.isInt())
				return "I";
			if (theType.isChar())
				return "J";
			if (theType.isBoolean())
				return "Z";
			if (theType.isByte())
				return "B";
			if (theType.isShort())
				return "S";
			if (theType.isLong())
				return "J";
			if (theType.isFloat())
				return "F";
			if (theType.isDouble())
				return "D";
			if (theType.isVoid())
				return "V";
			throw new Error("Unexpected type" + theType.toString());
		} else {
                   if(useSunMiscUnsafe)
                      return "J";// should have been xlated to long getUnsafeAddress();
                   else {
                      if(!theType.toArray().isArray()) throw new Error("Unexpected type"+theType.toString());
                      Type baseType = theType.toArray().base();
                      if(!baseType.isPrimitive()) throw new Error("Only primitive arrays are supported, not "+theType.toString()); 
                      return "]"+typeToJavaSigString(baseType);
                   }
                }
	}

	private String generateJavaSignature(MethodDecl_c method) {
		MethodInstance mi = method.methodInstance();
		String signature = "";// "("

		for (ListIterator i = method.formals().listIterator(); i.hasNext();) {
			Formal_c parameter = (Formal_c) i.next();
			signature += typeToJavaSigString(parameter.declType());
		}
		if (false) signature += ")" + typeToJavaSigString(mi.returnType());
		return signature;
	}

	private static final String zeros = "0000";
	/**
	 * replace '_' with '_1'
	 *         '<unicode>' with '_0<unicode>'
	 *         ';' with '_2'
	 *         '[' with '_3'
	 * @param inName
	 * @return
	 */
	private String JNImangle(String inName) {
		char [] charName = inName.toCharArray();
		StringBuffer buffer = new StringBuffer(inName.length());
		for (int i = 0; i < inName.length(); ++i) {
			char ch = inName.charAt(i);

			switch (ch) {
				case '_':
					buffer.append("_1");
					break;
				case ';':
					buffer.append("_2");
					break;
				case '[':
					buffer.append("_3");
					break;
				case '.':
					buffer.append("_");
					break;
				default:
					if (Character.isLetterOrDigit(ch))
						buffer.append(ch);
					else {
						String hex = Integer.toHexString((int)ch);
						hex = zeros.substring(hex.length()) + hex;
						buffer.append("_0").append(hex);
					}
					break;
			}
		}
		if(false)System.out.println("convert from "+inName+" to "+buffer.toString());
		return buffer.toString();
	}

	private static final String JNI_PREFIX = "Java_";

	/**
	 * Apply same mangling algorithm as javah does so we can automatically
	 * generate a name for the JNI code.
	 * @param method
	 * @param isOverloaded
	 * @return
	 */
	private String generateJNIName(MethodDecl_c method, boolean isOverloaded) {
		String name = JNI_PREFIX +
			JNImangle(canonicalTypeString(method.methodInstance().container())) +
			"_" + JNImangle(generateX10NativeName(method));
		if (isOverloaded)
			name += "__" + generateJavaSignature(method);
		return name;
	}

	/**
	 * Apply a milder mangling algorithm (that of Sun's javah).
	 * Needed for broken VMs.
	 * @param method
	 * @param isOverloaded
	 * @return
	 */
	private String generateJNIAlias(MethodDecl_c method, boolean isOverloaded) {
		String name = JNI_PREFIX +
			JNImangle(method.methodInstance().container().toString()) +
			"_" + JNImangle(generateX10AliasName(method));
		if (isOverloaded)
			name += "__" + generateJavaSignature(method);
		return name;
	}

	/**
	 * Convert the input type to a canonical fully-qualified string,
	 * with '.'s separating packages, and '$'s separating nested classes.
	 * @param t the type (must be a class type)
	 */
	private String canonicalTypeString(Type t) {
		String s = "";
		ClassType cl = t.toClass();
		for (; cl.isNested(); cl = cl.outer()) {
			if (cl.isAnonymous())
				throw new RuntimeException("Anonymous inner classes not supported yet");
			s = "$" + cl.name() + s;
		}
		return cl.fullName() + s;
	}

	private String generateX10NativeName(MethodDecl_c method) {
		return JNImangle(canonicalTypeString(method.methodInstance().container())) + "_" + method.name();
	}

	private String generateX10AliasName(MethodDecl_c method) {
		return JNImangle(method.methodInstance().container().toString()) + "_" + method.name();
	}

	/**
	 * if use sun.misc.unsafe then
	 * change the name of native Method and any non-primitive parameters
	 * to type long, with a following long to hold the descriptor (it's
	 * assumed non-primitives will only be arrays)
	 * if instead we use JNI methods to get the base address, then pass
	 * the array object and the descriptor (an array of ints) to the native method
	 * @param nativeMethod
	 * @param nf node factory
	 * @return new method declaration of java jni call
	 */
	private MethodDecl_c createNewNative(MethodDecl_c nativeMethod, NodeFactory nf) {

		MethodInstance mi = nativeMethod.methodInstance();

		String nativeName = generateX10NativeName(nativeMethod);
		MethodDecl_c newNative = (MethodDecl_c)nativeMethod.name(nativeName);
		ArrayList newFormals = new ArrayList();

		TypeNode longType = nf.CanonicalTypeNode(nativeMethod.position(), typeSystem.Long());
		TypeNode arrayOfIntType = nf.CanonicalTypeNode(nativeMethod.position(), 
							       typeSystem.arrayOf(nativeMethod.position(),typeSystem.Int()));
		boolean seenNonPrimitive = false;
		/* for sun.misc.unsafe,
		 * any objects that are not primitive will be unsafe arrays and
		 * transformed to getUnsafeAddress calls, so just map to type long
		 * else pass the array object and add in the descriptor one
                 * 
                 * otherwise: determine type of backing array as pass it as well
                 * as the descriptor array (an array of ints)
		 */
		for (ListIterator i = nativeMethod.formals().listIterator(); i.hasNext();) {
			Formal_c parameter = (Formal_c) i.next();
			if (parameter.declType().isPrimitive())
				newFormals.add(parameter);
			else {
				seenNonPrimitive = true;
				if(useSunMiscUnsafe) {
				  newFormals.add(parameter.type(longType)); //one for array
				  Formal_c paramDescriptor = (Formal_c)parameter.name(parameter.name() + KdescriptorNameSuffix);
				  newFormals.add(paramDescriptor.type(longType)); // another for descriptor
				}
				else {
				  ClassType_c ct = (ClassType_c)parameter.declType().toClass();
				  MethodInstance backingMethod = findMethod(ct,KgetBackingArrayMethod);
				  if(null == backingMethod) throw new Error("Could not find "+KgetBackingArrayMethod+" in class "+ct);
				  TypeNode theReturnType = nf.CanonicalTypeNode(nativeMethod.position(),backingMethod.returnType());
				  newFormals.add(parameter.type(theReturnType));
				  Formal_c paramDescriptor = (Formal_c)parameter.name(parameter.name() + KdescriptorNameSuffix);
				  newFormals.add(paramDescriptor.type(arrayOfIntType)); // another for descriptor
				}
			}
		}
		if (seenNonPrimitive) {
			newNative = (MethodDecl_c)newNative.formals(newFormals);
		}
		return newNative;
	}

/* search for the given method.  The method is expected to be found in the regular hierarchy,
 * but it may be in an interface--if initial search fails, start looking in the interfaces
 */
        private  MethodInstance findMethod(ClassType_c theClass,String targetName){
		MethodInstance targetMI=null,memberMI=null;
		final boolean trace=false;
                ClassType_c currentClass=theClass;
                while(currentClass!=null) {
                   if(trace) System.out.println("Searching class "+currentClass);
   		   List methods = currentClass.methods();
                   for(ListIterator j = methods.listIterator();j.hasNext();){
                   memberMI = (MethodInstance)j.next();
                   if(trace) System.out.println("inspecting member:"+memberMI.name());
                   if (memberMI.name().equals(targetName))
                      return memberMI;
                   }
                   currentClass = (ClassType_c)currentClass.superType();
                }
                /* now start looking in the interfaces...*/ 
                if(trace) System.out.println("Search the interfaces....");
                currentClass = theClass;
                while(currentClass!=null) {
                   List interfaceMethods = currentClass.interfaces();

                   for (ListIterator j = interfaceMethods.listIterator(); j.hasNext();) {
                      ClassType_c implementationClass = (ClassType_c)j.next();
                      if(trace)System.out.println("looking at interface "+implementationClass);
                      List methods = implementationClass.methods();

                      for(ListIterator k = methods.listIterator();k.hasNext();){
                         memberMI = (MethodInstance)k.next();
                         if(trace) System.out.println("inspecting member:"+memberMI.name());
                         if (memberMI.name().equals(targetName))
                            return memberMI;
                      }
                   }
                   currentClass = (ClassType_c)currentClass.superType();
                }
		return null;
        }

	/**
	 * Create java wrapper which calls a JNI call which implements
	 * interface with X10 extern
	 * @param nativeMethod
	 * @param nf node factory
	 * @return wrapper method
	 */
	private MethodDecl_c createNativeWrapper(MethodDecl_c nativeMethod, NodeFactory nf) {
		boolean trace = false;
		nativeMethod = (MethodDecl_c)nativeMethod.flags(nativeMethod.flags().clearNative());
		MethodInstance mi = nativeMethod.methodInstance();
		Position pos = nativeMethod.position();
		MethodDecl_c nativeWrapper = nativeMethod;

		ArrayList newArgs = new ArrayList();
		String jniName = generateX10NativeName(nativeMethod);

		TypeNode receiver = nf.CanonicalTypeNode(pos, mi.container());

		Call jniCall = nf.Call(pos, receiver, jniName, newArgs);
		jniCall = (Call_c)jniCall.targetImplicit(true);
		jniCall = (Call_c)jniCall.methodInstance(mi);

                String descriptorName = useSunMiscUnsafe?KgetUnsafeDescriptorMethod:KgetDescriptorMethod;
	
		ArrayList args = new ArrayList();
		for (ListIterator i = nativeMethod.formals().listIterator(); i.hasNext();) {
			Formal_c parameter = (Formal_c) i.next();

			if (parameter.declType().isPrimitive()) {
				Local arg= nf.Local(pos, parameter.name());
				// RMF 11/2/2005 - Set the type of the Local. This would normally
				// be handled by type-checking, but we're past that point now...
				arg= (Local) arg.type(parameter.declType());
				// RMF 7/10/2006 - Make sure the Local has an associated LocalInstance;
				// the InitChecker will need to see it...
				arg= (Local) arg.localInstance(typeSystem.localInstance(pos, parameter.flags(), arg.type(), arg.name()));
				args.add(arg);
			} else {
				ClassType_c ct = (ClassType_c)parameter.declType().toClass();
				if (null == ct)
					throw new Error("Problems with unsafe array "+parameter.name());

				if (trace)System.out.println("Processing "+parameter.name()+"::"+parameter);

				/**
				 * Look for the implementation of the Unsafe interface.  Start in this method
				 * and keep looking up inheritance tree.
				 */
				MethodInstance memberMI = null;
				MethodInstance unsafeBufferMI = null, arrayDescriptorMI = null, backingArrayMI=null;
				ClassType_c currentClass = ct;
				boolean doneSearch = false;
                               
				while (currentClass != null && !doneSearch) {
					List interfaceMethods = currentClass.interfaces();
                               
					for (ListIterator j = interfaceMethods.listIterator(); j.hasNext();) {
						ClassType_c implementationClass = (ClassType_c)j.next();
                                                if(trace)System.out.println("looking at interface "+implementationClass);
						List methods = implementationClass.methods();
						for (ListIterator k = methods.listIterator(); k.hasNext();) {
							memberMI = (MethodInstance) k.next();
							if (trace) System.out.println("inspecting interface member:"+ memberMI.name());
							if (memberMI.name().equals(KgetUnsafeAddressMethod))
								unsafeBufferMI = memberMI;
							if (memberMI.name().equals(descriptorName))
								arrayDescriptorMI = memberMI;
                                                        if(memberMI.name().equals(KgetBackingArrayMethod))
                                                           backingArrayMI = memberMI;
                                                         

                                                        if(arrayDescriptorMI!=null && (!useSunMiscUnsafe || unsafeBufferMI!=null))
								break;
							
						}
					}

                                        /* look for getBackingArray method.  Note that it is not an interface */
					if(!useSunMiscUnsafe && backingArrayMI == null){
                                           backingArrayMI = findMethod(currentClass,KgetBackingArrayMethod);
					}

					if (arrayDescriptorMI != null && 
					    (useSunMiscUnsafe?(unsafeBufferMI != null):(backingArrayMI !=null))){
						doneSearch = true;
					}
					currentClass = (ClassType_c)currentClass.superType();
				}
				if (useSunMiscUnsafe && null == unsafeBufferMI)
					throw new Error("Could not find "+KgetUnsafeAddressMethod+" in class "+ ct.fullName());
				if (null == arrayDescriptorMI) 
					throw new Error("Could not find "+descriptorName+" in class "+ ct.fullName());
				if (!useSunMiscUnsafe && null == backingArrayMI)
					throw new Error("Could not find "+KgetBackingArrayMethod+" in class "+ ct.fullName());
				

				Local getAddrTarget= nf.Local(pos, parameter.name());
			        // RMF 11/3/2005 - Set the type of getAddr call's target. This
				// would normally be handled by type-checking, but we're past
				// that point now...
				getAddrTarget= (Local) getAddrTarget.type(parameter.type().type());
				// RMF 7/10/2006 - Make sure the Local has an associated LocalInstance;
				// the InitChecker will need to see it...
				getAddrTarget= (Local) getAddrTarget.localInstance(typeSystem.localInstance(pos, parameter.flags(), 
                                                                                                            getAddrTarget.type(), 
                                                                                                            getAddrTarget.name()));
				
                                if(useSunMiscUnsafe){
                                   Call getAddr = nf.Call(pos, getAddrTarget,KgetUnsafeAddressMethod);
                                   getAddr = (Call_c)getAddr.methodInstance(unsafeBufferMI);
                                   // RMF 11/3/2005 - Set the type of getAddr call. This would
                                   // normally be handled by type-checking, but we're past that
                                   // point now...
                                   getAddr = (Call) getAddr.type(unsafeBufferMI.returnType());
                                   args.add(getAddr);
                                }
                                else {
                                   Call getAddr = nf.Call(pos, getAddrTarget, KgetBackingArrayMethod);
                                   getAddr = (Call_c)getAddr.methodInstance(backingArrayMI);
                                   // RMF 11/3/2005 - Set the type of getAddr call. This would
                                   // normally be handled by type-checking, but we're past that
                                   // point now...
                                   getAddr = (Call) getAddr.type(backingArrayMI.returnType());
                                   args.add(getAddr);
                                }
		     


				Call getDescriptor = nf.Call(pos, getAddrTarget, descriptorName);
				getDescriptor = (Call_c)getDescriptor.methodInstance(arrayDescriptorMI);
				// RMF 11/3/2005 - Set the type of getDescriptor call. This
				// would normally be handled by type-checking, but we're past
				// that point now...
				getDescriptor = (Call) getDescriptor.type(arrayDescriptorMI.returnType());
				args.add(getDescriptor);

			}
		}

		jniCall = (Call_c)jniCall.arguments(args);

		// RMF 11/2/2005 - Set type of jniCall to that of method's return type.
		// This would ordinarily be taken care of by type-checking, but we're
		// past that point...
		jniCall = (Call) jniCall.type(nativeMethod.returnType().type());

		ArrayList newStmts = new ArrayList();
		if (nativeMethod.methodInstance().returnType().isVoid())
			newStmts.add(nf.Eval(pos, (Expr)jniCall));
		else
			newStmts.add(nf.Return(pos, (Expr)jniCall));

		Block_c newBlock = (Block_c)nf.Block(pos, newStmts);

		nativeWrapper = (MethodDecl_c)nativeWrapper.body(newBlock);
		return nativeWrapper;
	}

	private String maybeCast(String to, String from) {
		if (!to.equals(from))
			return "("+to+")";
		return "";
	}

	private String maybeCast(Type theType) {
		return maybeCast(typeToCType(theType), typeToJNIString(theType));
	}

	private String maybeUncast(Type theType) {
		return maybeCast(typeToJNIString(theType), typeToCType(theType));
	}


       private String generateAcquireStmt(String arrayName,String ptrName) {
          String stmt ="#ifdef __cplusplus\n";
          stmt +="  void *"+ptrName+" = (env)->GetPrimitiveArrayCritical("+arrayName+",0);\n";
          stmt += "#else\n";
          stmt +="  void *"+ptrName+" = (*env)->GetPrimitiveArrayCritical(env,"+arrayName+",0);\n";
          stmt += "#endif\n";
          return stmt;
       }
       private String generateReleaseStmt(String arrayName,String ptrName) {
             String stmt = "#ifdef __cplusplus\n";
             stmt +="  (env)->ReleasePrimitiveArrayCritical("+arrayName+","+ptrName+",0);\n"; 
             stmt += "#else\n";
             stmt +="  (*env)->ReleasePrimitiveArrayCritical(env,"+arrayName+","+ptrName+",0);\n";
             stmt += "#endif\n";
            return stmt;
         }


      /**
       * scan args and ensure they conform.  
       * @param nativeMethod
       */
       private void parseArgsForCorrectness(MethodDecl_c nativeMethod) {
		for (ListIterator i = nativeMethod.formals().listIterator();
				i.hasNext();)
		{
			Formal_c parameter = (Formal_c) i.next();
			if(parameter.declType().isArray()){
			  if(!parameter.declType().toArray().base().isPrimitive()){
			      System.out.println("ERROR"+parameter.position()+":Native call parameter \""+parameter+
			                         "\" is invalid.  Only primitives, arrays of primitives and X10 arrays are allowable arguments.");
			      System.exit(2);
			  }
			      System.out.println("ERROR"+parameter.position()+":Native call parameter \""+parameter+
			                         "\" is invalid.  For now, java arrays are unsupported.");
			      System.exit(2);

			}
			else if(!parameter.declType().isPrimitive()){
			   ClassType_c ct = (ClassType_c)parameter.declType().toClass();
			   if(true)System.out.println("looking at parm type:"+ct);

			   if(!typeSystem.isX10Array(ct)) {
			      System.out.println("ERROR"+parameter.position()+":Native call parameter \""+parameter+
			                         "\" is invalid.  Only primitives and X10 arrays are allowable arguments.");
			      System.exit(2);
			   }

			}
			   
		}
       }
	/**
	 * Create C stub that user will later compile into a dynamic library
	 * contains JNI signature C code which calls the expected X10 routine
	 * @param nativeMethod
	 * @param isOverloaded
	 */
	private void generateStub(MethodDecl_c nativeMethod, boolean isOverloaded) {

		String newName = generateX10NativeName(nativeMethod);
		if (isOverloaded)
			newName += "__" + generateJavaSignature(nativeMethod);

		String jniCall, wrapperCall, wrapperDecl, saveTheValue = "";

		wrapperCall = "  "+newName + "(";
		wrapperDecl = "extern " + typeToCType(nativeMethod.methodInstance().returnType()) + " ";
		wrapperDecl += wrapperCall;

		newName = generateJNIName(nativeMethod, isOverloaded);

		String parm = nativeMethod.flags().isStatic()
						? "jclass cls" : "jobject obj";

		jniCall = "JNIEXPORT "
				+ typeToJNIString(nativeMethod.methodInstance().returnType())
				+ " JNICALL\n" + newName + "(JNIEnv *env, " + parm;

		String returnedValue="";
		if (!nativeMethod.methodInstance().returnType().isVoid()){
			String tempName="_x10ReTuRnVaL";
			saveTheValue = typeToCType(nativeMethod.methodInstance().returnType()) + 
			" "+ tempName+"="+
			maybeUncast(nativeMethod.methodInstance().returnType());
			returnedValue="return "+tempName+";\n";
		}
		String commaString = "";
                String releaseStmts="";
                String acquireStmts="";

		for (ListIterator i = nativeMethod.formals().listIterator();
				i.hasNext();)
		{
			Formal_c parameter = (Formal_c) i.next();
			if (useSunMiscUnsafe || parameter.declType().isPrimitive()) { 
			   jniCall += ", " + typeToJNIString(parameter.declType())
					   + " " + parameter.name();
			   wrapperDecl += commaString
					+ typeToCType(parameter.declType()) + " "
					+ parameter.name();
			   wrapperCall += commaString + maybeCast(parameter.declType()) + parameter.name();
			}
			else {
			   String arrayPtr = parameter.name()+KPtrNameSuffix;
                           acquireStmts += generateAcquireStmt(parameter.name(),arrayPtr);
                           releaseStmts = generateReleaseStmt(parameter.name(),arrayPtr) + releaseStmts; // release in reverse order
			   
			   ClassType_c ct = (ClassType_c)parameter.declType().toClass();
			   MethodInstance backingMethod = findMethod(ct,KgetBackingArrayMethod);
			   if(null == backingMethod) throw new Error("Could not find "+KgetBackingArrayMethod+" in class "+ct);

			   jniCall += ", " + typeToJNIString(backingMethod.returnType())
					   + " " + parameter.name();
			   wrapperDecl += commaString
					+ typeToCType(backingMethod.returnType()) + " "
					+ arrayPtr;
			   wrapperCall += commaString + maybeCast(backingMethod.returnType()) + arrayPtr;
			   
			}

			// if we see an array type there must be a descriptor right after
			if (!parameter.declType().isPrimitive()) {
                           if(useSunMiscUnsafe){
                              String descriptorName = parameter.name()+KdescriptorNameSuffix;
                              jniCall += ", jlong " + descriptorName;
                              wrapperCall += ", (int*)" + descriptorName;
                              wrapperDecl += ", int* " + descriptorName;
                           }
                           else {
                              String descriptorName = parameter.name()+KdescriptorNameSuffix;
                              String descriptorPtrName = descriptorName+KPtrNameSuffix;
                              jniCall += ", jintArray " + descriptorName;
                              acquireStmts += generateAcquireStmt(descriptorName,descriptorPtrName);
                              releaseStmts = generateReleaseStmt(descriptorName,descriptorPtrName)+ releaseStmts;
  
                              wrapperCall += ", (int*) "+descriptorPtrName;
                              wrapperDecl += ", int* " + descriptorName;  

                           }

			}

			commaString = ", ";
		}
		jniCall += ")";
		wrapperCall += ")";
		wrapperDecl += ");";
                

		String jniAlias = "";
		// Only generate the alias if inner class
		if (nativeMethod.methodInstance().container().toClass().isNested()) {
			String aliasName = generateJNIAlias(nativeMethod, isOverloaded);

			jniAlias = "#ifndef __WIN32__\nextern JNIEXPORT __typeof("
					 + newName + ") JNICALL\n" + aliasName
					 + "\n__attribute((alias(\""
					 + newName + "\")));\n#endif\n\n";
		}

		try {
                String getcrit="/*call get critical section*/\n";
			wrapperFile.write("\n/* * * * * * * */\n"+wrapperDecl + "\n"+jniCall + " {\n" +
                                          acquireStmts+"\n"+
					  "\n"+
                                          saveTheValue + wrapperCall
                                          + ";\n\n"+
                                          releaseStmts+
					  returnedValue+
                                          "}\n" + jniAlias);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error("Problems writing file");
		}
	}

	/**
	 * Primitive types get translated to their corresponding JNI type.
	 * Any non-primitive type should be an unsafe memory type,
	 * and get translated to a call to getUnsafeAddress, which
	 * returns an address, which we will put into a long
	 * @param theType
	 * @return JNI name for the type
	 */
	private String typeToJNIString(Type theType) {
		if (theType.isPrimitive()) {
			//System.out.println(theType.toString() + " is primitive");
			if (theType.isInt())
				return "jint";
			if (theType.isBoolean())
				return "jboolean";
			if (theType.isByte())
				return "jbyte";
			if (theType.isShort())
				return "jshort";
			if (theType.isLong())
				return "jlong";
			if (theType.isFloat())
				return "jfloat";
			if (theType.isChar())
				return "jchar";
			if (theType.isDouble())
				return "jdouble";
			if (theType.isVoid())
				return "void";
                        throw new Error("Unhandled type:"+theType);
		} else {
		   if(useSunMiscUnsafe)
			return "jlong";// should be a call to long getUnsafeAddress()
		   else if(theType.isArray())
		       return typeToJNIString((theType.toArray()).base())+"Array";
		   
                }
	
		return "<unknown>";
	}

	private static int containingClassDepth = 0; // use to create stub file for outermost class w/ natives

	/**
	 * Identify native (aka extern) x10 methods and create a wrapper with
	 * the same name.  The wrapper make a JNI call to a routine which
	 * then calls the expected X10 native call.
	 * e.g.
	 * <code>
	 * class C {
	 *   static int extern foo(int x);
	 * }
	 * </code>
	 * would result in java code
	 * <code>
	 * class C {
	 *   static int native C_foo(int x);
	 *   static int foo(int x) { return C_foo(x); }
	 * }
	 * </code>
	 *
	 * <code>C_foo</code> is a native C call, which would end up looking like
	 * <code>int Java_C_C_1foo(int x) { return C_foo(x); }</code>
	 * Stub files for each containing class are generated containing
	 * the C wrappers.  It is up to the user to compile these, along
	 * with the actual native implementation of <code>C_foo(int)</code>, into
	 * a dynamic library, and ensure that the X10 program can find them
	 */
	public Node rewrite(X10TypeSystem ts, NodeFactory nf, ExtensionInfo info) {
		typeSystem = ts;
		PrimitiveType tt = ts.Long();
		boolean seenNativeMethodDecl = false;

		ClassBody_c cb = (ClassBody_c) node();
		List members = cb.members();
		Map methodHash = null;
		ArrayList newListOfMembers = new ArrayList();
		for (ListIterator i = members.listIterator(); i.hasNext();) {
			Object o = i.next();
			if (o instanceof MethodDecl) {
				MethodDecl_c md = (MethodDecl_c) o;
				MethodInstance mi = md.methodInstance();

				if (!mi.flags().isNative()) {
					newListOfMembers.add(o);
					continue;
				}

				parseArgsForCorrectness(md);

				if (!seenNativeMethodDecl) {
					// JNI signature changes depends on whether the method
					// is overloaded or not.  Determine that by scanning
					// through and hashing all native method names
					methodHash = buildNativeMethodHash(members);
					if (0 == containingClassDepth++) {
						createWrapperFile(JNImangle(canonicalTypeString(mi.container())),
										  info.getOptions().output_directory);
						generateWrapperPrologue();
					}
					seenNativeMethodDecl = true;
				}

				boolean isOverLoaded = (null != methodHash.get(md.name()));

				//System.out.println("method: "+md.name() +" overload:"+isOverLoaded);
				generateStub(md, isOverLoaded);
				newListOfMembers.add(createNewNative(md, nf));
				newListOfMembers.add(createNativeWrapper(md, nf));
			}
			else
				newListOfMembers.add(o);
		}

		if (seenNativeMethodDecl) {
			--containingClassDepth;
			if (0 == containingClassDepth)
				generateWrapperEpilogue();
		}

		cb = (ClassBody_c)cb.members(newListOfMembers);
		return cb;
	}

	private Map buildNativeMethodHash(List members) {
		Map methodHash = new HashMap();
		for (ListIterator j = members.listIterator(); j.hasNext();) {
			Object theObj = j.next();
			if (!(theObj instanceof MethodDecl))
				continue;
			MethodDecl_c methodDecl = (MethodDecl_c) theObj;
			if (!methodDecl.methodInstance().flags().isNative())
				continue;

			if (methodHash.containsKey(methodDecl.name())) {
				methodHash.put(methodDecl.name(), methodDecl); // more than one instance
			} else {
				methodHash.put(methodDecl.name(), null);
			}
		}
		return methodHash;
	}
}

