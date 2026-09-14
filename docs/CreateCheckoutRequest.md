

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



## Enum: AllocationModelEnum

| Name | Value |
|---- | -----|
| BASIC_AMOUNT | &quot;basic_amount&quot; |
| ITEM_ASSIGNMENT | &quot;item_assignment&quot; |



