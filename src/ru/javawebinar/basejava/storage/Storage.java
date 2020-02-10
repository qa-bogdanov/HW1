package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;


public interface Storage {

    void save(Resume resume);

    void delete(String uuid);

    void update(Resume resume);

    void clear();

    int size();

    Resume get(String uuid);

    Resume[] getAll();
}
