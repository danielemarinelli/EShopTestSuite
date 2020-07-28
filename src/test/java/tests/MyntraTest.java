package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MyntraHome;
import pages.MyntraLogin;

import java.util.HashMap;

public class MyntraTest extends TestBase{
	
	MyntraHome home =null;
	MyntraLogin login = null;
	
	
	@Test(dataProvider = "getData")
	public void verifyMyntraLoginLogout(int itr, HashMap<String,String> data) throws Exception {
		home = new MyntraHome(getDriver());
		home.navigateToLoginPage();
		login = new MyntraLogin(getDriver());
		login.performLogin(data.get("username"),data.get("password"));
		home = new MyntraHome(getDriver());
		if(itr==1){
			home.performLogout();
		}
		if(itr==2){
			Assert.assertEquals(home.verifyLoginError("Account does not exist"),true,
					" Account does not exist - error was not visible");
		}
	}



}
