package com.project.goms.domain.account.usecase

import com.project.goms.domain.account.persistence.repository.AccountRepository
import com.project.goms.domain.account.presentation.data.dto.AccountDto
import com.project.goms.domain.account.presentation.data.enums.Authority
import com.project.goms.domain.outing.persistence.repository.OutingBlackListRepository
import com.project.goms.global.annotation.UseCaseWithReadOnlyTransaction

@UseCaseWithReadOnlyTransaction
class SearchAccountUseCase(
    private val accountRepository: AccountRepository,
    private val outingBlackListRepository: OutingBlackListRepository
) {

    fun execute(grade: Int?, classNum: Int?, name: String?, isBlackList: Boolean?, authority: Authority?): List<AccountDto> =
        accountRepository.findAllByOrderByGradeAscClassNumAscNumberAsc()
            .asSequence()
            .filter {
                if (grade != null) it.grade == grade else true
            }.filter {
                if (classNum != null) it.classNum == classNum else true
            }.filter {
                if (!name.isNullOrBlank()) it.name == name else true
            }.filter {
                if (isBlackList != null) outingBlackListRepository.existsById(it.idx) == isBlackList else true
            }.filter {
                if (authority != null) it.authority == authority else true
            }.map {
                AccountDto(
                    accountIdx = it.idx,
                    name = it.name,
                    studentNum = AccountDto.StudentNum(it.grade, it.classNum, it.number),
                    profileUrl = it.profileUrl,
                    authority = it.authority
                )
            }.toList()

}