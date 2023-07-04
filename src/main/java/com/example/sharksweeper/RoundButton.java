package com.example.sharksweeper;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.Border;


public class RoundButton implements Border {
	   private String BType; //if Button Type is img then seal button else question mark button
	   private int radius;


	    RoundButton(int radius, String BType) {
	        this.radius = radius;
	        this.BType = BType;
	    }


	    public Insets getBorderInsets(Component c) {
	        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
	    }


	    public boolean isBorderOpaque() {
	        return true;
	    }


	    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	    	if (BType == "img") {
	    		
	    	}
	    	else{
	    		g.setColor(Color.WHITE);
	    		g.drawOval(x+1, y, width-1, height-1);
	    		
	    	}
	    }
	    
	   

}
