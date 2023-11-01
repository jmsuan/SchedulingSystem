package lanej.schedulingsystem.helper;


/**
 * Represents a type of scene in the scheduling system's user interface.
 *
 * <p>
 * This class provides a convenient way to manage the properties of different scenes within the JavaFX application.
 * It contains scene details such as its title, file path to the FXML layout, and the dimensions (minimum, and preferred
 * width and height).
 * </p>
 *
 * Usage of this class can help streamline the process of switching between different scenes within the application by
 * ensuring consistency in scene settings and reducing redundancy.
 *
 * <p>
 * Example usage:
 * <pre>
 * SceneType loginScene = new SceneType("Login", "path/to/login.fxml", 300.0, 200.0);
 * </pre>
 * </p>
 *
 * @author Jonathan Lane
 * @version 1.0
 */
public class SceneType {
    private String screenTitle;
    private final String filePath;
    private final Double minWidth;
    private final Double minHeight;
    private final Double width;
    private final Double height;

    /**
     * Constructor to initialize a new SceneType with specific properties.
     *
     * @param screenTitle the title of the scene.
     * @param filePath the path to the FXML file of the scene.
     * @param minWidth the minimum width of the scene.
     * @param minHeight the minimum height of the scene.
     * @param width the default width of the scene.
     * @param height the default height of the scene.
     */
    public SceneType(String screenTitle,
                     String filePath,
                     Double minWidth,
                     Double minHeight,
                     Double width,
                     Double height) {
        this.screenTitle = screenTitle;
        this.filePath = filePath;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.width = width;
        this.height = height;
    }

    /**
     * Constructor to initialize a new SceneType with minimum dimensions.
     * The default width and height are set to the minimum values.
     *
     * @param screenTitle the title of the scene.
     * @param filePath the path to the FXML file of the scene.
     * @param minWidth the minimum width of the scene.
     * @param minHeight the minimum height of the scene.
     */
    public SceneType(String screenTitle,
                     String filePath,
                     Double minWidth,
                     Double minHeight) {
        this.screenTitle = screenTitle;
        this.filePath = filePath;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.width = minWidth;
        this.height = minHeight;
    }

    /**
     * Sets the title of the scene.
     *
     * @param screenTitle the new title of the scene.
     */
    public void setScreenTitle(String screenTitle) {
        this.screenTitle = screenTitle;
    }

    /**
     * @return the title of the scene.
     */
    public String getTitle() {
        return screenTitle;
    }

    /**
     * @return the path to the FXML file of the scene.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @return the minimum width of the scene.
     */
    public Double getMinWidth() {
        return minWidth;
    }

    /**
     * @return the minimum height of the scene.
     */
    public Double getMinHeight() {
        return minHeight;
    }

    /**
     * @return the default width of the scene.
     */
    public Double getWidth() {
        return width;
    }

    /**
     * @return the default height of the scene.
     */
    public Double getHeight() {
        return height;
    }
}
