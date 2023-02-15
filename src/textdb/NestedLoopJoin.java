package textdb;

import java.io.*;

/**
 * Performs a standard nested-loop join in iterator format. 
 */
public class NestedLoopJoin extends Operator
{
	private EquiJoinPredicate pred;			// A equi-join comparison class that can handle 1 or more attributes

	// Iterator state variables
	private Tuple tupleLeft;
	private Tuple tupleRight;

	public NestedLoopJoin(Operator []in, EquiJoinPredicate p)
	{	super(in, 0, 0);
		pred = p;
	}

	public void init() throws IOException
    {
        // Initialize inputs
        input[0].init();
        input[1].init();

        // Create output relation - keep all attributes of both tuples
        Relation out = new Relation(input[0].getOutputRelation());
        out.mergeRelation(input[1].getOutputRelation());
        setOutputRelation(out);
        tupleLeft = input[0].next();
    }


    public Tuple next() throws IOException
    {
        do {
            tupleRight = input[1].next();
            if (tupleRight == null) {
                input[1].close();
                tupleLeft = input[0].next();
                if (tupleLeft == null) return null;
                input[1].init();
                tupleRight = input[1].next();
            }
        } while (!tupleLeft.getValues()[0].equals(tupleRight.getValues()[0])); 

        return outputJoinTuple(tupleLeft, tupleRight);
    }

	public void close() throws IOException
	{	super.close();
	}

	private Tuple outputJoinTuple(Tuple left, Tuple right)
	{	Tuple t = new Tuple(left, right, getOutputRelation());
		incrementTuplesOutput();
		return t;
	}
}

