import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TwentyQuestionsLauncher {

    //every value is in this value


    //questions format
    //starts after begin
    //there are no commas, there are spaces between inputs
    //two stars to end string inputs (**)
    //create objects by firstly creating their parents (Upper nodes)
    //first node starts with coordinates -1
    //first node format is different: -1 question (example:-1 is this a mineral, vehicle or tool?)
    //Coordinates(as numbers with spaces between them), Which answer as an input will send to this node(String), Is this an guess(boolean(true, false)), Context of the node (Question or guess) (String)
    //example: 0 2 1 bigger** false Is it sharp?
    //don't use return as an input becase return sends to previous node on the program.


    //code doesn't work when text types are different
    



    static QuestionNode getQuestions() {

        QuestionNode ancestorNode = new QuestionNode();

        File questionsFile = new File( "questions.txt" );

        try {


        //end when there are no coordinates because coordiantes is start text
        Scanner scanFile = new Scanner( questionsFile );


        //creating ancestor node

        scanFile.nextInt(); //this is -1, so there is no need to save this value as a variable

        scanFile.useDelimiter( "\n" );
        ancestorNode.outputContext = scanFile.next().trim();
        scanFile.useDelimiter( "\\s+" );

            while( scanFile.hasNextInt() ) {

                
                //takes coordinates
                ArrayList<Integer> coordinatesList = new ArrayList<Integer>(); 

                while( scanFile.hasNextInt() ) {
                    coordinatesList.add( scanFile.nextInt() );
                }

                //converts coordinates to give as an input to goToCoordinates function

                //last coordinate is not important because it adds childs by order
                int parentCoordinates[] = new int[coordinatesList.size() - 1];
                for(int i = 0; coordinatesList.size() - 1 > i; i++ ) {

                    parentCoordinates[i] = coordinatesList.get(i);
                }
                
                
                //first takes node, after that varibles will saved in
                //start with 0 index, function will increase it

                scanFile.useDelimiter( Pattern.quote("**") );
                String inputAnswer = scanFile.next().trim();
                scanFile.useDelimiter("\\s+");
                scanFile.next(); //it will take **, so this is an unneccesary input, there is no need to save it;
        
                boolean isThisAGuess = scanFile.nextBoolean();
        
                scanFile.useDelimiter( "\n" );
                String outputContext = scanFile.next().trim();
                scanFile.useDelimiter( "\\s+" );
                

                ancestorNode.goToNodeViaCoordinates(parentCoordinates).addChild(inputAnswer, isThisAGuess, outputContext);

            }


        
        } catch(FileNotFoundException e) {

            return null;
        } 

        return ancestorNode;


        
        


    }


    public static void main( String[] args ) {


        QuestionNode ancestorNode = getQuestions();
        Scanner inputTake = new Scanner( System.in );
        inputTake.useDelimiter("\n");

    //
        if(ancestorNode != null) {

            System.out.println("Welcome to the Twenty Questions game!");
            System.out.println("You can always return previous question by typing *return* and exit by typing *exit*.");
            System.out.println("Furthermore, you can type coordinates and see where you are!");

            //i didn't use this variable
            boolean continueTo = true;
            QuestionNode currentNode = ancestorNode;
            while(continueTo) {


                if(currentNode.isThisAGuess == false) {
                    System.out.println(currentNode.outputContext);
                    System.out.print("Possible answers: ");
                    String possibleAnswers[] = currentNode.getPossibleAnswers();
                    for(int i = 0; i < possibleAnswers.length; i++) {
                        //different operations to ensure that no commas in the ending 
                        if(i != possibleAnswers.length - 1) {
                            System.out.print(possibleAnswers[i] + ", ");
                        } else {
                            System.out.print(possibleAnswers[i] + "\n");
                        }
                    }

                } else {
                    System.out.println("You think " + currentNode.outputContext + ".");
                    System.out.println("You can type *exit* to exit the program or type *return* to return to previous question");
                }

                String input = inputTake.next();
                input = input.substring(0, input.length() - 1 );
                //there is an extra character in input and doesn't work because of it i tried for hours to solve it 
                /*
                System.out.println(input.compareTo( currentNode.childs.get(1).inputAnswer ) + ".");
                System.out.println(currentNode.childs.get(1).inputAnswer + ".");
                System.out.println((int) input.charAt(6));
                System.out.println(input.length());
                System.out.println(currentNode.childs.get(1).inputAnswer.length());
                */

                if(input.equalsIgnoreCase("exit")) break;


                else if(input.equalsIgnoreCase("coordinates")) {

                    System.out.print("Here is coordinates of this node which you are on:");
                    int[] coordinatesOfTheNode = currentNode.getCoordinates(ancestorNode);
                    for(int i = 0; i < coordinatesOfTheNode.length; i++) {

                        System.out.print(" "+coordinatesOfTheNode[i]);
                    }
                    System.out.print("\n");
                }

                else if(input.equalsIgnoreCase("return")) {
                    //return the previous node so to the parent of this node
                    if(currentNode.parent != null)
                    currentNode = currentNode.parent;
                    else
                    System.out.println("There is no parent of this node. It is not possible to return.");
                } else {
                    QuestionNode newNode = currentNode.findNode(input);
                    if( newNode != null ) {

                        currentNode = newNode;
                    } else {
                        System.out.println("This is not a valid answer. Please type it again.");
                    }
                }



            }

            System.out.println("Thank you for playing the game! Exiting.....");
            return;

        } else {
            //terminate the program if an error occurs
            System.out.println( "An error occured." );
            return;
        }

        
        

   

    }


}