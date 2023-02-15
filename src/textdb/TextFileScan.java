package textdb;

import java.io.*;

/**
 * Performs a file scan in iterator form.  File is assumed to be on disk in TEXT form.  
 */
public class TextFileScan extends Operator
{
	protected String inFileName;					// Name of input file to scan
	protected BufferedReader inFile;				// Used to read from text file
	protected Relation inputRelation;				// Schema of file being scanned


	public TextFileScan(String inName, Relation r)
	{	super();
		inFileName = inName;
		inputRelation = r;
		setOutputRelation(r);						// Set output relation of this operator
	}

	public void init() throws FileNotFoundException, IOException
	{
		// TODO: Assign a new BufferedReader to inFile with the FileManager class
		inFile = FileManager.openTextInputFile(inFileName);
	}

	public Tuple next() throws IOException
	{
		// TODO: YOUR CODE TO CREATE A NEW TUPLE AND READ FROM TEXT FILE HERE

		Tuple t = new Tuple(inputRelation);
		if (!t.readText(inFile))
			return null;
		incrementTuplesRead();		
		incrementTuplesOutput();
		System.out.println(t);
		return t;
	}

	public boolean hasNext() throws IOException
	{	return inFile.ready();
	}

	public void close() throws IOException
	{
		// TODO: YOUR CODE HERE TO CLOSE File using FileManager
		FileManager.closeFile(inFile);
	}
}

