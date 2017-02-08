package net.sydrus.yuzuku;

import java.io.File;

public class Startt {
	
	  public static void main(String[] args)
	    throws Exception
	  {
	    if (new File("/").getCanonicalPath().contains("system32")) {
	      throw new Exception("**Error a system folder was detected, Remove the jar file from this folder or if the jar file is not in a system folder create a .bat file with the \"java -jar jarName.jar\"**");
	    } new YuzukuBot();
	 }
}
