/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.test;

import java.util.Random;

/**
 * Random data generator.
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class RandomDataGenerator {

    private Random random;

    /**
     * Creates a new random data generator.
     */
    public RandomDataGenerator() {
        this.random = new Random();
    }

    /**
     * Generates a random long.
     *
     * @return a long
     */
    public long getLong() {
        return this.random.nextLong();
    }

    /**
     * Generates a random integer.
     *
     * @return an integer
     */
    public int getInt() {
        return this.random.nextInt();
    }

    /**
     * Generates a positive integer.
     *
     * @return a positive integer
     */
    public int getPositiveInt() {
        return this.random.nextInt(Integer.MAX_VALUE);
    }

    /**
     * Fills the given array with random bytes.
     * 
     * @param bytes the array to fill with random bytes
     */
    public void generateBytes(byte[] bytes) {
        this.random.nextBytes(bytes);
    }

    /**
     * Generates a random boolean.
     * 
     * @return a boolean
     */
    public boolean getBoolean() {
        return this.random.nextBoolean();
    }

    /**
     * Generates one of the constants of the given enum class.
     *
     * @param <E> the enum type
     * @param enumClass the enum class
     * @return an enum constant based on the given ID value
     * @throws NullPointerException if enumClass is null
     * @throws IllegalArgumentException if enum type does not define any constants
     */
    public <E extends Enum<E>> E getEnum(Class<E> enumClass) throws NullPointerException, IllegalArgumentException {

        E[] values = enumClass.getEnumConstants();

        if (values.length == 0) {
            throw new IllegalArgumentException("enum type must define at least one constant");
        }

        int index = Math.abs(getInt() % values.length);

        return values[index];
    }
}
