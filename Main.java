package knapsack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws IOException {

		// clearing the data from the file so it can be run multiple times
		PrintWriter writer = new PrintWriter("src/files/fnma-dataset-complete.txt");
		writer.print("");
		writer.close();

		// Array to access each file and append the files data to a new file
		// that contains the data of each file
		String[] fileNames = { "fnma-dataset-00.txt", "fnma-dataset-01.txt", "fnma-dataset-02.txt",
				"fnma-dataset-03.txt", "fnma-dataset-04.txt", "fnma-dataset-05.txt", "fnma-dataset-06.txt",
				"fnma-dataset-07.txt", "fnma-dataset-08.txt", "fnma-dataset-09.txt", "fnma-dataset-10.txt",
				"fnma-dataset-11.txt", "fnma-dataset-12.txt", "fnma-dataset-13.txt", "fnma-dataset-14.txt",
				"fnma-dataset-15.txt" };
		File f;
		Scanner sc = null;
		FileWriter fwriter = new FileWriter("src/files/fnma-dataset-complete.txt", true);

		for (int fileNum = 0; fileNum < fileNames.length; fileNum++) {

			f = new File("src/files/" + fileNames[fileNum]);
			sc = new Scanner(f);
			while (sc.hasNext()) {
				fwriter.write(sc.nextLine());
				fwriter.append('\n');
			}

		}
		fwriter.close();
		sc.close();

		Scanner reader = new Scanner(new File("src/files/fnma-dataset-complete.txt"));
		ArrayList<String> input = new ArrayList<>();

		while (reader.hasNext()) {
			input.add(reader.nextLine());
		}
		reader.close();

		// array to store all of the mortgage objects created
		// this will be passed into the knapsack class constructor
		ArrayList<Mortgage> mortgages = new ArrayList<>();
		int i;

		// this loop will split each element of the arraylist by the | character
		// it will be split into an array that will contain all the fields for
		// each mortgage object
		// The array will then be passed into the Mortgage constructor and the
		// newly created Mortgage object will be stored in the mortgages array
		for (i = 1; i < input.size(); i++) {
			String fields[] = input.get(i).split("\\|");
			// create a constructor that takes in an array parameter
			// System.out.println(Arrays.toString(fields));

			try {
				// may throw NumberFormatException
				mortgages.add(new Mortgage(fields));
			} catch (NumberFormatException e) {
				continue;
			}

		}

		// Parsing the input for each pool class
		Scanner scan = new Scanner(new File("src/files/PoolSpec.txt"));
		// array to hold the data for each pool class
		// there will be 10 pool classes, so there will be 11 lines of input from the
		// file
		// the first line will have information about each line of data; this line will
		// not need to be used
		String[] poolArr = new String[11];
		i = 0;

		// loop to store each line into poolArr
		while (scan.hasNext()) {
			poolArr[i++] = scan.nextLine();
		}
		scan.close();

		// Creating an array to hold the 10 knapsack objects
		// The pool number will be the object's position in the array
		Knapsack[] pools = new Knapsack[10];
		for (i = 1; i < poolArr.length; i++) {
			// Creating a new knapsack object and storing it into the array
			pools[i - 1] = new Knapsack(poolArr[i].split("\\s+"));
		}
		/*
		ArrayList<Mortgage> list = pools[0].createKnapsack(mortgages);
	    System.out.println(list);
	    System.out.println(list.size());
	    */
		ArrayList<Mortgage> list;
	    for(int index = 0; index<4;index++) {
	    	list = pools[index].createKnapsack(mortgages);
		    System.out.println(list);
		    System.out.println(list.size());
		    
		 // clearing the data from the files so it can be run multiple times
			PrintWriter out = new PrintWriter("src/files/pool-" + (index + 1) + "-1");
			out.print("");
			out.close();
		    
		    FileWriter outputWriter = new FileWriter("src/files/pool-" + (index + 1) + "-1", true);
		    for (Mortgage m : list) {
		    	outputWriter.append(m.toString());
		    	outputWriter.append('\n');
		    }
		    outputWriter.close();
	    }
		
	    
		/*
		list = pools[1].createKnapsack(mortgages);
		System.out.println(list);
		System.out.println(list.size());

		list = pools[2].createKnapsack(mortgages);
		System.out.println(list);
		System.out.println(list.size());
		 
		 */
	    
		// System.out.println(pools[1].solveKnapsack());

	}

}
