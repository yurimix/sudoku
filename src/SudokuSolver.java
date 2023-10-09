import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SudokuSolver {

    private static final int MATRIX_SECTION_SIZE = 3;

    private static final int MATRIX_ROWS = MATRIX_SECTION_SIZE * MATRIX_SECTION_SIZE;
    private static final int MATRIX_COLS = MATRIX_ROWS;

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = MATRIX_COLS;
    private static final int EMPTY_VALUE = 0;

    private static class Section {
        int start;
        int end;

        public Section(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

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
        for (var row = 0; row < MATRIX_ROWS; row++) {
            for (var col = 0; col < MATRIX_COLS; col++) {
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
                    // it's just more fun this way
                    List<Integer> values = IntStream.range(1, MAX_VALUE + 1).
                            mapToObj(Integer::valueOf).
                            collect(Collectors.toList());
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
        int[] data = new int[MATRIX_COLS];
        for (int r = 0; r < MATRIX_ROWS; r++) {
            data[r] = sudoku[r][col];
        }
        return checkDuplicates(data, sudoku[row][col]);
    }

    private static boolean checkSection(int[][] sudoku, int row, int col) {
        var rowIndexes = sectionIndices(row);
        var colIndexes = sectionIndices(col);
        int[] data = new int[MATRIX_SECTION_SIZE * MATRIX_SECTION_SIZE];
        var i = 0;
        for (var r = rowIndexes.start; r <= rowIndexes.end; r++) {
            for (var c = colIndexes.start; c <= colIndexes.end; c++) {
                data[i++] = sudoku[r][c];
            }
        }
        return checkDuplicates(data, sudoku[row][col]);
    }

    private static boolean checkDuplicates(int[] data, int val) {
        return IntStream.of(data).filter(i -> i == val).count() < 2;
    }

    private static Section sectionIndices(int idx) {
        var start =  (idx / MATRIX_SECTION_SIZE) * MATRIX_SECTION_SIZE;
        return new Section(start, start + MATRIX_SECTION_SIZE - 1);
    }
}
