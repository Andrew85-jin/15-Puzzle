import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

//Этот класс не нужен

public class game extends JFrame {
    private final int size = 4;
    private JButton[][] button = new JButton[size][size];
    private int[][] board = new int[size][size];
    private int emptyRow, emptyCol;

    public void initGame() {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i <= 15; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        int index = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = numbers.get(index);
                if (board[row][col] == 0) {
                    emptyRow = row;
                    emptyCol = col;
                }
                index++;
            }
        }
    }

    public void updateButtons() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == 0) {
                    button[row][col].setText("");
                } else {
                    button[row][col].setText(Integer.toString(board[row][col]));
                }
            }
        }
    }

    public void handleButtonClick(int row, int col) {
        if ((row == emptyRow && Math.abs(col - emptyCol) == 1) ||
                (col == emptyCol && Math.abs(row - emptyRow) == 1)) {
            int temp = board[row][col];
            board[row][col] = board[emptyRow][emptyCol];
            board[emptyRow][emptyCol] = temp;

            emptyRow = row;
            emptyCol = col;
            updateButtons();

            if (checkWin()) {
                JOptionPane.showMessageDialog(this, "О це ти розумний, все зібрав, красава");
                int answer = JOptionPane.showConfirmDialog(this,
                        "Якщо ти такий розумний то може ще разок", "Нова гра",
                        JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    initGame();
                    updateButtons();
                } else {
                    System.exit(0);
                }
            }
        }
    }

    public boolean checkWin() {
        int expected = 1;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (row == size - 1 && col == size - 1) {
                    return board[row][col] == 0;
                }
                if (board[row][col] != expected) {
                    return false;
                }
                expected++;
            }
        }
        return true;
    }

    public void addListeners() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int r = row;
                int c = col;
                button[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleButtonClick(r, c);
                    }
                });
            }
        }
    }
}
