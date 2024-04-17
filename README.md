# coding challenge solution

When started the application runs on port 8080

Sample curl --location --request GET 'http://localhost:8080/repositories/popular?limit=10&created_at=2024-04-16&language=java'

Params:

- ```created_at``` is the date of creation onward
- ```limit``` This is an optional field that default to 10, it is the number of repositories to be return max is 100
- ```language``` is the  language of the code in the repositories.


Assumptions made in this development.
1. I decided to use the latest RestClient in springboot 3.2 for the api call
2. I tried to be more concise, but I didn't want to leave the Client Service untested, so I add mockwebserver dependency to mock api interaction.
3. I added test for each component (controller, service, client).

Improvements.
1. For scalability purpose we can decide to use Reactive (project-reactor) by return Flux and call the endpoint in a reactive way so that threads are not blocked during the I/O operation.
2. Integration test can be added (we could run a docker instance with testcontainer to give us the dependency) but I didn't want to complicate things.


