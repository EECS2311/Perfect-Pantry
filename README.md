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
- This project was devloped using Java 17.
1. Pull this project repository.
2. Create an info.java file within the database package.
Initialize the class as such.
```
package database;
public class info {

	public static String dbName = "";
	public static String dbUser = "";
	public static String dbPass = "";
	public static String url = "";

}
```
3. Run the DBDef.sql queries in the database package. The init.sql queries are optional (if you want to setup some default values.
4. Install JDBC for postgreSQL and put it on your buildpath.
5. src/gui/HomeView -> Run
