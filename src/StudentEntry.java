/* Encoka St. - Stochastic Search Project Allocation System */
import java.util.Random;
import java.util.Vector;

public class StudentEntry {
	
	//----------Properties---------- 
	
	private String studentName;
	private boolean preassignedProject; // flag 
	private Vector<String> preferences; 
	private Random RND = new Random();
	
	//----------Constructors----------
	
	public StudentEntry() {
		studentName = ""; 
		preassignedProject = false;
		preferences = new Vector<String>();
	}
	
	public StudentEntry(String studentName) {
		this.studentName = studentName;
		preassignedProject = false;
		preferences = new Vector<String>();
	}
	
	//----------Methods----------
	
	public String getStudentName() {
		 return studentName;
	}
	
	public void setStudentName(String sName) {
		studentName = sName;
	}

	public Vector<String> getOrderedPreferences() {
		return preferences;
	}
	
	public void setOrderedPreferences(Vector<String> preferences) {
		this.preferences = preferences;
	}
	
	// Ensures each student has only one preference if prearranged
	public void preassignProject(String pname) {
		preassignedProject = true; 
		preferences.clear();
		preferences.add(pname);
	}
	
	public boolean hasPreassignedProject() {
		return preassignedProject;
	}
	
	public String getPreassignedProject() {
		if ( hasPreassignedProject() ) {
			return preferences.get(0);
		}
		return null;
	}
	
	public int getNumberOfStatedPreferences() {
		return preferences.size(); 
	}
		
	public void addProject(String pname) {
		if ( ! hasPreassignedProject() ) {
			preferences.add(pname); // stores project once in memory 
		}
	}
	
	
	
	public String getRandomPreference() {
		if ( hasPreassignedProject() ) 
			return getOrderedPreferences().get(0) ; // Preassigned project stored at 0
		int index = RND.nextInt(preferences.size());
		return getOrderedPreferences().get(index);
	}
	
	public boolean hasPreference(String preference) {
		return preferences.contains(preference);
	}
	
	public int getRanking(String preference) {
		return preferences.indexOf(preference);
	}
	
	public void removePreference(int index) {
		preferences.removeElementAt(index);
	}

	// Prints out student name, whether project is prearranged or not and their project preference(s)
	public void display() {
		System.out.println("\nStudent Name: " + this.studentName);
		if (hasPreassignedProject()) { // if student has prearranged project
			System.out.println("Prearranged: Yes");
			System.out.println("Pre-Assigned Project Name: " + preferences.get(0)); // prints out prearranged project
		}
		else { // if student does not have prearranged project
			System.out.println("Prearranged: No");
			System.out.println("Preferences: ");	
			// For each preference 
			for (String preference : preferences) {
				System.out.println("\t" + preference);
			}
		}
	}
	
	public String stdToString() {
		String toReturn = "";
		toReturn += "\r\nStudent Name: " + studentName;
		if (hasPreassignedProject()) { // if student has prearranged project
			toReturn += "\tPrearranged: Yes";
			toReturn += "\tPre-Assigned Project Name: " + preferences.get(0); // prints out prearranged project
		}
		else { // if student does not have prearranged project
			toReturn += "\tPrearranged: No";
			toReturn += "\tPreferences: ";	
			// For each preference 
			for (String preference : preferences) {
				toReturn += "\t" + preference;
			}
		}
		
		return toReturn;
	}
}
