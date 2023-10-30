package lanej.schedulingsystem.helper;


/**
 *
 */
public class SceneType {
    private String screenTitle = null;
    private String filePath = null;
    private Double minWidth = null;
    private Double minHeight = null;
    private Double width = null;
    private Double height = null;
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

    public void setScreenTitle(String screenTitle) {
        this.screenTitle = screenTitle;
    }

    public String getTitle() {
        return screenTitle;
    }
    public String getFilePath() {
        return filePath;
    }
    public Double getMinWidth() {
        return minWidth;
    }
    public Double getMinHeight() {
        return minHeight;
    }
    public Double getWidth() {
        return width;
    }
    public Double getHeight() {
        return height;
    }
}
