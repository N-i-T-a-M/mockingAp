package model.Property;

public class Resources extends Property {
    private final ResourceType resourceType;

    public Resources(ResourceType resourceType, int value) {
        super(value);
        this.resourceType = resourceType;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }
}
