/* Encoka St. - Stochastic Search Project Allocation System */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

public class GeneticAlgorithm {
	
	//----------Properties---------- 

	private final int DOUBLE_POPULATION_SIZE = 1500;
	private CandidateSolutionComparator comparator = new CandidateSolutionComparator();
	public PreferenceTable pref;
	private Random rand = new Random();

	//----------Constructors----------

	public GeneticAlgorithm(PreferenceTable prefs) {	
		pref = prefs;
	}
	
	//-------------Methods-------------

	// Create an array list of 2xP random solutions 
	public ArrayList<CandidateSolution> createSolutions() {
		ArrayList<CandidateSolution> solutions = new ArrayList<CandidateSolution>();
		for ( int i = 0; i < DOUBLE_POPULATION_SIZE; i++ ) {
			CandidateSolution candSol = new CandidateSolution(pref);
			solutions.add(candSol);
		}
		return solutions;
	}
	
	// Cull all but the fittest P solutions
	public ArrayList<CandidateSolution> cull(ArrayList<CandidateSolution> parentList) {
		ArrayList<CandidateSolution> afterCull = new ArrayList<CandidateSolution>();
		for(int i=0; i<(DOUBLE_POPULATION_SIZE/2); i++){
			afterCull.add(parentList.get(i));
		}
		return afterCull;
	}
	
	private ArrayList<CandidateSolution> generateM(ArrayList<CandidateSolution> parentList){
		ArrayList<CandidateSolution> fittestM = new ArrayList<CandidateSolution>();
		if(parentList.size() < 10){
			fittestM.add(parentList.get(0));
		} else {
			for(int i=0; i<10; i++){
				fittestM.add(parentList.get(i));
			}
		}
		
		return fittestM;
	}
	
	private ArrayList<CandidateSolution> mateFittest(ArrayList<CandidateSolution> fittestM, ArrayList<CandidateSolution> parentList){
		ArrayList<CandidateSolution> offspring = new ArrayList<CandidateSolution>();
		
		// Mate fittest M amongst themselves and with lower members of population
		for(int i=0; i<fittestM.size(); i++){
			for(int j=0; j<parentList.size(); j++){
				offspring.add(combine(fittestM.get(i), parentList.get(j)));
			}
		}
		
		return offspring;
	}
	
	private CandidateSolution combine(CandidateSolution sol1, CandidateSolution sol2){
		CandidateSolution sol = new CandidateSolution();
		sol.setPrefs(pref);
		
		ArrayList<CandidateAssignment> candAssignList1 = new ArrayList<CandidateAssignment>(sol1.getCandidateAssignments());
		for ( int i = 0; i < candAssignList1.size()/2; i++ ) {
			String name = candAssignList1.get(i).getStudentEntry().getStudentName(); // key 
			CandidateAssignment toAdd = new CandidateAssignment(candAssignList1.get(i));
			sol.setCandAssignList(name, toAdd);
		}
		
		// Add other half candidate assignments from parent 2
		ArrayList<CandidateAssignment> candAssignList2 =  new ArrayList<CandidateAssignment>(sol2.getCandidateAssignments());
		for ( int i = candAssignList2.size()/2; i < candAssignList2.size(); i++ ) {
			StudentEntry student = candAssignList2.get(i).getStudentEntry();
			String name = student.getStudentName(); // key 
			CandidateAssignment toAdd = new CandidateAssignment(candAssignList2.get(i));
			sol.setCandAssignList(name, toAdd);
		}
		
		// 1 in 5 chance of probability
		if(rand.nextInt(5) == 2){
			sol.getRandomAssignment().randomizeAssignment();
		}
		
		return sol;
		

	}
	
	public CandidateSolution generateBestSolution() {
		ArrayList<CandidateSolution> parentList = createSolutions();
		ArrayList<CandidateSolution> fittestM;
		ArrayList<CandidateSolution> offspring; 
		ArrayList<Integer> fittestSolutions = new ArrayList<Integer>();
		
		int counter = 0;
		
		// Evalutate each solution for fitness
		// Order population of solutions by fitness
		parentList.sort(comparator);
		
		while(true){
			// Cull all but the fittest P solutions
			parentList = cull(parentList);
					
			// Select fittest M members of population for mating
			fittestM = generateM(parentList);			
			
			// Mate fittest M amongst themselves and with lower members of population
			offspring = mateFittest(fittestM, parentList);			
			
			// Generate N new offspring to add to population
			parentList.addAll(offspring);
			
			//Select fittest members of population F
			Collections.sort(parentList, comparator);
			
			// Checking if noticeable difference in last 10 iterations
			if(counter<10){
				fittestSolutions.add((counter%10), parentList.get(0).getFitness());
			} else{
				int difference = fittestSolutions.get(0) - parentList.get(0).getFitness();
				for(int i=1; i<fittestSolutions.size(); i++){
					int tempDifference = fittestSolutions.get(i) - parentList.get(0).getFitness();
					if(tempDifference < difference){
						difference = tempDifference;
					}
				}
				
				if(difference < 0){
					// YES, loop again
					fittestSolutions.set((counter%10), parentList.get(0).getFitness());
				} else {
					// NO, found fittest solution quit
					break;
				}
			}
			
			if(counter>=10){
				fittestSolutions.set((counter%10), parentList.get(0).getFitness());
			}

			counter++;
			
		}
		
		return parentList.get(0);
	}
		
	public int getDoublePopulationSize(){
		return DOUBLE_POPULATION_SIZE;
	}
}
