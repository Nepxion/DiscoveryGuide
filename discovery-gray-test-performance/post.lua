wrk.method = "POST"
wrk.body = '{"userId": "10001","coinType": "GT","type": "2","amount": "5.1"}'
wrk.headers["id"] = "123"
wrk.headers["token"] = "abc"
wrk.headers["Content-Type"] = "application/json"