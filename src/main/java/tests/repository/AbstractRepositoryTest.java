package main.java.tests.repository;

import domain.TestEntitateConv;
import org.junit.jupiter.api.Test;
import repository.RepositoryExceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

// Clasă concretă pentru testare
class TestRepository extends AbstractRepository<TestEntitateConv> {

    @Override
    public void add(TestEntitateConv elem) throws RepositoryExceptions {
        if (data.stream().anyMatch(e -> e.getId() == elem.getId())) {
            throw new RepositoryExceptions("ID already exists");
        }
        data.add(elem);
    }

    @Override
    public void delete(TestEntitateConv elem) throws RepositoryExceptions {
        if (!data.remove(elem)) {
            throw new RepositoryExceptions("Element not found");
        }
    }

    @Override
    public TestEntitateConv find(int id) {
        return data.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }
}

// Test JUnit

class AbstractRepositoryTest {

    @Test
    void testAddAndFind() throws RepositoryExceptions {
        TestRepository repository = new TestRepository();
        TestEntitateConv e1 = new TestEntitateConv(1, "Entity1");
        TestEntitateConv e2 = new TestEntitateConv(2, "Entity2");

        // Test adăugare
        repository.add(e1);
        repository.add(e2);
        assertEquals(2, repository.size());

        // Test găsire
        assertEquals(e1, repository.find(1));
        assertEquals(e2, repository.find(2));
        assertNull(repository.find(3));
    }

    @Test
    void testDelete() throws RepositoryExceptions {
        TestRepository repository = new TestRepository();
        TestEntitateConv e1 = new TestEntitateConv(1, "Entity1");

        repository.add(e1);
        assertEquals(1, repository.size());

        // Test ștergere
        repository.delete(e1);
        assertEquals(0, repository.size());
    }
}
