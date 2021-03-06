/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.jpa.jpql;

import javax.annotation.Nonnull;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;

import org.opendaylight.persistence.util.common.filter.EqualityCondition;
import org.opendaylight.persistence.util.common.filter.StringCondition;

import com.google.common.base.Preconditions;

/**
 * String condition predicate.
 * 
 * @param <P>
 *            type of the entity type of the entity (an object annotated with {@link javax.persistence.Entity})
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
class JpqlStringCondition<P> implements JpqlPredicate {

    private JpqlPredicate delegate;

    /**
     * Creates an equality predicate.
     * 
     * @param condition
     *            string condition
     * @param attribute
     *            attribute to apply the condition to
     * @param entityClass
     *            persistent object class
     */
    public JpqlStringCondition(@Nonnull StringCondition condition,
            @Nonnull SingularAttribute<? super P, String> attribute,
            @Nonnull Class<P> entityClass) {
        Preconditions.checkNotNull(condition, "condition");
        Preconditions.checkNotNull(attribute, "atttibute");
        Preconditions.checkNotNull(entityClass, "entityClass");

        switch (condition.getMode()) {
            case EQUAL: {
                EqualityCondition<String> equalityCondition = EqualityCondition
                        .equalTo(condition.getValue());
                this.delegate = new JpqlEqualityCondition<P, String>(
                        equalityCondition, attribute, entityClass);
            }
                break;
            case UNEQUAL: {
                EqualityCondition<String> equalityCondition = EqualityCondition
                        .unequalTo(condition.getValue());
                this.delegate = new JpqlEqualityCondition<P, String>(
                        equalityCondition, attribute, entityClass);
            }
                break;
            case STARTS_WITH:
            case CONTAINS:
            case ENDS_WITH:
                this.delegate = new SubstringPredicate<P>(condition, attribute,
                        entityClass);
                break;
        }
    }

    @Override
    public String getPredicate() {
        return this.delegate.getPredicate();
    }

    @Override
    public void addParameters(Query query) {
        this.delegate.addParameters(query);
    }

    private static class SubstringPredicate<P> implements JpqlPredicate {

        private static final char WILDCARD = '%';

        private Class<P> entityClass;
        private StringCondition condition;
        private SingularAttribute<? super P, String> attribute;

        public SubstringPredicate(StringCondition condition,
                SingularAttribute<? super P, String> attribute,
                Class<P> entityClass) {
            this.condition = Preconditions.checkNotNull(condition, "condition");
            this.attribute = Preconditions.checkNotNull(attribute, "attribute");
            this.entityClass = Preconditions.checkNotNull(entityClass,
                    "entityClass");
        }

        @Override
        public String getPredicate() {
            String attributeNameInQuery = JpqlUtil.getNameInQuery(
                    this.attribute, this.entityClass);

            StringBuilder str = new StringBuilder(64);

            str.append(attributeNameInQuery);
            str.append(" Like '");
            switch (this.condition.getMode()) {
                case EQUAL:
                    // Ignored since this case is not possible
                    break;
                case UNEQUAL:
                    // Ignored since this case is not possible
                    break;
                case STARTS_WITH:
                    str.append(this.condition.getValue());
                    str.append(WILDCARD);
                    break;
                case CONTAINS:
                    str.append(WILDCARD);
                    str.append(this.condition.getValue());
                    str.append(WILDCARD);
                    break;
                case ENDS_WITH:
                    str.append(WILDCARD);
                    str.append(this.condition.getValue());
                    break;
            }
            str.append('\'');

            return str.toString();
        }

        @Override
        public void addParameters(Query query) {
        }
    }
}
