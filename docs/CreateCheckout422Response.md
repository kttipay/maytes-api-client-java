

# CreateCheckout422Response


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**message** | **String** |  |  |
|**error** | **String** |  |  |
|**code** | [**CodeEnum**](#CodeEnum) |  |  |
|**metadata** | **Map&lt;String, Object&gt;** |  |  [optional] |



## Enum: CodeEnum

| Name | Value |
|---- | -----|
| INVALID_AMOUNT | &quot;invalid_amount&quot; |
| AMOUNT_BELOW_MINIMUM | &quot;amount_below_minimum&quot; |
| INVALID_AMOUNT_ARITHMETIC | &quot;invalid_amount_arithmetic&quot; |
| CURRENCY_NOT_SUPPORTED | &quot;currency_not_supported&quot; |
| MERCHANT_CURRENCY_MISMATCH | &quot;merchant_currency_mismatch&quot; |
| MERCHANT_NOT_CONFIGURED | &quot;merchant_not_configured&quot; |



