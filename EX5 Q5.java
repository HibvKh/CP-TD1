package etud.sudoku;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class SudokuQ5GTSudoku {
    static int n = 9;
    static int s = 3;
    IntVar[][] rows, cols, shapes;
    Model model;

    public static void main(String[] args) {
        new SudokuQ5GTSudoku().solve();
    }

    public void solve() {
        buildModel();
        if (model.getSolver().solve()) {
            printGrid();
        }
        model.getSolver().printStatistics();
    }

    public void buildModel() {
        model = new Model("Greater Than Sudoku");
        rows = new IntVar[n][n];
        cols = new IntVar[n][n];
        shapes = new IntVar[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rows[i][j] = model.intVar("c_" + i + "_" + j, 1, n, false);
                cols[j][i] = rows[i][j];
            }
        }

        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                for (int k = 0; k < s; k++) {
                    for (int l = 0; l < s; l++) {
                        shapes[j + k * s][i + (l * s)] = rows[l + k * s][i + j * s];
                    }
                }
            }
        }

        // Standard Sudoku constraints
        for (int i = 0; i < n; i++) {
            model.allDifferent(rows[i]).post();
            model.allDifferent(cols[i]).post();
            model.allDifferent(shapes[i]).post();
        }

        // GT-Sudoku Inequality Constraints Example:
        // Add model.arithm(rows[r1][c1], ">", rows[r2][c2]).post(); 
        // or "<" based on the comparison symbols shown in Figure 3.
    }

    public void printGrid() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(rows[i][j].getValue() + " ");
            }
            System.out.println();
        }
    }
}