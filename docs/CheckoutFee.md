

# CheckoutFee


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** |  |  |
|**description** | **String** |  |  [optional] |
|**feeType** | [**FeeTypeEnum**](#FeeTypeEnum) |  |  [optional] |
|**scope** | [**ScopeEnum**](#ScopeEnum) |  |  |
|**amount** | **Integer** |  |  |
|**currency** | **String** |  |  |
|**metadata** | **Map&lt;String, Object&gt;** |  |  [optional] |
|**appliesTo** | **List&lt;String&gt;** |  |  [optional] |



## Enum: FeeTypeEnum

| Name | Value |
|---- | -----|
| REFUND_PROTECTION | &quot;refund_protection&quot; |
| SURCHARGE | &quot;surcharge&quot; |
| TRANSACTION_FEE | &quot;transaction_fee&quot; |
| SERVICE_FEE | &quot;service_fee&quot; |
| DELIVERY_FEE | &quot;delivery_fee&quot; |
| CARBON_OFFSET | &quot;carbon_offset&quot; |
| OTHER | &quot;other&quot; |



## Enum: ScopeEnum

| Name | Value |
|---- | -----|
| CART | &quot;cart&quot; |
| ITEM | &quot;item&quot; |



