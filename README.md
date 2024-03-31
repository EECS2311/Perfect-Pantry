# EECS2311Z
Group 7  

Members:  
Chiaghame Allen  
Sarah Asghar   
Nina Dang    
Wan Ning Ma   
Michel Nwoye-Vincent    
Edison Tran  

<img width="795" alt="image" src="https://github.com/EECS2311/EECS2311/assets/97976216/14fefb87-ecda-4a02-b759-90d9a0807c2c">


# Running the Project
**This project was developed using Java 17.**

1. Pull this project repository.
2. Create an info.java file within the database package.
Initialize the class as such.
```java
package database;
public class info {

	public static String dbName = "";
	public static String dbUser = "";
	public static String dbPass = "";
	public static String url = "";

}
```
3. `RecipeApiClient` uses gson 2.10.1 contained in `lib` folder. It is an external library that does JSON parsing. You will need to add this jar file to your build path (Eclipse) or project structure (IntelliJ).
4. Run the the following queries that are located in the database package... DB_drop.sql > DB_init.sql > DB_populate.sql (in this order)
5. Install JDBC for postgreSQL and put it on your buildpath.
6. src/gui/HomeView -> Run
To setup gradle:
7. In the terminal, run `./gradlew build` (Linux/OSX) or `./gradlew.bat build` (Windows)
8. Run `./gradlew start` (Linux/OSX) or `./gradlew.bat start` (Windows) to run the program.

# Features

* Create containers representing pantry storage that keeps track of which items are in which compartment the ingredient/item is in
* Store, sort, and filter items inside of containers with specific information such as expiration date, food freshness, and food group
* Help to reduce food waste:
	* Receive notifications when items are close to expiration
	* View and star recipes that include ingredients that are in your containers
	* Visual calendar display of all items on the days that they expire
* Statistics relating to grocery and ingredient habits
* Find food storage tips for certain household staples
* Customizable program inside of settings
* Set up a grocery list within the app and export it as a `.txt` file for later use
 
