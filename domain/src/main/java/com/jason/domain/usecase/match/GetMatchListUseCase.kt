package com.jason.domain.usecase.match

import com.jason.domain.core.AppResult
import com.jason.domain.entity.MatchCollectionList
import com.jason.domain.entity.Match
import com.jason.domain.repository.IMatchRepository
import com.jason.domain.usecase.IBaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMatchListUseCase @Inject constructor(
    private val repository: IMatchRepository
) : IBaseUseCase<Unit, AppResult<MatchCollectionList>> {
    override suspend fun invoke(input: Unit): Flow<AppResult<MatchCollectionList>> {
        return repository.getMatchList()
    }
}