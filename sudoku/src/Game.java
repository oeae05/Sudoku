import java.util.Scanner;

public class Game {
    private Sudoku sudoku;
    private int[][] initialBoard;
    private int errorCount;
    private static final int MAX_ERRORS = 3;

    public Game() {
        sudoku = new Sudoku();
        errorCount = 0;
    }

    public void start(int difficulty) {
        sudoku.generateBoard(difficulty);
        initialBoard = copyBoard(sudoku.getBoard());
        play();
    }

    private void play() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard(sudoku.getBoard());
            System.out.println("Entrez votre mouvement (ligne colonne nombre) ou 'solve' pour résoudre :");
            String input = scanner.nextLine();
            if (input.equals("solve")) {
                if (sudoku.solve()) {
                    System.out.println("Solution trouvée !");
                    printBoard(sudoku.getBoard());
                } else {
                    System.out.println("Pas de solution possible.");
                }
                break;
            }
            String[] parts = input.split(" ");
            if (parts.length != 3) {
                System.out.println("Entrée invalide. Essayez à nouveau.");
                continue;
            }
            int row = Integer.parseInt(parts[0]) - 1;
            int col = Integer.parseInt(parts[1]) - 1;
            int num = Integer.parseInt(parts[2]);

            if (initialBoard[row][col] != 0) {
                System.out.println("Vous ne pouvez pas modifier cette cellule.");
                continue;
            }

            if (sudoku.isSafeToPlace(row, col, num)) {
                sudoku.getBoard()[row][col] = num;
            } else {
                errorCount++;
                System.out.println("Mouvement incorrect. Erreurs: " + errorCount);
                if (errorCount >= MAX_ERRORS) {
                    System.out.println("Trop d'erreurs. Partie terminée.");
                    break;
                }
            }

            if (isBoardFull(sudoku.getBoard())) {
                System.out.println("Félicitations ! Vous avez complété le Sudoku.");
                break;
            }
        }
        scanner.close();
    }

    private boolean isBoardFull(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("-------------------");
            }
            for (int j = 0; j < board[i].length; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("|");
                }
                if (board[i][j] == 0) {
                    System.out.print("_");
                } else {
                    System.out.print(board[i][j]);
                }
                if (j < board[i].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private int[][] copyBoard(int[][] board) {
        int[][] copy = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }
}