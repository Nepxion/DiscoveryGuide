wrk.method="POST"
wrk.body=""
wrk.headers["app"]="demo-service"
wrk.headers["content-type"]="application/json"
wrk.headers["token"]=""

function request()
  return wrk.format("GET","/demo/ip",wrk.headers)
  -- return wrk.request()
end

function response(status,headers,body)
  if status ~= 200 then
    print(body)
  end
end