package com.example.domain.usecase

import com.example.domain.repository.Repository
import javax.inject.Inject

class GetDataUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke() = repository.getData()
}
