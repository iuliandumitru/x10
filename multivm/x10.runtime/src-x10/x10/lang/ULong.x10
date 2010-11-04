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

package x10.lang;

import x10.compiler.Native;
import x10.compiler.NativeRep;
import x10.util.Ordered;

/**
 * ULong is a 64-bit unsigned integral data type, with
 * values ranging from 0 to 18446744073709551616, inclusive.  All of the normal
 * arithmetic and bitwise operations are defined on ULong, and ULong
 * is closed under those operations.  There are also static methods
 * that define conversions from other data types, including String,
 * as well as some ULong constants.
 */
// @NativeRep("java", "long", null, "x10.rtt.Types.ULONG")
@NativeRep("c++", "x10_ulong", "x10_ulong", null)
public final struct ULong /*TODO implements Arithmetic[ULong], Bitwise[ULong], Ordered[ULong]*/ {

    /** The actual number with Long representation */
    public val longVal:Long;
    public def this(value:Long) {
        this.longVal = value;
    }

    /**
     * A less-than operator.
     * Compares the given ULong with another ULong and returns true if the given ULong is
     * strictly less than the other ULong.
     * @param x the given ULong
     * @param y the other ULong
     * @return true if the given ULong is strictly less than the other ULong.
     */
    // @Native("java", "x10.core.Unsigned.lt(#1, #2)")
    @Native("c++",  "((#1) < (#2))")
    public static operator (x:ULong) < (y:ULong): Boolean {
        return (x.longVal + Long.MIN_VALUE) < (y.longVal + Long.MIN_VALUE);
    }

    /**
     * A greater-than operator.
     * Compares the given ULong with another ULong and returns true if the given ULong is
     * strictly greater than the other ULong.
     * @param x the given ULong
     * @param y the other ULong
     * @return true if the given ULong is strictly greater than the other ULong.
     */
    // @Native("java", "x10.core.Unsigned.gt(#1, #2)")
    @Native("c++",  "((#1) > (#2))")
    public static operator (x:ULong) > (y:ULong): Boolean {
        return (x.longVal + Long.MIN_VALUE) > (y.longVal + Long.MIN_VALUE);
    }

    /**
     * A less-than-or-equal-to operator.
     * Compares the given ULong with another ULong and returns true if the given ULong is
     * less than or equal to the other ULong.
     * @param x the given ULong
     * @param y the other ULong
     * @return true if the given ULong is less than or equal to the other ULong.
     */
    // @Native("java", "x10.core.Unsigned.le(#1, #2)")
    @Native("c++",  "((#1) <= (#2))")
    public static operator (x:ULong) <= (y:ULong): Boolean {
        return (x.longVal + Long.MIN_VALUE) <= (y.longVal + Long.MIN_VALUE);
    }

    /**
     * A greater-than-or-equal-to operator.
     * Compares the given ULong with another ULong and returns true if the given ULong is
     * greater than or equal to the other ULong.
     * @param x the given ULong
     * @param y the other ULong
     * @return true if the given ULong is greater than or equal to the other ULong.
     */
    // @Native("java", "x10.core.Unsigned.ge(#1, #2)")
    @Native("c++",  "((#1) >= (#2))")
    public static operator (x:ULong) >= (y:ULong): Boolean {
        return (x.longVal + Long.MIN_VALUE) >= (y.longVal + Long.MIN_VALUE);
    }


    /**
     * A binary plus operator.
     * Computes the result of the addition of the two operands.
     * Overflows result in truncating the high bits.
     * @param x the given ULong
     * @param y the other ULong
     * @return the sum of the given ULong and the other ULong.
     */
    // @Native("java", "((#1) + (#2))")
    @Native("c++",  "((x10_ulong) ((#1) + (#2)))")
    public static operator (x:ULong) + (y:ULong): ULong = ULong(x.longVal + y.longVal);
    /**
     * A binary plus operator (unsigned disambiguation).
     * @see #operator(ULong)+(ULong)
     */
    // @Native("java", "((#1) + (#2))")
    @Native("c++",  "((x10_ulong) ((#1) + (#2)))")
    public static operator (x:Long) + (y:ULong): ULong = ULong(x + y.longVal);
    /**
     * A binary plus operator (unsigned disambiguation).
     * @see #operator(ULong)+(ULong)
     */
    // @Native("java", "((#1) + (#2))")
    @Native("c++",  "((x10_ulong) ((#1) + (#2)))")
    public static operator (x:ULong) + (y:Long): ULong = ULong(x.longVal + y);

