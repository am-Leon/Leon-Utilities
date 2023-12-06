package am.leon.utilities.common.data.mapper

import am.leon.utilities.common.data.models.dto.BaseDTO
import am.leon.utilities.common.domain.models.BaseDomain

internal object BaseDomainMapper : Mapper<BaseDTO, BaseDomain, Unit>() {

    override fun dtoToDomain(model: BaseDTO): BaseDomain {
        return BaseDomain(model.message.orEmpty(), model.code ?: -1)
    }

    override fun domainToDto(model: BaseDomain): BaseDTO {
        return BaseDTO(model.message, model.code)
    }
}