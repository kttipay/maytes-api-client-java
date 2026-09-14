

# TicketCustomData


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**eventId** | **String** |  |  |
|**eventStartAt** | **OffsetDateTime** |  |  |
|**timezone** | **String** |  |  |
|**ticketClass** | **String** |  |  [optional] |
|**name** | **String** |  |  [optional] |
|**eventEndAt** | **OffsetDateTime** |  |  [optional] |
|**deliveryMode** | [**DeliveryModeEnum**](#DeliveryModeEnum) |  |  [optional] |
|**venueName** | **String** |  |  [optional] |
|**venueAddress** | **Map&lt;String, Object&gt;** |  |  [optional] |
|**seating** | [**TicketSeating**](TicketSeating.md) |  |  [optional] |
|**termsUrl** | **String** |  |  [optional] |
|**attendeeRequired** | **Boolean** |  |  [optional] |



## Enum: DeliveryModeEnum

| Name | Value |
|---- | -----|
| EMAIL | &quot;email&quot; |
| SMS | &quot;sms&quot; |
| POST | &quot;post&quot; |
| AT_VENUE | &quot;at_venue&quot; |



