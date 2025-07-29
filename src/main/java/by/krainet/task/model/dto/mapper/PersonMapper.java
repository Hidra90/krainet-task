package by.krainet.task.model.dto.mapper;

import by.krainet.task.model.Person;
import by.krainet.task.model.dto.PersonUpdateDto;
import by.krainet.task.model.dto.ProfileUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PersonMapper {

    ProfileUpdateDto toDto(Person person);

    Person toEntity(ProfileUpdateDto dto);

    PersonUpdateDto toUpdateDto(Person person);

    void updatePersonFromProfileDto(ProfileUpdateDto dto, @MappingTarget Person person);

    void updateFromDto(PersonUpdateDto dto, @MappingTarget Person person);

}
