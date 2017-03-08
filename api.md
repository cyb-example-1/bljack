# REST API

The REST API to the example app is described below.

## Session login

### Request

`POST /session/login`

    curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X POST -d "{\"username\":\"test\",\"password\":\"test\"}"  http://localhost:8080/session/login

### Response

	HTTP/1.1 200
	X-Auth-Token: 56c3790f-4b82-4de3-a7b3-b7ff2e8cf53e
	Content-Type: application/json;charset=UTF-8
	Content-Length: 47
	
	{"errors":null,"warnings":null,"messages":null}

## Session logout

### Request

`POST /session/logout`

    curl -i -H "Accept: application/json" -H "Content-Type: application/json" -H "X-Auth-Token: 56c3790f-4b82-4de3-a7b3-b7ff2e8cf53e" -X POST -d ""  http://localhost:8080/session/logout

### Response

	HTTP/1.1 200
	Content-Type: application/json;charset=UTF-8
	Content-Length: 47
	
	{"errors":null,"warnings":null,"messages":null}

## Get user data

### Request

`GET /user/get`

    curl -i -H "Accept: application/json" -H "Content-Type: application/json" -H "X-Auth-Token: 56c3790f-4b82-4de3-a7b3-b7ff2e8cf53e" -X GET   http://localhost:8080/user/get

### Response

	HTTP/1.1 200
	Content-Type: application/json;charset=UTF-8
	Transfer-Encoding: chunked

	
	{"errors":null,"warnings":null,"messages":null,"object":{"id":1,"username":"test","currency":null,"balance":7795.0,"game":null},"user":{"id":1,"username":"test","currency":null,"balance":7795.0,"game":null}}


## Update user balance

### Request

`POST /user/balance`

    curl -i -H "Accept: application/json" -H "Content-Type: application/json" -H "X-Auth-Token: 56c3790f-4b82-4de3-a7b3-b7ff2e8cf53e" -X POST -d "{\"balanceChange\":100}"  http://localhost:8080/user/balance

### Response

	HTTP/1.1 200
	Content-Type: application/json;charset=UTF-8
	Transfer-Encoding: chunked
	
	{"errors":null,"warnings":null,"messages":null,"object":{"id":1,"username":"test","currency":null,"balance":7775.0,"game":null},"user":{"id":1,"username":"test","currency":null,"balance":7775.0,"game":null}}

.........................
.........................	