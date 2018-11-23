import java.io.File;
import java.util.Scanner;

public class FileReader {
	private Scanner in;
	FileReader(){
		in = new Scanner(System.in);
	}
	FileReader(String file){
		try{
			in = new Scanner(new File(file));
		}
		catch(Exception e){
			System.out.println("You Messed Up");
		}
	}
	public String getLine(int row){
		int i = 0;
		while(in.hasNextLine() && i < row){
			in.nextLine();
			i++;
		}
		if(i != row){
			return "";
		}
		if(in.hasNextLine()){
			return in.nextLine();
		}
		else{
			return "";
		}
	}
	public String[][] getFile(){		
		String[][] temp = new String[300][8];
		String line;
		int walker;
		for(int i = 0; i < 300;i++){
			line = in.nextLine();
			walker = 0;
			for(int j = 0; j < 7;j++){
				temp[i][j] = line.substring(walker, line.indexOf(',', walker));
				walker = line.indexOf(',', walker)+1;
			}
		}
		return temp;
	}

}
