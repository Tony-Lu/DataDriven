package com.w2a.rough;

import java.util.Date;

public class TestTimeStamp {
	
	public static void main(String[] args) {
		
		//this is for test jenkins trigger new build once git checked in
		//using Eclipse plugin commit to git
		Date d = new Date();
		String screenShotName = d.toString().replace(":", "_").replace(" ",	"_")+".jpg";
		
		System.out.println(screenShotName);
		
		
		
	}
	

}
