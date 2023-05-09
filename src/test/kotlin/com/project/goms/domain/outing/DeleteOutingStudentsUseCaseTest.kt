package com.project.goms.domain.outing

import com.project.goms.domain.outing.entity.repository.OutingRepository
import com.project.goms.domain.outing.usecase.DeleteOutingStudentsUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class DeleteOutingStudentsUseCaseTest: BehaviorSpec({
    val outingRepository = mockk<OutingRepository>()
    val deleteOutingStudentsUseCase = DeleteOutingStudentsUseCase(outingRepository)

    Given("외출자 테이블에 컬럼들이 남아있을때") {

        every { outingRepository.deleteAll() } returns Unit

        When("외출자 전체 삭제 요청을 하면") {
            deleteOutingStudentsUseCase.execute()

            Then("외출자들이 모두 삭제가 되어야 한다.") {
                verify(exactly = 1) { outingRepository.deleteAll() }
            }
        }
    }

})