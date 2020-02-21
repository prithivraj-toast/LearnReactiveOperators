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
        TODO()

    fun countCountries(countries: List<Country>): Single<Int> =
        TODO()

    fun listPopulationOfEachCountry(countries: Observable<Country>): Observable<Long> =
        TODO()

    fun listNameOfEachCountry(countries: Observable<Country>): Observable<String> =
        TODO()

    fun listOnly3rdAnd4thCountry(countries: Observable<Country>): Observable<Country> =
        TODO()

    fun isAllCountriesPopulationMoreThanOneMillion(countries: Observable<Country>): Single<Boolean> =
        TODO()

    fun listPopulationMoreThanOneMillion(countries: Observable<Country>): Observable<Country> =
        TODO()

    fun getCurrencyUsdIfNotFound(
        countryName: String,
        countries: Observable<Country>
    ): Observable<String> =
        TODO()

    fun sumPopulationOfCountries(countries: Observable<Country>): Maybe<Long> =
        TODO()

    fun mapCountriesToNamePopulation(countries: Observable<Country>): Single<Map<String, Long>> =
        TODO()

    fun sumPopulationOfCountries(
        countryObservable1: Observable<Country>,
        countryObservable2: Observable<Country>
    ): Maybe<Long> =
        TODO()

    fun areEmittingSameSequences(
        countryObservable1: Observable<Country>,
        countryObservable2: Observable<Country>
    ): Single<Boolean> =
        TODO()
}