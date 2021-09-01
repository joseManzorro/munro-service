package com.xdesign.munro.service;

import com.xdesign.munro.domain.MunroData;
import com.xdesign.munro.parameters.Category;
import com.xdesign.munro.parameters.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.xdesign.munro.parameters.SortDirection.ASC;
import static org.assertj.core.api.Assertions.assertThat;

class MunroServiceTest {

    private MunroService munroService;

    @BeforeEach
    void setUp() {
        MunroData munro1 = createMunro("1", "A", Category.MUN, 900f);
        MunroData munro2 = createMunro("2", "B", Category.TOP, 1000f);
        MunroData munro3 = createMunro("3", "A", Category.MUN, 950f);
        MunroData munro4 = createMunro("4", "C", Category.MUN, 950f);

        munroService = new MunroService(List.of(munro1, munro2, munro3, munro4));
    }

    @ParameterizedTest
    @CsvSource(
            // limit | expectedRunningNo
            { "      ,      '1,3,4'     ",
              "  0   ,        ''        ",
              "  1   ,        '1'       ",
              "  3   ,      '1,3,4'     ",
              "  4   ,      '1,3,4'     ",
              "  5   ,      '1,3,4'     ",
              }
    )
    void testLimit( Long limit, String expectedRunningNo) {
        List<MunroData> matchingMunros = munroService.findMunros(Category.MUN, 1100f, 900f, ASC, ASC, Optional.ofNullable(limit));

        String actualRunningNoList = matchingMunros.stream().map(MunroData::getRunningNo).collect(Collectors.joining(","));
        assertThat(actualRunningNoList).isEqualTo(expectedRunningNo);
    }

    @ParameterizedTest
    @CsvSource(
            // category | expectedRunningNo
            { "         ,     '3,4,1'     ",
              "  MUN    ,     '3,4,1'     ",
              "  TOP    ,       '2'       " }
    )
    void testCategoryFilter(Category category, String expectedRunningNo) {
        List<MunroData> matchingMunros = munroService.findMunros(category, null, null, null, null, Optional.empty());

        String actualRunningNoList = matchingMunros.stream().map(MunroData::getRunningNo).collect(Collectors.joining(","));
        assertThat(actualRunningNoList).isEqualTo(expectedRunningNo);
    }


    @ParameterizedTest
    @CsvSource(
            // maxHeightInMeters | minHeightInMeters | expectedRunningNo
            { "                  ,                   ,     '3,4,1'     ",
              "                  ,      500          ,     '3,4,1'     ",
              "                  ,      900          ,     '3,4,1'     ",
              "                  ,     1200          ,       ''        ",
              "                  ,                   ,     '3,4,1'     ",
              "       500        ,                   ,       ''        ",
              "      1000        ,                   ,     '3,4,1'     ",
              "      1200        ,                   ,     '3,4,1'     "}
    )
    void testMaxAndMinHeightFilters(Float maxHeightInMeters, Float minHeightInMeters, String expectedRunningNo) {
        List<MunroData> matchingMunros = munroService.findMunros(null, maxHeightInMeters, minHeightInMeters, null, null, Optional.empty());

        String actualRunningNoList = matchingMunros.stream().map(MunroData::getRunningNo).collect(Collectors.joining(","));
        assertThat(actualRunningNoList).isEqualTo(expectedRunningNo);
    }

    @ParameterizedTest
    @CsvSource(
            // heightSort | nameSort | expectedRunningNo
            { "           ,          ,     '3,4,1'     ",
              "   ASC     ,          ,     '1,3,4'     ",
              "   DESC    ,          ,     '3,4,1'     ",
              "           ,    ASC   ,     '3,4,1'     ",
              "           ,    DESC   ,    '4,3,1'     ",
              "   ASC     ,    ASC   ,     '1,3,4'     ",
              "   ASC     ,    DESC  ,     '1,4,3'     ",
              "   DESC    ,    ASC   ,     '3,4,1'     ",
              "   DESC    ,    DESC  ,     '4,3,1'     ",
            }
    )
    void testSortByHeightAndName(SortDirection heightSort, SortDirection nameSort, String expectedRunningNo) {
        List<MunroData> matchingMunros = munroService.findMunros(null, null, null, heightSort, nameSort, Optional.empty());

        String actualRunningNoList = matchingMunros.stream().map(MunroData::getRunningNo).collect(Collectors.joining(","));
        assertThat(actualRunningNoList).isEqualTo(expectedRunningNo);
    }

    @ParameterizedTest
    @CsvSource(
           //category | maxHeightInMeters | minHeightInMeters | heightSort | nameSort | limit | expectedRunningNo
            {  "MUN   ,       800         ,       100         ,    ASC     ,   ASC    ,   3   ,      ''       ",
               "MUN   ,      1200         ,      1100         ,    ASC     ,   ASC    ,   3   ,      ''       ",
               "MUN   ,       950         ,       100         ,    ASC     ,   ASC    ,   2   ,     '1,3'     ",
               "MUN   ,       950         ,       100         ,    ASC     ,   ASC    ,   3   ,    '1,3,4'    ",
               "MUN   ,       950         ,       100         ,    ASC     ,   ASC    ,   4   ,    '1,3,4'    ",
               "MUN   ,      1100         ,       900         ,    ASC     ,   ASC    ,   3   ,    '1,3,4'    ",
               "MUN   ,      1100         ,       100         ,    ASC     ,   ASC    ,   3   ,    '1,3,4'    ",
               "MUN   ,      1100         ,       100         ,    DESC    ,   ASC    ,   3   ,    '3,4,1'    ",
               "MUN   ,      1100         ,       100         ,    DESC    ,   DESC   ,   3   ,    '4,3,1'    ",
               "MUN   ,      1000         ,       950         ,    ASC     ,   ASC    ,   3   ,     '3,4'     ",
               "MUN   ,      1000         ,       950         ,    ASC     ,   DESC   ,   3   ,     '4,3'     ",
               "TOP   ,      1200         ,      1100         ,    ASC     ,   ASC    ,   3   ,      ''       ",
               "TOP   ,      1000         ,       100         ,    ASC     ,   ASC    ,   3   ,      '2'      ",
               "TOP   ,      1100         ,      1000         ,    ASC     ,   ASC    ,   3   ,      '2'      ",
               "TOP   ,      1100         ,      900         ,     ASC     ,   ASC     ,  3   ,      '2'      ",}
    )
    void testMultipleFilters(Category category, Float maxHeightInMeters, Float minHeightInMeters, SortDirection heightSort, SortDirection nameSort, Long limit, String expectedRunningNo) {
        List<MunroData> matchingMunros = munroService.findMunros(category, maxHeightInMeters, minHeightInMeters, heightSort, nameSort, Optional.ofNullable(limit));

        String actualRunningNoList = matchingMunros.stream().map(MunroData::getRunningNo).collect(Collectors.joining(","));
        assertThat(actualRunningNoList).isEqualTo(expectedRunningNo);
    }

    private MunroData createMunro(String number, String name, Category category, Float heightInMeters) {
        MunroData munroData = new MunroData();
        munroData.setRunningNo(number);
        munroData.setName(name);
        munroData.setPostYear1997(category.name());
        munroData.setHeightInMeters(heightInMeters);
        return munroData;
    }

}