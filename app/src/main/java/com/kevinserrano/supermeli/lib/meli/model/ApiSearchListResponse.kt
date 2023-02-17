package com.kevinserrano.supermeli.lib.meli.model


import com.google.gson.annotations.SerializedName
import com.kevinserrano.supermeli.search.model.Article
import com.kevinserrano.supermeli.utils.getDueDetail
import com.kevinserrano.supermeli.utils.toPriceFormat
import com.kevinserrano.supermeli.utils.toSecureProtocol

data class ApiSearchListResponse(
    @SerializedName("country_default_time_zone")
    val countryDefaultTimeZone: String = "",
    @SerializedName("paging")
    val paging: Paging = Paging(),
    @SerializedName("query")
    val query: String = "",
    @SerializedName("results")
    val results: List<Result> = listOf(),
    @SerializedName("site_id")
    val siteId: String = "",
    @SerializedName("sort")
    val sort: Sort = Sort()
) {

    data class Paging(
        @SerializedName("limit")
        val limit: Int = 0,
        @SerializedName("offset")
        val offset: Int = 0,
        @SerializedName("primary_results")
        val primaryResults: Int = 0,
        @SerializedName("total")
        val total: Int = 0
    )

    data class Result(
        @SerializedName("accepts_mercadopago")
        val acceptsMercadopago: Boolean = false,
        @SerializedName("attributes")
        val attributes: List<Attribute> = listOf(),
        @SerializedName("available_quantity")
        val availableQuantity: Int = 0,
        @SerializedName("buying_mode")
        val buyingMode: String = "",
        @SerializedName("category_id")
        val categoryId: String = "",
        @SerializedName("condition")
        val condition: String = "",
        @SerializedName("currency_id")
        val currencyId: String = "",
        @SerializedName("differential_pricing")
        val differentialPricing: DifferentialPricing = DifferentialPricing(),
        @SerializedName("domain_id")
        val domainId: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("installments")
        val installments: Installments? = null,
        @SerializedName("inventory_id")
        val inventoryId: String = "",
        @SerializedName("listing_type_id")
        val listingTypeId: String = "",
        @SerializedName("official_store_id")
        val officialStoreId: Int = 0,
        @SerializedName("order_backend")
        val orderBackend: Int = 0,
        @SerializedName("original_price")
        val originalPrice: Int = 0,
        @SerializedName("permalink")
        val permalink: String = "",
        @SerializedName("price")
        val price: Double = 0.0,
        @SerializedName("sale_price")
        val salePrice: String? = null,
        @SerializedName("seller")
        val seller: Seller = Seller(),
        @SerializedName("shipping")
        val shipping: Shipping = Shipping(),
        @SerializedName("site_id")
        val siteId: String = "",
        @SerializedName("sold_quantity")
        val soldQuantity: Int = 0,
        @SerializedName("stop_time")
        val stopTime: String = "",
        @SerializedName("tags")
        val tags: List<String> = listOf(),
        @SerializedName("thumbnail")
        val thumbnail: String = "",
        @SerializedName("thumbnail_id")
        val thumbnailId: String = "",
        @SerializedName("title")
        val title: String = "",
        @SerializedName("use_thumbnail_id")
        val useThumbnailId: Boolean = false,
        @SerializedName("variation_filters")
        val variationFilters: List<String> = listOf(),
        @SerializedName("variations_data")
        val variationsData: VariationsData = VariationsData(),
        @SerializedName("winner_item_id")
        val winnerItemId: String? = null
    ) {
        fun toArticle(): Article {
            return Article(
                id = id,
                title = title,
                thumbnail = thumbnail.toSecureProtocol(),
                price = price.toPriceFormat(),
                condition = condition,
                isFreeShipping = shipping.freeShipping,
                sellerName = seller.nickname,
                sellerLink = seller.permalink,
                meliChoiceCandidate = tags.contains("meli_choice_candidate"),
                dueText = installments.getDueDetail()
            )
        }

        data class Attribute(
            @SerializedName("attribute_group_id")
            val attributeGroupId: String = "",
            @SerializedName("attribute_group_name")
            val attributeGroupName: String = "",
            @SerializedName("id")
            val id: String = "",
            @SerializedName("name")
            val name: String = "",
            @SerializedName("source")
            val source: Long = 0,
            @SerializedName("value_id")
            val valueId: String = "",
            @SerializedName("value_name")
            val valueName: String = "",
            @SerializedName("value_struct")
            val valueStruct: ValueStruct = ValueStruct(),
            @SerializedName("value_type")
            val valueType: String = "",
            @SerializedName("values")
            val values: List<Value> = listOf()
        ) {
            data class ValueStruct(
                @SerializedName("number")
                val number: Double = 0.0,
                @SerializedName("unit")
                val unit: String = ""
            )

            data class Value(
                @SerializedName("id")
                val id: String = "",
                @SerializedName("name")
                val name: String = "",
                @SerializedName("source")
                val source: Long = 0,
                @SerializedName("struct")
                val struct: Struct = Struct()
            ) {
                data class Struct(
                    @SerializedName("number")
                    val number: Double = 0.0,
                    @SerializedName("unit")
                    val unit: String = ""
                )
            }
        }

        data class DifferentialPricing(
            @SerializedName("id")
            val id: Int = 0
        )

        data class Installments(
            @SerializedName("amount")
            val amount: Double = 0.0,
            @SerializedName("currency_id")
            val currencyId: String = "",
            @SerializedName("quantity")
            val quantity: Int = 0,
            @SerializedName("rate")
            val rate: Int = 0
        )

        data class Seller(
            @SerializedName("car_dealer")
            val carDealer: Boolean = false,
            @SerializedName("car_dealer_logo")
            val carDealerLogo: String = "",
            @SerializedName("eshop")
            val eshop: Eshop = Eshop(),
            @SerializedName("id")
            val id: Int = 0,
            @SerializedName("nickname")
            val nickname: String = "",
            @SerializedName("permalink")
            val permalink: String = "",
            @SerializedName("real_estate_agency")
            val realEstateAgency: Boolean = false,
            @SerializedName("registration_date")
            val registrationDate: String = "",
            @SerializedName("seller_reputation")
            val sellerReputation: SellerReputation = SellerReputation(),
            @SerializedName("tags")
            val tags: List<String> = listOf(),
            @SerializedName("_")
            val x: Boolean = false
        ) {
            data class Eshop(
                @SerializedName("eshop_experience")
                val eshopExperience: Int = 0,
                @SerializedName("eshop_id")
                val eshopId: Int = 0,
                @SerializedName("eshop_logo_url")
                val eshopLogoUrl: String = "",
                @SerializedName("eshop_status_id")
                val eshopStatusId: Int = 0,
                @SerializedName("nick_name")
                val nickName: String = "",
                @SerializedName("seller")
                val seller: Int = 0,
                @SerializedName("site_id")
                val siteId: String = ""
            )

            data class SellerReputation(
                @SerializedName("level_id")
                val levelId: String = "",
                @SerializedName("metrics")
                val metrics: Metrics = Metrics(),
                @SerializedName("power_seller_status")
                val powerSellerStatus: String = "",
                @SerializedName("transactions")
                val transactions: Transactions = Transactions()
            ) {
                data class Metrics(
                    @SerializedName("cancellations")
                    val cancellations: Cancellations = Cancellations(),
                    @SerializedName("claims")
                    val claims: Claims = Claims(),
                    @SerializedName("delayed_handling_time")
                    val delayedHandlingTime: DelayedHandlingTime = DelayedHandlingTime(),
                    @SerializedName("sales")
                    val sales: Sales = Sales()
                ) {
                    data class Cancellations(
                        @SerializedName("excluded")
                        val excluded: Excluded = Excluded(),
                        @SerializedName("period")
                        val period: String = "",
                        @SerializedName("rate")
                        val rate: Double = 0.0,
                        @SerializedName("value")
                        val value: Int = 0
                    ) {
                        data class Excluded(
                            @SerializedName("real_rate")
                            val realRate: Double = 0.0,
                            @SerializedName("real_value")
                            val realValue: Int = 0
                        )
                    }

                    data class Claims(
                        @SerializedName("excluded")
                        val excluded: Excluded = Excluded(),
                        @SerializedName("period")
                        val period: String = "",
                        @SerializedName("rate")
                        val rate: Double = 0.0,
                        @SerializedName("value")
                        val value: Int = 0
                    ) {
                        data class Excluded(
                            @SerializedName("real_rate")
                            val realRate: Double = 0.0,
                            @SerializedName("real_value")
                            val realValue: Int = 0
                        )
                    }

                    data class DelayedHandlingTime(
                        @SerializedName("excluded")
                        val excluded: Excluded = Excluded(),
                        @SerializedName("period")
                        val period: String = "",
                        @SerializedName("rate")
                        val rate: Double = 0.0,
                        @SerializedName("value")
                        val value: Int = 0
                    ) {
                        data class Excluded(
                            @SerializedName("real_rate")
                            val realRate: Double = 0.0,
                            @SerializedName("real_value")
                            val realValue: Int = 0
                        )
                    }

                    data class Sales(
                        @SerializedName("completed")
                        val completed: Int = 0,
                        @SerializedName("period")
                        val period: String = ""
                    )
                }

                data class Transactions(
                    @SerializedName("canceled")
                    val canceled: Int = 0,
                    @SerializedName("completed")
                    val completed: Int = 0,
                    @SerializedName("period")
                    val period: String = "",
                    @SerializedName("ratings")
                    val ratings: Ratings = Ratings(),
                    @SerializedName("total")
                    val total: Int = 0
                ) {
                    data class Ratings(
                        @SerializedName("negative")
                        val negative: Double = 0.0,
                        @SerializedName("neutral")
                        val neutral: Double = 0.0,
                        @SerializedName("positive")
                        val positive: Double = 0.0
                    )
                }
            }
        }


        data class Shipping(
            @SerializedName("free_shipping")
            val freeShipping: Boolean = false,
            @SerializedName("logistic_type")
            val logisticType: String = "",
            @SerializedName("mode")
            val mode: String = "",
            @SerializedName("store_pick_up")
            val storePickUp: Boolean = false,
            @SerializedName("tags")
            val tags: List<String> = listOf()
        )

        data class VariationsData(
            @SerializedName("173552544138")
            val x173552544138: X173552544138 = X173552544138(),
            @SerializedName("176261548392")
            val x176261548392: X176261548392 = X176261548392(),
            @SerializedName("68466014709")
            val x68466014709: X68466014709 = X68466014709()
        ) {
            data class X173552544138(
                @SerializedName("name")
                val name: String = "",
                @SerializedName("pictures_qty")
                val picturesQty: Int = 0,
                @SerializedName("ratio")
                val ratio: String = "",
                @SerializedName("thumbnail")
                val thumbnail: String = ""
            )

            data class X176261548392(
                @SerializedName("name")
                val name: String = "",
                @SerializedName("pictures_qty")
                val picturesQty: Int = 0,
                @SerializedName("ratio")
                val ratio: String = "",
                @SerializedName("thumbnail")
                val thumbnail: String = ""
            )

            data class X68466014709(
                @SerializedName("inventory_id")
                val inventoryId: String = "",
                @SerializedName("name")
                val name: String = "",
                @SerializedName("pictures_qty")
                val picturesQty: Int = 0,
                @SerializedName("ratio")
                val ratio: String = "",
                @SerializedName("thumbnail")
                val thumbnail: String = ""
            )
        }
    }

    data class Sort(
        @SerializedName("id")
        val id: String = "",
        @SerializedName("name")
        val name: String = ""
    )
}