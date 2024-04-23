package com.skycat.ucrashedlol;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    public static final String CONFIG_FILE_NAME = "ucrashedlol.txt";
    public static final Logger LOGGER = LoggerFactory.getLogger("ucrashedlol");

    public static String[] getCommentStrings(String[] original) {
        File configFile = FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE_NAME).toFile();
        if (!configFile.exists()) {
            makeConfigFile(configFile, original);
        }

        return readFromFile(original, configFile);
    }

    private static String[] readFromFile(String[] original, File configFile) {
        try (Scanner scanner = new Scanner(configFile)) {
            ArrayList<String> comments = new ArrayList<>();
            while (scanner.hasNextLine()) {
                comments.add(scanner.nextLine());
            }
            return comments.toArray(new String[0]);
        } catch (IOException e) {
            LOGGER.warn("ucrashedlol failed to get comments, we'll just stick with the vanilla ones");
            return original;
        }
    }

    protected static void makeConfigFile(File file, String[] comments) {
        try {
            if (file.createNewFile()) {
                writeToFile(file, comments);
            }
        } catch (IOException e) {
            LOGGER.warn("ucrashedlol couldn't generate the config file");
        }
    }

    protected static void writeToFile(File file, String[] contents)  throws IOException {
        try (PrintWriter pw = new PrintWriter(file)) {
            for (int i = 0; i < contents.length - 1; i++) {
                pw.println(contents[i]);
            }
            pw.print(contents[contents.length - 1]); // Avoid trailing \n
        }
    }
}
