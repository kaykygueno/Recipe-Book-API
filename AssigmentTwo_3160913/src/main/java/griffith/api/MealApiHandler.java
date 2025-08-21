package griffith.api;
//Kayky Gueno 3160913
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import griffith.model.Category;
import griffith.model.Recipe;
import griffith.model.RecipeDetail;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealApiHandler {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    // Filter remove vegan and vegetarian from original list
    public List<Category> getCategories() throws IOException, InterruptedException {
        // build requisition 
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/categories.php"))
            .GET()
            .build();
        // build and read JSON
        HttpResponse<InputStream> resp = client.send(req, HttpResponse.BodyHandlers.ofInputStream());
        try (InputStreamReader reader = new InputStreamReader(resp.body())) {
            CategoriesWrapper wrapper = gson.fromJson(reader, CategoriesWrapper.class);
            List<Category> result = new ArrayList<>();
            for (Category c : wrapper.categories) {
                String name = c.getStrCategory();
                // remove vegan and vegetarian from the original list to improve searching 
                if (name != null
                    && !"Vegan".equalsIgnoreCase(name)
                    && !"Vegetarian".equalsIgnoreCase(name)) {
                    result.add(c);
                }
            }
            return result;
        }
    }

    // search for recipes in an json file
    public List<Recipe> getRecipesByCategory(String category) throws IOException, InterruptedException {
        String endpoint = BASE_URL + "/filter.php?c=" + URLEncoder.encode(category, StandardCharsets.UTF_8);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(endpoint))
            .GET()
            .build();
        HttpResponse<InputStream> resp = client.send(req, HttpResponse.BodyHandlers.ofInputStream());
        try (InputStreamReader reader = new InputStreamReader(resp.body())) {
            RecipesWrapper wrapper = gson.fromJson(reader, RecipesWrapper.class);
            return wrapper.meals != null
                ? new ArrayList<>(Arrays.asList(wrapper.meals))
                : new ArrayList<>();
        }
    }

    //Build search by ingredient
    public List<Recipe> getRecipesByIngredient(String ingredient) throws IOException, InterruptedException {
        String endpoint = BASE_URL + "/filter.php?i=" + URLEncoder.encode(ingredient, StandardCharsets.UTF_8);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(endpoint))
            .GET()
            .build();
        HttpResponse<InputStream> resp = client.send(req, HttpResponse.BodyHandlers.ofInputStream());
        try (InputStreamReader reader = new InputStreamReader(resp.body())) {
            RecipesWrapper wrapper = gson.fromJson(reader, RecipesWrapper.class);
            return wrapper.meals != null
                ? new ArrayList<>(Arrays.asList(wrapper.meals))
                : new ArrayList<>();
        }
    }

    // search all recipe details
    public RecipeDetail getRecipeDetail(String id) throws IOException, InterruptedException {
        String endpoint = BASE_URL + "/lookup.php?i=" + URLEncoder.encode(id, StandardCharsets.UTF_8);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(endpoint))
            .GET()
            .build();
        HttpResponse<InputStream> resp = client.send(req, HttpResponse.BodyHandlers.ofInputStream());
        try (InputStreamReader reader = new InputStreamReader(resp.body())) {
            DetailWrapper wrapper = gson.fromJson(reader, DetailWrapper.class);
            if (wrapper.meals != null && wrapper.meals.length > 0) {
                return wrapper.meals[0];
            }
            return new RecipeDetail();
        }
    }

    // Wrappers deserialisation
    private static class CategoriesWrapper {
        @SerializedName("categories")
        Category[] categories;
    }
    private static class RecipesWrapper {
        @SerializedName("meals")
        Recipe[] meals;
    }
    private static class DetailWrapper {
        @SerializedName("meals")
        RecipeDetail[] meals;
    }
}
