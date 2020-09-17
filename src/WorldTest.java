import student.TestCase;

/**
 * unit test for world database
 * 
 * @author light
 * @version 2020 fall
 * 
 */
public class WorldTest extends TestCase {
    /**
     * world database test
     * insert, delete ,update method
     * 
     */
    public void testInsert() {
        World world = new World(10, 32);

        assertNotNull(world);
        world.insert("light god");
        world.insert("light      god");
        world.insert("dark   god");
        world.delete("light");
        world.delete("dark   god");
        world.insert("light sb");
        world.updateAdd("light", "gene", "person");
        world.updateAdd("light   god", "gene", "person");
        world.updateAdd("light god", "height", "1.75");
        world.updateAdd("light god", "gene", "beast");
        world.updateAdd("light     god", "hobby", "game");
        world.updateAdd("light god", "gene", "nothing");
        world.updateDelete("light god", "weight");
        world.updateDelete("light", "gene");
        world.updateDelete("light god", "height");
        world.updateDelete("light god", "gene");
        world.updateDelete("light god", "hobby");
        world.updateAdd("light god", "hobby", "novel");
        world.updateAdd("light   god", "gene", "beast");

        World worldx = new World(10, 32);
        worldx.insert("light is a phucking SB");
        worldx.insert("dark is coming");
        worldx.insert("darkness come");
        worldx.insert("light come");
        worldx.delete("light come");
        worldx.insert("darkness come");

    }
}
