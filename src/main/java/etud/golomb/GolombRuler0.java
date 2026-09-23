package etud.golomb;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class GolombRuler0 {

    public static void main(String[] args) {



        int m = 8;
// A new model instance
        Model model = new Model("Golomb ruler");

// VARIABLES
// set of marks that should be put on the ruler
        IntVar[] ticks; //TODO
// CONSTRAINTS
//TODO


        // SOLVER
        Solver solver = model.getSolver();
        // Show resolution statistics
        solver.showShortStatistics();
        // Find a solution of the CSP version
        solver.solve();
        // Print detailed solver statistics
        solver.printStatistics();


    }


}