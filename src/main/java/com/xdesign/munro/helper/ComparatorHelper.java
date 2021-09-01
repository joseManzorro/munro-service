package com.xdesign.munro.helper;

import com.xdesign.munro.domain.MunroData;
import com.xdesign.munro.parameters.SortDirection;
import lombok.experimental.UtilityClass;

import java.util.Comparator;

import static java.util.Comparator.reverseOrder;

@UtilityClass
public class ComparatorHelper {

    public Comparator<MunroData> getHeightComparator(SortDirection heightSort) {
        Comparator<MunroData> heightComparator = Comparator.comparing(MunroData::getHeightInMeters);
        if (heightSort.equals(SortDirection.DESC)) {
            heightComparator = Comparator.comparing(MunroData::getHeightInMeters, reverseOrder());
        }
        return heightComparator;
    }

    public Comparator<MunroData> getNameComparator(SortDirection nameSort) {
        Comparator<MunroData> nameComparator = Comparator.comparing(MunroData::getName);
        if (nameSort.equals(SortDirection.DESC)) {
            nameComparator = Comparator.comparing(MunroData::getName, reverseOrder());
        }
        return nameComparator;
    }
}
