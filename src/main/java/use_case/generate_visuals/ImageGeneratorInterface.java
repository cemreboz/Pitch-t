package use_case.generate_visuals;

/**
 * Interface for image generation functionality.
 */
public interface ImageGeneratorInterface {
    /**
     * Abstract the image generation functionality into an interface.
     * @return ImageAnalyzer output
     */
    String generateImage(String prompt, String filePath) throws Exception;
}
