package model;


import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String question;
    private String avatar;
    private boolean isOnline = false;
    private ArrayList<User> friends = new ArrayList<>();

    public void setGameRequest(GameRequest gameRequest) {
        this.gameRequest = gameRequest;
    }

    public GameRequest getGameRequest() {
        return gameRequest;
    }

    private GameRequest gameRequest = null;

    public ArrayList<User> getFriends() {
        return friends;
    }
    public boolean isFriendListFull () {
        if (friends.size() <= 100)
            return false;
        return true;
    }

    private ArrayList<User> waitingForThemToAccept = new ArrayList<>();

    public ArrayList<User> getWaitingForThemToAccept() {

        return waitingForThemToAccept;
    }

    private ArrayList<User> waitingForYouToAccept = new ArrayList<>();

    public ArrayList<User> getWaitingForYouToAccept() {
        return waitingForYouToAccept;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setQuestion(int number) {
        this.question = UserDatabase.getQuestions().get(number - 1);
    }

    public String getQuestion() {
        return question;
    }

    private String answer;
    private String slogan;

    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }

    public boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    private boolean stayLoggedIn;
    private int highScore;
    private int currentScore;
    private int rank;

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setCurrentScore(int currentScore) {
        if (currentScore > highScore)
            setHighScore(currentScore);
        this.currentScore = currentScore;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public User(String username, String password, String nickname, String email, String slogan) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
        this.avatar = User.class.getResource("/Avatars/no_avatar.png").toExternalForm();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getAnswer() {
        return answer;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getRank() {
        ArrayList<User> users = UserDatabase.rankPlayers();
        for (User user : users) {
            if (user == this) {
                this.rank = users.indexOf(user) + 1;
                return users.indexOf(user) + 1;
            }
        }
        return 0;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
