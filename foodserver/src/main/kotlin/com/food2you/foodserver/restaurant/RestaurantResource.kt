package com.food2you.foodserver.restaurant

import com.food2you.foodserver.costumer.requests.LoginRequest
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
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/restaurant")
data class RestaurantResource(
    private val restaurantService: RestaurantService,
    private val orderService: OrderService
) {
    @Operation(
        summary = "Realiza o Login",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Login bem sucedido"),
        ApiResponse(responseCode = "400", description = "Credenciais invalidas")
    )
    @PostMapping("/login")
    fun loginRestaurant(@RequestBody @Valid @Schema(example = "{\"email\": \"emailRestaurante@email.com\",\n \"password\": \"minhaSenha\"}") restaurant : RestaurantLoginRequest) {
        status(HttpStatus.OK).body(restaurantService.restaurantLogin(restaurant))
    }

    @Operation(
        summary = "Cria um novo restaurante",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Restaurante Criado"),
        ApiResponse(responseCode = "400", description = "Dados incorretos")
    )

    @PostMapping
    fun createRestaurant(@RequestBody @Valid @Schema(example = "{\"name\": \"MeuRestaurante\",\n \"email\": \"emailRestaurante@email.com\",\n \"password\": \"minhaSenha\"}") restaurant : NewRestaurant) = status(HttpStatus.CREATED).body(restaurantService.createRestaurant(restaurant))

    @Operation(
        summary = "Cria um novo Menu",
        parameters = [
            Parameter(
                name = "restaurantId",
                description = "O ID do restaurante criador do produto",
            )
        ]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Menu Criado"),
        ApiResponse(responseCode = "400", description = "Dados incorretos")
    )
    @PostMapping("/{restaurantId}/menu")
    fun createMenu(@RequestBody @Valid @Schema(example = "{\"name\": \"CardapioEspecial\"}") menu: NewMenu, @PathVariable restaurantId: Long) = status(HttpStatus.CREATED).body(restaurantService.createMenu(menu,restaurantId))

    @Operation(
        summary = "Adiciona um novo produto ao restaurante",
        parameters = [
            Parameter(
                name = "restaurantId",
                description = "O ID do restaurante criador do produto",
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
        )
    )
    @PostMapping("/{restaurantId}/products")
    fun addProductToRestaurant(@RequestBody @Valid @Schema(example = "{\"name\": \"Burger\", \"price\": 9.99, \"qtt\": 1, \"description\": \"Delicious burger\"}") product: Product, @PathVariable restaurantId: Long) = status(HttpStatus.CREATED).body(restaurantService.addProduct(product, restaurantId))

    @Operation(
        summary = "Adiciona um produto ao Menu",
        parameters = [
            Parameter(
                name = "productId",
                description = "O ID do produto a ser adicionado ao Menu",
            ),
            Parameter(
                name = "menuId",
                description = "O ID do Menu",
            )
        ]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Produto Adicionado"),
        ApiResponse(responseCode = "400", description = "Dados incorretos")
    )
    @PostMapping("/{restaurantId}/{menuId}/{productId}")
    fun addProductToMenu(@PathVariable productId: Long, @PathVariable menuId: Long) = status(HttpStatus.CREATED).body(restaurantService.addProductToMenu (productId, menuId))

    @Operation(
        summary = "Retorna todos os produtos vinculados a um restaurante",
        parameters = [
            Parameter(
                name = "restaurantId",
                description = "ID do Restaurante",
            ),
        ]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Produto Retornados"),
        ApiResponse(responseCode = "400", description = "Dados incorretos")
    )
    @GetMapping("/{restaurantId}/products")
    fun getAllProducts(@PathVariable restaurantId: Long) = status(HttpStatus.CREATED).body(restaurantService.getAllProducts (restaurantId))

    @Operation(
        summary = "Retorna todos os pedidos vinculados a um restaurante",
        parameters = [
            Parameter(
                name = "restaurantId",
                description = "ID do Restaurante",
            ),
        ]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Pedidos Retornados"),
        ApiResponse(responseCode = "400", description = "Dados incorretos")
    )
    @GetMapping("/{restaurantId}/orders")
    fun getAllOrders(@PathVariable @Valid restaurantId: Long) = status(HttpStatus.OK).body(restaurantService.getAllOrders(restaurantId))

    @Operation(
        summary = "Retorna todos os produtos vinculados a um pedido",
        parameters = [
            Parameter(
                name = "orderId",
                description = "ID do Pedido",
            ),
        ]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Produtos Retornados"),
        ApiResponse(responseCode = "400", description = "Dados incorretos")
    )
    @GetMapping("/{restaurantId}/{orderId}/products")
    fun getAllOrdersProducts( @PathVariable orderId: Long) = status(HttpStatus.OK).body(restaurantService.getAllProductsFromOrder(orderId))

    @Operation(
        summary = "Retorna todos os restaurantes registrados",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Restaurantes Retornados"),
        ApiResponse(responseCode = "400", description = "Dados incorretos")
    )
    @GetMapping
    fun getAllRestaurants() = status(HttpStatus.OK).body(restaurantService.findAllRestaurants())

    @Operation(
        summary = "Retorna todos os menus vinculados a um restaurante",
        parameters = [
            Parameter(
                name = "restaurantId",
                description = "ID do Restaurante",
            ),
        ]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "menus Retornados"),
        ApiResponse(responseCode = "400", description = "Dados incorretos")
    )
    @GetMapping("/{restaurantId}")
    fun getAllMenus(@PathVariable @Valid restaurantId: Long) = status(HttpStatus.OK).body(restaurantService.getAllMenus(restaurantId))

    @Operation(
        summary = "Exclui um produto especifico",
        parameters = [
            Parameter(
                name = "restaurantId",
                description = "ID do Restaurante",
            ),
        ]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Produto Excluido"),
        ApiResponse(responseCode = "400", description = "Dados incorretos")
    )
    @DeleteMapping("/{restaurantId}/products/{productId}")
    fun deleteProduct(@PathVariable @Valid productId : Long, @PathVariable restaurantId: String) = status(HttpStatus.OK).body(restaurantService.deleteProduct(productId))


    @Operation(
        summary = "Atualiza os dados de um determinado produto",
        parameters = [
            Parameter(
                name = "productId",
                description = "O ID do produto a ser adicionado ao Menu",
            ),
        ]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Produto Atualizado"),
        ApiResponse(responseCode = "400", description = "Dados incorretos")
    )
    @PutMapping("/{restaurantId}/products/{productId}")
    fun updateProduct(@PathVariable @Valid productId : Long, @RequestBody @Valid @Schema(example = "{\"name\": \"Burger\", \"price\": 9.99, \"qtt\": 1, \"description\": \"Delicious burger\"}") product: Product) = status(HttpStatus.OK).body(restaurantService.updateProduct(productId, product))
}
