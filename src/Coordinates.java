/**
 * Created by Rahim on 5/17/2017.
 */
public class Coordinates {
    private double latitude;
    private double longitude;
    private boolean west;
    private boolean north;

    public Coordinates(){
        latitude = 0;
        longitude = 0;
    }
    public Coordinates(double lat, double lon){
        setLatitude(lat);
        setLongitude(lon);
    }

    public void setLatitude(double lat){
        if(lat < 0){
            north = false;
            latitude = -lat;
        }
        else{
            north = true;
            latitude = lat;
        }
    }
    public void setLongitude(double lon){

        if(lon < 0){
            west = false;
            longitude = -lon;
        }
        else{
            west = true;
            longitude = lon;
        }
    }

    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }
    public boolean getWest(){
        return west;
    }
    public boolean getNorth(){
        return north;
    }
    public void print(){
        System.out.print(latitude + " ");
        if(north){
            System.out.print("N,  ");
        }
        else{
            System.out.print("S,  ");
        }
        System.out.print(longitude+ " ");
        if(west){
            System.out.println("W");
        }
        else{
            System.out.println("E");
        }
    }
}
