Matei Tudor-Andrei - 324CA

# Proiect Etapa 1 - J. POO Morgan Chase & Co.

## Short description of the app

The application emulates the "back-end" of a simple banking application, giving the results
of a series of commands in JSON format.

The input is given in the JSON format and the results will be printed into files in the same format. 
For the managing the input and printing the output, the Object Mapper, ArrayNode, ObjectNode and ObjectWritter classes from the com.fasterxml.jackson.databind library were used. The class that represents objects that will be printed will also have a method which will return an ObjectNode that contains the needed stats which will later be added to the output ArrayNode.

## Classes

### Account Package

Contains the following classes:

#### User

- stores information about users (accounts, name, email, transactions, aliases)
- is also used to print the information about the user with  the print method

#### Account

- used to store information about accounts (IBAN, owner, currency, balance, cards, transactions)
- has methods to add/remove cards from the list of cards
- used to get payments and transactions between given timestamps
- also has a setInterestRate method that returns -1
##### ClassicAccount
- extends the Account class
##### SavingsAccount
- extends the Account class and implements the setInterestRate to signal that the command can be applied
on that account


#### Card
- stores information about the card: status, number and corresponding account
- has method to print the card; to use the card, which extracts money from the account
- also has an update() method which will check if the current status of the card is still correct

##### OneTimeCard
- extends the Card class and it represent a card that can only be used once
- after it's used it will get a new card number
- the need to change the card number is signaled in the use() method, where the status becomes "mustBeReplaced"


###  Commands package
This is where the command pattern was used, but without an invoker and a history of commands, 
because they were not needed.
#### Command interface
- has the void execute() method, which will be used by the command classes to implement the action
- the other classes from this package implement this interface, which will containt the implementation
of each command from the input, except the next class
#### CommandHandler class
- has a method to get the right type of instace for the command, then executes it
- the method used to get the right type of command implements the Factory pattern, with a switch, 
returning a new instance of a class that implements the Command interface, based on input

### Errors package
- contains a Log class that implements the Builder pattern
- has a method print() that is used to add information about the error in the output
- the method check which fields were initialiased and adds them to output

### Transactions package
- has multiple classes that represents different type of transactions, which will be stored in the 
Account class and in the User class
- each class has a print method() that will be used when iterating through arrays of transaction
to print them with ease
### System package
#### Converter class
- implements the Singleton pattern, since we only need one instance of the converter
- it uses a Graph with edges, both classes defined in the graph package
- it has 2 important methods, a dfs() method that does a Depth-First Search through the Currency
Graph and returns the conversion rate between 2 given currencies, recursively
- the other method defines a set of visited nodes and returns the conversion rate given by the DFS
#### SystemManager class
- contains an ArrayList of Users, 3 HashMaps, one to associate 	an IBAN string to an Account 
instance, another one to associate an email with an User instance and the last one to associate
a card number to a Card instance and a Converter (which will be the only instance of the Converter)
- it contains only one method, run(), that extracts the Users and the exchange rates from the input 
and adds them in the ArrayList of Users and in the currency graph, respectively. Then it creates
a CommandHandler instance, which will be used to execute every command from the input. At last,
it will reset the converter for the next test. 

 