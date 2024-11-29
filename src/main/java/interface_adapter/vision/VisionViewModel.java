package interface_adapter.vision;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * View model for vision feature.
 */
public class VisionViewModel {
    private String imagePath;
    private String errorMessage;
    private final PropertyChangeSupport support;

    public VisionViewModel() {
        this.support = new PropertyChangeSupport(this);
    }

    // Getter for imagePath
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Setter for Image Path.
     */
    public void setImagePath(String imagePath) {
        final String oldImagePath = this.imagePath;
        this.imagePath = imagePath;
        support.firePropertyChange("imagePath", oldImagePath, imagePath);
    }

    // Getter for errorMessage
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Setter for error messages.
     */
    public void setErrorMessage(String errorMessage) {
        final String oldErrorMessage = this.errorMessage;
        this.errorMessage = errorMessage;
        support.firePropertyChange("errorMessage", oldErrorMessage, errorMessage);
    }

    // Method to add PropertyChangeListener for imagePath changes
    public void addImagePathListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener("imagePath", listener);
    }

    // Method to add PropertyChangeListener for errorMessage changes
    public void addErrorMessageListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener("errorMessage", listener);
    }

    // Notifies all listeners about the change in ViewModel state
    public void notifyListeners() {
        support.firePropertyChange("state", null, this);
    }
}