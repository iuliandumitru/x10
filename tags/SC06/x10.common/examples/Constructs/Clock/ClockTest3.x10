// Automatically generated by the command
// m4 ClockTest3.m4 > ClockTest3.x10
// Do not edit
import harness.x10Test;

/**
 * Clock test for barrier functions.
 * @author kemal 3/2005
 */
public class ClockTest3 extends x10Test {

	int val = 0;
	static final int N = 32;

	public boolean run() {
		final clock c = clock.factory.clock();

		foreach (point [i]: [0:(N-1)]) clocked(c) {
			async(here) clocked(c) finish async(here) { async(here) { atomic val++; } }
			next;
			int temp;
			atomic { temp = val; }
			if (temp != N) {
				throw new Error();
			}
			next;
			async(here) clocked(c) finish async(here) { async(here) { atomic val++; } }
			next;
		}
		next; next; next;
		int temp2;
		atomic { temp2 = val; }
		if (temp2 != 2*N) {
			throw new Error();
		}
		return true;
	}

	public static void main(String[] args) {
		new ClockTest3().executeAsync();
	}
}

