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
		for(int i = 0; i < ASCII_TABLE_SIZE; i++)
		{
				codeTable[i]= "";
		}
		makeCodeTable(huffTree.root, "");
		encode();
		displayEncodedMessage();
		displayCodeTable();
		decode();
		displayDecodedMessage();		
	}
	
	private void readInput() {
		try {
			originalMessage = new String(Files.readAllBytes(Paths.get("input")));
		}

		catch(IOException e)
		{
			e.printStackTrace();
		}

	}
	
	private void displayOriginalMessage() {
		System.out.println("Original message: " +  originalMessage);
	}
	
	private void makeFrequencyTable(String inputString)
	{
		char [] c_array = inputString.toCharArray();
		freqTable = new int[ASCII_TABLE_SIZE];
		for(int i = 0; i < ASCII_TABLE_SIZE; i++)
		{
			freqTable[i]= 0;
		}
		for(int i =0; i < c_array.length; i++)
		{
			freqTable[(int)c_array[i]]++;
		}
	}
	
	private void displayFrequencyTable()
	{
		System.out.println("Frequency Table");
		for(int i = 0; i < ASCII_TABLE_SIZE; i++)
		{
			if(freqTable[i]!=0)
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
			if(freqTable[i]!=0)
			{
				theQueue.insert(huffTree = new HuffTree((char)i, freqTable[i]));
			}
		}

	}

	private void buildTree(PriorityQ hufflist)
	{
		while(theQueue.getSize() > 1)
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

		if (huffNode.leftChild == null || huffNode.leftChild.isLeaf())
		{
			codeTable[huffNode.leftChild.character] = bc + "0";
		}

		else
		{
			makeCodeTable(huffNode.leftChild, bc+"0");
		}

		if(huffNode.rightChild == null || huffNode.rightChild.isLeaf())
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
		for(int i = 0; i < ASCII_TABLE_SIZE; i++)
		{
			if(codeTable[i].equals("")||codeTable[i]==null)
			{

			}
			else
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
	}

	private void displayEncodedMessage() {
		System.out.println("\nEncoded message: " + encodedMessage);		
	}

	private void decode()
	{
		//decode the message and store the result in the decodedMessage field
	}
	
	public void displayDecodedMessage() {
		System.out.println("Decoded message: " + decodedMessage);
	}	
}
