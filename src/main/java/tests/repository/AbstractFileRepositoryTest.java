package main.java.tests.repository;

import domain.TestEntitateConv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Clasă concretă pentru testare
class TestFileRepository extends AbstractFileRepository<TestEntitateConv> {
    TestEntitateConv[] data = new TestEntitateConv[0];
    public TestFileRepository(String fileName) {
        super(fileName);
    }

    @Override
    protected void saveFile() throws RepositoryExceptions {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (TestEntitateConv entitate : data) {
                writer.write(entitate.getId() + "," + entitate.getName());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RepositoryExceptions("Error saving file");
        }
    }

    @Override
    protected void loadFile() throws RepositoryExceptions {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                int id = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                //data.add(new TestEntitateConv(id, name));
            }
        } catch (IOException e) {
            throw new RepositoryExceptions("Error loading file");
        }
    }
}

// Test JUnit

class AbstractFileRepositoryTest {

    private static final String FILE_NAME = "test_repo.txt";
    private TestFileRepository repository;

    @BeforeEach
    void setUp() {
        repository = new TestFileRepository(FILE_NAME);
    }

    @AfterEach
    void tearDown() {
        new File(FILE_NAME).delete();
    }

    @Test
    void testAddAndSaveFile() throws RepositoryExceptions {
        TestEntitateConv e1 = new TestEntitateConv(1, "Entity1");
        TestEntitateConv e2 = new TestEntitateConv(2, "Entity2");

        repository.add(e1);
        repository.add(e2);

        // Reîncarcă repository-ul din fișier
        TestFileRepository newRepository = new TestFileRepository(FILE_NAME);
     //   assertEquals(2, newRepository.size());
     //   assertEquals(e1.getName(), newRepository.find(1).getName());
       // assertEquals(e2.getName(), newRepository.find(2).getName());
    }


}
