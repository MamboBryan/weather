package sources.remotesources.helpers

/**
 * PROJECT : weather
 * DATE    : Fri 02/02/2024
 * TIME    : 20:41
 * USER    : mambo
 */

/**
 * A sealed class to hold all our endpoints
 * - why use a sealed class?
 * - unlike enums, sealed classes offer the ability to have varying types for each instance of the class a simple example is
 *  ```
 *  enum class Endpoints(val route: String) {
 *      PRODUCTS("/products"), PRODUCT("/products/{id}")
 *  }
 *  ```
 *  - in this case we wouldn't have much control over how the PRODUCT endpoint is being called
 *  - with sealed classes we can enforce when the product endpoint is being called we have to provide an id and they type should be an integer
 *  ```
 *  sealed class Endpoint(val route: String){
 *      object Products : Endpoint("/products"){
 *          data class Product(val id: Int) : Endpoint(route + "$id")
 *      }
 *  }
 *  ```
 *  - as an added benefit is also reuses the route from the parent class
 */
sealed class Endpoint(private val route: String) {

    /**
     * The complete url endpoint
     */
    val url: String
        get() = buildString {
            append("http://api.weatherapi.com/v1")
            append(route)
        }

    /**
     * Endpoint object for getting the current day's forecast
     */
    data object Forecast : Endpoint(route = "/forecast.json")

}