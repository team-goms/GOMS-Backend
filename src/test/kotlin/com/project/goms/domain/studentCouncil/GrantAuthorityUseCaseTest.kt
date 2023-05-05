package com.project.goms.domain.studentCouncil

import com.project.goms.common.AnyValueObjectGenerator
import com.project.goms.domain.account.common.exception.AccountNotFoundException
import com.project.goms.domain.account.entity.Account
import com.project.goms.domain.account.entity.repository.AccountRepository
import com.project.goms.domain.studentCouncil.usecase.dto.GrantAuthorityDto
import com.project.goms.domain.account.entity.Authority
import com.project.goms.domain.account.entity.updateAuthority
import com.project.goms.domain.studentCouncil.usecase.GrantAuthorityUseCase
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.data.repository.findByIdOrNull
import java.util.UUID

class GrantAuthorityUseCaseTest: BehaviorSpec({
    val accountRepository = mockk<AccountRepository>()
    val grantAuthorityUseCase = GrantAuthorityUseCase(accountRepository)

    Given("grantAuthorityDto가 주어질때") {
        val accountIdx = UUID.randomUUID()
        val grantAuthorityDto = GrantAuthorityDto(accountIdx = accountIdx, authority = Authority.ROLE_STUDENT_COUNCIL)
        val account = AnyValueObjectGenerator.anyValueObject<Account>("idx" to accountIdx)

        every { accountRepository.findByIdOrNull(account.idx) } returns account
        every { accountRepository.save(any()) } returns account

        When("권한 수정 요청을 하면") {
            grantAuthorityUseCase.execute(grantAuthorityDto)

            Then("권한이 수정이 되어야 한다.") {
                verify(exactly = 1) { accountRepository.save(any()) }
            }
        }

        When("없는 계정일 경우") {
            every { accountRepository.findByIdOrNull(account.idx) } returns null

            Then("AccountNotFoundException이 터져야 한다") {
                shouldThrow<AccountNotFoundException> {
                    grantAuthorityUseCase.execute(grantAuthorityDto)
                }
            }
        }
    }
})