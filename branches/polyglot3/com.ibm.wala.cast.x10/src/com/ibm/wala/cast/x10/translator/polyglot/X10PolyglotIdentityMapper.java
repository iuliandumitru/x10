package com.ibm.wala.cast.x10.translator.polyglot;

import java.util.HashMap;
import java.util.Map;

import polyglot.ext.x10.types.*;
import polyglot.types.*;

import com.ibm.wala.cast.java.translator.polyglot.*;
import com.ibm.wala.types.*;
import com.ibm.wala.util.debug.Assertions;

class X10PolyglotIdentityMapper extends PolyglotIdentityMapper {

    public X10PolyglotIdentityMapper(ClassLoaderReference clr, TypeSystem ts) {
	super(clr, ts);
    }

    private final static Map<String, String> typeTranslationMap= new HashMap<String, String>();

    static {
//	typeTranslationMap.put("x10.lang.BooleanReferenceArray", "[Z");
//	typeTranslationMap.put("x10.lang.ByteReferenceArray", "[B");
//	typeTranslationMap.put("x10.lang.CharReferenceArray", "[C");
//	typeTranslationMap.put("x10.lang.DoubleReferenceArray", "[D");
//	typeTranslationMap.put("x10.lang.FloatReferenceArray", "[F");
//	typeTranslationMap.put("x10.lang.GenericReferenceArray", "[L");
//	typeTranslationMap.put("x10.lang.IntReferenceArray", "[I");
//	typeTranslationMap.put("x10.lang.LongReferenceArray", "[J");
//	typeTranslationMap.put("x10.lang.ShortReferenceArray", "[S");
    }

    public String typeToTypeID(Type type) {
/*      if (type instanceof NullableType) {
                return "Lnullable<" + typeToTypeID(((NullableType) type).base()) + ">";
        } else if (type instanceof FutureType) {
                return "Lfuture<" + typeToTypeID(((FutureType) type).base()) + ">";
        } else 
*/        
        if (type instanceof ParametrizedType) {
            Assertions.UNREACHABLE("typeToTypeID(ParametrizedType)");
        }
        if (type.isClass()
                        && typeTranslationMap.containsKey(((ClassType) type).fullName())) {
                return typeTranslationMap.get(((ClassType) type).fullName());
        } else {
                return super.typeToTypeID(type);
        }
    }
}
