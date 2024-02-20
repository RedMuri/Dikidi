package com.example.dikidi.domain.usecase

import com.example.dikidi.domain.repository.Repository

class GetDataUseCase (private val repository: Repository) {
    operator fun invoke() = repository.getData()
}
