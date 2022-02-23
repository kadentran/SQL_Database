/*
 * This program demonstrates how to do binary I/O in Java.  The idea is to read
 * in a block and work with it as bytes, and write out a block of bytes.  That
 * way you're not constrained to working only with text files.
 *
 * Written by John Cole at The University of Texas at Dallas as a sample for
 * use in classes.  You may freely copy, use, and adapt this code for any
 * class I teach.
 */
package binaryio;

import java.io.*;
import java.nio.channels.SeekableByteChannel;
import java.util.*;

/**
 *
 * @author jxc064000
 */
public class BinaryIO
  {
  private Scanner readName;
  // The file name has a .txt extension so we can open it in Notepad, even
  // though it is not actually text.
  private String strFilename = "binaryio.txt";

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args)
    {
    BinaryIO cls = new BinaryIO();
    }

  public BinaryIO()
    {
    byte[] bdata = new byte[128];
    byte[] bin = new byte[256];
    // Fill an array with the bytes 1 through 128.  Some of these will
    // correspond to printable characters in ASCII, but Java uses Unicode.
    for (int ix = 0; ix < bdata.length; ix++)
      {
      bdata[ix] = (byte) (ix + 1);
      }
    try
      {
      // Create the file for read and write and write a binary number as the
      // first four bytes.  This number will later be used as an offset within
      // the file.
      RandomAccessFile randFile = new RandomAccessFile(strFilename, "rw");
      int ipos = 32;
      randFile.writeInt(ipos);
      // Write the byte array of "characters.
      randFile.write(bdata);
      System.out.println("Wrote " + bdata.length + " bytes of data");
      // Go back to the beginning of the file and read the integer we wrote
      // into an array of bytes.
      randFile.seek(0l);
      randFile.read(bdata, 0, 4);
      // This strange piece of code takes the four bytes of integer we wrote
      // and reconstructs.  Think of it as working in base 256.  The first byte
      // is the highest-order "digit" so that gets multiplied by 256**3.  The
      // next one is multiplied by 256**2, etc.
      ipos = bdata[3] + (bdata[2] * 256 + bdata[1] * 256 * 256 + bdata[0] * 256 * 256 * 256);
      System.out.println("Position read back: " + ipos);
      randFile.seek(0l);
      int itemp = randFile.readInt();
      System.out.println("Position read as integer: " + itemp);
      randFile.seek(ipos + 4);
      // Read from the given position.
      randFile.read(bin, 0, 127 - ipos);
      // Convert the byte array to a string and print that.
      String strin = new String(bin);
      System.out.print(strin);
      System.out.println();
      randFile.close();
      readName = new Scanner(System.in);
      System.out.print("Press a key: ");
      String ans = readName.nextLine();
      }
    catch (Exception ex)
      {
      System.out.println("Error: " + ex.getMessage());
      }
    }

  }
