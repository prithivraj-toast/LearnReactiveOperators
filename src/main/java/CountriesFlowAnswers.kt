import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.skip
import kotlinx.coroutines.flow.take

class CountriesFlowAnswers {

    fun listPopulationOfEachCountry(countries: Flow<Country>): Flow<Long> =
        countries.map { it.population }

    fun listNameOfEachCountry(allCountriesFlow: Flow<Country>): Flow<String> =
        allCountriesFlow.map { it.name }

    fun listOnly3rdAnd4thCountry(countries: Flow<Country>): Flow<Country> =
        countries
            .drop(3)
            .take(2)

    suspend fun isAllCountriesPopulationMoreThanOneMillion(countries: Flow<Country>): Boolean =
        countries
            .count { it.population > 1000000 } > 0

    fun listPopulationMoreThanOneMillion(countries: Flow<Country>): Flow<Country> =
        countries
            .filter { it.population > 1000000 }

    fun getCurrencyUsdIfNotFound(
        countryName: String,
        countries: Flow<Country>
    ): Flow<String> =
        countries
            .filter { it.name == countryName }
            .map {
                if (it.currency.isEmpty()) {
                    "USD"
                }
                it.currency
            }

    suspend fun sumPopulationOfCountries(countries: Flow<Country>): Long =
        countries
            .map { it.population }
            .reduce { i1: Long, i2: Long -> i1 + i2 }

    suspend fun sumPopulationOfCountries(
        countryObservable1: Flow<Country>,
        countryObservable2: Flow<Country>
    ): Long =
        countryObservable1.combine(countryObservable2) { c1, c2 ->
            return@combine listOf(c1, c2)
        }.flatMapConcat { it.asFlow() }
            .map { it.population }
            .reduce { i1: Long, i2: Long -> i1 + i2 }

    suspend fun areEmittingSameSequences(
        countryObservable1: Flow<Country>,
        countryObservable2: Flow<Country>
    ): Boolean {
        val a = mutableListOf<Country>()
        val b = mutableListOf<Country>()
        countryObservable1.collect {
            a.add(it)
        }
        countryObservable2.collect {
            b.add(it)
        }
        return a.containsAll(b)
    }
}