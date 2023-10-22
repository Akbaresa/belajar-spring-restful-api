# Contact API Spec

## Create Contact
Endpoint : POST /api/contacts

Request Header :
- X-API-TOKEN : TOKEN (Mandatory)


Request Body : 
```json
{
  "firstName" : "Maulana Akbar" ,
  "LastName" : "Esa Putra" ,
  "email" : "esa@example.com",
  "phone" : "085806678"
          
}
```

Response Body (Success):

```json
{
  "data":{
    "id" : "random String",
    "firstName" : "Maulana Akbar" ,
    "LastName" : "Esa Putra" ,
    "email" : "esa@example.com",
    "phone" : "085806678"
  }
}
```

Response Body (Failed):
```json
{
  "errors" : "Email format Invalid , phone format Invalid"
}
```

## Get Contact
Endpoint : GET /api/contacts/{idContact}

Request Header :
- X-API-TOKEN : TOKEN (Mandatory)

Response Body (Success):
```json
{
  "data":{
    "id" : "random String",
    "firstName" : "Maulana Akbar" ,
    "LastName" : "Esa Putra" ,
    "email" : "esa@example.com",
    "phone" : "085806678"
  }
}
```

Response Body (Failed , 404):
```json
{
  "errors" : "contact is not found"
}
```

## Update Contact
Endpoint : PUT /api/contacts/{idContact}

Request Header :
- X-API-TOKEN : TOKEN (Mandatory)


Request Body :
```json
{
  "firstName" : "Maulana Akbar" ,
  "LastName" : "Esa Putra" ,
  "email" : "esa@example.com",
  "phone" : "085806678"
}
```

Response Body (Success):
```json
{
  "data":{
    "id" : "random String",
    "firstName" : "Maulana Akbar" ,
    "LastName" : "Esa Putra" ,
    "email" : "esa@example.com",
    "phone" : "085806678"
  }
}
```

Response Body (Failed):
```json
{
  "errors" : "Email format Invalid , phone format Invalid"
}
```

## Search Contact
Endpoint : GET /api/contacts/

Query Param :
- name: String , contact name , using like query , optional
- phone : String , contact phone , using like query , optional
- email : String , contact email , using like query , optional
- page : Integer , start from 0  // paging ketika kirim page
- size : Integer , default 10  // ukuran per page

Request Header :
- X-API-TOKEN : TOKEN (Mandatory)

Response Body (Success):

```json
{
  "data":[
    {
      "id" : "random String",
      "firstName" : "Maulana Akbar" ,
      "LastName" : "Esa Putra" ,
      "email" : "esa@example.com",
      "phone" : "085806678"
    }
  ] , 
  "paging": {
    "currentPage" : 0,
    "totalPage" : 10 , 
    "size" : 10
  }
}
```

Response Body (Failed):

```json
{
  "data": "Unauthorized"
}
```

## Remove Contact
Endpoint : DELETE /api/contacts/{idContact}

Request Header :
- X-API-TOKEN : TOKEN (Mandatory)

Response Body (Success):
```json
{
  "data" : "accept"
}
```

Response Body (Failed):
```json
{
  "errors" : "contact is not found"
}
```