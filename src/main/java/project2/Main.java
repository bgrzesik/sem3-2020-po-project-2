package project2;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        createApplication();
    }

    private static void createApplication() {
        new LwjglApplication(new Application(), getDefaultConfiguration());
    }

    private static LwjglApplicationConfiguration getDefaultConfiguration() {
        LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
        configuration.title = "Pacman";
        configuration.width = 720;
        configuration.height = 1280;
        configuration.forceExit = false;

        // FIXME
        configuration.x = 2000;

        return configuration;
    }
}
