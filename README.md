# Flight Simulator Control 

[![Presentation Project](/uml/example.png)](https://youtu.be/rWe9220DOY8)

This is a project we developed during our advanced software programming course in our 2nd year.
This project helped us to gain a higher level of knowledge in programming, with emphasis on design patterns and programming principles such as SOLID and GRASP, and finally developing our own JavaFX desktop application.

## Server

In this section we wrote a general server, which can be used over and over again in various projects.
In order for the server to be re-usable, there must be a seperation between the server's functionality and the rest of the code. 

Therefore, we defined the functionality of the server as an interface,
and each project can have different classes that will implement the same functionality in different ways.
Thus, the **Open / Close principle** has been applied.

Now the ```Server``` interface has a quite simple functionality:
* A method that receives a port for listening and its function will be to open the server and wait for clients.
* A method to close the server.

For this project we will use a class called ```MySerialServer``` that will be a type of ```Server```.

### ClientHandler

Imagine a situation in which the ```MySerialServer``` class would also define the client-server call protocol.
In different projects, there might be different conversations in different formats and with different expectations between the client and the server.
Therefore, we won't be able to use this class in other projects. 

To solve that issue, we had to separate the server mechanism implemented in ```MySerialServer``` from different forms of conversation with possible clients.
For that reason we created an interface called ```ClientHandler``` to determine the type of call with the client and its handling.
Now ```MySerialServer``` class can inject any desired implementation for ```ClientHandler```.


For example, for every implementation of a ```Server``` we can inject a call of inversion of strings or solving equations.
In the same way, if one day we would like to implement additional protocols then we will only need to add the implementation of ```ClientHandler``` without changing or copying again the code of the various implementations to the ```Server```.

In this method, we maintained both the **Single Responsibility** and **Open / Close** principles.

## Caching

The project also has a caching system,
for it might take a lot of time to calculate some solutions.
It would be redundant to calculate a solution for a problem that we already solved.
Instead, we can save solutions that were already calculated in an external file, or a database.
Upon receiving a new problem, we will first check the cache to see if we have already solved it.
If so, we will extract the solution from the disk instead of calculating it.

 
We created the ```CacheManager``` interface to manage the cache for us, with the following functionalities:
* Checks whether the solution already exists in the database.
If a solution exists : * Extracts the data from the database.
Else : * Saves the solution for the problem.

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
### Interpreter stages

<p align="center">
  <img src="/uml/interpreter.png" width="600">
</p>

So this script-reader works in a very similar way to the interpreter of a real programming language.

And the first stage that happens in the interpretation process is Lexer

That actually takes the string as it is, and converts it to logical distribution according to commands and parameters that can run later on with a Scanner.

The next stage that is supposed to happen to is the parser stage, which actually begins to convert the "array" created by the Lexer into commands and to execute them.

However, since this script is supposed to run an airplane, we don't want the interpreter to run the simulator, connect to the server and start running the code and in the middle of the script we will find that there are syntactic errors or incorrect entries.

So, before we start running the commands, we will make sure that a pre-parser will pass the initial scan on the script and check for Syntax errors, such as incorrect parameters or irrational values.

Since we are already running an initial scan to check the integrity of the code, we won't run again the same code and do exactly the same operation in the parser stage, so in the Pre-Parser phase, in addition to testing the program we will also maintain a list of commands.

So in the parser phase, we can run on this list instead of running over each cell in the array and reinterpreting the different commands.

---
## MVVM Architecture

<p align="center">
  <img src="/uml/mvvm.png" width="600">
</p>

So as we said, we chose to use the **MVVM architecture**.

We have the View layer that is responsible for the presentation, for example the 
input from the user, and he is also responsible for producing the graphic and also has the code-
behind - for example, functions that are activated when we press a button. Which actually called
event-oriented programming.

* **Model** – Responsible for our business logic, such as algorithms and data access
* **View Model** – It passes commands from the View to the Model, and its purpose is to
separate the View from the Model.
* **Data Binding** – We can wrap variables such as those in the View, and then when we change
something in the text, it will automatically changed in the ViewModel.

Actually, for all the MVVM architecture to work, we'll have to wrap the different components
together. And this is done through the Observer Pattern, actually bind
the different components together.

```java
   FXMLLoader loader = new FXMLLoader(getClass().getResource("Flight.fxml"));
        Parent root = loader.load();
        FlightController ctrl = loader.getController();
        ViewModel viewModel=new ViewModel();
        Model model=new Model();
        model.addObserver(viewModel);
        viewModel.setModel(model);
        viewModel.addObserver(ctrl);
        ctrl.setViewModel(viewModel);
        primaryStage.setTitle("Flight Gear Simulator");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            DisconnectCommand command=new DisconnectCommand();
            String[] disconnect={""};
            command.executeCommand(disconnect);
            AutoPilotParser.thread1.interrupt();
            model.stopAll();
            System.out.println("bye");
        });
```

So this was our application with emphasis on many programming principles.
