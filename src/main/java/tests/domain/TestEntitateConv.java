package main.java.tests.domain;

import domain.Entitate;
import domain.EntitateConverter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEntitateConv extends Entitate {
    private String name;

    public TestEntitateConv(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "TestEntitate{id=" + getId() + ", name='" + name + "'}";
    }
}


// Clasă concretă pentru testare
class TestEntitateConverter extends EntitateConverter<TestEntitateConv> {

    @Override
    public String toString(TestEntitateConv entitate) {
        return entitate.getId() + "," + entitate.getName();
    }

    @Override
    public TestEntitateConv fromString(String string) {
        String[] tokens = string.split(",");
        int id = Integer.parseInt(tokens[0]);
        String name = tokens[1];
        return new TestEntitateConv(id, name);
    }
}

// Test JUnit

class EntitateConverterTest {

    @Test
    void testToStringAndFromString() {
        TestEntitateConverter converter = new TestEntitateConverter();
        TestEntitateConv entitate = new TestEntitateConv(1, "Test");

        // Test conversia în String
        String serialized = converter.toString(entitate);
        assertEquals("1,Test", serialized);

        // Test conversia înapoi în obiect
        TestEntitateConv deserialized = converter.fromString(serialized);
        assertEquals(entitate.getId(), deserialized.getId());
        assertEquals(entitate.getName(), deserialized.getName());
    }
}
