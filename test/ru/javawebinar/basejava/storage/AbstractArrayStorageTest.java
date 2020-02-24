package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.*;
import ru.javawebinar.basejava.model.Resume;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    private static final String UUID = "test_uuid";
    private static final Resume RESUME = new Resume(UUID);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
    }

    // Save
    @Test
    public void save() {
        storage.save(RESUME);
        assertEquals(RESUME, storage.get(UUID));
        assertEquals(1, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME);
        storage.save(RESUME);
    }

    @Test(expected = StorageException.class)
    public void saveWithStorageOverflow() {
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            fail("Storage overflow");
        }
        storage.save(new Resume());
    }

    // Delete
    @Test
    public void delete() {
        storage.save(RESUME);
        assertEquals(1, storage.size());
        storage.delete(UUID);
        assertEquals(0, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID);
    }

    // Update
    @Test
    public void update() {
        storage.save(RESUME);
        storage.update(RESUME);
        assertEquals(RESUME, storage.get(UUID));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME);
    }

    // Clear
    @Test
    public void clear() {
        storage.save(RESUME);
        assertEquals(1, storage.size());
        storage.clear();
        assertEquals(0, storage.size());
    }

    // Size
    @Test
    public void size() {
        storage.save(new Resume());
        assertEquals(1, storage.size());
        storage.save(new Resume());
        assertEquals(2, storage.size());
        storage.save(new Resume());
        assertEquals(3, storage.size());
    }

    // Get
    @Test
    public void get() {
        storage.save(RESUME);
        assertEquals(RESUME, storage.get(UUID));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID);
    }

    // getAll
    @Test
    public void getAll() {
        String[] uuids = {"r1", "r2", "r3", "r4"};
        for (int i = 0; i < uuids.length; i++) {
            storage.save(new Resume(uuids[i]));
            assertEquals(i + 1, storage.getAll().length);
        }

        Resume[] resumes = storage.getAll();
        for (int i = 0; i < uuids.length; i++) {
            assertEquals(new Resume(uuids[i]), resumes[i]);
        }
    }
}