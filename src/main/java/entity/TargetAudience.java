package entity;

/**
 * Class for Target Audience.
 */
public class TargetAudience {
    private String name;
    private String description;

    // Constructor
    public TargetAudience(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

