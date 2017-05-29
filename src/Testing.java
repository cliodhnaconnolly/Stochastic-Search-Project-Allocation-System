/* Encoka St. - Stochastic Search Project Allocation System */
import java.util.Map;

public class Testing {

//		public static void main(String[] args){
//			PreferenceTable prefs = new PreferenceTable("Project allocation data.tsv");
//
//			
//			/*	Preference Table Tests */
//			for (StudentEntry line : prefs.getAllStudentEntries()) { // For each line in the vector, print the line
//				line.display();
//			}
//			
//			prefs.getEntryFor("Alan Turing").display();	
//			prefs.getRandomStudent().display();
//			System.out.println(prefs.getRandomPreference());
//			System.out.println(prefs.getNumberPreAssignedProjects());
//			System.out.println(prefs.getMostPopularProject());
//			System.out.println(prefs.getProjectList());
//			System.out.println(prefs.getNumberOfProjects());
//			System.out.println(prefs.getNumberOfStudents() + "\n\n"); 
//			
//			/* Student Entry Tests */
//			StudentEntry test = prefs.getRandomStudent();
//			System.out.println("Student name: " +test.getStudentName());
//			System.out.println("Number of stated prefs" + test.getNumberOfStatedPreferences());
//			System.out.println("Preassigned :" +test.hasPreassignedProject());
//			System.out.println(test.getOrderedPreferences());
//			String pref_test = test.getRandomPreference();
//			System.out.println(pref_test);
//			System.out.println("Ranking of prev pref: " +test.getRanking(pref_test)  + "\n\n");
//			
//			StudentEntry test2 = prefs.getRandomStudent();
//			System.out.println("Student name: " +test2.getStudentName());
//			System.out.println("Number of stated prefs" + test2.getNumberOfStatedPreferences());
//			System.out.println("Preassigned :" +test2.hasPreassignedProject());
//			System.out.println(test2.getOrderedPreferences());
//			String pref_test2 = test2.getRandomPreference();
//			System.out.println(pref_test2);
//			System.out.println("Ranking of prev pref: " +test2.getRanking(pref_test2) + "\n\n");
//			
//			
//			
//			/*	Candidate Assignment and solution Tests */
//			
//			CandidateSolution sol = new CandidateSolution(prefs);
//			
//			for(Map.Entry<String, CandidateAssignment> candAssign : sol.getCandAssignList().entrySet() ) {
//				System.out.println(candAssign.getValue().getAssignedProject());
//			}
//
//			CandidateAssignment assignment = sol.getAssignmentFor("Monica Lewinsky"); 
//			System.out.println("Name: " + assignment.getStudentEntry().getStudentName());
//			System.out.println("Project: " + assignment.getAssignedProject());
//			assignment.setAssignedProject("NEW PROJECT");
//			System.out.println("Expected: NEW PROJECT\t Actual: " + assignment.getAssignedProject());
//			assignment.undoChange();
//			System.out.println("Project after undo change: " + assignment.getAssignedProject());
//			System.out.println("Energy: " + assignment.getEnergy()); 
//			
//			CandidateAssignment assignment2 = sol.getAssignmentFor("Adam West"); 
//			System.out.println("\nName: " + assignment2.getStudentEntry().getStudentName());
//			System.out.println("Project: " + assignment2.getAssignedProject());
//			assignment.setAssignedProject(assignment2.randomizeAssignment());
//			System.out.println("Project Randomly Assigned: " + assignment2.getAssignedProject());
//			assignment2.undoChange();
//			System.out.println("Project: " + assignment2.getAssignedProject());
//			System.out.println("Energy: " + assignment2.getEnergy()); 
//			
//			System.out.println("Random Assignment: " + sol.getRandomAssignment().getAssignedProject());
//					
//					
//			 /*Genetic Algorithm Tests*/
//			GeneticAlgorithm genAlg = new GeneticAlgorithm(prefs);
//			CandidateSolution solution = genAlg.generateBestSolution();
//			
//			System.out.println("\n\n\nGenetic Algorithm solution is " + solution.getEnergy());
//			
//			
//			/* SA Tests */
//			SimulatedAnnealingSolver tester = new SimulatedAnnealingSolver(prefs);
//			tester.generateBestSolution();
//		}
	}
