package antiSpamFilter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import antiSpamFilter.FileChooser;

import static org.junit.Assert.*;

public class FileChooserTest
{
	FileChooser chooser;
	FileChooser chooser2;

	/*
	 * Testing Constructor
	 */
	@Test
	public void test_method_FileChooser()
	{
		System.out.println("Now Testing Method:FileChooser");		
		//Constructor
		chooser = new FileChooser("log", "Ham or Spam");
		chooser2 = new FileChooser("cf","Rules");
		//Check Test Verification Points
		assertEquals("log", chooser.tipoDeFicheiro);
		assertEquals("Ham or Spam", chooser.titulo);
		assertEquals("cf", chooser2.tipoDeFicheiro);
		assertEquals("Rules", chooser2.titulo);
		
	}

	/*
	 * Testing getPath()
	 */
	@Test
	public void test_method_getPath()
	{
		System.out.println("Now Testing Method:getPath");		
		
		//Get expected result and result
		String expResult = "";		
		//Check Return value
		assertNotNull(expResult, new String());		
		
	}

}
