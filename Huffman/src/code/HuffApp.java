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
	private String[] codeTable;
	private String decodedMessage = "";
	char[] c_array;
	

	public static void main(String[] args){
		new HuffApp();	
	}

		
	
	public HuffApp() {
		codeTable = new String[ASCII_TABLE_SIZE];  
		readInput();
		displayOriginalMessage();
		makeFrequencyTable(originalMessage);
		displayFrequencyTable();
		addToQueue();
		buildTree(theQueue);
		//when the following method is implemented, remove the "//", so it executes
		//makeCodeTable(huffTree.root, "");  						
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
		c_array = inputString.toCharArray();
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
		//add the values in the frequency table to the PriorityQueue. Hint use the 
		//PriorityQ class. save the results to theQueue field
	}
	
	private void buildTree(PriorityQ hufflist) 
	{
		//pull items from the priority queue and combine them to form 
		//a HuffTree. Save the results to the huffTree field
	}
	
	private void makeCodeTable(HuffNode huffNode, String bc)
	{		
		//hint, this will be a recursive method
	}
	
	private void displayCodeTable()
	{	
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
