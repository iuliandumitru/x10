/**************************************************************************
 *                                                                         *
 *             Java Grande Forum Benchmark Suite - Version 2.0             *
 *                                                                         *
 *                            produced by                                  *
 *                                                                         *
 *                  Java Grande Benchmarking Project                       *
 *                                                                         *
 *                                at                                       *
 *                                                                         *
 *                Edinburgh Parallel Computing Centre                      *
 *                                                                         * 
 *                email: epcc-javagrande@epcc.ed.ac.uk                     *
 *                                                                         *
 *                                                                         *
 *      This version copyright (c) The University of Edinburgh, 1999.      *
 *                         All rights reserved.                            *
 *                                                                         *
 **************************************************************************/


package crypt;
import jgfutil.*; 


public class JGFCryptBench extends IDEATest implements JGFSection2{ 
	
	private int size; 
	private int datasizes[]={32000,20000000,50000000};
	
	public void JGFsetsize(int size){
		this.size = size;
	}
	
	public void JGFinitialise(){
		array_rows = datasizes[size];
		buildTestData();
	}
	
	public void JGFkernel(){
		Do(); 
	}
	
	public void JGFvalidate(){
		final byte[.] tmp_plain1 = plain1;
		final byte[.] tmp_plain2 = plain2;
		final byte[.] tmp_crypt1 = crypt1;
		
		finish ateach (point [i] : tmp_plain1 )
		if (tmp_plain1[i] != tmp_plain2[i]) {
			System.out.println("Validation failed");
			System.out.println("Original Byte " + i + " = " + tmp_plain1[i]); 
			System.out.println("Encrypted Byte " + i + " = " + tmp_crypt1[i]); 
			System.out.println("Decrypted Byte " + i + " = " + tmp_plain2[i]); 
			throw new Error("Validation failed");
		}
	}
	
	
	public void JGFtidyup(){
		freeTestData(); 
	}  
	
	
	
	public void JGFrun(int size){
		
		
		JGFInstrumentor.addTimer("Section2:Crypt:Kernel", "Kbyte",size);
		
		JGFsetsize(size); 
		JGFinitialise(); 
		JGFkernel(); 
		JGFvalidate(); 
		JGFtidyup(); 
		
		
		JGFInstrumentor.addOpsToTimer("Section2:Crypt:Kernel", (2*array_rows)/1000.); 
		JGFInstrumentor.printTimer("Section2:Crypt:Kernel"); 
	}
}
