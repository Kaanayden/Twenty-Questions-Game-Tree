import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TwentyQuestionsCreator {

    

    static void writeToFile( String text ) {
 
        try {
            File myObj = new File("questions.txt");
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
            } else {
              //System.out.println("File already exists.");
            }
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

          try {
            FileWriter myWriter = new FileWriter("questions.txt");
            myWriter.write(text);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }


    }


    static int[] coordinatesScanner( String remainingInput ) {
        Scanner inputScan = new Scanner( remainingInput );
        //inputScan.useDelimiter("\n");

            //takes coordinates
            ArrayList<Integer> coordinatesList = new ArrayList<Integer>(); 

            while( inputScan.hasNextInt() ) {
                coordinatesList.add( inputScan.nextInt() );
            }

            int coordinates[] = new int[coordinatesList.size()];
            for(int i = 0; coordinatesList.size() > i; i++) {
                coordinates[i] = coordinatesList.get(i);
            }
            
            return coordinates;

    }

    static void printCurrentAttributes(QuestionNode current, QuestionNode ancestorNode) {

        System.out.print("Coordinates:");
        System.out.print(current.getCoordinatesAsText(ancestorNode));
        System.out.print("\n");

        if(current != ancestorNode) System.out.println("Parent Question: " + current.parent.outputContext);
        System.out.println("Input Answer: " + current.inputAnswer);
        System.out.println("Node Type: " + current.nodeType());
        System.out.println("Context of the node: " + current.outputContext);

        String[] possibleAnswers = current.getPossibleAnswers();
        if(possibleAnswers.length > 0) {
            System.out.print("Possible answers: ");
            for(int i = 0; i < possibleAnswers.length; i++) {
                if(i != possibleAnswers.length - 1) System.out.print(possibleAnswers[i] + ", ");
                else System.out.print(possibleAnswers[i] + "\n");
            }
        }
    }

    public static void main( String[] args ) {

        QuestionNode ancestorNode;
        Scanner inputScan = new Scanner( System.in );
        String input;
        System.out.println("Welcome to the Twenty Questions Game File Creator app!");
        File myObj = new File("questions.txt");
        if( myObj.exists() ) {
            System.out.println("There is already a *questions.txt* file. Do you want to continue from that file? (y/n)");
            while(true) {
                input = inputScan.next();
                if(input.equalsIgnoreCase("y")) {
                    ancestorNode = TwentyQuestionsLauncher.getQuestions(); //to open save "questions.txt"
                    break;
                } else if(input.equalsIgnoreCase("n")) {
                    ancestorNode = new QuestionNode();
                    ancestorNode.isThisAGuess = false;
                    break;
                } else {
                    System.out.println("This is not a valid answer. Please type y or n .");
                }
        }

        } else {
            ancestorNode = new QuestionNode();
            ancestorNode.isThisAGuess = false;
        }

        System.out.println("Creator has started succesfully. You are in the ancestor node now. You can learn commands by typing \"help\".");
        QuestionNode current = ancestorNode;

        while(true) {

            input = inputScan.next();
            System.out.println("------------------------------------");

            if( input.equalsIgnoreCase("help") ) {

                System.out.println("Commands:");
                System.out.printf("%-20s %s \n", "help", "Shows commands.");
                System.out.printf("%-20s %s \n", "save", "Saves current state.");
                System.out.printf("%-20s %s \n", "exit", "Terminates program.");
                System.out.printf("%-20s %s \n", "current", "Shows current node's coordinates (according to ancestor), parent question, input (key) answer, whether it is a guess or not and context of the node.");
                System.out.printf("%-20s %s \n", "location", "Shows current node's coordinates according to ancestor node.");
                System.out.printf("%-20s %s \n", "parent", "Makes current node parent of current node.");
                System.out.printf("%-20s %s \n", "ancestor", "Makes current node first parent (ancestor) node.");
                System.out.printf("%-20s %s \n", "goto [coordinates]", "Goes to node which has given coordinates according to this node. Ex: goto 1 0 2");
                System.out.printf("%-20s %s \n", "gotoan [coordinates]", "Goes to node which has given coordinates according to first parent (ancestor) node. Ex: goto 1 0 2");
                System.out.printf("%-20s %s \n", "create", "Creates a new child of this node and makes the current node new node.");
                System.out.printf("%-20s %s \n", "change", "Changes properties of the current node.");
                System.out.printf("%-20s %s \n", "remove", "Removes this node and its childs and makes current node parent of current node."); //first node can't be removed
                
                System.out.println("Commands below shows coordinates according to current node.");
                System.out.printf("%-20s %s \n", "deadends a", "Shows index, coordinates and questions of deadend nodes, which questions don't have answers");
                System.out.printf("%-20s %s \n", "deadends [index]", "Goes to deadend index according to deadends function data. Ex: deadends 2");
                System.out.printf("%-20s %s \n", "oneanswers a", "Shows question which has only one answer with their index, coordinates, question and possible answer.");
                System.out.printf("%-20s %s \n", "oneanswers [index]", "Goes to oneanswer index according to oneanswers function data. Ex: oneanswers 3");
                System.out.printf("%-20s %s \n", "guess a", "Shows index, coordinates, parent question, input answer and guess of guess nodes");
                System.out.printf("%-20s %s \n", "guess [index]", "Goes to guess index according to guess function data. Ex: guess 3");
                System.out.printf("%-20s %s \n", "all a", "Shows index, coordinates, node type and context of all childs of that node.");
                System.out.printf("%-20s %s \n", "all [index]", "Goes to index according to all function data. Ex: all 9");


            } else if( input.equalsIgnoreCase("save") ) {
                writeToFile( ancestorNode.textCreator(ancestorNode) );
            } else if( input.equalsIgnoreCase("exit") ) {
                System.out.println("Terminating the program...");
                return;
            } else if( input.equalsIgnoreCase("current") ) {

                printCurrentAttributes(current, ancestorNode);
    
            } else if( input.equalsIgnoreCase("parent") ) {
                if(current.parent != null) {
                    current = current.parent;
                    System.out.println("Coordinates: " + current.getCoordinatesAsText(ancestorNode));
                } else {
                    System.out.println("This node doesn't have parent.");
                }
            }else if( input.equalsIgnoreCase("location") ) {
                System.out.println("Coordinates: " + current.getCoordinatesAsText(ancestorNode));
            } else if( input.equalsIgnoreCase("ancestor") ) {
                current = ancestorNode;
                System.out.println("Coordinates: " + current.getCoordinatesAsText(ancestorNode));
            } else if( input.equalsIgnoreCase("goto") ) {
                QuestionNode targetNode = current.goToNodeViaCoordinates( coordinatesScanner( inputScan.nextLine() ) );
                if(targetNode != null) {
                    current = targetNode;
                    System.out.println("Coordinates: " + current.getCoordinatesAsText(ancestorNode));
                } else {
                    System.out.println("There is no node in given coordinates.");
                    System.out.println("Coordinates: " + current.getCoordinatesAsText(ancestorNode));
                }
            } else if( input.equalsIgnoreCase("gotoan") ) {
                QuestionNode targetNode = ancestorNode.goToNodeViaCoordinates( coordinatesScanner( inputScan.nextLine() ) );
                if(targetNode != null) {
                    current = targetNode;
                    System.out.println("Coordinates: " + current.getCoordinatesAsText(ancestorNode));
                } else {
                    System.out.println("There is no node in given coordinates.");
                    System.out.println("Coordinates: " + current.getCoordinatesAsText(ancestorNode));
                    }
            } else if( input.equalsIgnoreCase("create") ) {
                System.out.println("New node will be created.");
                System.out.println("Parent Question of the new node: " + current.outputContext);
                System.out.print("Enter input answer of the new node: ");
                inputScan.nextLine(); //to skip an enter line
                String inputAnswer = inputScan.nextLine();
                System.out.print("Enter node type of the new node: (guess or question): ");

                String nodeType = inputScan.nextLine();
                boolean isGuess;
                while(true) {
                    if( nodeType.equalsIgnoreCase("question") ) {
                    isGuess =  false;
                        break;
                    }else if( nodeType.equalsIgnoreCase("guess") ) {
                    isGuess =  true;
                        break;
                    } else {
                        System.out.println("This is not a valid answer.");
                        nodeType = inputScan.nextLine();
                    }
                }
                
                System.out.print("Enter context of the new node: ");
                String context = inputScan.nextLine();
                current = current.addChild(inputAnswer, isGuess, context);

                System.out.println("New node has been created. Attributes of the new node:");
                printCurrentAttributes(current, ancestorNode);
    

            } else if( input.equalsIgnoreCase("change") ) {

                System.out.println("Node attributes will be changed.");
                if(current != ancestorNode) System.out.println("Parent Question of the new node: " + current.parent.outputContext);
                System.out.print("Enter new input answer of the node: ");
                inputScan.nextLine(); //to skip an enter line
                String inputAnswer = inputScan.nextLine();
                System.out.print("Enter new node type of the node: (guess or question): ");

                String nodeType = inputScan.nextLine();
                boolean isGuess;
                while(true) {
                    if( nodeType.equalsIgnoreCase("question") ) {
                    isGuess =  false;
                        break;
                    }else if( nodeType.equalsIgnoreCase("guess") ) {
                    isGuess =  true;
                        break;
                    } else {
                        System.out.println("This is not a valid answer.");
                        nodeType = inputScan.nextLine();
                    }
                }
                
                System.out.print("Enter new context of the node: ");
                String context = inputScan.nextLine();

                current.inputAnswer = inputAnswer;
                current.isThisAGuess = isGuess;
                current.outputContext = context;

                System.out.println("Node attributes has been changed. New attributes:");
                printCurrentAttributes(current, ancestorNode);
    


            } else if( input.equalsIgnoreCase("remove") ) {
                if(current == ancestorNode) {
                    System.out.println("Ancestor node cannot be removed.");
                } else {

                    current = current.removeThisNode();
                    
                    System.out.println("Node has been removed. Current node is it's parent.");
                    printCurrentAttributes(current, ancestorNode);
                }



            } else if( input.equalsIgnoreCase("all") ) {

                QuestionNode loopArray[] = current.allNodes();
                if(inputScan.hasNextInt()) {
                    int targetIndex = inputScan.nextInt();

                    if(targetIndex >= 0 && targetIndex < loopArray.length) {
                        current = loopArray[targetIndex];
                        printCurrentAttributes(current, ancestorNode);
                    }else {
                        System.out.println("This not a valid index");
                    }

                    } else {
                        inputScan.next(); //to skip an unneccessary variable
                        System.out.println("Here is list of the all childs of this node:");
                        System.out.printf("%-10s %-35s %-12s %-70s %s\n", "Index:", "Coordinates:", "Node Type:", "Context:", "Possible Answers:");

                        for(int i = 0; i < loopArray.length; i++) {
                            QuestionNode loopNode = loopArray[i];
                            
                            String[] possibleAnswers = loopNode.getPossibleAnswers();
                            String toWrite = "";
                            if(possibleAnswers.length > 0) {
                                for(int x = 0; x < possibleAnswers.length; x++) {
                                    if(x != possibleAnswers.length - 1) toWrite += possibleAnswers[x] + ", ";
                                    else toWrite += possibleAnswers[x] + " ";
                                }
                            }

                            System.out.printf("%-10d %-35s %-12s %-70s %s\n", i, loopNode.getCoordinatesAsText(ancestorNode), loopNode.nodeType(), loopNode.outputContext, toWrite);
                        }
                    }

            } else if( input.equalsIgnoreCase("deadends") ) {
                QuestionNode deadEndsArray[] = current.deadEnds();
                if(inputScan.hasNextInt()) {
                    int targetIndex = inputScan.nextInt();

                    if(targetIndex >= 0 && targetIndex < deadEndsArray.length) {
                        current = deadEndsArray[targetIndex];
                        System.out.println("Succesfully location changed to that index. Current node:");
                        printCurrentAttributes(current, ancestorNode);
                    }else {
                        System.out.println("This not a valid index");
                    }

                    } else {
                        inputScan.next(); //to skip an unneccessary variable
                        System.out.println("Here is list of the questions which don't have answers:");
                        System.out.printf("%-10s %-35s %s\n", "Index:", "Coordinates:", "Questions:");
                        for(int i = 0; i < deadEndsArray.length; i++) {
                            QuestionNode loopNode = deadEndsArray[i]; 
                            System.out.printf("%-10d %-35s %s\n", i, loopNode.getCoordinatesAsText(ancestorNode), loopNode.outputContext );
                        }
    
                    }

            } else if( input.equalsIgnoreCase("oneanswers") ) {

                QuestionNode loopArray[] = current.oneAnswers();
                if(inputScan.hasNextInt()) {
                    int targetIndex = inputScan.nextInt();

                    if(targetIndex >= 0 && targetIndex < loopArray.length) {
                        current = loopArray[targetIndex];
                        printCurrentAttributes(current, ancestorNode);
                    }else {
                        System.out.println("This not a valid index");
                    }

                    } else {
                        inputScan.next(); //to skip an unneccessary variable
                        System.out.println("Here is list of the questions which has only one answer:");
                        System.out.printf("%-10s %-35s %-50s %s\n", "Index:", "Coordinates:", "Questions:", "Possible Answer:");
                        for(int i = 0; i < loopArray.length; i++) {
                            QuestionNode loopNode = loopArray[i]; 
                            System.out.printf("%-10d %-35s %-50s %s\n", i, loopNode.getCoordinatesAsText(ancestorNode), loopNode.outputContext, loopNode.goToNode(0).inputAnswer );
                        }
                    }

            } else if( input.equalsIgnoreCase("guess") ) {

                QuestionNode loopArray[] = current.guess();
                if(inputScan.hasNextInt()) {
                    int targetIndex = inputScan.nextInt();

                    if(targetIndex >= 0 && targetIndex < loopArray.length) {
                        current = loopArray[targetIndex];
                        printCurrentAttributes(current, ancestorNode);
                    }else {
                        System.out.println("This not a valid index");
                    }

                    } else {
                        inputScan.next(); //to skip an unneccessary variable
                        System.out.println("Here is list of the guesses:");
                        System.out.printf("%-10s %-35s %-50s %-20s %s\n", "Index:", "Coordinates:", "Parent Question:", "Answer:", "Guess:");
                        for(int i = 0; i < loopArray.length; i++) {
                            QuestionNode loopNode = loopArray[i]; 
                            System.out.printf("%-10d %-35s %-50s %-20s %s\n", i, loopNode.getCoordinatesAsText(ancestorNode), loopNode.parent.outputContext, loopNode.inputAnswer, loopNode.outputContext );
                        }
                    }

            } else {
                System.out.println("This is not a valid command. You can type \"help\" and learn commands.");

            }
        

    }

    }

}
