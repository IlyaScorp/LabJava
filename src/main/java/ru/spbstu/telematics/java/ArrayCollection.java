package ru.spbstu.telematics.java;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class ArrayCollection<Item> {

    private static final int DEFAULT_CAPACITY = 4;
    private Item[] elementData;
    private int size = 0;

    @SuppressWarnings("unchecked")
    ArrayCollection() {
        this.elementData = (Item[]) new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    ArrayCollection(int capacity){
        if (capacity <= 0){
            throw new IllegalArgumentException();
        }

        this.elementData = (Item[]) new Objects[capacity];
    }


    public void add(int idx, Item item) {
        if (0 > idx || idx > size) {
            throw new IndexOutOfBoundsException();
        }
        if (elementData.length <= size) {
            grow();
        }
        if (size == idx) {
            elementData[size++] = item;
        } else {
            System.arraycopy(elementData, idx, elementData, idx + 1, size - idx);
            elementData[idx] = item;
            size++;
        }


    }

    public boolean add(Item item) {
        add(size, item);
        return true;
    }

    public Item remove(int idx) {

        if (0 > idx || idx >= size) {
            throw new IndexOutOfBoundsException();
        }
        Item temp = elementData[idx];

            size--;
            shift(idx);
            return temp;

    }

    public boolean remove(Item item) {
        if (elementData.length > size * 4){
            shrink();
        }
        for (int i = 0; i < size; i++) {
            if (item.equals(elementData[i])) {
                shift(i);
                size--;
                return true;
            }
        }
        return false;
    }

    private void shift(int idx) {

        System.arraycopy(elementData, idx + 1, elementData, idx, size - idx);
    }

    public Item get(int idx) {
        if (idx < 0 || idx >= size){
            throw new IndexOutOfBoundsException();
        }
        return elementData[idx];
    }

    public boolean contains(Item item) {
        if (Objects.isNull(item)){
            throw new  NullPointerException();
        }
        for (int i = 0; i < size; i++) {
            if (item.equals(elementData[i])){
                return true;
            }
        }
        return false;
    }

    public int size() {

        return size;
    }


    @SuppressWarnings("unchecked")
    private void grow() {
        /**
         * Если массив заполнился,
         * то увеличить его размер в полтора раз
         */
        changeCapacity(elementData.length / 2 + elementData.length);

    }

    private void shrink() {
        /**
         * Если количество элементов в четыре раза меньше,
         * то уменьшить его размер в два раза
         */
        changeCapacity(elementData.length / 2);
    }

    private void changeCapacity(int newCapacity) {
        elementData = Arrays.copyOf(elementData, newCapacity);

    }


    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {

        private int currentPosition = 0;

        @Override
        public boolean hasNext() {
            return currentPosition != size;
        }

        @Override
        public Item next() {
            return elementData[currentPosition++];
        }

    }

}