    /**
     * A binary minus operator.
     * Computes the result of the subtraction of the two operands.
     * Overflows result in truncating the high bits.
     * @param x the given ULong
     * @param y the other ULong
     * @return the difference of the given ULong and the other ULong.
     */
    // @Native("java", "((#1) - (#2))")
    @Native("c++",  "((x10_ulong) ((#1) - (#2)))")
    public static operator (x:ULong) - (y:ULong): ULong  = ULong(x.longVal - y.longVal);
    /**
     * A binary minus operator (unsigned disambiguation).
     * @see #operator(ULong)-(ULong)
     */
    // @Native("java", "((#1) - (#2))")
    @Native("c++",  "((x10_ulong) ((#1) - (#2)))")
    public static operator (x:Long) - (y:ULong): ULong = ULong(x - y.longVal);
    /**
     * A binary minus operator (unsigned disambiguation).
     * @see #operator(ULong)-(ULong)
     */
    // @Native("java", "((#1) - (#2))")
    @Native("c++",  "((x10_ulong) ((#1) - (#2)))")
    public static operator (x:ULong) - (y:Long): ULong = ULong(x.longVal - y);

    /**
     * A binary multiply operator.
     * Computes the result of the multiplication of the two operands.
     * Overflows result in truncating the high bits.
     * @param x the given ULong
     * @param y the other ULong
     * @return the product of the given ULong and the other ULong.
     */
    // @Native("java", "((#1) * (#2))")
    @Native("c++",  "((x10_ulong) ((#1) * (#2)))")
    public static operator (x:ULong) * (y:ULong): ULong = ULong(x.longVal * y.longVal);
    /**
     * A binary multiply operator (unsigned disambiguation).
     * @see #operator(ULong)*(ULong)
     */
    // @Native("java", "((#1) * (#2))")
    @Native("c++",  "((x10_ulong) ((#1) * (#2)))")
    public static operator (x:Long) * (y:ULong): ULong = ULong(x * y.longVal);
    /**
     * A binary multiply operator (unsigned disambiguation).
     * @see #operator(ULong)*(ULong)
     */
    // @Native("java", "((#1) * (#2))")
    @Native("c++",  "((x10_ulong) ((#1) * (#2)))")
    public static operator (x:ULong) * (y:Long): ULong = ULong(x.longVal * y);

    /**
     * A binary divide operator.
     * Computes the result of the division of the two operands.
     * @param x the given ULong
     * @param y the other ULong
     * @return the quotient of the given ULong and the other ULong.
     */
    // @Native("java", "x10.core.Unsigned.div(#1, #2)")
    @Native("c++",  "((x10_ulong) ((#1) / (#2)))")
    public static operator (x:ULong) / (y:ULong): ULong {
        if (x.longVal > 0 && y.longVal > 0)
            return ULong(x.longVal / y.longVal);
        else if (x.longVal < y.longVal)
            return 0L as ULong;
        else
            return 1L as ULong;
    }
    /**
     * A binary divide operator (unsigned disambiguation).
     * @see #operator(ULong)/(ULong)
     */
    // @Native("java", "x10.core.Unsigned.div(#1, #2)")
    @Native("c++",  "((x10_ulong) ((#1) / (#2)))")
    public static operator (x:Long) / (y:ULong): ULong {
        if (x > 0 && y.longVal > 0)
            return ULong(x / y.longVal);
        else if (x < y.longVal)
            return 0L as ULong;
        else
            return 1L as ULong;
    }
    /**
     * A binary divide operator (unsigned disambiguation).
     * @see #operator(ULong)/(ULong)
     */
    // @Native("java", "x10.core.Unsigned.div(#1, #2)")
    @Native("c++",  "((x10_ulong) ((#1) / (#2)))")
    public static operator (x:ULong) / (y:Long): ULong {
        if (x.longVal > 0 && y > 0)
            return ULong(x.longVal / y);
        else if (x.longVal < y)
            return 0L as ULong;
        else
            return 1L as ULong;
    }

