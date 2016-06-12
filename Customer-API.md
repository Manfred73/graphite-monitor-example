#Customer API
The customer API offers the following actions:

 - Retrieve (GET) an existing customer by its id.
 - Retrieve (GET) customers by their first name.
 - Retrieve (GET) customers by their last name.
 - Retrieve (GET) customers by their first name and last name.
 - Retrieve (GET) all customers.
 - Create (POST) a new customer.
 - Update (PUT) a customer by its id.
 - Remove (DELETE) a customer by its id.

##Contents

1. [Data format of the Customer](#data-format-of-the-customer)
2. [The HTTP interface](#the-http-interface)
3. [Retrieval of a customer](#retrieval-of-a-customer)
4. [Retrieval of customers by first name](#retrieval-of-customers-by-first-name)
5.  [Retrieval of customers by last name](#retrieval-of-customers-by-last-name)
6. [Retrieval of customers by first name and last name](#retrieval-of-customers-by-first-name-and-last-name)
7.  [Retrieval of all customers](#retrieval-of-all-customers)
8. [Creating a new customer](#creating-a-new-customer)
9. [Updating an existing customer](#updating-an-existing-customer)
10. [Deleting an existing customer](#deleting-an-existing-customer)
11. [Validations](#validations)



###Data format of the Customer
The customer API provides the data as a JSON document.

    {
       "firstName": "Gage",
       "lastName": "Oscar"
    }

The following applies:

 - The order of attributes can be arbitrary.
 - The attribute definitions are case-sensitive.

The response is also provided as a JSON document and can either be:

a single customer:

    {
       "firstName": "Gage",
       "lastName": "Oscar",
       "_links": {
          "self": {
             "href": "http://localhost:8080/1"
          }
       }
    }

or multiple customers:

    {  
       "customers": [
          {
             "firstName": "Gage",
             "lastName": "Oscar",
             "_links": {
                "self": {
                   "href": "http://localhost:8080/1"
                }
             }
          },
          {
             "firstName": "Uriel",
             "lastName": "Cruz",
             "_links": {
                "self": {
                   "href": "http://localhost:8080/2"
                }
             }
          },
          {
             "firstName": "Larissa",
             "lastName": "Joy",
             "_links": {
                "self": {
                   "href": "http://localhost:8080/300"
                }
             }
          }
       ]
    }

###The HTTP interface
The HTTP interface of the customer API is accessible under the following base URL: http://localhost:8080/

####Content-Type
The interface only delivers data with Content-Type "application/json". The following headers should be set in every request:

| Request Header Name | Header Value                   |
|---------------------|--------------------------------|
| Accept              | application/json               |
| Content-Type        | application/json;charset=UTF-8 |

###Retrieval of a customer
An existing customer can be retrieved by its id using the HTTP-GET method. It will be returned as a JSON document. The url template for retrieving an existing customer is as follows:

    http://localhost:8080/customer/{id}

A succussful retrieval will result in a response with HTTP statuscode 200 OK.

If the customer could not be found with the id provided, a response with HTTP statuscode 404 NOT FOUND will be returned.

The body of the response contains the actual data in JSON format.

Added to the response body will also be the url (_links > self > href) for the retrieved customer.

####**GET request example**

    GET http://localhost:8080/customer/1
    Accept: application/json
    Content-Type: application/json;charset=UTF-8 

####**GET response example**

200 OK
Content-Type: application/json;charset=UTF-8 
Content-Length: 100

    {
      "firstName": "Gage",
      "lastName": "Oscar",
      "_links": {
         "self": {
            "href": "http://localhost:8080/1"
         }
      }
    }

###Retrieval of customers by first name
Customers can be retrieved by their first name using the HTTP-GET method. All customers with the matching first name will be returned as a JSON document. The url template for retrieving customers by their first name is as follows:

    http://localhost:8080/customers/firstName/{firstName}

A succussful retrieval will result in a response with HTTP statuscode 200 OK.

If no customers could be found with the first name provided, a response with HTTP statuscode 404 NOT FOUND will be returned.

The body of the response contains the actual data in JSON format.

Added to the response body for each customer there will also be the url (_links > self > href).

####**GET request example**

    GET http://localhost:8080/customers/firstName/Addison
    Accept: application/json
    Content-Type: application/json;charset=UTF-8 

####**GET response example**

200 OK
Content-Type: application/json;charset=UTF-8 
Content-Length: 411

    {
       "customers": [
       {
          "firstName": "Addison",
          "lastName": "Merrill",
          "_links": {
             "self": {
                "href": "http://localhost:8080/7"
             }
          }
       },
       {
          "firstName": "Addison",
          "lastName": "Leroy",
          "_links": {
             "self": {
                "href": "http://localhost:8080/41"
             }
          }
       },
       {
          "firstName": "Addison",
          "lastName": "Galvin",
          "_links": {
             "self": {
                "href": "http://localhost:8080/281"
             }
          }
       }]
    }

###Retrieval of customers by last name
Customers can be retrieved by their last name using the HTTP-GET method. All customers with the matching last name will be returned as a JSON document. The url template for retrieving customers by their last name is as follows:

    http://localhost:8080/customers/lastName/{lastName}

A succussful retrieval will result in a response with HTTP statuscode 200 OK.

If no customers could be found with the last name provided, a response with HTTP statuscode 404 NOT FOUND will be returned.

The body of the response contains the actual data in JSON format.

Added to the response body for each customer there will also be the url (_links > self > href).

####**GET request example**

    GET http://localhost:8080/customers/lastName/Alvin
    Accept: application/json
    Content-Type: application/json;charset=UTF-8 

####**GET response example**

200 OK
Content-Type: application/json;charset=UTF-8 
Content-Length: 411

    {
       "customers": [
       {
          "firstName": "Marvin",
          "lastName": "Alvin",
          "_links": {
             "self": {
                "href": "http://localhost:8080/16"
             }
          }
       },
       {
          "firstName": "Dominic",
          "lastName": "Alvin",
          "_links": {
             "self": {
                "href": "http://localhost:8080/260"
             }
          }
       },
       {
          "firstName": "Colby",
          "lastName": "Alvin",
          "_links": {
             "self": {
                "href": "http://localhost:8080/276"
             }
          }
       }]
    }

###Retrieval of customers by first name and last name
Customers can be retrieved by their first name and last name using the HTTP-GET method. All customers with the matching first name and last name will be returned as a JSON document. The url template for retrieving customers by their first name and last name is as follows:

    http://localhost:8080/customers/firstName/{firstName}/lastName/{lastName}

A succussful retrieval will result in a response with HTTP statuscode 200 OK.

If no customers could be found with the last name provided, a response with HTTP statuscode 404 NOT FOUND will be returned.

The body of the response contains the actual data in JSON format.

Added to the response body for each customer there will also be the url (_links > self > href).

####**GET request example**

    GET http://localhost:8080/customers/firstName/Carlos/lastName/Lucius
    Accept: application/json
    Content-Type: application/json;charset=UTF-8 

####**GET response example**

200 OK
Content-Type: application/json;charset=UTF-8 
Content-Length: 146

    {
       "customers": [
       {
          "firstName": "Carlos",
          "lastName": "Lucius",
          "_links": {
             "self": {
                "href": "http://localhost:8080/18"
             }
          }
       }]
    }

###Retrieval of all customers
All existings customers can be retrieved by using the HTTP-GET method. All customers will be returned as a JSON document. The url template for retrieving all customer is as follows:

    http://localhost:8080/customers

A succussful retrieval will result in a response with HTTP statuscode 200 OK.

If no customers could be found with the last name provided, a response with HTTP statuscode 404 NOT FOUND will be returned.

The body of the response contains the actual data in JSON format.

Added to the response body for each customer there will also be the url (_links > self > href).

####**GET request example**

    GET http://localhost:8080/customers
    Accept: application/json
    Content-Type: application/json;charset=UTF-8 

####**GET response example**

200 OK
Content-Type: application/json;charset=UTF-8 
Content-Length: 39451

    {
       "customers": [
       {
          "firstName": "Gage",
          "lastName": "Oscar",
          "_links": {
             "self": {
                "href": "http://localhost:8080/1"
             }
          }
       },
       {
          "firstName": "Uriel",
          "lastName": "Cruz",
          "_links": {
             "self": {
                "href": "http://localhost:8080/2"
             }
          }
       },
       ..........
       ..........
       ..........
       {
          "firstName": "Larissa",
          "lastName": "Joy",
          "_links": {
             "self": {
                "href": "http://localhost:8080/300"
             }
          }
       }]
    }
    
###Creating a new customer

A new customer can be stored via an HTTP-POST request. The url template for creating a newcustomer is as follows: 

    http://localhost:8080/customer

The data will be passed on in the body of the POST request as a JSON document.

The following rules apply when evaluating the request on the server:

 - Unknown attributes will be ignored.
 - "id" cannot be set. When it is set in the body of the request, a response with HTTP statuscode 400
   BAD REQUEST will be returned. A successful creation will result in a response with HTTP statuscode 201 CREATED.

The body of the response contains the actual data in JSON format.

In the HTTP header "Location" you can find the url for the newly created customer. Added to the response body will also be the url (_links > self > href) for the newly created customer.

####**POST request example**

    POST http://localhost:8080/customer
    Accept: application/json
    Content-Type: application/json;charset=UTF-8 
    
    {
       "firstName": "John",
       "lastName": "Doe"
    }

####**POST response example**

    201 CREATED
    Location: http://localhost:8080/customer/301
    Content-Type: application/json;charset=UTF-8
    Content-Length: 109
    
    {
      "firstName": "John",
      "lastName": "Doe",
      "_links": {
         "self": {
            "href": "http://localhost:8080/customer/301"
         }
      }
    }

###Updating an existing customer
An existing customer can be updated by its id using the HTTP-PUT method. The url template for updating an existing customer is as follows:

    http://localhost:8080/customer/{id}

The data to be passed on in the body of the PUT request as a JSON document will be the same as for the POST request.

The following rules apply when evaluating the request on the server:

 - Unknown attributes will be ignored.
 - "id" cannot be set in the body, but can only be passed on in the PUT url. When it is set in the body of the request, a response with HTTP statuscode 400 BAD REQUEST will be returned. A successful update will result in a response with HTTP statuscode 200 OK.

If the customer could not be found with the id provided, a response with HTTP statuscode 404 NOT FOUND will be returned.

The body of the response contains the actual data in JSON format.

####**PUT request example**

PUT http://localhost:8080/customer/301
Accept: application/json
Content-Type: application/json;charset=UTF-8 

    {
       "firstName": "Jane",
       "lastName": "Doe"
    }

####**PUT response example**

    200 OK
    Content-Type: application/json;charset=UTF-8
    Content-Length: 100
    
    {
      "firstName": "Jane",
      "lastName": "Doe",
      "_links": {
         "self": {
            "href": "http://localhost:8080/customer/301"
         }
      }
    }

###Deleting an existing customer
An existing customer can be deleted by its id using the HTTP-DELETE method. The url template for deleting an existing customer is as follows:

    http://localhost:8080/customer/{id}

A succussful delete will result in a response with HTTP statuscode 204 NO CONTENT.

If the customer could not be found with the id provided, a response with HTTP statuscode 404 NOT FOUND will be returned.

The body of the response will be empty.

####**DELETE request example**

DELETE http://localhost:8080/301
Accept: application/json
Content-Type: application/json;charset=UTF-8 

####**DELETE response example**

204 NO CONTENT
Content-Type: application/json;charset=UTF-8 

##Validations

The following validations are done:

 - For POST and PUT requests, no id is allowed in the request body.

If the validation fails an HTTP response with statuscode 400 BAD REQUEST will be returned in the response. Currently no specific validation messages will be returned in the response body.
