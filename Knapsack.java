package knapsack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Knapsack {

	private int classNum;
	// poolSize represents the range of valid values for the class
	// For all array fields, array[0] is the min value in the range and array[1] is
	// the max range value
	private int[] poolSize;
	private double[] noteRate;
	private int[] ficoScore;
	// The maximum percentage of loans from any given US state allowed into the
	// pool; for example, if the value is 5%, then the total number of loans from a
	// single state, such as NY, cannot exceed 5% of the loans in the pool
	private int statePercent;
	private int[] dtiRange;
	private int[] ltvRange;
	private ArrayList<Mortgage> validMortgages;
	
	public int maxficoscore() {
		return ficoScore[1];
	}
	public int minficoscore() {
		return ficoScore[0];
	}
	
	public ArrayList<Mortgage> groupableMortgages(int index){
		ArrayList<Mortgage> groupableMortgages = new ArrayList<Mortgage>();
		Mortgage baseCase = validMortgages.get(index);
		
		for(Mortgage potentialGroup: validMortgages) {
			if(baseCase.maturityDate().equals(potentialGroup.maturityDate())) {
				if(baseCase.getLoanTerm() == potentialGroup.getLoanTerm()) {
					if(baseCase.propertyType().equals(potentialGroup.propertyType())) {
					groupableMortgages.add(potentialGroup);
					}
				}
			}
		}
		return groupableMortgages;
		
	}

	public Knapsack(String[] arr) {
		// Temp string array to temporarily convert data types
		String[] tempArr = new String[2];

		classNum = Integer.parseInt(arr[0].substring(0, 1));
		// tempArr is used to convert the values from a String to an int here
		tempArr = arr[1].split("\\-");
		// new memory must be allocated for the poolSize array to avoid memory leakage
		poolSize = new int[2];
		// converting the data and storing the data into the poolSize array
		poolSize[0] = Integer.parseInt(tempArr[0].substring(1).replace(",", ""));
		poolSize[1] = Integer.parseInt(tempArr[1].substring(1).replace(",", ""));

		// Repeating the same process for all array fields
		tempArr = arr[2].split("\\-");
		noteRate = new double[2];
		noteRate[0] = Double.parseDouble(tempArr[0]);
		noteRate[1] = Double.parseDouble(tempArr[1]);

		tempArr = arr[3].split("\\-");
		ficoScore = new int[2];
		ficoScore[0] = Integer.parseInt(tempArr[0]);
		ficoScore[1] = Integer.parseInt(tempArr[1]);

		statePercent = Integer.parseInt(arr[4].substring(0, 1));

		tempArr = arr[5].split("\\-");
		dtiRange = new int[2];
		dtiRange[0] = Integer.parseInt(tempArr[0]);
		dtiRange[1] = Integer.parseInt(tempArr[1]);

		tempArr = arr[6].split("\\-");
		ltvRange = new int[2];
		ltvRange[0] = Integer.parseInt(tempArr[0]);
		ltvRange[1] = Integer.parseInt(tempArr[1]);

		validMortgages = new ArrayList<>();

	}

	public void printKnapsack() {

		System.out.println(classNum);
		System.out.println(Arrays.toString(poolSize));
		System.out.println(Arrays.toString(noteRate));
		System.out.println(Arrays.toString(ficoScore));
		System.out.println(statePercent);
		System.out.println(Arrays.toString(dtiRange));
		System.out.println(Arrays.toString(ltvRange));

	}

	// the method traverses through the array of all mortgage objects and checks
	// whether an object is valid or not. It'll return an array of valid objects
	// that can be used in the knapsack algo.
	public ArrayList<Mortgage> findValues(ArrayList<Mortgage> arrlst) {
		validMortgages = new ArrayList<>();
		for (Mortgage m : arrlst) {
			if (m != null && noteRate[0] <= m.getNoteRate() && noteRate[1] >= m.getNoteRate()
					&& ficoScore[0] <= m.getCombinedFico() && ficoScore[1] >= m.getCombinedFico()
					&& dtiRange[0] <= m.getdti() && dtiRange[1] >= m.getdti() && ltvRange[0] <= m.getltv()
					&& ltvRange[1] >= m.getltv()) {
				validMortgages.add(m);
			}
		}
		return validMortgages;
	}

	public void printArrayList() {
		System.out.println(validMortgages);
		System.out.println(validMortgages.size());
		
	}
	
	
//	public int solveKnapsack() {
//		
//		int[][] results = new int[validMortgages.size() + 1][poolSize[1] + 1];
//		int index = 0;
//		
//		for (int row = 1; row < results.length; row++) {
//			
//			for (int col = 1; col < results[0].length; col++) {
//				
//				if (results[0].length - validMortgages.get(index).getUnpaidBalance() >= 0) {
//					results[row][col] = Math.max(results[row - 1][col], 
//							results[row - 1][(int) (results[0].length - validMortgages.get(index).getUnpaidBalance())] + 1);
//				}				
//			}
//			
//		}
//		return results[validMortgages.size()][poolSize[1] / 1000];
//		
//	}
	
	
	public int knapSack() {
        int i, w;
        int K[][] = new int[validMortgages.size() + 1][poolSize[0] / 1000 + 1];
 
        // Build table K[][] in bottom up manner
        for (i = 0; i <= validMortgages.size(); i++) {
            for (w = 0; w <= poolSize[0] / 1000; w++) {
                if (i == 0 || w == 0)
                    K[i][w] = 0;
                else if (validMortgages.get(i - 1).getUnpaidBalance() <= w)
                    K[i][w]
                        = Math.max(1 + K[i - 1][(int) 
                                                (w - validMortgages.get(i - 1).getUnpaidBalance())],
                              K[i - 1][w]);
                else
                    K[i][w] = K[i - 1][w];
            }
        }
 
        return K[validMortgages.size()][poolSize[0] / 1000 ];
    }
	
	
	public ArrayList<Mortgage> createKnapsack(ArrayList<Mortgage> mortgages) {
		
		findValues(mortgages);
		double totalUPB = 0;
		int numElements = 0;
		ArrayList<Mortgage> knapsack = new ArrayList<>();
		int[] possibleSolutions = new int[validMortgages.size()];
		
		for (int i = 0; i < validMortgages.size(); i++) {
			numElements = 0;
			ArrayList<Mortgage> elements = groupableMortgages(i);
			elements.sort(new Comparator<Mortgage>(){
				
				public int compare(Mortgage object1, Mortgage object2) {
					return (int) (object1.getUnpaidBalance()-object2.getUnpaidBalance());
				}
				
				
			});
			
			
			for (Mortgage m : elements) {
				if (totalUPB + m.getUnpaidBalance() <= poolSize[1]) {
					totalUPB += m.getUnpaidBalance();
					numElements++;
				}
			}
			
			possibleSolutions[i] = numElements;
			
		}
		
		int max = Integer.MIN_VALUE;
		totalUPB = 0;
		for (int i = 0; i < possibleSolutions.length; i++) {
			if (possibleSolutions[i] > max) {
				max = possibleSolutions[i];
				ArrayList<Mortgage> elts = groupableMortgages(i);
				
				for (Mortgage m : elts) {
					if (totalUPB + m.getUnpaidBalance() <= poolSize[1]) {
						totalUPB += m.getUnpaidBalance();
						knapsack.add(m);
						mortgages.remove(m);
					}
				}
			}
		}
		
		return knapsack;
		
		
	}

}
