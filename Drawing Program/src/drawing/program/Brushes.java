/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing.program;

import java.awt.*;
import java.awt.geom.Ellipse2D;
/**
 *
 * @author Owner
 */
public class Brushes {
    private static Canvas c = new Canvas();
    public static int type = 0;
    public static int size = 100;
    
    public Brushes(Canvas canvas) {
        c = canvas;
    }
    
    //draw fractal each iteration
    public static void paint(Canvas canvas, int x, int y) {
        c = canvas;
        
        //brush 1
        if (type == 0) {
            circle(x - size, y - size, size*2);
        //brush 2
        } else if (type == 1) {
            circle(x - size/2, y - size/2, size);
        //brush 3
        } else if (type == 2){
            rect(x, y, size, size);
        //more brushes here
        } else {
            System.out.println("error choosing fractal drawing type");
        }
    }
    
    //class brushs
    public static void line(int x1, int y1, int x2, int y2) {
        c.getGraphics().drawLine(x1, y1, x2, y2);
    }
    public static void circle(int x, int y, int r) {
        Graphics2D g2 = (Graphics2D) c.getGraphics();
        Shape circle = new Ellipse2D.Double(x, y, r, r);
        g2.draw(circle);
    }
    public static void rect(int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) c.getGraphics();
        Shape rect = new Rectangle(x, y, width, height);
        g2.draw(rect);
    }
    public static void fillRect(int x1, int y1, int x2, int y2) {
        Graphics2D g2 = (Graphics2D) c.getGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(x1, y1, x2, y2);
    }
    
    //other methods
    public static double getAngle(int x1, int y1, int x2, int y2) {
        double angle = (double) Math.toRadians(Math.atan2(y1-y2, x1-x2));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }
    public static void clearCanvas() {
        fillRect(0, 0, c.getWidth(), c.getHeight());
    }
}
