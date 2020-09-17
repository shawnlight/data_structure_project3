import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// -------------------------------------------------------------------------

/**
 * the parser class
 *
 * @author Ziqi Xiao
 * @version 2020 fall
 */
public class Parser {
    private World myWorld;

    /**
     * the parser constructor
     *
     * @param world
     *            the world database
     * 
     */

    public Parser(World world) {
        this.myWorld = world;
    }


    /**
     * the parser constructor
     * 
     * @param theFile
     *            File locate at specific path
     *
     * @return boolean
     *         return true if the file is read
     * 
     */
    public boolean readCmdFile(File theFile) throws IOException {
        String type;
        Scanner sc = new Scanner(theFile);
        while (sc.hasNext()) { // While the scanner has information to read
            String cmd = sc.next(); // Read the next term
            if (cmd.equals("add")) {
                String nameString = sc.nextLine().trim();
                myWorld.insert(nameString);
            }
            else if (cmd.equals("update")) { // Found an update command
                type = sc.next();
                if (type.equals("add")) {
                    String pattern =
                        "\\s*(.*)\\s*(<SEP>)\\s*(.*)\\s*(<SEP>)\\s*(.*)";
                    Pattern r = Pattern.compile(pattern);
                    String input = sc.nextLine().trim();
                    Matcher m = r.matcher(input);
                    m.find();
                    String name = m.group(1).trim();
                    String fieldName = m.group(3).trim();
                    String fieldValue = m.group(5).trim();
                    myWorld.updateAdd(name, fieldName, fieldValue);
                }
                else if (type.equals("delete")) {
                    String pattern = "\\s*(.*)\\s*(<SEP>)\\s*(.*)";
                    Pattern r = Pattern.compile(pattern);
                    String input = sc.nextLine().trim();
                    Matcher m = r.matcher(input);
                    m.find();
                    String nameString = m.group(1).trim();
                    String fieldNameString = m.group(3).trim();
                    myWorld.updateDelete(nameString, fieldNameString);
                }
                else {
                    System.out.println("Bad update option |" + type + "|");
                }
            }
            else if (cmd.equals("delete")) {
                String nameString = sc.nextLine().trim();
                myWorld.delete(nameString);
            }
            else if (cmd.equals("print")) {
                type = sc.next();
                myWorld.print(type);
            }
            else {
                System.out.println("Unrecognized input: |" + cmd + "|");
            }
        }
        sc.close();
        return true;
    }

}
