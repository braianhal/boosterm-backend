package boosterm.backend.domain;

public class Term {

    private String code;

    private String name;

    private String type;

    private String description;

    public Term(String code, String name, String type, String description) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
