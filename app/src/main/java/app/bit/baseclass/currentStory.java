package app.bit.baseclass;

/**
 * Created by bdatd on 3/12/2016.
 */
public class currentStory {

    private static currentStory ourInstance = new currentStory();

    public static currentStory getInstance() {
        return ourInstance;
    }

    private currentStory() {
    }
}
