package uz.pdp.myappfigma.generic;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.pdp.myappfigma.dto.card.CardCreateDto;
import uz.pdp.myappfigma.dto.card.CardDto;
import uz.pdp.myappfigma.entity.Card;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardMapper extends GenericMapper<Card, CardDto, CardCreateDto> {

}
