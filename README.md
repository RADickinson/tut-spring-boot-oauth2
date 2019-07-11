# Eclipse SSO Demo

This demo shows how Eclipse can be used to authenticate Users of external apps and provide
Single Sign-on (SSO). The Apps can then retrieve an OAuth2 access token and use it to invoke
REST APIs in Eclipse.

# How to setup the apps and run them
* You don't need anything new installed - Just Java 8 and Maven.
* Clone this repo locally and switch to the `develop` branch.
* Import the projects from git into a new workspace in your IDE.
* Find the `com.example.SsoPoc1Application.java` in app1 (or `SsoPoc2Application.java` in app2), right-click and `Run As -> Spring boot application`.
* Alternatively, change directory to the app1 (or app2) sub-folders and run `mvn spring-boot:run`

# How to use the apps
The Apps will by default connect with [Eclipse Custom Build](https://olm-custombuild-ecl.olmeservices.co.uk/) so first make sure this is running. It should be a recent copy of Alpha, so you can login with your accounts from Alpha.

When App1 is running you can access it on [http://localhost:8081](http://localhost:8081). App2 can be accessed on [http://localhost:8082](http://localhost:8082).

To login to either App, simple click on `Login with Eclipse` and it should take you first to the Eclipse login and then, once logged in, to the main App page. If you're already logged into Eclipse, you will be taken directly to the main App page (because SSO).

From there you can launch Eclipse or the other App (if running). You can also search Eclipse for person
records using the search box.

> Please Note: Currently the Refresh and Revoke functions do not work. If your token expires then the Eclipse Search will stop working also. To refresh with a working token, simply logout and login again until this is fixed. 

If you want to customise any aspect of the applications, click into each sub-folder above for more details.