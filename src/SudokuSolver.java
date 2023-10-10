import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SudokuSolver {

    private static final int MATRIX_SECTION_SIZE = 3;
    private static final int MATRIX_ROWS = MATRIX_SECTION_SIZE * MATRIX_SECTION_SIZE;
    private static final int MATRIX_COLS = MATRIX_ROWS;
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = MATRIX_COLS;
    private static final int EMPTY_VALUE = 0;

    private record Section(int start, int end){}

    public static boolean solve(int[][] sudoku) {
        if (sudoku.length != MATRIX_ROWS || sudoku[0].length != MATRIX_COLS) {
            throw new IllegalArgumentException("Sudoku matrix must be " + MATRIX_ROWS + "x" + MATRIX_COLS);
        }
        if (!checkSudoku(sudoku)) {
            throw new IllegalArgumentException("Input sudoku matrix contains unresolvable data");
        }
        return solveSudoku(sudoku);
    }

    private static boolean checkSudoku(int[][] sudoku) {
        for (int row = 0; row < MATRIX_ROWS; row++) {
            for (int col = 0; col < MATRIX_COLS; col++) {
                if (sudoku[row][col] != EMPTY_VALUE) {
                    if (!checkCell(sudoku, row, col)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean solveSudoku(int[][] sudoku) {
        for (int row = 0; row < MATRIX_ROWS; row++) {
            for (int col = 0; col < MATRIX_COLS; col++) {
                if (sudoku[row][col] == EMPTY_VALUE) {
                    // it's just more fun this way :)
                    var values = IntStream.range(MIN_VALUE, MAX_VALUE + 1).
                            boxed().collect(Collectors.toList());
                    Collections.shuffle(values);
                    // for each possible values
                    for (var val : values) {
                        // try to set value into sudoku board cell
                        sudoku[row][col] = val;
                        if (checkCell(sudoku, row, col) && solveSudoku(sudoku)) {
                            // value accepted
                            return true;
                        }
                        // reset sudoku board cell
                        sudoku[row][col] = EMPTY_VALUE;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkCell(int[][] sudoku, int row, int col) {
        return checkRow(sudoku, row, col) && checkColumn(sudoku, row, col) && checkSection(sudoku, row, col);
    }
    
    private static boolean checkRow(int[][] sudoku, int row, int col) {
        return checkDuplicates(sudoku[row], sudoku[row][col]);
    }

    private static boolean checkColumn(int[][] sudoku, int row, int col) {
        var data = new int[MATRIX_COLS];
        for (int r = 0; r < MATRIX_ROWS; r++) {
            data[r] = sudoku[r][col];
        }
        return checkDuplicates(data, sudoku[row][col]);
    }

    private static boolean checkSection(int[][] sudoku, int row, int col) {
        var rIdx = sectionIndexes(row);
        var cIdx = sectionIndexes(col);
        var data = new int[MATRIX_SECTION_SIZE * MATRIX_SECTION_SIZE];
        int i = 0;
        for (int r = rIdx.start; r <= rIdx.end; r++) {
            for (int c = cIdx.start; c <= cIdx.end; c++) {
                data[i++] = sudoku[r][c];
            }
        }
        return checkDuplicates(data, sudoku[row][col]);
    }

    private static boolean checkDuplicates(int[] data, int val) {
        return IntStream.of(data).filter(i -> i == val).count() < 2;
    }

    private static Section sectionIndexes(int idx) {
        int start =  (idx / MATRIX_SECTION_SIZE) * MATRIX_SECTION_SIZE;
        return new Section(start, start + MATRIX_SECTION_SIZE - 1);
    }
}
