import java.util.ArrayList;

//pathleri child diye değiştirdim

public class QuestionNode {

    QuestionNode parent = null; //if it is ancestor node
    boolean isThisAGuess;
    String outputContext, inputAnswer;
    ArrayList<QuestionNode> childs = new ArrayList<QuestionNode>();
    int forIndex;



    public QuestionNode addChild(String newInputAnswer, boolean isNewNodeAGuess, String newOutputContext) {

        childs.add( new QuestionNode() );
        int lastObjectIndex = childs.size() - 1;
        childs.get( lastObjectIndex ).inputAnswer = newInputAnswer;
        childs.get( lastObjectIndex ).isThisAGuess = isNewNodeAGuess;
        childs.get( lastObjectIndex ).outputContext = newOutputContext;
        childs.get( lastObjectIndex ).parent = this;

        return childs.get( lastObjectIndex );

    }


    public  QuestionNode goToNode(int index) {
        if( index < childs.size() ){
            return childs.get(index);
        } else {
            return null;
        }
    }


    //this is a recursive function and returns output object when array is empty
    public QuestionNode goToNodeViaCoordinates(int[] coordinates) {

        //return this object if coordinates array is empty
        if(coordinates.length == 0) return this;

        if( coordinates[0] < childs.size() ){
            //clone without first element of the array
            int newCoordinates[] = new int[ coordinates.length - 1];
            for(int i = 1; i < coordinates.length; i++) newCoordinates[i - 1] = coordinates[i];

            return childs.get( coordinates[0] ).goToNodeViaCoordinates(newCoordinates);            
        } else {
            return null;
        }
    
    }

    //according to given node as variable
    public int[] getCoordinates(QuestionNode parentNode) {

        ArrayList<Integer> coordinatesList = new ArrayList<Integer>();
        QuestionNode currentNode = this;

        while(currentNode != parentNode) {

            //this looks this which index according to its parent
            int indexNumber = currentNode.parent.childs.indexOf(currentNode);
            coordinatesList.add(indexNumber);
            currentNode = currentNode.parent;

        }


        int[] coordinates = new int[coordinatesList.size()];
        //coordinates should be reverted to make it according to parent to childs
        for(int i = 0; i < coordinatesList.size(); i++) {

            coordinates[i] = coordinatesList.get(coordinatesList.size() - i - 1);

        }
        return coordinates;


    }
    // returns coordinates as string Ex: "5 3 7 2 6 "
    public String getCoordinatesAsText(QuestionNode parentNode) {
        String text = "";
        int[] coordinates = this.getCoordinates(parentNode);
        for(int i = 0; i < coordinates.length; i++) {
            text += coordinates[i] + " ";
        }
        if(coordinates.length == 0) text += "-1 ";
        return text;
    }

    public String nodeType() {

        if(isThisAGuess == true) return "Guess";
        else return "Question";

    }

    //returns parent node after removing itself
    public QuestionNode removeThisNode() {

        this.parent.childs.remove(this);
        return this.parent;

    }

    public String[] getPossibleAnswers() {

        String[] possibleAnswers = new String[childs.size()];

        for(int i = 0; i < childs.size(); i++) {
                possibleAnswers[i] = childs.get(i).inputAnswer;
        }

        return possibleAnswers;
        //return empty if its child empty, but probably it will be an error to return via this command with empty string

    }

    //returns null if there is no answer like that
    public QuestionNode findNode( String answerToSearch ) {

        for(int i = 0; i < childs.size(); i++) {


           if( answerToSearch.equalsIgnoreCase( childs.get(i).inputAnswer )) {

                return childs.get(i);

            }

        }

        return null;

    }

    //these which are below in this text are generally recursive functions and runs parent to childs

    //merges arrays
    public QuestionNode[] mergeArrays(QuestionNode[] firstArray, QuestionNode[] secondArray) {

        QuestionNode[] newArray = new QuestionNode[firstArray.length + secondArray.length];
        int i = 0;
        for(i = 0; i < firstArray.length; i++) {
            newArray[i] = firstArray[i];
        }
        int lastIndex = i;
        for(i = 0; i < secondArray.length; i++) {
            newArray[i + lastIndex] = secondArray[i];
        }

        return newArray;

    }
    
    // reverse this array because it will return weird and revers arrays
    //no need to reverse you can use mergeArrays function to make inversion 

    //bunu da ekle
    public QuestionNode[] allNodes() {
        QuestionNode[] nodesArray = new QuestionNode[1];
        nodesArray[0] = this;

        for(int i = 0; i < this.childs.size(); i++) {
            QuestionNode[] newArray = mergeArrays(nodesArray, this.goToNode( i ).allNodes());
            nodesArray = new QuestionNode[newArray.length];
            nodesArray = newArray;
        }

        return nodesArray;
    }

    public QuestionNode[] deadEnds() {

        QuestionNode[] nodesArray = new QuestionNode[0];
        if(this.childs.size() == 0 && this.isThisAGuess == false) { 
            nodesArray = new QuestionNode[1];
            nodesArray[0] = this;
        }

        for(int i = 0; i < this.childs.size(); i++) {
            QuestionNode[] newArray = mergeArrays(nodesArray, this.goToNode( i ).deadEnds());
            nodesArray = new QuestionNode[newArray.length];
            nodesArray = newArray;
        }
        return nodesArray;

    }

    public QuestionNode[] oneAnswers() {

        QuestionNode[] nodesArray = new QuestionNode[0];
        if(this.childs.size() == 1 && this.isThisAGuess == false) { 
            nodesArray = new QuestionNode[1];
            nodesArray[0] = this;
        }

        for(int i = 0; i < this.childs.size(); i++) {
            QuestionNode[] newArray = mergeArrays(nodesArray, this.goToNode( i ).oneAnswers());
            nodesArray = new QuestionNode[newArray.length];
            nodesArray = newArray;
        }

        return nodesArray;
    }

    public QuestionNode[] guess() {

        QuestionNode[] nodesArray = new QuestionNode[0];
        if(this.isThisAGuess == true) { 
            nodesArray = new QuestionNode[1];
            nodesArray[0] = this;
        }

        for(int i = 0; i < this.childs.size(); i++) {
            QuestionNode[] newArray = mergeArrays(nodesArray, this.goToNode( i ).guess());
            nodesArray = new QuestionNode[newArray.length];
            nodesArray = newArray;
        }

        return nodesArray;
    }



    //method for creating text file according to ancestorNode
    public String textCreator(QuestionNode ancestorNode) {

        String text = "";
        
        int[] coordinates = this.getCoordinates(ancestorNode);

        if(coordinates.length == 0){

        text += "-1 "; //do when coordinates are empty, so when it is ancestor node, because first format is different
        text += outputContext + "\n";
        } else {

        for(int i = 0; i < coordinates.length; i++) {
            text += coordinates[i] + " ";
        } 

        text += inputAnswer + "** ";
        text += isThisAGuess + " ";
        text += outputContext + "\n";

    }

        for(int i = 0; i < childs.size(); i++) {
        text += this.goToNode( i ).textCreator(ancestorNode);
        }

        return text;
    }




}