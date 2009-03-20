//OPTIONS: -PLUGINS=bits.plugin.BitsTypePlugin

import harness.x10Test;
import bits.*;

public class BitsTest extends harness.x10Test {
    var a: int;
    var b: int;
    
    public def run(): boolean = {
    	b = 65536;
    	b = 0;
        a = 7 & 1;
        b = 0x01234567 & 0x1ffff;
        return a == 1 && b == 0x14567;
    }

    public static def main(var args: Rail[String]): void = {
        new BitsTest().execute();
    }
}