    /**
     * A binary remainder operator.
     * Computes a remainder from the division of the two operands.
     * @param x the given ULong
     * @param y the other ULong
     * @return the remainder from dividing the given ULong by the other ULong.
     */
    // @Native("java", "x10.core.Unsigned.rem(#1, #2)")
    @Native("c++",  "((x10_ulong) ((#1) % (#2)))")
    public static operator (x:ULong) % (y:ULong): ULong {
        if (x.longVal > 0 && y.longVal > 0)
            return ULong(x.longVal % y.longVal);
        else if (x.longVal < y.longVal)
            return x;
        else
            return ULong(x.longVal - y.longVal);
    }
    /**
     * A binary remainder operator (unsigned disambiguation).
     * @see #operator(ULong)%(ULong)
     */
    // @Native("java", "x10.core.Unsigned.rem(#1, #2)")
    @Native("c++",  "((x10_ulong) ((#1) % (#2)))")
    public static operator (x:Long) % (y:ULong): ULong {
        if (x > 0 && y.longVal > 0)
            return ULong(x % y.longVal);
        else if (x < y.longVal)
            return ULong(x);
        else
            return ULong(x - y.longVal);
    }
    /**
     * A binary remainder operator (unsigned disambiguation).
     * @see #operator(ULong)%(ULong)
     */
    // @Native("java", "x10.core.Unsigned.rem(#1, #2)")
    @Native("c++",  "((x10_ulong) ((#1) % (#2)))")
    public static operator (x:ULong) % (y:Long): ULong {
        if (x.longVal > 0 && y > 0)
            return ULong(x.longVal % y);
        else if (x.longVal < y)
            return x;
        else
            return ULong(x.longVal - y);
    }

    /**
     * A unary plus operator.
     * A no-op.
     * @param x the given ULong
     * @return the value of the given ULong.
     */
    // @Native("java", "((long) +(#1))")
    @Native("c++",  "((x10_ulong) +(#1))")
    public static operator + (x:ULong): ULong = x;

    /**
     * A unary minus operator.
     * Computes the two's complement of the operand.
     * Overflows result in truncating the high bits.
     * @param x the given ULong
     * @return the two's complement of the given ULong.
     */
    // @Native("java", "((long) -(#1))")
    @Native("c++",  "((x10_ulong) -(#1))")
    public static operator - (x:ULong): ULong = ULong(-(x.longVal));


    /**
     * A bitwise and operator.
     * Computes a bitwise AND of the two operands.
     * @param x the given ULong
     * @param y the other ULong
     * @return the bitwise AND of the given ULong and the other ULong.
     */
    // @Native("java", "((#1) & (#2))")
    @Native("c++",  "((x10_ulong) ((#1) & (#2)))")
    public static operator (x:ULong) & (y:ULong): ULong = ULong(x.longVal & y.longVal);
    /**
     * A bitwise and operator (unsigned disambiguation).
     * @see #operator(ULong)&(ULong)
     */
    // @Native("java", "((#1) & (#2))")
    @Native("c++",  "((x10_ulong) ((#1) & (#2)))")
    public static operator (x:Long) & (y:ULong): ULong = ULong(x & y.longVal);
    /**
     * A bitwise and operator (unsigned disambiguation).
     * @see #operator(ULong)&(ULong)
     */
    // @Native("java", "((#1) & (#2))")
    @Native("c++",  "((x10_ulong) ((#1) & (#2)))")
    public static operator (x:ULong) & (y:Long): ULong = ULong(x.longVal & y);

