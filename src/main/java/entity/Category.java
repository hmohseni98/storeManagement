package entity;

public class Category {

    private Integer id;
    private String title;
    private String description;
    private Integer categoryId;

    public Category(Integer id, String title, String description, Integer categoryId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
    }

    public Category(String title, String description, Integer categoryId) {
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
