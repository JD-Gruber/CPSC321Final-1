//imports
import java.io.*;
import java.util.Scanner;
import java.sql.*;  
import java.util.Properties;
import ViewTables.*;
import Functions.*;

//java -cp .;C:\Folder\mysql-connector.jar Homework9

class Homework9{
	
	static String Input;

	//Add country
	public static void Option2()
	{
		try {
			//  create  connection
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream("config.properties");
			prop.load(in);
			in.close();
			
			// connect to datbase
			String hst = prop.getProperty("host");
			String usr = prop.getProperty("user");
			String pwd = prop.getProperty("password");
			String dab = "gheberling_DB";
			String url = "jdbc:mysql://" + hst + "/" + dab;
			Connection con = DriverManager.getConnection(url, usr, pwd);

			// create and execute query
			Statement stmt = con.createStatement();
			String q = "SELECT country_code FROM Country";
			ResultSet rs = stmt.executeQuery(q);
			
			Scanner scanner = new Scanner(System.in);
			// prompt for country code to input
			System.out.print("Country code................:");
			String CountryCode = scanner.nextLine();
			// prompt for country name to input
			System.out.print("Country name................:");
			String CountryName = scanner.nextLine();
			// prompt for GDP 
			System.out.print("Country per capita gdp (USD):");
			String GDP = scanner.nextLine();
			int    GDPEntered = Integer.parseInt(GDP);
			//prompt for inflation
			System.out.print("Country inflation (pct).....:");
			String inflation = scanner.nextLine();
			float  inflationEntered = Float.parseFloat(inflation);

			// print results
			while(rs.next()) {
				String cntry_code = rs.getString("country_code");
				if(cntry_code.equals(CountryCode)){
					System.out.println("That Country Code already exists, returning to the main menu.");
					// release resources
					rs.close();
					stmt.close();
					con.close();
					mainMenu();
				}
			}
			
			// create and execute insert query
			stmt = con.createStatement();
			q = "INSERT INTO Country VALUES ('" + CountryCode + "', '" + CountryName + "', " + GDPEntered + ", " + inflationEntered + ");";
			stmt.executeUpdate(q);

			// release resources
			rs.close();
			stmt.close();
			con.close();
			}
		catch(Exception e) {
			System.out.println(e);
		}
		mainMenu();
	}
	
