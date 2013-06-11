/*
 * Wait.java
 *
 * create a simulation delay
 *
 * Created on November 3, 2004, 8:01 PM
 */

package animatsim;


public class Wait {
    public static void oneSec() {
        try {
            Thread.currentThread().sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void manySec(double m) {
        int wholeMilli = (int)Math.ceil(m*1000);
        try {
            Thread.currentThread().sleep(wholeMilli);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
