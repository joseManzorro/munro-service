package com.xdesign.munro.api;

import com.xdesign.munro.domain.MunroData;
import com.xdesign.munro.parameters.Category;
import com.xdesign.munro.parameters.SortDirection;
import com.xdesign.munro.service.MunroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static com.xdesign.munro.api.MunroController.MUNROS_API_PATH;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MunroControllerTest {

    @Autowired
    protected MockMvc mvc;

    @MockBean
    private MunroService munroService;

    @Test
    void givenInvalidCategoryShouldReturnExpectedErrorResponse() throws Exception {
        //Given
        String invalidCategory = "invalid-category";
        //When
        ResultActions performGet = mvc.perform(get(MUNROS_API_PATH)
                .param("category", invalidCategory));
        //Then
        performGet
                .andExpect(content().string("Category should be either MUN or TOP"))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource({"-1", "0"})
    void givenInvalidMaxHeightShouldReturnExpectedErrorResponse(String invalidMaxHeight) throws Exception {
        //When
        ResultActions performGet = mvc.perform(get(MUNROS_API_PATH)
                .param("maxHeightInMeters", invalidMaxHeight));
        //Then
        performGet
                .andExpect(content().string("maxHeightInMeters: must be greater than 0"))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource({"-1", "0"})
    void givenInvalidMinHeightShouldReturnExpectedErrorResponse(String invalidMinHeight) throws Exception {
        //When
        ResultActions performGet = mvc.perform(get(MUNROS_API_PATH)
                .param("minHeightInMeters", invalidMinHeight));
        //Then
        performGet
                .andExpect(content().string("minHeightInMeters: must be greater than 0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenMaxHeightLessThanMinHeightShouldReturnExpectedErrorResponse() throws Exception {
        //Given
        String maxHeightInMeters = "900";
        String minHeightInMeters = "1000";
        //When
        ResultActions performGet = mvc.perform(get(MUNROS_API_PATH)
                .param("maxHeightInMeters", maxHeightInMeters)
                .param("minHeightInMeters", minHeightInMeters));
        //Then
        performGet
                .andExpect(content().string("maxHeight should be greater or equals than minHeight"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenInvalidHeightSortShouldReturnExpectedErrorResponse() throws Exception {
        //Given
        String invalidHeightSort = "invalid-sort-direction";
        //When
        ResultActions performGet = mvc.perform(get(MUNROS_API_PATH)
                .param("heightSort", invalidHeightSort));
        //Then
        performGet
                .andExpect(content().string("Sort direction should be either ASC or DESC"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenInvalidNameSortShouldReturnExpectedErrorResponse() throws Exception {
        //Given
        String invalidNameSort = "invalid-sort-direction";
        //When
        ResultActions performGet = mvc.perform(get(MUNROS_API_PATH)
                .param("nameSort", invalidNameSort));
        //Then
        performGet
                .andExpect(content().string("Sort direction should be either ASC or DESC"))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource({"-1", "0"})
    void givenInvalidLimitShouldReturnExpectedErrorResponse(String invalidLimit) throws Exception {
        //When
        ResultActions performGet = mvc.perform(get(MUNROS_API_PATH)
                .param("limit", invalidLimit));
        //Then
        performGet
                .andExpect(content().string("limit: must be greater than 0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInvocationWithDefaultParameters() throws Exception {
        //When
        mvc.perform(get(MUNROS_API_PATH));
        //Then
        verify(munroService, times(1))
                .findMunros(Category.MUN, null, null, SortDirection.DESC, SortDirection.ASC, Optional.empty());
    }

    @Test
    void testSuccessfulCall() throws Exception {
        //Given
        MunroData munro = new MunroData();
        munro.setRunningNo("1");
        munro.setPostYear1997(Category.MUN.name());

        when(munroService.findMunros(any(), any(), any(), any(), any(), any())).thenReturn(singletonList(munro));

        //When
        ResultActions performGet = mvc.perform(get(MUNROS_API_PATH));

        //Then
        performGet
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].['Post 1997']", is("MUN")))
                .andExpect(status().isOk());
    }
}