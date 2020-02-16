import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.functions.Predicate
import java.util.concurrent.FutureTask
import java.util.concurrent.TimeUnit

class CountriesRxJavaAnswers {

    fun countryNameInCapitals(country: Country): Single<String> =
        Single.just(country.name.toUpperCase())

    fun countCountries(countries: List<Country>): Single<Int> =
        Single.just(countries.size)

    fun listPopulationOfEachCountry(countries: Observable<Country>): Observable<Long> =
        countries.map(Country::population)

    fun listNameOfEachCountry(countries: Observable<Country>): Observable<String> =
        countries.map(Country::name)

    fun listOnly3rdAnd4thCountry(countries: Observable<Country>): Observable<Country> =
        countries
            .skip(3)
            .take(2)

    fun isAllCountriesPopulationMoreThanOneMillion(countries: Observable<Country>): Single<Boolean> =
        countries
            .all { (_, _, population) -> population > 1000000 }

    fun listPopulationMoreThanOneMillion(countries: Observable<Country>): Observable<Country> =
        countries
            .filter { (_, _, population) -> population > 1000000 }

    fun getCurrencyUsdIfNotFound(
        countryName: String,
        countries: Observable<Country>
    ): Observable<String> =
        countries
            .filter { it.name == countryName }
            .map(Country::currency)
            .defaultIfEmpty("USD")

    fun sumPopulationOfCountries(countries: Observable<Country>): Maybe<Long> =
        countries
            .map(Country::population)
            .reduce { i1: Long, i2: Long -> i1 + i2 }

    fun mapCountriesToNamePopulation(countries: Observable<Country>): Single<Map<String, Long>> =
        countries
            .toMap(
                Country::name,
                Country::population
            )

    fun sumPopulationOfCountries(
        countryObservable1: Observable<Country>,
        countryObservable2: Observable<Country>
    ): Maybe<Long> =
        Observable.merge(countryObservable1, countryObservable2)
            .map(Country::population)
            .reduce { i1: Long, i2: Long -> i1 + i2 }

    fun areEmittingSameSequences(
        countryObservable1: Observable<Country>,
        countryObservable2: Observable<Country>
    ): Single<Boolean> =
        Observable.sequenceEqual(countryObservable1, countryObservable2)
}