    /**
     * A bitwise or operator.
     * Computes a bitwise OR of the two operands.
     * @param x the given ULong
     * @param y the other ULong
     * @return the bitwise OR of the given ULong and the other ULong.
     */
    // @Native("java", "((#1) | (#2))")
    @Native("c++",  "((x10_ulong) ((#1) | (#2)))")
    public static operator (x:ULong) | (y:ULong): ULong = ULong(x.longVal | y.longVal);
    /**
     * A bitwise or operator (unsigned disambiguation).
     * @see #operator(ULong)|(ULong)
     */
    // @Native("java", "((#1) | (#2))")
    @Native("c++",  "((x10_ulong) ((#1) | (#2)))")
    public static operator (x:Long) | (y:ULong): ULong = ULong(x | y.longVal);
    /**
     * A bitwise or operator (unsigned disambiguation).
     * @see #operator(ULong)|(ULong)
     */
    // @Native("java", "((#1) | (#2))")
    @Native("c++",  "((x10_ulong) ((#1) | (#2)))")
    public static operator (x:ULong) | (y:Long): ULong = ULong(x.longVal | y);

    /**
     * A bitwise xor operator.
     * Computes a bitwise XOR of the two operands.
     * @param x the given ULong
     * @param y the other ULong
     * @return the bitwise XOR of the given ULong and the other ULong.
     */
    // @Native("java", "((#1) ^ (#2))")
    @Native("c++",  "((x10_ulong) ((#1) ^ (#2)))")
    public static operator (x:ULong) ^ (y:ULong): ULong = ULong(x.longVal ^ y.longVal);
    /**
     * A bitwise xor operator (unsigned disambiguation).
     * @see #operator(ULong)^(ULong)
     */
    // @Native("java", "((#1) ^ (#2))")
    @Native("c++",  "((x10_ulong) ((#1) ^ (#2)))")
    public static operator (x:Long) ^ (y:ULong): ULong = ULong(x ^ y.longVal);
    /**
     * A bitwise xor operator (unsigned disambiguation).
     * @see #operator(ULong)^(ULong)
     */
    // @Native("java", "((#1) ^ (#2))")
    @Native("c++",  "((x10_ulong) ((#1) ^ (#2)))")
    public static operator (x:ULong) ^ (y:Long): ULong = ULong(x.longVal ^ y);

    /**
     * A bitwise left shift operator.
     * Computes the value of the left-hand operand shifted left by the value of the right-hand operand.
     * If the right-hand operand is negative, the results are undefined.
     * @param x the given ULong
     * @param count the shift count
     * @return the given ULong shifted left by count.
     */
    // @Native("java", "((#1) << (#2))")
    @Native("c++",  "((x10_ulong) ((#1) << (#2)))")
    public static operator (x:ULong) << (count:Int): ULong = ULong(x.longVal << count);

    /**
     * A bitwise right shift operator.
     * Computes the value of the left-hand operand shifted right by the value of the right-hand operand,
     * filling the high bits with zeros.
     * If the right-hand operand is negative, the results are undefined.
     * @param x the given ULong
     * @param count the shift count
     * @return the given ULong shifted right by count.
     */
    // @Native("java", "((#1) >>> (#2))")
    @Native("c++",  "((x10_ulong) ((#1) >> (#2)))")
    public static operator (x:ULong) >> (count:Int): ULong = ULong(x.longVal >>> count);

    /**
     * A bitwise logical right shift operator (zero-fill).
     * Computes the value of the left-hand operand shifted right by the value of the right-hand operand,
     * filling the high bits with zeros.
     * If the right-hand operand is negative, the results are undefined.
     * @deprecated use the right-shift operator.
     * @param x the given ULong
     * @param count the shift count
     * @return the given ULong shifted right by count with high bits zero-filled.
     */
    // @Native("java", "((#1) >>> (#2))")
    @Native("c++",  "((x10_ulong) ((#1) >> (#2)))")
    public static operator (x:ULong) >>> (count:Int): ULong = ULong(x.longVal >>> count);