	//Find countries based on gdp and inflation
	public static void Option3()
	{
		try {
			//  create  connection
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream("config.properties");
			prop.load(in);
			in.close();
			
			// connect to datbase
			String hst = prop.getProperty("host");
			String usr = prop.getProperty("user");
			String pwd = prop.getProperty("password");
			String dab = "gheberling_DB";
			String url = "jdbc:mysql://" + hst + "/" + dab;
			Connection con = DriverManager.getConnection(url, usr, pwd);
			
			Scanner scanner = new Scanner(System.in);
			
			// prompt for Limit
			System.out.print("Number of countries to display:");
			String Limit = scanner.nextLine();
			int    LimitEntered = Integer.parseInt(Limit);
			// prompt for GDP 
			System.out.print("Minimum per capita gdp (USD)..:");
			String GDP = scanner.nextLine();
			int    GDPEntered = Integer.parseInt(GDP);
			//prompt for inflation
			System.out.print("Maximum inflation (pct).......:");
			String inflation = scanner.nextLine();
			float  inflationEntered = Float.parseFloat(inflation);
			
			// create and execute query
			Statement stmt = con.createStatement();
			String q = "SELECT country_code, country_name, gdp, inflation FROM Country WHERE gdp >= " + GDPEntered + " AND inflation <= " + inflationEntered + " ORDER BY gdp DESC, inflation" + " LIMIT " + LimitEntered;
			ResultSet rs = stmt.executeQuery(q);

			// print results
			while(rs.next()) {
				String cntry_name = rs.getString("country_name");
				String cntry_code = rs.getString("country_code");
				String gdp        = rs.getString("gdp");
				       inflation  = rs.getString("inflation");
				System.out.println(cntry_name + " (" + cntry_code + "), " + gdp + ", " + inflation);
			}

			// release resources
			rs.close();
			stmt.close();
			con.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		mainMenu();
	}
	
	//Update country's gdp and inflation
	public static void Option4()
	{
		boolean countryFound = false;
		try {
			//  create  connection
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream("config.properties");
			prop.load(in);
			in.close();
			
			// connect to datbase
			String hst = prop.getProperty("host");
			String usr = prop.getProperty("user");
			String pwd = prop.getProperty("password");
			String dab = "gheberling_DB";
			String url = "jdbc:mysql://" + hst + "/" + dab;
			Connection con = DriverManager.getConnection(url, usr, pwd);

			// create and execute query
			Statement stmt = con.createStatement();
			String q = "SELECT country_code FROM Country";
			ResultSet rs = stmt.executeQuery(q);
			
			Scanner scanner = new Scanner(System.in);
			// prompt for country code 
			System.out.print("Country code................:");
			String CountryCode = scanner.nextLine();
			// prompt for GDP 
			System.out.print("Country per capita gdp (USD):");
			String GDP = scanner.nextLine();
			int    GDPEntered = Integer.parseInt(GDP);
			//prompt for inflation
			System.out.print("Country inflation (pct).....:");
			String inflation = scanner.nextLine();
			float  inflationEntered = Float.parseFloat(inflation);

			// print results
			while(rs.next()) {
				String cntry_code = rs.getString("country_code");
				if(cntry_code.equals(CountryCode)){
					countryFound = true;
				}
			}
			
			//perform the update
			if(countryFound == true){
				stmt = con.createStatement();
				q = "UPDATE Country SET gdp = " + GDPEntered + ", inflation = " + inflationEntered + " WHERE country_code = '" + CountryCode + "'";
				stmt.executeUpdate(q);
			}
			else{
				System.out.println("The country doesn't exist");
				mainMenu();
			}

			// release resources
			rs.close();
			stmt.close();
			con.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		mainMenu();
	}
	
	//Exit
	public static void Option5()
	{
		System.exit(0);
	}
	
	//this is the main menu for the program
	public static void mainMenu(){
		try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		boolean running = true;
		Scanner scanner = new Scanner(System.in);
		System.out.println("1. View Records");
		System.out.println("2. Add Records");
		System.out.println("3. Update Records");
		System.out.println("4. Delete Records");
		System.out.println("5. Exit");
		System.out.print("Enter your choice (1-5):");

		Input = scanner.nextLine();
		int InputInt = Integer.parseInt(Input);
		execute( InputInt );

	}
	
	//main 
	public static void main(String[] args)
	{
		mainMenu();
	}

	//this code determines which option the user has selected and goes to the appropriate function
	public static void execute(int userInput)
	{
		if(userInput == 1)
		{
			Option1();
		}

		else if(userInput == 2)
		{
			Option2();
		}

		else if(userInput == 3)
		{
			Option3();
		}

		else if(userInput == 4)
		{
			Option4();
		}

		else if(userInput == 5)
		{
			Option5();
		}
		
		else
		{
			System.out.println("Error");
			mainMenu();
		}
	}

	// List Tables to View
	public static void Option1(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("1. View Teams");
		System.out.println("2. View Quarterbacks");
		System.out.println("3. View Offensive Players");
		System.out.println("4. View Defensive Players");
		System.out.println("5. View Head Coaches");
		System.out.println("6. Exit");
		System.out.print("Enter your choice (1-6):");
		Input = scanner.nextLine();
		int InputInt = Integer.parseInt(Input);
		executeOption1( InputInt );
	}
	
	//this code determines which option the user has selected from view records and goes to the appropriate function
	public static void executeOption1(int userInput)
	{
		if(userInput == 1)
		{
			Teams.ViewTeams();
		}

		else if(userInput == 2)
		{
			Option2();
		}

		else if(userInput == 3)
		{
			ViewOffensivePlayers.ViewOffensivePlayers();
		}

		else if(userInput == 4)
		{
			Option4();
		}

		else if(userInput == 5)
		{
			Option5();
		}
		
		else if(userInput == 6)
		{
		    mainMenu();	
		}
		
		else
		{
			System.out.println("Error");
			mainMenu();
		}
	}

}


