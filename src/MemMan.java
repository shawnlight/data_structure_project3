import java.io.File;
import java.io.IOException;
/**
 * {Project Description Here}
 */

/**
 * The class containing the main method.
 *
 * @author {Ziqi Xiao Weiming Chen}
 * @version {2020 fall}
 * 
 */

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

public class MemMan {
    /**
     * @param args
     *            Command line parameters
     *            [0] The initial size of the memory pool
     *            [1] The initial size of the hash table
     *            [2] The name of the command file passed in as a command
     *            line argument.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // This is the main file for the program.
        int initMemorySize = 0;
        int initHashSize = 0;
        if (args.length != 3) {
            System.out.println("Bad Argument Count. Usage: MemMan "
                + "<init-memory-size> <init-hash-size> <command-file>");
            return;
        }

        try {
            initMemorySize = Integer.parseInt(args[0]);
            if (!powerof2(initMemorySize)) {
                System.out.println("Bad value for initial memory size: "
                    + initMemorySize + ", must be a positive power of two");
                return;
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Bad Parameter |" + args[0] + "|\n"
                + "Usage: MemMan "
                + "<init-memory-size> <init-hash-size> <command-file>");
            return;
        }
        try {
            initHashSize = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
            System.out.println("Bad Parameter |" + args[1] + "|\n"
                + "Usage: MemMan "
                + "<init-memory-size> <init-hash-size> <command-file>");
            return;
        }
        String commandFile = args[2].trim();
        File theFile = new File(commandFile);
        if (!theFile.exists()) {
            System.out.println("There is no such input file as |" + commandFile
                + "|");
            return;
        }

        World world = new World(initHashSize, initMemorySize);
        Parser parser = new Parser(world);
        parser.readCmdFile(theFile);

    }


    /**
     * Return true iff "it" is a power of 2
     *
     * @param it
     *            The value to compute on
     * @return True iff it is a power of 2
     */
    private static Boolean powerof2(int it) {
        if (it <= 0) {
            return false;
        }
        else {
            while (it != 1) {
                if (((it / 2) * 2) == it) {
                    it = it / 2;
                }
                else {
                    return false;
                }
            }
            return true;
        }
    }
}
