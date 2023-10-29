package lanej.schedulingsystem.helper;


/**
 *
 */
public class ScreenType {
    private String screenName = null;
    private String filePath = null;
    private Double minWidth = null;
    private Double minHeight = null;
    private Double width = null;
    private Double height = null;
    public ScreenType(String screenName,
                      String filePath,
                      Double minWidth,
                      Double minHeight,
                      Double width,
                      Double height) {
        this.screenName = screenName;
        this.filePath = filePath;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.width = width;
        this.height = height;
    }
    public String getTitle() {
        return screenName;
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
