package test.bg.softuni.dataStructures;

import main.bg.softuni.contracts.SimpleOrderedBag;
import main.bg.softuni.dataStructures.SimpleSortedList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleOrderedBagTests {

    public static final String[] BIGGER_THAN_INITIAL_SIZE_COLLECTION = {"h", "i", "j", "k", "l", "m", "n", "o", "p", "q","a", "b", "c", "d", "e", "f", "g"};
    public static final int EXPECTED_VALUE_ON_SINGLE_ADD = 1;
    public static final int CAPACITY_BIGGER_THAN_INITIAL = 30;
    public static final int ZERO_ELEMENTS = 0;
    public static final int INITIAL_CAPACITY = 16;
    public static final int EXPECTED_BIGGER_SIZE = 17;
    public static final int EXPECTED_TWO_ELEMENTS_SIZE = 2;
    public static final String EXPECTED_JOIN_RESULT = "A, B";
    private SimpleOrderedBag<String> name;

    @Before
    public void setUp(){
        this.name = new SimpleSortedList<>(String.class);
    }

    @Test
    public void testEmptyCtor(){
        Assert.assertEquals(INITIAL_CAPACITY, this.name.capacity());
        Assert.assertEquals(ZERO_ELEMENTS, this.name.size());
    }

    @Test
    public void testCtorWithInitialCapacity(){
        this.name = new SimpleSortedList<>(String.class,CAPACITY_BIGGER_THAN_INITIAL);
        Assert.assertEquals(CAPACITY_BIGGER_THAN_INITIAL,this.name.capacity());
        Assert.assertEquals(ZERO_ELEMENTS,this.name.size());
    }

    @Test
    public void testCtorWithInitialComparer(){
        this.name = new SimpleSortedList<>(
                String.class,
                String.CASE_INSENSITIVE_ORDER);
        Assert.assertEquals(INITIAL_CAPACITY, this.name.capacity());
        Assert.assertEquals(ZERO_ELEMENTS,this.name.size());
    }

    @Test
    public void testCtorWithAllParams(){
        this.name = new SimpleSortedList<>(
                String.class,
                String.CASE_INSENSITIVE_ORDER,
                CAPACITY_BIGGER_THAN_INITIAL);
        Assert.assertEquals(CAPACITY_BIGGER_THAN_INITIAL,
                this.name.capacity(),
                this.name.size());
        Assert.assertEquals(ZERO_ELEMENTS, this.name.size());
    }

    @Test
    public void testAddIncreasesSize(){
        this.name.add("Petyo");
        Assert.assertEquals(EXPECTED_VALUE_ON_SINGLE_ADD,this.name.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullElement(){
        this.name.add(null);
    }

    @Test
    public void testAddUnsortedDataIsHeldSorted(){
        this.name.add("Rosen");
        this.name.add("Georgi");
        this.name.add("Balkan");
        this.name.add("Balkan");
        String curr = null;
        for (String name : this.name) {
            if (curr!=null){
                if (curr.compareTo(name) > 0){
                    throw new RuntimeException();
                }
            }
            curr = name;
        }
    }

    @Test
    public void testAddingMoreThanInitialCapacity(){
        for (int i = 65; i < 82; i++) {
            char temp = (char)i;
            this.name.add(String.valueOf(temp));
        }
        Assert.assertEquals(
                "Wrong new size",
                EXPECTED_BIGGER_SIZE,
                this.name.size());

        Assert.assertTrue(
                "The capacity is not extended",
                this.name.capacity() != INITIAL_CAPACITY);
    }

    @Test
    public void testAddingAllFromCollectionIncreasesSize(){
        List<String> testCollection = new ArrayList<>();
        testCollection.add("Pesho");
        testCollection.add("Ivan");
        this.name.addAll(testCollection);
        Assert.assertEquals(
                "Wrong new size",
                EXPECTED_TWO_ELEMENTS_SIZE,
                this.name.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingAllFromNullThrowsException(){
        this.name.addAll(null);
    }

    @Test
    public void testAddAllKeepsSorted(){
        String currName = null;
        this.name.addAll(Arrays.asList(BIGGER_THAN_INITIAL_SIZE_COLLECTION));
        for (String name : this.name) {
            if (currName != null){
                if (currName.compareTo(name) > 0){
                    throw new RuntimeException();
                }
            }
            currName = name;
        }
    }

    @Test
    public void testRemoveValidElementDecreasesSize(){
        this.name.add("ivan");
        this.name.add("nasko");

        String elementToRemove = "ivan";
        this.name.remove(elementToRemove);

        boolean isRemoved = true;
        for (String s : this.name) {
            if (s.equals(elementToRemove)){
                isRemoved = false;
            }
        }
        Assert.assertTrue("The element is not removed",
                isRemoved);


    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemovingNullThrowsException(){
        this.name.remove(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testJoinWithNull(){
        this.name.addAll(Arrays.asList(BIGGER_THAN_INITIAL_SIZE_COLLECTION));
        this.name.joinWith(null);
    }

    @Test
    public void testJoinWorksFine(){
        this.name.add("A");
        this.name.add("B");
        String joinResult = this.name.joinWith(", ");
        Assert.assertEquals("Wrong joined elements",
                EXPECTED_JOIN_RESULT,
                joinResult);
    }
}
