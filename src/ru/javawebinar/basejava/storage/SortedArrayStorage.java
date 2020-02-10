package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected void saveResume(Resume resume) {
        /*
            как быть в случае когда в одну реализацию требуется передать только объект, а в другую объект и индекс?
            если передевать объект и индекс, то получится путаница в абстрактном классе
            если не передавать, то повторно вызывается getIndex()
        */
        int index = - 1 - (getIndex(resume.getUuid())); // через неделю я не пойму эту логику...
        /*
        System.out.println("index: " + index);
        System.out.println("index + 1: " + (index + 1));
        System.out.println("size: " + size);
        System.out.println("size - index: " + (size - index));
         */
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
        //size++;
    }

    protected void deleteResume(int index) {
        int binaryIndex = size - index - 1;
        /*
        System.out.println("index: " + index);
        System.out.println("size: " + size);
        System.out.println("binaryIndex: " + binaryIndex);
        System.out.println("index + 1: " + (index + 1));
         */
        System.arraycopy(storage, index + 1, storage, index, binaryIndex);
    }


    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

}
