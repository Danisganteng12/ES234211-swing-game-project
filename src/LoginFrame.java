import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField     txtUsername;
    private JPasswordField txtPassword;
    private JButton        btnLogin;
    private PlayerService  playerService;

    public LoginFrame() {
        playerService = new PlayerService();

        // GUI layout is already provided by the lecturer.
        // Students only need to complete the missing logic.
        setTitle("Tic-Tac-Toe - Login");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(new Color(240, 248, 255));

        JLabel lblTitle = new JLabel("TIC-TAC-TOE", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitle.setForeground(new Color(30, 90, 160));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 5, 8));
        formPanel.setBackground(new Color(240, 248, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.BOLD, 13));
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 13));

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 13));
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 13));

        formPanel.add(lblUsername);
        formPanel.add(txtUsername);
        formPanel.add(lblPassword);
        formPanel.add(txtPassword);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(new Color(240, 248, 255));
        btnLogin = new JButton("LOGIN");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(30, 90, 160));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setPreferredSize(new Dimension(140, 38));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setOpaque(true);
        btnPanel.add(btnLogin);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());
                Player player = playerService.login(username, password);
                if (player != null) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Login successful!");
                    MainMenuFrame menuFrame = new MainMenuFrame(player);
                    menuFrame.setVisible(true);
                    LoginFrame.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid username or password!");
                }

            }
        });
    }
}
