package griffith.io;

import com.google.gson.Gson;
import griffith.model.Category;
import griffith.model.Recipe;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CacheManager {
    private static final String CAT_CACHE_FILE = "categories_cache.json";
    private static final String REC_CACHE_FILE = "recipes_cache.json";
    private final Gson gson = new Gson();

    // off line mode categories saving to another file
    public void saveCategories(List<Category> cats) {
        try (FileWriter writer = new FileWriter(CAT_CACHE_FILE)) {
            gson.toJson(cats, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // try load the categories list in off line case
    public List<Category> loadCategories() {
        File file = new File(CAT_CACHE_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(file)) {
            Category[] arr = gson.fromJson(reader, Category[].class);
            
            return arr != null ? Arrays.asList(arr) : new ArrayList<>();
        } catch (IOException e) { //check if json is empty
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // save the recipes in another file
    public void saveRecipes(List<Recipe> recs) {
        try (FileWriter writer = new FileWriter(REC_CACHE_FILE)) {
            gson.toJson(recs, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // try to load the recipes in off line mode 
    public List<Recipe> loadRecipes() {
        File file = new File(REC_CACHE_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(file)) {
            Recipe[] arr = gson.fromJson(reader, Recipe[].class);
            return arr != null ? Arrays.asList(arr) : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
