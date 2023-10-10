import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[][] sudoku = {
            {0, 9, 0,   0, 5, 0,    8, 0, 0},
            {5, 0, 0,   0, 0, 4,    1, 0, 0},
            {7, 0, 4,   0, 0, 0,    0, 0, 0},

            {0, 1, 0,   0, 6, 0,    0, 0, 2},
            {0, 0, 0,   0, 4, 0,    0, 0, 7},
            {0, 7, 2,   0, 0, 9,    0, 0, 3},

            {0, 0, 0,   0, 0, 0,    0, 0, 0},
            {0, 5, 0,   3, 0, 0,    0, 0, 1},
            {0, 2, 0,   0, 9, 0,    6, 0, 0}
        };
        printSudoku(sudoku);
        if (SudokuSolver.solve(sudoku) ) {
            printSudoku(sudoku);
        } else {
            System.err.println("Unresolvable sudoku!");
        }
    }

    private static void printSudoku(int[][] sudoku) {
        System.out.println("-----------------------------");
        System.out.println(Arrays.deepToString(sudoku).
                replace("],", "],\n").
                replace("]", "}").
                replace("[", "{")
        );
        System.out.println("-----------------------------");
    }
}