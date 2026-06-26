import java.util.Random;

public class GameLogic {
    private char[] board;

    public GameLogic() {
        board = new char[9];
        resetBoard();
    }

    // Reset semua sel agar isinya kosong
    public void resetBoard() {
        for (int i = 0; i < 9; i++) {
            board[i] = ' ';
        }
    }

    // Lakukan move jika sel kosong, return false jika invalid
    public boolean makeMove(int index, char symbol) {
        if (index < 0 || index >= 9) return false;
        if (board[index] != ' ') return false;
        board[index] = symbol;
        return true;
    }

    // Cek apakah simbol tertentu menang
    public boolean checkWinner(char symbol) {
        int[][] patterns = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // baris
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // kolom
            {0, 4, 8}, {2, 4, 6}              // diagonal
        };
        for (int[] p : patterns) {
            if (board[p[0]] == symbol &&
                board[p[1]] == symbol &&
                board[p[2]] == symbol) {
                return true;
            }
        }
        return false;
    }

    // Cek apakah seri (tidak ada sel kosong)
    public boolean isDraw() {
        for (char c : board) {
            if (c == ' ') return false;
        }
        return true;
    }

    // Giliran komputer: pilih sel kosong secara random
    public int computerMove() {
        Random rand = new Random();
        int index;
        int attempts = 0;
        do {
            index = rand.nextInt(9);
            attempts++;
            if (attempts > 100) return -1; // safety guard
        } while (board[index] != ' ');
        return index;
    }

    public char[] getBoard() {
        return board;
    }
}
