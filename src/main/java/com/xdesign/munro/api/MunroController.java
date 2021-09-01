package com.xdesign.munro.api;

import com.xdesign.munro.dto.MunroDto;
import com.xdesign.munro.facade.MunroFacade;
import com.xdesign.munro.parameters.Category;
import com.xdesign.munro.parameters.SortDirection;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(MunroController.MUNROS_API_PATH)
@Validated
@AllArgsConstructor
public class MunroController {

    static final String MUNROS_API_PATH = "/munros";

    private MunroFacade munroFacade;

    @GetMapping
    public List<MunroDto> findMunros(@RequestParam(defaultValue = "MUN") Category category,
                                     @RequestParam(required = false) @Positive Float maxHeightInMeters,
                                     @RequestParam(required = false) @Positive Float minHeightInMeters,
                                     @RequestParam(defaultValue = "DESC") SortDirection heightSort,
                                     @RequestParam(defaultValue = "ASC") SortDirection nameSort,
                                     @RequestParam(required = false) @Positive Long limit) {

        validateHeightParams(maxHeightInMeters, minHeightInMeters);

        return munroFacade.findMunros(category, maxHeightInMeters, minHeightInMeters, heightSort, nameSort, Optional.ofNullable(limit));
    }

    private void validateHeightParams(Float maxHeight, Float minHeight) {
        if (maxHeight != null && minHeight != null && maxHeight < minHeight) {
            throw new IllegalArgumentException("maxHeight should be greater or equals than minHeight");
        }
    }
}
