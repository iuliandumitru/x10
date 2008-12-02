/*
 *
 * (C) Copyright IBM Corporation 2006
 *
 *  This file is part of X10 Test.
 *
 */
import harness.x10Test;

/**
 * Testing miscellaneous array declarations and initializations.
 *
 * @author kemal 4/2005
 */

public class ArrayDecl extends x10Test {

    public const N: int = 24;

    public def run(): boolean = {

        val ia0 = Array.make[int](Dist.makeConstant(0..N-1, here));
        val p: Place = here;

        chk(ia0.dist.equals(Dist.makeConstant(0..N-1, p)));

        finish ateach (val (i): Point in ia0.dist) chk(ia0(i) == 0);

        val v_ia2: Array[int] = Array.make[int](Dist.makeConstant(0..N-1, here), ((i): Point)=>i);
        chk(v_ia2.dist.equals(Dist.makeConstant(0..N-1, here)));
        for (val (i): Point in v_ia2.region) chk(v_ia2(i) == i);

        val ia2: Array[byte] = Array.make[byte](Dist.makeConstant(0..N-1, (here).prev().prev()), (Point)=> (0 to byte));
        chk(ia2.dist.equals(Dist.makeConstant(0..N-1, (here).prev().prev())));
        finish ateach ((i): Point in ia2.dist) chk(ia2(i) == (0 to byte));

        //Examples similar to section 10.3 of X10 reference manual

        val data1: Array[double] = Array.make[double](Dist.makeConstant(0..16, here), ((i):Point)=> i to  Double);
        chk(data1.dist.equals(Dist.makeConstant(0..16, here)));
        for (val (i): Point in data1.region) chk(data1(i) == (i to Double));

        val myStr: String = "abcdefghijklmnop";
        val data2 = Array.make[char](Dist.makeConstant([1..2, 1..3] to Region, here), ((i,j): Point)=> myStr.charAt(i*j));
        chk(data2.dist.equals(Dist.makeConstant([1..2, 1..3], here)));
        for (val (i,j): Point in data2.region) chk(data2(i, j) == myStr.charAt(i*j));

        // is a region R converted to R->here in a dist context?
        //final long[.] data3 = new long[1:11]
        val data3: Array[long] = Array.make[long](Dist.makeConstant(1..11, here), ((i) : Point)=> i*i to Long);
        chk(data3.dist.equals(Dist.makeConstant(1..11, here)));
        for (val (i): Point in data3.region) chk(data3(i) == (i*i to Long));

        val D: Dist = Dist.makeCyclic(0..9, 0);
        val d = Array.make[float](D, ((i):Point) => (10.0*i to Float));
        chk(d.dist.equals(D));
        finish ateach (val (i): Point in D) chk(d(i) == (10.0*i to Float));

        val E = Dist.makeCyclic([1..7, 0..1], 1);
        val result1  = Array.make[Short](E, ((i,j): Point) => (i+j to Short));
        chk(result1.dist.equals(E));
        finish ateach (val (i,j): Point in E) chk(result1(i, j) == (i+j to Short));

        val result2 = Array.make[complex](Dist.makeConstant(0..N-1, here), ((i) : Point) =>  new complex(i*N,-i));
        chk(result2.dist.equals(Dist.makeConstant(0..N-1, here)));
        // work around XTENLANG-46
        finish ateach (val (i): Point in result2.dist) chk(result2(i)/*==*/.equals(new complex(i*N,-i)));

        return true;
    }

    final static value complex  {
        val re:Int;
        val im:Int;
        def equals(that:complex) = this.re==that.re && this.im==that.im;
        public def this(re:Int, im:Int) { this.re = re; this.im = im; }
    }

    public static def main(Rail[String]):Void = {
        new ArrayDecl().execute();
    }

}
