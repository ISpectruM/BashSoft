package main.bg.softuni.dataStructures;

import main.bg.softuni.contracts.SimpleOrderedBag;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class SimpleSortedList<E extends Comparable<E>> implements SimpleOrderedBag<E> {

    private static final int DEFAULT_SIZE = 16;

    private E[] innerCollection;
    private int size;
    private Comparator<E> comparator;

    public SimpleSortedList(Class<E> type, Comparator<E> comparator, int capacity) {
        this.initializeInnerCollection(type,capacity);
        this.comparator = comparator;
    }

    public SimpleSortedList(Class<E> type, int capacity){
        this(type,Comparable::compareTo, capacity);
    }

    public SimpleSortedList(Class<E> type, Comparator<E> comparator){
        this(type, comparator, DEFAULT_SIZE);
    }

    public SimpleSortedList(Class<E> type){
        this(type, Comparable::compareTo, DEFAULT_SIZE);
    }



    @SuppressWarnings("unchecked")
    private void initializeInnerCollection(Class<E> type, int capacity) {
        if (capacity < 0){
            throw new IllegalArgumentException("Capacity cannot be negative!");
        }

        this.innerCollection = (E[]) Array.newInstance(type,capacity);
    }

    @Override
    public void add(E element) {
        if (element == null){
            throw new IllegalArgumentException();
        }
        if (this.innerCollection.length <= this.size()){
            this.resize();
        }
        this.innerCollection[this.size()] = element;
        this.size++;
        Arrays.sort(this.innerCollection,0,this.size(),this.comparator);
    }

    @Override
    public void addAll(Collection<E> elements) {
        if (elements == null){
            throw new IllegalArgumentException();
        }
        if (this.size() + elements.size() >= this.innerCollection.length){
            this.multiResize(elements);
        }

        for (E element : elements) {
            this.innerCollection[this.size()] = element;
            this.size++;
        }

        Arrays.sort(this.innerCollection,0,this.size(),this.comparator);
    }

    private void multiResize(Collection<E> elements) {
        int newSize = this.innerCollection.length*2;
        while (this.size() + elements.size() >= newSize){
            newSize *= 2;
        }

        E[]newCollection =  Arrays.copyOf(this.innerCollection,newSize);
        this.innerCollection = newCollection;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public String joinWith(String joiner) {
        if (joiner == null){
            throw new IllegalArgumentException();
        }
        StringBuilder output = new StringBuilder();
        for (E e : this) {
            output.append(e)
                    .append(joiner);
        }
        output.setLength(output.length() - joiner.length());
        return output.toString();
    }

    @Override
    public boolean remove(E element) {
        if (element == null){
            throw new IllegalArgumentException();
        }

        boolean isRemoved = false;
        int removedElementIndex = 0;
        for (int i = 0; i < this.size; i++) {
            if (this.innerCollection[i].compareTo(element) == 0){
                this.innerCollection[i] = null;
                isRemoved = true;
                removedElementIndex = i;
            }
        }
        if (isRemoved){
            System.arraycopy(this.innerCollection,
                    removedElementIndex + 1,
                    this.innerCollection,
                    removedElementIndex,
                    this.size() - removedElementIndex);
            this.innerCollection[this.size()-1] = null;
            this.size--;
        }

        return isRemoved;
    }

    @Override
    public int capacity() {
        return this.innerCollection.length;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> iterator = new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < size();
            }

            @Override
            public E next() {
                return innerCollection[this.index++];
            }
        };
        return iterator;
    }

    private void resize(){
        E[] newCollection = Arrays.copyOf(this.innerCollection,this.innerCollection.length*2);
        this.innerCollection = newCollection;
    }

}
