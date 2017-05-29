/* Encoka St. - Stochastic Search Project Allocation System */
public class CandidateAssignment {
	
	//----------Properties---------- 
	
	private StudentEntry studentEntry;
	private String project;
	private String prevProject; // stores previous project assigned 
		
	//----------Constructors----------

	public CandidateAssignment(StudentEntry studentEntryPar) {
		project = "";
		prevProject = null;
		studentEntry = studentEntryPar;
		
		// If student has a preassigned project 
		project = studentEntry.getPreassignedProject();
		
		// Else select a random project
		if (project == null) {
			randomizeAssignment();
		}
	}
	
	//----------Methods----------
	
	public CandidateAssignment(CandidateAssignment candidateAssignment) {
		studentEntry = candidateAssignment.getStudentEntry();
		project = candidateAssignment.getAssignedProject();
		prevProject = null;
	}

	public String randomizeAssignment() {
		prevProject = project; 
		project = studentEntry.getRandomPreference();
		return project;
	}

	public void undoChange() {
		if (prevProject != null) {
			String temp = project;
			project = prevProject;
			prevProject = temp;
		}
	}

	public StudentEntry getStudentEntry() {
		return studentEntry;
	}

	public String getAssignedProject() {
		return project;
	}
	
	public void setAssignedProject(String assignedProject) {
		prevProject = project; // stores previous project
		project = assignedProject;
	}
	
	public int getEnergy() {
		int ranking = studentEntry.getRanking(project) + 1;
		return ranking * ranking;	
	}
}
