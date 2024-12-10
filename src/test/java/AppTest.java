package com.javarush;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Юнит-тест для простого приложения.
 */
public class AppTest
        extends TestCase
{
    /**
     * Создание тестового примера
     *
     * @param testName название тестового примера
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return тестируемый набор тестов
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Строгий тест
     */
    public void testApp()
    {
        assertTrue( true );
    }
}