import x10.compiler.FinishAsync;
import x10.util.Timer;
public class ManyLocalFinish2_old {
    public static def main(args: Rail[String]!) throws Exception{
	    val start = Timer.milliTime();
            finish{
            var i:int = 0;
            for(i=0;i<1000;i++){
		val p = Place.place(i % Place.MAX_PLACES);
	    	async(p){
            	  finish{
		    for(var j:int=0;j<50;j++){
		      async{
		         for(var k:int = 0; k<50; k++){
			     async{}
			 }
		      }
		    }
            	  }
 	        }
	      }
	     }
	     val end = Timer.milliTime();
    	     Console.OUT.println("time = "+(end-start)+" milliseconds");
    }
    /** x10doc comment for myMethod */
    public def myMethod(): boolean = {
       return true;
    }
}
