/*
 *
 * (C) Copyright IBM Corporation 2006
 *
 *  This file is part of X10 Test.
 *
 */
import harness.x10Test;;

/**
 * Check if illegal uses of clock are raising an exception.
 * @author kemal 4/2005
 */
public class ClockTest14 extends x10Test {

	public def run(): boolean = {
		val c: clock = clock.make();
		var gotException: boolean;
		next;
		c.resume();
		c.drop();
		chk(c.dropped());
		next; // empty clock set is acceptable, next is no-op
		gotException = false;
		try {
			c.resume();
		} catch (var e: ClockUseException) {
			gotException = true;
		}
		chk(gotException);
		gotException = false;
		try {
			async clocked(c) { }
		} catch (var e: ClockUseException) {
			gotException = true;
		}
		chk(gotException);
		return true;
	}

	public static def main(var args: Rail[String]): void = {
		new ClockTest14().execute();
	}
}
