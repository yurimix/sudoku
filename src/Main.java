import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[][] sudoku = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 3, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 4, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 5, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 9}
        };
        printSudoku(sudoku);
        if (SudokuSolver.solve(sudoku) ) {
            printSudoku(sudoku);
        } else {
            System.err.println("Unresolvable sudoku!");
        }
    }

    private final static void printSudoku(int[][] sudoku) {
        System.out.println("-----------------------------");
        System.out.println(Arrays.deepToString(sudoku).
                replace("],", "],\n").
                replace("]", "}").
                replace("[", "{")
        );
    }
}