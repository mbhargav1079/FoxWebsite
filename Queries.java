package com.svk.foxwebsite;

public class Queries {
	private static WebsiteProperties prop = new WebsiteProperties("DataFile.properities");
	private static String showstoVerify[] = {"So You Think You Can Dance",
			                             	 "Meghan Markle: An American Princess",
			                                 "Hypnotize Me",
			                                 "24 Hours To Hell & Back"};
	public static void main(String[] args) throws Exception{

		executeQuery5();

	}
	public static void executeQuery1() throws InterruptedException {

		FoxWebsite fw = new FoxWebsite();
		fw.BrowserInit(prop.getProperty("Fox.URL"));
		fw.FoxSignup();
		fw.FoxSignin();
	}
	public static void executeQuery2() throws InterruptedException {

		FoxWebsite fw = new FoxWebsite();
		fw.BrowserInit(prop.getProperty("Fox.URL"));
		fw.FoxSignin();
		fw.ClickTab("FOX", "FOX Social", 10000);
		fw.ExportLast4Shows();
	}
	public static void executeQuery3() throws InterruptedException {

		FoxWebsite fw = new FoxWebsite();
		fw.BrowserInit(prop.getProperty("Fox.URL"));
		fw.FoxSignin();
		fw.ClickTab("FX","FOX Social",5000);
		String[][] result = fw.VerifyShows("FX",showstoVerify,null);
		for(String[] rec : result){
			System.out.println(rec[0] + " :"+ rec[1]);
		}
	}
	public static void executeQuery4() throws InterruptedException {

		FoxWebsite fw = new FoxWebsite();
		fw.BrowserInit(prop.getProperty("Fox.URL"));
		fw.FoxSignin();
		
		fw.ClickTab("National Geographic","FOX Social",5000);
		String[][] resultNG = fw.VerifyShows("National Geographic",showstoVerify,null);
		
		fw.ClickTab("FOX Sports","FOX Social",5000);
		String[][] resultFxSports = fw.VerifyShows("FOX Sports",showstoVerify,null);
		
		fw.ClickTab("All Shows","FOX Social",5000);
		String[][] resultAllShows = fw.VerifyShows("All Shows",showstoVerify,null);
		
		System.out.println("*********** National Geographic *************************");
		for(String[] rec : resultNG){
			System.out.println(rec[0] + " :"+ rec[1]);
		}
		System.out.println("****************************************");
		
		

		System.out.println("*********** FOX Sports *************************");
		for(String[] rec : resultFxSports){
			System.out.println(rec[0] + " :"+ rec[1]);
		}
		System.out.println("****************************************");
		
		

		System.out.println("*********** All Shows *************************");
		for(String[] rec : resultAllShows){
			System.out.println(rec[0] + " :"+ rec[1]);
		}
		System.out.println("****************************************");
	}
	public static void executeQuery5() throws InterruptedException {

		FoxWebsite fw = new FoxWebsite();
		fw.BrowserInit(prop.getProperty("Fox.URL"));
		fw.FoxSignin();
		String ExcelFile = prop.getProperty("Excel.File");

		
		fw.ClickTab("FX","FOX Social",5000);
		String[][] resultFx = fw.VerifyShows("FX",showstoVerify,null);
		
		fw.ClickTab("National Geographic","FOX Social",5000);
		String[][] resultNG = fw.VerifyShows("National Geographic",showstoVerify,null);
		
		fw.ClickTab("FOX Sports","FOX Social",5000);
		String[][] resultFxSports = fw.VerifyShows("FOX Sports",showstoVerify,null);
		
		fw.ClickTab("All Shows","FOX Social",5000);
		String[][] resultAllShows = fw.VerifyShows("All Shows",showstoVerify,null);
		
		String[][] result = new String[showstoVerify.length][2];
		for (int i = 0; i<showstoVerify.length; i++) {
			result[i][0] = resultFx[i][0];
			result[i][1] = "EMPTY"	;
			if(resultFx[i][1] != null && "Displayed".equals(resultFx[i][1])){
				result[i][1] = result[i][1] + ",FX"	;
			}
			if(resultNG[i][1] != null && "Displayed".equals(resultNG[i][1])){
				result[i][1] = result[i][1] + ",National Geographic"	;
			}
			if(resultFxSports[i][1] != null && "Displayed".equals(resultFxSports[i][1])){
				result[i][1] = result[i][1] + ",FOX Sports"	;
			}
			if(resultAllShows[i][1] != null && "Displayed".equals(resultAllShows[i][1])){
				result[i][1] = result[i][1] + ",All Shows"	;
			}
			if(result[i][1].split(",").length>2){
				result[i][1] = "Duplicated";
			}
			
			if(resultFx[i][1] != null && "Displayed".equals(resultFx[i][1]) && "Duplicated".equals(result[i][1])){
				resultFx[i][1]= "Duplicated";
			}
			if(resultNG[i][1] != null && "Displayed".equals(resultNG[i][1]) && "Duplicated".equals(result[i][1])){
				resultNG[i][1]= "Duplicated";
			}
			if(resultFxSports[i][1] != null && "Displayed".equals(resultFxSports[i][1]) && "Duplicated".equals(result[i][1])){
				resultFxSports[i][1]= "Duplicated";
			}
			if(resultAllShows[i][1] != null && "Displayed".equals(resultAllShows[i][1]) && "Duplicated".equals(result[i][1])){
				resultAllShows[i][1]= "Duplicated";
			}
		}
		ExcelWorkBook book = new ExcelWorkBook(ExcelFile, "FX");			
		book.writeData(resultFx);
		
		book = new ExcelWorkBook(ExcelFile, "National Geographic");			
		book.writeData(resultNG);
		
		book = new ExcelWorkBook(ExcelFile, "FOX Sports");			
		book.writeData(resultFxSports);
		
		book = new ExcelWorkBook(ExcelFile, "All Shows");			
		book.writeData(resultAllShows);

	}

}
