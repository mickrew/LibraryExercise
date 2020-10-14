public class Publisher {
    private String name;
    private String location;

    public Publisher(String name, String location){
        this.name=name;
        this.location=location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
