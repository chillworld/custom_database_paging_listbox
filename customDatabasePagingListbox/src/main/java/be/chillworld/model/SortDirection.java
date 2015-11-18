package be.chillworld.model;

/**
 *
 * @author cossaer.f
 */
public enum SortDirection {

    ASCENDING("ASC","ASCENDING"),
    DESCENDING("DESC","DESCENDING");
    
    private final String shortName;
    private final String longName;

    private SortDirection(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
    }
    
    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }
}
