package com.xdesign.munro.helper;

import com.xdesign.munro.domain.MunroData;
import com.xdesign.munro.parameters.Category;
import lombok.experimental.UtilityClass;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

@UtilityClass
public class PredicateHelper {

    public static List<Predicate<MunroData>> getPredicates(Float maxHeightInMeters, Float minHeightInMeters, Category categoryValue) {
        List<Predicate<MunroData>> predicates = new LinkedList<>();

        predicates.add(getCategoryPredicate(categoryValue));

        if (maxHeightInMeters != null) {
            predicates.add(getMaxHeightPredicate(maxHeightInMeters));
        }
        if (minHeightInMeters != null) {
            predicates.add(getMinHeightPredicate(minHeightInMeters));
        }
        return predicates;
    }

    private Predicate<MunroData> getCategoryPredicate(Category categoryValue) {
        return m -> categoryValue.name().equalsIgnoreCase(m.getPostYear1997());
    }

    private Predicate<MunroData> getMinHeightPredicate(Float minHeightInMeters) {
        return m -> m.getHeightInMeters() != null && minHeightInMeters <= m.getHeightInMeters();
    }

    private Predicate<MunroData> getMaxHeightPredicate(Float maxHeightInMeters) {
        return m -> m.getHeightInMeters() != null && maxHeightInMeters >= m.getHeightInMeters();
    }
}
