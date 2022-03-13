# Twenty-Questions-Game-Tree
This application uses tree structure and moves around nodes to answer questions. 
You can create your own game with Creator file.
You can play pre-created games by placing "questions.txt" in the folder.
These 3 java codes should be put in same folder.

Creator App Commands:
help                 Shows commands.
save                 Saves current state.
exit                 Terminates program.
current              Shows current node's coordinates (according to ancestor), parent question, input (key) answer, whether it is a guess or not and context of the node.
location             Shows current node's coordinates according to ancestor node.
parent               Makes current node parent of current node.
ancestor             Makes current node first parent (ancestor) node.
goto [coordinates]   Goes to node which has given coordinates according to this node. Ex: goto 1 0 2
gotoan [coordinates] Goes to node which has given coordinates according to first parent (ancestor) node. Ex: goto 1 0 2
create               Creates a new child of this node and makes the current node new node.
change               Changes properties of the current node.
remove               Removes this node and its childs and makes current node parent of current node.
Commands below shows coordinates according to current node.
deadends a           Shows index, coordinates and questions of deadend nodes, which questions don't have answers
deadends [index]     Goes to deadend index according to deadends function data. Ex: deadends 2
oneanswers a         Shows question which has only one answer with their index, coordinates, question and possible answer.
oneanswers [index]   Goes to oneanswer index according to oneanswers function data. Ex: oneanswers 3
guess a              Shows index, coordinates, parent question, input answer and guess of guess nodes
guess [index]        Goes to guess index according to guess function data. Ex: guess 3
all a                Shows index, coordinates, node type and context of all childs of that node.
all [index]          Goes to index according to all function data. Ex: all 9


Launcher App Commands: 
return               Moves location to previous (parent) node
coordinates          Shows coordinates of node you located
exit                 Terminates the program


questions.txt File Format: (It is not required to know that because it can be used creator to create game.)
    //starts after begin
    //there are no commas, there are spaces between inputs
    //two stars to end string inputs 
    //create objects by firstly creating their parents (Upper nodes)
    //first node starts with coordinates -1
    //first node format is different: -1 question (example:-1 is this a mineral, vehicle or tool?)
    //Coordinates(as numbers with spaces between them), Which answer as an input will send to this node(String), Is this an guess(boolean(true, false)), Context of the node (Question or guess) (String)
    //example: 0 2 1 bigger** false Is it sharp?
    //don't use return, coordinates and exit as an input becase return sends to previous node on the program.
   
