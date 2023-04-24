package com.project.goms.domain.account.presentation.data.dto

import java.util.*

data class ProfileDto(
    val accountIdx: UUID,
    val studentNum: StudentNum,
    val profileUrl: String?,
    val rateCount: Int
) {
    data class StudentNum(
        val grade: Int,
        val classNum: Int,
        val number: Int
    )
}