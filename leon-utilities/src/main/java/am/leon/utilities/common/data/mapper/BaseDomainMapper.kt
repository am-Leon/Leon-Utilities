package am.leon.utilities.common.data.mapper

import am.leon.utilities.common.data.dto.BaseDTO
import am.leon.utilities.common.domain.models.BaseDomain

internal object BaseDomainMapper : Mapper<BaseDTO, BaseDomain, Unit>() {

    override fun dtoToDomain(model: BaseDTO): BaseDomain {
        return BaseDomain(model.message.orEmpty(), model.code.orEmpty())
    }

    override fun domainToDto(model: BaseDomain): BaseDTO {
        return BaseDTO(model.message, model.code)
    }
}