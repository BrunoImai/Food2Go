package com.food2you.foodserver.restaurant

import com.food2you.foodserver.costumer.requests.LoginRequest
import com.food2you.foodserver.costumer.response.LoginResponse
import com.food2you.foodserver.menus.requests.NewMenu
import com.food2you.foodserver.orders.OrderService
import com.food2you.foodserver.product.Product
import com.food2you.foodserver.restaurant.requests.NewRestaurant
import com.food2you.foodserver.restaurant.requests.RestaurantLoginRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterStyle
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity.status
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/restaurant")
data class RestaurantResource(
    private val restaurantService: RestaurantService,
    private val orderService: OrderService
) {
    @Operation(
        summary = "Make a login request",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successful login "),
        ApiResponse(responseCode = "400", description = "Invalid credentials"),
        ApiResponse(responseCode = "404", description = "Email not found")
    )
    @PostMapping("/login")
    fun loginRestaurant(@RequestBody @Valid @Schema(example = "{\"email\": \"emailRestaurante@email.com\",\n \"password\": \"MyPassword123\"}") restaurant : RestaurantLoginRequest) = status(HttpStatus.OK).body( restaurantService.restaurantLogin(restaurant))



    @Operation(
        summary = "Create a new Restaurant",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Restaurant created"),
        ApiResponse(responseCode = "400", description = "Incorrect Body"),
    )

    @PostMapping
    fun createRestaurant(@RequestBody @Valid @Schema(example = "{\"name\": \"MyRestaurant\",\n \"email\": \"emailRestaurant@email.com\",\n \"password\": \"MyPassword123\"}") restaurant : NewRestaurant) = status(HttpStatus.CREATED).body(restaurantService.createRestaurant(restaurant))

    @Operation(
        summary = "Create a new Menu",
        parameters = [
            Parameter(
                name = "restaurantId",
                description = "Restaurant Identifier",
            )
        ]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Menu created"),
        ApiResponse(responseCode = "400", description = "Incorrect Body"),
    )
    @PostMapping("/{restaurantId}/menu")
    fun createMenu(@RequestBody @Valid @Schema(example = "{\"name\": \"Special Menu\"}") menu: NewMenu, @PathVariable restaurantId: Long) = status(HttpStatus.CREATED).body(restaurantService.createMenu(menu,restaurantId))

    @Operation(
        summary = "Add a new Product to Restaurant",
        parameters = [
            Parameter(
                name = "restaurantId",
                description = "Restaurant Identifier",
            )
        ]
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Product updated",
            useReturnTypeSchema = true,
            content = [
                Content(
                    mediaType = "application/json",
                    examples = [
                        ExampleObject(
                            value = "{\"id\": 1, \"name\": \"Updated Burger\", \"price\": 10.99, \"qtt\": 2, \"description\": \"Even more delicious burger\"}"
                        )
                    ]
                )
            ]
        ),
        ApiResponse(responseCode = "404", description = "Restaurant not Found")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{restaurantId}/products")
    fun addProductToRestaurant(@RequestBody @Valid @Schema(example = "{\"name\": \"Burger\", \"price\": 9.99, \"qtt\": 1, \"description\": \"Delicious burger\"}") product: Product, @PathVariable restaurantId: Long) = status(HttpStatus.CREATED).body(restaurantService.addProduct(product, restaurantId))

    @Operation(
        summary = "Add a product to Menu",
        parameters = [
            Parameter(
                name = "productId",
                description = "Product Identifier to be linked to Menu",
            ),
            Parameter(
                name = "menuId",
                description = "Menu Identifier",
            )
        ]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Product Data"),
        ApiResponse(responseCode = "400", description = "Incorrect Body"),
        ApiResponse(responseCode = "404", description = "Product or Menu not Found")
    )
    @PostMapping("/{restaurantId}/{menuId}/{productId}")
    fun addProductToMenu(@PathVariable productId: Long, @PathVariable menuId: Long) = status(HttpStatus.CREATED).body(restaurantService.addProductToMenu (productId, menuId))

    @Operation(
        summary = "Returns all products linked to a restaurant\n",
        parameters = [
            Parameter(
                name = "restaurantId",
                description = "Restaurant Identifier",
            ),
        ]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Product founded"),
        ApiResponse(responseCode = "400", description = "Incorrect Body"),
    )
    @GetMapping("/{restaurantId}/products")
    fun getAllProducts(@PathVariable restaurantId: Long) = status(HttpStatus.CREATED).body(restaurantService.getAllProducts (restaurantId))

    @Operation(
        summary = "Returns all orders linked to a restaurant\n",
        parameters = [
            Parameter(
                name = "restaurantId",
                description = "Restaurant Identifier",
            ),
        ]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Orders founded"),
        ApiResponse(responseCode = "400", description = "Incorrect Body"),
    )
    @GetMapping("/{restaurantId}/orders")
    fun getAllOrders(@PathVariable @Valid restaurantId: Long) = status(HttpStatus.OK).body(restaurantService.getAllOrders(restaurantId))

    @Operation(
        summary = "Returns all products linked to an order",
        parameters = [
            Parameter(
                name = "orderId",
                description = "Order Identifier",
            ),
        ]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Product found"),
        ApiResponse(responseCode = "400", description = "Incorrect Body"),
    )
    @GetMapping("/{restaurantId}/{orderId}/products")
    fun getAllOrdersProducts( @PathVariable orderId: Long) = status(HttpStatus.OK).body(restaurantService.getAllProductsFromOrder(orderId))

    @Operation(
        summary = "Returns all registered restaurants",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Restaurants founded"),
        ApiResponse(responseCode = "400", description = "Incorrect Body"),
    )
    @GetMapping
    fun getAllRestaurants() = status(HttpStatus.OK).body(restaurantService.findAllRestaurants())

    @Operation(
        summary = "Returns all menus linked to a restaurant",
        parameters = [
            Parameter(
                name = "restaurantId",
                description = "Restaurant Identifier",
            ),
        ]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Menu founded"),
        ApiResponse(responseCode = "400", description = "Incorrect Body"),
    )
    @GetMapping("/{restaurantId}")
    fun getAllMenus(@PathVariable @Valid restaurantId: Long) = status(HttpStatus.OK).body(restaurantService.getAllMenus(restaurantId))

    @Operation(
        summary = "Excludes a specific product",
        parameters = [
            Parameter(
                name = "restaurantId",
                description = "Restaurant Identifier",
            ),
        ]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully deleted product"),
        ApiResponse(responseCode = "400", description = "Incorrect Body"),
        ApiResponse(responseCode = "404", description = "Product not Found")

    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{restaurantId}/products/{productId}")
    fun deleteProduct(@PathVariable @Valid productId : Long, @PathVariable restaurantId: String) = status(HttpStatus.OK).body(restaurantService.deleteProduct(productId))


    @Operation(
        summary = "Update data for a particular product",
        parameters = [
            Parameter(
                name = "productId",
                description = "Product dentifier to be updated",
            ),
        ]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Product updated"),
        ApiResponse(responseCode = "400", description = "Incorrect Body"),
        ApiResponse(responseCode = "404", description = "Product not Found")
    )
    @PutMapping("/{restaurantId}/products/{productId}")
    fun updateProduct(@PathVariable @Valid productId : Long, @RequestBody @Valid @Schema(example = "{\"name\": \"Burger\", \"price\": 9.99, \"qtt\": 1, \"description\": \"Delicious burger\"}") product: Product) = status(HttpStatus.OK).body(restaurantService.updateProduct(productId, product))
}
