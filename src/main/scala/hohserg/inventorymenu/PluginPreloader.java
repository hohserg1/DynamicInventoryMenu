package hohserg.inventorymenu;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;

public class PluginPreloader extends JavaPlugin {
    public static PluginPreloader instance;

    @Override
    public void onEnable() {
        getLogger().info("Checking for libraries...");
        if (!getDataFolder().exists() && !getDataFolder().mkdirs()) {
            getLogger().warning("Error creating data folder!");
            return;
        }

        File librariesDir = new File("./libs");
        if (!librariesDir.exists() && !librariesDir.mkdirs()) {
            getLogger().warning("Error creating lib folder!");
            return;
        }

        String[] librariesUrl = {
                "http://repo1.maven.org/maven2/org/scala-lang/scala-library/2.12.6/scala-library-2.12.6.jar"
        };
        File[] librariesFiles = new File[]{
                new File(librariesDir, "scala-library-2.12.6.jar")
        };

        for (int i = 0; i < librariesUrl.length; i++) {
            String url = librariesUrl[i];
            File file = librariesFiles[i];
            if (!loadLibrary(url, file)) {
                getLogger().severe("Error preloading HeadHanter!");
                getPluginLoader().disablePlugin(this);
                return;
            }
        }

        for (File file : librariesFiles) {
            try {
                addToClasspath(file.toURI().toURL());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        instance = this;

        Main.onEnable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return Main.onCommand(sender, command, label, args);
    }

    private void addToClasspath(final URL url) {
        final ClassLoader classLoader = getClass().getClassLoader();
        if (!(classLoader instanceof URLClassLoader)) {
            System.out.println("Cannot add '" + url
                    + "' to classloader because it's not an URL classloader");
            return;
        }
        final URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
        try {
            final Method addURL = URLClassLoader.class.getDeclaredMethod(
                    "addURL", new Class[]{URL.class});
            addURL.setAccessible(true);
            addURL.invoke(urlClassLoader, url);
            getLogger().info("Injected " + url + " into ClassLoader");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        Main.onDisable();
    }

    private boolean loadLibrary(String url, File libraryFile) {
        if (libraryFile.exists() && libraryFile.length() > 0) {
            getLogger().info(libraryFile.getName() + " is already installed");
            return true;
        } else {
            getLogger().info("Loading " + libraryFile.getName() + " from " + url);
            try {
                downloadFile(url, libraryFile);
                getLogger().info("Loaded " + libraryFile.getName());
                return true;
            } catch (IOException e) {
                if (!libraryFile.delete())
                    getLogger().warning("Error deleting " + libraryFile);
                getLogger().log(Level.SEVERE, "Error downloading " + libraryFile.getName(), e);
                return false;
            }
        }
    }

    private void downloadFile(String url, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ReadableByteChannel rbc = null;
        try {
            URLConnection connection = new URL(url).openConnection();
            // Scala library url gives 403 if no user-agent
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

            rbc = Channels.newChannel(connection.getInputStream());
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } finally {
            fos.close();
            if (rbc != null)
                rbc.close();
        }
    }
}