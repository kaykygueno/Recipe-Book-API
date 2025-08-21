package griffith.ui;
//Kayky Gueno 3160913
import griffith.api.MealApiHandler;
import griffith.io.CacheManager;
import griffith.io.Settings;
import griffith.model.Category;
import griffith.model.Recipe;
import griffith.model.RecipeDetail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final MealApiHandler api = new MealApiHandler();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        Settings settings = Settings.load();
        CacheManager cache = new CacheManager();
        List<Category> categories = loadCategories(cache);
        //loop first menu options
        while (true) {
            printHeader();
            printMainMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": showCategories(categories, cache, settings); break;
                case "2": searchByIngredient(); break;
                case "3": showSettings(settings); break;
                case "0": System.out.println("Goodbye!"); return;
                default: System.out.println("Invalid option");
            }
        }
    }

    private void printHeader() { //Head
        System.out.println("\n=== Recipe Book ===");
    }

    private void printMainMenu() { //first page content
        System.out.println("1) View categories");
        System.out.println("2) Search by ingredient");
        System.out.println("3) Settings");
        System.out.println("0) Exit");
        System.out.print("Choose: ");
    }

    private List<Category> loadCategories(CacheManager cache) { //search content in cache.json
        List<Category> cats = cache.loadCategories();
        if (cats == null || cats.isEmpty()) {
            try {
                cats = api.getCategories(); //case dont find search in api
                cache.saveCategories(cats);
            } catch (Exception e) {
                System.out.println("Could not load categories: " + e.getMessage()); //case dont find print error 
                cats = new ArrayList<>();
            }
        }
        return cats;
    }

    //Filter case user select option diet vegan or vegetarian 
    private void showCategories(List<Category> categories, CacheManager cache, Settings settings) {
        
        List<Category> toShow = new ArrayList<>();
        String diet = settings.getDietType();
        for (Category c : categories) {
            String name = c.getStrCategory();
            // exclude meat categories
            if ("VEGETARIAN".equals(diet)) {
                if (Arrays.asList("Beef","Chicken","Lamb","Pork","Seafood","Goat").contains(name))
                    continue;
            }
            // Vegan exclude meat and dairy/egg categories
            if ("VEGAN".equals(diet)) {
                if (Arrays.asList("Beef","Chicken","Lamb","Pork","Seafood","Goat","Dessert","Breakfast").contains(name))
                    continue;
            }
            toShow.add(c);
        }
        System.out.println("\n--- Categories ---");
        for (int i = 0; i < toShow.size(); i++) {
            System.out.printf("%d) %s%n", i+1, toShow.get(i).getStrCategory());
        }
        System.out.println("0) Back");
        System.out.print("Choose: ");
        int sel = readNumber(0, toShow.size());
        if (sel == 0) return;
        String category = toShow.get(sel-1).getStrCategory();
        showRecipes(category);
    }

    private void showRecipes(String category) { //recipe page list
        try {
            List<Recipe> list = api.getRecipesByCategory(category);
            System.out.println("\n--- Recipes in " + category + " ---");
            for (int i = 0; i < list.size(); i++) {
                System.out.printf("%d) %s%n", i+1, list.get(i).getStrMeal());
            }
            System.out.println("0) Back");
            System.out.print("Choose: ");
            int sel = readNumber(0, list.size());
            if (sel == 0) return;
            showRecipeDetail(list.get(sel-1).getIdMeal());
        } catch (IOException | InterruptedException e) {
            System.out.println("Error loading recipes: " + e.getMessage());
        }
    }

    private void searchByIngredient() { //search by ingredient menu
        System.out.print("\nEnter ingredient: ");
        String ingr = scanner.nextLine().trim();
        try {
            List<Recipe> list = api.getRecipesByIngredient(ingr);
            System.out.println("\n--- Recipes with " + ingr + " ---");
            for (int i = 0; i < list.size(); i++) {
                System.out.printf("%d) %s%n", i+1, list.get(i).getStrMeal());
            }
            System.out.println("0) Back");
            System.out.print("Choose: ");
            int sel = readNumber(0, list.size());
            if (sel == 0) return;
            showRecipeDetail(list.get(sel-1).getIdMeal());
        } catch (Exception e) {
            System.out.println("Search failed: " + e.getMessage());
        }
    }

    private void showRecipeDetail(String id) {  //instructions and ingredients menu 
        try {
            RecipeDetail detail = api.getRecipeDetail(id);
            System.out.println("\nIngredients:");
            for (String ing : detail.getIngredients()) {
                System.out.println("- " + ing);
            }
            System.out.println("\nInstructions:");
            System.out.println(detail.getInstructions());
        } catch (Exception e) {
            System.out.println("Could not load details: " + e.getMessage());
        }
    }

    private void showSettings(Settings settings) { //settings menu 
        while (true) {
            System.out.println("\n--- Settings ---");
            System.out.println("   Diet: " + settings.getDietType());
            System.out.println("   Allergens: " + settings.getAllergens());
            System.out.println("");
            System.out.println("1) Change diet");
            System.out.println("2) Add allergen");
            System.out.println("3) Remove allergen");
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String op = scanner.nextLine().trim();
            if (op.equals("0")) break;
            switch (op) {
                case "1": changeDiet(settings); break;
                case "2": addAllergen(settings); break;
                case "3": removeAllergen(settings); break;
                default: System.out.println("Invalid option");
            }
        }
    }

    private void changeDiet(Settings settings) { //first option inside settings 
        System.out.print("Enter NONE / VEGETARIAN / VEGAN: ");
        String d = scanner.nextLine().trim().toUpperCase();
        if (d.matches("NONE|VEGETARIAN|VEGAN")) {
            settings.setDietType(d);
            settings.save();
        } else {
            System.out.println("Invalid diet type");
        }
    }

    private void addAllergen(Settings settings) { //second option
        System.out.print("Enter allergen to avoid: ");
        String a = scanner.nextLine().trim().toLowerCase();
        settings.getAllergens().add(a);
        settings.save();
    }

    private void removeAllergen(Settings settings) { //last option 
        System.out.print("Enter allergen to remove: ");
        String a = scanner.nextLine().trim().toLowerCase();
        if (!settings.getAllergens().remove(a)) {
            System.out.println("Not found: " + a);
        } else {
            settings.save();
        }
    }
  //error catch in case user imput a invalid number in categories
    private int readNumber(int min, int max) { 
        while (true) {
            try {
                int n = Integer.parseInt(scanner.nextLine().trim());
                if (n >= min && n <= max) return n;
            } catch (NumberFormatException ignored) {}
            System.out.print("Enter a number between " + min + " and " + max + ": ");
        }
    }
}
