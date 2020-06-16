# This is a basic voting web server exposing a RestApi 

## The serve contains 2 Routs

#### /vote

The route receives ```POST``` requests with a body that contains 1 candidate name.

#### /candidates

The rout returns a list of all candidates and their scores via a ```GET``` request.