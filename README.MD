[![Build Status](https://api.travis-ci.com/sergeykin/job4j_chat.svg?branch=master)](https://travis-ci.com/sergeykin/job4j_chat)

curl -i -H 'Content-Type: application/json' -X POST -d '{"name":"room1"}' http://localhost:8080/room/
curl -i -H 'Content-Type: application/json' -X POST -d '{"name":"ADMIN"}' http://localhost:8080/role/
curl -i -H 'Content-Type: application/json' -X POST -d '{"login":"user1","roles":[{"id":"1"}]}' http://localhost:8080/person/
curl -i -H 'Content-Type: application/json' -X POST -d '{"login":"user2","roles":[{"id":"1"}]}' http://localhost:8080/person/
curl -i http://localhost:8080/person/
curl -i -H 'Content-Type: application/json' -X POST -d '{"text":"any text1","room":{"id":"1"},"person":{"id":"1"}}' http://localhost:8080/message/
curl -i -H 'Content-Type: application/json' -X POST -d '{"text":"any text2","room":{"id":"1"},"person":{"id":"2"}}' http://localhost:8080/message/
curl -i -H 'Content-Type: application/json' -X POST -d '{"text":"any text3","room":{"id":"1"},"person":{"id":"2"}}' http://localhost:8080/message/
curl -i http://localhost:8080/message/
