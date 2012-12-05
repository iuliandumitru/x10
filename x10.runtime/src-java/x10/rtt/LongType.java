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

package x10.rtt;

import x10.serialization.X10JavaDeserializer;
import x10.serialization.X10JavaSerializable;
import x10.serialization.X10JavaSerializer;

import java.io.IOException;
import java.lang.reflect.Array;

public final class LongType extends RuntimeType<x10.core.Long> {
//public final class LongType extends RuntimeType<x10.core.Long> implements X10JavaSerializable {

    private static final long serialVersionUID = 1L;
//    private static final short _serialization_id = x10.x10rt.DeserializationDispatcher.addDispatcher(DeserializationDispatcher.ClosureKind.CLOSURE_KIND_NOT_ASYNC, LongType.class.getName());

    // make sure deserialized RTT object is not duplicated
    private Object readResolve() throws java.io.ObjectStreamException {
        return Types.LONG;
    }

    public LongType() {
        super(x10.core.Long.class,
            new Type[] {
                ParameterizedType.make(Types.COMPARABLE, UnresolvedType.THIS),
                ParameterizedType.make(x10.lang.Arithmetic.$RTT, UnresolvedType.THIS),
                ParameterizedType.make(x10.lang.Bitwise.$RTT, UnresolvedType.THIS),
                ParameterizedType.make(x10.util.Ordered.$RTT, UnresolvedType.THIS),
                Types.STRUCT
            });
    }
    
    @Override
    public String typeName() {
        return "x10.lang.Long";
    }

    // for shortcut
    @Override
    public boolean isInstance(Object o) {
        return o instanceof x10.core.Long;
    }
    
    @Override
    public long[] makeArray(int dim0) {
        return new long[dim0];
    }
    
    @Override
    public long[][] makeArray(int dim0, int dim1) {
        return new long[dim0][dim1];
    }
    
    @Override
    public long[][][] makeArray(int dim0, int dim1, int dim2) {
        return new long[dim0][dim1][dim2];
    }
    
    @Override
    public long[][][][] makeArray(int dim0, int dim1, int dim2, int dim3) {
        return new long[dim0][dim1][dim2][dim3];
    }
    
    @Override
    public Object makeArray(int... dims) {
        return Array.newInstance(long.class, dims);
    }

    @Override
    public x10.core.Long getArray(Object array, int i) {
        return x10.core.Long.$box(((long[]) array)[i]);
    }
    
    @Override
    public void setArray(Object array, int i, x10.core.Long v) {
        ((long[]) array)[i] = x10.core.Long.$unbox(v);
    }
    
    @Override
    public int arrayLength(Object array) {
    	return ((long[]) array).length;
    }

    @Override
    public boolean isref() {
        return false;
    }

//    public void $_serialize(X10JavaSerializer serializer) throws IOException {
//    }
//
//    public short $_get_serialization_id() {
//        return _serialization_id;
//    }
//
//    public static X10JavaSerializable $_deserializer(X10JavaDeserializer deserializer) throws IOException {
//		return $_deserialize_body(null, deserializer);
//	}
//
//    public static X10JavaSerializable $_deserialize_body(LongType t, X10JavaDeserializer deserializer) throws IOException {
//        LongType longType = (LongType) Types.LONG;
//        deserializer.record_reference(longType);
//        return longType;
//    }
}
