1- You can clone the repository

2- Use Maven to set all dependencies (mvn clean install)

3- I use H2 in memory database, that's mean the DB is erased and recreated at launch
if you need extra data, you can add some in the data.sql ( src/main/resources )

4-Launch the app, you can access swagger via https://localhost:8443/swagger-ui/index.html

Login and Create user are the only endpoint accesible without authentification

For testing, you can login with admin/password and then :
1-In swagger, get the bearer token after login
2-paste the bearer token in "Authorize" at the top of swagger
