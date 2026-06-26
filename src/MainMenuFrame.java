import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    private Player  currentPlayer;
    private JButton btnStartGame;
    private JButton btnStatistics;
    private JButton btnTopScorers;
    private JButton btnExit;

    public MainMenuFrame(Player player) {
        this.currentPlayer = player;

        setTitle("Main Menu - " + currentPlayer.getUsername());
        setSize(380, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ── Main panel ──────────────────────────────────────
        JPanel mainPanel = new JPanel(new BorderLayout(10, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 35, 20, 35));
        mainPanel.setBackground(new Color(240, 248, 255));

        // Header
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        headerPanel.setBackground(new Color(240, 248, 255));

        JLabel lblTitle = new JLabel("TIC-TAC-TOE", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitle.setForeground(new Color(30, 90, 160));

        JLabel lblInfo = new JLabel(
            "Halo, " + currentPlayer.getUsername() +
            "!  |  Score: " + currentPlayer.getScore(),
            SwingConstants.CENTER);
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblInfo.setForeground(new Color(80, 80, 80));

        headerPanel.add(lblTitle);
        headerPanel.add(lblInfo);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Tombol menu
        JPanel btnPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        btnPanel.setBackground(new Color(240, 248, 255));

        btnStartGame  = createMenuButton("Start Game",    new Color(46,  139, 87));
        btnStatistics = createMenuButton("My Statistics", new Color(70,  130, 180));
        btnTopScorers = createMenuButton("Top 5 Scorers", new Color(184, 134, 11));
        btnExit       = createMenuButton("Exit",          new Color(178,  34,  34));

        btnPanel.add(btnStartGame);
        btnPanel.add(btnStatistics);
        btnPanel.add(btnTopScorers);
        btnPanel.add(btnExit);
        mainPanel.add(btnPanel, BorderLayout.CENTER);

        add(mainPanel);

        // ── Event handling ───────────────────────────────────
        btnStartGame.addActionListener(e -> {
            GameFrame gameFrame = new GameFrame(currentPlayer);
            gameFrame.setVisible(true);
            dispose();
        });

        btnStatistics.addActionListener(e -> {
            StatisticsFrame statsFrame = new StatisticsFrame(currentPlayer);
            statsFrame.setVisible(true);
        });

        btnTopScorers.addActionListener(e -> {
            TopScorersFrame topFrame = new TopScorersFrame();
            topFrame.setVisible(true);
        });

        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Yakin mau keluar?", "Exit",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private JButton createMenuButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(0, 45));
        return btn;
    }

}
