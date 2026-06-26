public class Player {
    //fields
    private int id;
    private String username;
    private int wins;
    private int losses;
    private int draws;
    private int score;

    //constructor
    public Player(int id, String username, int wins, int losses, int draws, int score) {
        this.id = id;
        this.username = username;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
        this.score = score;
    }

    //getter method
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public int getWins() {
        return wins;
    }
    public int getLosses() {
        return losses;
    }
    public int getDraws() {
        return draws;
    }
    public int getScore() {
        return score;
    }
}
