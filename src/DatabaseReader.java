import java.sql.*;

import static jdk.nashorn.internal.objects.NativeString.trim;

/**
 * Created by Rahim on 6/5/2017.
 */
public class DatabaseReader {
    Connection conn;
    Statement stat;
    DatabaseReader() {
        String driverName = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driverName);
        } catch (Exception e) {
            System.out.println("Driver Not Found.");
            return;
        }
        String url = "jdbc:mysql://75.140.92.204:3306/Stars?useSSL=false";
        String username = "guest";
        String password = "password";
        try {
            conn = DriverManager.getConnection(url, username, password);
            stat = conn.createStatement();

        }
        catch (Exception e) {
            System.out.println("Url Not Found");
        }
    }
    DatabaseReader(int in){
        String driverName = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driverName);
        } catch (Exception e) {
            System.out.println("Driver Not Found.");
            return;
        }
        String url = "jdbc:mysql://localhost:3306/Stars?useSSL=false";
        String username = "";
        String password = "";
        try {
            conn = DriverManager.getConnection(url, username, password);
            stat = conn.createStatement();
            FileReader f = new FileReader("TopStars.txt");
            String[][] temp = f.getFile();
//            for(int i = 0; i < 300; i++){
//                System.out.println("INSERT INTO brighttest VALUES("+temp[i][0]+",'" + temp[i][1] + "','" + temp[i][2] + "'," + temp[i][3] + "," + temp[i][4] + "," + temp[i][5] + "," + temp[i][6] + ");");
//                String query = "INSERT INTO brighttest VALUES(?,?,?,?,?,?,?)";
//                PreparedStatement pStmt = conn.prepareStatement(query);
//                pStmt.setInt(1,Integer.parseInt(trim(temp[i][0])));
//                pStmt.setString(2,temp[i][1]);
//                pStmt.setString(3,temp[i][2]);
//                pStmt.setInt(4,Integer.parseInt(temp[i][3]));
//                pStmt.setInt(5,Integer.parseInt(temp[i][4]));
//                pStmt.setFloat(6,Float.parseFloat(temp[i][5]));
//                pStmt.setFloat(7,Float.parseFloat(temp[i][6]));
//                pStmt.execute();
//                stat.execute("INSERT INTO brighttest VALUES("+temp[i][0]+",'" + temp[i][1] + "','" + temp[i][2] + "'," + temp[i][3] + "," + temp[i][4] + "," + temp[i][5] + "," + temp[i][6] + ");");
//            }
            ResultSet result = stat.executeQuery("SELECT * FROM  brightTest");
            result.next();
            System.out.println(result.getString("NickName"));

        }
        catch (Exception e) {
            System.out.println("Url Not Found");
        }
    }
    public String[][] getDatabase(){
        String[][] temp = new String[300][8];
        try{
            stat = conn.createStatement();
            ResultSet result = stat.executeQuery("SELECT * FROM  brighttest");
            result.next();
            for(int i = 0; i < 300; i ++){
                for(int j = 0; j < 7; j++) {
                    temp[i][j] = result.getString(j + 1);
                    //System.out.print(temp[i][j] + "    ");
                }
                result.next();

            }
        }
        catch(Exception e){
            System.out.println("You messed uperoni");
        }
        return temp;
    }
}

