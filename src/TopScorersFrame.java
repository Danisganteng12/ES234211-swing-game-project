import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TopScorersFrame extends JFrame {

    public TopScorersFrame() {
        PlayerService      ps         = new PlayerService();
        ArrayList<Player>  topPlayers = ps.getTopFiveScorers();

        setTitle("Top 5 Scorers");
        setSize(520, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ── Main panel ──────────────────────────────────────
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(240, 248, 255));

        // Judul
        JLabel lblTitle = new JLabel("Top 5 Scorers", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(184, 134, 11));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Tabel
        String[] columns = {"Rank", "Username", "Wins", "Losses", "Draws", "Score"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            // Buat tabel read-only
            public boolean isCellEditable(int row, int col) { return false; }
        };

        int rank = 1;
        for (Player p : topPlayers) {
            model.addRow(new Object[]{
                "#" + rank++,
                p.getUsername(),
                p.getWins(),
                p.getLosses(),
                p.getDraws(),
                p.getScore()
            });
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.setSelectionBackground(new Color(255, 220, 100));

        // Style header tabel
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(184, 134, 11));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);

        // Lebar kolom
        table.getColumnModel().getColumn(0).setPreferredWidth(45);
        table.getColumnModel().getColumn(1).setPreferredWidth(130);
        table.getColumnModel().getColumn(2).setPreferredWidth(60);
        table.getColumnModel().getColumn(3).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setPreferredWidth(60);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);

        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Tombol tutup
        JButton btnClose = new JButton("Tutup");
        btnClose.setFont(new Font("Arial", Font.BOLD, 13));
        btnClose.setBackground(new Color(184, 134, 11));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFocusPainted(false);
        btnClose.setBorderPainted(false);
        btnClose.setOpaque(true);
        btnClose.addActionListener(e -> dispose());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(new Color(240, 248, 255));
        btnPanel.add(btnClose);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);

    }
}
