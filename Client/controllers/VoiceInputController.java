package controllers;

import com.chinloyal.listen.Hear;

public class VoiceInputController extends Hear {
	
	public static int PROGRESS = 0;
	

	public VoiceInputController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void closing() {
//		System.out.println("Stop speaking...");
	}

	@Override
	public void recording() {
//		System.out.println("Speak now...");
//		PROGRESS = 50;
	}

}
