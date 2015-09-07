package uppgift.a;

public class Employee{
	private String shortName;
	private int shortNumber;
	
	Employee (String shortName, int shortNumber){
		setShortName(shortName);
		setShortNumber(shortNumber);
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public int getShortNumber() {
		return shortNumber;
	}

	public void setShortNumber(int shortNumber) {
		this.shortNumber = shortNumber;
	}
}
