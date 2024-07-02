import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game();

        System.out.println("Bienvenue au jeu de Sudoku !");
        System.out.println("Choisissez le niveau de difficulté (1: Facile, 2: Moyen, 3: Difficile): ");
        int difficulty = scanner.nextInt();

        game.start(difficulty);
        scanner.close();
    }
}