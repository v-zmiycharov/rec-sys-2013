package statistics;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import com.google.common.base.Joiner;

import utils.Formatter;

public class Point {
	private double X;
	private double Y;
	
	public double getX() {
		return X;
	}
	public void setX(double x) {
		X = x;
	}
	public double getY() {
		return Y;
	}
	public void setY(double y) {
		Y = y;
	}
	
	public Point() {}
	
	public Point(double x, double y) {
		this.X = x;
		this.Y = y;
	}
	
	public String toString() {
		return "{" + Formatter.formatDouble(this.X) + "," + Formatter.formatDouble(this.Y) + "}"; 
	}
	
	public static String listToString(List<Point> points) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("# X   Y");
		sb.append("\r\n");
		
		for(Point point : points) {
			sb.append(" " + Formatter.formatDouble(point.getX()) + " " + Formatter.formatDouble(point.getY()));
			sb.append("\r\n");
		}
		
		return sb.toString();
	}
}
