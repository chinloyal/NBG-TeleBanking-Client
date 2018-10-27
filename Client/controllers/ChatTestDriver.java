package controllers;

import java.util.Scanner;

public class ChatTestDriver {

	public static void main(String[] args) {
		TransactionController tc = new TransactionController();
				
		while(true) {
			Scanner sc = new Scanner(System.in);

			System.out.print("You: ");

			String input = sc.nextLine();

			System.out.println("Assistant: " + tc.smallTalk(input));
		}
	}

}
