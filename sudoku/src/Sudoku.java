import java.util.Random;

public class Sudoku {
    private int[][] board;
    private static final int SIZE = 9;
    private static final int SUBGRID_SIZE = 3;

    public Sudoku() {
        board = new int[SIZE][SIZE];
    }

    public int[][] getBoard() {
        return board;
    }

    public void generateBoard(int difficulty) {
        fillDiagonalBoxes();
        fillRemainingCells(0, SUBGRID_SIZE);
        removeNumbers(difficulty);
    }

    private void fillDiagonalBoxes() {
        for (int i = 0; i < SIZE; i += SUBGRID_SIZE) {
            fillBox(i, i);
        }
    }

    private void fillBox(int row, int col) {
        Random random = new Random();
        int[] nums = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            nums[i] = i + 1;
        }
        shuffleArray(nums);

        for (int i = 0; i < SUBGRID_SIZE; i++) {
            for (int j = 0; j < SUBGRID_SIZE; j++) {
                board[row + i][col + j] = nums[i * SUBGRID_SIZE + j];
            }
        }
    }

    private void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private boolean fillRemainingCells(int i, int j) {
        if (j >= SIZE && i < SIZE - 1) {
            i++;
            j = 0;
        }
        if (i >= SIZE && j >= SIZE) {
            return true;
        }
        if (i < SUBGRID_SIZE) {
            if (j < SUBGRID_SIZE) {
                j = SUBGRID_SIZE;
            }
        } else if (i < SIZE - SUBGRID_SIZE) {
            if (j == (i / SUBGRID_SIZE) * SUBGRID_SIZE) {
                j += SUBGRID_SIZE;
            }
        } else {
            if (j == SIZE - SUBGRID_SIZE) {
                i++;
                j = 0;
                if (i >= SIZE) {
                    return true;
                }
            }
        }
        for (int num = 1; num <= SIZE; num++) {
            if (isSafeToPlace(i, j, num)) {
                board[i][j] = num;
                if (fillRemainingCells(i, j + 1)) {
                    return true;
                }
                board[i][j] = 0;
            }
        }
        return false;
    }

    public boolean isSafeToPlace(int row, int col, int num) {
        return !isInRow(row, num) && !isInCol(col, num) && !isInBox(row - row % SUBGRID_SIZE, col - col % SUBGRID_SIZE, num);
    }

    private boolean isInRow(int row, int num) {
        for (int col = 0; col < SIZE; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInCol(int col, int num) {
        for (int row = 0; row < SIZE; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInBox(int startRow, int startCol, int num) {
        for (int row = 0; row < SUBGRID_SIZE; row++) {
            for (int col = 0; col < SUBGRID_SIZE; col++) {
                if (board[row + startRow][col + startCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    private void removeNumbers(int difficulty) {
        int cellsToRemove = difficulty == 1 ? 20 : (difficulty == 2 ? 40 : 60);
        Random random = new Random();
        while (cellsToRemove > 0) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (board[row][col] != 0) {
                board[row][col] = 0;
                cellsToRemove--;
            }
        }
    }

    public boolean solve() {
        return solveSudoku(0, 0);
    }

    private boolean solveSudoku(int row, int col) {
        if (row == SIZE - 1 && col == SIZE) {
            return true;
        }
        if (col == SIZE) {
            row++;
            col = 0;
        }
        if (board[row][col] != 0) {
            return solveSudoku(row, col + 1);
        }
        for (int num = 1; num <= SIZE; num++) {
            if (isSafeToPlace(row, col, num)) {
                board[row][col] = num;
                if (solveSudoku(row, col + 1)) {
                    return true;
                }
                board[row][col] = 0;
            }
        }
        return false;
    }
}
