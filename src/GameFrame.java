import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private Player        currentPlayer;
    private PlayerService playerService;
    private GameLogic     gameLogic;
    private JButton[]     buttons;
    private JLabel        lblStatus;
    private boolean       gameOver;

    public GameFrame(Player player) {
        this.currentPlayer = player;
        this.playerService = new PlayerService();
        this.gameLogic     = new GameLogic();
        this.gameOver      = false;

        setTitle("Game - " + currentPlayer.getUsername() + " (X) vs Computer (O)");
        setSize(430, 520);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ── Main panel ──────────────────────────────────────
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        mainPanel.setBackground(new Color(240, 248, 255));

        // Status label (atas)
        lblStatus = new JLabel("Giliran kamu! Klik sel untuk bermain.", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 14));
        lblStatus.setForeground(new Color(30, 90, 160));
        lblStatus.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        mainPanel.add(lblStatus, BorderLayout.NORTH);

        // Board 3x3
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 6, 6));
        boardPanel.setBackground(new Color(30, 90, 160));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 45));
            buttons[i].setBackground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorderPainted(false);
            buttons[i].setPreferredSize(new Dimension(110, 110));
            final int index = i;
            buttons[i].addActionListener(e -> handlePlayerMove(index));
            boardPanel.add(buttons[i]);
        }
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        // Tombol kembali (bawah)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(240, 248, 255));

        JButton btnBack = new JButton("Kembali ke Menu");
        btnBack.setFont(new Font("Arial", Font.PLAIN, 12));
        btnBack.setBackground(new Color(120, 120, 120));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setOpaque(true);
        btnBack.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Kembali ke menu? Game saat ini akan dibatalkan.",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                MainMenuFrame menuFrame = new MainMenuFrame(currentPlayer);
                menuFrame.setVisible(true);
                dispose();
            }
        });
        bottomPanel.add(btnBack);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // ── Logika saat player klik sel ─────────────────────────
    private void handlePlayerMove(int index) {
        if (gameOver) return;

        // Coba move player
        boolean moved = gameLogic.makeMove(index, 'X');
        if (!moved) return; // sel sudah terisi, abaikan

        // Tampilkan X di tombol
        buttons[index].setText("X");
        buttons[index].setForeground(new Color(30, 90, 160));
        buttons[index].setEnabled(false);

        // Cek player menang
        if (gameLogic.checkWinner('X')) {
            finishGame("WIN");
            return;
        }

        // Cek seri
        if (gameLogic.isDraw()) {
            finishGame("DRAW");
            return;
        }

        // Giliran komputer
        lblStatus.setText("Giliran komputer...");
        int compIndex = gameLogic.computerMove();
        if (compIndex != -1) {
            gameLogic.makeMove(compIndex, 'O');
            buttons[compIndex].setText("O");
            buttons[compIndex].setForeground(new Color(178, 34, 34));
            buttons[compIndex].setEnabled(false);
        }

        // Cek komputer menang
        if (gameLogic.checkWinner('O')) {
            finishGame("LOSE");
            return;
        }

        // Cek seri lagi setelah move komputer
        if (gameLogic.isDraw()) {
            finishGame("DRAW");
            return;
        }

        lblStatus.setText("Giliran kamu! Klik sel untuk bermain.");
    }

    // ── Selesaikan game dan update database ─────────────────
    private void finishGame(String result) {
        gameOver = true;

        // Disable semua tombol
        for (JButton btn : buttons) {
            btn.setEnabled(false);
        }

        String popupMsg;
        String statusMsg;
        Color  statusColor;

        if (result.equals("WIN")) {
            popupMsg   = "Selamat! Kamu MENANG! \n+10 poin ditambahkan!";
            statusMsg  = "Kamu Menang!";
            statusColor = new Color(46, 139, 87);
        } else if (result.equals("LOSE")) {
            popupMsg   = "Kamu KALAH!\n+0 poin.";
            statusMsg  = "Kamu Kalah!";
            statusColor = new Color(178, 34, 34);
        } else {
            popupMsg   = "SERI!\n+3 poin ditambahkan!";
            statusMsg  = "Seri!";
            statusColor = new Color(184, 134, 11);
        }

        lblStatus.setText(statusMsg);
        lblStatus.setForeground(statusColor);

        // Update statistik ke database
        playerService.updateStatistics(currentPlayer, result);

        // Tanya user: main lagi atau kembali ke menu?
        int choice = JOptionPane.showConfirmDialog(this,
            popupMsg + "\n\nMau main lagi?",
            "Game Selesai",
            JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            // Reset board untuk game baru
            gameLogic.resetBoard();
            gameOver = false;
            for (JButton btn : buttons) {
                btn.setText("");
                btn.setEnabled(true);
            }
            lblStatus.setText("Giliran kamu! Klik sel untuk bermain.");
            lblStatus.setForeground(new Color(30, 90, 160));
        } else {
            MainMenuFrame menuFrame = new MainMenuFrame(currentPlayer);
            menuFrame.setVisible(true);
            dispose();
        }

    }
}
