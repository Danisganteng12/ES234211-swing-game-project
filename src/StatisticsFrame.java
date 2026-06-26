import javax.swing.*;
import java.awt.*;

public class StatisticsFrame extends JFrame {

    public StatisticsFrame(Player player) {
        // Ambil data terbaru dari database (bukan dari objek lama)
        PlayerService ps = new PlayerService();
        Player updated = ps.getPlayerById(player.getId());
        if (updated == null) updated = player; // fallback jika gagal koneksi

        setTitle("My Statistics - " + updated.getUsername());
        setSize(340, 330);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ── Main panel ──────────────────────────────────────
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(240, 248, 255));

        // Judul
        JLabel lblTitle = new JLabel("My Statistics", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(70, 130, 180));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Stats
        JPanel statsPanel = new JPanel(new GridLayout(5, 2, 10, 14));
        statsPanel.setBackground(new Color(240, 248, 255));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        addRow(statsPanel, "Username :",    updated.getUsername(),              new Color(50,  50,  50));
        addRow(statsPanel, "Wins :",        String.valueOf(updated.getWins()),  new Color(46,  139, 87));
        addRow(statsPanel, "Losses :",      String.valueOf(updated.getLosses()),new Color(178,  34, 34));
        addRow(statsPanel, "Draws :",       String.valueOf(updated.getDraws()), new Color(184, 134, 11));
        addRow(statsPanel, "Total Score :", String.valueOf(updated.getScore()), new Color(30,   90,160));

        mainPanel.add(statsPanel, BorderLayout.CENTER);

        // Tombol tutup
        JButton btnClose = new JButton("Tutup");
        btnClose.setFont(new Font("Arial", Font.BOLD, 13));
        btnClose.setBackground(new Color(70, 130, 180));
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

    private void addRow(JPanel panel, String label, String value, Color valueColor) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        lbl.setForeground(new Color(80, 80, 80));

        JLabel val = new JLabel(value);
        val.setFont(new Font("Arial", Font.BOLD, 14));
        val.setForeground(valueColor);

        panel.add(lbl);
        panel.add(val);
    }
}
