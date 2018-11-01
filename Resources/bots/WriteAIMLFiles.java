package bots;

import java.io.File;

import org.alicebot.ab.Bot;

public class WriteAIMLFiles {
	static String botName = "super";

	public static void main(String[] args) {
		try {

			String resourcesPath = getResourcesPath();
			System.out.println(resourcesPath);
			Bot bot = new Bot("super", resourcesPath);
			
			bot.writeAIMLFiles();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getResourcesPath() {
		File dir = new File("Resources/");
		
		return dir.getAbsolutePath();
	}

}
