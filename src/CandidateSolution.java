/* Encoka St. - Stochastic Search Project Allocation System */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class CandidateSolution {
	
	//----------Properties---------- 
	
	private final int PENALTY = 1000;
	private HashMap<String, CandidateAssignment> candAssignList;
	private PreferenceTable prefs;
	
	//----------Constructors----------

	public CandidateSolution() {	
		candAssignList = new HashMap<String, CandidateAssignment>();
	}
	
	public CandidateSolution(PreferenceTable prefs) {
		
		this.prefs = prefs; 
		candAssignList = new HashMap<String, CandidateAssignment>(); 
		
		// Stores list of student entries
		Vector<StudentEntry> students = prefs.getAllStudentEntries();
		
		// Iterates through list of StudentEntry objects and creates a candidate
		// assignment list where students are assigned a project
		
		CandidateAssignment cand;
		for (StudentEntry studentEntry : students ) {
			cand = new CandidateAssignment(studentEntry);
			candAssignList.put(studentEntry.getStudentName(), cand);
		}
		fillPreferencesOfAll(10);
				
	}
	
	//----------Methods----------
	
	public CandidateAssignment getAssignmentFor(String name) {
		return candAssignList.get(name);
	}
	
	public CandidateAssignment getRandomAssignment() {
		StudentEntry studentEntry = prefs.getRandomStudent();		
		return getAssignmentFor(studentEntry.getStudentName());
	}	
	
	public HashMap<String, CandidateAssignment> getCandAssignList() {
		return candAssignList;
	}
		
	public int getEnergy(){
		int energy = 0;
		HashSet<String> projects = new HashSet<String>();
		
		// Calculate energy of a candidate solution by summing the energy of all the candidate assignment objects
		for(CandidateAssignment cand : candAssignList.values()){
			energy += cand.getEnergy(); 
			
			if(!projects.contains(cand.getAssignedProject())){
				projects.add(cand.getAssignedProject());
			} else { energy += PENALTY; }
					
		}
		return energy;
	}
	
	// Used to check a valid mapping was/not obtained
	public boolean noPenalties(){
		Vector<String> projectVector = new Vector<String>();
		for(CandidateAssignment cand : candAssignList.values()){

			if(!projectVector.contains(cand.getAssignedProject())){
				projectVector.add(cand.getAssignedProject());
			} else { return false; }
		}
		return true;
	}
	
	public int getFitness() {
		return  -getEnergy(); // inverse
	}
	
	public ArrayList<CandidateAssignment> getCandidateAssignments() {
		ArrayList<CandidateAssignment> candAssignments = new ArrayList<CandidateAssignment>();
		for (Map.Entry<String, CandidateAssignment> candAssign : candAssignList.entrySet() ) {
			candAssignments.add(candAssign.getValue());
		}
		return candAssignments;
	}
	
	
	public void fillPreferencesOfAll(int maxPrefs) {
		Random RND = new Random();
		
		// For each student entry in the array 
		for (Map.Entry<String, CandidateAssignment> candAssign : candAssignList.entrySet() ) {
			StudentEntry studentEntry = candAssign.getValue().getStudentEntry();

			// Checks if student has preassigned project
			if (studentEntry.hasPreassignedProject()) 
				continue;
			// Checks if student has max number of preferences
			if (studentEntry.getNumberOfStatedPreferences() == maxPrefs) {
				for(String preAssignedProject : prefs.getPreassignedProjects()){
					int index = studentEntry.getRanking(preAssignedProject);
					if(index != -1){
						studentEntry.removePreference(index);
					}
				}
			}
				//continue;
			// Gets how many preferences to fill up
			int numberToFill = maxPrefs - studentEntry.getNumberOfStatedPreferences();

			// Add random projects to fill up preference list
			int numberFilled = 0;
			while (numberToFill != numberFilled) {
				// StudentEntry student = prefs.getRandomStudent();
				int index = RND.nextInt(10);
				String preference = prefs.getProjectList().get(index);
				
				if ( ! studentEntry.hasPreference(preference) ) {
					studentEntry.addProject(preference);
					numberFilled++;
				}
				
			}	
		}
	}

	public void setPrefs(PreferenceTable prefs) {
		this.prefs = prefs;
	}
	
	public String results(){
		String toReturn = "";
		for (Map.Entry<String, CandidateAssignment> candAssign : candAssignList.entrySet() ) {
			toReturn += "\n" + candAssign.getValue().getStudentEntry().getStudentName() + "\t" + candAssign.getValue().getAssignedProject();
		}
		
		return toReturn;
	}
	
	public int getNumberOfFirstPrefs(){
		int firstPrefs = 0;
		for(CandidateAssignment cand : candAssignList.values()){
			if(cand.getEnergy() == 1){
				firstPrefs++;
			}
		}
		return firstPrefs;
	}
	
	public String getNumberOfPreferences(){
		String toReturn = "";
		
		int secondPrefs = 0;
		int thirdPrefs = 0;
		int fourthPrefs = 0;
		int fifthPrefs = 0;
		int sixthPrefs = 0;
		int seventhPrefs = 0;
		int eighthPrefs = 0;
		int ninthPrefs = 0;
		int tenthPrefs = 0;
		double energy = 0;
		
		for(CandidateAssignment cand : candAssignList.values()){
			energy = (double) cand.getEnergy();
			if(Math.sqrt(energy) == 2){
				secondPrefs++;
			}
			else if(Math.sqrt(energy) == 3){
				thirdPrefs++;
			}
			else if(Math.sqrt(energy) == 4){
				fourthPrefs++;
			}
			else if(Math.sqrt(energy) == 5){
				fifthPrefs++;
			}
			else if(Math.sqrt(energy) == 6){
				sixthPrefs++;
			}
			else if(Math.sqrt(energy) == 7){
				seventhPrefs++;
			}
			else if(Math.sqrt(energy) == 8){
				eighthPrefs++;
			} 
			else if(Math.sqrt(energy) == 9){
				ninthPrefs++;
			}
			else if(Math.sqrt(energy) == 10){
				tenthPrefs++;
			}
		}

		toReturn += "\r\nNumber of first preferences = " + getNumberOfFirstPrefs();
		toReturn += "\r\nNumber of second preferences = " + secondPrefs;
		toReturn += "\r\nNumber of third preferences = " + thirdPrefs;
		toReturn += "\r\nNumber of fourth preferences = " + fourthPrefs;
		toReturn += "\r\nNumber of fifth preferences = " + fifthPrefs;
		toReturn += "\r\nNumber of sixth preferences = " + sixthPrefs;
		toReturn += "\r\nNumber of seventh preferences = " + seventhPrefs;
		toReturn += "\r\nNumber of eighth preferences = " + eighthPrefs;
		toReturn += "\r\nNumber of ninth preferences = " + ninthPrefs;
		toReturn += "\r\nNumber of tenth preferences = " + tenthPrefs;
		
		return toReturn;
		
	}
	
	public String getStudentsWithOutPref(PreferenceTable pref){
		String toReturn = "Students with projects assigned they had not stated a preference for.";
		toReturn += "\n(Also includes students who had not selected 10 preferences)";
		for(CandidateAssignment cand : candAssignList.values()){
			// Testing if in original preferences
			if(pref.getEntryFor(cand.getStudentEntry().getStudentName()).getNumberOfStatedPreferences() < Math.sqrt(cand.getEnergy())){
				toReturn += "\n" + cand.getStudentEntry().getStudentName() + "\t" + cand.getAssignedProject();
			}
		}
		
		return toReturn;
		
	}

	public void setCandAssignList(String key, CandidateAssignment value){
		candAssignList.put(key, value);
	}
	
	public String studentPrefsAfterChecks(){
		String toReturn = "";
		
		for(CandidateAssignment cand : candAssignList.values()){
			toReturn += cand.getStudentEntry().stdToString();
		}
		return toReturn;
	}
}
