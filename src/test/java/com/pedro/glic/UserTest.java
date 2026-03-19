package com.pedro.glic;

import com.pedro.glic.entity.User;
import com.pedro.glic.enums.DiabetesType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Pedro", "pedro@email.com", "123456",LocalDate.of(2004, 3, 19), LocalDate.of(2025, 8, 1), "31975877180", 15, DiabetesType.TYPE_1);
    }

    @Test
    void shouldReturnCorrectAge(){
        int age = user.getAge();
        assertEquals(22, age);
    }

    @Test
    void shouldReturnNullAgeWhenBirthdayIsNull(){
        user.setBirthday(null);
        assertNull(user.getAge());
    }

    @Test
    void shouldReturnTimeSinceDiagnosis(){
        String result = user.getTimeSinceDiagnosis();
        assertNotNull(result);
        assertTrue(result.contains("anos"));
        assertTrue(result.contains("mes"));
        assertTrue(result.contains("dias"));
    }

    @Test
    void shouldReturnNullTimeSinceDiagnosisWhenDiagnosisIsNull(){
        user.setDiagnostic(null);
        assertNull(user.getTimeSinceDiagnosis());
    }

    @Test
    void shouldReturnCorrectName(){
        String name = "Pedro";
        assertEquals(name, user.getName());
    }

}
