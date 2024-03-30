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
4. Run the DBDef.sql queries in the database package. The init.sql queries are optional (if you want to setup some default values.
5. Install JDBC for postgreSQL and put it on your buildpath.
6. src/gui/HomeView -> Run
