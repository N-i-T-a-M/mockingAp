import controller.ProfileController;
import model.User;
import model.UserDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ProfileTest {
    static User user;
    static ProfileController profileController;

    @BeforeAll
    public static void starter() throws Exception {
        user = new User("Mohammad", "Davood", "Mammad", "h1309973@gmail.com", "goomba goomba");
        User user2 = new User("Matin", "Mohammad", "Hj.Matin", "matin.mohammadi@gamil.com", "koomba koomba");
        UserDatabase.addUser(user);
        UserDatabase.addUser(user2);
        profileController = new ProfileController(user);
    }

    @Test
    public void changeUsernameTest() throws Exception {
        Assertions.assertEquals("Username can't be empty", profileController.changeUsername(""));
        Assertions.assertEquals("Username <M@mmad> has incorrect format", profileController.changeUsername("M@mmad"));
        Assertions.assertEquals("Player with username <Matin> already exists", profileController.changeUsername("Matin"));
        Assertions.assertEquals("Your username from <Mohammad> changed to <MohammadDF>", profileController.changeUsername("MohammadDF"));
        Assertions.assertEquals("MohammadDF", user.getUsername());
        Assertions.assertEquals("Your username from <MohammadDF> changed to <Mohammad>", profileController.changeUsername("Mohammad"));
    }

    @Test
    public void changeNicknameTest() throws Exception {
        Assertions.assertEquals("Your nickname <Mammad> changed to <MahoneDF> successfully", profileController.changeNickname("MahoneDF"));
        Assertions.assertEquals("MahoneDF", user.getNickname());
        Assertions.assertEquals("Your nickname <MahoneDF> changed to <Mammad> successfully", profileController.changeNickname("Mammad"));
    }

    @Test
    public void changePasswordTest() throws Exception {
        Assertions.assertEquals("Password can't be empty", profileController.changePassword("", "Davood"));
        Assertions.assertEquals("New password can't be empty", profileController.changePassword("Davood", ""));
    }


    @Test
    public void changeEmailTest() throws Exception {
        Assertions.assertEquals("Email can't be empty", profileController.changeEmail(""));
        Assertions.assertEquals("This email is already used", profileController.changeEmail("h1309973@gmail.com"));
        Assertions.assertEquals("The email format is incorrect", profileController.changeEmail("h1309973gmail.com"));
        Assertions.assertEquals("Your email <h1309973@gmail.com> changed to <h1337408davo@gmail.com> successfully", profileController.changeEmail("h1337408davo@gmail.com"));
        Assertions.assertEquals("h1337408davo@gmail.com", user.getEmail());
        Assertions.assertEquals("Your email <h1337408davo@gmail.com> changed to <h1309973@gmail.com> successfully", profileController.changeEmail("h1309973@gmail.com"));
    }

    @Test
    public void changeSloganTest() throws Exception {
        Assertions.assertEquals("Slogan can't be empty", profileController.changeSlogan(""));
        Assertions.assertEquals("Your slogan <goomba goomba> changed to <The king> successfully", profileController.changeSlogan("The king"));
        Assertions.assertEquals("The king", user.getSlogan());
        Assertions.assertEquals("Your slogan <The king> changed to <goomba goomba> successfully", profileController.changeSlogan("goomba goomba"));
    }

    @Test
    public void changeRemoveSloganTest() throws Exception {
        Assertions.assertEquals("Your slogan removed successfully", profileController.removeSlogan());
        Assertions.assertNull(user.getSlogan());
        user.setSlogan("goomba goomba");
    }

    @Test
    public void displayHighScoreTest() throws Exception {
        Assertions.assertEquals("Your highScore is : " + user.getHighScore(), profileController.displayHighScore());
    }

    @Test
    public void displaySloganTest() throws Exception {

        Assertions.assertEquals("Your slogan is : goomba goomba", profileController.displaySlogan());
        user.setSlogan(null);
        Assertions.assertEquals("You don't have a slogan", profileController.displaySlogan());
        user.setSlogan("goomba goomba");
    }

    @Test
    public void displayRankTest() throws Exception {
        Assertions.assertEquals("Your rank is : " + UserDatabase.playerRank(user), profileController.displayRank());
    }

    @Test
    public void displayAllTest() throws Exception {
        Assertions.assertEquals("User Information :" +
                "\n\tUsername = Mohammad" +
                "\n\tNickname = Mammad" +
                "\n\tSlogan = goomba goomba" +
                "\n\tHighScore = " + user.getHighScore() +
                "\n\tRank = " + user.getRank() +
                "\n\tE-mail = h1309973@gmail.com", profileController.displayAll());
    }
}
