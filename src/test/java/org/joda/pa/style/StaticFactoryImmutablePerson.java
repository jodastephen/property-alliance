/*
 *  Copyright 2014-present Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.joda.pa.style;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Immutable bean with a static factory.
 * This is not compatible with JavaBeans v1.0.
 */
public class StaticFactoryImmutablePerson implements Serializable {

    /**
     * Serialization version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The surname.
     */
    private final String surname;
    /**
     * The forename.
     */
    private final String forename;
    /**
     * The date of birth.
     */
    private final LocalDate birthDate;
    /**
     * The number of children.
     */
    private final int numberOfChildren;

    //-----------------------------------------------------------------------
    /**
     * Creates a person.
     * 
     * @param surname  the surname, not null
     * @param forename  the forename, not null
     * @param birthDate  the birth date, not null
     * @param numberOfChildren  the number of children, zero or greater
     * @return the person, not null
     */
    public static StaticFactoryImmutablePerson of(
            String surname,
            String forename,
            LocalDate birthDate,
            int numberOfChildren) {
        Objects.requireNonNull(surname, "surname");
        Objects.requireNonNull(forename, "forename");
        Objects.requireNonNull(birthDate, "birthDate");
        return new StaticFactoryImmutablePerson(surname, forename, birthDate, numberOfChildren);
    }

    /**
     * Creates a person.
     * 
     * @param surname  the surname, not null
     * @param forename  the forename, not null
     * @param birthDate  the birth date, not null
     * @param numberOfChildren  the number of children, zero or greater
     */
    private StaticFactoryImmutablePerson(
            String surname,
            String forename,
            LocalDate birthDate,
            int numberOfChildren) {
        this.surname = surname;
        this.forename = forename;
        this.birthDate = birthDate;
        this.numberOfChildren = numberOfChildren;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the surname.
     * 
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Gets the forename.
     * 
     * @return the forename
     */
    public String getForename() {
        return forename;
    }

    /**
     * Gets the birth date.
     * 
     * @return the birthDate
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Gets the number of children.
     * 
     * @return the numberOfChildren
     */
    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    //-----------------------------------------------------------------------
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StaticFactoryImmutablePerson other = (StaticFactoryImmutablePerson) obj;
        return Objects.equals(surname, other.surname) &&
                Objects.equals(forename, other.forename) &&
                Objects.equals(birthDate, other.birthDate) &&
                numberOfChildren == other.numberOfChildren;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(surname);
        result = prime * result + Objects.hashCode(forename);
        result = prime * result + Objects.hashCode(birthDate);
        result = prime * result + numberOfChildren;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Person[surname=").append(surname)
                .append(", forename=").append(forename).append(", birthDate=")
                .append(birthDate).append(", numberOfChildren=")
                .append(numberOfChildren).append("]");
        return builder.toString();
    }

}
