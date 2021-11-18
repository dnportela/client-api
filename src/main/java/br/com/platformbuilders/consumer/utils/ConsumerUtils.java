package br.com.platformbuilders.consumer.utils;

import java.time.LocalDate;
import java.time.Period;

import static java.util.Objects.isNull;

public class ConsumerUtils {

    public static Integer calculateAge(LocalDate birthDate) {
        return (isNull(birthDate)) ? 0 :
                Period.between(birthDate, LocalDate.now()).getYears();
    }
}
