import x10.compiler.FinishAsync;
public class ManyLocalFinish2 {
    public static def main(args: Rail[String]!) throws Exception{
            var i:int = 0;
	    for(i=0;i<100000;i++){
            	@FinishAsync(1,1,true,1)
            	finish{
                    	async{}
                    	async{}
                    	async{}
                   	async{}
            	}
 	    }
     }
    /** x10doc comment for myMethod */
    public def myMethod(): boolean = {
       return true;
    }
}
