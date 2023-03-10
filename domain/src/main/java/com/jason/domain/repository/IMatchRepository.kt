package com.jason.domain.repository

import com.jason.domain.core.AppResult
import com.jason.domain.entity.MatchCollectionList
import com.jason.domain.entity.Match
import kotlinx.coroutines.flow.Flow

interface IMatchRepository {
    fun getMatchList(): Flow<AppResult<MatchCollectionList>>
    fun getTeamMatchList(id: String): Flow<AppResult<MatchCollectionList>>
}