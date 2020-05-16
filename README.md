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
