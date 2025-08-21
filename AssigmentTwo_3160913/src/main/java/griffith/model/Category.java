package griffith.model;
//Kayky Gueno 3160913
public class Category {
    private String idCategory;
    private String strCategory;
    private String strCategoryDescription;

    // getters e setters 
    public String getIdCategory() { return idCategory; }
    public void setIdCategory(String idCategory) { this.idCategory = idCategory; }

    public String getStrCategory() { return strCategory; }
    public void setStrCategory(String strCategory) { this.strCategory = strCategory; }

    public String getStrCategoryDescription() { return strCategoryDescription; }
    public void setStrCategoryDescription(String strCategoryDescription) { this.strCategoryDescription = strCategoryDescription; }

    public String toString() {
        return strCategory;
    }
}
