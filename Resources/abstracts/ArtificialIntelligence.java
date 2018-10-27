package abstracts;

import java.io.File;

import org.alicebot.ab.*;
public abstract class ArtificialIntelligence {
	
	private String response;
	private String request;
	private Chat chatSession;
	private Bot bot;
	
	public ArtificialIntelligence() {
		String resourcesPath = getResourcesPath();
		MagicBooleans.trace_mode = false;
		bot = new Bot("super", resourcesPath);
		chatSession = new Chat(bot);
		response = "";
		request = "";
	}
	
	public String smallTalk(String input) {
		try {
	
			if ((input == null) || (input.length() < 1))
				input = MagicStrings.null_input;
			else {
				request = input;
				response = chatSession.multisentenceRespond(request);
				while (response.contains("&lt;"))
					response = response.replace("&lt;", "<");
				while (response.contains("&gt;"))
					response = response.replace("&gt;", ">");
				
				processResponse(input);

				return response;
			}
		} catch (Exception e) { //Tried to catch IOExction here but java says it's unreachable, so I'm gonna catch an exception.
			return "I am a banking AI, not google.";
		}
		
		return response;
	}
	
	private static String getResourcesPath() {
		File dir = new File("Resources/");
		
		return dir.getAbsolutePath();
	}
	
	private void initializeBot() {
		
	}
	
	/**
	 * This method should execute an action based on the response.
	 * 
	 * @param request
	 */
	public abstract void processResponse(String response);

}
