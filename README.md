# ServerClient_Java

A project we are developing during the coming year in Advanced software development course.
The project is written in Java about generic server-side that given a problem, he could solve it.

## Introduction

As said in the main description, this project is about a general server,
in which the programmer can decide how and what the server will do given a general problem and how he should solve it.

We want to write a general server, a server that can be used again and again in various projects.
To do so, he must make a fundamental and important separation: separating what changes between a project and a project, and what is not.

We will define the functionality of the server through an interface,
and each project can have another class that will implement the same functionality in a different way.
In that way, we kept the **Open / Close principle**.

### Server

We have ```Server``` interface that has quite simple functionality:
* A method that receives a port for listening and its function will be to open the server and wait for clients.
* A method to close the server.

For this project we will use a class called ```MySerialServer``` that will be a type of ```Server```.

### ClinetHandler

Imagine a situation in which the ```MySerialServer``` would also define the client-server call protocol.
In different projects, there may be a different conversations in a different format and with different expectations between the client and the server.
So we wouldn't be able to use this class in other projects.


Therefore, we need to separate the server mechanism implemented in MySerialServer from different forms of conversation with the client.
We will create an interface called ```ClientHandler``` to determine the type of call with the client and its handling,
which leads to only two Server classes that should be done MySerialServer and MyParallelServer,
and each class can inject any desired implementation for ```ClientHandler```.


For example, for every implementation of a ```Server``` we can inject a call of inversion of strings or solving equations.
In the same way if tomorrow we would like to implement additional protocols then we will only need to add the an implementation of ```ClientHandler``` without changing or copying again the code of the various implementations to the ```Server```.

In this method, we maintained both the **Single Responsibility** and **Open / Close** principles.

## Caching
This system has also caching system,
it may take a lot of time to calculate some solutions.
It would be superfluous to calculate a solution for a problem that we have already solved.
Instead, we can save solutions we've already calculated in a file, or a database.
If there is a problem, we will have to check quickly if we have already solved it.
If so, we will extract the solution from the disk instead of calculating it.
At this point, we already understand that there may be several different implementations to save the solutions,
for example in files or in a database. Therefore, we will again implement the same interface use tactic to preserve the various SOLID principles.
 

We will deploy ```CacheManager``` interface to manage the cache for us. with the following functionality:
* Checks whether the solution already exists in the database.
* Extracts the data from the database.
* Saves the solution for the problem.

Currently, only ```FileCacheManager``` is implemented.

## UML

![ServerClient Java UML](/project_uml.png "ServerClient Java UML")

## Running some examples and tests

The specific problem and solution in the project I created is a server that given a matrix is able to solve it and say the cheapest path from point A to point B using **Astar** algorithm.

For example: lets assume we have this matrix:

|  |   |  |  |
| :---: | :---: | :---: | :---: |
| **114** | **93**  | 164 | 123 |
| 109 | **27**  | **40**  | **15**  |
| 156 | 175 | 189 | **5**   |
| 160 | 186 | 153 | **38**  |

If we'll set the start point to be 114 (0,0) and the end point to be 38 (3,3) then the path ( the output ) will be:

Right, Down, Right, Right, Down, Down.


