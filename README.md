# Flight Simulator Control

A project we developed during the in the advanced software development course, which deals with java programming, with emphasis on design patterns and programming principles such as SOLID and GRASP.
As well as object-oriented architectures and the development of JavaFX desktop application.

## Server

We want to write a general server, a server that can be used again and again in various projects.
To do so, he must make a fundamental and important separation: separating what changes between a project and a project, and what is not.

We will define the functionality of the server through an interface,
and each project can have another class that will implement the same functionality in a different way.
In that way, we kept the **Open / Close principle**.

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

### Our Concerete Server
Given a graph, it could solve it,
Using [A-star](https://en.wikipedia.org/wiki/A*_search_algorithm) algorithm ( which is already implemented in this project based on djkistra algorithm using manhattan distance ) or any other search algorithm.

<p align="center">
  <img src="/uml/server_bridgepattern.png" width="600">
</p>
So in our concrete server, given a weighted graph, it will run the search algorithm, and as a output will return the cheapest route of the target.

In addition, we can see that we used the Bridge Design Pattern, we created a separation between the problem, and what solves the problem and so we can solve various problems through different solutions.

The specific problem and solution in the project is that given a matrix is able to solve it and say the cheapest path from point A to point B using **A-star** algorithm as said before.

For example: lets assume we have this matrix:

|  |   |  |  |
| :---: | :---: | :---: | :---: |
| **114** | **93**  | 164 | 123 |
| 109 | **27**  | **40**  | **15**  |
| 156 | 175 | 189 | **5**   |
| 160 | 186 | 153 | **38**  |

If we'll set the start point to be 114 (0,0) and the end point to be 38 (3,3) then the path ( the output ) will be:

Right, Down, Right, Right, Down, Down.



---
##   Interpreter 

As stated at the beginning of the repository, the project is a GUI of a flight simulator by which you can control the plane and get information from it.

And one of its features is running a script, basically a kind of programming language that can run and fly the plane.

As in the following example:

```scala
openDataServer 5400 10
connect 127.0.0.1 5402
var breaks = bind "/controls/flight/speedbrake"
var throttle = bind "/controls/engines/current-engine/throttle"
var heading = bind "/instrumentation/heading-indicator/indicated-heading-deg"
var roll = bind "/instrumentation/attitude-indicator/indicated-roll-deg"
var pitch = bind "/instrumentation/attitude-indicator/internal-pitch-deg"
var rudder = bind "/controls/flight/rudder"
var aileron = bind "/controls/flight/aileron"
var elevator = bind "/controls/flight/elevator"
var alt = bind "/instrumentation/altimeter/indicated-altitude-ft"
breaks = 0
throttle = 1
var h0 = heading
sleep 5000
while alt < 1000 {
	rudder = (h0 - heading)/180
	aileron = - roll / 70
	elevator = pitch / 50
	sleep 150
}

```
For this purpose, we wrote a code reader, an interpreter, which allows you to connect to the simulator, open a server, and run various commands that control the plane and sample its data.
For example:

We see a while loop that will actually take place as long as the plane’s altitude is less than 1000 meters, and the loop content is the acceleration of the plane.
And in this part:
```scala
rudder = (h0 - heading)/180
```
We can see that arithmetic expressions are supported as well, and to interpret them we use [Dijkstra's Shunting Yard algorithm](https://en.wikipedia.org/wiki/Shunting-yard_algorithm).

---

### Command Pattern

<p align="center">
  <img src="/uml/CommandPattern.png" width="600">
</p>

There is also extensive use of different commands, which use different design patterns:
For example, the design pattern we use to implement the parser is Command Pattern - which means that each command in the program will receive a Command object.

It is important that all commands will implement the same interface, because we want them to have a common polymorphic denominator.

Another reason to use the Pattern Pattern template and not simply to use functions, is so we can do the assemblies, for example, a command that holds a lot of commandos, and we actually combined **Command Pattern** with **Composite Pattern**.

So if, for example, we take a look at the loop command or if command, then we can see that each contains a list of commands which in turn can be either a standard command or another list of commands.

---
