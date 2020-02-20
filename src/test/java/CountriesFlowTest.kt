import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.ArrayList
import java.util.Collections

@ExperimentalCoroutinesApi
@FlowPreview
class CountriesFlowTest {
    private lateinit var flowAnswers: CountriesFlowAnswers
    private lateinit var allCountriesFlow: Flow<Country>
    private lateinit var allCountries: List<Country>

    @Before
    fun setup() {
        flowAnswers = CountriesFlowAnswers()
        allCountries = CountriesTestProvider.countries()
        allCountriesFlow = allCountries.asFlow()
    }

    @Test
    fun flow_ListPopulationOfEachCountry() = runBlocking {
        val expectedResult = CountriesTestProvider.populationOfCountries()
        val actualResult = mutableListOf<Long>()
        flowAnswers
            .listPopulationOfEachCountry(allCountriesFlow)
            .toCollection(actualResult)
        assert(expectedResult.containsAll(actualResult))
    }

    @Test
    fun flow_ListNameOfEachCountry() = runBlocking {
        val expectedResult = CountriesTestProvider.namesOfCountries()
        val actualResult = mutableListOf<String>()
        flowAnswers
            .listNameOfEachCountry(allCountriesFlow)
            .toCollection(actualResult)
        assert(expectedResult.containsAll(actualResult))
    }

    @Test
    fun flow_ListOnly3rdAnd4thCountry() = runBlocking {
        val expectedResult: MutableList<Country> = ArrayList()
        expectedResult.add(allCountries[2])
        expectedResult.add(allCountries[3])
        val actualResult = mutableListOf<Country>()
        flowAnswers
            .listOnly3rdAnd4thCountry(allCountriesFlow)
            .toCollection(actualResult)
        assert(expectedResult.containsAll(actualResult))
        assert(actualResult.containsAll(expectedResult))
    }

    @Test
    fun flow_IsAllCountriesPopulationMoreThanOneMillion_Positive() = runBlocking {
        val result = flowAnswers
            .isAllCountriesPopulationMoreThanOneMillion(CountriesTestProvider.countriesPopulationMoreThanOneMillionFlow())
        assert(result)
    }

    @Test
    fun flow_IsAllCountriesPopulationMoreThanOneMillion_Negative() = runBlocking {
        val allCountriesPopulationMoreThanOneMillion = flowAnswers
            .isAllCountriesPopulationMoreThanOneMillion(allCountriesFlow)
        assert(!allCountriesPopulationMoreThanOneMillion)
    }

    @Test
    fun flow_ListPopulationMoreThanOneMillion() = runBlocking {
        val expectedResult =
            CountriesTestProvider.countriesPopulationMoreThanOneMillion().toList().blockingGet()
        val actualResult = mutableListOf<Country>()
        flowAnswers
            .listPopulationMoreThanOneMillion(allCountriesFlow)
            .toCollection(actualResult)
        assert(expectedResult.containsAll(actualResult))
        assert(actualResult.containsAll(expectedResult))
    }

    @Test
    fun flow_GetCurrencyUsdIfNotFound_When_CountryFound() = runBlocking {
        val countryRequested = "Austria"
        val expectedCurrencyValue = "EUR"
        val actualResult = mutableListOf<String>()
        flowAnswers
            .getCurrencyUsdIfNotFound(countryRequested, allCountriesFlow)
            .toCollection(actualResult)
        assert(actualResult.size == 1)
        assert(actualResult.first() == expectedCurrencyValue)
    }

    @Test
    fun flow_GetCurrencyUsdIfNotFound_When_CountryNotFound() = runBlocking {
        val countryRequested = "Senegal"
        val expectedCurrencyValue = "USD"
        val actualResult = mutableListOf<String>()
        flowAnswers
            .getCurrencyUsdIfNotFound(countryRequested, allCountriesFlow)
            .toCollection(actualResult)
        assert(actualResult.size == 1)
        assert(actualResult.first() == expectedCurrencyValue)
    }

    @Test
    fun flow_SumPopulationOfCountries() = runBlocking {
        // hint: use "reduce" operator
        val sum = flowAnswers
            .sumPopulationOfCountries(allCountriesFlow)
        assert(sum == CountriesTestProvider.sumPopulationOfAllCountries())
    }

    @Test
    fun flow_sumPopulationOfCountries() = runBlocking {
        // hint: use "map" operator
        val sum = flowAnswers
            .sumPopulationOfCountries(
                allCountriesFlow,
                allCountriesFlow
            )
        assert(
            sum ==
                CountriesTestProvider.sumPopulationOfAllCountries()
                + CountriesTestProvider.sumPopulationOfAllCountries()
        )
    }

    @Test
    fun flow_areEmittingSameSequences_Positive() = runBlocking {
        // hint: use "sequenceEqual" operator
        val isEmittingSame = flowAnswers
            .areEmittingSameSequences(
                allCountriesFlow,
                allCountriesFlow
            )
        assert(isEmittingSame)
    }

    @Test
    fun flow_areEmittingSameSequences_Negative() = runBlocking {
        val allCountriesDifferentSequence: List<Country> =
            ArrayList(allCountries)
        Collections.swap(allCountriesDifferentSequence, 0, 1)
        val testObserver = flowAnswers
            .areEmittingSameSequences(
               allCountriesFlow,
                allCountriesFlow
            )
        assert(!testObserver)
    }
}