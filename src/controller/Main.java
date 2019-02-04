package controller;

import java.io.IOException;

import MapClassPackage.LoadMapFromFile;
import MapClassPackage.MapClass;

public class Main {

	public static void main(String args[]) throws IOException{
//		System.out.println("Get Ready to play Conquest!\n");
//		GameMenu gameMenu=new GameMenu();
//		gameMenu.display();
//		gameMenu.select();
		
//		MapClass map=new MapClass();
//		map.populateMap();
//		map.writeTheMapToTheTextFile();
		
	    LoadMapFromFile lm=new LoadMapFromFile();
	    lm.loapMap();
	    lm.writeTheMapToTheTextFile();
		
		
	}
	
	
}