    /**
     * A bitwise complement operator.
     * Computes a bitwise complement (NOT) of the operand.
     * @param x the given ULong
     * @return the bitwise complement of the given ULong.
     */
    // @Native("java", "((long) ~(#1))")
    @Native("c++",  "((x10_ulong) ~(#1))")
    public static operator ~ (x:ULong): ULong = ULong(~(x.longVal));


    /**
     * Coerce a given UByte to a ULong.
     * @param x the given UByte
     * @return the given UByte converted to a ULong.
     */
    // @Native("java", "((long) ((#1) & 0xffL))")
    @Native("c++",  "((x10_ulong) (#1))")
    public static operator (x:UByte): ULong = ULong(x.byteVal & 0xffL);

    /**
     * Coerce a given UShort to a ULong.
     * @param x the given UShort
     * @return the given UShort converted to a ULong.
     */
    // @Native("java", "((long) ((#1) & 0xffffL))")
    @Native("c++",  "((x10_ulong) (#1))")
    public static operator (x:UShort): ULong = ULong(x.shortVal & 0xffffL);

    /**
     * Coerce a given UInt to a ULong.
     * @param x the given UInt
     * @return the given UInt converted to a ULong.
     */
    // @Native("java", "((long) ((#1) & 0xffffffffL))")
    @Native("c++",  "((x10_ulong) (#1))")
    public static operator (x:UInt): ULong = ULong(x.intVal & 0xffffffffL);


    /**
     * Coerce a given Byte to a ULong.
     * @param x the given Byte
     * @return the given Byte converted to a ULong.
     */
    // @Native("java", "((long)(byte)(#1))")
    @Native("c++",  "((x10_ulong) (#1))")
    public static operator (x:Byte): ULong = ULong(x);

    /**
     * Coerce a given Short to a ULong.
     * @param x the given Short
     * @return the given Short converted to a ULong.
     */
    // @Native("java", "((long)(short)(#1))")
    @Native("c++",  "((x10_ulong) (#1))")
    public static operator (x:Short): ULong = ULong(x);

    /**
     * Coerce a given Int to a ULong.
     * @param x the given Int
     * @return the given Int converted to a ULong.
     */
    // @Native("java", "((long)(int)(#1))")
    @Native("c++",  "((x10_ulong) (#1))")
    public static operator (x:Int): ULong = ULong(x);

    /**
     * Convert a given Float to a ULong.
     * @param x the given Float
     * @return the given Float converted to a ULong.
     */
    // @Native("java", "((long)(float)(#1))")
    @Native("c++",  "((x10_ulong) (#1))")
    public static operator (x:Float) as ULong = ULong(x as Long);

    /**
     * Convert a given Double to a ULong.
     * @param x the given Double
     * @return the given Double converted to a ULong.
     */
    // @Native("java", "((long)(double)(#1))")
    @Native("c++",  "((x10_ulong) (#1))")
    public static operator (x:Double) as ULong = ULong(x as Long);

    /**
     * Coerce a given Long to a ULong.
     * @param x the given Long
     * @return the given Long converted to a ULong.
     */
    // @Native("java", "((long)(long)(#1))")
    @Native("c++",  "((x10_ulong) (#1))")
    public static operator (x:Long): ULong = ULong(x);


    /**
     * A constant holding the minimum value a ULong can have, 0.
     */
    // @Native("java", "0L")
    @Native("c++", "((x10_ulong)0UL)")
    public static MIN_VALUE = 0L as ULong;

    /**
     * A constant holding the maximum value a ULong can have, 2<sup>64</sup>-1.
     */
    // @Native("java", "0xffffffffffffffffL")
    @Native("c++", "0xffffffffffffffffLU")
    public static MAX_VALUE = 0xffffffffffffffffL as ULong;


