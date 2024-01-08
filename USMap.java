import java.util.Scanner;
import java.util.Arrays;

/**
 *	USMap.java
 * 	Displays gray dots for cities around the United States
 * 	Displays large blue dots for the high populace cities
 * 	Displays larger red dots for the top ten populated cities
 *
 *	@author	Prathik Kumar
 *	@since	September 12, 2023
 */

public class USMap {
	private String[] xCoords; //	Stores the x coordinates of every city
	private String[] yCoords; //	Stores the y coordinates of every city	
	private String[] popCities; //	Stores the populations for all the big cities
	private String[] names; // 	Stores the names of every single city
	private String[] bigNames; //	Stores the names of all the cities with top population
	
	// This is my constructor, it is used to initialize all my field variables
	public USMap() {
		names = new String[1112];
		bigNames = new String[1112];
		popCities = new String[1112];
		yCoords = new String[1112];
		xCoords = new String[1112];
	}
	
	// Main is used to call all my other methods in the program, creates obj of class
	public static void main(String[] args) {
		USMap usm = new USMap();
		usm.setupCanvas();
		usm.readIt();
		usm.readingBigCities();
		usm.grayDots();
	}
	
	/** Set up the canvas size and scale, given by Mr. Greenstein*/
	public void setupCanvas() {
		StdDraw.setTitle("USMap");
		StdDraw.setCanvasSize(900, 512);
		StdDraw.setXscale(128.0, 65.0);
		StdDraw.setYscale(22.0, 52.0);
		StdDraw.setPenRadius(0.05);
		StdDraw.setPenColor(StdDraw.RED);
	}
	
	/**
	 * 	First we set the pen radius to 0.006 for the all the cities in the US
	 * 	The color is also set to gray, and we store all the cities in a city name array
	 * 	We also take all the x and y coords, using them we create gray points for cities
	 */
	public void readIt() {
		Scanner cities = null;
		int cnt = 0;
		cities = FileUtils.openToRead("cities.txt");
		
		while (cities.hasNext() && cnt < 1112) {
			String y = cities.next();
			String x = cities.next();
			names[cnt] = cities.nextLine();
			xCoords[cnt] = y;
			yCoords[cnt] = x;
			cnt++;
		}
	}
	
	/**	This method will read the big cities txt takes the entire line onto one variable
	 * 	We are using substring to take the city, store it in the big city array
	 * 	Same techniques are also used to get the populations of the big cities (stored in population array)
	 * 	After we get all our big city data, we call the compare cities method
	 */
	public void readingBigCities() {
		Scanner bigCities = null;
		int counter = 0;
		bigCities = FileUtils.openToRead("bigCities.txt");
		String[] entireLine = new String[276];
		
		while (bigCities.hasNext() && counter < 276) {
			
			entireLine[counter] = bigCities.nextLine();
			int index = entireLine[counter].indexOf(",");
			int numbersIndex = entireLine[counter].indexOf(" ");
			String city = entireLine[counter].substring(numbersIndex, index);
			int lastSpace = entireLine[counter].lastIndexOf(" ");
			city = city.trim();
			String popNum = entireLine[counter].substring(lastSpace);
			bigNames[counter] = city;
			popCities[counter] = popNum;
			
			
			counter++;
		}
		compareCities();
	}
	
	/**
	 * 	Our original normal city name array has city name and state, so we use substring to just
	 * 	take the city name, and compare it with all big cities. We use for loops so that we can 
	 * 	compare one element in normal city name array with all of the elements with the big city
	 * 	names, and then move on to the next normal city name. For our first ten big cities
	 * 	(most populated cities), we have the same logic to make it blue, just use a different color.
	 * 	For all the other cities, they use the blue logic.
	 */
	public void compareCities() {
		boolean flag = false;
		for (int i = 0; i < 1112; i++) {
			String sub = names[i].substring(0, names[i].indexOf(","));
			sub = sub.trim();
			flag = false;
			for (int j = 0; j < 275; j++) {
				if (bigNames[j].equalsIgnoreCase(sub) && j >= 10)
				{
					double popSizeCalculation = 0.6 * (Math.sqrt(Double.parseDouble(popCities[j]))/18500);
					
					double yDouble = Double.parseDouble(yCoords[i]);
					double xDouble = Double.parseDouble(xCoords[i]);
					
					StdDraw.setPenRadius(popSizeCalculation);
					StdDraw.setPenColor(StdDraw.BLUE);
					StdDraw.point(yDouble, xDouble);
				} else if (bigNames[j].equalsIgnoreCase(sub) && j < 10 && !flag){ // this checks if index of array is less than 10, meaning highest populace cities
					double popSizeCalculationRed = 0.6 * (Math.sqrt(Double.parseDouble(popCities[j]))/18500);
					
					double yDoubleRed = Double.parseDouble(yCoords[i]);
					double xDoubleRed = Double.parseDouble(xCoords[i]);
					
					StdDraw.setPenRadius(popSizeCalculationRed);
					StdDraw.setPenColor(StdDraw.RED);
					StdDraw.point(yDoubleRed, xDoubleRed);
					flag = true;
				}
			}
		}
	}
	
	/**
	 *	Draws the gray dots after all the red and blue dots are already drawn
	 */
	public void grayDots() {
		StdDraw.setPenRadius(0.006);
		StdDraw.setPenColor(StdDraw.GRAY);
		for (int i = 0; i < 1112; i++) {
			double yDouble = Double.parseDouble(yCoords[i]);
			double xDouble = Double.parseDouble(xCoords[i]);
			StdDraw.point(yDouble, xDouble);
		}
	}

}
