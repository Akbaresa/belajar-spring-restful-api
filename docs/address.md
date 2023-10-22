# Address API Spec

## Create Address
Endpoint : POST /api/contacts/{idContact}/addresses

Request Header : 
- X-API-TOKEN : TOKEN (Mandatory)

Request Body :
```json
{
  "street" : "jalan mu",
  "city" : "surabaya" , 
  "province" : "jatim" , 
  "country" : "negara" ,
  "postalCode" : "6114"
}
```
Response Body (Success) :

```json
{
  "data":{
    "id" : "random String",
    "street" : "jalan mu",
    "city" : "surabaya" ,
    "province" : "jatim" ,
    "country" : "negara" ,
    "postalCode" : "6114"
  }
}
```

Response Body (Failed) :

```json
{
  "errors": "Contact is not found"
}
```

## Update Address

Endpoint : PUT /api/contacts/{idContact}/addresses/{idAddresses}
 
Request Header :
- X-API-TOKEN : TOKEN (Mandatory)

Request Body :
```json
{
  "street" : "jalan mu",
  "city" : "surabaya" ,
  "province" : "jatim" ,
  "country" : "negara" ,
  "postalCode" : "6114"
}
```
Response Body (Success) :
```json
{
  "data":{
    "id" : "random String",
    "street" : "jalan mu",
    "city" : "surabaya" ,
    "province" : "jatim" ,
    "country" : "negara" ,
    "postalCode" : "6114"
  }
}
```

Response Body (Failed) :
```json
{
  "errors": "Address is not found"
} 
```

## Get Address

Endpoint : GET /api/contacts/{idContact}/addresses/{idAddress}

Request Header :
- X-API-TOKEN : TOKEN (Mandatory)

Response Body (Success) :
```json
{
  "data":{
    "id" : "random String",
    "street" : "jalan mu",
    "city" : "surabaya" ,
    "province" : "jatim" ,
    "country" : "negara" ,
    "postalCode" : "6114"
  }
}
```

Response Body (Failed) :
```json
{
  "errors": "Address is not found"
} 
```

## Remove Address

Endpoint : DELETE /api/contacts/{idContact}/addresses/{idAddress}

Request Header :
- X-API-TOKEN : TOKEN (Mandatory)

Response Body (Success) :
```json
{
  "data" : "accept"
}
```
 
Response Body (Failed) :

```json
{
  "errors": "Address is not found"
}
```

## List Address

Endpoint : GET /api/contacts/{idContact}/addresses

Request Header :
- X-API-TOKEN : TOKEN (Mandatory)

Response Body (Success) :
```json
{
  "data":[
    {
      "id" : "random String",
      "street" : "jalan mu",
      "city" : "surabaya" ,
      "province" : "jatim" ,
      "country" : "negara" ,
      "postalCode" : "6114"
    } 
  ]
}
```

Response Body (Failed) :
```json
{
  "errors": "Contact is not found"
} 
```
