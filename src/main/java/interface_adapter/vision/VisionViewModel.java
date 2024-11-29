package interface_adapter.vision;

/**
 * View model for vision feature.
 */
public class VisionViewModel {
    private String imagePath;
    private String errorMessage;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Method to notify the view when data changes.
     */
    public void notifyView() {
        // This method will be called by the Presenter to notify the View that the state has changed.
        // For example, update the image or display an error message.
        System.out.println("Image Path: " + imagePath);
        System.out.println("Error Message: " + errorMessage);
        }
}
