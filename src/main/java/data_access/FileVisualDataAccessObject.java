package data_access;

import entity.Visual;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * File-based implementation of VisualDataAccessObject.
 */
public class FileVisualDataAccessObject implements VisualDataAccessObject {
    private final String storagePath;

    public FileVisualDataAccessObject(String storagePath) {
        this.storagePath = storagePath;
    }

    @Override
    public void saveVisual(Visual visual) {
        try (FileWriter writer = new FileWriter(new File(storagePath, "visuals.txt"), true)) {
            writer.write(visual.getImagePath() + " | " + visual.getDescription() + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Failed to save visual: " + e.getMessage());
        }
    }
}

