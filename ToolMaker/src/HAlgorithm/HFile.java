package HAlgorithm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardOpenOption.*;


/** This class contains static method for writing to a file.
 * 
 * After write then flush.
 * The file is open defaultsively in APPEND MODE
 * 
 * @author HIENBN
 *
 */
public class HFile {

	
		
	
	/** This function write a String to a file without automatic add newline
	 * 
	 * @param file
	 * @param msg
	 */
	public static void write(Path file, String msg)
	{
		
		try {
			BufferedWriter bufWrite = Files.newBufferedWriter(file, StandardOpenOption.APPEND);
			
			bufWrite.write(msg);
			bufWrite.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/** This function write a String to a file without automatic add newline
	 * 
	 * @param bufWrite
	 * @param msg
	 */
	
	public static void bwrite(BufferedWriter bufWrite, String msg)
	{
		
		try {
						
			bufWrite.write(msg);
			bufWrite.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	/** Make this file empty
	 * 
	 * @param file
	 */
	public static void clear(Path file)
	{
		try {
			BufferedWriter bufWrite = Files.newBufferedWriter(file, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/** This function write a String to a file with automatically add newline
	 * 
	 * @param file
	 * @param msg
	 */
	public static void writeln(Path file, String msg)
	{
		
		try 
		{
			BufferedWriter bufWrite = Files.newBufferedWriter(file, StandardOpenOption.APPEND);
			
			bufWrite.write(msg + "\r\n");
			//bufWrite.flush();
			
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	/** This function write a String to a file with automatically add newline
	 * 
	 * @param bufWrite
	 * @param msg
	 */
	public static void bwriteln(BufferedWriter bufWrite, String msg)
	{
		
		try 
		{
			//BufferedWriter bufWrite = Files.newBufferedWriter(file, StandardOpenOption.APPEND);
			
			bufWrite.write(msg + "\r\n");
			bufWrite.flush();
			
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	
	public static void openNewFile(Path file)
	{
		try {
			BufferedWriter bufWrite = Files.newBufferedWriter(file, StandardOpenOption.CREATE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//
	public static BufferedWriter bopenNewFile(Path file)
	{
		BufferedWriter bufWrite = null;
		try {
			 bufWrite = Files.newBufferedWriter(file, StandardOpenOption.CREATE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bufWrite;
	}
	
	
	public static void flush(Path file)
	{
		BufferedWriter bufWrite;
		try {
			bufWrite = Files.newBufferedWriter(file, StandardOpenOption.APPEND);
			bufWrite.write("/r/n");
			bufWrite.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
	}
	
	
	public static void bflush(BufferedWriter bufWrite)
	{
		//BufferedWriter bufWrite;
		try {
			//bufWrite = Files.newBufferedWriter(file, StandardOpenOption.APPEND);
			bufWrite.write("/r/n");
			bufWrite.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
	}
	
	
	
	
	
	
}
