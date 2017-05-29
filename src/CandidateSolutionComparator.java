/* Encoka St. - Stochastic Search Project Allocation System */
import java.util.Comparator;

class CandidateSolutionComparator implements Comparator<CandidateSolution> {
	public int compare(CandidateSolution object1, CandidateSolution object2) {      
		int obj1Fitness = object1.getFitness();
		int obj2Fitness = object2.getFitness();
		if (obj1Fitness < obj2Fitness) 
			return 1;
		else if (obj1Fitness > obj2Fitness)
			return -1;
		else
			return 0;
	}
}
