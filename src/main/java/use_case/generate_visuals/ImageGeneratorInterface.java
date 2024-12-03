package use_case.generate_visuals;

/**
 * Interface for image generation functionality.
 */
public interface ImageGeneratorInterface {
    /**
     * Abstract the image generation functionality into an interface.
     * @param prompt prompt
     * @param filePath prompt
     * @return ImageAnalyzer output
     * @throws Exception exception
     */
    String generateImage(String prompt, String filePath) throws Exception;
}
