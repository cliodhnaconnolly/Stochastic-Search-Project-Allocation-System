/* Encoka St. - Stochastic Search Project Allocation System */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

public class PreferenceTable {

	// --------- Properties --------
	
	private Hashtable<String, StudentEntry> studentLookup;
	private Random RND = new Random(); 
	public Vector<String> projectList = new Vector<String>(); 
	private Hashtable<String, Integer> projectPopularity = new Hashtable<String, Integer>();
	private HashSet<String> preassignedProject = new HashSet<String>();
	private int numberOfPreAssignedProjects = 0;
	
	
	// -------- Constructors -------
	
	public PreferenceTable() {
		studentLookup = new Hashtable<String, StudentEntry>();
	}  
	
	public PreferenceTable(String fileName) {
		studentLookup = new Hashtable<String, StudentEntry>();
		loadContentFromFile(fileName);	
	}
	
	// --------- Methods ----------
	
	private void loadContentFromFile(String fileName) {
		FileInputStream stream;
		try {
			stream = new FileInputStream(fileName);
			BufferedReader input = new BufferedReader(new InputStreamReader(stream)); 
			String line; 
			String project;

			input.readLine(); // ignore header (first row) 
			while ( (line = input.readLine()) != null) {
				StringTokenizer tokens = new StringTokenizer(line, "\t");
				StudentEntry studentEntry = new StudentEntry(tokens.nextToken()); 
				String prearranged = tokens.nextToken(); 
				if ( prearranged.equals("Yes")) { // If project is prearranged
					project = tokens.nextToken();
					studentEntry.preassignProject(project); 
					preassignedProject.add(project);
					numberOfPreAssignedProjects++;
				}
				else {  // If project is NOT prearranged
					String preference;
					while (tokens.hasMoreTokens()) {
						// Ensure that a student is not allowed to have several of the same preference
						preference = tokens.nextToken();
						if ( !studentEntry.hasPreference(preference) ) {
							studentEntry.addProject(preference);
						}
						if ( !projectList.contains(preference)){
							projectList.addElement(preference);
							projectPopularity.put(preference, 1);
						} else {
							int index = projectPopularity.get(preference);
							projectPopularity.replace(preference, index+1);
						}
							
					}
				}
				studentLookup.put(studentEntry.getStudentName(), studentEntry);
			}	
			stream.close(); // close FileInputStream
			input.close(); // close BufferReader
		} catch (IOException e) {
			// Print error message
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Vector<StudentEntry> getAllStudentEntries() {
		Vector<StudentEntry> students = new Vector<StudentEntry>();
		
		// Iterates through hashmap and creates a list of StudentEntry objects
		// by mapping String names to instances of StudentEntry
		for (Map.Entry<String, StudentEntry> student : studentLookup.entrySet() ) {
			students.add(student.getValue());
		}
		return students;
	}
	
	public StudentEntry getEntryFor(String sname) {
		return studentLookup.get(sname);
	}
	
	public StudentEntry getRandomStudent() {
		int index = RND.nextInt(studentLookup.size());
		
		// Get random student entry object using randomly generated index
		StudentEntry studentEntry = getAllStudentEntries().get(index);
	
		return studentEntry;
	}
	
	public String getRandomPreference() {
		StudentEntry studentEntry = getRandomStudent();
		String preference = studentEntry.getRandomPreference();
		return preference;
	}
		
	// Prints out entry of each student
	public void print() {
		for (Map.Entry<String, StudentEntry> student : studentLookup.entrySet() ) {
			StudentEntry studentEntry = student.getValue();
			studentEntry.display();
		}
	}	

	public int getNumberPreAssignedProjects(){
		return numberOfPreAssignedProjects;
	}
	
	public String getMostPopularProject(){
		String mostPopular = "";
		int currentMostPopular=0;
		
		Set<String> keys = projectPopularity.keySet();
		for(String key : keys){
			if(projectPopularity.get(key) >= currentMostPopular){
				currentMostPopular = projectPopularity.get(key);
				mostPopular = key;
			}
		}
		
		return mostPopular;
	}
	
	public HashSet<String> getPreassignedProjects(){
		return preassignedProject;
	}
	
	public Vector<String> getProjectList(){
		return projectList;
	}
	
	public int getNumberOfProjects(){
		return projectList.size();
	}
	
	public int getNumberOfStudents(){
		return studentLookup.size();
	}
	
}
