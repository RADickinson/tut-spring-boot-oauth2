# Eclipse SSO Application 1

This is the source for the application SSO1 in the Eclipse SSO demo.

# How to run the app
* Right click on `SsoPoc1Application.java` and select `Run As -> Spring boot application`, or
* From the root of the project (app1 folder) run `mvn spring-boot:run`
 
# Implementation details
By default the app runs on [http://localhost:8081] and connects to Eclipse running on [(https://olm-custombuild-ecl.olmeservices.co.uk/]

The app identifies itself to Eclipse as `ssoClient1Id` with the credentials `secret`. Eclipse is configured to recognize this client Id, but may not allow you to change it.

The app configuration is managed by the YAML file `src/main/resources/application.yml`. If you want to run a local copy of Eclipse built from the `oauth_develop` branch you can comment out the existing OAuth section
in the config file and uncomment "local" OAuth section included in the file but commented out.

You shouldn't need to change much of the application autowiring in the `SsoPoc1Application.java` as it is a fairly simple bootstrapping of a spring boot app.

You can add additional REST endpoints in the `SsoPocController.java` (look at existing code as an example).

The UI is all maintained within `src/main/resources/static/index.html` where you can edit the layout, add jquery functions and play to your hearts content.

> If you do want to edit this project, please create a separate branch for your personal changes as the version in `develop` is likely to be updated when new features are completed.