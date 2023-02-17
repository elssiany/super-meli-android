package com.kevinserrano.supermeli.lib.meli.model


import com.google.gson.annotations.SerializedName
import com.kevinserrano.supermeli.detail.model.ArticleDetail
import com.kevinserrano.supermeli.utils.getAttributesText
import com.kevinserrano.supermeli.utils.toPriceFormat
import com.kevinserrano.supermeli.utils.toSecureProtocol

data class ApiArticleDetailsResponse(
    @SerializedName("accepts_mercadopago")
    val acceptsMercadopago: Boolean = false,
    @SerializedName("attributes")
    val attributes: List<Attribute> = listOf(),
    @SerializedName("automatic_relist")
    val automaticRelist: Boolean = false,
    @SerializedName("available_quantity")
    val availableQuantity: Int = 0,
    @SerializedName("base_price")
    val basePrice: Int = 0,
    @SerializedName("buying_mode")
    val buyingMode: String = "",
    @SerializedName("catalog_listing")
    val catalogListing: Boolean = false,
    @SerializedName("catalog_product_id")
    val catalogProductId: Any? = null,
    @SerializedName("category_id")
    val categoryId: String = "",
    @SerializedName("channels")
    val channels: List<String> = listOf(),
    @SerializedName("condition")
    val condition: String = "",
    @SerializedName("coverage_areas")
    val coverageAreas: List<Any> = listOf(),
    @SerializedName("currency_id")
    val currencyId: String = "",
    @SerializedName("date_created")
    val dateCreated: String = "",
    @SerializedName("deal_ids")
    val dealIds: List<String> = listOf(),
    @SerializedName("descriptions")
    val descriptions: List<Any> = listOf(),
    @SerializedName("differential_pricing")
    val differentialPricing: Any? = null,
    @SerializedName("domain_id")
    val domainId: String = "",
    @SerializedName("health")
    val health: Double = 0.0,
    @SerializedName("id")
    val id: String = "",
    @SerializedName("initial_quantity")
    val initialQuantity: Int = 0,
    @SerializedName("international_delivery_mode")
    val internationalDeliveryMode: String = "",
    @SerializedName("last_updated")
    val lastUpdated: String = "",
    @SerializedName("listing_source")
    val listingSource: String = "",
    @SerializedName("listing_type_id")
    val listingTypeId: String = "",
    @SerializedName("location")
    val location: Location = Location(),
    @SerializedName("non_mercado_pago_payment_methods")
    val nonMercadoPagoPaymentMethods: List<Any> = listOf(),
    @SerializedName("official_store_id")
    val officialStoreId: Int = 0,
    @SerializedName("original_price")
    val originalPrice: Double = 0.0,
    @SerializedName("parent_item_id")
    val parentItemId: String = "",
    @SerializedName("permalink")
    val permalink: String = "",
    @SerializedName("pictures")
    val pictures: List<Picture> = listOf(),
    @SerializedName("price")
    val price: Double = 0.0,
    @SerializedName("sale_terms")
    val saleTerms: List<SaleTerm> = listOf(),
    @SerializedName("secure_thumbnail")
    val secureThumbnail: String = "",
    @SerializedName("seller_address")
    val sellerAddress: SellerAddress = SellerAddress(),
    @SerializedName("seller_contact")
    val sellerContact: Any? = null,
    @SerializedName("seller_id")
    val sellerId: Int = 0,
    @SerializedName("shipping")
    val shipping: Shipping = Shipping(),
    @SerializedName("site_id")
    val siteId: String = "",
    @SerializedName("sold_quantity")
    val soldQuantity: Int = 0,
    @SerializedName("start_time")
    val startTime: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("stop_time")
    val stopTime: String = "",
    @SerializedName("sub_status")
    val subStatus: List<Any> = listOf(),
    @SerializedName("subtitle")
    val subtitle: Any? = null,
    @SerializedName("tags")
    val tags: List<String> = listOf(),
    @SerializedName("thumbnail")
    val thumbnail: String = "",
    @SerializedName("thumbnail_id")
    val thumbnailId: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("variations")
    val variations: List<Variation> = listOf(),
    @SerializedName("video_id")
    val videoId: Any? = null,
    @SerializedName("warnings")
    val warnings: List<Any> = listOf(),
    @SerializedName("warranty")
    val warranty: String = ""
) {
    data class Attribute(
        @SerializedName("attribute_group_id")
        val attributeGroupId: String = "",
        @SerializedName("attribute_group_name")
        val attributeGroupName: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("value_id")
        val valueId: String? = null,
        @SerializedName("value_name")
        val valueName: String? = null,
        @SerializedName("value_struct")
        val valueStruct: Any? = null,
        @SerializedName("value_type")
        val valueType: String = "",
        @SerializedName("values")
        val values: List<Value> = listOf()
    ) {
        data class Value(
            @SerializedName("id")
            val id: String? = null,
            @SerializedName("name")
            val name: String? = null,
            @SerializedName("struct")
            val struct: Any? = null
        )
    }

    class Location

    data class Picture(
        @SerializedName("id")
        val id: String = "",
        @SerializedName("max_size")
        val maxSize: String = "",
        @SerializedName("quality")
        val quality: String = "",
        @SerializedName("secure_url")
        val secureUrl: String = "",
        @SerializedName("size")
        val size: String = "",
        @SerializedName("url")
        val url: String = ""
    )

    data class SaleTerm(
        @SerializedName("id")
        val id: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("value_id")
        val valueId: String = "",
        @SerializedName("value_name")
        val valueName: String = "",
        @SerializedName("value_struct")
        val valueStruct: Any? = null,
        @SerializedName("value_type")
        val valueType: String = "",
        @SerializedName("values")
        val values: List<Value> = listOf()
    ) {
        data class Value(
            @SerializedName("id")
            val id: String = "",
            @SerializedName("name")
            val name: String = "",
            @SerializedName("struct")
            val struct: Any? = null
        )
    }

    data class SellerAddress(
        @SerializedName("city")
        val city: City = City(),
        @SerializedName("country")
        val country: Country = Country(),
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("search_location")
        val searchLocation: SearchLocation = SearchLocation(),
        @SerializedName("state")
        val state: State = State()
    ) {
        data class City(
            @SerializedName("id")
            val id: String = "",
            @SerializedName("name")
            val name: String = ""
        )

        data class Country(
            @SerializedName("id")
            val id: String = "",
            @SerializedName("name")
            val name: String = ""
        )

        data class SearchLocation(
            @SerializedName("city")
            val city: City = City(),
            @SerializedName("state")
            val state: State = State()
        ) {
            data class City(
                @SerializedName("id")
                val id: String = "",
                @SerializedName("name")
                val name: String = ""
            )

            data class State(
                @SerializedName("id")
                val id: String = "",
                @SerializedName("name")
                val name: String = ""
            )
        }

        data class State(
            @SerializedName("id")
            val id: String = "",
            @SerializedName("name")
            val name: String = ""
        )
    }

    data class Shipping(
        @SerializedName("dimensions")
        val dimensions: Any? = null,
        @SerializedName("free_shipping")
        val freeShipping: Boolean = false,
        @SerializedName("local_pick_up")
        val localPickUp: Boolean = false,
        @SerializedName("logistic_type")
        val logisticType: String = "",
        @SerializedName("methods")
        val methods: List<Any> = listOf(),
        @SerializedName("mode")
        val mode: String = "",
        @SerializedName("store_pick_up")
        val storePickUp: Boolean = false,
        @SerializedName("tags")
        val tags: List<Any> = listOf()
    )

    data class Variation(
        @SerializedName("attribute_combinations")
        val attributeCombinations: List<AttributeCombination> = listOf(),
        @SerializedName("available_quantity")
        val availableQuantity: Int = 0,
        @SerializedName("catalog_product_id")
        val catalogProductId: Any? = null,
        @SerializedName("id")
        val id: Long = 0,
        @SerializedName("picture_ids")
        val pictureIds: List<String> = listOf(),
        @SerializedName("price")
        val price: Int = 0,
        @SerializedName("sale_terms")
        val saleTerms: List<Any> = listOf(),
        @SerializedName("sold_quantity")
        val soldQuantity: Int = 0
    ) {
        data class AttributeCombination(
            @SerializedName("id")
            val id: String = "",
            @SerializedName("name")
            val name: String = "",
            @SerializedName("value_id")
            val valueId: String = "",
            @SerializedName("value_name")
            val valueName: String = "",
            @SerializedName("value_struct")
            val valueStruct: Any? = null,
            @SerializedName("value_type")
            val valueType: String = "",
            @SerializedName("values")
            val values: List<Value> = listOf()
        ) {
            data class Value(
                @SerializedName("id")
                val id: String = "",
                @SerializedName("name")
                val name: String = "",
                @SerializedName("struct")
                val struct: Any? = null
            )
        }
    }
    fun toArticleDetail(description: String) = ArticleDetail(
        id = id,
        title = title,
        description = description,
        price = price.toPriceFormat(),
        originalPrice = originalPrice.toPriceFormat(),
        condition = condition,
        characteristic = attributes.getAttributesText(),
        pictures = pictures.map { it.secureUrl },
        isFreeShipping = shipping.freeShipping,
        permalink = permalink,
        thumbnail = thumbnail.toSecureProtocol(),
        availableQuantity = availableQuantity
    )
}