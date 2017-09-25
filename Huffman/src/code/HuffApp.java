package code;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;


public class HuffApp {
	private int[] freqTable;
	private final static int ASCII_TABLE_SIZE = 128;
	private String originalMessage = "";
	private PriorityQ theQueue;
	private HuffTree huffTree;
	private String encodedMessage = "";
	private String[] codeTable = new String[ASCII_TABLE_SIZE];
	private String decodedMessage = "";


	public static void main(String[] args){
		new HuffApp();	
	}

		
	
	public HuffApp() {
		theQueue = new PriorityQ(ASCII_TABLE_SIZE);
		codeTable = new String[ASCII_TABLE_SIZE];
		readInput();
		displayOriginalMessage();
		makeFrequencyTable(originalMessage);
		displayFrequencyTable();
		addToQueue();
		buildTree(theQueue);
		for(int i = 0; i < ASCII_TABLE_SIZE; i++) //fills the array with blank strings to prevent any null pointer errors
		{
			codeTable[i]= "";
		}
		makeCodeTable(huffTree.root, "");
		encode();
		displayEncodedMessage();
		displayCodeTable();
		decode(huffTree.root);
		displayDecodedMessage();
	}

	private void readInput() {
		try {
			originalMessage = new String(Files.readAllBytes(Paths.get("input"))); //reads the original Message and saves to originalMessage
		}

		catch(IOException e)
		{
			e.printStackTrace(); //Prints an error message
		}

	}

	private void displayOriginalMessage() {
		System.out.println("Original message: " +  originalMessage);
	}

	private void makeFrequencyTable(String inputString)
	{
		char [] c_array = inputString.toCharArray(); //turns string message into characters
		freqTable = new int[ASCII_TABLE_SIZE];
		for(int i = 0; i < ASCII_TABLE_SIZE; i++)
		{
			freqTable[i]= 0;//fills the array with 0 to prevent a null pointer error
		}
		for(int i =0; i < c_array.length; i++)
		{
			freqTable[(int)c_array[i]]++; //makes a table where frequency at a certain int value is calculated
		}
	}

	private void displayFrequencyTable()
	{
		System.out.println("Frequency Table");
		for(int i = 0; i < ASCII_TABLE_SIZE; i++)
		{
			if(freqTable[i]!=0) //looks for frequencies to print
			{
				System.out.print((char)i + " " + freqTable[i]);
				System.out.println();
			}
		}
	}

	private void addToQueue()
	{
		for(int i = 0; i < ASCII_TABLE_SIZE; i++)
		{
			if(freqTable[i]!=0) //looks at frequencies to add to theQueue
			{
				theQueue.insert(huffTree = new HuffTree((char)i, freqTable[i]));
			}
		}

	}

	private void buildTree(PriorityQ hufflist)
	{
		while(theQueue.getSize() > 1) //merges theQueue items and builds a HuffTree
		{
			int frequency1 = hufflist.peekMin().getWeight();
			HuffTree temp1 = hufflist.remove();
			int frequency2 = hufflist.peekMin().getWeight();
			HuffTree temp2 = hufflist.remove();
			huffTree = new HuffTree((frequency1+frequency2), temp1, temp2);
			hufflist.insert(huffTree);
		}
	}

	private void makeCodeTable(HuffNode huffNode, String bc)
	{

		if (huffNode.leftChild == null || huffNode.leftChild.isLeaf()) //finds all left nodes for characters and their paths
		{
			codeTable[huffNode.leftChild.character] = bc + "0";
		}

		else
		{
			makeCodeTable(huffNode.leftChild, bc+"0");
		}

		if(huffNode.rightChild == null || huffNode.rightChild.isLeaf()) //finds all right nodes for characters and their paths
		{
			codeTable[huffNode.rightChild.character] = bc + "1";
		}

		else
		{
			makeCodeTable(huffNode.rightChild, bc+"1");
		}

		//hint, this will be a recursive method
	}

	private void displayCodeTable()
	{
		System.out.println("Code Table");
		for(int i = 0; i < ASCII_TABLE_SIZE; i++) //searches all characters for shortened binary codes
		{
			if(codeTable[i].equals("")||codeTable[i]==null) //prevents null pointer exception errors
			{

			}
			else //prints found characters and their location
			{
				System.out.print((char)i + " " + codeTable[i]);
				System.out.println();
			}
		}
		//print code table, skipping any empty elements
	}

	private void encode()                   
	{		
		//use the code table to encode originalMessage. Save result in the encodedMessage field
		char[] ogMessageArray = originalMessage.toCharArray(); // I set the original message to a char array
		StringBuilder builder = new StringBuilder(); // I create a string builder so i can piece together the string from the code table.
			for (int i = 0; i<ogMessageArray.length; i++){ // Loop for going through the original message
				for (int j = 0; j< ASCII_TABLE_SIZE; j++){ // Loop for ascii table check
					if(codeTable[j].equals("")||codeTable[j]==null)
					{
						
					} else if((int)ogMessageArray[i] == j){
						builder.append(codeTable[j]); // pieces together the string
						
					}else{
						
					}
					encodedMessage = builder.toString(); // sets to = encoded message
				}
			}
		
	}

	private void displayEncodedMessage() {
		System.out.println("\nEncoded message: " + encodedMessage);		
	}

	private void decode(HuffNode inHuff)
	{
		//decode the message and store the result in the decodedMessage field
		String message = encodedMessage; // sets the encoded message to a temporary variable
		char[] decodeCharArray = message.toCharArray(); // sets the encoded message to char array
		StringBuilder builder = new StringBuilder(); // string builder to piece together the string later
		HuffNode root = inHuff; // setting the root node so i can go back to it.
		HuffNode current = inHuff; // sets a current node to traverse the tree
			for (int i = 0; i < decodeCharArray.length; i++){ 
				if(decodeCharArray[i] == '0'){  // Tree traversal 
					current = current.leftChild;
				}else if(decodeCharArray[i] == '1'){
					current = current.rightChild;
				}else{
					System.out.println("Houston we have a problem."); // My own personal error message
				}
				if(current.isLeaf()){ // tests to see if there is no more nodes to traverse to.
					builder.append(current.character); //build the String up!
					current = root; // Reset the current node to the root.
				}
			}
			decodedMessage = builder.toString(); // set the decoded message to the pieced together builder.

		
	}
	
	
	public void displayDecodedMessage() {
		System.out.println("Decoded message: " + decodedMessage);
	}	
}
