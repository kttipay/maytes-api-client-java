

# CreateCheckoutRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**merchantOrderId** | **String** |  |  [optional] |
|**merchantExpiresAt** | **OffsetDateTime** |  |  [optional] |
|**totalAmount** | **Integer** |  |  |
|**currency** | **String** |  |  |
|**returnUrl** | **URI** |  |  |
|**cancelUrl** | **URI** |  |  |
|**items** | [**List&lt;LineItem&gt;**](LineItem.md) |  |  |
|**fees** | [**List&lt;CheckoutFee&gt;**](CheckoutFee.md) |  |  [optional] |
|**discounts** | [**List&lt;CheckoutDiscount&gt;**](CheckoutDiscount.md) |  |  [optional] |
|**tax** | [**Tax**](Tax.md) |  |  [optional] |
|**shippingAmount** | **Integer** |  |  [optional] |
|**customerData** | [**CustomerData**](CustomerData.md) |  |  [optional] |
|**metadata** | **Map&lt;String, Object&gt;** |  |  [optional] |
|**allocationModel** | [**AllocationModelEnum**](#AllocationModelEnum) |  |  [optional] |
|**friends** | [**CheckoutFriends**](CheckoutFriends.md) | Who the customer is expected to split with. Pre-fills their share and allocation screens; the customer can still change it. Personal data — send it only when you hold it. |  [optional] |



## Enum: AllocationModelEnum

| Name | Value |
|---- | -----|
| BASIC_AMOUNT | &quot;basic_amount&quot; |
| ITEM_ASSIGNMENT | &quot;item_assignment&quot; |



