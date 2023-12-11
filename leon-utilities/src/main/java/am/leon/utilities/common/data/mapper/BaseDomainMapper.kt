package am.leon.utilities.common.data.mapper

import am.leon.utilities.common.data.models.dto.BaseDto
import am.leon.utilities.common.domain.models.BaseDomain

object BaseDomainMapper : Mapper<BaseDto, BaseDomain, Unit>() {

    override fun dtoToDomain(model: BaseDto): BaseDomain {
        return BaseDomain(model.message.orEmpty(), model.code ?: -1)
    }

    override fun domainToDto(model: BaseDomain): BaseDto {
        return BaseDto(model.message, model.code)
    }
}