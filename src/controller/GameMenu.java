package controller;

import java.util.Scanner;

public class GameMenu {
	
	private int choice;

	public GameMenu(){
		
	}
	
	public void display(){
		System.out.println("1. Start Game\n2. Exit Game");
	}
	
	public void select(){
		Scanner scan=new Scanner(System.in);
		choice= scan.nextInt();
		switch(choice){
		case 1:
			System.out.println("Game has started");
			break;
		case 2:
			System.out.println("Exiting Game");
			System.exit(0);
			break;
		default:
			System.out.println("Invalid choice");
		}
	}
	
}
