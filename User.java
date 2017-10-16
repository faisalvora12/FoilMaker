

/**
 * Created by faisa on 11/25/2016.
 */
public class User {
    private String username;
    private String password;
    private int cumulativeScore;
    private int fooled;
    private int fooledBy;
    private String playerChoice="";
    private String playerSuggestion ="";
    private String message="";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public User(String username,String password,int cumulativeScore,int fooled,int fooledBy){
        this.username=username;
        this.password=password;
        this.cumulativeScore=cumulativeScore;
        this.fooled=fooled;
        this.fooledBy=fooledBy;
        this.playerChoice="";
        this.playerSuggestion="";
    }

    public String getPlayerChoice() {
        return playerChoice;
    }

    public void setPlayerChoice(String playerChoice) {
        this.playerChoice = playerChoice;
    }

    public String getPlayerSuggestion() {
        return playerSuggestion;
    }

    public void setPlayerSuggestion(String playerSuggestion) {
        this.playerSuggestion = playerSuggestion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCumulativeScore() {
        return cumulativeScore;
    }

    public void setCumulativeScore(int cumulativeScore) {
        this.cumulativeScore = cumulativeScore;
    }

    public int getFooled() {
        return fooled;
    }

    public void setFooled(int fooled) {
        this.fooled = fooled;
    }

    public int getFooledBy() {
        return fooledBy;
    }

    public void setFooledBy(int fooledBy) {
        this.fooledBy = fooledBy;
    }

}
