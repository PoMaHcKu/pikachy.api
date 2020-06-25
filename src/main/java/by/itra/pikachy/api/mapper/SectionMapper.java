package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.dto.SectionDto;
import by.itra.pikachy.api.entity.Section;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SectionMapper {
    SectionDto fromSection(Section section);
    Section toSection(SectionDto sectionDto);
}