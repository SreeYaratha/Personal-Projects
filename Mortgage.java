package knapsack;

public class Mortgage {

	private String loanId;
	private double unpaidBalance;
	private double noteRate;
	private int borrowerFico;
	private int coborrowerFico;
	private int combinedFico;
	private String state;
	private int dti;
	private int ltv;
	private String maturityDate;
	private int loanTerm;
	private String propertyType;

	// Constructor that takes in a String array and assigns the fields based on that
	public Mortgage(String[] arr) {

		// This constructor assigns the fields sequentially
		loanId = arr[0];
		unpaidBalance = Double.parseDouble(arr[1]);
		noteRate = Double.parseDouble(arr[2]);
		borrowerFico = Integer.parseInt(arr[3]);
		
		// check if arr[4] is an empty string
		if (arr[4] == "") {
			// set equal to -1
			// check in toString() if coborrower = -1
			coborrowerFico = -1;
		} else {
			coborrowerFico = Integer.parseInt(arr[4]);
		}
		
		// jump past indivual fico score to average because that is what is needed
		combinedFico = Integer.parseInt(arr[5]);
		state = arr[6];
		dti = Integer.parseInt(arr[7]);
		ltv = Integer.parseInt(arr[8]);
		maturityDate = arr[9];
		loanTerm = Integer.parseInt(arr[10]);
		propertyType = arr[11];

	}

	public String getloanId() {
		return loanId;
	}

	public double getUnpaidBalance() {
		return unpaidBalance;
	}

	public double getNoteRate() {
		return noteRate;
	}

	public int getCombinedFico() {
		return combinedFico;
	}

	public String getState() {
		return state;
	}

	public int getdti() {
		return dti;
	}

	public int getltv() {
		return ltv;
	}

	// *****
	public String maturityDate() {
		return maturityDate;
	}

	public int getLoanTerm() {
		return loanTerm;
	}

	public String propertyType() {
		return propertyType;
	}
	
	public String toString() {
		if(this.coborrowerFico == -1) {
			return loanId + "|" + unpaidBalance + "|" + noteRate + "|" + borrowerFico + "||" 
					+ combinedFico + "|" + state + "|" + dti + "|" + ltv + "|" + maturityDate +"|"
					+ loanTerm + "|" + propertyType;

		}else {
			return loanId + "|" + unpaidBalance + "|" + noteRate + "|" + borrowerFico + "|" + coborrowerFico + "|"
					+ combinedFico + "|" + state + "|" + dti + "|" + ltv + "|" + maturityDate +"|"
					+ loanTerm + "|" + propertyType;
		}
		
	}
	
	  public int compareTo(Object other){
	    if(!(other instanceof Mortgage)){
	      return -10;
	    }
	    Mortgage temp = (Mortgage) other;
	    if(this.getUnpaidBalance()>temp.getUnpaidBalance()){
	      return 1;
	    } else if(this.getUnpaidBalance()<temp.getUnpaidBalance()){
	      return -1;
	    }else{
	      return 0;
	    }
	  }

}
