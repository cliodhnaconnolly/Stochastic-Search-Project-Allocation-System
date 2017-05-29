/* Encoka St. - Stochastic Search Project Allocation System */
import javax.swing.SwingWorker;

public class ProjectSolver extends SwingWorker<CandidateSolution, Integer> {

	private int timesToRunSA;
	private int timesToRunGA;
	private String filename;
	private CandidateSolution bestSol;
	private PreferenceTable prefs;
	private boolean slowSA;
	
	public ProjectSolver(int timesToRunSA, int timesToRunGA, String filename, boolean slowSA){
		this.timesToRunGA = timesToRunGA;
		this.timesToRunSA = timesToRunSA;
		this.filename = filename;
		this.slowSA = slowSA;
	}

	protected CandidateSolution doInBackground() {
		try{
			CandidateSolution temp;
			SimulatedAnnealingSolver sim;
			prefs = new PreferenceTable(filename);
			
			// Running simulated Annealing
			setProgress(0);
			if(slowSA){
				sim = new SimulatedAnnealingSolver(prefs, slowSA);
			} else {sim = new SimulatedAnnealingSolver(prefs);}
			
			CandidateSolution solutionSA = sim.generateBestSolution();
			
			setProgress(getProgress() + ((Integer) 50/timesToRunSA));
			for(int i=1; i<timesToRunSA; i++){
				temp = sim.generateBestSolution();
				if(temp.getEnergy() < solutionSA.getEnergy()){ solutionSA = temp; }
				setProgress(getProgress() + (Integer) 50/timesToRunSA);
			}
			
			// Running genetic algortihm
			setProgress(50);
			GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(prefs);
			CandidateSolution solutionGA = geneticAlgorithm.generateBestSolution();
			setProgress(getProgress() + (Integer) 50/timesToRunGA);
			
			for(int i=1; i<timesToRunGA; i++){
				temp = geneticAlgorithm.generateBestSolution();
				if(temp.getEnergy() < solutionGA.getEnergy()){
					solutionGA = temp;
				}
				setProgress(getProgress() + (Integer) 50/timesToRunGA);
			}
			
			// Evaluate better solution
			if(solutionGA.getEnergy() > solutionSA.getEnergy()){ bestSol = solutionSA; }
			else { bestSol = solutionGA; }
			setProgress(100);
			
			return bestSol;
		} catch (Exception e) { e.printStackTrace(); }
		return bestSol;
	}
	
	public PreferenceTable getPrefs(){
		return prefs;
	}
	
}
