# book.store

Online Book Store

1- Creat book 

`POST Method -  localhost:9090/api/newBook` check is found in database:

`
{
    "name": "It Starts with Us",
    "description": "It Starts With Us is told from the alternating perspectives of two main characters, Atlas Corrigan and Lily Bloom.
    Lily deals with her chaotic life as a floral shop owner, single mom and co-parent with her ex-husband — who can't seem to grasp the 
    fact that they will never get back together.",
    "author": "Colleen Hoover",
    "classification_id": 1,
    "price": 150,
    "availableQuantity":100,
    "isbn": "EV2D96s2S"
}
`

2- Get Book By Id:

`GET Method - localhost:9090/api/books`



3- Delete Book By Id:

`DELETE Method  - localhost:9090/api/book/1`  check id is found first


4- Edit Book By Id

`PUT Method - localhost:9090/api/book/9` edit book data

`
{
     "name": "No Plan B: A Jack Reacher Novel",
    "description": "No Plan B: A Jack Reacher Novel",
    "author": "Andrew Child and Lee Child",
    "classification_id": 1,
    "price": 150,
    "availableQuantity":100,
    "isbn": "EV2D96s2S"
}
`





