import com.rapidminer.RapidMiner;
import com.rapidminer.tools.PlatformUtilities;

import java.nio.file.Paths;

/**
 * The type Cli launcher.
 */
class CliLauncher {
    /**
     * Main.
     *
     * @param args the args
     * @throws Exception the exception
     */
    public static void main(String args[]) throws Exception {
        System.setProperty("com.apple.macos.useScreenMenuBar","true");
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty(PlatformUtilities.PROPERTY_RAPIDMINER_HOME, Paths.get("").toAbsolutePath().toString());
        RapidMiner.setExecutionMode(RapidMiner.ExecutionMode.COMMAND_LINE);
        RapidMiner.init();
    }
}