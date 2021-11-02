### Description:

The initail Performer code only has one function for adding strings to an array: 

## Protocol

### Requests:

request: { "selected": <int: 1=add, 2=remove, 3=display, 4=count, 5=reverse,
0=quit>, "data": <thing to send>}

  data <string>: add
  data <int> pop
  data <int> <int> switch but send as String

### Responses:

sucess response: {"type": <"hello", add",
"remove", "display", "count", "switch", "quit"> "data": <thing to return> }

type <String>: echoes original selected from request
data <string>: add = new list, pop = new list, display = current list, count = num elements, switch = switch two elements


error response: {"type": "error", "message"": <error string> }


### How to run the program:

The program is defaulted to:
gradle runTask1
gradle runTask2
gradle runTask3 -Pconnections=n, with n being the number of connections at a time
gradle runClient

## How task 2 and task 3 work:

Task 2 and 3 both use Atomic integer to help run the multithreaded server. Each thread communicates
with eachother through the Atomic integer and is able to see how many clients are on at once. 
In task3, the server blocks connections based on the number of connections specified.

### Terminal:

Base Code, please use the following commands:

```
    For Server, run "gradle runServer -Pport=9099 -q --console=plain"
```
```   
    For Client, run "gradle runClient -Phost=localhost -Pport=9099 -q --console=plain"
```   



