package com.xdesign.munro.service;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.xdesign.munro.domain.MunroData;
import com.xdesign.munro.helper.ComparatorHelper;
import com.xdesign.munro.helper.PredicateHelper;
import com.xdesign.munro.parameters.Category;
import com.xdesign.munro.parameters.SortDirection;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class MunroService {

    private static final String FILE_PATH = "data/munro_data.csv";

    private List<MunroData> munroData;

    /**
     * Loads munro data from data/munro_data.csv
     * @throws IOException
     */
    @PostConstruct
    public void init() throws IOException {
        File file = new ClassPathResource(FILE_PATH).getFile();

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(MunroData.class).withHeader();
        ObjectReader reader = csvMapper.readerFor(MunroData.class).with(csvSchema);
        munroData = reader.<MunroData>readValues(new FileReader(file)).readAll()
                //Exclude all entries which don't have a running number
                .stream().filter(m -> m.getRunningNo() != null)
                .collect(toList());

    }

    public List<MunroData> findMunros(Category category,
                                      Float maxHeightInMeters,
                                      Float minHeightInMeters,
                                      SortDirection heightSort,
                                      SortDirection nameSort,
                                      Optional<Long> limit) {

        Category categoryValue = Objects.requireNonNullElse(category, Category.MUN);
        SortDirection heightSortValue = Objects.requireNonNullElse(heightSort, SortDirection.DESC);
        SortDirection nameSortValue = Objects.requireNonNullElse(nameSort, SortDirection.ASC);

        List<Predicate<MunroData>> predicates = PredicateHelper.getPredicates(maxHeightInMeters, minHeightInMeters, categoryValue);

        return munroData.parallelStream()
                .filter(predicates.stream().reduce(x -> true, Predicate::and))
                .sorted(getComparator(heightSortValue, nameSortValue))
                .limit(limit.orElse(Long.MAX_VALUE))
                .collect(toList());
    }

    /**
     *
     * @param heightSort
     * @param nameSort
     * @return comparator by height and name
     */
    private Comparator<MunroData> getComparator(SortDirection heightSort, SortDirection nameSort) {
        Comparator<MunroData> heightComparator = ComparatorHelper.getHeightComparator(heightSort);
        Comparator<MunroData> nameComparator = ComparatorHelper.getNameComparator(nameSort);
        return heightComparator.thenComparing(nameComparator);
    }

}
