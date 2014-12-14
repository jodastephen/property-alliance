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
 * Immutable bean with a builder for construction.
 * This is not compatible with JavaBeans v1.0.
 */
public class BuilderImmutablePerson implements Serializable {

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
     * @return the builder
     */
    public Builder builder() {
        return new Builder();
    }

    /**
     * Creates a person.
     * 
     * @param surname  the surname, not null
     * @param forename  the forename, not null
     * @param birthDate  the birth date, not null
     * @param numberOfChildren  the number of children, zero or greater
     */
    private BuilderImmutablePerson(
            String surname,
            String forename,
            LocalDate birthDate,
            int numberOfChildren) {
        Objects.requireNonNull(surname, "surname");
        Objects.requireNonNull(forename, "forename");
        Objects.requireNonNull(birthDate, "birthDate");
        if (numberOfChildren < 0) {
            throw new IllegalArgumentException("Number of children must be zero or greater");
        }
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
        BuilderImmutablePerson other = (BuilderImmutablePerson) obj;
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

    //-----------------------------------------------------------------------
    /**
     * The builder class.
     * <p>
     * Each builder method may be called more than once, with the last call
     * used when building.
     */
    public static final class Builder {
        
        /**
         * The surname.
         */
        private String surname;
        /**
         * The forename.
         */
        private String forename;
        /**
         * The date of birth.
         */
        private LocalDate birthDate;
        /**
         * The number of children.
         */
        private int numberOfChildren;

        /**
         * Creates the builder.
         */
        private Builder() {
        }

        /**
         * Sets the value of the surname in the builder.
         * 
         * @param surname  the surname
         * @return the builder, for method chaining
         */
        public Builder surname(String surname) {
            this.surname = Objects.requireNonNull(surname, "surname");
            return this;
        }

        /**
         * Sets the value of the surname in the builder.
         * 
         * @param surname  the surname
         * @return the builder, for method chaining
         */
        public Builder forename(String surname) {
            this.forename = Objects.requireNonNull(forename, "forename");
            return this;
        }

        /**
         * Sets the value of the surname in the builder.
         * 
         * @param birthDate  the date of birth
         * @return the builder, for method chaining
         */
        public Builder birthDate(LocalDate birthDate) {
            this.birthDate = Objects.requireNonNull(birthDate, "birthDate");
            return this;
        }

        /**
         * Sets the value of the surname in the builder.
         * 
         * @param numberOfChildren  the number of children
         * @return the builder, for method chaining
         */
        public Builder numberOfChildren(int numberOfChildren) {
            if (numberOfChildren < 0) {
                throw new IllegalArgumentException("Number of children must be zero or greater");
            }
            this.numberOfChildren = numberOfChildren;
            return this;
        }

        /**
         * Builds the bean from the builder.
         * <p>
         * The builder may still be used once this method is called.
         * 
         * @return the builder
         */
        public BuilderImmutablePerson build() {
            return new BuilderImmutablePerson(surname, forename, birthDate, numberOfChildren);
        }
    }

}
