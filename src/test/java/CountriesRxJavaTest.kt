import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.Locale

class CountriesRxJavaTest {
    private lateinit var rxJavaAnswers: CountriesRxJavaAnswers
    private lateinit var allCountries: List<Country>
    private lateinit var allCountriesObservable: Observable<Country>

    @Before
    fun setUp() {
        rxJavaAnswers = CountriesRxJavaAnswers()
        allCountries = CountriesTestProvider.countries()
        allCountriesObservable = Observable.fromIterable(allCountries)
    }

    @Test
    fun rx_CountryNameInCapitals() {
        val testCountry = CountriesTestProvider.countries()[0]
        val expected = testCountry.name.toUpperCase(Locale.US)
        val testObserver = rxJavaAnswers
            .countryNameInCapitals(testCountry)
            .test()
        testObserver.assertNoErrors()
        testObserver.assertValue(expected)
    }

    @Test
    fun rx_CountAmountOfCountries() {
        val expected = CountriesTestProvider.countries().size
        val testObserver = rxJavaAnswers
            .countCountries(allCountries)
            .test()
        testObserver.assertNoErrors()
        testObserver.assertValue(expected)
    }

    @Test
    fun rx_ListPopulationOfEachCountry() {
        val expectedResult = CountriesTestProvider.populationOfCountries()
        val testObserver = rxJavaAnswers
            .listPopulationOfEachCountry(allCountriesObservable)
            .test()
        testObserver.assertValueSequence(expectedResult)
        testObserver.assertNoErrors()
    }

    @Test
    fun rx_ListNameOfEachCountry() {
        val expectedResult = CountriesTestProvider.namesOfCountries()
        val testObserver = rxJavaAnswers
            .listNameOfEachCountry(allCountriesObservable)
            .test()
        testObserver.assertValueSequence(expectedResult)
        testObserver.assertNoErrors()
    }

    @Test
    fun rx_ListOnly3rdAnd4thCountry() {
        val expectedResult: MutableList<Country?> = ArrayList()
        expectedResult.add(allCountries[2])
        expectedResult.add(allCountries[3])
        val testObserver = rxJavaAnswers
            .listOnly3rdAnd4thCountry(allCountriesObservable)
            .test()
        testObserver.assertValueSequence(expectedResult)
        testObserver.assertNoErrors()
    }

    @Test
    fun rx_IsAllCountriesPopulationMoreThanOneMillion_Positive() {
        val testObserver = rxJavaAnswers
            .isAllCountriesPopulationMoreThanOneMillion(CountriesTestProvider.countriesPopulationMoreThanOneMillion())
            .test()
        testObserver.assertResult(true)
        testObserver.assertNoErrors()
    }

    @Test
    fun rx_IsAllCountriesPopulationMoreThanOneMillion_Negative() {
        val testObserver = rxJavaAnswers
            .isAllCountriesPopulationMoreThanOneMillion(allCountriesObservable)
            .test()
        testObserver.assertResult(false)
        testObserver.assertNoErrors()
    }

    @Test
    fun rx_ListPopulationMoreThanOneMillion() {
        val expectedResult =
            CountriesTestProvider.countriesPopulationMoreThanOneMillion().toList().blockingGet()
        val testObserver = rxJavaAnswers
            .listPopulationMoreThanOneMillion(allCountriesObservable)
            .test()
        testObserver.assertValueSequence(expectedResult)
        testObserver.assertNoErrors()
    }

    @Test
    fun rx_GetCurrencyUsdIfNotFound_When_CountryFound() {
        val countryRequested = "Austria"
        val expectedCurrencyValue = "EUR"
        val testObserver = rxJavaAnswers
            .getCurrencyUsdIfNotFound(countryRequested, allCountriesObservable)
            .test()
        testObserver.assertResult(expectedCurrencyValue)
        testObserver.assertNoErrors()
    }

    @Test
    fun rx_GetCurrencyUsdIfNotFound_When_CountryNotFound() {
        val countryRequested = "Senegal"
        val expectedCurrencyValue = "USD"
        val testObserver = rxJavaAnswers
            .getCurrencyUsdIfNotFound(countryRequested, allCountriesObservable)
            .test()
        testObserver.assertResult(expectedCurrencyValue)
        testObserver.assertNoErrors()
    }

    @Test
    fun rx_SumPopulationOfCountries() { // hint: use "reduce" operator
        val testObserver = rxJavaAnswers
            .sumPopulationOfCountries(allCountriesObservable)
            .test()
        testObserver.assertResult(CountriesTestProvider.sumPopulationOfAllCountries())
        testObserver.assertNoErrors()
    }

    @Test
    fun rx_MapCountriesToNamePopulation() {
        val values =
            rxJavaAnswers.mapCountriesToNamePopulation(allCountriesObservable).test()
        val expected: MutableMap<String, Long> = HashMap()
        for (country in allCountries) {
            expected[country.name] = country.population
        }
        values.assertResult(expected)
        values.assertNoErrors()
    }

    @Test
    fun rx_sumPopulationOfCountries() { // hint: use "map" operator
        val testObserver = rxJavaAnswers
            .sumPopulationOfCountries(
                Observable.fromIterable(allCountries),
                Observable.fromIterable(allCountries)
            )
            .test()
        testObserver.assertResult(
            CountriesTestProvider.sumPopulationOfAllCountries()
                + CountriesTestProvider.sumPopulationOfAllCountries()
        )
        testObserver.assertNoErrors()
    }

    @Test
    fun rx_areEmittingSameSequences_Positive() { // hint: use "sequenceEqual" operator
        val testObserver = rxJavaAnswers
            .areEmittingSameSequences(
                Observable.fromIterable(allCountries),
                Observable.fromIterable(allCountries)
            )
            .test()
        testObserver.assertResult(true)
        testObserver.assertNoErrors()
    }

    @Test
    fun rx_areEmittingSameSequences_Negative() {
        val allCountriesDifferentSequence: List<Country?> =
            ArrayList(allCountries)
        Collections.swap(allCountriesDifferentSequence, 0, 1)
        val testObserver = rxJavaAnswers
            .areEmittingSameSequences(
                Observable.fromIterable(allCountries),
                Observable.fromIterable(allCountriesDifferentSequence)
            )
            .test()
        testObserver.assertResult(false)
        testObserver.assertNoErrors()
    }
}