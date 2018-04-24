package android.example.com.libjokes;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Jokes {

    private static List<String> categoryA = Arrays.asList(
            "Can a kangaroo jump higher than a house? Of course, a house doesn't jump at all.",
            "Relationships are like fat people... Most of them don't work out.",
            "I'm in shape... Unfortunately, it's the shape of a potato.",
            "Running away doesn't help you with your problems... unless you're fat.",
            "Light travels faster than sound. This is why some people appear bright until you hear them speak."
            );

    private static List<String> categoryB = Arrays.asList(
            "Chuck Norris doesn't brush his teeth. He points his fist at his mouth and the plaque jumps out.",
            "When Chuck Norris does a push-up, he isn't lifting himself up, he's pushing the Earth down.",
            "Some people wear Superman pajamas. Superman wears Chuck Norris pajamas.",
            "Apple pays Chuck Norris 99 cents every time he listens to a song.",
            "When the Boogeyman goes to sleep every night, he checks his closet for Chuck Norris.");

    public static String getRandomJoke() {
        List<List<String>> allJokes = Arrays.asList(categoryA, categoryB);
        Collections.shuffle(allJokes);
        List<String> category = allJokes.get(0);
        Collections.shuffle(category);
        return category.get(0);
    }

}
