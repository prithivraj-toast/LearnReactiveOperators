import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.Locale

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
}