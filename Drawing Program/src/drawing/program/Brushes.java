/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing.program;

import static drawing.program.Drawer.strokeColor;
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
            circle(x, y, size*2);
        //brush 2
        } else if (type == 1) {
            circle(x, y, size);
        //brush 3
        } else if (type == 2){
            rect(x, y, size, size);
        //more brushes here
        } else {
            System.out.println("error choosing fractal drawing type");
        }
//        Drawer.c = c;
    }
    
    //class brushs
    public static void line(int x1, int y1, int x2, int y2) {
        stroke(Drawer.strokeColor);
        c.getGraphics().drawLine(x1, y1, x2, y2);
    }
    public static void circle(int x, int y, int r) {
        stroke(Drawer.strokeColor);
        Graphics2D g2 = (Graphics2D) c.getGraphics();
        Shape circle = new Ellipse2D.Double(x - r/2, y - r/2, r, r);
        g2.draw(circle);
    }
    public static void rect(int x, int y, int width, int height) {
        stroke(Drawer.strokeColor);
        Graphics2D g2 = (Graphics2D) c.getGraphics();
        Shape rect = new Rectangle(x - width/2, y - height/2, width, height);
        g2.draw(rect);
    }
    public static void fillRect(int x1, int y1, int x2, int y2) {
        Graphics2D g2 = (Graphics2D) c.getGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(x1, y1, x2, y2);
    }
    
    //getters
    
    //setters
    public static void stroke(Color theColor) {
        c.getGraphics().setColor(theColor);
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
