package com.example.dikidi.domain.usecase

import com.example.dikidi.domain.repository.Repository
import javax.inject.Inject

class GetDataUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke() = repository.getData()
}
