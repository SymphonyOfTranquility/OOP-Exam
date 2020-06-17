package Entity;

public class Inhabitants {
    private int id;
    private String name;
    private String languageName;

    public Inhabitants(int id, String name, String languageName) {
        this.id = id;
        this.name = name;
        this.languageName = languageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
}
