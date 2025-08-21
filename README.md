# Recipe Book API

A Java-based API and console application for managing a recipe book, featuring modular design, data caching, and a console user interface.

## Features

- Add, view, update, and delete recipes
- Organize recipes by category
- Store and retrieve recipe details (ingredients, instructions, etc.)
- Search recipes by name or ingredient
- API handler for meal/recipe data
- Caching and settings management (JSON files)
- Console-based user interface
- Unit tests included

## How to Run

1. **Clone or download this repository.**
2. **Compile the Java files usando Maven:**
mvn clean compile

Ou, se preferir compilar manualmente:
javac -d out src/main/java/griffith/**/*.java

3. **Execute a aplicação principal:**
java -cp out griffith.RecipeApp

*(Ajuste o caminho se necessário, dependendo do seu ambiente IDE ou linha de comando)*

4. **Certifique-se de que os arquivos de dados (`categories_cache.json`, `recipes_cache.json`, `settings.json`) estejam no diretório raiz do projeto, pois são necessários para o funcionamento correto da aplicação.*

## Project Structure

AssigmentTwo_3160913/
categories_cache.json
recipes_cache.json
settings.json
pom.xml
src/
main/
java/
griffith/
api/
MealApiHandler.java
io/
CacheManager.java
Settings.java
model/
Category.java
Recipe.java
RecipeDetail.java
ui/
ConsoleMenu.java
RecipeApp.java
test/
java/
griffith/
AssigmentTwo_3160913/
AppTest.java


## Technologies Used

- Java
- Maven

## Author

Kayky Gueno

---

*Feel free to adapt this README, add usage examples, or include screenshots of the console interface!
