package com.xdesign.munro.facade;

import com.xdesign.munro.domain.MunroData;
import com.xdesign.munro.dto.MunroDto;
import com.xdesign.munro.parameters.Category;
import com.xdesign.munro.parameters.SortDirection;
import com.xdesign.munro.service.MunroService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MunroFacade {

    private MunroService munroService;

    private final ModelMapper mapper = new ModelMapper();

    @PostConstruct
    public void init() {
        mapper.typeMap(MunroData.class, MunroDto.class).addMappings(mapper -> {
            //map postYear1997 to category field on response
            mapper.map(MunroData::getPostYear1997, MunroDto::setCategory);
        });
    }

    public List<MunroDto> findMunros(Category category, Float maxHeightInMeters, Float minHeightInMeters, SortDirection heightSort, SortDirection nameSort, Optional<Long> limit) {
        List<MunroData> munroDataList = munroService.findMunros(category, maxHeightInMeters, minHeightInMeters, heightSort, nameSort, limit);
        return mapper.map(munroDataList, new TypeToken<List<MunroDto>>() {}.getType());
    }
}