    /**
     * Returns a String representation of this ULong in the specified radix.
     * @param radix the radix to use in the String representation
     * @return a String representation of this ULong in the specified radix.
     */
    // @Native("java", "java.lang.Long.toString(#0 & 0xffffffffffffffffL, #1)")
    @Native("c++", "x10aux::long_utils::toString(#0, #1)")
    public def toString(radix:Int): String = (this.longVal & 0xFFFFFFFFFFFFFFFFL).toString(radix);

    /**
     * Returns a String representation of this ULong as a hexadecimal number.
     * @return a String representation of this ULong as a hexadecimal number.
     */
    // @Native("java", "java.lang.Long.toHexString(#0)")
    @Native("c++", "x10aux::long_utils::toHexString(#0)")
    public def toHexString(): String = this.longVal.toHexString();

    /**
     * Returns a String representation of this ULong as an octal number.
     * @return a String representation of this ULong as an octal number.
     */
    // @Native("java", "java.lang.Long.toOctalString(#0)")
    @Native("c++", "x10aux::long_utils::toOctalString(#0)")
    public def toOctalString(): String = this.longVal.toOctalString();

    /**
     * Returns a String representation of this ULong as a binary number.
     * @return a String representation of this ULong as a binary number.
     */
    // @Native("java", "java.lang.Long.toBinaryString(#0)")
    @Native("c++", "x10aux::long_utils::toBinaryString(#0)")
    public def toBinaryString(): String = this.longVal.toBinaryString();

    /**
     * Returns a String representation of this ULong as a decimal number.
     * @return a String representation of this ULong as a decimal number.
     */
    // @Native("java", "java.lang.Long.toString(#0 & 0xffffffffffffffffL)")
    @Native("c++", "x10aux::to_string(#0)")
    public def toString(): String {
        if (this.longVal >= 0)
            return (this.longVal & 0xFFFFFFFFFFFFFFFFL).toString();

        // array representation of long.MAX_VALUE + 1
        val offs <: Array[Int] = [0,9,2,2,3,3,7,2,0,3,6,8,5,4,7,7,5,8,0,8];
        // result buffer
        val buf <: Rail[Char] = Rail.make[Char](20, (i:Int)=>'0');
        // drop sign bit
        var a : Long = this.longVal & 0x7FFFFFFFFFFFFFFFL;
        var pos : Int = offs.size();
        var carry : Int = 0;
        while (pos > 0) {
            var digit : Int = ((a % 10) as Int) + offs(--pos) + carry;
            buf(pos) = '0' + digit % 10;
            carry = (digit >= 10) ? 1 : 0;
            a /= 10;
        }
        pos = (buf(0) == '0') ? 1 : 0;
        return new String(buf, pos, 20-pos);
    }

    /**
     * @deprecated use {@link #parse(String,Int)} instead
     */
    // @Native("java", "java.lang.Long.parseLong(#1, #2)")
    @Native("c++", "((x10_ulong) x10aux::long_utils::parseLong(#1, #2))")
    public static def parseULong(s:String, radix:Int): ULong //throwsNumberFormatException 
    {
        return ULong(Long.parse(s, radix));
    }

    /**
     * @deprecated use {@link #parse(String)} instead
     */
    // @Native("java", "java.lang.Long.parseLong(#1)")
    @Native("c++", "((x10_ulong) x10aux::long_utils::parseLong(#1))")
    public static def parseULong(s:String): ULong //throwsNumberFormatException 
    {
        return ULong(Long.parse(s));
    }

    /**
     * Parses the String argument as a ULong in the radix specified by the second argument.
     * @param s the String containing the ULong representation to be parsed
     * @param radix the radix to be used while parsing s
     * @return the ULong represented by the String argument in the specified radix.
     * @throws NumberFormatException if the String does not contain a parsable ULong.
     */
    // @Native("java", "java.lang.Long.parseLong(#1, #2)")
    @Native("c++", "((x10_ulong) x10aux::long_utils::parseLong(#1, #2))")
    public static def parse(s:String, radix:Int): ULong //throwsNumberFormatException 
    {
        return ULong(Long.parse(s, radix));
    }

