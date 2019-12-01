import javax.swing.*;

import java.awt.*;

/**
 * Created by Rahim on 5/29/2017.
 */
public class Star extends CelestialObject {

    private int number;
    private String properName;
    private String Name;
    private int rightAscensionHour;
    private int rightAscensionMinute;
    private int rightAscensionSecond;
    private double declination;
    private double visibleMagnitude;
    
    private int x;
    private int y;
    private int radius;


    Star(int no, String p, String n, int h, int m, int s, double d, double v){
        number = no;
        properName = p;
        Name = n;
        rightAscensionHour = h;
        rightAscensionMinute = m;
        rightAscensionSecond = s;
        declination = d;
        visibleMagnitude = v;
        x = -1;
        y = -1;
        
    }

    @Override
    void draw(Graphics g) {
        //draw
        int width = 600;
        if(altitude > 0) {
            g.setColor(Color.white);
            //x = (int)((width/2) - (width/2) * Math.sin(Math.toRadians(azimuth)) * Math.sin(Math.toRadians(90 - altitude)));
            //y = (int)((width/2) - (width/2) * Math.cos(Math.toRadians(azimuth)) *  Math.sin(Math.toRadians(90 - altitude)));
            x = (int)((width/2) - (width/2) * Math.sin(Math.toRadians(azimuth)) * ((90-altitude)/90));
            y = (int)((width/2) - (width/2) * Math.cos(Math.toRadians(azimuth))* ((90-altitude)/90));
            radius = (int)(((-visibleMagnitude) + 5) * 1.5);
//            if(radius == 1){
//            	System.out.println(properName);
//            }
            g.fillOval( x, y,radius, radius);
        }
    }

    @Override
    String print() {
        return String.format("%-" + 3 + "d", number)  + " " + String.format("%-" + 25 + "s", properName) + String.format("%-" + 18 + "s", Name) + String.format("%" + 2 + "dh",rightAscensionHour) + String.format("%-" + 2 + "dm",rightAscensionMinute) + " " +/* String.format("%" + 2 + "ds",rightAscensionSecond) + " "  +*/ String.format("%-7.2f",declination)+ String.format("%-7.2f", visibleMagnitude) + String.format("%-7.2f",super.altitude) + String.format("%-6.2f", super.azimuth);

    }

    public void update(Coordinates c, Time t){
        double longtemp = c.getLongitude();
        if(c.getWest()){
            longtemp = -longtemp;
        }
        double lattemp = c.getLatitude();
        if(!c.getNorth()){
            lattemp = -lattemp;
        }
        double LHA = t.getSiderealTime() + (longtemp * 24.0) / 360.0 - (rightAscensionHour + rightAscensionMinute / 60.0 + rightAscensionSecond / 3600.0);
        if(LHA< 0){
        	LHA += 24;
        }
        altitude = Math.toDegrees(Math.asin((Math.sin(Math.toRadians(lattemp)) * Math.sin(Math.toRadians(declination))) + (Math.cos(Math.toRadians(lattemp)) * Math.cos(Math.toRadians(declination)) * Math.cos(Math.toRadians((LHA / 24.0) * 360.0)))));
        azimuth = Math.toDegrees(Math.acos((Math.sin(Math.toRadians(declination)) - (Math.sin(Math.toRadians(lattemp)) * Math.sin(Math.toRadians(altitude)))) / (Math.cos(Math.toRadians(lattemp)) * Math.cos(Math.toRadians(altitude)))));
        if(LHA > 0 && LHA < 12){
            azimuth = 360 - azimuth;
        }

    }

	public void clicked(){
		System.out.println("You clicked on " + Name);
	}
	
	@Override
	public boolean contains(Point p){
		if(p.x < (x + radius ) && p.x > (x - radius ) ){
			if(p.y < (y + radius) && p.y > (y - radius )){
				return true;
			}
		}	
		return false;
	}

}
