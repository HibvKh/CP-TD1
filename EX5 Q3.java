package etud.sudoku;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class SudokuQ3 {
    static int n = 9;
    static int s = 3;
    IntVar[][] rows, cols, shapes;
    Model model;

    // Initial grid instance from Figure 1 (0 represents empty cells)
    int[][] initialGrid = {
        {8, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 3, 6, 0, 0, 0, 0, 0, 0},
        {0, 7, 0, 9, 0, 2, 0, 0, 0},
        {0, 5, 0, 0, 7, 0, 0, 0, 0},
        {0, 0, 0, 4, 5, 7, 0, 0, 0},
        {0, 0, 1, 0, 0, 3, 0, 0, 0},
        {0, 1, 0, 0, 0, 6, 8, 0, 0},
        {0, 8, 5, 0, 0, 1, 0, 0, 0},
        {9, 0, 0, 0, 4, 0, 0, 0, 0}
    };

    public static void main(String[] args) {
        new SudokuQ3().solve();
    }

    public void solve() {
        buildModel();
        if (model.getSolver().solve()) {
            printGrid();
        }
        model.getSolver().printStatistics();
    }

    public void buildModel() {
        model = new Model("Sudoku Q3 - Figure 1");
        rows = new IntVar[n][n];
        cols = new IntVar[n][n];
        shapes = new IntVar[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int val = initialGrid[i][j];
                if (val != 0) {
                    rows[i][j] = model.intVar("c_" + i + "_" + j, val, val, false);
                } else {
                    rows[i][j] = model.intVar("c_" + i + "_" + j, 1, n, false);
                }
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

        for (int i = 0; i < n; i++) {
            model.allDifferent(rows[i]).post();
            model.allDifferent(cols[i]).post();
            model.allDifferent(shapes[i]).post();
        }
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