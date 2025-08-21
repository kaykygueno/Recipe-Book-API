package griffith.model;
//Kayky Gueno 3160913
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

// imput to give all the recipe details, including ingredients and instructions.

public class RecipeDetail {
    @SerializedName("idMeal")
    private String idMeal;

    @SerializedName("strMeal")
    private String strMeal;

    @SerializedName("strInstructions")
    private String instructions;

    // build 20 options with ingredient and portion size
    @SerializedName("strIngredient1") private String ing1;
    @SerializedName("strIngredient2") private String ing2;
    @SerializedName("strIngredient3") private String ing3;
    @SerializedName("strIngredient4") private String ing4;
    @SerializedName("strIngredient5") private String ing5;
    @SerializedName("strIngredient6") private String ing6;
    @SerializedName("strIngredient7") private String ing7;
    @SerializedName("strIngredient8") private String ing8;
    @SerializedName("strIngredient9") private String ing9;
    @SerializedName("strIngredient10") private String ing10;
    @SerializedName("strIngredient11") private String ing11;
    @SerializedName("strIngredient12") private String ing12;
    @SerializedName("strIngredient13") private String ing13;
    @SerializedName("strIngredient14") private String ing14;
    @SerializedName("strIngredient15") private String ing15;
    @SerializedName("strIngredient16") private String ing16;
    @SerializedName("strIngredient17") private String ing17;
    @SerializedName("strIngredient18") private String ing18;
    @SerializedName("strIngredient19") private String ing19;
    @SerializedName("strIngredient20") private String ing20;

    @SerializedName("strMeasure1") private String m1;
    @SerializedName("strMeasure2") private String m2;
    @SerializedName("strMeasure3") private String m3;
    @SerializedName("strMeasure4") private String m4;
    @SerializedName("strMeasure5") private String m5;
    @SerializedName("strMeasure6") private String m6;
    @SerializedName("strMeasure7") private String m7;
    @SerializedName("strMeasure8") private String m8;
    @SerializedName("strMeasure9") private String m9;
    @SerializedName("strMeasure10") private String m10;
    @SerializedName("strMeasure11") private String m11;
    @SerializedName("strMeasure12") private String m12;
    @SerializedName("strMeasure13") private String m13;
    @SerializedName("strMeasure14") private String m14;
    @SerializedName("strMeasure15") private String m15;
    @SerializedName("strMeasure16") private String m16;
    @SerializedName("strMeasure17") private String m17;
    @SerializedName("strMeasure18") private String m18;
    @SerializedName("strMeasure19") private String m19;
    @SerializedName("strMeasure20") private String m20;

    public String getInstructions() {
        return instructions;
    }

    //Return string list ingredient quantity
    
    public List<String> getIngredients() {
        List<String> list = new ArrayList<>();
        addIfPresent(list, m1, ing1);
        addIfPresent(list, m2, ing2);
        addIfPresent(list, m3, ing3);
        addIfPresent(list, m4, ing4);
        addIfPresent(list, m5, ing5);
        addIfPresent(list, m6, ing6);
        addIfPresent(list, m7, ing7);
        addIfPresent(list, m8, ing8);
        addIfPresent(list, m9, ing9);
        addIfPresent(list, m10, ing10);
        addIfPresent(list, m11, ing11);
        addIfPresent(list, m12, ing12);
        addIfPresent(list, m13, ing13);
        addIfPresent(list, m14, ing14);
        addIfPresent(list, m15, ing15);
        addIfPresent(list, m16, ing16);
        addIfPresent(list, m17, ing17);
        addIfPresent(list, m18, ing18);
        addIfPresent(list, m19, ing19);
        addIfPresent(list, m20, ing20);
        return list;
    }
    //Check is the ingredient is correct or if it null to avoid errors
    private void addIfPresent(List<String> list, String measure, String ingredient) {
        if (ingredient != null && !ingredient.isBlank()) {
            String entry = (measure != null && !measure.isBlank())
                ? measure.trim() + " " + ingredient.trim()
                : ingredient.trim();
            list.add(entry);
        }
    }
}
