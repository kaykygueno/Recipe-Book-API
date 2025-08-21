package griffith.model;
//Kayky Gueno 3160913
public class Recipe {
    private String idMeal;
    private String strMeal;

    // enforces static typing, simplifies JSON serialization and deserialization 
    //Cleanly separates data-transport responsibilities from business logic.
    public String getIdMeal() { return idMeal; }
    public void setIdMeal(String idMeal) { this.idMeal = idMeal; }

    public String getStrMeal() { return strMeal; }
    public void setStrMeal(String strMeal) { this.strMeal = strMeal; }
}
