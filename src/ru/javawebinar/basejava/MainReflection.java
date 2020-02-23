package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume();

        Field field = resume.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println("Field: " + field.getName());
        System.out.println("Value: " + field.get(resume));
        field.set(resume, "new_uuid");
        System.out.println("After field.set(): " + resume);

        // TODO : invoke resume.toString via reflection
        System.out.println("-----------------------------");

        Method method = resume.getClass().getDeclaredMethod("toString");
        method.setAccessible(true);
        System.out.println("Class: " + method.getDeclaringClass());
        System.out.println("Method: " + method.getName());
        System.out.println("Result: " + method.invoke(resume));
    }

}
