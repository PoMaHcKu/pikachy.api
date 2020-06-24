package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.dto.SectionDto;
import by.itra.pikachy.api.entity.Section;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SectionMapper {
    SectionMapper SECTION_MAPPER = Mappers.getMapper(SectionMapper.class);
    SectionDto fromSection(Section section);
    @InheritInverseConfiguration
    Section toSection(SectionDto sectionDto);
}