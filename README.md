![Parallel Mergesort Client Logo](https://github.com/roshanlas/Parallel-Mergesort-Client/blob/master/app/src/main/res/drawable/parallellogoclient1.png?raw=true)
# Android Parallel Computing Mergesort Client

## Synopsis
Android application (Client Module) to simulate Parallel Computing using several android devices. This application makes use of the Merge Sort algorithm for simulation purpose.

For Server Module Check Repo: [https://github.com/roshanlas/Parallel-Mergesort-Server](https://github.com/roshanlas/Parallel-Mergesort-Server)

The server application distributes the processing task among all connected Client devices. In case of multiple Clients performing parallel computing, the Server does the final Merge operation.

## Motivation
This application was developed for learning purposes. This technique, if implemented at the kernal level with a faster communication mechanism, will help to distribute the processing of an android device among several others.

## Installation
To simulate parallel computing:
* Run the ParallelServer application on one device.
* Run the ParallelClient application on other devices. The server can support upto 10 clients.
* Make sure that both the Client and Server devices are on the same network.
* Enter the Server's IP address and port number in the client devices and press Connect.
* Generate a Random Array of integers on the server device and press Execute.
