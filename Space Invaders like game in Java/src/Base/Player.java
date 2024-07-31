package Base;

import BoardPieces.*;
import GUI.*;

import java.io.*;
import java.util.*;

public class Player {
	private static String name;
	private static int score;
	private static int highScore;
	private static ArrayList<String[]> profiles;
	
	public static void setName(String s) { name = s; }
	
	public static String getName() { return name; }
	
	public static void setScore(int i) { score = i; }
	
	public static int getScore() { return score; }
	
	public static void setHighScore(int i) { highScore = i; }
	
	public static int getHighScore() { return  highScore; }
	
	public static void setProfiles(ArrayList<String[]> al) { profiles = al; }
	
	public static ArrayList<String[]> getProfiles() { return profiles; }
	
	public Player() {
		profiles = new ArrayList<String[]>();
		load();
	}
	
	public static void increaseScore(int i) { score += i; }
	
	public static boolean newRecord() {
		if(score > highScore) { 
			highScore = score;
			String[] temp = {name, String.valueOf(highScore)};
			for(int i = 0; i < 3; i++) {
				if(profiles.get(i)[0].equals(name)) { profiles.set(i, temp); }
			}
			return true;
		} else { return false; }
	}
	
	public static void save() {
		try {
			FileWriter fw = new FileWriter("profiles.txt");
			PrintWriter pw = new PrintWriter(fw);
			for(int i = 0; i < 3; i++) {
				pw.println(profiles.get(i)[0]+":"+profiles.get(i)[1]);
			}
			pw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void load() {
		try {
			FileReader fr = new FileReader("profiles.txt");
			BufferedReader br = new BufferedReader(fr);
			//profiles = new ArrayList<String[]>();
			String inputLine;
			String[] inputArray;
			while((inputLine = br.readLine()) != null) {
				inputArray = inputLine.split(":");
				profiles.add(inputArray);
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			name = "-";
			score = 0;
			highScore = 0;
			String[] init = { name, String.valueOf(highScore) };
			for(int i = 0; i < 3; i++) {
				profiles.add(init);
			}
			save();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