    /**
     * Parses the String argument as a decimal ULong.
     * @param s the String containing the ULong representation to be parsed
     * @return the ULong represented by the String argument.
     * @throws NumberFormatException if the String does not contain a parsable ULong.
     */
    // @Native("java", "java.lang.Long.parseLong(#1)")
    @Native("c++", "((x10_ulong) x10aux::long_utils::parseLong(#1))")
    public static def parse(s:String): ULong //throwsNumberFormatException 
    {
        return ULong(Long.parse(s));
    }


    /**
     * Returns a ULong value with at most a single one-bit, in the position
     * of the highest-order ("leftmost") one-bit in this ULong value.
     * Returns zero if this ULong has no one-bits in its
     * binary representation, that is, if it is equal to zero.
     * @return a ULong value with a single one-bit, in the position of the highest-order one-bit in this ULong, or zero if this ULong is itself equal to zero.
     */
    // @Native("java", "java.lang.Long.highestOneBit(#0)")
    @Native("c++", "((x10_ulong) x10aux::long_utils::highestOneBit(#0))")
    public def highestOneBit(): ULong = ULong(this.longVal.highestOneBit());

    /**
     * Returns a ULong value with at most a single one-bit, in the position
     * of the lowest-order ("rightmost") one-bit in this ULong value.
     * Returns zero if this ULong has no one-bits in its
     * binary representation, that is, if it is equal to zero.
     * @return a ULong value with a single one-bit, in the position of the lowest-order one-bit in this ULong, or zero if this ULong is itself equal to zero.
     */
    // @Native("java", "java.lang.Long.lowestOneBit(#0)")
    @Native("c++", "((x10_ulong) x10aux::long_utils::lowestOneBit(#0))")
    public def lowestOneBit(): ULong = ULong(this.longVal.lowestOneBit());

    /**
     * Returns the number of zero bits preceding the highest-order ("leftmost")
     * one-bit in the binary representation of this ULong.
     * Returns 64 if this ULong has no one-bits in its representation,
     * in other words if it is equal to zero.
     * @return the number of zero bits preceding the highest-order one-bit in the binary representation of this ULong, or 64 if this ULong is equal to zero.
     */
    // @Native("java", "java.lang.Long.numberOfLeadingZeros(#0)")
    @Native("c++", "x10aux::long_utils::numberOfLeadingZeros(#0)")
    public def numberOfLeadingZeros(): Int = this.longVal.numberOfLeadingZeros();

    /**
     * Returns the number of zero bits following the lowest-order ("rightmost")
     * one-bit in the binary representation of this ULong.
     * Returns 64 if this ULong has no one-bits in its representation,
     * in other words if it is equal to zero.
     * @return the number of zero bits following the lowest-order one-bit in the binary representation of this ULong, or 64 if this ULong is equal to zero.
     */
    // @Native("java", "java.lang.Long.numberOfTrailingZeros(#0)")
    @Native("c++", "x10aux::long_utils::numberOfTrailingZeros(#0)")
    public def numberOfTrailingZeros(): Int = this.longVal.numberOfTrailingZeros();

    /**
     * Returns the number of one-bits in the binary representation
     * of this ULong.  This function is sometimes referred
     * to as the <i>population count</i>.
     * @return the number of one-bits in the binary representation of this ULong.
     */
    // @Native("java", "java.lang.Long.bitCount(#0)")
    @Native("c++", "x10aux::long_utils::bitCount(#0)")
    public def bitCount(): Int = this.longVal.bitCount();

