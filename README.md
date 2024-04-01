# EECS2311Z
Group 7  

Members:  
Chiaghame Allen  
Sarah Asghar   
Nina Dang    
Wan Ning Ma   
Michel Nwoye-Vincent    
Edison Tran  

![image](https://github.com/EECS2311/EECS2311/assets/80595547/abb1524f-ab3a-40b9-a31f-7d3720e94506)


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

![image](https://github.com/EECS2311/EECS2311/assets/80595547/ddca1879-1ce4-4307-8a8a-a65957d3c20f)
![image](https://github.com/EECS2311/EECS2311/assets/80595547/b6f433f3-623a-461c-8467-70bc62133e74)
![image](https://github.com/EECS2311/EECS2311/assets/80595547/7e100ca4-3ddb-411a-bb89-9f57fc7df340)
![image](https://github.com/EECS2311/EECS2311/assets/80595547/3dc11725-1d73-4048-83ab-547cc71d0239)
![image](https://github.com/EECS2311/EECS2311/assets/80595547/ffe58f8a-242f-41b0-9bc0-76e76097dd97)
![image](https://github.com/EECS2311/EECS2311/assets/80595547/0b474bb7-e13c-4311-8309-92a333c8b996)
![image](https://github.com/EECS2311/EECS2311/assets/80595547/1b49c138-5f3b-4d04-9a01-e467fd9a4400)
![image](https://github.com/EECS2311/EECS2311/assets/80595547/4fa8f637-df1e-4b1a-9cd5-a52f01717ea5)





 
