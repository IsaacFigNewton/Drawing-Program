/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing.program;

/**
 *
 * @author Owner
 */

import static drawing.program.Drawer.stroke;
import static drawing.program.Drawer.strokeColor;
import java.awt.*;

public class Rainbow {
    private static int r = 100;
    private static boolean rDoIncrement = true;
    private static int g = 50;
    private static boolean gDoIncrement = true;
    private static int b = 1;
    private static boolean bDoIncrement = true;
    
    public static void doIt() {
        //red
        if (rDoIncrement) {
            r++;
        } else {
            r--;
        }
        //if it goes above 255, make it go down
        if (r > 255) {
            rDoIncrement = false;
            r = 255;
        //if it goes below 0, make it go up
        } else if (r < 0) {
            rDoIncrement = true;
            r = 0;
        }

        //green
        if (gDoIncrement) {
            g++;
        } else {
            g--;
        }
        //if it goes above 255, make it go down
        if (g > 255) {
            gDoIncrement = false;
            g = 255;
        //if it goes below 0, make it go up
        } else if (g < 0) {
            gDoIncrement = true;
            g = 0;
        }

        //blue
        if (bDoIncrement) {
            b++;
        } else {
            b--;
        }
        //if it goes above 255, make it go down
        if (b > 255) {
            bDoIncrement = false;
            b = 255;
        //if it goes below 0, make it go up
        } else if (b < 0) {
            bDoIncrement = true;
            b = 0;
        }

        Drawer.strokeColor = new Color(r, g, b);
    }
}
