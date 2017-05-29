/* Encoka St. - Stochastic Search Project Allocation System */
import java.util.Random;


public class SimulatedAnnealingSolver {

	//----------Properties---------- 
	
	private PreferenceTable prefs;
	private boolean slowSA=false;
	
	//----------Constructors----------
	public SimulatedAnnealingSolver(PreferenceTable prefs) {
		this.prefs = prefs;
	}
	
	public SimulatedAnnealingSolver(PreferenceTable prefs, boolean slowSA) {
		this.prefs = prefs;
		this.slowSA = slowSA;
	}
	
	//-------------Methods-------------
	
	public CandidateSolution generateBestSolution(){
		Random rand = new Random();		
		CandidateSolution sol = new CandidateSolution(prefs);
		int initialEnergy = sol.getEnergy();
		double annealingSchedule = 0.0001;
		
		if(slowSA){
			annealingSchedule = 0.00003;
		}
				
		// Temperature = 100
		for(double j=100; j>0; j -= annealingSchedule){
			// We've to get the name so we can change back later if needed
			CandidateAssignment cand = sol.getRandomAssignment();
			cand.randomizeAssignment();
			
			int newEnergy = sol.getEnergy();
			int difference = initialEnergy - newEnergy;
			
			// Improvement of solution
			if(difference >= 0){
				// Accept current solution
				initialEnergy = newEnergy;
				//continue;
			}
			// Worsening of solution
			else if(difference < 0){
				double boltzmann = (Math.exp(difference/j));
				if(rand.nextDouble() <= boltzmann){
					initialEnergy = newEnergy;
					// Accept solution
					continue;
				} else { 
					cand.undoChange();
				}
			}
			
		}

		return sol;
	}
	
}
