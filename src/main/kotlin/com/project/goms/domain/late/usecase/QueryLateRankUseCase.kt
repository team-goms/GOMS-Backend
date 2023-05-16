package com.project.goms.domain.late.usecase

import com.project.goms.domain.account.usecase.dto.StudentNumberDto
import com.project.goms.domain.late.entity.repository.LateRepository
import com.project.goms.domain.late.usecase.dto.LateRankDto
import com.project.goms.global.annotation.UseCaseWithReadOnlyTransaction
import org.springframework.data.domain.PageRequest

@UseCaseWithReadOnlyTransaction
class QueryLateRankUseCase(
    private val lateRepository: LateRepository
) {

    fun execute(): List<LateRankDto> =
        lateRepository.findDistinctTop5ByOrderByAccountDesc(PageRequest.of(0, 5))
            .map {
                LateRankDto(
                    accountIdx = it.account.idx,
                    name = it.account.name,
                    studentNum = StudentNumberDto(it.account.grade, it.account.classNum, it.account.number),
                    profileUrl = it.account.profileUrl
                )
            }

}