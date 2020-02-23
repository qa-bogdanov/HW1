package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.*;
import ru.javawebinar.basejava.model.Resume;

import org.junit.After;
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

    /*
      Мне кажется, что в этой секции нельзя подготавливать данные с помощью метода который мы собираеся тестировать.
      Она нужна для первоначальной подготовки данных, моков, инициализации каких-то зависимостей.
      Если готовить данные тем методом, который будет тестироваться, то дальнейшую проверку нельзя считать честной.
      Это примерно как реализовать калькулятор, на нем выполнить операцию сложения и результат этой операции считать
      эталонным значением в тестах.
      Плюс такая подготовка усложняет разработку тестов, зачем для всех кейсов иметь именно три созданных резюме?
      Причем созданных методом, который еще не протестирован.
      В общем сейчас оставляю только очистку, после проверки дз скорее всего придется вернуть
    */
    @Before
    public void setUp() throws Exception {
        storage.clear();
    }

    @After
    public void tearDown() throws Exception {
        storage.clear();
    }

    // Save
    @Test
    public void save() {
        storage.save(RESUME);
        assertEquals(RESUME, storage.get(UUID));
        assertEquals(UUID, storage.get(UUID).toString());
    }

    @Test
    public void saveRandomUUID() {
        storage.save(new Resume());
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
        storage.delete("test");
    }

    // Update
    @Test
    public void update() {
        storage.save(RESUME);
        storage.update(RESUME);
        assertEquals(RESUME, storage.get(UUID));
        assertEquals(UUID, storage.get(UUID).toString());
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
        assertEquals(UUID, storage.get(UUID).toString());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("test");
    }

    // getAll
    @Test
    public void getAll() {
        String[] resume = {"r1", "r2", "r3", "r4"};
        for (int i = 0; i < resume.length; i++) {
            storage.save(new Resume(resume[i]));
            assertEquals(i + 1, storage.getAll().length);
        }

        Resume[] arrayResume = storage.getAll();
        for (int i = 0; i < resume.length; i++) {
            assertEquals(new Resume(resume[i]), arrayResume[i]);
            assertEquals(resume[i], arrayResume[i].toString());
        }
    }
}