    /**
     * Returns the value obtained by rotating the binary representation
     * of this ULong left by the specified number of bits.
     * (Bits shifted out of the left hand, or high-order, side reenter on
     * the right, or low-order.)<br>
     * Note that left rotation with a negative distance is equivalent to
     * right rotation:
     * <code>rotateLeft(val, -distance) == rotateRight(val, distance)</code>.
     * Note also that rotation by any multiple of 64 is a no-op, so all but
     * the last five bits of the rotation distance can be ignored, even if
     * the distance is negative:
     * <code>rotateLeft(val, distance) == rotateLeft(val, distance &amp; 0x3F)</code>.
     * @param distance the distance to rotate by
     * @return the value obtained by rotating the binary representation of this ULong left by the specified number of bits.
     * @see #rotateRight(Int)
     */
    // @Native("java", "java.lang.Long.rotateLeft(#0, #1)")
    @Native("c++", "x10aux::long_utils::rotateLeft(#0, #1)")
    public def rotateLeft(distance:Int): ULong = ULong(this.longVal.rotateLeft(distance));

    /**
     * Returns the value obtained by rotating the binary representation
     * of this ULong right by the specified number of bits.
     * (Bits shifted out of the right hand, or low-order, side reenter on
     * the left, or high-order.)<br>
     * Note that right rotation with a negative distance is equivalent to
     * left rotation:
     * <code>rotateRight(val, -distance) == rotateLeft(val, distance)</code>.
     * Note also that rotation by any multiple of 64 is a no-op, so all but
     * the last five bits of the rotation distance can be ignored, even if
     * the distance is negative:
     * <code>rotateRight(val, distance) == rotateRight(val, distance &amp; 0x3F)</code>.
     * @param distance the distance to rotate by
     * @return the value obtained by rotating the binary representation of this ULong right by the specified number of bits.
     * @see #rotateLeft(Int)
     */
    // @Native("java", "java.lang.Long.rotateRight(#0, #1)")
    @Native("c++", "x10aux::long_utils::rotateRight(#0, #1)")
    public def rotateRight(distance:Int): ULong = ULong(this.longVal.rotateRight(distance));

    /**
     * Returns the value obtained by reversing the order of the bits in the
     * binary representation of this ULong.
     * @return the value obtained by reversing order of the bits in this ULong.
     */
    // @Native("java", "java.lang.Long.reverse(#0)")
    @Native("c++", "x10aux::long_utils::reverse(#0)")
    public def reverse(): ULong = ULong(this.longVal.reverse());

    /**
     * Returns the signum function of this ULong.  The return value is 0 if
     * this ULong is zero and 1 if this ULong is non-zero.
     * @return the signum function of this ULong.
     */
    // @Native("java", "(((#0)==0L) ? 0 : 1)")
    @Native("c++",  "(((#0)==0UL) ? 0 : 1)")
    public def signum(): Int = (this.longVal == 0L) ? 0 : 1;

    /**
     * Returns the value obtained by reversing the order of the bytes in the
     * representation of this ULong.
     * @return the value obtained by reversing the bytes in this ULong.
     */
    // @Native("java", "java.lang.Long.reverseBytes(#0)")
    @Native("c++", "((x10_ulong) x10aux::long_utils::reverseBytes((x10_long) #0))")
    public def reverseBytes(): ULong = ULong(this.longVal.reverseBytes());


    /**
     * Return true if the given entity is a ULong, and this ULong is equal
     * to the given entity.
     * @param x the given entity
     * @return true if this ULong is equal to the given entity.
     */
    // @Native("java", "x10.rtt.Equality.equalsequals(#0, #1)")
    @Native("c++", "x10aux::equals(#0,#1)")
    public def equals(x:Any):Boolean = this.longVal.equals(x);

    /**
     * Returns true if this ULong is equal to the given ULong.
     * @param x the given ULong
     * @return true if this ULong is equal to the given ULong.
     */
    // @Native("java", "x10.rtt.Equality.equalsequals(#0, #1)")
    @Native("c++", "x10aux::equals(#0,#1)")
    public def equals(x:ULong):Boolean = this.longVal == x.longVal;
}
