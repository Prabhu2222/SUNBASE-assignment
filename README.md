# SUNBASE-assignment

Brief Description About Project

---

1.The customer-management-portal folder contains the backend (springboot v3.2.2,Java 17)and sunbase-front end folder conatins the frontend(HTML5,CSS,Javascript)

2.The Backend has APIs' for performing CRUD operation like creating customer,updating customer,getting list of customer page by page(which implements pagination and searching) and deleteing customer.

3.The springboot application is equipped with JWT Authentication which validates user based on InMemoryDatabase for simplicity.

4.The default loginId is "user" and password is "user@123".This default data can be modified in application.properties file during launching of application.similarly the Jwt token validation time can also be configured from application.properties file as well as from out side for testing purpose.

5.The backend uses MySql database for storing customer details.

6.Application runs on Default Port 8080.

7.The FrontEnd part is made with HTML,CSS and Vanila Javascript and it runs on port 10000.
The Front end has three pages index.html(login page),customerlist.html(main section which shows datas in tabular form) and addcustomer.html(for adding and updating customers).

Execution Flow of Project

---

1.The UI part starts with index.html file which is basically a log in page for entering
username and password.

2.On unSuccessful Authentication an alert will appear denying redirection to main section.
But on successful authentication from backend a Jwt token will be generated an will be stored in local storgae of the browser which will be attached as Authorizaion header for subsequent request made to the backend.

3.The default jwt token expiration time is 1 minute which can be modified before start of the application.

4.During token expiration duration the admin user can perform all sorts of backend operation through UI.But As soon as the token expires an alert will appear on window which will redirect the admin user agian to login page.
