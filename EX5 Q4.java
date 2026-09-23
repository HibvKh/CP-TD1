package etud.sudoku;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class SudokuQ4 {
    int n;
    int s;
    IntVar[][] rows, cols, shapes;
    Model model;

    public SudokuQ4(int size) {
        this.n = size;
        this.s = (int) Math.sqrt(n);
    }

    public static void main(String[] args) {
        // Pass 9 or 16 as command-line arguments, defaults to 9
        int size = (args.length > 0) ? Integer.parseInt(args[0]) : 9;
        new SudokuQ4(size).solve();
    }

    public void solve() {
        buildModel();
        int count = 1;
        while (model.getSolver().solve()) {
            System.out.println("Solution #" + count++);
            printGrid();
            System.out.println("-----------------------------------");
        }
        model.getSolver().printStatistics();
    }

    public void buildModel() {
        model = new Model("Sudoku Size " + n + " x " + n);
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

        for (int i = 0; i < n; i++) {
            model.allDifferent(rows[i]).post();
            model.allDifferent(cols[i]).post();
            model.allDifferent(shapes[i]).post();
        }
    }

    public void printGrid() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(rows[i][j].getValue() + "\t");
            }
            System.out.println();
        }
    }
}