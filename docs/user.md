# User API SPEC

## Register USER
Endpoint : POST /api/users

Request Body :
```json
{
  "username" : "esa" ,
  "password" : "rahasia",
  "name" : "Maulana Akbar Esa"
}
```
Response Body (Succes) :
```json
{
  "data" : "accept"
}
```
Response Body (Failed) :
```json
{
  "error" : "Username must be filled"
}
```

## Login USER
Endpoint : POST /api/auth/login

Request Body :
```json
{
  "username" : "esa" ,
  "password" : "rahasia"
}
```
Response Body (Succes) :
```json
{
  "data" : {
    "token" : "INI TOKEN" ,
    "expiredAt" : 2324242323232 // milisecond
    
  }
}
```
Response Body (Failed) :
```json
{
  "error" : "Username or password wrong"
}
```

## Get USER
Endpoint : GET /api/user/current

Request Header : 
- X-API-TOKEN : TOKEN (Mandatory)

Response Body (Succes) :
```json
{
  "data" : {
    "username" : "esa" ,
    "name" : "Maulana Akbar Esa Putra"
    
  }
}
```
Response Body (Failed , 401) :
```json
{
  "error" : "Unauthorize"
}
```

## Update User
Endpoint : PATCH /api/user/current

Request Body :
```json
{
  "name" : "Maulana Akbar Esa Putra" , // put if only want to update name
  "password" : "new password"  // put if only want to update name
}
```
Response Body (Succes) :
```json
{
  "data" : {
    "username" : "esa" , 
    "name" : "Maulana Akbar Esa Putra"
    
  }
}
```
Response Body (Failed , 401) :
```json
{
  "error" : "Unauthorized"
}
```

## Logout USER
Endpoint : DELETE /api/auth/logout

Request Header :
- X-API-TOKEN : TOKEN (Mandatory)

Response Body (Success) :
```json
{
  "data" : "accept"
}
```
