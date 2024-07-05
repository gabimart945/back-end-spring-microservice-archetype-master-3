# back-end-spring-microservice-archetype
Spring Microservice Archetype

You can deploy the project on Railway using the following button:

[![Deploy on Railway](https://railway.app/button.svg)](https://railway.app/template/JvYvDw?referralCode=jesus-unir)


If you want to deploy this project within an entire Spring microservices ecosystem, you can use the following button:

[![Deploy on Railway](https://railway.app/button.svg)](https://railway.app/template/f6CKpT?referralCode=jesus-unir)

### API Definition

| HTTP method | URI                 | Query Params                                                                                    | Request Body                                                                                                                                                                                                       | Response Body                                                                                                                                                                                                                           | Response HTTP Codes                                                                          |
|-------------|---------------------|-------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------|
| GET         | /hotels             | name, max_price, min_price, max_stars, min_stars, max_opinion, min_opinion, guests, facilities  | N/A                                                                                                                                                                                                                | `{"hotels": [{"id": 1, "name": "Lawrence, Massachusetts, United States", "price": 87, "stars": 3, "opinion": 2, "max_guests": 4, "contact_mail": "greasce39@people.com.cn", "facilities": "gym", "contact_number": "(555) 335-8590"}]}` | 200 OK<br/>400 Bad Request<br/>500 Internal Server Error                                     |
| GET         | /hotels/{hotelId}   | N/A                                                                                             | N/A                                                                                                                                                                                                                | `{"id": 1, "name": "Lawrence, Massachusetts, United States", "price": 87, "stars": 3, "opinion": 2, "max_guests": 4, "contact_mail": "greasce39@people.com.cn", "facilities": "gym", "contact_number": "(555) 335-8590"}`               | 200 OK<br/>404 Not Found<br/>500 Internal Server Error                                       |
| POST        | /hotels             | N/A                                                                                             | `{"name": "Lawrence, Massachusetts, United States", "price": 87, "stars": 3, "opinion": 2, "max_guests": 4, "contact_mail": "greasce39@people.com.cn", "facilities": "gym", "contact_number": "(555) 335-8590"}`   | `{"id": 1, "name": "Lawrence, Massachusetts, United States", "price": 87, "stars": 3, "opinion": 2, "max_guests": 4, "contact_mail": "greasce39@people.com.cn", "facilities": "gym", "contact_number": "(555) 335-8590"}`               | 201 Created<br/>400 Bad Request<br/>500 Internal Server Error                                |
| DELETE      | /hotels/{hotelId}   | N/A                                                                                             | N/A                                                                                                                                                                                                                | N/A                                                                                                                                                                                                                                     | 200 OK<br/>404 Not Found<br/>500 Internal Server Error                                       |
| PATCH       | /hotels/{hotelId}   | N/A                                                                                             | `{"rating": 5}`                                                                                                                                                                                                    | `{"id": 1, "name": "Lawrence, Massachusetts, United States", "price": 87, "stars": 5, "opinion": 2, "max_guests": 4, "contact_mail": "greasce39@people.com.cn", "facilities": "gym", "contact_number": "(555) 335-8590"}`               | 200 OK<br/>404 Not Found<br/>400 Bad Request<br/>404 Not Found<br/>500 Internal Server Error |
| PUT           | /hotels/{hotelId}   | N/A                                                                                             | `{"name": "Lawrence, Massachusetts, United States", "price": 87, "stars": 5, "opinion": 7, "max_guests": 4, "contact_mail": "greasce39@people.com.cn", "facilities": "gym", "contact_number": "(555) 335-8590"}`   | `{"id": 1, "name": "Lawrence, Massachusetts, United States", "price": 87, "stars": 5, "opinion": 7, "max_guests": 4, "contact_mail": "greasce39@people.com.cn", "facilities": "gym", "contact_number": "(555) 335-8590"}`               | 200 OK<br/>404 Not Found<br/>400 Bad Request<br/>500 Internal Server Error                   |
