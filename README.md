# EMT
EMT project Online Bookstore in Spring Boot using Thymeleaf, Hibernate, Stripe API and Maven.
For use on a local machine, the default setup is with a MySQL database, the details for connection can be modified in the application properties.

## Login and Registration (using Spring Security)
On running the application locally, it uses the port 8080.
If the current user is not logged in, it will redirect to the http://localhost:8080/login page.
If the user wants to create a new profile, they will have manually reach the http://localhost:8080/register page, which
after a successful registration redirects back to the login page.

## Navigation
On successful login, the user is redirected to the home page, the page with the list of available products, http://localhost:8080/products .
From the home page, the user has access to go to the page of each individual product or his profile page.
The header of each page allows the user easier navigation across the application as it contains the links to the home page, the user's profile page and the logout page.

## Orders
The main functionality of the application is placing orders on a certain product. 
The user can add orders in his cart via the home page, from the list of products, or on the individual product's page.
On the profile page, the user can change his orders, by updating the quantity of the product order, or deleting the order.
If the user wants to finish his order, the next step is payment for the entire cart.

## Payment
The payment process is made using Stripe API, although in the current state it is only using a test version, as for the final product, a licence
would have to be bought from Stripe, with which the encryption keys will be unique.
In order to make the payment, the user is taken through a new page, on which he will have to input his private information.
If the payment is successful, the user is taken back to his profile page, and the current cart of the user and payment history is updated.
On the backend the transaction is saved in relation to the cart, so there is no confusion as to which user made which transaction.

## Admin privileges
If the user has admin privileges, he will have access to certain pages on the application, specifically for the use of adding or deleting products, categories and authors.
Although both admin and user have access to the home page, the user only has the ability to place purchases on products, while the admin only has the ability to delete products from this page.
Also, both admin and user have access to their profile page, the admin's page does not contain a cart or purchase history, it only contains links to said pages with admin priviliges.

## Pagination
The home page, the page with the list of products, has been made more easily accessible by limiting the number of products at one time on said page.

## Validation
On all the inputs, both on user and admin-only pages, there is validation before entry into database.
On the registration page, the order placement, the order update, and admin-only adding new products, categories or authors,
any invalid input leads to a refreshing of the page, with an error message next to the invalid input field, as oppossed to a redirect to an error page.

## Test accounts
For the purposes of testing the application, there are two test accounts made, one with user priviliges and one with admin priviliges.
### Test user
Username: user  
Password: user
### Test admin
Username: admin  
Password: admin
