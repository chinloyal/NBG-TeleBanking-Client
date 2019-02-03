package ai.abstracts;

import org.alicebot.ab.*;

import com.chinloyal.speak.*;
public abstract class ArtificialIntelligence extends Voice{
	
	private String response;
	private Chat chatSession;
	private Bot bot;
	
	public ArtificialIntelligence() {
		super("My Google API Key");
		String resourcesPath = getResourcesPath();
		MagicBooleans.trace_mode = false;
		bot = new Bot("super", resourcesPath);
		chatSession = new Chat(bot);
		response = "";
	}
	
	public ArtificialIntelligence(String resourcesPath) {
		super("My Google API Key");
		MagicBooleans.trace_mode = false;
		bot = new Bot("super", resourcesPath);
		chatSession = new Chat(bot);
		response = "";
	}
	
	public String respond(String input) {
	
		if ((input == null) || (input.length() < 1))
			input = MagicStrings.null_input;
		else {
			response = chatSession.multisentenceRespond(input);
			response = response.replaceAll("&lt;", "<");
			response = response.replaceAll("&gt;",	 ">");

			processAction(response, input);

			return response;
		}
		
		return response;
	}
	
	private String getResourcesPath() {
		/*URL url = getClass().getResource("/ai/");
		
		if(url != null) {
			return url.getFile();
		}else {
			File file = new File("/ai/");
			
			try {
				url = new URL("file:/" + file.getAbsolutePath());
			} catch (MalformedURLException e) {
				e.printStackTrace(); //Intentional
			}
			
			return url.getFile();
		}*/
		return "Client/ai";
		
	}
	
	/**
	 * This method should execute an action based on the response of the bot
	 * and the user's request.
	 * 
	 * @param response
	 * @param request
	 */
	public abstract void processAction(String response, String request);

}
