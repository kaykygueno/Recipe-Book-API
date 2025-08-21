package griffith.io;
//Kayky Gueno 3160913
import com.google.gson.Gson;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Settings {
    private String dietType = "NONE";
    private List<String> allergens = new ArrayList<>();

    public String getDietType() { return dietType; }
    public void setDietType(String dietType) { this.dietType = dietType; }

    public List<String> getAllergens() { return allergens; }
    public void setAllergens(List<String> allergens) { this.allergens = allergens; }

    // Load the settings.json and return
    public static Settings load() {
        File f = new File("settings.json");
        if (!f.exists()) return new Settings();
        try (Reader r = new FileReader(f)) {
            return new Gson().fromJson(r, Settings.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new Settings();
        }
    }

    // save the object in settings.json
    public void save() {
        try (Writer w = new FileWriter("settings.json")) {
            new Gson().toJson(this, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
