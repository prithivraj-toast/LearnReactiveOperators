import io.reactivex.rxjava3.core.Observable
import java.util.ArrayList

internal object CountriesTestProvider {
    private const val CURRENCY_EUR = "EUR"
    private const val CURRENCY_PLN = "PLN"
    private const val CURRENCY_GBP = "GBP"
    private const val CURRENCY_UAH = "UAH"
    private const val CURRENCY_CHF = "CHF"

    private val countries = ArrayList<Country>()
    fun countries(): List<Country> {
        return ArrayList(countries)
    }

    fun countriesPopulationMoreThanOneMillion(): Observable<Country> {
        val result: MutableList<Country> = ArrayList()
        for (country in countries) {
            if (country.population > 1000000) {
                result.add(country)
            }
        }
        return Observable.fromIterable(result)
    }

    fun populationOfCountries(): List<Long> {
        val result: MutableList<Long> =
            ArrayList(countries.size)
        for ((_, _, population) in countries) {
            result.add(population)
        }
        return result
    }

    fun namesOfCountries(): List<String> {
        val result: MutableList<String> =
            ArrayList(countries.size)
        for ((name) in countries) {
            result.add(name)
        }
        return result
    }

    fun sumPopulationOfAllCountries(): Long {
        var result = 0L
        for ((_, _, population) in countries) {
            result += population
        }
        return result
    }

    init {
        countries.add(Country("Germany", CURRENCY_EUR, 80620000))
        countries.add(Country("France", CURRENCY_EUR, 66030000))
        countries.add(Country("United Kingdom", CURRENCY_GBP, 64100000))
        countries.add(Country("Poland", CURRENCY_PLN, 38530000))
        countries.add(Country("Ukraine", CURRENCY_UAH, 45490000))
        countries.add(Country("Austria", CURRENCY_EUR, 8474000))
        countries.add(Country("Switzerland", CURRENCY_CHF, 8081000))
        countries.add(Country("Luxembourg", CURRENCY_EUR, 576249))
    }
}